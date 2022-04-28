package br.org.flem.primeiroemprego.dao;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.primeiroemprego.entity.DistanciaEntreMunicipios;
import br.org.flem.primeiroemprego.entity.Municipio;
import com.google.maps.DirectionsApi;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.errors.NotFoundException;
import com.google.maps.errors.OverDailyLimitException;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;
import com.google.maps.model.TravelMode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class DistanciaEntreMunicipiosDAO extends GenericDAO<DistanciaEntreMunicipios, Long>{

    public static final GeoApiContext CONTEXT_INDIVIDUAL = new GeoApiContext.Builder().apiKey(PropertiesUtil.getProperty("googleApiKeyIndividual")).build();

    public static final GeoApiContext CONTEXT_DESENV   = new GeoApiContext.Builder().apiKey(PropertiesUtil.getProperty("googleApiKeyDesenv")).build();
    public static final GeoApiContext CONTEXT_HOMOLOGA = new GeoApiContext.Builder().apiKey(PropertiesUtil.getProperty("googleApiKeyHomologa")).build();
    public static final GeoApiContext CONTEXT_PRODUCAO = new GeoApiContext.Builder().apiKey(PropertiesUtil.getProperty("googleApiProducao")).build();

    private GeoApiContext currentContext = CONTEXT_DESENV;

    public DistanciaEntreMunicipiosDAO(){
        super(DistanciaEntreMunicipios.class);
    }

    //Caso a distancia ainda não tenha sido calculada é feito uma requisição para calcular
    public DistanciaEntreMunicipios distanciaEntreMunicipios(Municipio municipioOrigem,Municipio municipioDestino) throws ApiException{
        if(municipioOrigem != null && municipioDestino != null && !municipioOrigem.equals(municipioDestino)){
            DistanciaEntreMunicipios distancia = distanciaEntreMunicipiosSemRequisicao(municipioOrigem, municipioDestino);
            if(distancia == null){
                calcularDistanciasQuebradoDirections(municipioOrigem, municipioDestino);
                return distanciaEntreMunicipiosSemRequisicao(municipioOrigem, municipioDestino);
            }
            return distancia;
        }else{
            return null;
        }
    }

    private DistanciaEntreMunicipios distanciaEntreMunicipiosSemRequisicao(Municipio municipioOrigem,Municipio municipioDestino){
        try{
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<DistanciaEntreMunicipios> criteria = cb.createQuery(DistanciaEntreMunicipios.class);
            Root<DistanciaEntreMunicipios> root = criteria.from(DistanciaEntreMunicipios.class);
            criteria.where(cb.and(cb.equal(root.get("municipioOrigem"), municipioOrigem),
                                  cb.equal(root.get("municipioDestino"), municipioDestino)));
            return getEntityManager().createQuery(criteria).getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
    
    public boolean distanciaJaCalculada(Municipio municipioOrigem,Municipio municipioDestino){
        if(municipioOrigem != null && municipioDestino != null){
            return distanciaEntreMunicipiosSemRequisicao(municipioOrigem, municipioDestino) != null;
        }else{
            return false;
        }
    }

    public List<Municipio> municipiosCalculadosDoMunicipio(Municipio municipio){
        try{
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Municipio> criteria = cb.createQuery(Municipio.class);
            Root<DistanciaEntreMunicipios> root = criteria.from(DistanciaEntreMunicipios.class);
            criteria.select(root.<Municipio>get("municipioDestino"));
            criteria.where(cb.equal(root.get("municipioOrigem"), municipio));
            return getEntityManager().createQuery(criteria).getResultList();
        }catch(Exception e){
            return new ArrayList<Municipio>();
        }
    }

    public void calcularDistancias(final Municipio municipio,List<Municipio> municipios) throws Exception{
        if(municipio != null && municipios!= null && !municipios.isEmpty()){
            Set<Municipio> municipiosParaCalcular = new HashSet<Municipio>(municipios);
            List<Municipio> municipiosJaCalculados = municipiosCalculadosDoMunicipio(municipio);
            if(!municipiosJaCalculados.contains(municipio)){
                inserirDistanciaParaSiMesmo(municipio);// para ter o valor da distancia entre si mesmo no banco
            }
            municipiosParaCalcular.removeAll(municipiosJaCalculados);//Remove os já calculados
            municipiosParaCalcular.remove(municipio);//Garantir que não vai calcular a distancia no gmaps entre o mesmo municipio

            for(Municipio municipioDestino: municipiosParaCalcular){
                try{
                    calcularDistanciasQuebradoDirections(municipio, municipioDestino);
                }catch(OverDailyLimitException ex){
                    if(getCurrentContext().equals(CONTEXT_PRODUCAO)){
                        throw new Exception("Acabou a cota das três contas");
                    }
                    setCurrentContext(CONTEXT_HOMOLOGA);
                    try{
                        calcularDistanciasQuebradoDirections(municipio, municipioDestino);
                    }catch(OverDailyLimitException ex1){
                        if(getCurrentContext().equals(CONTEXT_PRODUCAO)){
                            throw new Exception("Acabou a cota das três contas");
                        }
                        setCurrentContext(CONTEXT_PRODUCAO);
                        try{
                            calcularDistanciasQuebradoDirections(municipio, municipioDestino);
                        }catch(OverDailyLimitException ex2){
                            Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.SEVERE, "googleApiProducao", ex2);
                            throw new Exception("Acabou a cota das três contas");
                        }
                        Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.SEVERE, "googleApiKeyHomologa", ex1);
                    }
                    Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.SEVERE, "googleApiDesenv", ex);
                }
            }
        }
    }
    
    private void inserirDistanciaParaSiMesmo(Municipio municipio){
        try{
            DistanciaEntreMunicipios d = new DistanciaEntreMunicipios();
            d.setMunicipioOrigem(municipio);
            d.setMunicipioDestino(municipio);
            d.setDistancia(0l);

            inserir(d);
        } catch (Exception ex) {
            Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void calcularDistanciasDireto(final Municipio municipioOrigem,Municipio municipioDestino) throws Exception{
        if(municipioOrigem != null && municipioDestino != null){
            List<Municipio> municipiosJaCalculados = municipiosCalculadosDoMunicipio(municipioOrigem);
            if(!municipiosJaCalculados.contains(municipioDestino)){
                setCurrentContext(CONTEXT_INDIVIDUAL);
                calcularDistanciasQuebradoDirections(municipioOrigem, municipioDestino);
            }
        }
    }

    private void calcularDistanciasQuebradoDirections(final Municipio municipioOrigem,Municipio municipioDestino) throws ApiException{
        
        DirectionsApiRequest req = DirectionsApi.getDirections(currentContext, municipioOrigem.getNome()+", "+municipioOrigem.getUf(), municipioDestino.getNome()+", "+municipioDestino.getUf());
        req.avoid(DirectionsApi.RouteRestriction.FERRIES);
        req.mode(TravelMode.DRIVING).alternatives(true);

        try{
            DirectionsResult directions = req.await();
            DistanciaEntreMunicipios d;
            d = new DistanciaEntreMunicipios();
            d.setMunicipioOrigem(municipioOrigem);
            d.setMunicipioDestino(municipioDestino);

            if(directions.routes.length == 0){
                d.setDistancia(-1l);
            }else{
                for(DirectionsRoute route : directions.routes){
                    if(d.getDistancia() == null || route.legs[0].distance.inMeters < d.getDistancia()){
                        d.setDistancia(route.legs[0].distance.inMeters);
                    }
                }
            }
            try {
                inserir(d);
            }catch(Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            }
        }catch(InterruptedException e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        } catch (IOException ex) {
            Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.SEVERE, null, ex);
        }catch(NotFoundException ex1){
            Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }

    public GeoApiContext getCurrentContext() {
        return currentContext;
    }

    public void setCurrentContext(GeoApiContext currentContext) {
        this.currentContext = currentContext;
    }

}

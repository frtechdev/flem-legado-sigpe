package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DistanciaEntreMunicipiosDAO;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class PendenteDeCalculoDeDistanciaMB implements Serializable{

    @ManagedProperty(value = "#{egressoDAO}")
    private EgressoDAO egressoDAO;
    
    @ManagedProperty(value = "#{distanciaEntreMunicipiosDAO}")
    private DistanciaEntreMunicipiosDAO distanciaEntreMunicipiosDAO;

    private List<Egresso> egressos;

    @PostConstruct
    public void init(){
        egressos = egressoDAO.pendentesDeCalculoDeDistanciaEntreMunicipios();
    }

    public void calcularDistancia(Egresso egresso){
        try {
            distanciaEntreMunicipiosDAO.calcularDistanciasDireto(egresso.getMunicipio(), egresso.getVaga().getMunicipio());
            init();
            Mensagem.lancar("Distancia entre os municipios calculada.");
        } catch (Exception ex) {
            Mensagem.lancar("Erro ao calcular a distancia entre os municipios.");
            Logger.getLogger(PendenteDeCalculoDeDistanciaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public List<Egresso> getEgressos() {
        return egressos;
    }

    public void setEgressos(List<Egresso> egressos) {
        this.egressos = egressos;
    }

    public DistanciaEntreMunicipiosDAO getDistanciaEntreMunicipiosDAO() {
        return distanciaEntreMunicipiosDAO;
    }

    public void setDistanciaEntreMunicipiosDAO(DistanciaEntreMunicipiosDAO distanciaEntreMunicipiosDAO) {
        this.distanciaEntreMunicipiosDAO = distanciaEntreMunicipiosDAO;
    }

}

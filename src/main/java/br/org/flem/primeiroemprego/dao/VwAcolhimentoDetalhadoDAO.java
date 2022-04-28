
package br.org.flem.primeiroemprego.dao;

import br.org.flem.fwe.util.StringUtil;
import br.org.flem.primeiroemprego.dto.FiltroFrequencia;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.VwAcolhimentoDetalhado;
import br.org.flem.primeiroemprego.util.CommonDAO;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Classe VwAcolhimentoDetalhadoDAO
 * @author <code>tscortes@flem.org.br</code>
 * 25/02/2019
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class VwAcolhimentoDetalhadoDAO extends CommonDAO {
    
    
    /**
     * Obter lista de beneficiários que participarão do Evento
     * @author <code>tscortes@flem.org.br</code>
     * @param filtro FiltroFrequencia
     * @return listaFrequencia List
     */
    public List<VwAcolhimentoDetalhado> obterListaDeAcolhimento(FiltroFrequencia filtro){
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<VwAcolhimentoDetalhado> criteria = cb.createQuery(VwAcolhimentoDetalhado.class);
        
        Root<VwAcolhimentoDetalhado> root = criteria.from(VwAcolhimentoDetalhado.class);
        
        if( filtro != null ){
            List<Predicate> predicates = new ArrayList<>();
            if (filtro.getCpfs()!= null && !filtro.getCpfs().isEmpty()){
                predicates.add(root.get("cpf").in(filtro.getCpfs()));
            }
            if (filtro.getDemandantes() != null && !filtro.getDemandantes().isEmpty()){    
                List<Long> ids = new ArrayList<>();
                filtro.getDemandantes().forEach(obj ->{ ids.add(obj.getId());});
                predicates.add(root.get("idDemandante").in(ids));
            }
            
            if (filtro.getEscritoriosRegionais()!= null && !filtro.getEscritoriosRegionais().isEmpty()){    
                List<Long> ids = new ArrayList<>();
                filtro.getEscritoriosRegionais().forEach(obj ->{ ids.add(obj.getId());});
                predicates.add(root.get("idEscritorioRegional").in(ids));
            }
            
            if (filtro.getMunicipios()!= null && !filtro.getMunicipios().isEmpty()){   
                List<Long> ids = new ArrayList<>();
                filtro.getMunicipios().forEach(obj ->{ ids.add(obj.getId());});
                predicates.add(root.get("idMunicipio").in(ids));
            }
            
            if (filtro.getCategorias()!= null && !filtro.getCategorias().isEmpty()){
                List<Long> ids = filtro.getCategorias()
                        .stream()
                        .map(CategoriaDaSituacao::getId)
                        .collect(Collectors.toList());
                predicates.add(root.get("idCategoria").in(ids));
            }
            
            if (StringUtil.stringNotNull(filtro.getAcolhido())){    
                predicates.add(cb.equal(root.get("participou"), filtro.getAcolhido()));
            }
            
            if(!predicates.isEmpty()){
                criteria.where(predicates.toArray(new Predicate[]{}));
            }
        }
        criteria.orderBy(
                cb.asc(root.get("escritorioRegional")), 
                cb.asc(root.get("municipioVaga")), 
                cb.asc(root.get("demandante")), 
                cb.asc(root.get("unidadeDeLotacao")), 
                cb.asc(root.get("nome")));
        
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    /**
     * Obter lista de Categorias dos beneficiários
     * @author <code>tscortes@flem.org.br</code>
     * @param demandates
     * @param municipios
     * @return listaFrequencia List
     */
    public List<CategoriaDaSituacao> obterCategoriasAcolhimento(List<Long> demandates, List<Long> municipios){
        
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<CategoriaDaSituacao> criteria = cb.createQuery(CategoriaDaSituacao.class);
        
        Root<VwAcolhimentoDetalhado> root = criteria.from(VwAcolhimentoDetalhado.class);
        criteria.distinct(true);
        criteria.select(cb.construct(CategoriaDaSituacao.class,
                root.get("idCategoria"),
                root.get("categoriaSituacao")
        ));
        List<Predicate> predicates = new ArrayList<>();
        if (demandates != null && !demandates.isEmpty()){
            predicates.add(root.get("idDemandante").in(demandates));
        }
        if (municipios != null && !municipios.isEmpty()){
            predicates.add(root.get("idMunicipio").in(municipios));
        }
        
        if(!predicates.isEmpty()){
            criteria.where(predicates.toArray(new Predicate[]{}));
        }
        
        criteria.orderBy(cb.asc(root.get("categoriaSituacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    /**
     * 
     * @param municipios
     * @return 
     */
    public List<Demandante> obterDemandantesPorMunicipios(List<Long> municipios){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<Demandante> criteria = cb.createQuery(Demandante.class);
        
        Root<VwAcolhimentoDetalhado> root = criteria.from(VwAcolhimentoDetalhado.class);
        criteria.distinct(true);
        criteria.select(cb.construct(Demandante.class,
                root.get("idDemandante"),
                root.get("demandante")
        ));
        
        if (municipios != null && !municipios.isEmpty()){    
            criteria.where(root.get("idMunicipio").in(municipios));
        }
        criteria.orderBy(cb.asc(root.get("demandante")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    /**
     * 
     * @param escritorios
     * @return 
     */
    public List<Municipio> obterMunicipiosPorEscritorios(List<Long> escritorios){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<Municipio> criteria = cb.createQuery(Municipio.class);
        
        Root<VwAcolhimentoDetalhado> root = criteria.from(VwAcolhimentoDetalhado.class);
        criteria.distinct(true);
        criteria.select(cb.construct(Municipio.class,
                root.get("idMunicipio"),
                root.get("municipioVaga")
        ));
        
        if (escritorios != null && !escritorios.isEmpty()){
            criteria.where(root.get("idEscritorioRegional").in(escritorios));
        }
        criteria.orderBy(cb.asc(root.get("municipioVaga")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    /**
     * 
     * @return 
     */
    public List<EscritorioRegional> obterEscritorios(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery<EscritorioRegional> criteria = cb.createQuery(EscritorioRegional.class);
        
        Root<VwAcolhimentoDetalhado> root = criteria.from(VwAcolhimentoDetalhado.class);
        criteria.distinct(true);
        criteria.select(cb.construct(EscritorioRegional.class,
                root.get("idEscritorioRegional"),
                root.get("escritorioRegional")
        ));

        criteria.where(root.get("idEscritorioRegional").isNotNull());
        criteria.orderBy(cb.asc(root.get("escritorioRegional")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    /**
     * 
     * @return 
     */
    public List<Municipio> obterPorEscritorioNaoImpl(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Municipio> criteria = cb.createQuery(Municipio.class);
        Root<VwAcolhimentoDetalhado> root = criteria.from(VwAcolhimentoDetalhado.class);
        criteria.distinct(true);
        criteria.select(cb.construct(Municipio.class,
                root.get("idMunicipio"),
                root.get("municipioVaga")
        ));
        criteria.where(root.get("idEscritorioRegional").isNull());
        criteria.orderBy(cb.asc(root.get("municipioVaga")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
}

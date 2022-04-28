package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.dto.EgressoListaDTO;
import br.org.flem.primeiroemprego.entity.EgressoLista;
import br.org.flem.primeiroemprego.entity.Lista;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class EgressoListaDAO extends GenericDAO<EgressoLista, Long> {

    public EgressoListaDAO(){
        super(EgressoLista.class);
    }

    public List<EgressoLista> obterDaLista(Lista lista) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EgressoLista> criteria = cb.createQuery(EgressoLista.class);
        Root<EgressoLista> root = criteria.from(EgressoLista.class);
        criteria.where(cb.equal(root.get("lista"), lista));
        criteria.orderBy(cb.asc(root.get("egresso").get("nome")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    /**
     * Obter Egresso Lista DTO
     * @param lista
     * @return 
     */
    public List<EgressoListaDTO> obterDaListaDTO(Lista lista) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        //CriteriaQuery<ListaEgressoDTO> criteria = cb.createQuery(ListaEgressoDTO.class);
        CriteriaQuery<EgressoListaDTO> criteria = cb.createQuery(EgressoListaDTO.class);
        Root<EgressoLista> root = criteria.from(EgressoLista.class);
        Join joinEgresso = root.join("egresso");
        Join joinLista = root.join("lista");
        //Long id, String nomeEgresso, String cpfEgresso, Long idLista, Boolean feito
        criteria.select(cb.construct(EgressoListaDTO.class,
                root.get("id"),
                joinEgresso.get("id"),
                joinEgresso.get("nome"),
                joinEgresso.get("cpf"),
                joinLista.get("id"),
                root.get("feito"),
                root.get("deAcordo")
        ));
        
        criteria.where(cb.equal(root.get("lista"), lista));
        criteria.orderBy(cb.asc(joinEgresso.get("nome")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public boolean existeEgressoLista(List<EgressoLista> egressosLista, Lista lista){
        if(egressosLista != null && !egressosLista.isEmpty()){
            for(EgressoLista eg : egressosLista){
                if(eg.getLista().equals(lista)){
                    return true;
                }
            }
        }
        return false;
    }
    
    public Long qtdConcluidos(Lista lista){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
        Root<EgressoLista> root = criteria.from(EgressoLista.class);
        criteria.select(cb.count(root));
        criteria.where(cb.and(
                                cb.equal(root.get("lista"), lista),
                                cb.equal(root.get("feito"), true)));
        return getEntityManager().createQuery(criteria).getSingleResult();
    }

}
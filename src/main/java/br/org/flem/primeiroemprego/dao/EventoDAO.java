package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Evento;
import java.util.List;
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
public class EventoDAO extends GenericDAO<Evento, Long> {
    
    public EventoDAO() throws Exception{
        super(Evento.class);
        this.nomeColunaParaOdemSimples = "nome";
    }
    
    public List<String> obterRespostasDoCampo(String campo){
        CriteriaBuilder cb  = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        criteria.distinct(true);
        Root<Evento> root = criteria.from(Evento.class);
        criteria.select(root.<String>get(campo));
        criteria.where(cb.isNotNull(root.get(campo)));
        criteria.orderBy(cb.asc(root.get(campo)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Evento> obterEventoOrdenadoPorDataDesc(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Evento> criteria = cb.createQuery(Evento.class);
        Root<Evento> root = criteria.from(Evento.class);
        criteria.orderBy(cb.desc(root.get("data")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
}

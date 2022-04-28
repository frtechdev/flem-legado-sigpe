package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Colaborador;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class ColaboradorDAO extends GenericDAO<Colaborador, Long>  {
    
    public ColaboradorDAO(){
        super(Colaborador.class);
    }
    
    public List<Colaborador> obterListaAtivosOuInativos(Boolean ativo){
        
        String str = "from Colaborador t where t.ativo = :ativo";
        Query query = getEntityManager().createQuery(str);
        query.setParameter("ativo", ativo);

        return query.getResultList();
    }
    
    public List<Colaborador> obterPorEscritorioRegional(EscritorioRegional escritorio){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Colaborador> query = cb.createQuery(Colaborador.class);
        Root<Colaborador> root = query.from(Colaborador.class);
        query.where(
                cb.equal(root.get("escritorioRegional"), escritorio), 
                cb.equal(root.get("ativo"), Boolean.TRUE)
        );
        query.orderBy(cb.asc(root.get("nome")));
        return getEntityManager().createQuery(query).getResultList();
    }
}

package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Deficiencia;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ViewScoped
@ManagedBean
public class DeficienciaDAO extends GenericDAO<Deficiencia, Long>{
    
    public DeficienciaDAO(){
        super(Deficiencia.class);
        this.nomeColunaParaOdemSimples = "nome";
    }
    
    public Deficiencia obterPorNome(String nome) {
        try{
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Deficiencia> criteria = cb.createQuery(Deficiencia.class);
            Root<Deficiencia> root = criteria.from(Deficiencia.class);
            criteria.where(cb.equal(root.get("nome"), nome));
            criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
            return getEntityManager().createQuery(criteria).setMaxResults(1).getSingleResult();
        }catch(Exception e){
            return null;
        }
    }
}

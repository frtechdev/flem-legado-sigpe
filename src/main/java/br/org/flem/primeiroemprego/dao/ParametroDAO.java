package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Parametro;
import br.org.flem.primeiroemprego.util.CommonDAO;
import java.util.List;
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
public class ParametroDAO extends CommonDAO{
    
    public List<Parametro> obterLista(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Parametro> query = cb.createQuery(Parametro.class);
        Root<Parametro> root = query.from(Parametro.class);
        return getEntityManager().createQuery(query).getResultList();
        
    }
    
    public String obterValor(String parametro){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> query = cb.createQuery(String.class);
        Root<Parametro> root = query.from(Parametro.class);
        query.select(root.<String>get("valor")).where(cb.equal(root.get("chave"), parametro));
        try{
            return getEntityManager().createQuery(query).getSingleResult();
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
    }
    
    public void salvar(List<Parametro> parametros) throws Exception {
        begin();
        try{
            for(Parametro p : parametros){
                getEntityManager().merge(p);
                getEntityManager().flush();
            }
            commit();
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            rollback();
            throw e;
        }
    }

    
}

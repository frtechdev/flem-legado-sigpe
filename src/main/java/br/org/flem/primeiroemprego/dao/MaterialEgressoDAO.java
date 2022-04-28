package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Material;
import br.org.flem.primeiroemprego.entity.MaterialEgresso;
import br.org.flem.primeiroemprego.entity.Tamanho;
import br.org.flem.primeiroemprego.entity.UID;
import br.org.flem.primeiroemprego.entity.UnidadeDeMedida;
import br.org.flem.primeiroemprego.util.CoreUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 * @author tscortes
 */

@ViewScoped
@ManagedBean
public class MaterialEgressoDAO extends GenericDAO<MaterialEgresso, Long> {

    public MaterialEgressoDAO() {
        super(MaterialEgresso.class);
    }

    public List<MaterialEgresso> obterMateriasEgressoAssociados(UID object){
        Predicate predicate = null;
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MaterialEgresso> criteria = cb.createQuery(MaterialEgresso.class);
        Root<MaterialEgresso> root = criteria.from(MaterialEgresso.class);
        
        if(object instanceof Tamanho){
            predicate = cb.equal(root.get("tamanho"), object); 
        }else if (object instanceof Material){
            predicate = cb.equal(root.get("material"), object); 
        }else if (object instanceof UnidadeDeMedida){
            predicate = cb.equal(root.get("unidade"), object); 
        }else if (object instanceof Egresso){
            predicate = cb.equal(root.get("egresso"), object); 
        }
        if( predicate == null )
            return null;
        criteria.where(predicate);

        return getEntityManager().createQuery(criteria).setMaxResults(1).getResultList();
        
    }
    
    public List<MaterialEgresso> obterMateriaisEgressoPorLote(Material material, Egresso egresso, String lote){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MaterialEgresso> criteria = cb.createQuery(MaterialEgresso.class);
        Root<MaterialEgresso> root = criteria.from(MaterialEgresso.class);
        
        if(!CoreUtil.someIsNull(material, egresso) && StringUtils.isNotEmpty(lote)){
            criteria.where(cb.equal(root.get("material"), material), cb.equal(root.get("egresso"), egresso), cb.equal(root.get("lote"), lote)); 
        }
        
        return getEntityManager().createQuery(criteria).setMaxResults(1).getResultList();
        
    }
}
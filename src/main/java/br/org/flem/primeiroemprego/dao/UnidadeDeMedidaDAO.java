/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Material;
import br.org.flem.primeiroemprego.entity.UnidadeDeMedida;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author AJLima
 */

@ManagedBean
@ViewScoped
public class UnidadeDeMedidaDAO extends GenericDAO<UnidadeDeMedida, Long>{
    
    public UnidadeDeMedidaDAO(){
        super(UnidadeDeMedida.class);
    }
    
    public List<UnidadeDeMedida> obterPorMaterial(Material material) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UnidadeDeMedida> criteria = cb.createQuery(UnidadeDeMedida.class);
        Root<UnidadeDeMedida> root = criteria.from(UnidadeDeMedida.class);
        criteria.where(cb.equal(root.get("material"), material));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    public UnidadeDeMedida obterPorSigla(String nome){
        if(StringUtils.isNotEmpty(nome)){
            
            try{
                String str = "from UnidadeDeMedida t where t.sigla = :sigla";
     
                Query query = getEntityManager().createQuery(str);

                query.setParameter("sigla", nome);

                return (UnidadeDeMedida) query.getSingleResult(); 
                
            }catch (NoResultException nre){
                return null;
            }
        }
        return null;
    }
}

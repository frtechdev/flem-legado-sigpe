/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Material;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author AJLima
 */

@ViewScoped
@ManagedBean
public class MaterialDAO extends GenericDAO<Material , Long>{
    
    public MaterialDAO(){
        super(Material.class);
    }
    
    public Material obterPorNome(String nome){
        if(StringUtils.isNotEmpty(nome)){
            
            try{
                String str = "from Material t where t.descricao = :descricao";
     
                Query query = getEntityManager().createQuery(str);

                query.setParameter("descricao", nome);

                return (Material) query.getSingleResult(); 
                
            }catch (NoResultException nre){
                return null;
            }
        }
        return null;
    }
    
}

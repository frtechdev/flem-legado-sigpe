/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Tamanho;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author AJLima
 */

@ManagedBean
@ViewScoped
public class TamanhoDAO extends GenericDAO<Tamanho, Long>{
    
    public TamanhoDAO(){
        super(Tamanho.class);
    }
    
    public Tamanho obterPorSigla(String nome){
        if(StringUtils.isNotEmpty(nome)){
            
            try{
                String str = "from Tamanho t where t.sigla = :sigla";
     
                Query query = getEntityManager().createQuery(str);

                query.setParameter("sigla", nome);

                return (Tamanho) query.getSingleResult(); 
                
            }catch (NoResultException nre){
                return null;
            }
        }
        return null;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Documento;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author tscortes
 * @param <T>
 */
@ManagedBean
@ViewScoped
public class DocumentoDAO<T extends Documento> extends GenericDAO<T, Long>{
    
    private final Class<T> objeto;
    
    public DocumentoDAO(Class<T> objeto){
        super(objeto);
        this.objeto = objeto;
    }
    
    public List<T> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(objeto);
        Root<Documento> root = query.from(Documento.class);
        
        query.select(cb.construct(objeto,
                root.get("id"),
                root.get("nome"),
                root.get("arquivo")
        ));
        return getEntityManager().createQuery(query).getResultList();
    }
}

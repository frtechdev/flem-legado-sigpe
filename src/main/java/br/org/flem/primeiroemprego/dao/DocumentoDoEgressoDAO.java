package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.DocumentoDoEgresso;
import br.org.flem.primeiroemprego.entity.Egresso;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class DocumentoDoEgressoDAO extends DocumentoDAO<DocumentoDoEgresso>{
    
    public DocumentoDoEgressoDAO(){
        super(DocumentoDoEgresso.class);
        this.ordemPadraoAscendente = false;
    }
    
    @Override
    public List<DocumentoDoEgresso> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DocumentoDoEgresso> query = cb.createQuery(DocumentoDoEgresso.class);
        Root<DocumentoDoEgresso> root = query.from(DocumentoDoEgresso.class);
        
        query.select(cb.construct(DocumentoDoEgresso.class,
                root.get("id"),
                root.get("nome")
        ));
        query.where(cb.isNull(root.get("filePath")));
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<DocumentoDoEgresso> findByEgresso(Egresso egresso) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DocumentoDoEgresso> query = cb.createQuery(DocumentoDoEgresso.class);
        Root<DocumentoDoEgresso> root = query.from(DocumentoDoEgresso.class);

        query.where(cb.equal(root.get("egresso"), egresso));
        query.orderBy(cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(query).getResultList();
    }
}

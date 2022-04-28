package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
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
public class ModeloDeOficioDAO extends DocumentoDAO<ModeloDeOficio>{

    public ModeloDeOficioDAO(){
        super(ModeloDeOficio.class);
        this.ordemPadraoAscendente = false;
    }

    public List<ModeloDeOficio> naoGerados() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ModeloDeOficio> query = cb.createQuery(ModeloDeOficio.class);
        Root<ModeloDeOficio> root = query.from(ModeloDeOficio.class);
        query.where(cb.equal(root.get("status"), ModeloDeOficio.Status.SALVO));
        return getEntityManager().createQuery(query).getResultList();
    }
    @Override
    public List<ModeloDeOficio> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<ModeloDeOficio> query = cb.createQuery(ModeloDeOficio.class);
        Root<ModeloDeOficio> root = query.from(ModeloDeOficio.class);
        
        query.select(cb.construct(ModeloDeOficio.class,
                root.get("id"),
                root.get("nome")
        ));
        return getEntityManager().createQuery(query).getResultList();
    }

}

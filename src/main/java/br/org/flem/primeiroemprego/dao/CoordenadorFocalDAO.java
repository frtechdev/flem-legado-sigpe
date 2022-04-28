package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.CoordenadorFocal;
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
public class CoordenadorFocalDAO extends GenericDAO<CoordenadorFocal, Long> {
    public CoordenadorFocalDAO() throws Exception{
        super(CoordenadorFocal.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public List<CoordenadorFocal> obterPorDemandante(Demandante demandante) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CoordenadorFocal> criteria = cb.createQuery(CoordenadorFocal.class);
        Root<CoordenadorFocal> root = criteria.from(CoordenadorFocal.class);
        criteria.where(cb.equal(root.get("demandante"), demandante));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Instituicao;
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
public class InstituicaoDAO extends GenericDAO<Instituicao, Long> {
    public InstituicaoDAO() throws Exception{
        super(Instituicao.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public List<Instituicao> obterPorDemandante(Demandante demandante) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Instituicao> criteria = cb.createQuery(Instituicao.class);
        Root<Instituicao> root = criteria.from(Instituicao.class);
        criteria.where(cb.equal(root.get("demandante"), demandante));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Instituicao;
import br.org.flem.primeiroemprego.entity.UnidadeDeLotacao;
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
public class UnidadeDeLotacaoDAO extends GenericDAO<UnidadeDeLotacao, Long> {
    public UnidadeDeLotacaoDAO() throws Exception{
        super(UnidadeDeLotacao.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public List<UnidadeDeLotacao> obterPorInstituicao(Instituicao instituicao) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<UnidadeDeLotacao> criteria = cb.createQuery(UnidadeDeLotacao.class);
        Root<UnidadeDeLotacao> root = criteria.from(UnidadeDeLotacao.class);
        criteria.where(cb.equal(root.get("instituicao"), instituicao));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

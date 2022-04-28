package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Telefone;
import br.org.flem.primeiroemprego.entity.TelefoneID;
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
public class TelefoneDAO extends GenericDAO<Telefone, TelefoneID> {

    public TelefoneDAO() throws Exception {
        super(Telefone.class);
        this.nomeColunaParaOdemSimples = "id.seq";
    }

    public List<Telefone> obterPorEgresso(Egresso egresso) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Telefone> criteria = cb.createQuery(Telefone.class);
        Root<Telefone> root = criteria.from(Telefone.class);
        criteria.where(cb.equal(root.get("id").get("egresso"), egresso));
        criteria.orderBy(cb.asc(root.get("id").get("seq")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public Short obterMaxSeq(Egresso egresso) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Telefone> criteria = cb.createQuery(Telefone.class);
        Root<Telefone> root = criteria.from(Telefone.class);
        criteria.where(cb.equal(root.get("id").get("egresso"), egresso));
        criteria.orderBy(cb.desc(root.get("id").get("seq")));
        List<Telefone> telefones = getEntityManager().createQuery(criteria).getResultList();
        if( telefones.isEmpty() )
            return Short.valueOf(""+0);
        return telefones.get(telefones.size() - 1).getId().getSeq();
    }

}

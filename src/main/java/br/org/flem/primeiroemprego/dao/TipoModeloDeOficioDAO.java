package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.TipoModeloDeOficio;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class TipoModeloDeOficioDAO extends GenericDAO<TipoModeloDeOficio, Long> {

    public TipoModeloDeOficioDAO() {
        super(TipoModeloDeOficio.class);
        this.nomeColunaParaOdemSimples = "descricao";
    }

    public TipoModeloDeOficio obterPorCodigo(String codigo) {
        try {
            String str = "from TipoModeloDeOficio t where t.codigo = :codigo";
            Query query = getEntityManager().createQuery(str);
            query.setParameter("codigo", codigo);
            return (TipoModeloDeOficio) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
    public List<TipoModeloDeOficio> obterListaAtivos() {
        try {
            String str = "from TipoModeloDeOficio t where t.ativo = :ativo";
            Query query = getEntityManager().createQuery(str);
            query.setParameter("ativo", Boolean.TRUE);
            return (List<TipoModeloDeOficio>) query.getResultList();
        } catch (NoResultException nre) {
            return null;
        }
    }
    public TipoModeloDeOficio obterPorDescricao(String descricao) {
        try {
            String str = "from TipoModeloDeOficio t where t.descricao = :descricao";
            Query query = getEntityManager().createQuery(str);
            query.setParameter("descricao", descricao);
            return (TipoModeloDeOficio) query.getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

}

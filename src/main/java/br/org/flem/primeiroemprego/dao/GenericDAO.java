package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.UID;
import br.org.flem.primeiroemprego.util.CommonDAO;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author AJLima
 * @param <T>
 * @param <I>
 */
public abstract class GenericDAO<T extends UID, I> extends CommonDAO {

    private Class<T> objeto;
    protected boolean ordemPadraoAscendente = true;
    protected String nomeColunaParaOdemSimples = "id";

    protected GenericDAO() {
    }

    protected GenericDAO(Class<T> objeto) {
        this();
        this.objeto = objeto;
    }

    public T inserir(T obj) throws Exception {
        begin();
        try {
            getEntityManager().persist(obj);
            getEntityManager().flush();
            commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            rollback();
            throw e;
        }
        return obj;
    }

    public T inserirSemTransacao(T obj) {
        getEntityManager().persist(obj);
        getEntityManager().flush();
        return obj;
    }

    public T alterar(T obj) throws Exception {
        begin();
        try {
            getEntityManager().merge(obj);
            getEntityManager().flush();
            commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            rollback();
            throw e;
        }
        return obj;
    }

    public void excluir(T obj) throws Exception {
        begin();
        try {
            getEntityManager().remove(getEntityManager().getReference(objeto, obj.getId()));
            getEntityManager().flush();
            commit();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            rollback();
            throw e;
        }
    }

    public T obterPorPK(I id) {
        return getEntityManager().find(objeto, id);
    }

    public List<T> obterLista() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(objeto);
        Root<T> root = criteria.from(objeto);
        criteria.orderBy(ordemPadraoAscendente ? cb.asc(root.get(nomeColunaParaOdemSimples)) : cb.desc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<T> obterListaEscritorio() { 
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(objeto);
        Root<T> root = criteria.from(objeto);
        criteria.orderBy(cb.asc(root.get("nome")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

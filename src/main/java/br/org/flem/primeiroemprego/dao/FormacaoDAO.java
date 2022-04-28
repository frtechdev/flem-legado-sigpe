package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.EixoDeFormacao;
import br.org.flem.primeiroemprego.entity.Formacao;
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
public class FormacaoDAO extends  GenericDAO<Formacao, Long> {

    public FormacaoDAO() throws Exception{
        super(Formacao.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public List<Formacao> obterPorEixo(EixoDeFormacao eixo) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Formacao> criteria = cb.createQuery(Formacao.class);
        Root<Formacao> root = criteria.from(Formacao.class);
        criteria.where(cb.equal(root.get("eixo"), eixo));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public Formacao obterPorNome(String nome) {
        try{
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Formacao> criteria = cb.createQuery(Formacao.class);
            Root<Formacao> root = criteria.from(Formacao.class);
            criteria.where(cb.equal(root.get("nome"), nome));
            criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
            return getEntityManager().createQuery(criteria).setMaxResults(1).getSingleResult();
        }catch(Exception e){
            return null;
        }
    }

}
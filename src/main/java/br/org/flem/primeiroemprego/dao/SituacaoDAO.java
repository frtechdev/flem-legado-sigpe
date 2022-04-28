package br.org.flem.primeiroemprego.dao;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Situacao;
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
public class SituacaoDAO extends  GenericDAO<Situacao, Long> {

    public SituacaoDAO() throws Exception{
        super(Situacao.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public List<Situacao> obterParaTelaDeCadastro() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Situacao> criteria = cb.createQuery(Situacao.class);
        Root<Situacao> root = criteria.from(Situacao.class);
        criteria.where(cb.not(root.get("id").in(PropertiesUtil.getProperty("categoriaContratado"),
                                         PropertiesUtil.getProperty("categoriaDesligado"),PropertiesUtil.getProperty("categoriaTransferido"))));//Ticjet #3492 nao deve exibir essas categorias
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    public List<Situacao> obterPorCategoria(CategoriaDaSituacao categoria) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Situacao> criteria = cb.createQuery(Situacao.class);
        Root<Situacao> root = criteria.from(Situacao.class);
        criteria.where(cb.equal(root.get("categoria"), categoria));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Situacao> obterPorCategoriaParaTelaDeCadastro(CategoriaDaSituacao categoria) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Situacao> criteria = cb.createQuery(Situacao.class);
        Root<Situacao> root = criteria.from(Situacao.class);
        criteria.where(cb.and(
                            cb.equal(root.get("categoria"), categoria),
                            cb.not(root.get("id").in(PropertiesUtil.getProperty("categoriaContratado"),PropertiesUtil.getProperty("categoriaDesligado"),PropertiesUtil.getProperty("categoriaTransferido")))));//Ticjet #3492 nao deve exibir essas categorias
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public Situacao obterPorNome(String nome) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Situacao> criteria = cb.createQuery(Situacao.class);
        Root<Situacao> root = criteria.from(Situacao.class);
        criteria.where(cb.equal(root.get("nome"), nome));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).setMaxResults(1).getSingleResult();
    }

    public Situacao obterPorNomeECategoria(String nomeECategoria) throws Exception {
        if(nomeECategoria == null || nomeECategoria.trim().isEmpty()){
            return null;
        }
        String[] arNomeEcategoria = nomeECategoria.split("-");
        if(arNomeEcategoria.length != 2){
            throw new Exception("Formato da situação incorreto. Deve ser SITUAÇÃO - CATEGORIA");
        }
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Situacao> criteria = cb.createQuery(Situacao.class);
        Root<Situacao> root = criteria.from(Situacao.class);
        criteria.where(cb.and(cb.equal(root.get("nome"), arNomeEcategoria[0].trim()),
                              cb.equal(root.get("categoria").get("nome"), arNomeEcategoria[1].trim())));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        try{
            return getEntityManager().createQuery(criteria).setMaxResults(1).getSingleResult();
        }catch(Exception e){
            return null;
        }
    }

}
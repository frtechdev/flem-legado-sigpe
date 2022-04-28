package br.org.flem.primeiroemprego.dao;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Vaga;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CategoriaDaSituacaoDAO extends GenericDAO<CategoriaDaSituacao, Long> {

    public CategoriaDaSituacaoDAO() throws Exception {
        super(CategoriaDaSituacao.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public List<CategoriaDaSituacao> obterParaTelaDeCadastro() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CategoriaDaSituacao> criteria = cb.createQuery(CategoriaDaSituacao.class);
        Root<CategoriaDaSituacao> root = criteria.from(CategoriaDaSituacao.class);
        criteria.where(cb.not(root.get("id").in(PropertiesUtil.getProperty("categoriaContratado"),
                PropertiesUtil.getProperty("categoriaDesligado"), PropertiesUtil.getProperty("categoriaTransferido"))));//Ticjet #3492 nao deve exibir essas categorias
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    /**
     * Obtem categorias<br>
     *
     * @author ermoura<br>
     * @return List
     */
    public List<CategoriaDaSituacao> obterCategoriaDaSituacao() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CategoriaDaSituacao> criteria = cb.createQuery(CategoriaDaSituacao.class);
        Root<CategoriaDaSituacao> root = criteria.from(CategoriaDaSituacao.class);
        criteria.orderBy(cb.asc(root.get("nome")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    /**
     * Carregar Situacoes das Vagas por demandantes
     *
     * @param demandantes
     * @return
     */
    public List<CategoriaDaSituacao> obterSituacaoPorVagaDemandante(List<Demandante> demandantes) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CategoriaDaSituacao> criteria = cb.createQuery(CategoriaDaSituacao.class);

        Root<Vaga> root = criteria.from(Vaga.class);
        Join joinDemandante = root.join("demandante");
        Join joinSituacao = root.join("situacao");
        Join joinCategoria = joinSituacao.join("categoria");
        criteria.distinct(true);
        criteria.select(cb.construct(CategoriaDaSituacao.class,
                joinCategoria.get("id"),
                joinCategoria.get("nome")
        ));

        if (demandantes != null && !demandantes.isEmpty()) {
            criteria.where(root.get("demandante").in(demandantes));
        }

        criteria.orderBy(cb.asc(joinCategoria.get("nome")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

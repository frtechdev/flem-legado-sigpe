package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Notificacao;
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
@ViewScoped
@ManagedBean
public class NotificacaoDAO extends GenericDAO<Notificacao, Long> {
    
    public NotificacaoDAO(){
        super(Notificacao.class);
        this.nomeColunaParaOdemSimples = "dataCriacao";
    }
    
    public List<Notificacao> obterNaoConcluidasPorUsuario(Integer codigoDominio){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Notificacao> criteria = cb.createQuery(Notificacao.class);
        Root<Notificacao> root = criteria.from(Notificacao.class);
        criteria.where(cb.and(cb.equal(root.get("usuarioNotificado"), codigoDominio),
                                cb.isNull(root.get("dataConcluido"))));
        criteria.orderBy(cb.asc(root.get("dataLimite")),cb.desc(root.get("urgente")),cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Notificacao> obterCriadasPorUsuario(String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Notificacao> criteria = cb.createQuery(Notificacao.class);
        Root<Notificacao> root = criteria.from(Notificacao.class);
        criteria.where(cb.equal(root.get("usuarioCriacao"), login));
        criteria.orderBy(cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

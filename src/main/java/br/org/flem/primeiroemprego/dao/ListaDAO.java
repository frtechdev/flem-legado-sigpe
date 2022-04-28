package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Lista;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
/**
 *
 * @author MPPassos
 */
@ManagedBean
@ViewScoped
public class ListaDAO extends GenericDAO<Lista, Long> {

    public ListaDAO() throws Exception{
        super(Lista.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public List<Lista> obterAssociadaAoUsuario(Integer codigoDominio) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Lista> criteria = cb.createQuery(Lista.class);
        Root<Lista> root = criteria.from(Lista.class);

        //Sim, tinha que rolar esse armengue pois não dá para usar distinct e order by com campos formula (o campo concluida)
        Subquery<Long> subquery = criteria.subquery(Long.class);
        Root<Lista> subfrom = subquery.from(Lista.class);
        subquery.select(subfrom.<Long>get("id"));
        subquery.where(subfrom.join("usuariosResponsaveis").in(codigoDominio));

        criteria.where(root.get("id").in(subquery));
        criteria.orderBy(cb.asc(root.get("concluida")),cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    public List<Lista> obterAssociadaOuCriadaPor(Integer codigoDominio, String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Lista> criteria = cb.createQuery(Lista.class);
        Root<Lista> root = criteria.from(Lista.class);
        
        //Sim, tinha que rolar esse armengue pois não dá para usar distinct e order by com campos formula (o campo concluida)
        Subquery<Long> subquery = criteria.subquery(Long.class);
        Root<Lista> subfrom = subquery.from(Lista.class);
        subquery.select(subfrom.<Long>get("id"));
        subquery.where(cb.or(subfrom.join("usuariosResponsaveis").in(codigoDominio),
                             cb.equal(subfrom.get("usuarioCriacao"), login)));

        criteria.where(root.get("id").in(subquery));
        criteria.orderBy(cb.asc(root.get("concluida")),cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<Lista> obterCriadasPeloUsuario(String login) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Lista> criteria = cb.createQuery(Lista.class);
        Root<Lista> root = criteria.from(Lista.class);

        //Sim, tinha que rolar esse armengue pois não dá para usar distinct e order by com campos formula (o campo concluida)
        Subquery<Long> subquery = criteria.subquery(Long.class);
        Root<Lista> subfrom = subquery.from(Lista.class);
        subquery.select(subfrom.<Long>get("id"));
        subquery.where(cb.equal(subfrom.get("usuarioCriacao"), login));

        criteria.where(root.get("id").in(subquery));
        criteria.orderBy(cb.asc(root.get("concluida")),cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    public List<Lista> obterOrdenadoDataCriacao() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Lista> criteria = cb.createQuery(Lista.class);
        Root<Lista> root = criteria.from(Lista.class);
        criteria.orderBy(cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
}

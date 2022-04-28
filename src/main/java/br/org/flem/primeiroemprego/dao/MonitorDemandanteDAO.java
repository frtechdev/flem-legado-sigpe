package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.*;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Descreva Sua Classe Classe MonitorDemandante
 *
 * @author <code>tscortes@flem.org.br</code> 03/05/2019
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class MonitorDemandanteDAO extends GenericDAO<MonitorDemandante, Long> {

    public MonitorDemandanteDAO() throws Exception {
        super(MonitorDemandante.class);
    }

    public List<MonitorDemandante> obterPorColaborador(Colaborador colaborador) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MonitorDemandante> criteria = cb.createQuery(MonitorDemandante.class);
        Root<MonitorDemandante> root = criteria.from(MonitorDemandante.class);
        criteria.where(cb.equal(root.get("monitor"), colaborador));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<MonitorDemandante> obterPorMunicipioDemandante(Municipio municipio, Demandante demandante) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MonitorDemandante> criteria = cb.createQuery(MonitorDemandante.class);
        Root<MonitorDemandante> root = criteria.from(MonitorDemandante.class);
        criteria.where(cb.equal(root.get("municipio"), municipio), cb.equal(root.get("demandante"), demandante));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    public List<MonitorDemandante> obterPorMunicipioDemandante(Municipio municipio, Demandante demandante, Colaborador colaborador) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MonitorDemandante> criteria = cb.createQuery(MonitorDemandante.class);
        Root<MonitorDemandante> root = criteria.from(MonitorDemandante.class);
        criteria.where(
                cb.equal(
                        root.get("municipio"), municipio),
                cb.equal(
                        root.get("demandante"), demandante),
                cb.equal(
                        root.get("monitor"), colaborador)
        );
        return getEntityManager().createQuery(criteria).getResultList();
    }
    /**
     * Obter Associações por municipios e colaborador informado
     * @param municipios
     * @param colaborador
     * @return 
     */
    public List<MonitorDemandante> obterPorMunicipiosColaborador(List<Municipio> municipios, Colaborador colaborador) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MonitorDemandante> criteria = cb.createQuery(MonitorDemandante.class);
        Root<MonitorDemandante> root = criteria.from(MonitorDemandante.class);
        criteria.where(root.get("municipio").in(municipios),
                cb.equal(root.get("monitor"), colaborador)
        );
        return getEntityManager().createQuery(criteria).getResultList();
    }
    /**
     * Obter Associações que não sejam para o colaborador informado
     * @param municipios
     * @param colaborador
     * @return 
     */
    public List<MonitorDemandante> obterPorMunicipiosNaoColaborador(List<Municipio> municipios, Colaborador colaborador) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MonitorDemandante> criteria = cb.createQuery(MonitorDemandante.class);
        Root<MonitorDemandante> root = criteria.from(MonitorDemandante.class);
        criteria.where(root.get("municipio").in(municipios),
                cb.notEqual(root.get("monitor"), colaborador)
        );
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

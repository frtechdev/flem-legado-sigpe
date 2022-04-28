package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.dto.DemandanteMunicipioDTO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Municipio;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class DemandanteDAO extends GenericDAO<Demandante, Long> {

    public DemandanteDAO() throws Exception {
        super(Demandante.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public Demandante obterPorNome(String nome) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Demandante> criteria = cb.createQuery(Demandante.class);
            Root<Demandante> root = criteria.from(Demandante.class);
            criteria.where(cb.equal(root.get("nome"), nome));
            criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
            return getEntityManager().createQuery(criteria).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<String> siglasDemandantes() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<String> criteria = cb.createQuery(String.class);
        Root<Demandante> root = criteria.from(Demandante.class);
        criteria.select(root.<String>get("sigla"));
        criteria.orderBy(cb.asc(root.get(nomeColunaParaOdemSimples)));
        return getEntityManager().createQuery(criteria).getResultList();
    }

    /**
     * *
     * Metodo que recupera os demandantes cadastrados<br>
     *
     * @author ermoura<br>
     * @return List
     */
    public List<Demandante> obterDemandante() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Demandante> query = cb.createQuery(Demandante.class);
        Root<Demandante> root = query.from(Demandante.class);
        return getEntityManager().createQuery(query).getResultList();
    }

    /**
     * Obtem o demandante pelo municipio<br>
     *
     * @author ermoura<br>
     * @param municipios<br>
     * @return List
     */
    public List<Demandante> obterDemandantePorMunicio(List<Municipio> municipios) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Demandante> query = cb.createQuery(Demandante.class);
        Root<Demandante> root = query.from(Demandante.class);
        Join vaga = root.join("vagas", JoinType.INNER);
        query.select(cb.construct(Demandante.class,
                root.get("id"),
                root.get("nome")
        )).distinct(true);
        query.where(vaga.get("municipio").in(municipios));
        query.orderBy(cb.asc(root.get("nome")));
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<Demandante> obterPorMunicipio(Municipio municipio) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Demandante> query = cb.createQuery(Demandante.class);
        Root<Demandante> root = query.from(Demandante.class);
        Join vaga = root.join("vagas", JoinType.INNER);
        query.select(cb.construct(Demandante.class,
                root.get("id"),
                root.get("nome")
        )).distinct(true);
        query.where(cb.equal(vaga.get("municipio"), municipio));
        query.orderBy(cb.asc(root.get("nome")));
        return getEntityManager().createQuery(query).getResultList();
    }
    
    /**
     * Obtem o demandantes por municipios
     *
     * @author tscortes
     * @param municipios
     * @return List
     */
    public List<DemandanteMunicipioDTO> obterDTOPorMunicipio(List<Municipio> municipios) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<DemandanteMunicipioDTO> query = cb.createQuery(DemandanteMunicipioDTO.class);
        Root<Demandante> root = query.from(Demandante.class);
        Join vaga = root.join("vagas", JoinType.INNER);
        query.select(cb.construct(DemandanteMunicipioDTO.class,
                root.get("id"),
                vaga.get("municipio").get("id"),
                root.get("nome"),
                vaga.get("municipio").get("nome")
        )).distinct(true);
        query.where(vaga.get("municipio").in(municipios));
        query.orderBy(cb.asc(root.get("nome")));
        return getEntityManager().createQuery(query).getResultList();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.dto.MunicipioDTO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.Vaga;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

/**
 *
 * @author AJLima
 */

@ManagedBean
@ViewScoped
public class VagaDAO extends GenericDAO<Vaga, Long>{
    
    @ManagedProperty(value = "#{situacaoDAO}")
    private SituacaoDAO situacaoDAO;
    
    public VagaDAO(){
        super(Vaga.class);
    }
    
    public List<Vaga> obterPorMunicipios(List<Municipio> municipios){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Vaga> query = cb.createQuery(Vaga.class);
        Root<Vaga> root = query.from(Vaga.class);
        query.select(cb.construct(Vaga.class,
                root.get("demandante"),
                root.get("municipio")
        )).distinct(true);
        query.where(root.get("municipio").in(municipios));
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<Municipio> obterMunicipios(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Municipio> query = cb.createQuery(Municipio.class);
        Root<Vaga> root = query.from(Vaga.class);
        query.select(cb.construct(Municipio.class,
                root.get("municipio").get("id"),
                root.get("municipio").get("nome")
                )).distinct(true);
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<Demandante> obterDemandantes(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Demandante> query = cb.createQuery(Demandante.class);
        Root<Vaga> root = query.from(Vaga.class);
        query.select(cb.construct(Demandante.class,
                root.get("demandante").get("id"),
                root.get("demandante").get("nome")
                )).distinct(true);
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<MunicipioDTO> obterMunicipioDTOPorEscritorioRegional(EscritorioRegional escritorio) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MunicipioDTO> query = cb.createQuery(MunicipioDTO.class);
        Root<Vaga> root = query.from(Vaga.class);
        Join joinMunicipio = root.join("municipio", JoinType.INNER);
        query.select(cb.construct(MunicipioDTO.class,
                joinMunicipio.get("id"),
                joinMunicipio.get("nome"),
                joinMunicipio.get("uf"),
                cb.count(root.get("egresso"))
        ));
        query.groupBy(joinMunicipio.get("id"), joinMunicipio.get("nome"), joinMunicipio.get("uf"));
        query.where(cb.equal(joinMunicipio.get("escritorioRegional"), escritorio), cb.equal(root.get("situacao"), situacaoDAO.obterPorPK(17l)));
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<MunicipioDTO> obterMunicipioDTOPorEscritoriosRegional(List<EscritorioRegional> escritorios) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<MunicipioDTO> query = cb.createQuery(MunicipioDTO.class);
        Root<Vaga> root = query.from(Vaga.class);
        Join joinMunicipio = root.join("municipio", JoinType.INNER);
        query.select(cb.construct(MunicipioDTO.class,
                joinMunicipio.get("id"),
                joinMunicipio.get("nome"),
                joinMunicipio.get("uf"),
                cb.count(root.get("egresso"))
        ));
        query.groupBy(joinMunicipio.get("id"), joinMunicipio.get("nome"), joinMunicipio.get("uf"));
        query.where(joinMunicipio.get("escritorioRegional").in(escritorios), cb.equal(root.get("situacao"), situacaoDAO.obterPorPK(17l)));
        return getEntityManager().createQuery(query).getResultList();
    }

    public SituacaoDAO getSituacaoDAO() {
        return situacaoDAO;
    }

    public void setSituacaoDAO(SituacaoDAO situacaoDAO) {
        this.situacaoDAO = situacaoDAO;
    }
    
    
}

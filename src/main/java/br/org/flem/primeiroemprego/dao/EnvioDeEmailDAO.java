/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.dto.EnvioDeEmailDTO;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EnvioDeEmail;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author AJLima
 */
@ManagedBean
@ViewScoped
public class EnvioDeEmailDAO extends GenericDAO<EnvioDeEmail, Long> {

    public EnvioDeEmailDAO() {
        super(EnvioDeEmail.class);
    }

    public List<EnvioDeEmail> obterEnviosPorCampanha(Campanha campanha) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EnvioDeEmail> query = cb.createQuery(EnvioDeEmail.class);
        Root<Campanha> root = query.from(Campanha.class);
        query.where(cb.equal(root.get("campanha"), campanha));

        return getEntityManager().createQuery(query).getResultList();

    }

    public List<EnvioDeEmailDTO> obterEnviosDTOPorCampanha(Campanha campanha) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EnvioDeEmailDTO> criteria = cb.createQuery(EnvioDeEmailDTO.class);
        Root<EnvioDeEmail> root = criteria.from(EnvioDeEmail.class);
        
        //Long id, Long idCampanha, StatusEnvioDaCampanha status, String descricaoEnvio, String email, String emailSecundario, Long idEgresso, Long idAcao
        criteria.select(cb.construct(EnvioDeEmailDTO.class,
                root.get("id"),
                root.get("campanha").get("id"),
                root.get("status"),
                root.get("descricaoEnvio"),
                root.get("email"),
                root.get("emailSecundario"),
                root.get("egresso").get("id"),
                root.get("acao").get("id")
        ));

        criteria.where(cb.equal(root.get("campanha"), campanha));

        return getEntityManager().createQuery(criteria).getResultList();

    }

    public EnvioDeEmail obterEnviosPorCampanhaEgresso(Campanha campanha, Egresso egresso) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<EnvioDeEmail> query = cb.createQuery(EnvioDeEmail.class);
        Root<EnvioDeEmail> root = query.from(EnvioDeEmail.class);

        query.where(
                cb.equal(root.get("campanha"), campanha),
                cb.equal(root.get("egresso"), egresso)
        );
        List<EnvioDeEmail> envios = getEntityManager().createQuery(query).getResultList();
        if (envios != null && !envios.isEmpty()) {
            return envios.get(0);
        }
        return null;

    }
}

package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Territorio;
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
public class TerritorioDAO extends GenericDAO<Territorio, Long>{

    public TerritorioDAO(){
        super(Territorio.class);
        this.nomeColunaParaOdemSimples = "nome";
    }
    /**
      * obtem alista de teritorios<br>
      * @author ermouta<br>
      * @param escritorioRegional<br> 
      * return List
      */
     public List<Territorio> obterTerririo(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Territorio> query = cb.createQuery(Territorio.class);
        Root<Territorio> root = query.from(Territorio.class);
       return getEntityManager().createQuery(query).getResultList();
    }
       /**
      * obtem alista de teritorios por escritorio regional<br>
      * @author ermouta<br>
      * @param escritorioRegional<br> 
      * return List
      */ 
      public List<Territorio> obterTerririoPorEscritorioRegional(List<EscritorioRegional> escritorioRegional){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Territorio> query = cb.createQuery(Territorio.class);
        Root<Territorio> root = query.from(Territorio.class);
        Join joinMunicipio = root.join("municipios", JoinType.INNER);
        query.select(cb.construct(Territorio.class,
                root.get("id"),
                root.get("nome")
        )).distinct(true);
        query.where(joinMunicipio.get("escritorioRegional").in( escritorioRegional));
       return getEntityManager().createQuery(query).getResultList();
    }
      
    /**
     * Obtem lista de territórios que não tenham municipios com escritório regional associado
     * @return lista de territorios
     */  
    public List<Territorio> obterTerritorioPorEscritorioRegionalNaoImpl(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Territorio> query = cb.createQuery(Territorio.class);
        Root<Territorio> root = query.from(Territorio.class);
        Join joinMunicipio = root.join("municipios", JoinType.INNER);
        query.select(cb.construct(Territorio.class,
                root.get("id"),
                root.get("nome")
        )).distinct(true);
        query.where(joinMunicipio.get("escritorioRegional").isNull());
        return getEntityManager().createQuery(query).getResultList();
    }

}

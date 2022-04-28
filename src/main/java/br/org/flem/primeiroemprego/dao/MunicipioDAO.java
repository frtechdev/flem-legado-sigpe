package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.Territorio;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
public class MunicipioDAO extends GenericDAO<Municipio, Long> {

    @ManagedProperty(value = "#{distanciaEntreMunicipiosDAO}")
    private DistanciaEntreMunicipiosDAO distanciaEntreMunicipiosDAO;

    public MunicipioDAO() {
        super(Municipio.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public Municipio obterPorNomeUF(String cidade, String uf) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Municipio> criteria = cb.createQuery(Municipio.class);
            Root<Municipio> root = criteria.from(Municipio.class);
            criteria.where(cb.and(cb.equal(root.get("nome"), cidade), cb.equal(root.get("uf"), uf)));

            return getEntityManager().createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Municipio inserir(Municipio municipio) throws Exception {
        super.inserir(municipio);
        distanciaEntreMunicipiosDAO.calcularDistancias(municipio, obterLista());
        return municipio;
    }

    public List<Municipio> obterPorTerritorio(List<Territorio> territorios) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Municipio> query = cb.createQuery(Municipio.class);
        Root<Municipio> root = query.from(Municipio.class);
        query.where(root.get("territorio").in(territorios));
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<Municipio> obterPorEscritorioRegional(EscritorioRegional escritorio) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Municipio> query = cb.createQuery(Municipio.class);
        Root<Municipio> root = query.from(Municipio.class);
        query.where(cb.equal(root.get("escritorioRegional"), escritorio));
        return getEntityManager().createQuery(query).getResultList();
    }

    public List<Municipio> obterPorEscritorioRegional(List<EscritorioRegional> escritorios) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Municipio> query = cb.createQuery(Municipio.class);
        Root<Municipio> root = query.from(Municipio.class);
        query.where(root.get("escritorioRegional").in(escritorios));
        return getEntityManager().createQuery(query).getResultList();
    }

    /**
     * Obtem municipios que não tenham Escritório Regional associado
     *
     * @return lista de Municipios
     */
    public List<Municipio> obterPorEscritorioNaoImpl() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Municipio> query = cb.createQuery(Municipio.class);
        Root<Municipio> root = query.from(Municipio.class);
        query.where(root.get("escritorioRegional").isNull());
        return getEntityManager().createQuery(query).getResultList();
    }

    public Municipio obterPorNome(String cidade) {
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Municipio> criteria = cb.createQuery(Municipio.class);
            Root<Municipio> root = criteria.from(Municipio.class);
            if (cidade.contains("-")) {
                criteria.where(cb.and(cb.equal(root.get("nome"), cidade.substring(0, cidade.indexOf("-")).trim()),
                        cb.equal(root.get("uf"), cidade.substring(cidade.indexOf("-") + 1).trim())));
            } else {
                criteria.where(cb.equal(root.get("nome"), cidade));
            }

            return getEntityManager().createQuery(criteria).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Método que obtem a lista de municipios
     *
     * @auth ermoura
     * @return
     */
    public List<Municipio> obterMunicipios() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Municipio> query = cb.createQuery(Municipio.class);
        Root<Municipio> root = query.from(Municipio.class);
        return getEntityManager().createQuery(query).getResultList();
    }

    public DistanciaEntreMunicipiosDAO getDistanciaEntreMunicipiosDAO() {
        return distanciaEntreMunicipiosDAO;
    }

    public void setDistanciaEntreMunicipiosDAO(DistanciaEntreMunicipiosDAO distanciaEntreMunicipiosDAO) {
        this.distanciaEntreMunicipiosDAO = distanciaEntreMunicipiosDAO;
    }
}

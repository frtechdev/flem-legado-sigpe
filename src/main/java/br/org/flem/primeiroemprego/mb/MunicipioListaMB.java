package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.negocio.ColaboradorRN;
import br.org.flem.primeiroemprego.negocio.EscritorioRegionalRN;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class MunicipioListaMB extends BaseMB{
    
    @ManagedProperty(value="#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    @ManagedProperty(value="#{escritorioRegionalRN}")
    private EscritorioRegionalRN escritorioRegionalRN;
    @ManagedProperty(value="#{colaboradorRN}")
    private ColaboradorRN colaboradorRN;
    
    private List<Municipio> municipios;
    private EscritorioRegional escritorioRegional;
    
    @PostConstruct
    public void init() {
        municipios = municipioDAO.obterLista();
    }
    
    public void abrirEscritorio(Long idEscritorio){
        this.escritorioRegional = escritorioRegionalRN.obterPorId(idEscritorio);
        this.escritorioRegional.setColaboradores(colaboradorRN.obterPorEscritorioRegional(escritorioRegional));
        abrirModal("escritorioRegionalDialog");
    }
    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public EscritorioRegional getEscritorioRegional() {
        return escritorioRegional;
    }

    public void setEscritorioRegional(EscritorioRegional escritorioRegional) {
        this.escritorioRegional = escritorioRegional;
    }

    public EscritorioRegionalRN getEscritorioRegionalRN() {
        return escritorioRegionalRN;
    }

    public void setEscritorioRegionalRN(EscritorioRegionalRN escritorioRegionalRN) {
        this.escritorioRegionalRN = escritorioRegionalRN;
    }

    public ColaboradorRN getColaboradorRN() {
        return colaboradorRN;
    }

    public void setColaboradorRN(ColaboradorRN colaboradorRN) {
        this.colaboradorRN = colaboradorRN;
    }
    
    
}

package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CampanhaDAO;
import br.org.flem.primeiroemprego.dao.EnvioDeEmailDAO;
import br.org.flem.primeiroemprego.dto.CampanhaDTO;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.EnvioDeEmail;
import java.io.Serializable;
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
public class CampanhaListaMB  implements Serializable{
    
    @ManagedProperty(value="#{campanhaDAO}")
    private CampanhaDAO campanhaDAO;
    
    private List<CampanhaDTO> campanhas;
    
    @ManagedProperty(value = "#{envioDeEmailDAO}")
    private EnvioDeEmailDAO envioDeEmailDAO;

    @PostConstruct
    public void init() {
        campanhas = campanhaDAO.obterListaDTO();
    }
    
    public List<EnvioDeEmail> obterEnviosdeEmail(Campanha campanha){
        
        return envioDeEmailDAO.obterEnviosPorCampanha(campanha);
    }

    public CampanhaDAO getCampanhaDAO() {
        return campanhaDAO;
    }

    public void setCampanhaDAO(CampanhaDAO campanhaDAO) {
        this.campanhaDAO = campanhaDAO;
    }

    public List<CampanhaDTO> getCampanhas() {
        return campanhas;
    }

    public void setCampanhas(List<CampanhaDTO> campanhas) {
        this.campanhas = campanhas;
    }

    public EnvioDeEmailDAO getEnvioDeEmailDAO() {
        return envioDeEmailDAO;
    }

    public void setEnvioDeEmailDAO(EnvioDeEmailDAO envioDeEmailDAO) {
        this.envioDeEmailDAO = envioDeEmailDAO;
    }

    
}
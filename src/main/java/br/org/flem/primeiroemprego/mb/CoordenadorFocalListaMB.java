package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CoordenadorFocalDAO;
import br.org.flem.primeiroemprego.entity.CoordenadorFocal;
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
public class CoordenadorFocalListaMB implements Serializable{
    
    @ManagedProperty(value="#{coordenadorFocalDAO}")
    private CoordenadorFocalDAO coordenadorFocalDAO;
    
    private List<CoordenadorFocal> coodenadoresFocais;

    @PostConstruct
    public void init() {
        coodenadoresFocais = coordenadorFocalDAO.obterLista();
    }

    public CoordenadorFocalDAO getCoordenadorFocalDAO() {
        return coordenadorFocalDAO;
    }

    public void setCoordenadorFocalDAO(CoordenadorFocalDAO coordenadorFocalDAO) {
        this.coordenadorFocalDAO = coordenadorFocalDAO;
    }

    public List<CoordenadorFocal> getCoodenadoresFocais() {
        return coodenadoresFocais;
    }

    public void setCoodenadoresFocais(List<CoordenadorFocal> coodenadoresFocais) {
        this.coodenadoresFocais = coodenadoresFocais;
    }

}

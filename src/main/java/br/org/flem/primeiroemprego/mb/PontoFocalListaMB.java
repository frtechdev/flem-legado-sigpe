package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.PontoFocalDAO;
import br.org.flem.primeiroemprego.entity.PontoFocal;
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
public class PontoFocalListaMB implements Serializable{
    
    @ManagedProperty(value="#{pontoFocalDAO}")
    private PontoFocalDAO pontoFocalDAO;
    
    private List<PontoFocal> pontosFocais;

    @PostConstruct
    public void init() {
        pontosFocais = pontoFocalDAO.obterLista();
    }


    public PontoFocalDAO getPontoFocalDAO() {
        return pontoFocalDAO;
    }

    public void setPontoFocalDAO(PontoFocalDAO pontoFocalDAO) {
        this.pontoFocalDAO = pontoFocalDAO;
    }

    public List<PontoFocal> getPontosFocais() {
        return pontosFocais;
    }

    public void setPontosFocais(List<PontoFocal> pontosFocais) {
        this.pontosFocais = pontosFocais;
    }

}

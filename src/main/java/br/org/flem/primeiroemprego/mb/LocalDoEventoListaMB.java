package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.LocalDoEventoDAO;
import br.org.flem.primeiroemprego.entity.LocalDoEvento;
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
public class LocalDoEventoListaMB implements Serializable{
    
    @ManagedProperty(value="#{localDoEventoDAO}")
    private LocalDoEventoDAO localDoEventoDAO;
    
    private List<LocalDoEvento> locaisDoEvento;

    @PostConstruct
    public void init() {
        locaisDoEvento = localDoEventoDAO.obterLista();
    }


    public LocalDoEventoDAO getLocalDoEventoDAO() {
        return localDoEventoDAO;
    }

    public void setLocalDoEventoDAO(LocalDoEventoDAO localDoEventoDAO) {
        this.localDoEventoDAO = localDoEventoDAO;
    }

    public List<LocalDoEvento> getLocaisDoEvento() {
        return locaisDoEvento;
    }

    public void setLocaisDoEvento(List<LocalDoEvento> locaisDoEvento) {
        this.locaisDoEvento = locaisDoEvento;
    }

}

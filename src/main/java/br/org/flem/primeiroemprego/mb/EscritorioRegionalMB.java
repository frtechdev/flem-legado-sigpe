package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EscritorioRegionalDAO;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class EscritorioRegionalMB implements Serializable {
    
    @ManagedProperty(value = "#{escritorioRegionalDAO}")
    private EscritorioRegionalDAO escritorioRegionalDAO;
    
    private List<EscritorioRegional> lista = new ArrayList<>();
     
    @PostConstruct
    public void init() {
        lista = obterLista();
    }

    public List<EscritorioRegional> obterLista() {
        try {
            return escritorioRegionalDAO.obterLista();
        } catch (Exception ex) {
            Logger.getLogger(EscritorioRegionalMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    //Getters e Setters
    public EscritorioRegionalDAO getEscritorioRegionalDAO() {
        return escritorioRegionalDAO;
    }

    public void setEscritorioRegionalDAO(EscritorioRegionalDAO escritorioRegionalDAO) {
        this.escritorioRegionalDAO = escritorioRegionalDAO;
    }

    public List<EscritorioRegional> getLista() {
        return lista;
    }

    public void setLista(List<EscritorioRegional> lista) {
        this.lista = lista;
    }

    
    
}
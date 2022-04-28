package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DeficienciaDAO;
import br.org.flem.primeiroemprego.entity.Deficiencia;
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
public class DeficienciaListaMB implements Serializable{

    @ManagedProperty(value="#{deficienciaDAO}")
    private DeficienciaDAO deficienciaDAO;

    private List<Deficiencia> deficiencias;

    @PostConstruct
    public void init() {
        deficiencias = deficienciaDAO.obterLista();
    }

    public DeficienciaDAO getDeficienciaDAO() {
        return deficienciaDAO;
    }

    public void setDeficienciaDAO(DeficienciaDAO deficienciaDAO) {
        this.deficienciaDAO = deficienciaDAO;
    }

    public List<Deficiencia> getDeficiencias() {
        return deficiencias;
    }

    public void setDeficiencias(List<Deficiencia> deficiencias) {
        this.deficiencias = deficiencias;
    }

}
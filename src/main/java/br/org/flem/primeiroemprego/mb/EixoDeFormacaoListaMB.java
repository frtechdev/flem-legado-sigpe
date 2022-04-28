package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EixoDeFormacaoDAO;
import br.org.flem.primeiroemprego.entity.EixoDeFormacao;
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
public class EixoDeFormacaoListaMB implements Serializable{
    @ManagedProperty(value="#{eixoDeFormacaoDAO}")
    private EixoDeFormacaoDAO eixoDeFormacaoDAO;

    private List<EixoDeFormacao> eixos;
    
    @PostConstruct
    public void init(){
        eixos = eixoDeFormacaoDAO.obterLista();
    }

    public EixoDeFormacaoDAO getEixoDeFormacaoDAO() {
        return eixoDeFormacaoDAO;
    }

    public void setEixoDeFormacaoDAO(EixoDeFormacaoDAO eixoDeFormacaoDAO) {
        this.eixoDeFormacaoDAO = eixoDeFormacaoDAO;
    }

    public List<EixoDeFormacao> getEixos() {
        return eixos;
    }

    public void setEixos(List<EixoDeFormacao> eixos) {
        this.eixos = eixos;
    }

}

package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EixoDeFormacaoDAO;
import br.org.flem.primeiroemprego.dao.FormacaoDAO;
import br.org.flem.primeiroemprego.entity.Formacao;
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
public class FormacaoListaMB implements Serializable{

    @ManagedProperty(value="#{eixoDeFormacaoDAO}")
    private EixoDeFormacaoDAO eixoDeFormacaoDAO;
    
    @ManagedProperty(value="#{formacaoDAO}")
    private FormacaoDAO formacaoDAO;

    private List<Formacao> formacoes;

    @PostConstruct
    public void init() {
        formacoes = formacaoDAO.obterLista();
    }

    public EixoDeFormacaoDAO getEixoDeFormacaoDAO() {
        return eixoDeFormacaoDAO;
    }

    public void setEixoDeFormacaoDAO(EixoDeFormacaoDAO eixoDeFormacaoDAO) {
        this.eixoDeFormacaoDAO = eixoDeFormacaoDAO;
    }

    public FormacaoDAO getFormacaoDAO() {
        return formacaoDAO;
    }

    public void setFormacaoDAO(FormacaoDAO formacaoDAO) {
        this.formacaoDAO = formacaoDAO;
    }

    public List<Formacao> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(List<Formacao> formacoes) {
        this.formacoes = formacoes;
    }

}
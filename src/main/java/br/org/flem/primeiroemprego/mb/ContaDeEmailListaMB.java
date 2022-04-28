package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ContaDeEmailDAO;
import br.org.flem.primeiroemprego.entity.ContaDeEmail;
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
public class ContaDeEmailListaMB implements Serializable{

    @ManagedProperty(value="#{contaDeEmailDAO}")
    private ContaDeEmailDAO contaDeEmailDAO;

    private List<ContaDeEmail> contaDeEmails;

    @PostConstruct
    public void init() {
        contaDeEmails = contaDeEmailDAO.obterLista();
    }

    public ContaDeEmailDAO getContaDeEmailDAO() {
        return contaDeEmailDAO;
    }

    public void setContaDeEmailDAO(ContaDeEmailDAO contaDeEmailDAO) {
        this.contaDeEmailDAO = contaDeEmailDAO;
    }

    public List<ContaDeEmail> getContaDeEmails() {
        return contaDeEmails;
    }

    public void setContaDeEmails(List<ContaDeEmail> contaDeEmails) {
        this.contaDeEmails = contaDeEmails;
    }

}
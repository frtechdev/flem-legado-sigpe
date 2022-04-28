package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CIDAO;
import br.org.flem.primeiroemprego.entity.CI;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.RedirectUtil;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CIEgressosMB implements Serializable{

    private CI ci;

    @ManagedProperty(value = "#{cIDAO}")
    private CIDAO ciDAO;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            ci = ciDAO.obterPorPK(Long.parseLong(id));
        }else{
            Mensagem.lancar("CI não identificada");
            RedirectUtil.redirect("/");
        }
    }

    public CI getCi() {
        return ci;
    }

    public void setCi(CI ci) {
        this.ci = ci;
    }

    public CIDAO getCiDAO() {
        return ciDAO;
    }

    public void setCiDAO(CIDAO ciDAO) {
        this.ciDAO = ciDAO;
    }

}
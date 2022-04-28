package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.InstituicaoDAO;
import br.org.flem.primeiroemprego.entity.Instituicao;
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
public class InstituicaoListaMB implements Serializable{
    
    @ManagedProperty(value="#{instituicaoDAO}")
    private InstituicaoDAO instituicaoDAO;
    
    private List<Instituicao> instituicoes;

    @PostConstruct
    public void init() {
        instituicoes = instituicaoDAO.obterLista();
    }


    public InstituicaoDAO getInstituicaoDAO() {
        return instituicaoDAO;
    }

    public void setInstituicaoDAO(InstituicaoDAO instituicaoDAO) {
        this.instituicaoDAO = instituicaoDAO;
    }

    public List<Instituicao> getInstituicoes() {
        return instituicoes;
    }

    public void setInstituicoes(List<Instituicao> instituicoes) {
        this.instituicoes = instituicoes;
    }

}

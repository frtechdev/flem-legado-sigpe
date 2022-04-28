package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.UnidadeDeLotacaoDAO;
import br.org.flem.primeiroemprego.entity.UnidadeDeLotacao;
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
public class UnidadeDeLotacaoListaMB implements Serializable{

    @ManagedProperty(value="#{unidadeDeLotacaoDAO}")
    private UnidadeDeLotacaoDAO unidadeDeLotacaoDAO;
    
    private List<UnidadeDeLotacao> unidadesDeLotacao;

    @PostConstruct
    public void init() {
        unidadesDeLotacao = unidadeDeLotacaoDAO.obterLista();
    }

    public UnidadeDeLotacaoDAO getUnidadeDeLotacaoDAO() {
        return unidadeDeLotacaoDAO;
    }

    public void setUnidadeDeLotacaoDAO(UnidadeDeLotacaoDAO unidadeDeLotacaoDAO) {
        this.unidadeDeLotacaoDAO = unidadeDeLotacaoDAO;
    }

    public List<UnidadeDeLotacao> getUnidadesDeLotacao() {
        return unidadesDeLotacao;
    }

    public void setUnidadesDeLotacao(List<UnidadeDeLotacao> unidadesDeLotacao) {
        this.unidadesDeLotacao = unidadesDeLotacao;
    }

}

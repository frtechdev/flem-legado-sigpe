package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CategoriaDaSituacaoDAO;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
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
public class CategoriaDaSituacaoListaMB implements Serializable{
    @ManagedProperty(value="#{categoriaDaSituacaoDAO}")
    private CategoriaDaSituacaoDAO categoriaDaSituacaoDAO;

    private List<CategoriaDaSituacao> categorias;
    
    @PostConstruct
    public void init(){
        categorias = categoriaDaSituacaoDAO.obterLista();
    }

    public CategoriaDaSituacaoDAO getCategoriaDaSituacaoDAO() {
        return categoriaDaSituacaoDAO;
    }

    public void setCategoriaDaSituacaoDAO(CategoriaDaSituacaoDAO categoriaDaSituacaoDAO) {
        this.categoriaDaSituacaoDAO = categoriaDaSituacaoDAO;
    }

    public List<CategoriaDaSituacao> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDaSituacao> categorias) {
        this.categorias = categorias;
    }

}

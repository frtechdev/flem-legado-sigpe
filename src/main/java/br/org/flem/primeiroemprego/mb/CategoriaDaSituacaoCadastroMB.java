package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CategoriaDaSituacaoDAO;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.Mensagem;
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
public class CategoriaDaSituacaoCadastroMB implements Serializable{

    @ManagedProperty(value = "#{categoriaDaSituacaoDAO}")
    private CategoriaDaSituacaoDAO categoriaDaSituacaoDAO;

    private CategoriaDaSituacao categoria;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            categoria = categoriaDaSituacaoDAO.obterPorPK(Long.parseLong(id));
        } else {
            categoria = new CategoriaDaSituacao();
        }
    }

    public String salvar() {
        if (categoria.getId() != null) {
            try{
                categoriaDaSituacaoDAO.alterar(categoria);
                Mensagem.lancar("Categoria alterada com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a categoria");
            }
        } else {
            try{
                categoriaDaSituacaoDAO.inserir(categoria);
                Mensagem.lancar("Categoria inserida com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir a categoria");
            }
        }

        return "lista.xhtml";
    }
    public CategoriaDaSituacaoDAO getCategoriaDaSituacaoDAO() {
        return categoriaDaSituacaoDAO;
    }

    public void setCategoriaDaSituacaoDAO(CategoriaDaSituacaoDAO categoriaDaSituacaoDAO) {
        this.categoriaDaSituacaoDAO = categoriaDaSituacaoDAO;
    }

    public CategoriaDaSituacao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDaSituacao categoria) {
        this.categoria = categoria;
    }

}

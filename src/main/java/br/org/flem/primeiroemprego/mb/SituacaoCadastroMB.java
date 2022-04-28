package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CategoriaDaSituacaoDAO;
import br.org.flem.primeiroemprego.dao.SituacaoDAO;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Situacao;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
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
public class SituacaoCadastroMB implements Serializable{

    @ManagedProperty(value = "#{situacaoDAO}")
    private SituacaoDAO situacaoDAO;
    
    @ManagedProperty(value = "#{categoriaDaSituacaoDAO}")
    private CategoriaDaSituacaoDAO categoriaDaSituacaoDAO;

    private List<CategoriaDaSituacao> categorias;

    private Situacao situacao;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            situacao = situacaoDAO.obterPorPK(Long.parseLong(id));
        } else {
            situacao = new Situacao();
        }
        categorias = categoriaDaSituacaoDAO.obterLista();
    }
    

    public String salvar() {
        if (situacao.getId() != null) {
            try{
                situacaoDAO.alterar(situacao);
                Mensagem.lancar("Situacao alterada com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a situacao");
            }
        } else {
            try{
                situacaoDAO.inserir(situacao);
                Mensagem.lancar("Situacao inserida com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir a situacao");
            }
        }

        return "lista.xhtml";
    }

    public SituacaoDAO getSituacaoDAO() {
        return situacaoDAO;
    }

    public void setSituacaoDAO(SituacaoDAO situacaoDAO) {
        this.situacaoDAO = situacaoDAO;
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

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

}
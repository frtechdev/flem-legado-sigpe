package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EixoDeFormacaoDAO;
import br.org.flem.primeiroemprego.dao.FormacaoDAO;
import br.org.flem.primeiroemprego.entity.EixoDeFormacao;
import br.org.flem.primeiroemprego.entity.Formacao;
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
public class FormacaoCadastroMB implements Serializable{

    @ManagedProperty(value = "#{formacaoDAO}")
    private FormacaoDAO formacaoDAO;
    
    @ManagedProperty(value = "#{eixoDeFormacaoDAO}")
    private EixoDeFormacaoDAO eixoDeFormacaoDAO;

    private List<EixoDeFormacao> eixos;

    private Formacao formacao;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            formacao = formacaoDAO.obterPorPK(Long.parseLong(id));
        } else {
            formacao = new Formacao();
        }
        eixos = eixoDeFormacaoDAO.obterLista();
    }
    

    public String salvar() {
        if (formacao.getId() != null) {
            try{
                formacaoDAO.alterar(formacao);
                Mensagem.lancar("Formação alterada com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a formação");
            }
        } else {
            try{
                formacaoDAO.inserir(formacao);
                Mensagem.lancar("Formação inserida com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir a formação");
            }
        }

        return "lista.xhtml";
    }

    public FormacaoDAO getFormacaoDAO() {
        return formacaoDAO;
    }

    public void setFormacaoDAO(FormacaoDAO formacaoDAO) {
        this.formacaoDAO = formacaoDAO;
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

    public Formacao getFormacao() {
        return formacao;
    }

    public void setFormacao(Formacao formacao) {
        this.formacao = formacao;
    }

}
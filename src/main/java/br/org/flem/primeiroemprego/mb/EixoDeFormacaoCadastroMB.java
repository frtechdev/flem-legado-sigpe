package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EixoDeFormacaoDAO;
import br.org.flem.primeiroemprego.entity.EixoDeFormacao;
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
public class EixoDeFormacaoCadastroMB implements Serializable{

    @ManagedProperty(value = "#{eixoDeFormacaoDAO}")
    private EixoDeFormacaoDAO eixoDeFormacaoDAO;

    private EixoDeFormacao eixoDeFormacao;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            eixoDeFormacao = eixoDeFormacaoDAO.obterPorPK(Long.parseLong(id));
        } else {
            eixoDeFormacao = new EixoDeFormacao();
        }
    }

    public String salvar() {
        if (eixoDeFormacao.getId() != null) {
            try{
                eixoDeFormacaoDAO.alterar(eixoDeFormacao);
                Mensagem.lancar("Eixo de Formação alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar o eixo de formação");
            }
        } else {
            try{
                eixoDeFormacaoDAO.inserir(eixoDeFormacao);
                Mensagem.lancar("Eixo de formação inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o eixo de formação");
            }
        }

        return "lista.xhtml";
    }
    public EixoDeFormacaoDAO getEixoDeFormacaoDAO() {
        return eixoDeFormacaoDAO;
    }

    public void setEixoDeFormacaoDAO(EixoDeFormacaoDAO eixoDeFormacaoDAO) {
        this.eixoDeFormacaoDAO = eixoDeFormacaoDAO;
    }

    public EixoDeFormacao getEixoDeFormacao() {
        return eixoDeFormacao;
    }

    public void setEixoDeFormacao(EixoDeFormacao eixoDeFormacao) {
        this.eixoDeFormacao = eixoDeFormacao;
    }

}

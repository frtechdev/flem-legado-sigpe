package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ContaDeEmailDAO;
import br.org.flem.primeiroemprego.entity.ContaDeEmail;
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
public class ContaDeEmailCadastroMB implements Serializable{

    @ManagedProperty(value = "#{contaDeEmailDAO}")
    private ContaDeEmailDAO contaDeEmailDAO;

    private ContaDeEmail contaDeEmail;
    
    private boolean mostrarPanelConexao;
    
    private String senha;//Mapeado na tela, para evitar exibir na tela a senha salva no banco

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            contaDeEmail = contaDeEmailDAO.obterPorPK(Long.parseLong(id));
        } else {
            contaDeEmail = new ContaDeEmail();
        }
    }
    

    public String salvar() {
        if(!senha.isEmpty()){
            contaDeEmail.setSenha(senha);
        }
        if (contaDeEmail.getId() != null) {
            try{
                contaDeEmailDAO.alterar(contaDeEmail);
                Mensagem.lancar("Conta de Email alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a conta de email");
            }
        } else {
            try{
                contaDeEmailDAO.inserir(contaDeEmail);
                Mensagem.lancar("Conta de Email inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir a conta de email");
            }
        }

        return "lista.xhtml";
    }

    public ContaDeEmailDAO getContaDeEmailDAO() {
        return contaDeEmailDAO;
    }

    public void setContaDeEmailDAO(ContaDeEmailDAO contaDeEmailDAO) {
        this.contaDeEmailDAO = contaDeEmailDAO;
    }

    public ContaDeEmail getContaDeEmail() {
        return contaDeEmail;
    }

    public void setContaDeEmail(ContaDeEmail contaDeEmail) {
        this.contaDeEmail = contaDeEmail;
    }

    public boolean isMostrarPanelConexao() {
        return mostrarPanelConexao;
    }

    public void setMostrarPanelConexao(boolean mostrarPanelConexao) {
        this.mostrarPanelConexao = mostrarPanelConexao;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
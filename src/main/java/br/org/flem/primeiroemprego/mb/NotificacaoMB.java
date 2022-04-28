package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.NotificacaoDAO;
import br.org.flem.primeiroemprego.entity.Notificacao;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ViewScoped
@ManagedBean
public class NotificacaoMB implements Serializable{

    @ManagedProperty(value = "#{notificacaoDAO}")
    private NotificacaoDAO notificacaoDAO;

    private List<Notificacao> notificacoes;

    @ManagedProperty(value = "#{usuarioLogadoMB}")
    private UsuarioLogadoMB usuarioLogadoMB;

    private Notificacao notificacao;

    private boolean fieldsetFechado = true;
    
    

    @PostConstruct
    public void init(){
        notificacao = new Notificacao();
        atualizarNotificacoes();
    }

    private void atualizarNotificacoes() {
        notificacoes = notificacaoDAO.obterNaoConcluidasPorUsuario(usuarioLogadoMB.getUsuario().getCodigoDominio());
    }

    public void registrarConclusao(Long id){
        Notificacao notificacaoAConcluir = notificacaoDAO.obterPorPK(id);
        notificacaoAConcluir.setDataConcluido(new Date());
        try {
            notificacaoDAO.alterar(notificacaoAConcluir);
            atualizarNotificacoes();
            Mensagem.lancar("Conclusão registrada");
        } catch (Exception ex) {
            Mensagem.lancar("Erro ao registrar conclusão");
            Logger.getLogger(NotificacaoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public NotificacaoDAO getNotificacaoDAO() {
        return notificacaoDAO;
    }

    public void setNotificacaoDAO(NotificacaoDAO notificacaoDAO) {
        this.notificacaoDAO = notificacaoDAO;
    }

    public List<Notificacao> getNotificacoes() {
        return notificacoes;
    }

    public void setNotificacoes(List<Notificacao> notificacoes) {
        this.notificacoes = notificacoes;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

    public boolean isFieldsetFechado() {
        return fieldsetFechado;
    }

    public void setFieldsetFechado(boolean fieldsetFechado) {
        this.fieldsetFechado = fieldsetFechado;
    }

}
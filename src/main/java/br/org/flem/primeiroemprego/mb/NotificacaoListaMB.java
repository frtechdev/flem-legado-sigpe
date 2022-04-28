package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.NotificacaoDAO;
import br.org.flem.primeiroemprego.entity.Notificacao;
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
@ViewScoped
@ManagedBean
public class NotificacaoListaMB implements Serializable{

    @ManagedProperty(value = "#{notificacaoDAO}")
    private NotificacaoDAO notificacaoDAO;

    private List<Notificacao> notificacoes;

    @ManagedProperty(value = "#{usuarioLogadoMB}")
    private UsuarioLogadoMB usuarioLogadoMB;

    @PostConstruct
    public void init(){
        if(usuarioLogadoMB.temPermissao("primEmp.notificacaoGeral")){
            notificacoes = notificacaoDAO.obterLista();
        }else{
            notificacoes = notificacaoDAO.obterCriadasPorUsuario(usuarioLogadoMB.getUsuario().getUsername());
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

}
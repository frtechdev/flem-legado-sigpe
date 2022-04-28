package br.org.flem.primeiroemprego.mb;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.primeiroemprego.dao.NotificacaoDAO;
import br.org.flem.primeiroemprego.entity.Notificacao;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ViewScoped
@ManagedBean
public class NotificacaoNovaMB implements Serializable{

    @ManagedProperty(value = "#{notificacaoDAO}")
    private NotificacaoDAO notificacaoDAO;

    private Notificacao notificacao = new Notificacao();

    public String salvar(){
        try{
            notificacaoDAO.inserir(notificacao);
            Mensagem.lancar("Notificação salva com sucesso");
            return "lista.xhtml";
        }catch(Exception e){
            Mensagem.lancar("Erro ao salvar a Notificação");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
    
    public List<Usuario> obterUsuariosComAcessoAoAplicativo(){
//        List<Usuario> usuarios = new UsuarioDAO().obterTodosComAcessoAoAplicativo(PropertiesUtil.getProperty("nomeCurtoAplicativo"));
//        Usuario usuarioRemover = null;
//        for(Usuario usu : usuarios ){
//            if(usu.getUsername().equals("ufilho")){
//                usuarioRemover = usu;
//            }
//        }
//        usuarios.remove(usuarioRemover);
//        return usuarios;
        return new UsuarioDAO().obterTodosComAcessoAoAplicativo(PropertiesUtil.getProperty("nomeCurtoAplicativo"));
    }

    public NotificacaoDAO getNotificacaoDAO() {
        return notificacaoDAO;
    }

    public void setNotificacaoDAO(NotificacaoDAO notificacaoDAO) {
        this.notificacaoDAO = notificacaoDAO;
    }

    public Notificacao getNotificacao() {
        return notificacao;
    }

    public void setNotificacao(Notificacao notificacao) {
        this.notificacao = notificacao;
    }

}
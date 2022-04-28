package br.org.flem.primeiroemprego.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


/**
 *
 * @author emsilva
 */
public class Mensagem {    
    
    public static void lancar(String mensagem){
        lancar(null, FacesMessage.SEVERITY_INFO, mensagem);
    }

    public static void lancar(String clientId, FacesMessage.Severity severity, String mensagem){
        FacesContext.getCurrentInstance().addMessage(clientId ,new FacesMessage(severity, mensagem, ""));
        FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
    }
    
    public static void lancarMiniPopUp(String titulo, String mensagem){
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(titulo, mensagem) );
    }
    
    public static void lancarMensagemErro(String mensagem){
        lancar(null, FacesMessage.SEVERITY_ERROR, mensagem);
    }
    
    public static void lancarMensagemFatalErro(String mensagem){
        lancar(null, FacesMessage.SEVERITY_FATAL, mensagem);
    }
    
    public static void lancarMensagemInfo(String mensagem){
        lancar(null, FacesMessage.SEVERITY_INFO, mensagem);
    }
    
    public static void lancarMensagemWarn(String mensagem){
        lancar(null, FacesMessage.SEVERITY_WARN, mensagem);
    }
}
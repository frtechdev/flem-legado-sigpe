package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import java.io.Serializable;
import javax.faces.bean.ManagedProperty;
import org.primefaces.context.RequestContext;

/**
 *
 * @author tscortes
 */
public class BaseMB implements Serializable{
    
    @ManagedProperty(value = "#{usuarioLogadoMB}")
    private UsuarioLogadoMB usuarioLogadoMB;
    
    public void abrirModal(String modal){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('"+modal+"').show()");
    }
    
    public void fecharModal(String modal){
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('"+modal+"').hide()");
    }
    //Verificar se Usuário Logado é administrador
    public boolean isUserAdm(){
        return usuarioLogadoMB.temPermissao("primEmp.super");
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }
    
    
}

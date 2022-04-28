package br.org.flem.primeiroemprego.envers;

import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.BeansUtil;
import org.hibernate.envers.RevisionListener;

/**
 *
 * @author emsilva
 */
public class VersaoDosDadosListener implements RevisionListener{

    @Override
    public void newRevision(Object revisionEntity) {
        UsuarioLogadoMB usuarioLogadoMB = (UsuarioLogadoMB) BeansUtil.getBean("usuarioLogadoMB");
        ((VersaoDosDados)revisionEntity).setLogin(usuarioLogadoMB != null ? usuarioLogadoMB.getUsuario().getUsername() : "publico");
    }
    
}

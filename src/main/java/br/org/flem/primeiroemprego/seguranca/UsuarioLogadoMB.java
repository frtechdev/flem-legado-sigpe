package br.org.flem.primeiroemprego.seguranca;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.fw.service.IUsuario;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;


/**
 *
 * @author emsilva
 */
@ManagedBean
@SessionScoped
public class UsuarioLogadoMB implements Serializable{

    @ManagedProperty(value = "#{sessionScope['USUARIO_LOGADO']}")
    private IUsuario usuario;
    
    private Map<Integer,Usuario> mapUsuariosComAcessoAoAplicativo;
    private Map<String,Usuario> mapUsuariosComAcessoAoAplicativoPorLogin;
    private List<Usuario> usuariosComAcessoAoAplicativo;

    @PostConstruct
    public void init(){
        usuariosComAcessoAoAplicativo =  new UsuarioDAO().obterTodosComAcessoAoAplicativo(PropertiesUtil.getProperty("nomeCurtoAplicativo"));
        mapUsuariosComAcessoAoAplicativo = new HashMap<Integer, Usuario>();
        mapUsuariosComAcessoAoAplicativoPorLogin = new HashMap<String, Usuario>();
        for(Usuario u : usuariosComAcessoAoAplicativo){
            mapUsuariosComAcessoAoAplicativo.put(u.getCodigoDominio(), u);
            mapUsuariosComAcessoAoAplicativoPorLogin.put(u.getUsername(), u);
        }
    }
    
    public String nomeUsuarioPorLogin(String login){
        if(mapUsuariosComAcessoAoAplicativoPorLogin.containsKey(login)){
            return mapUsuariosComAcessoAoAplicativoPorLogin.get(login).getNome();
        }
        return login;
    }
    
    public String nomeUsuarioPorId(Integer id){
        if(mapUsuariosComAcessoAoAplicativo.containsKey(id)){
            return mapUsuariosComAcessoAoAplicativo.get(id).getNome();
        }
        return id.toString();
    }

    public IUsuario getUsuario() {
        return usuario;
    }

    public void setUsuario(IUsuario usuario) {
        this.usuario = usuario;
    }

    public boolean temPermissao(String codigo) {
        return this.usuario.getPermissoes().contains(codigo);
    }

    public Map<Integer, Usuario> getMapUsuariosComAcessoAoAplicativo() {
        return mapUsuariosComAcessoAoAplicativo;
    }

    public void setMapUsuariosComAcessoAoAplicativo(Map<Integer, Usuario> mapUsuariosComAcessoAoAplicativo) {
        this.mapUsuariosComAcessoAoAplicativo = mapUsuariosComAcessoAoAplicativo;
    }

    public Map<String, Usuario> getMapUsuariosComAcessoAoAplicativoPorLogin() {
        return mapUsuariosComAcessoAoAplicativoPorLogin;
    }

    public void setMapUsuariosComAcessoAoAplicativoPorLogin(Map<String, Usuario> mapUsuariosComAcessoAoAplicativoPorLogin) {
        this.mapUsuariosComAcessoAoAplicativoPorLogin = mapUsuariosComAcessoAoAplicativoPorLogin;
    }

    public List<Usuario> getUsuariosComAcessoAoAplicativo() {
        return usuariosComAcessoAoAplicativo;
    }

    public void setUsuariosComAcessoAoAplicativo(List<Usuario> usuariosComAcessoAoAplicativo) {
        this.usuariosComAcessoAoAplicativo = usuariosComAcessoAoAplicativo;
    }

}

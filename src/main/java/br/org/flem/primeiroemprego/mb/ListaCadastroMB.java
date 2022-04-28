package br.org.flem.primeiroemprego.mb;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dao.UsuarioDAO;
import br.org.flem.fw.persistencia.dto.Usuario;
import br.org.flem.primeiroemprego.dao.ListaDAO;
import br.org.flem.primeiroemprego.entity.Lista;
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
 * @author MPPassos
 */
@ManagedBean
@ViewScoped
public class ListaCadastroMB implements Serializable {

    @ManagedProperty(value = "#{listaDAO}")
    private ListaDAO listaDAO;
    
    private Lista lista;

    @PostConstruct
    public void init() {
        String idLista = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (idLista != null) {
            lista = listaDAO.obterPorPK(Long.parseLong(idLista));
        } else {
            lista = new Lista();
        }
    }

    public String salvar() {
        if (lista.getId() != null) {
            try{
                listaDAO.alterar(lista);
                Mensagem.lancarMensagemInfo("Lista alterada com sucesso");
            }catch(Exception e){
                Mensagem.lancarMensagemErro("Erro ao alterar a lista");
            }
        } else {
            try{
                listaDAO.inserir(lista);
                Mensagem.lancarMensagemInfo("Lista inserida com sucesso");
            }catch(Exception e){
                Mensagem.lancarMensagemErro("Erro ao inserir a lista");
            }
        }

        return "lista.xhtml";
    }

    public List<Usuario> obterUsuariosComAcessoAoAplicativo(){
        return new UsuarioDAO().obterTodosComAcessoAoAplicativo(PropertiesUtil.getProperty("nomeCurtoAplicativo"));
    }

    public ListaDAO getListaDAO() {
        return listaDAO;
    }

    public void setListaDAO(ListaDAO listaDAO) {
        this.listaDAO = listaDAO;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

}

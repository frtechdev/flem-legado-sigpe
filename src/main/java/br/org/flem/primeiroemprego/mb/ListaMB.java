package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EgressoListaDAO;
import br.org.flem.primeiroemprego.dao.ListaDAO;
import br.org.flem.primeiroemprego.entity.EgressoLista;
import br.org.flem.primeiroemprego.entity.Lista;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author MPPassos
 */
@ManagedBean
@ViewScoped
public class ListaMB implements Serializable {

    @ManagedProperty(value="#{listaDAO}")
    private ListaDAO listaDAO;
    @ManagedProperty(value="#{egressoListaDAO}")
    private EgressoListaDAO egressoListaDAO;

    public String excluir(Lista lista) {
        try{
            List<EgressoLista> listaEgresso = egressoListaDAO.obterDaLista(lista);
            if(listaEgresso != null && !listaEgresso.isEmpty()){
                for(EgressoLista  el : listaEgresso){
                    egressoListaDAO.excluir(el);
                }
            }
            listaDAO.excluir(lista);
            Mensagem.lancar("Lista excluída com sucesso");
        }catch(Exception e){
            Mensagem.lancar("Erro ao excluir a lista");
        }
        return "lista.xhtml";
    }

    public List<Lista> listas() {
        return listaDAO.obterOrdenadoDataCriacao();
    }
    
    public void ativarInativar(Lista item){
        
        try{
            Lista lista = listaDAO.obterPorPK(item.getId());
            if( lista != null ){
                lista.setAtivo(!item.getAtivo());
                listaDAO.alterar(lista);
                Mensagem.lancar("Lista alterada com sucesso");
            }
        }catch(Exception e){
            Mensagem.lancar("Erro ao alterar a lista");
        }
        
    }

    public ListaDAO getListaDAO() {
        return listaDAO;
    }

    public void setListaDAO(ListaDAO listaDAO) {
        this.listaDAO = listaDAO;
    }

    public EgressoListaDAO getEgressoListaDAO() {
        return egressoListaDAO;
    }

    public void setEgressoListaDAO(EgressoListaDAO egressoListaDAO) {
        this.egressoListaDAO = egressoListaDAO;
    }
    
    

}

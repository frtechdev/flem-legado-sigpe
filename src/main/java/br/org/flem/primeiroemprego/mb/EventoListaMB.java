package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EventoDAO;
import br.org.flem.primeiroemprego.entity.Evento;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.logging.Logger;
import java.util.logging.Level;


/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class EventoListaMB implements Serializable{
    
    @ManagedProperty(value="#{eventoDAO}")
    private EventoDAO eventoDAO;
    
    private List<Evento> eventos;
    
    private static final String MSG_EXCLUIR_EVENTO = "Evento exluido com sucesso!";

    @PostConstruct
    public void init() {
        eventos = eventoDAO.obterEventoOrdenadoPorDataDesc();
    }
    
    /***
     * Metodo que remove o evento da lista<br>
     * @autor <a href="ermoura@flem.org.br">ermoura</a><br>
     * @param evento objeto
     */
    public void removerEvento(Evento evento) {
        try {
           this.eventoDAO.excluir(evento);
           init();
           Mensagem.lancar(MSG_EXCLUIR_EVENTO);
        } catch (Exception ex) {
            Logger.getLogger(EventoListaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     /***
     * Metodo que redireciona para pagina de cadastro de eventos 
     * em modo edição<br>
     * @autor <a href="ermoura@flem.org.br">ermoura</a><br>
     * @param id Long
     */
    public String editarEvento(Long id) {
        return "cadastro.xhtml?id="+id+"faces-redirect=true";
    }
    
    public void openModalRemover(Evento evento){
        HashMap<String,Object> options = new HashMap<String, Object>();
    }
    

    public EventoDAO getEventoDAO() {
        return eventoDAO;
    }

    public void setEventoDAO(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

}

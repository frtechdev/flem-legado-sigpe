package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.EventoDAO;
import br.org.flem.primeiroemprego.dao.LocalDoEventoDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Evento;
import br.org.flem.primeiroemprego.entity.LocalDoEvento;
import br.org.flem.primeiroemprego.entity.Municipio;
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
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class EventoCadastroMB implements Serializable{

    @ManagedProperty(value = "#{eventoDAO}")
    private EventoDAO eventoDAO;
    
    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;
    
    @ManagedProperty(value = "#{localDoEventoDAO}")
    private LocalDoEventoDAO localDoEventoDAO;
    
    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;

    private List<Demandante> demandantes;
    private List<Municipio> municipios;
    private List<LocalDoEvento> locais;

    private Evento evento;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            evento = eventoDAO.obterPorPK(Long.parseLong(id));
        } else {
            evento = new Evento();
        }
        demandantes = demandanteDAO.obterLista();
        municipios = municipioDAO.obterLista();
        locais = localDoEventoDAO.obterLista();
    }

    public List<String> obterRespostas(String campo){
        return eventoDAO.obterRespostasDoCampo(campo);
    }    

    public String salvar() {
        if (evento.getId() != null) {
            try{
                eventoDAO.alterar(evento);
                Mensagem.lancar("Evento alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar o evento");
            }
        } else {
            try{
                eventoDAO.inserir(evento);
                Mensagem.lancar("Evento inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o evento");
            }
        }

        return "lista.xhtml";
    }

    public EventoDAO getEventoDAO() {
        return eventoDAO;
    }

    public void setEventoDAO(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
    }

    public List<Demandante> getDemandantes() {
        return demandantes;
    }

    public void setDemandantes(List<Demandante> demandantes) {
        this.demandantes = demandantes;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public LocalDoEventoDAO getLocalDoEventoDAO() {
        return localDoEventoDAO;
    }

    public void setLocalDoEventoDAO(LocalDoEventoDAO localDoEventoDAO) {
        this.localDoEventoDAO = localDoEventoDAO;
    }

    public List<LocalDoEvento> getLocais() {
        return locais;
    }

    public void setLocais(List<LocalDoEvento> locais) {
        this.locais = locais;
    }

}
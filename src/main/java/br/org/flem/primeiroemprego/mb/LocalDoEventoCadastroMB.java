package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.LocalDoEventoDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.entity.Instituicao;
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
public class LocalDoEventoCadastroMB implements Serializable{

    @ManagedProperty(value = "#{localDoEventoDAO}")
    private LocalDoEventoDAO localDoEventoDAO;

    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;

    private List<Municipio> municipios;

    private LocalDoEvento localDoEvento;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            localDoEvento = localDoEventoDAO.obterPorPK(Long.parseLong(id));
        } else {
            localDoEvento = new LocalDoEvento();
        }
        municipios = municipioDAO.obterLista();
    }
    

    public String salvar() {
        if (localDoEvento.getId() != null) {
            try{
                localDoEventoDAO.alterar(localDoEvento);
                Mensagem.lancar("Local do Evento alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar o Local do Evento");
            }
        } else {
            try{
                localDoEventoDAO.inserir(localDoEvento);
                Mensagem.lancar("Local do Evento inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o Local do Evento");
            }
        }

        return "lista.xhtml";
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public LocalDoEventoDAO getLocalDoEventoDAO() {
        return localDoEventoDAO;
    }

    public void setLocalDoEventoDAO(LocalDoEventoDAO localDoEventoDAO) {
        this.localDoEventoDAO = localDoEventoDAO;
    }

    public LocalDoEvento getLocalDoEvento() {
        return localDoEvento;
    }

    public void setLocalDoEvento(LocalDoEvento localDoEvento) {
        this.localDoEvento = localDoEvento;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

}
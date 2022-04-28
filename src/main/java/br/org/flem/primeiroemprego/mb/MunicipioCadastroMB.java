package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.dao.TerritorioDAO;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.Territorio;
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
public class MunicipioCadastroMB implements Serializable{

    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    
    @ManagedProperty(value = "#{territorioDAO}")
    private TerritorioDAO territorioDAO;

    private Municipio municipio;
    
    private List<Territorio> territorios;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            municipio = municipioDAO.obterPorPK(Long.parseLong(id));
        } else {
            municipio = new Municipio();
        }
        territorios = territorioDAO.obterLista();
    }

    public String salvar() {
        if (municipio.getId() != null) {
            try{
                municipioDAO.alterar(municipio);
                Mensagem.lancar("Município alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar o Município");
            }
        } else {
            try{
                municipioDAO.inserir(municipio);
                Mensagem.lancar("Município inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o Município");
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

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public TerritorioDAO getTerritorioDAO() {
        return territorioDAO;
    }

    public void setTerritorioDAO(TerritorioDAO territorioDAO) {
        this.territorioDAO = territorioDAO;
    }

    public List<Territorio> getTerritorios() {
        return territorios;
    }

    public void setTerritorios(List<Territorio> territorios) {
        this.territorios = territorios;
    }

}
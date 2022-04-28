package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.CoordenadorFocalDAO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.CoordenadorFocal;
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
public class CoordenadorFocalCadastroMB implements Serializable{

    @ManagedProperty(value = "#{coordenadorFocalDAO}")
    private CoordenadorFocalDAO coordenadorFocalDAO;
    
    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;

    private List<Demandante> demandantes;

    private CoordenadorFocal coordenadorFocal;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            coordenadorFocal = coordenadorFocalDAO.obterPorPK(Long.parseLong(id));
        } else {
            coordenadorFocal = new CoordenadorFocal();
        }
        demandantes = demandanteDAO.obterLista();
    }
    

    public String salvar() {
        if (coordenadorFocal.getId() != null) {
            try{
                coordenadorFocalDAO.alterar(coordenadorFocal);
                Mensagem.lancar("Coordenador Focal alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a coordenador focal");
            }
        } else {
            try{
                coordenadorFocalDAO.inserir(coordenadorFocal);
                Mensagem.lancar("Coordenador Focal inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o coordenador focal");
            }
        }

        return "lista.xhtml";
    }

    public CoordenadorFocalDAO getCoordenadorFocalDAO() {
        return coordenadorFocalDAO;
    }

    public void setCoordenadorFocalDAO(CoordenadorFocalDAO coordenadorFocalDAO) {
        this.coordenadorFocalDAO = coordenadorFocalDAO;
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

    public CoordenadorFocal getCoordenadorFocal() {
        return coordenadorFocal;
    }

    public void setCoordenadorFocal(CoordenadorFocal coordenadorFocal) {
        this.coordenadorFocal = coordenadorFocal;
    }

}
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.InstituicaoDAO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Instituicao;
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
public class InstituicaoCadastroMB implements Serializable{

    @ManagedProperty(value = "#{instituicaoDAO}")
    private InstituicaoDAO instituicaoDAO;
    
    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;

    private List<Demandante> demandantes;

    private Instituicao instituicao;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            instituicao = instituicaoDAO.obterPorPK(Long.parseLong(id));
        } else {
            instituicao = new Instituicao();
        }
        demandantes = demandanteDAO.obterLista();
    }
    

    public String salvar() {
        if (instituicao.getId() != null) {
            try{
                instituicaoDAO.alterar(instituicao);
                Mensagem.lancar("Instituição alterada com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a instituição");
            }
        } else {
            try{
                instituicaoDAO.inserir(instituicao);
                Mensagem.lancar("Instituição inserida com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir a institução");
            }
        }

        return "lista.xhtml";
    }

    public InstituicaoDAO getInstituicaoDAO() {
        return instituicaoDAO;
    }

    public void setInstituicaoDAO(InstituicaoDAO instituicaoDAO) {
        this.instituicaoDAO = instituicaoDAO;
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

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

}
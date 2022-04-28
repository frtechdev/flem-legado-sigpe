package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
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
public class DemandanteCadastroMB implements Serializable{

    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;

    private Demandante demandante;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            demandante = demandanteDAO.obterPorPK(Long.parseLong(id));
        } else {
            demandante = new Demandante();
        }
    }
    

    public String salvar() {
        if (demandante.getId() != null) {
            try{
                demandanteDAO.alterar(demandante);
                Mensagem.lancar("Demandante alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar o demandante");
            }
        } else {
            try{
                demandanteDAO.inserir(demandante);
                Mensagem.lancar("Demandante inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o demandante");
            }
        }

        return "lista.xhtml";
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
    }

    public Demandante getDemandante() {
        return demandante;
    }

    public void setDemandante(Demandante demandante) {
        this.demandante = demandante;
    }

}
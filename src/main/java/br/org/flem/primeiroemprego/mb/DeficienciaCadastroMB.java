package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DeficienciaDAO;
import br.org.flem.primeiroemprego.entity.Deficiencia;
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
public class DeficienciaCadastroMB implements Serializable{

    @ManagedProperty(value = "#{deficienciaDAO}")
    private DeficienciaDAO deficienciaDAO;

    private Deficiencia deficiencia;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            deficiencia = deficienciaDAO.obterPorPK(Long.parseLong(id));
        } else {
            deficiencia = new Deficiencia();
        }
    }
    

    public String salvar() {
        if (deficiencia.getId() != null) {
            try{
                deficienciaDAO.alterar(deficiencia);
                Mensagem.lancar("Deficiência alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar o deficiência");
            }
        } else {
            try{
                deficienciaDAO.inserir(deficiencia);
                Mensagem.lancar("Deficiência inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o deficiência");
            }
        }

        return "lista.xhtml";
    }

    public DeficienciaDAO getDeficienciaDAO() {
        return deficienciaDAO;
    }

    public void setDeficienciaDAO(DeficienciaDAO deficienciaDAO) {
        this.deficienciaDAO = deficienciaDAO;
    }

    public Deficiencia getDeficiencia() {
        return deficiencia;
    }

    public void setDeficiencia(Deficiencia deficiencia) {
        this.deficiencia = deficiencia;
    }

}
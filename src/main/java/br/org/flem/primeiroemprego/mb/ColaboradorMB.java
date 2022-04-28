package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ColaboradorDAO;
import br.org.flem.primeiroemprego.dao.EscritorioRegionalDAO;
import br.org.flem.primeiroemprego.entity.Cargo;
import br.org.flem.primeiroemprego.entity.Colaborador;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class ColaboradorMB extends BaseMB{
    
    public Colaborador obj;

    @ManagedProperty(value = "#{colaboradorDAO}")
    private ColaboradorDAO colaboradorDAO;
    
    @ManagedProperty(value = "#{escritorioRegionalDAO}")
    private EscritorioRegionalDAO escritorioRegionalDAO;
    
    private List<Colaborador> colaboradores = new ArrayList<>();
    
    private List<EscritorioRegional> escritorios = new ArrayList<>();
    
    private List<String> cargos;
    
    @PostConstruct
    public void init() {
        colaboradores = obterLista();
        escritorios = escritorioRegionalDAO.obterLista();
        cargos = Cargo.getListString();
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if(id != null && !id.isEmpty()){
            obj = this.obterPorPK(Long.parseLong(id));
        }else{
            obj = new Colaborador();
        }
    }

    public void inserir() {
        try {
            if(obj.getId() == null){
                colaboradorDAO.inserir(obj);
                obj = new Colaborador();
            }else{
                colaboradorDAO.alterar(obj);
            }
            
            Mensagem.lancarMensagemInfo("Salvo com sucesso");
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.lancarMensagemErro("Erro ao cadastrar: "+ex.getMessage());
        }
    }

    public void excluir() {
        try {
            colaboradorDAO.excluir(obj);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Colaborador> obterLista() {
        try {
            return colaboradorDAO.obterLista();
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Colaborador obterPorPK(Long id) {
        try {
            return colaboradorDAO.obterPorPK(id);
        } catch (Exception ex) {
            Logger.getLogger(ColaboradorMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public List<String> getCargo(){
        return Cargo.getListString();
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public ColaboradorDAO getColaboradorDAO() {
        return colaboradorDAO;
    }

    public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
        this.colaboradorDAO = colaboradorDAO;
    }

    public List<EscritorioRegional> getEscritorios() {
        return escritorios;
    }

    public void setEscritorios(List<EscritorioRegional> escritorios) {
        this.escritorios = escritorios;
    }

    public EscritorioRegionalDAO getEscritorioRegionalDAO() {
        return escritorioRegionalDAO;
    }

    public void setEscritorioRegionalDAO(EscritorioRegionalDAO escritorioRegionalDAO) {
        this.escritorioRegionalDAO = escritorioRegionalDAO;
    }

    public Colaborador getObj() {
        return obj;
    }

    public void setObj(Colaborador obj) {
        this.obj = obj;
    }

    public List<String> getCargos() {
        return cargos;
    }

    public void setCargos(List<String> cargos) {
        this.cargos = cargos;
    }
    
    
    
}

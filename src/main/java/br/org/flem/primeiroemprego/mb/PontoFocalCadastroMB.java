package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.InstituicaoDAO;
import br.org.flem.primeiroemprego.dao.PontoFocalDAO;
import br.org.flem.primeiroemprego.dao.UnidadeDeLotacaoDAO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Instituicao;
import br.org.flem.primeiroemprego.entity.PontoFocal;
import br.org.flem.primeiroemprego.entity.UnidadeDeLotacao;
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
public class PontoFocalCadastroMB implements Serializable{

    @ManagedProperty(value = "#{unidadeDeLotacaoDAO}")
    private UnidadeDeLotacaoDAO unidadeDeLotacaoDAO;
    
    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;
    
    @ManagedProperty(value = "#{instituicaoDAO}")
    private InstituicaoDAO instituicaoDAO;
    
    @ManagedProperty(value = "#{pontoFocalDAO}")
    private PontoFocalDAO pontoFocalDAO;
    
    private Demandante demandante;

    private List<Demandante> demandantes;

    private Instituicao instituicao;
    
    private List<Instituicao> instituicoes;

    private UnidadeDeLotacao unidadeDeLotacao;
    
    private List<UnidadeDeLotacao> unidadesDeLotacao;

    private PontoFocal pontoFocal;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            pontoFocal = pontoFocalDAO.obterPorPK(Long.parseLong(id));
        } else {
            pontoFocal = new PontoFocal();
        }
        demandantes = demandanteDAO.obterLista();
    }
    
    public void atualizarInstituicoes(){
        instituicoes = instituicaoDAO.obterPorDemandante(demandante);
        instituicao = null;
    }
    
    public void atualizarUnidadesDeLotacao(){
        unidadesDeLotacao = unidadeDeLotacaoDAO.obterPorInstituicao(instituicao);
        unidadeDeLotacao = null;
    }
    

    public String salvar() {
        if (pontoFocal.getId() != null) {
            try{
                pontoFocalDAO.alterar(pontoFocal);
                Mensagem.lancar("Ponto Focal alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar o Ponto Focal");
            }
        } else {
            try{
                pontoFocalDAO.inserir(pontoFocal);
                Mensagem.lancar("Ponto Focal inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir o Ponto Focal");
            }
        }

        return "lista.xhtml";
    }

    public UnidadeDeLotacaoDAO getUnidadeDeLotacaoDAO() {
        return unidadeDeLotacaoDAO;
    }

    public void setUnidadeDeLotacaoDAO(UnidadeDeLotacaoDAO unidadeDeLotacaoDAO) {
        this.unidadeDeLotacaoDAO = unidadeDeLotacaoDAO;
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
    }

    public InstituicaoDAO getInstituicaoDAO() {
        return instituicaoDAO;
    }

    public void setInstituicaoDAO(InstituicaoDAO instituicaoDAO) {
        this.instituicaoDAO = instituicaoDAO;
    }

    public PontoFocalDAO getPontoFocalDAO() {
        return pontoFocalDAO;
    }

    public void setPontoFocalDAO(PontoFocalDAO pontoFocalDAO) {
        this.pontoFocalDAO = pontoFocalDAO;
    }

    public Demandante getDemandante() {
        return demandante;
    }

    public void setDemandante(Demandante demandante) {
        this.demandante = demandante;
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

    public List<Instituicao> getInstituicoes() {
        return instituicoes;
    }

    public void setInstituicoes(List<Instituicao> instituicoes) {
        this.instituicoes = instituicoes;
    }

    public UnidadeDeLotacao getUnidadeDeLotacao() {
        return unidadeDeLotacao;
    }

    public void setUnidadeDeLotacao(UnidadeDeLotacao unidadeDeLotacao) {
        this.unidadeDeLotacao = unidadeDeLotacao;
    }

    public List<UnidadeDeLotacao> getUnidadesDeLotacao() {
        return unidadesDeLotacao;
    }

    public void setUnidadesDeLotacao(List<UnidadeDeLotacao> unidadesDeLotacao) {
        this.unidadesDeLotacao = unidadesDeLotacao;
    }

    public PontoFocal getPontoFocal() {
        return pontoFocal;
    }

    public void setPontoFocal(PontoFocal pontoFocal) {
        this.pontoFocal = pontoFocal;
    }

}
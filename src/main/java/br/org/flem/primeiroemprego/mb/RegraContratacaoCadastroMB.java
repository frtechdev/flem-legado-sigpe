/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.AcaoDAO;
import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dao.EgressoListaDAO;
import br.org.flem.primeiroemprego.dao.FormacaoDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.dao.RegraContratacaoDAO;
import br.org.flem.primeiroemprego.dao.TipoDeAcaoDAO;
import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Formacao;
import br.org.flem.primeiroemprego.entity.ListaEgressoDTO;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.RegraContratacao;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AJLima
 */
@ManagedBean
@ViewScoped
public class RegraContratacaoCadastroMB implements Serializable{
    
    
    
    @ManagedProperty(value="#{regraContratacaoDAO}")
    private RegraContratacaoDAO regraContratacaoDAO;
    
    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    
    @ManagedProperty(value="#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;
    
    @ManagedProperty(value = "#{formacaoDAO}")
    private FormacaoDAO formacaoDAO;
    
    @ManagedProperty(value = "#{egressoDAO}")
    private EgressoDAO egressoDAO;
    
    @ManagedProperty(value = "#{acaoDAO}")
    private AcaoDAO acaoDAO;
    
    @ManagedProperty(value = "#{tipoDeAcaoDAO}")
    private TipoDeAcaoDAO tipoDeAcaoDAO;
    

    
    @ManagedProperty(value="#{egressoListaDAO}")
    private EgressoListaDAO egressoListaDAO;
    
    private List<Formacao> formacoes;
    
    private List<Demandante> demandantes;
    
    private List<Municipio> municipios;
    
    private RegraContratacao regra;
    
    private List<Egresso> egressos;
    
    private List<Egresso> egressosSelecionados = new ArrayList<Egresso>();
    
    private Boolean ativo;
            
    
    
    
    @PostConstruct
    public void init()  {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            regra = regraContratacaoDAO.obterPorPK(Long.parseLong(id));
            ativo = regra.getAtivo();
        } else {
            regra = new RegraContratacao();
        }
        
        formacoes = formacaoDAO.obterLista();
        demandantes = demandanteDAO.obterLista();
        municipios =  municipioDAO.obterLista();
        egressos = egressoDAO.obterEgressosCategoriasDefinidas();
  
    }


    
    
    public String salvar(){
        
        if (regra.getId() != null) {
            
            try{
                if(regra.getAtivo() != ativo){
                    if(regra.getAtivo()){
                        this.ativarRegra();
                    } else {
                        this.desativarRegra();
                    }
                }
                regraContratacaoDAO.alterar(regra);
                Mensagem.lancar("Regra alterada com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a regra");
            }
        } else {
            try{
                
                this.ativarRegra();
                regraContratacaoDAO.inserir(regra);
                Mensagem.lancar("Regra inserida com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir a regra");
            }
        }
        return "lista.xhtml";
    }

    public String desativarRegra() throws Exception{
       
        if(!regra.getAtivo()){
            if(this.verificarEgressos().size() > 0){
                this.criarRevogacoesRegras();
            }
        }
        return "lista.xhtml";
    }

    public String ativarRegra() throws Exception{
        if(this.verificarEgressos().size() > 0){
            this.criarAcoesdasRegras();
        }
        return "lista.xhtml";
    }

    public String excluir(){
        try {
            regraContratacaoDAO.excluir(regra);
            Mensagem.lancar("regra excluida com sucesso.");
            return"lista.xhtml";
        } catch (Exception e) {
            e.printStackTrace();
            Mensagem.lancar("Erro ao excluir uma regra.");
        }
        return "lista.xhtml";
    } 

    public List<Egresso> verificarEgressos() throws Exception{
        
        for(Egresso e : egressos){
                if(e.getFormacao() != null){
                    if(regra.getMunicipio() == null && regra.getFormacao() == null){
                        if( e.getVaga().getDemandante().getId() == regra.getDemandante().getId()  ){
                                egressosSelecionados.add(e);
                        }
                    } else if( regra.getMunicipio() == null){
                        if( e.getFormacao().getId() == regra.getFormacao().getId() &&
                                e.getVaga().getDemandante().getId() == regra.getDemandante().getId()  ){
                                egressosSelecionados.add(e);
                        }
                    }else if( regra.getFormacao() == null){
                        if( e.getMunicipio().getId() == regra.getMunicipio().getId() &&
                                e.getVaga().getDemandante().getId() == regra.getDemandante().getId()  ){
                                egressosSelecionados.add(e);
                        }
                    }
                    else{
                        if(e.getFormacao().getId() == regra.getFormacao().getId() &&
                                e.getVaga().getDemandante().getId() == regra.getDemandante().getId() &&
                                e.getVaga().getMunicipio().getNome().equals(regra.getMunicipio().getNome()) ){
                            egressosSelecionados.add(e);
                        }
                      }
                }   
        }
        return egressosSelecionados;
    }
    
    public void criarAcoesdasRegras() throws Exception{
        for(Egresso e : egressosSelecionados){
            Acao a = new Acao();
            a.setEgresso(e);
            a.setTipoDeAcao(tipoDeAcaoDAO.ativarRegra());                                                                                 
            a.setData(new Date());
            if(regra.getMunicipio()  == null && regra.getFormacao() == null){
                a.setDescricao("Usuário se enquadra na regra de exceção :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+" Todas as formações "+"-"+ " Todos os Municípios");
            }else if(regra.getMunicipio()  == null ){
                a.setDescricao("Usuário se enquadra na regra de exceção :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+regra.getFormacao().getNome()+"-"+" Todos os Municípios");
            }else if(regra.getFormacao()== null ){
                a.setDescricao("Usuário se enquadra na regra de exceção :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+"Todas as fotmações"+"-"+regra.getMunicipio().getNome());
            }else if(regra.getMunicipio()  != null && regra.getFormacao() != null ){
                a.setDescricao("Usuário se enquadra na regra de exceção :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+regra.getFormacao().getNome()+"-"+regra.getMunicipio().getNome());
            }
            acaoDAO.inserir(a);
        }
    }
    
    public void criarRevogacoesRegras() throws Exception{
        for(Egresso e : egressosSelecionados){
            Acao a = new Acao();
            a.setEgresso(e);
            a.setTipoDeAcao(tipoDeAcaoDAO.desativarregra());
            a.setData(new Date());
            if(regra.getMunicipio()  == null && regra.getFormacao() == null){
                a.setDescricao("Exceção revogada. Usuário pode ser contratado :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+" Todas as formações "+"-"+ " Todos os Municípios");
            }else if(regra.getMunicipio()  == null ){
                a.setDescricao("Exceção revogada. Usuário pode ser contratado :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+regra.getFormacao().getNome()+"-"+" Todos os Municípios");
            }else if(regra.getFormacao()== null ){
                a.setDescricao("Exceção revogada. Usuário pode ser contratado :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+"Todas as fotmações"+"-"+regra.getMunicipio().getNome());
            }else if(regra.getMunicipio()  != null && regra.getFormacao() != null ){
                a.setDescricao("Exceção revogada. Usuário pode ser contratado :"+tipoDeAcaoDAO.desativarregra().getNome()+"-"+regra.getDemandante().getNome()+"-"+regra.getFormacao().getNome()+"-"+regra.getMunicipio().getNome());
            }
            acaoDAO.inserir(a);
        }
    }
    
    
    
    
    public RegraContratacaoDAO getRegraContratacaoDAO() {
        return regraContratacaoDAO;
    }

    public void setRegraContratacaoDAO(RegraContratacaoDAO regraContratacaoDAO) {
        this.regraContratacaoDAO = regraContratacaoDAO;
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
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

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public RegraContratacao getRegra() {
        return regra;
    }

    public void setRegra(RegraContratacao regra) {
        this.regra = regra;
    }

    public FormacaoDAO getFormacaoDAO() {
        return formacaoDAO;
    }

    public void setFormacaoDAO(FormacaoDAO formacaoDAO) {
        this.formacaoDAO = formacaoDAO;
    }

    public List<Formacao> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(List<Formacao> formacoes) {
        this.formacoes = formacoes;
    }

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public AcaoDAO getAcaoDAO() {
        return acaoDAO;
    }

    public void setAcaoDAO(AcaoDAO acaoDAO) {
        this.acaoDAO = acaoDAO;
    }

    public TipoDeAcaoDAO getTipoDeAcaoDAO() {
        return tipoDeAcaoDAO;
    }

    public void setTipoDeAcaoDAO(TipoDeAcaoDAO tipoDeAcaoDAO) {
        this.tipoDeAcaoDAO = tipoDeAcaoDAO;
    }
    
    public EgressoListaDAO getEgressoListaDAO() {
        return egressoListaDAO;
    }

    public void setEgressoListaDAO(EgressoListaDAO egressoListaDAO) {
        this.egressoListaDAO = egressoListaDAO;
    }

    public List<Egresso> getEgressos() {
        return egressos;
    }

    public void setEgressos(List<Egresso> egressos) {
        this.egressos = egressos;
    }

    public List<Egresso> getEgressosSelecionados() {
        return egressosSelecionados;
    }

    public void setEgressosSelecionados(List<Egresso> egressosSelecionados) {
        this.egressosSelecionados = egressosSelecionados;
    }


    
    
    
    
    
    
}

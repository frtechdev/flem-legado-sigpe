package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ColaboradorDAO;
import br.org.flem.primeiroemprego.dao.EscritorioRegionalDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.entity.Colaborador;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
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
public class EscritorioRegionalCadastroMB implements Serializable {
    
    @ManagedProperty(value = "#{escritorioRegionalDAO}")
    private EscritorioRegionalDAO escritorioRegionalDAO;
    
    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    
    @ManagedProperty(value = "#{colaboradorDAO}")
    private ColaboradorDAO colaboradorDAO;
    
    private List<Municipio> municipios = new ArrayList<>();
    private List<Colaborador> colaboradores = new ArrayList<>();
    
    private List<Municipio> municipiosSelecionados = new ArrayList<>();
    private List<Colaborador> colaboradoresSelecionados = new ArrayList<>();
    
    private EscritorioRegional obj = new EscritorioRegional();
    
    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        obj = new EscritorioRegional();
        carregarColaboradores();
        carregarMunicipios();
        
        if(id != null){
            Long idNumber = Long.parseLong(id);
            obj = this.obterPorPK(idNumber);
            if(obj != null){
                municipiosSelecionados.addAll(obj.getMunicipios());
                colaboradoresSelecionados.addAll(colaboradorDAO.obterPorEscritorioRegional(obj));
            }       
        }
    }

    public void inserir() {
        try {
            preInsert();
            inserirOuAtualizar();
            atualizarColaboradores();
            atualizarMunicipios();
            Mensagem.lancar("Salvo com sucesso");
        } catch (Exception ex) {
            Logger.getLogger(EscritorioRegionalCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.lancarMensagemErro("Erro ao cadastrar: "+ex.getMessage());
        }
    }
    private void preInsert() throws BusinessException{
        if(municipiosSelecionados == null || municipiosSelecionados.isEmpty()){
            throw new BusinessException("Pelo menos um municipio deve ser associado");
        }
    }
    private void inserirOuAtualizar() throws Exception{
        if(obj.getId() == null){
            escritorioRegionalDAO.inserir(obj);
        }else{
            escritorioRegionalDAO.alterar(obj);
        }
    }
    public EscritorioRegional obterPorPK(Long id) {
        try {
            return escritorioRegionalDAO.obterPorPK(id);
        } catch (Exception ex) {
            Logger.getLogger(EscritorioRegionalCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void carregarColaboradores(){
        colaboradores = colaboradorDAO.obterListaAtivosOuInativos(true);
    }
    
    private void atualizarColaboradores() throws Exception{
        if(obj != null && obj.getId() != null){
            List<Colaborador> lista = colaboradorDAO.obterPorEscritorioRegional(obj);
            if( !lista.isEmpty() ){
                if( colaboradoresSelecionados.isEmpty() ){
                    for( Colaborador colaborador : lista ){
                        removerColaborador(colaborador);
                    }
                }else{
                    for( Colaborador colaborador : lista ){
                        if(!colaboradoresSelecionados.contains(colaborador)){
                            removerColaborador(colaborador); 
                        }
                    }
                    for( Colaborador colaborador : colaboradoresSelecionados ){
                        if(!lista.contains(colaborador)){
                            atualizarColaborador(colaborador);
                        }
                    }
                }
            }else if(!colaboradoresSelecionados.isEmpty()){
                for( Colaborador colaborador : colaboradoresSelecionados){
                    atualizarColaborador(colaborador); 
                }
            }
        }
    }
    
    private void atualizarColaborador(Colaborador item) throws Exception{
        item.setEscritorioRegional(obj);
        colaboradorDAO.alterar(item);
    }
    private void removerColaborador(Colaborador item) throws Exception{
        item.setEscritorioRegional(null);
        colaboradorDAO.alterar(item);
    }
    
    private void carregarMunicipios(){
        municipios = municipioDAO.obterLista();
    }
        
    private void atualizarMunicipios() throws Exception{
        if(obj != null && obj.getId() != null){
            List<Municipio> lista = municipioDAO.obterPorEscritorioRegional(obj);
            if( !lista.isEmpty() ){
                if( municipiosSelecionados.isEmpty() ){
                    for( Municipio municipioEscritorio : lista ){
                        removerMunicipio(municipioEscritorio);
                    }
                }else{
                    for( Municipio municipioEscritorio : lista ){
                        if(!municipiosSelecionados.contains(municipioEscritorio)){
                            removerMunicipio(municipioEscritorio); 
                        }
                    }
                    for( Municipio municipioEscritorio : municipiosSelecionados ){
                        if(!lista.contains(municipioEscritorio)){
                            atualizarMunicipio(municipioEscritorio);
                        }
                    }
                }
            }else if(!municipiosSelecionados.isEmpty()){
                for( Municipio municipioEscritorio : municipiosSelecionados ){
                    atualizarMunicipio(municipioEscritorio); 
                }
            }
        }
    }
    
    private void atualizarMunicipio(Municipio item) throws Exception{
        item.setEscritorioRegional(obj);
        municipioDAO.alterar(item);
    }
    private void removerMunicipio(Municipio item) throws Exception{
        item.setEscritorioRegional(null);
        municipioDAO.alterar(item);
    }
    
    //Getters e Setters
    public EscritorioRegionalDAO getEscritorioRegionalDAO() {
        return escritorioRegionalDAO;
    }

    public void setEscritorioRegionalDAO(EscritorioRegionalDAO escritorioRegionalDAO) {
        this.escritorioRegionalDAO = escritorioRegionalDAO;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public ColaboradorDAO getColaboradorDAO() {
        return colaboradorDAO;
    }

    public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
        this.colaboradorDAO = colaboradorDAO;
    }

    public List<Municipio> getMunicipiosSelecionados() {
        return municipiosSelecionados;
    }

    public void setMunicipiosSelecionados(List<Municipio> municipiosSelecionados) {
        this.municipiosSelecionados = municipiosSelecionados;
    }

    public List<Colaborador> getColaboradoresSelecionados() {
        return colaboradoresSelecionados;
    }

    public void setColaboradoresSelecionados(List<Colaborador> colaboradoresSelecionados) {
        this.colaboradoresSelecionados = colaboradoresSelecionados;
    }

    public EscritorioRegional getObj() {
        return obj;
    }

    public void setObj(EscritorioRegional obj) {
        this.obj = obj;
    }
}
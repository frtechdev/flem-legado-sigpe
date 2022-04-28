package br.org.flem.primeiroemprego.mb;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.persistencia.dto.SituacaoFuncionarioEnum;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.dao.CIDAO;
import br.org.flem.primeiroemprego.dao.CategoriaDaSituacaoDAO;
import br.org.flem.primeiroemprego.dao.DeficienciaDAO;
import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dao.EgressoListaDAO;
import br.org.flem.primeiroemprego.dao.EixoDeFormacaoDAO;
import br.org.flem.primeiroemprego.dao.EventoDAO;
import br.org.flem.primeiroemprego.dao.FormacaoDAO;
import br.org.flem.primeiroemprego.dao.ListaDAO;
import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.dao.SituacaoDAO;
import br.org.flem.primeiroemprego.entity.CI;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Deficiencia;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EgressoLista;
import br.org.flem.primeiroemprego.entity.EixoDeFormacao;
import br.org.flem.primeiroemprego.entity.EstadoColunasEgressoDTO;
import br.org.flem.primeiroemprego.entity.Evento;
import br.org.flem.primeiroemprego.entity.EventoEgresso;
import br.org.flem.primeiroemprego.entity.Formacao;
import br.org.flem.primeiroemprego.entity.Lista;
import br.org.flem.primeiroemprego.entity.ListaEgressoDTO;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.RacaCor;
import br.org.flem.primeiroemprego.entity.Situacao;
import br.org.flem.primeiroemprego.entity.UID;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

/**
 *
 * @author AJLima
 */
@ManagedBean
@ViewScoped
public class EgressoListaMB implements Serializable {

    @ManagedProperty(value="#{egressoDAO}")
    private EgressoDAO egressoDAO;
    
    @ManagedProperty(value="#{listaDAO}")
    private ListaDAO listaDAO;
    
    @ManagedProperty(value="#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    
    @ManagedProperty(value="#{categoriaDaSituacaoDAO}")
    private CategoriaDaSituacaoDAO categoriaDaSituacaoDAO;
    
    @ManagedProperty(value="#{situacaoDAO}")
    private SituacaoDAO situacaoDAO;
    
    @ManagedProperty(value="#{eixoDeFormacaoDAO}")
    private EixoDeFormacaoDAO eixoDeFormacaoDAO;
    
    @ManagedProperty(value="#{formacaoDAO}")
    private FormacaoDAO formacaoDAO;
    
    @ManagedProperty(value="#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;

    @ManagedProperty(value="#{egressoListaDAO}")
    private EgressoListaDAO egressoListaDAO;
    
    @ManagedProperty(value="#{eventoDAO}")
    private EventoDAO eventoDAO;

    @ManagedProperty(value="#{deficienciaDAO}")
    private DeficienciaDAO deficienciaDAO;
    
    @ManagedProperty(value="#{modeloDeOficioDAO}")
    private ModeloDeOficioDAO modeloDeOficioDAO;

    @ManagedProperty(value="#{cIDAO}")
    private CIDAO ciDAO;

    private List<Lista> listas;
    private List<Evento> eventos;
    private List<ModeloDeOficio> modeloDeOficiosNaoGerados;
    private List<CI> cis;

    private ListaEgressoDTO egresso;

    private List<ListaEgressoDTO> egressos;

    private List<ListaEgressoDTO> egressosSelecionados;

    private List<ListaEgressoDTO> egressosFiltrados;

    private Lista lista;

    private String novaLista;

    private List<Municipio> municipios;

    private List<Situacao> situacoes;
    
    private List<CategoriaDaSituacao> categorias;
    
    private List<Formacao> formacoes;
    
    private List<EixoDeFormacao> eixosDeFormacao;
    
    private List<Demandante> demandantes;
    
    private List<Deficiencia> deficiencias;
    
    private ModeloDeOficio modeloDeOficio;
    
    private CI ci;

    @ManagedProperty(value="#{estadoColunasEgressoDTO}")
    private EstadoColunasEgressoDTO estadoDasColunas;

    @PostConstruct
    public void init() {
        final Map<Integer,Funcionario> mapFuncs = new HashMap<>();
        egressos = egressoDAO.obterListaEgressoDTO();
        listas = listaDAO.obterLista();
        municipios = municipioDAO.obterLista();
        situacoes = situacaoDAO.obterLista();
        categorias = categoriaDaSituacaoDAO.obterLista();
        eixosDeFormacao = eixoDeFormacaoDAO.obterLista();
        formacoes = formacaoDAO.obterLista();
        demandantes = demandanteDAO.obterLista();
        eventos = eventoDAO.obterLista();
        deficiencias = deficienciaDAO.obterLista();
        List<Funcionario> func = new RHServico().obterFuncionariosPorDepartamentoSituacao(PropertiesUtil.getProperty("departamentoPrimeiroEmprego"), null);
        for(Funcionario f : func){
            mapFuncs.put(f.getCodigoDominio(), f);
        }
        modeloDeOficiosNaoGerados = modeloDeOficioDAO.naoGerados();
        cis = ciDAO.obterNaoFechadas();
        IFuncionario f;
        for(ListaEgressoDTO e : egressos){
            if(e.getMatriculaFlem()!= null){
                f = mapFuncs.get(Integer.parseInt(e.getMatriculaFlem()));
                if(f != null ){
                    if(f.getSituacao() != null){
                        e.setAdmissao(f.getAdmissao());
                        e.setSituacaoFLEM(f.getSituacao().getDescricao());
                        e.setRescisao(f.getRescisao());
                    } else{
                        e.setAdmissao(f.getAdmissao());
                        e.setRescisao(f.getRescisao());
                    }
                }
            }
        }
        egressosSelecionados = new ArrayList<>();
        egressosFiltrados = new ArrayList<>();
        egressosFiltrados.addAll(egressos);
    }

    public boolean filtrarMultiplasOpcoes(Object listOpcoes, Object listSelecionados, Locale locale) {
        boolean contem = true;
        if(listSelecionados != null){
            for(String selecionado : (String[])listSelecionados){
                contem = false;
                for(String opcao : (List<String>)listOpcoes){
                    if(opcao.equals(selecionado)){
                        contem = true;
                        break;
                    }
                }
                if(!contem){
                    return false;
                }
            }
        }
        return contem;
    }
    
    public boolean filtrarMultiplasOpcoesUID(Object listIds, Object filter, Locale locale) {
        boolean contem = true;
        if(filter != null){
            for(String id : (String[])filter){
                contem = false;
                for(UID uid : (List<UID>)listIds){
                    if(uid.getId().equals(Long.parseLong(id))){
                        contem = true;
                        break;
                    }
                }
                if(!contem){
                    return false;
                }
            }
        }
        return contem;
    }

    public String emUmaLinha(List<String> lista){
        StringBuilder st = new StringBuilder();
        String l;
        if(lista != null){
            for(int i =0; i < lista.size(); i++){
                l = lista.get(i);
                st.append(l);
                if(i < lista.size()-1){
                    st.append("<br>");
                }
            }
        }
        return st.toString();
    }

    public String listasEmUmaLinha(Egresso egresso){
        StringBuilder st = new StringBuilder();
        if(egresso != null && egresso.getEstadoNasListas() != null && !egresso.getEstadoNasListas().isEmpty()){
            EgressoLista l;
            for(int i =0; i < egresso.getEstadoNasListas().size(); i++){
                l = egresso.getEstadoNasListas().get(i);
                st.append(l.getLista().getNome());
                if(i < egresso.getEstadoNasListas().size()-1){
                    st.append("<br>");
                }
            }
        }
        return st.toString();
    }
    

    public String eventosEmUmaLinha(Egresso egresso){
        StringBuilder st = new StringBuilder();
        if(egresso != null && egresso.getEventosParticipados() != null && !egresso.getEventosParticipados().isEmpty()){
            EventoEgresso e;
            for(int i =0; i < egresso.getEventosParticipados().size(); i++){
                e =  egresso.getEventosParticipados().get(i);
                st.append(e.getId().getEvento().getNome()).append(" ").append(Data.formataData(e.getId().getEvento().getData(), "dd/MM/yyyy HH:mm"));
                if(i < egresso.getEventosParticipados().size()-1){
                    st.append("<br>");
                }
            }
        }
        return st.toString();
    }

    public void incluirEgressosNaLista(){
        try{
            for(ListaEgressoDTO e : egressosSelecionados){
                if(!e.getListas().contains(lista.getNome())){
                    EgressoLista el = new EgressoLista();
                    el.setEgresso(egressoDAO.obterPorPK(e.getIdEgresso()));
                    el.setLista(lista);

                    e.getListas().add(el.getLista().getNome());

                    egressoListaDAO.inserir(el);
                }
            }
            Mensagem.lancar("Egressos incluídos na Lista");
        }catch(Exception e){
            Mensagem.lancarMensagemErro("Erro ao incluir Egressos na Lista");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void incluirEgressosNoOficio(){
        if(modeloDeOficio != null){
            if(modeloDeOficio.getStatus().equals(ModeloDeOficio.Status.SALVO)){
                try{
                    ModeloDeOficio m = modeloDeOficioDAO.obterPorPK(modeloDeOficio.getId());//Para evitar lazyinitialization abaixo
                    egressosSelecionados.removeAll(m.getEgressosParaGerar());//Impedindo que os egressos sejam incluidos novamente
                    List<Long> ids = new ArrayList<>();
                    for(ListaEgressoDTO e : egressosSelecionados){
                        ids.add(e.getIdEgresso());
                    }
                    m.getEgressosParaGerar().addAll(egressoDAO.obterPorID(ids));
                    modeloDeOficioDAO.alterar(m);

                    Mensagem.lancarMensagemErro("Egressos incluídos no modelo de ofício");
                }catch(Exception e){
                    Mensagem.lancarMensagemErro("Erro ao incluir egressos no modelo de ofício");
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                }
            }else{
                Mensagem.lancarMensagemWarn("Só é possivel incluir egressos em modelo de ofício com status SALVO");
            }
        }else{
            Mensagem.lancarMensagemErro("Nenhum modelo de ofício informado");
        }

    }
    
    public void incluirEgressosNaCI(){
        if(ci != null){
            try{
                egressosSelecionados.removeAll(ci.getEgressos());//Impedindo que os egressos sejam incluidos novamente
                List<Long> ids = new ArrayList<>();
                    for(ListaEgressoDTO e : egressosSelecionados){
                        ids.add(e.getIdEgresso());
                    }
                ci.getEgressos().addAll(egressoDAO.obterPorID(ids));
                ciDAO.alterar(ci);

                Mensagem.lancar("Egressos incluídos na CI");
            }catch(Exception e){
                Mensagem.lancarMensagemErro("Erro ao incluir egressos na CI");
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            }
        }else{
            Mensagem.lancarMensagemErro("Nenhuma CI informada");
        }

    }

    public void alternarSelecaoDeTodasAsPaginas(){
        if(egressosSelecionados.size() != egressosFiltrados.size()){
            egressosSelecionados.clear();
            egressosSelecionados.addAll(egressosFiltrados);
        }else{
            egressosSelecionados.clear();
        }
    }
    
    public RacaCor[] racasCor(){
        return RacaCor.values();
    }

    public List<String> obterRespostas(String campo){
        return egressoDAO.obterRespostasDoCampo(campo);
    }
    
    public List<String> obterRespostasDaVaga(String campo){
        return egressoDAO.obterRespostasDaVaga(campo);
    }
    
    public void onToggle(ToggleEvent e) {
        estadoDasColunas.set((Integer)e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }
    
    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public ListaEgressoDTO getEgresso() {
        return egresso;
    }

    public void setEgresso(ListaEgressoDTO egresso) {
        this.egresso = egresso;
    }

    public List<ListaEgressoDTO> getEgressos() {
        return egressos;
    }

    public void setEgressos(List<ListaEgressoDTO> egressos) {
        this.egressos = egressos;
    }

    public ListaDAO getListaDAO() {
        return listaDAO;
    }

    public void setListaDAO(ListaDAO listaDAO) {
        this.listaDAO = listaDAO;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

    public EstadoColunasEgressoDTO getEstadoDasColunas() {
        return estadoDasColunas;
    }

    public void setEstadoDasColunas(EstadoColunasEgressoDTO estadoDasColunas) {
        this.estadoDasColunas = estadoDasColunas;
    }

    public EgressoListaDAO getEgressoListaDAO() {
        return egressoListaDAO;
    }

    public void setEgressoListaDAO(EgressoListaDAO egressoListaDAO) {
        this.egressoListaDAO = egressoListaDAO;
    }

    public List<ListaEgressoDTO> getEgressosSelecionados() {
        return egressosSelecionados;
    }

    public void setEgressosSelecionados(List<ListaEgressoDTO> egressosSelecionados) {
        this.egressosSelecionados = egressosSelecionados;
    }

    public List<ListaEgressoDTO> getEgressosFiltrados() {
        return egressosFiltrados;
    }

    public void setEgressosFiltrados(List<ListaEgressoDTO> egressosFiltrados) {
        this.egressosFiltrados = egressosFiltrados;
    }
    
    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public String getNovaLista() {
        return novaLista;
    }

    public void setNovaLista(String novaLista) {
        this.novaLista = novaLista;
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public SituacaoFuncionarioEnum[] situacoesFLEM(){
        return SituacaoFuncionarioEnum.values();
    }

    public SituacaoDAO getSituacaoDAO() {
        return situacaoDAO;
    }

    public void setSituacaoDAO(SituacaoDAO situacaoDAO) {
        this.situacaoDAO = situacaoDAO;
    }

    public List<Situacao> getSituacoes() {
        return situacoes;
    }

    public void setSituacoes(List<Situacao> situacoes) {
        this.situacoes = situacoes;
    }

    public CategoriaDaSituacaoDAO getCategoriaDaSituacaoDAO() {
        return categoriaDaSituacaoDAO;
    }

    public void setCategoriaDaSituacaoDAO(CategoriaDaSituacaoDAO categoriaDaSituacaoDAO) {
        this.categoriaDaSituacaoDAO = categoriaDaSituacaoDAO;
    }

    public List<CategoriaDaSituacao> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDaSituacao> categorias) {
        this.categorias = categorias;
    }

    public EixoDeFormacaoDAO getEixoDeFormacaoDAO() {
        return eixoDeFormacaoDAO;
    }

    public void setEixoDeFormacaoDAO(EixoDeFormacaoDAO eixoDeFormacaoDAO) {
        this.eixoDeFormacaoDAO = eixoDeFormacaoDAO;
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

    public List<EixoDeFormacao> getEixosDeFormacao() {
        return eixosDeFormacao;
    }

    public void setEixosDeFormacao(List<EixoDeFormacao> eixosDeFormacao) {
        this.eixosDeFormacao = eixosDeFormacao;
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

    public EventoDAO getEventoDAO() {
        return eventoDAO;
    }

    public void setEventoDAO(EventoDAO eventoDAO) {
        this.eventoDAO = eventoDAO;
    }

    public List<Evento> getEventos() {
        return eventos;
    }

    public void setEventos(List<Evento> eventos) {
        this.eventos = eventos;
    }

    public DeficienciaDAO getDeficienciaDAO() {
        return deficienciaDAO;
    }

    public void setDeficienciaDAO(DeficienciaDAO deficienciaDAO) {
        this.deficienciaDAO = deficienciaDAO;
    }

    public List<Deficiencia> getDeficiencias() {
        return deficiencias;
    }

    public void setDeficiencias(List<Deficiencia> deficiencias) {
        this.deficiencias = deficiencias;
    }

    public ModeloDeOficioDAO getModeloDeOficioDAO() {
        return modeloDeOficioDAO;
    }

    public void setModeloDeOficioDAO(ModeloDeOficioDAO modeloDeOficioDAO) {
        this.modeloDeOficioDAO = modeloDeOficioDAO;
    }

    public List<ModeloDeOficio> getModeloDeOficiosNaoGerados() {
        return modeloDeOficiosNaoGerados;
    }

    public void setModeloDeOficiosNaoGerados(List<ModeloDeOficio> modeloDeOficiosNaoGerados) {
        this.modeloDeOficiosNaoGerados = modeloDeOficiosNaoGerados;
    }

    public ModeloDeOficio getModeloDeOficio() {
        return modeloDeOficio;
    }

    public void setModeloDeOficio(ModeloDeOficio modeloDeOficio) {
        this.modeloDeOficio = modeloDeOficio;
    }

    public CIDAO getCiDAO() {
        return ciDAO;
    }

    public void setCiDAO(CIDAO ciDAO) {
        this.ciDAO = ciDAO;
    }

    public List<CI> getCis() {
        return cis;
    }

    public void setCis(List<CI> cis) {
        this.cis = cis;
    }

    public CI getCi() {
        return ci;
    }

    public void setCi(CI ci) {
        this.ci = ci;
    }

}

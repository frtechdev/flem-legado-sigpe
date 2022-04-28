package br.org.flem.primeiroemprego.mb;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.persistencia.dto.SituacaoFuncionarioEnum;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.util.Data;
import br.org.flem.fwe.util.StringUtil;
import br.org.flem.primeiroemprego.dao.AcaoDAO;
import br.org.flem.primeiroemprego.dao.CIDAO;
import br.org.flem.primeiroemprego.dao.CampanhaDAO;
import br.org.flem.primeiroemprego.dao.DeficienciaDAO;
import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dao.EgressoListaDAO;
import br.org.flem.primeiroemprego.dao.EnvioDeEmailDAO;
import br.org.flem.primeiroemprego.dao.EventoDAO;
import br.org.flem.primeiroemprego.dao.EventoEgressoDAO;
import br.org.flem.primeiroemprego.dao.FormacaoDAO;
import br.org.flem.primeiroemprego.dao.ListaDAO;
import br.org.flem.primeiroemprego.dao.MaterialDAO;
import br.org.flem.primeiroemprego.dao.MaterialEgressoDAO;
import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.dao.ParametroDAO;
import br.org.flem.primeiroemprego.dao.SituacaoDAO;
import br.org.flem.primeiroemprego.dao.TamanhoDAO;
import br.org.flem.primeiroemprego.dao.TipoDeAcaoDAO;
import br.org.flem.primeiroemprego.dao.UnidadeDeMedidaDAO;
import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.CI;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.Deficiencia;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EgressoLista;
import br.org.flem.primeiroemprego.entity.EnumModalidadeEnsino;
import br.org.flem.primeiroemprego.entity.EnumSituacaoEvento;
import br.org.flem.primeiroemprego.entity.EnumTipoInstituicaoEnsino;
import br.org.flem.primeiroemprego.entity.EnvioDeEmail;
import br.org.flem.primeiroemprego.entity.Evento;
import br.org.flem.primeiroemprego.entity.EventoEgresso;
import br.org.flem.primeiroemprego.entity.Formacao;
import br.org.flem.primeiroemprego.entity.Lista;
import br.org.flem.primeiroemprego.entity.Material;
import br.org.flem.primeiroemprego.entity.MaterialEgresso;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.RacaCor;
import br.org.flem.primeiroemprego.entity.Situacao;
import br.org.flem.primeiroemprego.entity.Tamanho;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
import br.org.flem.primeiroemprego.entity.UnidadeDeMedida;
import br.org.flem.primeiroemprego.entity.Vaga;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.negocio.EgressoRN;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.entity.CamposEgressosUtil;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.text.MaskFormatter;
import org.alfredlibrary.validadores.CPF;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CargaMB extends BaseMB implements Serializable {

    @ManagedProperty(value = "#{egressoDAO}")
    private EgressoDAO egressoDAO;
    
    @ManagedProperty(value = "#{egressoRN}")
    private EgressoRN egressoRN;

    @ManagedProperty(value = "#{situacaoDAO}")
    private SituacaoDAO situacaoDAO;

    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;

    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;

    @ManagedProperty(value = "#{formacaoDAO}")
    private FormacaoDAO formacaoDAO;

    @ManagedProperty(value = "#{listaDAO}")
    private ListaDAO listaDAO;

    @ManagedProperty(value = "#{eventoDAO}")
    private EventoDAO eventoDAO;

    @ManagedProperty(value = "#{egressoListaDAO}")
    private EgressoListaDAO egressoListaDAO;

    @ManagedProperty(value = "#{deficienciaDAO}")
    private DeficienciaDAO deficienciaDAO;

    @ManagedProperty(value = "#{campanhaDAO}")
    private CampanhaDAO campanhaDAO;

    @ManagedProperty(value = "#{modeloDeOficioDAO}")
    private ModeloDeOficioDAO modeloDeOficioDAO;

    @ManagedProperty(value = "#{cIDAO}")
    private CIDAO ciDAO;
    @ManagedProperty(value = "#{parametroDAO}")
    private ParametroDAO parametroDAO;

    @ManagedProperty(value = "#{usuarioLogadoMB}")
    private UsuarioLogadoMB usuarioLogadoMB;
    @ManagedProperty(value = "#{materialEgressoDAO}")
    private MaterialEgressoDAO materialEgressoDAO;
    @ManagedProperty(value = "#{egressoCadastroMB}")
    private EgressoCadastroMB egressoCadastroMB;
    @ManagedProperty(value = "#{eventoEgressoDAO}")
    private EventoEgressoDAO eventoEgressoDAO;

    private UploadedFile arquivoXLS;

    private List<Egresso> egressos;

    private List<String> nomeColunas;

    private List<List<String>> linhasInseridas = new ArrayList<>();

    private List<List<String>> linhasErro = new ArrayList<>();

    private List<List<String>> linhasAtualizadas = new ArrayList<>();

    private List<Lista> listas;

    private List<Lista> listasSelecionadas;

    private List<Evento> eventos;

    private Evento eventoSelecionados;

    private List<Campanha> campanhasSalvas;

    private List<ModeloDeOficio> modelosNaoGerados;

    private List<CI> cis;

    private List<Campanha> campanhasSelecionadas;

    private List<ModeloDeOficio> modelosDeOficioSelecionados;

    private List<CI> cisSelecionadas;

    private List<Acao> acoesSelecionadas;

    private Boolean acao = false;
    private String acaoBeneficiario;

    private List<String> camposModelo;

    private Integer progresso = 0;
    private String mensagem = "";
    private Integer quantidade;

    @PostConstruct
    public void init() {
        listas = listaDAO.obterOrdenadoDataCriacao();
        eventos = eventoDAO.obterEventoOrdenadoPorDataDesc();
        campanhasSalvas = campanhaDAO.obterComStatusSalva();
        modelosNaoGerados = modeloDeOficioDAO.naoGerados();
        cis = ciDAO.obterNaoFechadas();
    }

    public List<String> camposPossiveis() {
        if (StringUtils.isNotEmpty(acaoBeneficiario) && acaoBeneficiario.equals("0")) {
            carregarCamposModelo(Material.class);
        } else if (StringUtils.isNotEmpty(acaoBeneficiario) && acaoBeneficiario.equals("1")) {
            carregarCamposModelo(Acao.class);
        } else {
            camposModelo = CamposEgressosUtil.camposPossiveis(Arrays.asList("CPF"));//O CPF já está fixo como primeira coluna na importação
        }
        return camposModelo;
    }

    public boolean cpfValido(String cpf) {
        return CPF.isValido(cpf);
    }

    public void carregar() {
        if (arquivoXLS != null) {
            if (StringUtils.isNotEmpty(acaoBeneficiario) && acaoBeneficiario.equals("0")) {
                importarMateriais();
            } else if (StringUtils.isNotEmpty(acaoBeneficiario) && acaoBeneficiario.equals("1")) {
                carregarAcao();
            } else {
                List<EnvioDeEmail> listaEnvio = new ArrayList<>();
                try {
                    linhasAtualizadas.clear();
                    linhasErro.clear();
                    linhasInseridas.clear();
                    DataFormatter formatador = new DataFormatter();

                    try (Workbook workbook = new XSSFWorkbook(arquivoXLS.getInputstream())) {
                        Sheet planilha = workbook.getSheetAt(0);

                        int indexLinha = 0;
                        Row row = planilha.getRow(indexLinha++);
                        nomeColunas = obterNomesDasColunas(row, formatador);

                        if (nomeColunas.get(0).toLowerCase().equals("cpf")) {
                            if (modelosDeOficioSelecionados != null && !modelosDeOficioSelecionados.isEmpty()) {
                                for (int i = 0; i < modelosDeOficioSelecionados.size(); i++) {
                                    modelosDeOficioSelecionados.set(i, modeloDeOficioDAO.obterPorPK(modelosDeOficioSelecionados.get(i).getId()));//Obtendo os objetos de ModeloDeOficio que estão na sessão do hibernate
                                    modelosDeOficioSelecionados.get(i).getEgressosParaGerar().size();
                                }
                            }
                            verificarCamposExistentes();

                            MaskFormatter mascaraCPF = new MaskFormatter("###.###.###-##");
                            mascaraCPF.setValueContainsLiteralCharacters(false);

                            List<String> colunas = new ArrayList<>();
                            row = planilha.getRow(indexLinha++);
//                        quantidade = planilha.getLastRowNum();
//                        resetarProgresso();
                            while (row != null) {
                                try {
                                    colunas = obterLinha(row, formatador);

                                    if (!colunas.isEmpty() && usuarioLogadoMB.temPermissao("primEmp.excelCompleto")) { // Só pode inserir ou atualizar egresso se tiver a permissão de importação completa
                                        String cpfNaoFormatado = "00000000000" + colunas.get(0).replace(".", "").replace("-", "");
                                        cpfNaoFormatado = cpfNaoFormatado.substring(cpfNaoFormatado.length() - 11);
                                        Egresso egresso = obterEgressoPorCpf(mascaraCPF.valueToString(cpfNaoFormatado), colunas);

                                        if (egresso == null) {
                                            egresso = new Egresso();
                                            egresso.setVaga(new Vaga());
                                            egresso.getVaga().setEgresso(egresso);
                                            egresso.setCpf(mascaraCPF.valueToString(cpfNaoFormatado));
                                        }
                                        adicionarInformacaoAoEgresso(egresso, colunas);
                                        addEgressosAEventos(egresso, eventoSelecionados, colunas);
                                        boolean inclusao = alterarEgresso(egresso, cpfNaoFormatado, PropertiesUtil.getProperty("departamentoPrimeiroEmprego"));
                                        addEgressosACampanha(egresso, campanhasSelecionadas);
                                        addEgressosAModeloDeOficio(egresso, modelosDeOficioSelecionados);
                                        addEgressosACis(egresso, cisSelecionadas);
                                        addEgressosAListas(egresso, listasSelecionadas);

                                        if (inclusao) {
                                            linhasInseridas.add(colunas);
                                        } else {
                                            linhasAtualizadas.add(colunas);
                                        }
                                    }
                                } catch (Exception e) {
                                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                                    colunas.add(e.getMessage());
                                    linhasErro.add(colunas);
                                }
//                            mensagem = "importados "+(indexLinha)+ " de "+quantidade;
//                                            atualizarProgresso(indexLinha);
                                row = planilha.getRow(indexLinha++);
                            }
                            if (campanhasSelecionadas != null && !campanhasSelecionadas.isEmpty()) {
                                for (Campanha campanha : campanhasSelecionadas) {
                                    Campanha old = campanhaDAO.obterPorPK(campanha.getId());
                                    if (old != null) {
                                        old.setEnvios(listaEnvio);
                                        campanhaDAO.alterar(old);
                                    }
                                }
                            }

                            if (linhasErro == null || linhasErro.isEmpty()) {
                                Mensagem.lancar("Importação concluída");
                            } else {
                                Mensagem.lancarMensagemErro("Foram encontrados erros durante a importação!");
                            }
                        } else {
                            Mensagem.lancarMensagemErro("Primeira coluna precisa ser o CPF");
                        }
                    }
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                    Mensagem.lancarMensagemErro("Erro ao proceder a importação");
                }
            }
        } else {
            Mensagem.lancarMensagemErro("Arquivo não informado ou inválido");
        }
    }

    private void adicionarInformacaoAoEgresso(Egresso egresso, List<String> colunas) throws Exception {
        for (int i = 1; i < nomeColunas.size(); i++) {
            String nameColumn = nomeColunas.get(i);
            if (StringUtils.isEmpty(nameColumn) || !nameColumn.equalsIgnoreCase("Participou do Evento?")) {
                CamposEgressosUtil.setInformacao(egresso, nameColumn, obterValorPorColuna(nameColumn, colunas.get(i)));
            }
        }
    }

    private void verificarCamposExistentes() throws Exception {
        for (int i = 1; i < nomeColunas.size(); i++) {
            if (!CamposEgressosUtil.contemCampo(nomeColunas.get(i))) {
                Mensagem.lancarMensagemErro("Coluna " + nomeColunas.get(i) + " não identificada, não é possível prosseguir com a importação");
                throw new BusinessException("Coluna " + nomeColunas.get(i) + " não identificada, não é possível prosseguir com a importação");
            }
        }
    }

    private boolean alterarEgresso(Egresso egresso, String cpf, String idDepartamento) throws Exception {
        boolean inclusao = true;
        if (egresso.getId() == null) {
            Departamento departamento = new RHServico().obterDepartamentoPorCodigo(idDepartamento);
            IFuncionario f = new RHServico().obterFuncionarioPorCPFSituacaoDepartamento(cpf, SituacaoFuncionarioEnum.ATIVO, departamento);
            if (f != null) {
                egresso.setMatriculaFlem(f.getMatricula().toString());
                egresso.setFuncionario(f);
            }
            egressoRN.inserir(egresso);
        } else if (egresso.getVaga() != null && !egresso.getVaga().getSituacao().getId().equals(29)) {
            egressoRN.alterar(egresso);
            inclusao = false;
        }
        return inclusao;
    }

    private void addEgressosAEventos(Egresso egresso, Evento eventoSelecionados, List<String> colunas) throws Exception {
        if (eventoSelecionados != null) {
            Evento evento = eventoSelecionados;
            EventoEgresso eventoEgresso = new EventoEgresso();
            eventoEgresso.setSituacao(obterEnumSituacaoEvento(colunas.get(1)));
            eventoEgresso.getId().setEvento(evento);
            eventoEgresso.getId().setEgresso(egresso);
            if (egresso.getEventosParticipados() == null) {
                egresso.setEventosParticipados(new ArrayList<>());
                egresso.getEventosParticipados().add(eventoEgresso);
            } else if (!egresso.getEventosParticipados().contains(eventoEgresso)) {
                egresso.getEventosParticipados().add(eventoEgresso);
            }
            EventoEgresso old = eventoEgressoDAO.obterPorPK(eventoEgresso.getId());
            if (old == null) {
                eventoEgressoDAO.inserir(eventoEgresso);
            } else {
                old.setSituacao(obterEnumSituacaoEvento(colunas.get(1)));
                eventoEgressoDAO.alterar(old);
            }

        }
    }

    private void addEgressosAListas(Egresso egresso, List<Lista> listasSelecionadas) throws Exception {
        if (listasSelecionadas != null && !listasSelecionadas.isEmpty() && usuarioLogadoMB.temPermissao("primEmp.excelCompleto")) { // Só pode adicionar em Lista se tiver a permissão de importação completa)
            for (Lista lista : listasSelecionadas) {
                if (!egressoListaDAO.existeEgressoLista(egresso.getEstadoNasListas(), lista)) {
                    EgressoLista el = new EgressoLista();
                    el.setEgresso(egresso);
                    el.setLista(lista);
                    egressoListaDAO.inserir(el);
                }
            }
        }
    }

    private void addEgressosACis(Egresso egresso, List<CI> cisSelecionadas) throws Exception {
        if (cisSelecionadas != null && !cisSelecionadas.isEmpty()) {
            for (CI ci : cisSelecionadas) {
                if (ci.getEgressos() != null & !ci.getEgressos().contains(egresso)) {
                    ci.getEgressos().add(egresso);
                    ciDAO.alterar(ci);
                }
            }
        }
    }

    private void addEgressosACampanha(Egresso egresso, List<Campanha> campanhasSelecionadas) throws Exception {
        if (campanhasSelecionadas != null && !campanhasSelecionadas.isEmpty()) {
            for (Campanha campanha : campanhasSelecionadas) {
                if (egresso != null) {
                    EnvioDeEmail envio = new EnvioDeEmailDAO().obterEnviosPorCampanhaEgresso(campanha, egresso);
                    if (envio == null) {
                        adicionarEgresso(egresso.getId(), campanha.getId());
                    }
                }
            }
        }
    }

    private void addEgressosAModeloDeOficio(Egresso egresso, List<ModeloDeOficio> modelosDeOficioSelecionados) throws Exception {
        if (modelosDeOficioSelecionados != null && !modelosDeOficioSelecionados.isEmpty()) {
            for (ModeloDeOficio modeloDeOficio : modelosDeOficioSelecionados) {
                if (modeloDeOficio.getEgressosParaGerar() != null && !modeloDeOficio.getEgressosParaGerar().contains(egresso)) {
                    ModeloDeOficio old = modeloDeOficioDAO.obterPorPK(modeloDeOficio.getId());
                    old.getEgressosParaGerar().add(egresso);
                    modeloDeOficioDAO.alterar(old);
                }
            }
        }
    }

    /**
     * @param cpf
     * @param situacao
     * @return
     * @throws ParseException
     */
    private Egresso obterEgressoPorCpf(String cpf, List<String> colunas) throws Exception {
        Egresso eg = null;
        if (!hasValue()) {
            eg = egressoDAO.obterPorCPFAtivo(cpf);
        } else {
            List<Egresso> egs = egressoDAO.obterEgressosPorCPF(cpf);
            if (egs == null || egs.isEmpty()) {
                return null;
            }
            if (egs.size() == 1) {
                eg = egs.get(0);
            } else {
                for (int i = 1; i < nomeColunas.size(); i++) {
                    String nameColumn = nomeColunas.get(i);
                    if (nameColumn.equalsIgnoreCase("Situação do Egresso")) {
                        String valueColumn = (String) obterValorPorColuna(nameColumn, colunas.get(i));
                        if (StringUtils.isNotEmpty(valueColumn)) {
                            eg = egs.stream().filter(item -> item.getFuncionario().getSituacao().equals(SituacaoFuncionarioEnum.DESLIGADO)).findAny().get();
                        } else {
                            eg = egs.stream().filter(item -> !item.getFuncionario().getSituacao().equals(SituacaoFuncionarioEnum.DESLIGADO)).findAny().get();
                        }
                    }
                }
            }
        }
        return eg;
    }

    private boolean hasValue() {
        if (this.listasSelecionadas != null && this.listasSelecionadas.size() > 0) {
            return true;
        } else if (this.eventoSelecionados != null) {
            return true;
        } else if (this.campanhasSelecionadas != null && this.campanhasSelecionadas.size() > 0) {
            return true;
        } else if (this.modelosDeOficioSelecionados != null && this.modelosDeOficioSelecionados.size() > 0) {
            return true;
        } else if (this.acaoBeneficiario != null && !this.acaoBeneficiario.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param situacao
     * @return
     * @throws BusinessException
     */
    private EnumSituacaoEvento obterEnumSituacaoEvento(String situacao) throws BusinessException {
        EnumSituacaoEvento situacaoEnum = EnumSituacaoEvento.getById(situacao);
        if (situacaoEnum == null) {
            throw new BusinessException("O campo 'Participou do Evento?' deve ser preenchido");
        }
        return situacaoEnum;
    }

    //Verifica se o campo passado por parametro é a um atributo da classe
    private boolean isAtributoDaClasse(String nomeColuna, Class classe) {
        Field[] fields = classe.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equalsIgnoreCase(nomeColuna)) {
                return true;
            }
        }
        return false;
    }

    //Carregar a planilha Ação e faz carga na tabela Acao com dados contidos nela
    public void carregarAcao() {
        try {
            linhasErro.clear();
            linhasInseridas.clear();
            DataFormatter formatador = new DataFormatter();

            Workbook workbook = new XSSFWorkbook(arquivoXLS.getInputstream());
            Sheet planilha = workbook.getSheetAt(0);

            int indexLinha = 0;

            Row row = planilha.getRow(indexLinha++);
            nomeColunas = obterNomesDasColunas(row, formatador);
            String cpf = nomeColunas.get(0);
            //primeira coluna deve ser CPF
            if (cpf.equalsIgnoreCase("cpf")) {
                for (int i = 1; i < nomeColunas.size(); i++) {
                    String nomeColuna = nomeColunas.get(i);
                    if (!cpf.equalsIgnoreCase(nomeColuna) && !"usuario".equalsIgnoreCase(nomeColuna)) {
                        boolean isTem = isAtributoDaClasse(nomeColuna, Acao.class);
                        if (!isTem) {
                            Mensagem.lancarMensagemErro("Coluna " + nomeColuna + " não identificada, não é possivel prosseguir com a importação");
                            throw new Exception("Coluna " + nomeColuna + " não identificada, não é possivel prosseguir com a importação");
                        }
                    }
                }

                List<String> colunas = new ArrayList<>();
                //Obtendo os dados por linha da planilha
                row = planilha.getRow(indexLinha++);
                while (row != null) {
                    try {
                        colunas = obterLinha(row, formatador);
                        if (!colunas.isEmpty()) {
                            String cpfFormatado = StringUtil.formataCPF(colunas.get(0));
                            Egresso egresso = egressoRN.obterPorCPF(cpfFormatado);
                            Date dataAcao = null;
                            if (!StringUtils.isEmpty(colunas.get(1))) {
                                dataAcao = Data.formataData(colunas.get(1).trim(), "dd/MM/yyyy HH:mm");
                            }
                            if (usuarioLogadoMB.temPermissao("primEmp.excelCompleto")) { // Só pode inserir ou atualizar egresso se tiver a permissão de importação completa
                                if (egresso == null) {
                                    colunas.add("Beneficiário não localizado");
                                    linhasErro.add(colunas);
                                } else if (dataAcao == null) {
                                    colunas.add("Coluna Data é obrigatória");
                                    linhasErro.add(colunas);
                                } else {
                                    TipoDeAcao tipoDeAcao = new TipoDeAcaoDAO().obterPorNome(colunas.get(2));
                                    if (tipoDeAcao != null) {
                                        Acao acao = new Acao();
                                        acao.setTipoDeAcao(tipoDeAcao);
                                        acao.setEgresso(egresso);

                                        if (!StringUtils.isEmpty(colunas.get(1))) {
                                            acao.setData(Data.formataData(colunas.get(1).trim(), "dd/MM/yyyy HH:mm"));
                                        }
                                        if (!StringUtils.isEmpty(colunas.get(3))) {
                                            acao.setDescricao(colunas.get(3).trim());
                                        }
                                        if (!StringUtils.isEmpty(colunas.get(4))) {
                                            acao.setUsuarioCriacao(colunas.get(4).trim());
                                        }

                                        new AcaoDAO().inserir(acao);
                                        linhasInseridas.add(colunas);
                                    } else {
                                        colunas.add("Tipo de Ação não localizada");
                                        linhasErro.add(colunas);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                        colunas.add(e.getMessage());
                        linhasErro.add(colunas);
                        workbook.close();
                    }
                    row = planilha.getRow(indexLinha++);
                }
                workbook.close();
                Mensagem.lancar("Importação concluída");
            } else {
                Mensagem.lancarMensagemErro("Primeira coluna precisa ser o CPF");
                workbook.close();
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            Mensagem.lancar("Erro ao proceder a importação");
        }
    }

    private void carregarCamposModelo(Class classe) {
        Field[] fields = classe.getDeclaredFields();
        camposModelo.clear();
        for (Field field : fields) {
            if (!field.getName().equalsIgnoreCase("id")) {
                camposModelo.add(field.getName());
            }
        }
    }

    public void importarMateriais() {
        if (usuarioLogadoMB.temPermissao("primEmp.excelCompleto")) { // Só pode inserir ou atualizar egresso se tiver a permissão de importação completa
            try {
                linhasErro.clear();
                linhasInseridas.clear();
                DataFormatter formatador = new DataFormatter();
                Workbook workbook = new XSSFWorkbook(arquivoXLS.getInputstream());
                Sheet planilha = workbook.getSheetAt(0);

                int indexLinha = 0;

                Row row = planilha.getRow(indexLinha++);
                nomeColunas = obterNomesDasColunas(row, formatador);
                String cpf = nomeColunas.get(0);
                //primeira coluna deve ser CPF
                if (cpf != null && cpf.equalsIgnoreCase("cpf")) {
                    for (int i = 1; i < nomeColunas.size(); i++) {
                        String nomeColuna = nomeColunas.get(i);
                        if (!cpf.equalsIgnoreCase(nomeColuna)) {
                            boolean isTem = isAtributoDaClasse(nomeColuna, MaterialEgresso.class);
                            if (!isTem) {
                                Mensagem.lancarMensagemErro("Coluna " + nomeColuna + " não identificada, não é possivel prosseguir com a importação");
                                throw new Exception("Coluna " + nomeColuna + " não identificada, não é possivel prosseguir com a importação");
                            }
                        }
                    }

                    List<String> colunas = new ArrayList<>();
                    //Obtendo os dados por linha da planilha
                    row = planilha.getRow(indexLinha++);
                    while (row != null) {
                        try {
                            colunas = obterLinha(row, formatador);
                            if (!colunas.isEmpty()) {
                                String cpfFormatado = StringUtil.formataCPF(colunas.get(0));
                                Egresso egresso = egressoDAO.obterPorCPF(cpfFormatado);

                                if (egresso == null) {
                                    colunas.add("Beneficiário não localizado");
                                    linhasErro.add(colunas);
                                } else {
                                    Material material = new MaterialDAO().obterPorNome(colunas.get(1));
                                    Tamanho tamanho = new TamanhoDAO().obterPorSigla(colunas.get(2));
                                    UnidadeDeMedida unidadeMedida = new UnidadeDeMedidaDAO().obterPorSigla(colunas.get(3));
                                    int quantidade = StringUtils.isNotEmpty(colunas.get(4)) ? Integer.parseInt(colunas.get(4)) : 0;
                                    String dataRecebimentostr = colunas.get(5);
                                    String observacao = colunas.get(6);
                                    String lote = colunas.get(7);

                                    String loteMaximo = parametroDAO.obterValor("maxLote");
                                    if (StringUtils.isNotEmpty(loteMaximo) && StringUtils.isNotEmpty(lote)) {
                                        Integer loteNumber = Integer.parseInt(lote);
                                        Integer loteMaxNumber = Integer.parseInt(loteMaximo);

                                        if (loteNumber.compareTo(loteMaxNumber) > 0 || loteNumber.compareTo(0) == 0) {
                                            colunas.add("Lote informado maior do que o máximo permitido ou não informado");
                                            linhasErro.add(colunas);
                                        } else if (!CoreUtil.someIsNull(material, tamanho, unidadeMedida) && quantidade > 0 && StringUtils.isNotEmpty(dataRecebimentostr)) {
                                            List<MaterialEgresso> lista = materialEgressoDAO.obterMateriaisEgressoPorLote(material, egresso, lote);
                                            if (lista == null || lista.isEmpty()) {
                                                Date dataRecebimento = Data.formataData(dataRecebimentostr, "dd/MM/yyyy");
                                                MaterialEgresso materialEgresso = new MaterialEgresso();
                                                materialEgresso.setDataRecebimento(dataRecebimento);
                                                materialEgresso.setMaterial(material);
                                                materialEgresso.setTamanho(tamanho);
                                                materialEgresso.setUnidade(unidadeMedida);
                                                materialEgresso.setQuantidade(quantidade);
                                                materialEgresso.setLote("Lote " + lote);
                                                materialEgresso.setObservacao(observacao);
                                                materialEgresso.setEgresso(egresso);
                                                materialEgressoDAO.inserir(materialEgresso);
                                                linhasInseridas.add(colunas);
                                            } else {
                                                colunas.add("Material já associado ao beneficiário");
                                                linhasErro.add(colunas);
                                            }
                                        } else {
                                            if (quantidade <= 0) {
                                                colunas.add("Quantidade deve ser maior que 0(zero)");
                                            } else {
                                                colunas.add("Um ou mais campos (Material, Tamanho ou Unidade) não foram localizados");
                                            }
                                            linhasErro.add(colunas);
                                        }
                                    } else {
                                        colunas.add("Lote não informado");
                                        linhasErro.add(colunas);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                            colunas.add(e.getMessage());
                            linhasErro.add(colunas);
                            workbook.close();
                        }
                        row = planilha.getRow(indexLinha++);
                    }
                    workbook.close();
                    Mensagem.lancarMensagemInfo("Importação concluída");
                } else {
                    Mensagem.lancarMensagemInfo("Primeira coluna precisa ser o CPF");
                }
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                Mensagem.lancarMensagemErro("Erro ao proceder a importação");
            }
        }

    }

    private List<String> obterNomesDasColunas(Row linha, DataFormatter formatador) {
        List<String> nomes = new ArrayList<>();
        int indexColuna = 0;
        Cell coluna = linha.getCell(indexColuna++);
        String strColuna = "";
        while (coluna != null) {
            strColuna = formatador.formatCellValue(coluna).trim();
            if (!strColuna.isEmpty()) {
                nomes.add(strColuna);
            }
            coluna = linha.getCell(indexColuna++);
        }
        return nomes;
    }

    private List<String> obterLinha(Row row, DataFormatter formatador) {
        List<String> linhaEmLista = new ArrayList<>();
        for (int a = 0; a < nomeColunas.size(); a++) {
            linhaEmLista.add(formatador.formatCellValue(row.getCell(a)).trim());
        }
        return linhaEmLista;
    }

    private Object obterValorPorColuna(String coluna, String valor) throws Exception {
        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
        if (CamposEgressosUtil.campoSituacao(coluna)) {
            Situacao s = situacaoDAO.obterPorNomeECategoria(valor);
            if (s == null) {
                throw new Exception("Situação não encontrada. A situação deve estar no formato SITUAÇÃO - CATEGORIA");
            }
            return s;
        }
        if (CamposEgressosUtil.campoDate(coluna) && valor.getClass().equals(String.class)) {//Se campo DATE e valor String, fazer a conversão adequada.
            if (!Data.validarFormato((String) valor, "dd/MM/yyyy")) {
                throw new Exception("Data inválida. Formato deve dd/MM/yyyy");
            }
            return Data.formataData((String) valor);
        }
        if (CamposEgressosUtil.campoMunicipio(coluna)) {
            Municipio m = municipioDAO.obterPorNome(valor);
            if (m == null) {
                m = new Municipio();
                if (valor.contains("-")) {
                    m.setNome(valor.substring(0, valor.indexOf("-")).trim());
                    m.setUf(valor.substring(valor.indexOf("-") + 1).trim());
                } else {
                    m.setNome(valor);
                }

                m = municipioDAO.inserir(m);
            }
            return m;
        }
        if (CamposEgressosUtil.campoDemandante(coluna)) {
            Demandante d = demandanteDAO.obterPorNome(valor);
            if (d == null) {
                throw new Exception(coluna + " não encontrado");
            }
            return d;
        }
        if (CamposEgressosUtil.campoFormacao(coluna)) {
            Formacao f = formacaoDAO.obterPorNome(valor);
            if (f == null) {
                throw new Exception(coluna + " não encontrado");
            }
            return f;
        }
        if (CamposEgressosUtil.campoDeficiencia(coluna)) {
            Deficiencia d = deficienciaDAO.obterPorNome(valor);
            if (d == null) {
                throw new Exception(coluna + " não encontrado");
            }
            return d;
        }
        if (CamposEgressosUtil.campoRacaCor(coluna)) {
            RacaCor r = RacaCor.obterPorDescricao(valor);
            if (r == null) {
                throw new Exception(coluna + " não encontrado");
            }
            return r;
        }

        if (CamposEgressosUtil.campoBoolean(coluna)) {
            Boolean value = valor.equals("1") || valor.equalsIgnoreCase("sim") ? Boolean.TRUE : Boolean.FALSE;
            return value;
        } else if (coluna.equals("Curso Superior") || coluna.equals("Curso Superior Concluído")
                || coluna.equals("Pretende Fazer Curso Superior") || coluna.equals("Curso Técnico ou Profissionalizante")) {
            String value = (valor.equals("1") || valor.equalsIgnoreCase("sim") || valor.equalsIgnoreCase("s")) ? "SIM" : "NAO";
            return value;
        } else if (coluna.equals("Modalidade da Graduação")) {
            return EnumModalidadeEnsino.getValue(valor);
        } else if (coluna.equals("Tipo de Instituição") || coluna.equals("Tipo de Instituição Técnico")) {
            return EnumTipoInstituicaoEnsino.getValue(valor);
        }

        return valor;
    }

    private void criarMensagem(String msg) {
        FacesMessage facesMessage = new FacesMessage(msg);
        FacesContext.getCurrentInstance().addMessage("", facesMessage);
    }

    private void resetarProgresso() {
        progresso = 0;
        mensagem = "";
    }

    private void atualizarProgresso(int i) {
        progresso = ((i - 1) * 100) / quantidade;
        try {
            Thread.sleep(200);
        } catch (Exception ex) {
            criarMensagem("erro ");
        }
    }

    public void adicionarEgresso(Long idEgresso, Long idCampanha) throws Exception {
        Egresso egresso = egressoDAO.obterPorPK(idEgresso);
        if (egresso == null) {
            throw new Exception("Egresso não localizado");
        }
        if (egresso.getEmailParticular() == null || egresso.getEmailParticular().trim().isEmpty()) {
            throw new Exception("Egresso sem email");
        }
        if (!egresso.isEmailValido()) {
            throw new Exception("Email inválido");
        }
        Campanha campanha = campanhaDAO.obterPorPK(idCampanha);
        if (campanha != null) {
            EnvioDeEmail envioDaCampanha = new EnvioDeEmail();

            Acao action = new Acao();
            action.setData(new Date());
            action.setDescricao("Envio de email da campanha::" + campanha.getAssunto());
            action.setEgresso(egresso);
            action.setTipoDeAcao(new TipoDeAcaoDAO().tipoEmail());

            envioDaCampanha.setEgresso(egresso);
            envioDaCampanha.setEmail(egresso.getEmailParticular());
            envioDaCampanha.setEmailSecundario(egresso.getEmailSecundario());
            envioDaCampanha.setAcao(action);
            envioDaCampanha.setCampanha(campanha);
            envioDaCampanha.setAcao(new AcaoDAO().inserir(action));
            new EnvioDeEmailDAO().inserir(envioDaCampanha);
        }

    }

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public UploadedFile getArquivoXLS() {
        return arquivoXLS;
    }

    public void setArquivoXLS(UploadedFile arquivoXLS) {
        this.arquivoXLS = arquivoXLS;
    }

    public List<Egresso> getEgressos() {
        return egressos;
    }

    public void setEgressos(List<Egresso> egressos) {
        this.egressos = egressos;
    }

    public List<String> getNomeColunas() {
        return nomeColunas;
    }

    public void setNomeColunas(List<String> nomeColunas) {
        this.nomeColunas = nomeColunas;
    }

    public List<List<String>> getLinhasInseridas() {
        return linhasInseridas;
    }

    public void setLinhasInseridas(List<List<String>> linhasInseridas) {
        this.linhasInseridas = linhasInseridas;
    }

    public List<List<String>> getLinhasErro() {
        return linhasErro;
    }

    public void setLinhasErro(List<List<String>> linhasErro) {
        this.linhasErro = linhasErro;
    }

    public List<List<String>> getLinhasAtualizadas() {
        return linhasAtualizadas;
    }

    public void setLinhasAtualizadas(List<List<String>> linhasAtualizadas) {
        this.linhasAtualizadas = linhasAtualizadas;
    }

    public SituacaoDAO getSituacaoDAO() {
        return situacaoDAO;
    }

    public void setSituacaoDAO(SituacaoDAO situacaoDAO) {
        this.situacaoDAO = situacaoDAO;
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
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

    public List<Lista> getListasSelecionadas() {
        return listasSelecionadas;
    }

    public void setListasSelecionadas(List<Lista> listasSelecionadas) {
        this.listasSelecionadas = listasSelecionadas;
    }

    public EgressoListaDAO getEgressoListaDAO() {
        return egressoListaDAO;
    }

    public void setEgressoListaDAO(EgressoListaDAO egressoListaDAO) {
        this.egressoListaDAO = egressoListaDAO;
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
    }

    public FormacaoDAO getFormacaoDAO() {
        return formacaoDAO;
    }

    public void setFormacaoDAO(FormacaoDAO formacaoDAO) {
        this.formacaoDAO = formacaoDAO;
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

    public CampanhaDAO getCampanhaDAO() {
        return campanhaDAO;
    }

    public void setCampanhaDAO(CampanhaDAO campanhaDAO) {
        this.campanhaDAO = campanhaDAO;
    }

    public List<Campanha> getCampanhasSalvas() {
        return campanhasSalvas;
    }

    public void setCampanhasSalvas(List<Campanha> campanhasSalvas) {
        this.campanhasSalvas = campanhasSalvas;
    }

    public List<Campanha> getCampanhasSelecionadas() {
        return campanhasSelecionadas;
    }

    public void setCampanhasSelecionadas(List<Campanha> campanhasSelecionadas) {
        this.campanhasSelecionadas = campanhasSelecionadas;
    }

    public ModeloDeOficioDAO getModeloDeOficioDAO() {
        return modeloDeOficioDAO;
    }

    public void setModeloDeOficioDAO(ModeloDeOficioDAO modeloDeOficioDAO) {
        this.modeloDeOficioDAO = modeloDeOficioDAO;
    }

    public List<ModeloDeOficio> getModelosNaoGerados() {
        return modelosNaoGerados;
    }

    public void setModelosNaoGerados(List<ModeloDeOficio> modelosNaoGerados) {
        this.modelosNaoGerados = modelosNaoGerados;
    }

    public List<ModeloDeOficio> getModelosDeOficioSelecionados() {
        return modelosDeOficioSelecionados;
    }

    public void setModelosDeOficioSelecionados(List<ModeloDeOficio> modelosDeOficioSelecionados) {
        this.modelosDeOficioSelecionados = modelosDeOficioSelecionados;
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

    public List<CI> getCisSelecionadas() {
        return cisSelecionadas;
    }

    public void setCisSelecionadas(List<CI> cisSelecionadas) {
        this.cisSelecionadas = cisSelecionadas;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Boolean getAcao() {
        return acao;
    }

    public void setAcao(Boolean acao) {
        this.acao = acao;
    }

    public List<Acao> getAcoesSelecionadas() {
        return acoesSelecionadas;
    }

    public void setAcoesSelecionadas(List<Acao> acoesSelecionadas) {
        this.acoesSelecionadas = acoesSelecionadas;
    }

    public MaterialEgressoDAO getMaterialEgressoDAO() {
        return materialEgressoDAO;
    }

    public void setMaterialEgressoDAO(MaterialEgressoDAO materialEgressoDAO) {
        this.materialEgressoDAO = materialEgressoDAO;
    }

    public String getAcaoBeneficiario() {
        return acaoBeneficiario;
    }

    public void setAcaoBeneficiario(String acaoBeneficiario) {
        this.acaoBeneficiario = acaoBeneficiario;
    }

    public ParametroDAO getParametroDAO() {
        return parametroDAO;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    public Integer getProgresso() {
        return progresso;
    }

    public void setProgresso(Integer progresso) {
        this.progresso = progresso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public EgressoCadastroMB getEgressoCadastroMB() {
        return egressoCadastroMB;
    }

    public void setEgressoCadastroMB(EgressoCadastroMB egressoCadastroMB) {
        this.egressoCadastroMB = egressoCadastroMB;
    }

    public Evento getEventoSelecionados() {
        return eventoSelecionados;
    }

    public void setEventoSelecionados(Evento eventoSelecionados) {
        this.eventoSelecionados = eventoSelecionados;
    }

    public EventoEgressoDAO getEventoEgressoDAO() {
        return eventoEgressoDAO;
    }

    public void setEventoEgressoDAO(EventoEgressoDAO eventoEgressoDAO) {
        this.eventoEgressoDAO = eventoEgressoDAO;
    }

    public EgressoRN getEgressoRN() {
        return egressoRN;
    }

    public void setEgressoRN(EgressoRN egressoRN) {
        this.egressoRN = egressoRN;
    }
    
    
}

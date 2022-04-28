package br.org.flem.primeiroemprego.mb;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dao.dominiosistemas.DominioFuncionarioDAO;
import br.org.flem.fw.persistencia.dto.Departamento;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.persistencia.dto.SituacaoFuncionarioEnum;
import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.dao.AcaoDAO;
import br.org.flem.primeiroemprego.dao.CategoriaDaSituacaoDAO;
import br.org.flem.primeiroemprego.dao.ColaboradorDAO;
import br.org.flem.primeiroemprego.dao.DeficienciaDAO;
import br.org.flem.primeiroemprego.dao.ListaDAO;
import br.org.flem.primeiroemprego.dao.MaterialDAO;
import br.org.flem.primeiroemprego.dao.MaterialEgressoDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.dao.OficioDAO;
import br.org.flem.primeiroemprego.dao.ParametroDAO;
import br.org.flem.primeiroemprego.dao.RegraContratacaoDAO;
import br.org.flem.primeiroemprego.dao.SituacaoDAO;
import br.org.flem.primeiroemprego.dao.TamanhoDAO;
import br.org.flem.primeiroemprego.dao.TipoAssistenciaSocialDAO;
import br.org.flem.primeiroemprego.dao.TipoDeAcaoDAO;
import br.org.flem.primeiroemprego.dao.UnidadeDeMedidaDAO;
import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.AssistenciaSocial;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Deficiencia;
import br.org.flem.primeiroemprego.entity.Documento;
import br.org.flem.primeiroemprego.entity.DocumentoDoEgresso;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EnumModalidadeEnsino;
import br.org.flem.primeiroemprego.entity.EnumTipoInstituicaoEnsino;
import br.org.flem.primeiroemprego.entity.EnumTipoTelefone;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Lista;
import br.org.flem.primeiroemprego.entity.Material;
import br.org.flem.primeiroemprego.entity.MaterialEgresso;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.entity.RacaCor;
import br.org.flem.primeiroemprego.entity.RegraContratacao;
import br.org.flem.primeiroemprego.entity.Situacao;
import br.org.flem.primeiroemprego.entity.Tamanho;
import br.org.flem.primeiroemprego.entity.Telefone;
import br.org.flem.primeiroemprego.entity.TipoAssistenciaSocial;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
import br.org.flem.primeiroemprego.entity.UnidadeDeMedida;
import br.org.flem.primeiroemprego.entity.Vaga;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.negocio.DocumentoDoEgressoRN;
import br.org.flem.primeiroemprego.negocio.EgressoRN;
import br.org.flem.primeiroemprego.negocio.OficioRN;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.RedirectUtil;
import br.org.flem.primeiroemprego.util.validator.ValidacaoEgressoImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.OptimisticLockException;
import javax.ws.rs.core.Response;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author AJLima
 */
@ManagedBean
@ViewScoped
public class EgressoCadastroMB extends BaseMB implements Serializable {

    @ManagedProperty(value = "#{egressoRN}")
    private EgressoRN egressoRN;
    private Egresso egresso;
    @ManagedProperty(value = "#{listaDAO}")
    private ListaDAO listaDAO;
    @ManagedProperty(value = "#{tipoDeAcaoDAO}")
    private TipoDeAcaoDAO tipoDeAcaoDAO;
    @ManagedProperty(value = "#{materialDAO}")
    private MaterialDAO materialDAO;
    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    @ManagedProperty(value = "#{situacaoDAO}")
    private SituacaoDAO situacaoDAO;
    @ManagedProperty(value = "#{categoriaDaSituacaoDAO}")
    private CategoriaDaSituacaoDAO categoriaDAO;
    @ManagedProperty(value = "#{tipoAssistenciaSocialDAO}")
    private TipoAssistenciaSocialDAO tipoAssistenciaSocialDAO;
    @ManagedProperty(value = "#{deficienciaDAO}")
    private DeficienciaDAO deficienciaDAO;
    @ManagedProperty(value = "#{oficioDAO}")
    private OficioDAO oficioDAO;
    @ManagedProperty(value = "#{tamanhoDAO}")
    private TamanhoDAO tamanhoDAO;
    @ManagedProperty(value = "#{unidadeDeMedidaDAO}")
    private UnidadeDeMedidaDAO unidadeDAO;
    @ManagedProperty(value = "#{materialEgressoDAO}")
    private MaterialEgressoDAO materialEgressoDAO;
    @ManagedProperty(value = "#{parametroDAO}")
    private ParametroDAO parametroDAO;
    @ManagedProperty(value = "#{acaoDAO}")
    private AcaoDAO acaoDAO;

    private String tempoDeContrato;

    private Acao acao;
    private MaterialEgresso materialEgresso;
    private Material material;
    private AssistenciaSocial assistenciaSocial;
    private RHServico rhServico = new RHServico();
    private Boolean block = true;
    private Boolean blockDocPendente = false;
    private String botao = "Desbloquear para Edição";

    private CategoriaDaSituacao categoria;

    private List<Municipio> municipios;
    private List<Situacao> situacoes;
    private List<CategoriaDaSituacao> categorias;
    private List<Deficiencia> deficiencias;
    private List<Material> materiais;
    private List<Tamanho> tamanhos;
    private List<UnidadeDeMedida> unidades;
    private boolean municipioVagaAlterado;
    private boolean municipioResidenciaAlterado;

    private UploadedFile novoOficio;

    private List<RegraContratacao> regras = new ArrayList<>();

    @ManagedProperty(value = "#{regraContratacaoDAO}")
    private RegraContratacaoDAO regraDAO;

    private EscritorioRegional escritorioRegional;

    private List<MaterialEgresso> listaExclusaoMaterial;
    private MaterialEgresso itemExcluir;

    private boolean desabilitarBtSalvar = false;
    private List<String> lotes = new ArrayList<>();

    private SituacaoFuncionarioEnum situacaoDominio;
    @ManagedProperty(value = "#{colaboradorDAO}")
    private ColaboradorDAO colaboradorDAO;
    @ManagedProperty(value = "#{documentoDoEgressoRN}")
    private DocumentoDoEgressoRN documentoDoEgressoRN;
    @ManagedProperty(value = "#{oficioRN}")
    private OficioRN oficioRN;

    private List<DocumentoDoEgresso> documentosExcluidos;
    private Telefone telefone;
    private List<Telefone> telefones = new ArrayList<>();

    @PostConstruct
    public void init() {
        situacoes = new ArrayList<>();
        documentosExcluidos = new ArrayList<>();
        listaExclusaoMaterial = new ArrayList<>();
        carregarListas();
        String idEgresso = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (idEgresso != null) {
            egresso = egressoRN.obterPorId(Long.parseLong(idEgresso));
            egressoRN.carregarHistoricoDeVaga(egresso);
            egresso.getAssistenciasSociais().isEmpty();//Necesário inicializar a PersistenseBag, pois alguns perfis não renderizam a tabela de assitencia social
            obterDadosEscritorioRegional();
            obterDocumentosDoEgresso();
            obterOficioDoEgresso();
            this.verificaRegra();
            obterDadosFuncionario();
            telefone = new Telefone(egresso);
            obterAcoes();
            carrgearTelefones();
        } else {
            egresso = new Egresso();
            egresso.setVaga(new Vaga());
            egresso.getVaga().setEgresso(egresso);
            block = false;//Na criação de novo Egresso não deve aparecer com os campos bloqueados
        }

        verificarPermissao();
    }

    private void obterDadosEscritorioRegional() {
        if (egresso.getVaga().getSituacao() != null) {
            categoria = egresso.getVaga().getSituacao().getCategoria();
            if (egresso.getVaga().getMunicipio().getEscritorioRegional() != null) {
                escritorioRegional = egresso.getVaga().getMunicipio().getEscritorioRegional();
                if (escritorioRegional != null) {
                    escritorioRegional.setColaboradores(colaboradorDAO.obterPorEscritorioRegional(escritorioRegional));
                }
            }
            atualizarSituacoes();
        }
    }

    private void obterDadosFuncionario() {
        if (egresso.getFuncionario() != null) {
            if (StringUtils.isNotEmpty(egresso.getMatriculaFlem())) {
                List<Funcionario> funcionarios = new DominioFuncionarioDAO().obterFuncionariosContratado(null, Integer.valueOf(egresso.getMatriculaFlem()), null);
                if (funcionarios != null && !funcionarios.isEmpty()) {
                    this.situacaoDominio = funcionarios.get(0).getSituacao();
                }
            }
            if (this.situacaoDominio == null) {
                this.situacaoDominio = egresso.getFuncionario().getSituacao();
            }
            this.tempoDeContrato = "Tempo de Contratação: " + Data.mesDiff(egresso.getFuncionario().getAdmissao(), egresso.getFuncionario().getRescisao() == null ? new Date() : egresso.getFuncionario().getRescisao()) + " meses";
        }
    }

    private void carregarListas() {
        municipios = municipioDAO.obterLista();
        categorias = categoriaDAO.obterParaTelaDeCadastro();
        deficiencias = deficienciaDAO.obterLista();
        materiais = materialDAO.obterLista();
        tamanhos = tamanhoDAO.obterLista();
        unidades = unidadeDAO.obterLista();
    }

    private void verificarPermissao() {
        blockDocPendente = !getUsuarioLogadoMB().temPermissao("primEmp.alterarDocPendente")
                && !getUsuarioLogadoMB().temPermissao("primEmp.lancarAcaoExclusivamente");
    }

    public boolean isDesabilitarBtSalvar() {
        if (block) {
            if (!getUsuarioLogadoMB().temPermissao("primEmp.alterarDocPendente")
                    && !getUsuarioLogadoMB().temPermissao("primEmp.lancarAcaoExclusivamente")) {
                desabilitarBtSalvar = true;
            }
        } else {
            desabilitarBtSalvar = false;
        }
        return desabilitarBtSalvar;
    }

    public void setDesabilitarBtSalvar(boolean desabilitarBtSalvar) {
        this.desabilitarBtSalvar = desabilitarBtSalvar;
    }

    public void destravarEdicao() {
        if (block) {
            block = false;
            botao = "Bloquear para Edição";
            if (isUserAdm()) {
                blockDocPendente = false;
            }
        } else {
            block = true;
            botao = "Desbloquear para Edição";
            blockDocPendente = true;
        }
    }

    public List<RegraContratacao> verificaRegra() {

        for (RegraContratacao r : regraDAO.obterLista()) {
            if (r.getAtivo()) {

                if (r.getMunicipio() == null && r.getFormacao() == null) {
                    if ((egresso.getVaga().getDemandante() == r.getDemandante()
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 3
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 6
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 7)) {
                        regras.add(r);
                    }
                } else if (r.getMunicipio() != null && r.getDemandante() != null && r.getFormacao() != null) {
                    if ((egresso.getFormacao() == r.getFormacao()
                            && egresso.getVaga().getDemandante() == r.getDemandante()
                            && egresso.getVaga().getMunicipio() == r.getMunicipio()
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 3
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 6
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 7)) {
                        regras.add(r);
                    }
                } else if (r.getMunicipio() == null) {
                    if ((egresso.getFormacao() == r.getFormacao()
                            && egresso.getVaga().getDemandante() == r.getDemandante()
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 3
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 6
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 7)) {
                        regras.add(r);
                    }
                } else if (r.getFormacao() == null) {
                    if ((egresso.getVaga().getDemandante() == r.getDemandante()
                            && egresso.getVaga().getMunicipio() == r.getMunicipio()
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 3
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 6
                            && egresso.getVaga().getSituacao().getCategoria().getId() != 7)) {
                        regras.add(r);
                    }
                }
            }
        }
        return regras;
    }

    public void salvar() {
        if (egresso.getId() != null) {
            try {
                validarRespostasCursoSuperior();
                validarEmail();
                alterarEgresso();
                if (listaExclusaoMaterial != null && !listaExclusaoMaterial.isEmpty()) {
                    for (MaterialEgresso obj : listaExclusaoMaterial) {
                        materialEgressoDAO.excluir(obj);
                    }
                }
                Mensagem.lancarMensagemInfo("Beneficiário alterado com sucesso");
                RedirectUtil.redirect("/egresso/cadastro.xhtml?id=" + egresso.getId());
            } catch (BusinessException e) {
                Mensagem.lancarMensagemErro(e.getMessage());
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            } catch (Exception ex) {
                if (ex instanceof OptimisticLockException) {
                    Mensagem.lancarMensagemErro("Este Beneficiário foi alterado por outra pessoa!");
                } else {
                    Mensagem.lancarMensagemErro("Erro ao alterar Beneficiário");
                }
                Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                egressoRN.inserir(egresso);
                Mensagem.lancarMensagemInfo("Beneficiário inserido com sucesso");
                RedirectUtil.redirect("/egresso/cadastro.xhtml?id=" + egresso.getId());
            } catch (Exception e) {
                Mensagem.lancarMensagemErro("Erro ao inserir o beneficiário");
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            }
        }
    }

    public void atualizarSituacoes() {
        situacoes = situacaoDAO.obterPorCategoria(categoria);
    }

    public List<String> obterRespostas(String campo) {
        return egressoRN.obterRespostas(campo);
    }

    public List<String> obterRespostasDaVaga(String campo) {
        return egressoRN.obterRespostasDaVaga(campo);
    }

    public void checarCPF() {
        egresso.setMatriculaFlem(null);
        egresso.setFuncionario(null);

        Egresso c = egressoRN.obterPorCPF(egresso.getCpf());
        if (c != null) {
            Mensagem.lancarMensagemInfo("Beneficiário já cadastrando, atualizando");
            egresso = c;
        } else {
            Departamento d = new Departamento();
            d.setCodigoDominio(Integer.parseInt(PropertiesUtil.getProperty(("departamentoPrimeiroEmprego"))));
            IFuncionario f = rhServico.obterFuncionarioPorCPFSituacaoDepartamento(egresso.getCpf().replace(".", "").replace("-", ""), SituacaoFuncionarioEnum.ATIVO, d);
            if (f != null) {
                egresso.setFuncionario(f);

                egresso.setMatriculaFlem(f.getMatricula().toString());
                egresso.setNome(f.getNome());
                egresso.setBairro(f.getEndereco().getBairro());
                egresso.setCep(f.getEndereco().getCep());
                egresso.setComplemento(f.getEndereco().getComplemento());
                egresso.setDataNascimento(f.getNascimento());
                egresso.setTelefone1(f.getTelefone());
                egresso.setTelefone2(f.getTelefone2());
                egresso.setCelular(f.getCelular());
                egresso.setContato(f.getContato());
                egresso.setEmailParticular(f.getEmail());
                egresso.setEndereco(f.getEndereco().getDescricao());
                egresso.setMunicipio(municipioDAO.obterPorNome(f.getEndereco().getCidade()));
                egresso.setNumero(f.getEndereco().getNumero());
                egresso.setRg(f.getRg());
                Mensagem.lancarMensagemInfo("Beneficiário já foi cadastrado no sistema de RH");
            }
        }
    }

    private void validarEmail() {
        if (!StringUtils.isEmpty(egresso.getEmailParticular()) && !CoreUtil.isValidEmailAddress(egresso.getEmailParticular())) {
            throw new BusinessException("Erro: E-mail Primário inválido");
        }
        if (!StringUtils.isEmpty(egresso.getEmailSecundario()) && !CoreUtil.isValidEmailAddress(egresso.getEmailSecundario())) {
            throw new BusinessException("Erro: E-mail Secundário inválido");
        }
    }

    public StreamedContent downloadDocumento(Documento documento) {
        if (StringUtils.isNotEmpty(documento.getFilePath())) {
            try {
                String filename = documento.getNome();
                Response res = documentoDoEgressoRN.get(documento.getFilePath());
                if (res == null) {
                    Mensagem.lancarMensagemErro("Arquivo não localizado");
                    RedirectUtil.redirect("/egresso/cadastro.xhtml?id=" + egresso.getId());
                } else {
                    if (StringUtils.isNotEmpty(res.getHeaderString("filename"))) {
                        filename = res.getHeaderString("filename");
                    }
                    InputStream stream = res.readEntity(InputStream.class);
                    return new DefaultStreamedContent(stream, documento.getTipo(), filename);
                }
            } catch (BusinessException ex) {
                Logger.getLogger(OficioListaMB.class.getName()).log(Level.SEVERE, null, ex);
                throw new BusinessException(ex.getMessage());
            }
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(documento.getArquivo()), documento.getTipo(), documento.getNome());
    }

    public void uploadDocumentoSigiloso(FileUploadEvent event) {
        try {
            DocumentoDoEgresso documento = documentoDoEgressoRN.postDocumentoDoEgresso(event.getFile().getContents(), event.getFile().getFileName(), egresso, true);
            salvarDocumento(documento);
        } catch (IOException | BusinessException ex) {
            Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void uploadDocumento(FileUploadEvent event) {
        try {
            DocumentoDoEgresso documento = documentoDoEgressoRN.postDocumentoDoEgresso(event.getFile().getContents(), event.getFile().getFileName(), egresso, false);
            salvarDocumento(documento);
        } catch (IOException | BusinessException ex) {
            Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void salvarDocumento(DocumentoDoEgresso documento) throws Exception {
        Egresso egressoOld = egressoRN.obterPorId(egresso.getId());
        if (egressoOld != null) {
            documentoDoEgressoRN.salvarDocumento(documento);
            obterDocumentosDoEgresso();
        }
    }

    private Egresso alterarEgresso() throws Exception {
        Egresso old = egressoRN.alterarEgresso(egresso);
        deleterDocumentosDoEgresso();
        atualizarTelefones();
        return old;
    }

    private void atualizarTelefones() throws Exception {
        egressoRN.atualizarTelefone(telefones, egresso);
    }

    public void addListaExclusao(DocumentoDoEgresso documento) {
        documentosExcluidos.add(documento);
        egresso.getDocumentos().remove(documento);
    }

    private void deleterDocumentosDoEgresso() {
        try {
            if (!documentosExcluidos.isEmpty()) {
                for (DocumentoDoEgresso documento : documentosExcluidos) {
                    documentoDoEgressoRN.deleteDocumento(documento);
                }
            }
            documentosExcluidos = new ArrayList<>();
        } catch (Exception ex) {
            Mensagem.lancarMensagemErro("Alguns Documentos do Egresso não puderam ser excluidos");
            Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void obterDocumentosDoEgresso() {
        egresso.setDocumentos(documentoDoEgressoRN.findByEgresso(egresso));
    }

    private void obterOficioDoEgresso() {
        egresso.setOficios(oficioRN.obterPorEgresso(egresso));
    }

    public void salvarOficio() throws Exception {
        if (this.novoOficio != null) {
            if (this.novoOficio.getFileName().matches(".*\\.pdf$")) {
                Oficio o = documentoDoEgressoRN.postOficio(this.novoOficio.getContents(), this.novoOficio.getFileName(), egresso);
                synchronized (OficioDAO.LOCK) {//Garantir que somente uma thread vai acessar a insersão para obter o numero do oficio
                    oficioDAO.atribuirNumeroDoOficio(o);
                    oficioDAO.inserir(o);
                }
                Mensagem.lancarMensagemInfo("Ofício salvo com sucesso");
            } else {
                throw new Exception("Formato de arquivo inválido. Deve ser PDF.");
            }
        } else {
            throw new Exception("Informar o Ofício a ser salvo");
        }
    }

    public StreamedContent abaVaga() {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Vaga");
            short rowNumber = 0;

            Row row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Beneficiário");
            row.createCell(1).setCellValue(egresso.getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("CPF");
            row.createCell(1).setCellValue(egresso.getCpf());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Matricula FLEM");
            row.createCell(1).setCellValue(egresso.getFuncionario() == null ? "" : Data.formataData(egresso.getFuncionario().getAdmissao()));

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Demandante");
            row.createCell(1).setCellValue(egresso.getVaga().getDemandante().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Formação Beneficiário");
            row.createCell(1).setCellValue(egresso.getFormacao().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Categoria");
            row.createCell(1).setCellValue(egresso.getVaga().getSituacao().getCategoria().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Situação");
            row.createCell(1).setCellValue(egresso.getVaga().getSituacao().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Território");
            row.createCell(1).setCellValue(egresso.getVaga().getMunicipio() == null ? "" : egresso.getVaga().getMunicipio().getTerritorio().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Município Vaga");
            row.createCell(1).setCellValue(egresso.getVaga().getMunicipio() == null ? "" : egresso.getVaga().getMunicipio().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Município Residência");
            row.createCell(1).setCellValue(egresso.getMunicipio() == null ? "" : egresso.getMunicipio().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Divergência Município");
            row.createCell(1).setCellValue(egresso.isDivergenciaMunicipio() ? "MUNICÍPIO VAGA DIVERGENTE" : "");

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Instituição");
            row.createCell(1).setCellValue(egresso.getVaga().getInstituicao());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Unidade de Lotação");
            row.createCell(1).setCellValue(egresso.getVaga().getUnidadeDeLotacao());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Logradouro da Unidade de Lotação");
            row.createCell(1).setCellValue(egresso.getVaga().getLogradouroUnidadeDeLotacao());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Bairro da Unidade de Lotação");
            row.createCell(1).setCellValue(egresso.getVaga().getBairroUnidadeDeLotacao());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Município da Unidade de Lotação");
            row.createCell(1).setCellValue(egresso.getVaga().getMunicipioUnidadeDeLotacao() == null ? "" : egresso.getVaga().getMunicipioUnidadeDeLotacao().getNome());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("CEP da Unidade de Lotação");
            row.createCell(1).setCellValue(egresso.getVaga().getCepUnidadeDeLotacao());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Ponto Focal na Unidade");
            row.createCell(1).setCellValue(egresso.getVaga().getPontoFocalNaUnidade());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Telefone 1 Ponto Focal");
            row.createCell(1).setCellValue(egresso.getVaga().getTelefone1PontoFocal());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Telefone 2 Ponto Focal");
            row.createCell(1).setCellValue(egresso.getVaga().getTelefone2PontoFocal());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Telefone 3 Ponto Focal");
            row.createCell(1).setCellValue(egresso.getVaga().getTelefone3PontoFocal());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Telefone 4 Ponto Focal");
            row.createCell(1).setCellValue(egresso.getVaga().getTelefone4PontoFocal());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Telefone 5 Ponto Focal");
            row.createCell(1).setCellValue(egresso.getVaga().getTelefone5PontoFocal());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Email Ponto Focal");
            row.createCell(1).setCellValue(egresso.getVaga().getEmailPontoFocal());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Data Convocação SETRE/SINE");
            row.createCell(1).setCellValue(egresso.getVaga().getDataConvocacaoSETRESINE());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Remessa SETRE/SINE ");
            row.createCell(1).setCellValue(egresso.getVaga().getRemessaSETRESINE());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Data Remessa SETRE/SINE");
            row.createCell(1).setCellValue(egresso.getVaga().getDataRemessaSETRESINE());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Mês Remessa SETRE/SINE");
            row.createCell(1).setCellValue(egresso.getVaga().getMesRemessaSETRESINE());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Data Início Atividades");
            row.createCell(1).setCellValue(egresso.getVaga().getDataInicioAtividades());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Publicação D.O");
            row.createCell(1).setCellValue(egresso.getVaga().getPublicacaoDiarioOficial());

            row = sheet.createRow(rowNumber++);
            row.createCell(0).setCellValue("Data de Envio da Situação para SETRE/SINE ");
            row.createCell(1).setCellValue(egresso.getVaga().getDataDeEnvioDaSituacaoParaSETRESINE());

            ByteArrayOutputStream bos = new ByteArrayOutputStream();

            workbook.write(bos);
            return new DefaultStreamedContent(new ByteArrayInputStream(bos.toByteArray()), "application/xls", "Vaga." + egresso.getNome().replace(" ", "") + ".xlsx");
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void novaAcao() {
        acao = new Acao();
    }

    public void novoMaterial() {
        carregarLotes();
        materialEgresso = new MaterialEgresso();
    }

    private void carregarLotes() {
        lotes.clear();
        String maxLote = parametroDAO.obterValor("maxLote");
        if (maxLote != null && StringUtils.isNotEmpty(maxLote)) {
            int lote = Integer.parseInt(maxLote);
            for (int i = 1; i <= lote; i++) {
                lotes.add("Lote " + i);
            }
        }
    }

    public RacaCor[] racasCor() {
        return RacaCor.values();
    }

    public void novaAssistenciaSocial() {
        assistenciaSocial = new AssistenciaSocial();
    }

    public Boolean existeMaterial() {
        boolean retorno = false;
        for (MaterialEgresso m : egresso.getMateriais()) {
            if (m.getLote().equals(materialEgresso.getLote())
                    && m.getMaterial().equals(materialEgresso.getMaterial()) && !CoreUtil.isIgual(m.getId(), materialEgresso.getId())) {
                retorno = true;
            }
        }
        if (retorno) {
            Mensagem.lancarMiniPopUp("Duplicado", "Este Material do " + materialEgresso.getLote() + " já foi cadastrado para o beneficiário.");
        }
        return retorno;
    }

    public void adicionarMaterial() {
        if (materialEgresso.getId() != null) {
            if (!this.existeMaterial()) {
                Iterator<MaterialEgresso> materiaisAsIterator = egresso.getMateriais().iterator();
                while (materiaisAsIterator.hasNext()) {
                    MaterialEgresso mat = materiaisAsIterator.next();
                    if (mat.getId() != null && materialEgresso.getId().equals(mat.getId())) {
                        materiaisAsIterator.remove();
                    }
                }
                adicionarMaterialAoEgresso();
            }
        } else if (!this.existeMaterial()) {
            materialEgresso.setEgresso(egresso);
            if (egresso.getMateriais() == null) {
                this.egresso.setMateriais(new ArrayList<>());
            }
            adicionarMaterialAoEgresso();
        }
    }

    private void adicionarMaterialAoEgresso() {
        egresso.getMateriais().add(materialEgresso);
        fecharModal("materialDialog");
    }

    public void excluirAcao(Long id) {
        Acao old = acaoDAO.obterPorPK(id);
        if (old != null) {
            try {
                acaoDAO.excluir(old);
                obterAcoes();
                Mensagem.lancarMensagemInfo("Ação excluida com sucesso!");
            } catch (Exception ex) {
                Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
                Mensagem.lancarMensagemInfo("Erro: " + ex.getMessage());
            }
        }
    }

    public void adicionarAcao() {

        acao.setEgresso(egressoRN.obterPorId(egresso.getId()));
        try {
            acaoDAO.inserir(acao);
            obterAcoes();
            Mensagem.lancarMensagemInfo("Ação adicionada com sucesso!");
        } catch (Exception ex) {
            Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        fecharModal("deleteWG");

    }

    public void alterarAcao() {

        if (acao.getId() != null) {
            try {
                Acao old = acaoDAO.obterPorPK(acao.getId());
                old.setData(acao.getData());
                old.setTipoDeAcao(acao.getTipoDeAcao());
                old.setDescricao(acao.getDescricao());
                acaoDAO.alterar(old);

                obterAcoes();
            } catch (Exception ex) {
                Logger.getLogger(EgressoCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @return
     */
    private void obterAcoes() {
        egresso.setAcoes(acaoDAO.obterAcoesPorEgresso(egresso));
    }

    public void editarMaterial(Long id) {
        carregarLotes();
        materialEgresso = materialEgressoDAO.obterPorPK(id);
        abrirModal("materialDialog");
    }

    public void excluirMaterial() {
        if (itemExcluir != null && itemExcluir.getId() != null) {
            listaExclusaoMaterial.add(itemExcluir);
        }
        egresso.getMateriais().remove(itemExcluir);
        fecharModal("deleteWG");
        Mensagem.lancarMensagemWarn("O item será removido permanentemente somente quando clicar em salvar!");
    }

    public void excluirItem(MaterialEgresso item) {
        itemExcluir = item;
        abrirModal("deleteWG");
    }

    public void adicionarAssistenciaSocial() {
        assistenciaSocial.setEgresso(egresso);
        if (egresso.getAssistenciasSociais() == null) {
            this.egresso.setAssistenciasSociais(new ArrayList<>());
        }
        egresso.getAssistenciasSociais().add(assistenciaSocial);
    }

    private void validarRespostasCursoSuperior() throws BusinessException {
        ValidacaoEgressoImpl validacao = new ValidacaoEgressoImpl();
        validacao.validarRespostasCursoSuperior(egresso);
    }

    public void limparCamposCursos(Integer pos) {
        ValidacaoEgressoImpl validar = new ValidacaoEgressoImpl();
        validar.limparGraduacao(egresso);
        validar.limparCursoTecnico(egresso);
        egresso.setRendaPPEAjuda(null);
        switch (pos) {
            case 1:
                egresso.setCursoSuperior(null);
                egresso.setCursoTecnicoProfissionalizante(null);
                egresso.setPretendeFazerCursoSuperior(null);
                break;
            case 2:
                egresso.setCursoTecnicoProfissionalizante(null);
                egresso.setPretendeFazerCursoSuperior(null);
                break;
        }
    }

    public EnumModalidadeEnsino[] getModalidades() {
        return EnumModalidadeEnsino.values();
    }

    public EnumTipoInstituicaoEnsino[] getTiposInstituicao() {
        return EnumTipoInstituicaoEnsino.values();
    }

    private void carrgearTelefones() {
        telefones = egressoRN.obterTelefones(egresso);
    }

    public void adicionarTelefone() {
        try {
            validarTelefone();
            String res = telefone.getNumero().replaceAll("[^0-9]+", "");
            telefone.setNumero(res);
            telefones.add(telefone);
            telefone = new Telefone(egresso);
        } catch (BusinessException ex) {
            Mensagem.lancarMensagemWarn(ex.getMessage());
        }
    }

    private void validarTelefone() {
        if (telefone.getTipo() == null) {
            throw new BusinessException("Tipo de Telefone é obrigatório");
        }
        if (StringUtils.isEmpty(telefone.getNumero())) {
            throw new BusinessException("Número do telefone é obrigatório");
        }
        if(telefone.getNumero().length() != 13 && telefone.getNumero().length() != 14)
            throw new BusinessException("Número de Telefone está em formato inválido");
        
        for (Telefone tel : telefones) {
            if (tel.getNumero().equalsIgnoreCase(telefone.getNumero())) {
                throw new BusinessException("Número de Telefone já cadastrado");
            }
        }
    }

    public void removerTelefone(Telefone telefone) {
        telefones.remove(telefone);
    }

    public TipoDeAcaoDAO getTipoDeAcaoDAO() {
        return tipoDeAcaoDAO;
    }

    public void setTipoDeAcaoDAO(TipoDeAcaoDAO tipoDeAcaoDAO) {
        this.tipoDeAcaoDAO = tipoDeAcaoDAO;
    }

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

    public List<TipoDeAcao> listagemTipoDeAcao() {
        return tipoDeAcaoDAO.obterLista();
    }

    public List<Material> listagemMaterial() {
        return materialDAO.obterLista();
    }

    public List<TipoAssistenciaSocial> listagemTiposAssistenciaSocial() {
        return tipoAssistenciaSocialDAO.obterLista();
    }

    public List<Lista> listas() {
        return listaDAO.obterLista();
    }

    public ListaDAO getListaDAO() {
        return listaDAO;
    }

    public void setListaDAO(ListaDAO listaDAO) {
        this.listaDAO = listaDAO;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    public AssistenciaSocial getAssistenciaSocial() {
        return assistenciaSocial;
    }

    public void setAssistenciaSocial(AssistenciaSocial assistenciaSocial) {
        this.assistenciaSocial = assistenciaSocial;
    }

    public RHServico getRhServico() {
        return rhServico;
    }

    public void setRhServico(RHServico rhServico) {
        this.rhServico = rhServico;
    }

    public Boolean getBlock() {
        return block;
    }

    public void setBlock(Boolean block) {
        this.block = block;
    }

    public String getBotao() {
        return botao;
    }

    public void setBotao(String botao) {
        this.botao = botao;
    }

    public TipoAssistenciaSocialDAO getTipoAssistenciaSocialDAO() {
        return tipoAssistenciaSocialDAO;
    }

    public void setTipoAssistenciaSocialDAO(TipoAssistenciaSocialDAO tipoAssistenciaSocialDAO) {
        this.tipoAssistenciaSocialDAO = tipoAssistenciaSocialDAO;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
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

    public CategoriaDaSituacaoDAO getCategoriaDAO() {
        return categoriaDAO;
    }

    public void setCategoriaDAO(CategoriaDaSituacaoDAO categoriaDAO) {
        this.categoriaDAO = categoriaDAO;
    }

    public CategoriaDaSituacao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDaSituacao categoria) {
        this.categoria = categoria;
    }

    public List<CategoriaDaSituacao> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDaSituacao> categorias) {
        this.categorias = categorias;
    }

    public boolean isMunicipioVagaAlterado() {
        return municipioVagaAlterado;
    }

    public void setMunicipioVagaAlterado(boolean municipioVagaAlterado) {
        this.municipioVagaAlterado = municipioVagaAlterado;
    }

    public boolean isMunicipioResidenciaAlterado() {
        return municipioResidenciaAlterado;
    }

    public void setMunicipioResidenciaAlterado(boolean municipioResidenciaAlterado) {
        this.municipioResidenciaAlterado = municipioResidenciaAlterado;
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

    public OficioDAO getOficioDAO() {
        return oficioDAO;
    }

    public void setOficioDAO(OficioDAO oficioDAO) {
        this.oficioDAO = oficioDAO;
    }

    public UploadedFile getNovoOficio() {
        return novoOficio;
    }

    public void setNovoOficio(UploadedFile novoOficio) {
        this.novoOficio = novoOficio;
    }

    public List<RegraContratacao> getRegras() {
        return regras;
    }

    public void setRegras(List<RegraContratacao> regras) {
        this.regras = regras;
    }

    public RegraContratacaoDAO getRegraDAO() {
        return regraDAO;
    }

    public void setRegraDAO(RegraContratacaoDAO regraDAO) {
        this.regraDAO = regraDAO;
    }

    public MaterialEgresso getMaterialEgresso() {
        return materialEgresso;
    }

    public void setMaterialEgresso(MaterialEgresso materialEgresso) {
        this.materialEgresso = materialEgresso;
    }

    public MaterialDAO getMaterialDAO() {
        return materialDAO;
    }

    public void setMaterialDAO(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    public TamanhoDAO getTamanhoDAO() {
        return tamanhoDAO;
    }

    public void setTamanhoDAO(TamanhoDAO tamanhoDAO) {
        this.tamanhoDAO = tamanhoDAO;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<Tamanho> getTamanhos() {
        return tamanhos;
    }

    public void setTamanhos(List<Tamanho> tamanhos) {
        this.tamanhos = tamanhos;
    }

    public String getTempoDeContrato() {
        return tempoDeContrato;
    }

    public void setTempoDeContrato(String tempoDeContrato) {
        this.tempoDeContrato = tempoDeContrato;
    }

    public UnidadeDeMedidaDAO getUnidadeDAO() {
        return unidadeDAO;
    }

    public void setUnidadeDAO(UnidadeDeMedidaDAO unidadeDAO) {
        this.unidadeDAO = unidadeDAO;
    }

    public List<UnidadeDeMedida> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadeDeMedida> unidades) {
        this.unidades = unidades;
    }

    public EscritorioRegional getEscritorioRegional() {
        return escritorioRegional;
    }

    public void setEscritorioRegional(EscritorioRegional escritorioRegional) {
        this.escritorioRegional = escritorioRegional;
    }

    public Boolean getBlockDocPendente() {
        return blockDocPendente;
    }

    public void setBlockDocPendente(Boolean blockDocPendente) {
        this.blockDocPendente = blockDocPendente;
    }

    public MaterialEgressoDAO getMaterialEgressoDAO() {
        return materialEgressoDAO;
    }

    public void setMaterialEgressoDAO(MaterialEgressoDAO materialEgressoDAO) {
        this.materialEgressoDAO = materialEgressoDAO;
    }

    public MaterialEgresso getItemExcluir() {
        return itemExcluir;
    }

    public void setItemExcluir(MaterialEgresso itemExcluir) {
        this.itemExcluir = itemExcluir;
    }

    public List<String> getLotes() {
        return lotes;
    }

    public void setLotes(List<String> lotes) {
        this.lotes = lotes;
    }

    public ParametroDAO getParametroDAO() {
        return parametroDAO;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    public AcaoDAO getAcaoDAO() {
        return acaoDAO;
    }

    public void setAcaoDAO(AcaoDAO acaoDAO) {
        this.acaoDAO = acaoDAO;
    }

    public SituacaoFuncionarioEnum getSituacaoDominio() {
        return situacaoDominio;
    }

    public void setSituacaoDominio(SituacaoFuncionarioEnum situacaoDominio) {
        this.situacaoDominio = situacaoDominio;
    }

    public ColaboradorDAO getColaboradorDAO() {
        return colaboradorDAO;
    }

    public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
        this.colaboradorDAO = colaboradorDAO;
    }

    public DocumentoDoEgressoRN getDocumentoDoEgressoRN() {
        return documentoDoEgressoRN;
    }

    public void setDocumentoDoEgressoRN(DocumentoDoEgressoRN documentoDoEgressoRN) {
        this.documentoDoEgressoRN = documentoDoEgressoRN;
    }

    public OficioRN getOficioRN() {
        return oficioRN;
    }

    public void setOficioRN(OficioRN oficioRN) {
        this.oficioRN = oficioRN;
    }

    public EgressoRN getEgressoRN() {
        return egressoRN;
    }

    public void setEgressoRN(EgressoRN egressoRN) {
        this.egressoRN = egressoRN;
    }

    public Telefone getTelefone() {
        return telefone;
    }

    public void setTelefone(Telefone telefone) {
        this.telefone = telefone;
    }

    public EnumTipoTelefone[] getTiposTelefone() {
        return EnumTipoTelefone.values();
    }

    public List<Telefone> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<Telefone> telefones) {
        this.telefones = telefones;
    }

}

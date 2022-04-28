package br.org.flem.primeiroemprego.mb;

import br.org.flem.fw.persistencia.dao.dominiosistemas.DominioFuncionarioDAO;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.dao.CategoriaDaSituacaoDAO;
import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dao.EscritorioRegionalDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.dao.TerritorioDAO;
import br.org.flem.primeiroemprego.dto.FiltroFrequencia;
import br.org.flem.primeiroemprego.dto.ListaFrequenciaDTO;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.Territorio;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.text.MaskFormatter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 * @Classe para gerenciar os relatórios de Frequência
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class RelatorioFrequenciaMB implements Serializable {

    @ManagedProperty(value = "#{egressoDAO}")
    private EgressoDAO egressoDAO;

    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;

    @ManagedProperty(value = "#{escritorioRegionalDAO}")
    private EscritorioRegionalDAO escritorioRegionalDAO;

    @ManagedProperty(value = "#{territorioDAO}")
    private TerritorioDAO territorioDAO;

    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;

    @ManagedProperty(value = "#{categoriaDaSituacaoDAO}")
    private CategoriaDaSituacaoDAO categoriaDaSituacaoDAO;

    private List<EscritorioRegional> escritoriosRegionais;
    private List<EscritorioRegional> escritorioSelecionados;
    private List<Municipio> municipios;
    private List<Municipio> municipiosSelecionados;
    private List<Territorio> territorios;
    private List<Territorio> territoriosSelecionados;

    private List<Demandante> demandantes;
    private List<Demandante> demandanteSelecionados;
    private List<CategoriaDaSituacao> categorias;
    private List<CategoriaDaSituacao> categoriaSelecionadas;

    private FiltroFrequencia filtro;

    private List<ListaFrequenciaDTO> listaImpressao = new ArrayList<>();
    private boolean pesquisaAtiva = false;

    //Dados para impressão do cabeçalho de evento
    private String nomeEvento;
    private String localEvento;
    private Date dataEvento;

    private Integer modeloRelatorio;
    private boolean selectEsc;
    private boolean selectTer;
    private boolean selectMun;
    private boolean selectDem;
    private boolean selectCat;

    private UploadedFile arquivoXLS;

    private Integer progresso = 0;
    private String mensagem = "";
    private Integer quantidade;
    
    private List<String> cpfs;

    @PostConstruct
    public void init() {
        this.obterEscritoriosRegionais();
        this.obterCategoria();
    }

    public void pesquisar() {
        preencherFiltro();
        listaImpressao = egressoDAO.obterListaDeFrequencia(filtro);
        this.obterDadosFuncionarios();
        pesquisaAtiva = true;

    }

    /**
     * Carregar dados do funcionário para exibir no relatório
     */
    private void obterDadosFuncionarios() {
        if (!listaImpressao.isEmpty()) {
            for (ListaFrequenciaDTO dto : listaImpressao) {
                if (StringUtils.isNotEmpty(dto.getMatricula()) && "CONTRATADO".equalsIgnoreCase(dto.getCategoria())) {
                    Integer matricula = Integer.parseInt(dto.getMatricula());
                    Funcionario funcionario = new DominioFuncionarioDAO().obterPorMatricula(matricula);
                    if (funcionario != null) {
                        dto.setDataContratacao((String) CoreUtil.nvl(Data.formataData(funcionario.getAdmissao(), "dd/MM/yyyy"), " "));
                    }
                }
            }
        }
    }

    /**
     * Limpa pesquisa e retorna a tela em estado default
     */
    public void limpar() {
        this.limparCombos();
        this.obterEscritoriosRegionais();
        pesquisaAtiva = false;
    }

    /**
     * Limpar todos os items selecionados nos combos
     */
    private void limparCombos() {
        this.escritorioSelecionados = new ArrayList<>();
        this.territoriosSelecionados = new ArrayList<>();
        this.municipiosSelecionados = new ArrayList<>();
        this.categoriaSelecionadas = new ArrayList<>();
        this.demandanteSelecionados = new ArrayList<>();
        this.selectDem = false;
        this.selectEsc = false;
        this.selectMun = false;
        this.selectTer = false;
        this.selectCat = false;
        this.arquivoXLS = null;
        this.cpfs = new ArrayList<>();
        progresso = 0;
        mensagem = "";
        quantidade = 0;
        
    }

    /**
     * imprimiro relatérios
     *
     * @return StreamedContent
     */
    public StreamedContent imprimir() {

        if (listaImpressao.isEmpty()) {
            Mensagem.lancarMensagemInfo("Não existem registro a serem impressos");
        } else {
            try {
                if (this.modeloRelatorio == null || this.modeloRelatorio == 0) {
                    Mensagem.lancarMensagemInfo("Por favor selecione um modelo de relatório a ser impresso.");
                    return null;
                } else {
                    return gerarIreport();
                }

            } catch (Exception ex) {
                Mensagem.lancarMensagemErro("Erro ao gerar relatório");
                Logger.getLogger(RelatorioFrequenciaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    /**
     * @Preencher Objeto filtro para pesquisa
     * @author <code>tscortes@flem.org.br</code>
     */
    private void preencherFiltro() {
        filtro = new FiltroFrequencia();
        filtro.setCpfs(cpfs);
        if( cpfs == null || cpfs.isEmpty()){
            filtro.setDemandantes((List<Demandante>) CoreUtil.nvl(demandanteSelecionados, demandantes));
            filtro.setEscritoriosRegionais((List<EscritorioRegional>) CoreUtil.nvl(escritorioSelecionados, escritoriosRegionais));
            filtro.setTerritorios((List<Territorio>) CoreUtil.nvl(territoriosSelecionados, territorios));
            filtro.setMunicipios((List<Municipio>) CoreUtil.nvl(municipiosSelecionados, municipios));
            filtro.setCategorias((List<CategoriaDaSituacao>) CoreUtil.nvl(categoriaSelecionadas, categorias));
        }
    }

    /**
     * Gerar arquivo para ser impresso
     *
     * @return StreamedContent
     * @throws JRException
     * @throws ParseException
     * @throws Exception
     */
    public StreamedContent gerarIreport() throws JRException, ParseException, Exception {

        Map<String, Object> parametros = new HashMap();
        URL a;
        if (modeloRelatorio == null) {
            a = getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/relatorios/listaDeFrequencia.jasper");
        } else {
            switch (modeloRelatorio) {
                case 1:
                    a = getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/relatorios/listaDeFrequencia.jasper");
                    break;
                case 2:
                    Collections.sort(listaImpressao);
                    a = getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/relatorios/listaDeFrequenciaModelo2.jasper");
                    break;
                case 3:
                    a = getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/relatorios/listaDeFrequenciaModelo3.jasper");
                    break;
                default:
                    a = getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/relatorios/listaDeFrequencia.jasper");
                    break;
            }
        }

        parametros.put("logo", getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/images/Marca_FLEM_horizontal.png").getFile());
        parametros.put("LOGO_PRIM_EMPREGO", getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/images/PP_GOV_2019.png").getFile());

        parametros.put("NOME_EVENTO", nomeEvento);
        parametros.put("LOCAL_EVENTO", localEvento);
        parametros.put("DATA_EVENTO", dataEvento);

        JasperReport report = (JasperReport) JRLoader.loadObject(a);
        JasperPrint print = JasperFillManager.fillReport(report, parametros, new JRBeanCollectionDataSource(listaImpressao));
        byte[] b = JasperExportManager.exportReportToPdf(print);
        ByteArrayInputStream c = new ByteArrayInputStream(b);
        return new DefaultStreamedContent(c, "pdf", "Lista de Presenca.pdf");

    }

    /**
     * Obter escritórios regionais
     */
    private void obterEscritoriosRegionais() {
        escritoriosRegionais = escritorioRegionalDAO.obterListaEscritorio();
        EscritorioRegional escr = new EscritorioRegional();
        escr.setId(0l);
        escr.setNome("Escritório Não Implementado");
        escritoriosRegionais.add(0, escr);
        this.obterTerritorios();
    }

    /**
     * obtem a lista de territórios<br>
     *
     * @author ermouta<br>
     */
    public void obterTerritorios() {
        if (this.escritorioSelecionados != null && this.escritorioSelecionados.size() > 0) {
            this.territorios = this.territorioDAO.obterTerririoPorEscritorioRegional(escritoriosRegionais);
            if (escritorioNulo()) {
                this.territorios.addAll(this.territorioDAO.obterTerritorioPorEscritorioRegionalNaoImpl());
            }
        } else {
            this.territorios = this.territorioDAO.obterTerririo();
        }

        this.obterMunicipioETerritorios();
    }

    /**
     * Obter municipios
     */
    public void obterMunicipios() {
        this.obterMunicipioETerritorios();
    }

    private void obterMunicipioETerritorios() {
        if (this.territoriosSelecionados != null && territoriosSelecionados.size() > 0) {
            this.municipios = this.municipioDAO.obterPorTerritorio(territoriosSelecionados);
        } else if (this.escritorioSelecionados != null && this.escritorioSelecionados.size() > 0) {
            if (this.escritorioSelecionados.get(0).getId() == 0L) {
                this.municipiosSelecionados = new ArrayList<>();
                this.municipios = this.municipioDAO.obterPorEscritorioNaoImpl();
                this.territorios = this.territorioDAO.obterTerritorioPorEscritorioRegionalNaoImpl();
            } else {
                this.municipios = this.municipioDAO.obterPorEscritorioRegional(escritorioSelecionados);
                this.territorios = this.territorioDAO.obterTerririoPorEscritorioRegional(escritorioSelecionados);
                this.municipiosSelecionados = new ArrayList<>();
            }
        } else if (this.escritorioSelecionados == null || this.escritorioSelecionados.isEmpty()) {
            this.municipios = this.municipioDAO.obterMunicipios();
            this.territorios = this.territorioDAO.obterTerririo();
        } else {
            this.municipios = municipioDAO.obterPorEscritorioRegional(escritorioSelecionados);
            this.territorios = this.territorioDAO.obterTerririoPorEscritorioRegional(escritorioSelecionados);
        }
        obterDemandantes();
    }

    /**
     * Caso o escritório não implementado seja selecionado, deve trazer
     * registros não associados a escritórios
     *
     * @return boolean
     */
    private boolean escritorioNulo() {
        boolean escIsNull = false;
        for (EscritorioRegional esc : escritorioSelecionados) {
            if (esc == null) {
                escIsNull = true;
            }
        }
        return escIsNull;
    }

    /**
     * Obter demandantes
     */
    public void obterDemandantes() {

        if (this.municipiosSelecionados != null && municipiosSelecionados.size() > 0) {
            this.demandantes = this.demandanteDAO.obterDemandantePorMunicio(this.municipiosSelecionados);
        } else {
            this.demandantes = this.demandanteDAO.obterDemandantePorMunicio(this.municipios);
        }
    }

    /**
     * obtem lista de categorias<br>
     *
     * @author ermouta<br>
     */
    private void obterCategoria() {
        this.categorias = this.categoriaDaSituacaoDAO.obterCategoriaDaSituacao();
    }

    public void onSelectAll(Integer val) {
        switch (val) {
            case 1:
                if (selectEsc) {
                    escritorioSelecionados = new ArrayList<>();
                    for (EscritorioRegional esc : escritoriosRegionais) {
                        escritorioSelecionados.add(esc);
                    }
                } else {
                    escritorioSelecionados.clear();
                }
                this.obterTerritorios();
                break;
            case 2:
                if (selectTer) {
                    territoriosSelecionados = new ArrayList<>();
                    for (Territorio ter : territorios) {
                        territoriosSelecionados.add(ter);
                    }
                } else {
                    territoriosSelecionados.clear();
                }
                this.obterMunicipioETerritorios();
                break;
            case 3:
                if (selectMun) {
                    municipiosSelecionados = new ArrayList<>();
                    for (Municipio mun : municipios) {
                        municipiosSelecionados.add(mun);
                    }
                } else {
                    municipiosSelecionados.clear();
                }
                this.obterDemandantes();
                break;
            case 4:
                if (selectDem) {
                    demandanteSelecionados = new ArrayList<>();
                    for (Demandante mun : demandantes) {
                        demandanteSelecionados.add(mun);
                    }
                } else {
                    demandanteSelecionados.clear();
                }
                break;
            case 5:
                if (selectCat) {
                    categoriaSelecionadas = new ArrayList<>();
                    for (CategoriaDaSituacao mun : categorias) {
                        categoriaSelecionadas.add(mun);
                    }
                } else {
                    categoriaSelecionadas.clear();
                }
                break;
        }
    }

    public void carregarEgressosPorCpf() {
        if (arquivoXLS != null) {
            try {

                DataFormatter formatador = new DataFormatter();

                try (Workbook workbook = new XSSFWorkbook(arquivoXLS.getInputstream())) {
                    Sheet planilha = workbook.getSheetAt(0);
                    
                    int indexLinha = 1;
                    Row row = planilha.getRow(0);
                    Cell coluna = row.getCell(0);
                    String nomeColuna = formatador.formatCellValue(coluna).trim();
                    
                    if (nomeColuna.toLowerCase().equals("cpf")) {
                        
                        MaskFormatter mascaraCPF = new MaskFormatter("###.###.###-##");
                        mascaraCPF.setValueContainsLiteralCharacters(false);
                        
                        row = planilha.getRow(indexLinha++);
                        quantidade = planilha.getLastRowNum();
                        resetarProgresso();
                        cpfs = new ArrayList<>();
                        while (row != null) {
                            
                            String value = obterLinha(row, formatador);
                            if (StringUtils.isNotEmpty(value)) {
                                cpfs.add(value);
                            }
                            mensagem = "importados " + (indexLinha - 1) + " de " + quantidade;
                            atualizarProgresso(indexLinha);
                            row = planilha.getRow(indexLinha++);
                        }
                        pesquisar();
                        Mensagem.lancar("Importação concluída");
                    } else {
                        Mensagem.lancarMensagemErro("Coluna única precisa ser o CPF");
                    }
                }
            } catch (IOException | ParseException e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
                Mensagem.lancarMensagemErro("Erro ao proceder a importação");
            }

        } else {
            Mensagem.lancarMensagemErro("Arquivo não informado ou inválido");
        }
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
        } catch (InterruptedException ex) {
            criarMensagem("erro ");
        }
    }

    private String obterLinha(Row row, DataFormatter formatador) {
        return formatador.formatCellValue(row.getCell(0)).trim();
    }
    
    public void onTabChange(TabChangeEvent event) {
        limpar();
    }

    //GET AND SET
    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public EscritorioRegionalDAO getEscritorioRegionalDAO() {
        return escritorioRegionalDAO;
    }

    public void setEscritorioRegionalDAO(EscritorioRegionalDAO escritorioRegionalDAO) {
        this.escritorioRegionalDAO = escritorioRegionalDAO;
    }

    public List<EscritorioRegional> getEscritoriosRegionais() {
        return escritoriosRegionais;
    }

    public void setEscritoriosRegionais(List<EscritorioRegional> escritoriosRegionais) {
        this.escritoriosRegionais = escritoriosRegionais;
    }

    public List<EscritorioRegional> getEscritorioSelecionados() {
        return escritorioSelecionados;
    }

    public void setEscritorioSelecionados(List<EscritorioRegional> escritorioSelecionados) {
        this.escritorioSelecionados = escritorioSelecionados;
    }

    public List<ListaFrequenciaDTO> getListaImpressao() {
        return listaImpressao;
    }

    public void setListaImpressao(List<ListaFrequenciaDTO> listaImpressao) {
        this.listaImpressao = listaImpressao;
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

    public List<Municipio> getMunicipiosSelecionados() {
        return municipiosSelecionados;
    }

    public void setMunicipiosSelecionados(List<Municipio> municipiosSelecionados) {
        this.municipiosSelecionados = municipiosSelecionados;
    }

    public boolean isPesquisaAtiva() {
        return pesquisaAtiva;
    }

    public void setPesquisaAtiva(boolean pesquisaAtiva) {
        this.pesquisaAtiva = pesquisaAtiva;
    }

    public List<Territorio> getTerritorios() {
        return territorios;
    }

    public void setTerritorios(List<Territorio> territorios) {
        this.territorios = territorios;
    }

    public TerritorioDAO getTerritorioDAO() {
        return territorioDAO;
    }

    public void setTerritorioDAO(TerritorioDAO territorioDAO) {
        this.territorioDAO = territorioDAO;
    }

    public List<Territorio> getTerritoriosSelecionados() {
        return territoriosSelecionados;
    }

    public void setTerritoriosSelecionados(List<Territorio> territoriosSelecionados) {
        this.territoriosSelecionados = territoriosSelecionados;
    }

    public List<Demandante> getDemandantes() {
        return demandantes;
    }

    public void setDemandantes(List<Demandante> demandantes) {
        this.demandantes = demandantes;
    }

    public List<Demandante> getDemandanteSelecionados() {
        return demandanteSelecionados;
    }

    public void setDemandanteSelecionados(List<Demandante> demandanteSelecionados) {
        this.demandanteSelecionados = demandanteSelecionados;
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
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

    public List<CategoriaDaSituacao> getCategoriaSelecionadas() {
        return categoriaSelecionadas;
    }

    public void setCategoriaSelecionadas(List<CategoriaDaSituacao> categoriaSelecionadas) {
        this.categoriaSelecionadas = categoriaSelecionadas;
    }

    public String getNomeEvento() {
        return nomeEvento;
    }

    public void setNomeEvento(String nomeEvento) {
        this.nomeEvento = nomeEvento;
    }

    public String getLocalEvento() {
        return localEvento;
    }

    public void setLocalEvento(String localEvento) {
        this.localEvento = localEvento;
    }

    public Date getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(Date dataEvento) {
        this.dataEvento = dataEvento;
    }

    public Integer getModeloRelatorio() {
        return modeloRelatorio;
    }

    public void setModeloRelatorio(Integer modeloRelatorio) {
        this.modeloRelatorio = modeloRelatorio;
    }

    public boolean isSelectEsc() {
        return selectEsc;
    }

    public void setSelectEsc(boolean selectEsc) {
        this.selectEsc = selectEsc;
    }

    public boolean isSelectTer() {
        return selectTer;
    }

    public void setSelectTer(boolean selectTer) {
        this.selectTer = selectTer;
    }

    public boolean isSelectMun() {
        return selectMun;
    }

    public void setSelectMun(boolean selectMun) {
        this.selectMun = selectMun;
    }

    public boolean isSelectDem() {
        return selectDem;
    }

    public void setSelectDem(boolean selectDem) {
        this.selectDem = selectDem;
    }

    public boolean isSelectCat() {
        return selectCat;
    }

    public void setSelectCat(boolean selectCat) {
        this.selectCat = selectCat;
    }

    public UploadedFile getArquivoXLS() {
        return arquivoXLS;
    }

    public void setArquivoXLS(UploadedFile arquivoXLS) {
        this.arquivoXLS = arquivoXLS;
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
    
    

}

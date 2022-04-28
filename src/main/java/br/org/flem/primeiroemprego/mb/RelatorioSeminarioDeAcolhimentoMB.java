package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dao.VwAcolhimentoDetalhadoDAO;
import br.org.flem.primeiroemprego.dto.FiltroFrequencia;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.VwAcolhimentoDetalhado;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.swing.text.MaskFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.UploadedFile;

/**
 * @Classe para gerenciar os relatórios de Frequência
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class RelatorioSeminarioDeAcolhimentoMB implements Serializable {

    @ManagedProperty(value = "#{egressoDAO}")
    private EgressoDAO egressoDAO;
    
    @ManagedProperty(value = "#{vwAcolhimentoDetalhadoDAO}")
    private VwAcolhimentoDetalhadoDAO vwAcolhimentoDetalhadoDAO;

    private List<EscritorioRegional> escritoriosRegionais;
    private List<EscritorioRegional> escritorioSelecionados;
    private List<Municipio> municipios;
    private List<Municipio> municipiosSelecionados;
    private List<Demandante> demandantes;
    private List<Demandante> demandanteSelecionados;
    private List<CategoriaDaSituacao> categorias;
    private List<CategoriaDaSituacao> categoriaSelecionadas;

    private FiltroFrequencia filtro;

    private List<VwAcolhimentoDetalhado> listaImpressao = new ArrayList<>();
    private boolean pesquisaAtiva = false;

    private boolean selectEsc;
    private boolean selectMun;
    private boolean selectDem;
    private boolean selectCat;
    
    private UploadedFile arquivoXLS;

    private Integer progresso = 0;
    private String mensagem = "";
    private Integer quantidade;
    
    private List<String> cpfs;
    
    private String acolhido;


    @PostConstruct
    public void init() {
        this.obterEscritoriosRegionais();
    }
    /**
     * Pesquisar
     */
    public void pesquisar() {
        preencherFiltro();
        listaImpressao = vwAcolhimentoDetalhadoDAO.obterListaDeAcolhimento(filtro);
        pesquisaAtiva = true;

    }

    /**
     * Limpa pesquisa e retorna a tela em estado default
     */
    
    public void limpar() {
        acolhido = "";
        this.limparCombos();
        this.obterEscritoriosRegionais();
        pesquisaAtiva = false;
    }

    /**
     * Limpar todos os items selecionados nos combos
     */
    private void limparCombos() {
        this.escritorioSelecionados = new ArrayList<>();
        this.municipiosSelecionados = new ArrayList<>();
        this.categoriaSelecionadas = new ArrayList<>();
        this.demandanteSelecionados = new ArrayList<>();
        this.selectEsc = false;
        this.selectMun = false;
        this.selectCat = false;
        this.selectDem = false;
        this.arquivoXLS = null;
        this.cpfs = new ArrayList<>();
        progresso = 0;
        mensagem = "";
        quantidade = 0;
        
    }

    /**
     * @Preencher Objeto filtro para pesquisa
     * @author <code>tscortes@flem.org.br</code>
     */
    private void preencherFiltro() {
        filtro = new FiltroFrequencia();
        filtro.setCpfs(cpfs);
        if( cpfs == null || cpfs.isEmpty()){
            if( escritorioSelecionados == null || escritorioSelecionados.isEmpty() ){
                filtro.setEscritoriosRegionais(escritoriosRegionais);
            }else{
                filtro.setEscritoriosRegionais(escritorioSelecionados);
            }
            
            if( municipiosSelecionados == null || municipiosSelecionados.isEmpty() ){
                filtro.setMunicipios(municipios);
            }else{
                filtro.setMunicipios( municipiosSelecionados);
            }
            
            if( demandanteSelecionados == null || demandanteSelecionados.isEmpty() ){
                filtro.setDemandantes(demandantes);
            }else{
                filtro.setDemandantes(demandanteSelecionados);
            }
            
            filtro.setCategorias(categoriaSelecionadas);
            filtro.setAcolhido(acolhido);
        }

    }

    /**
     * Obter escritórios regionais
     */
    private void obterEscritoriosRegionais() {
        escritorioSelecionados = new ArrayList<>();
        escritoriosRegionais = vwAcolhimentoDetalhadoDAO.obterEscritorios();
        this.obterMunicipios();
    }

    /**
     * Obter Municipios
     */
    public void obterMunicipios() {
        List<Long> ids = new ArrayList<>();
        this.municipiosSelecionados = new ArrayList<>();
        if (this.escritorioSelecionados != null && this.escritorioSelecionados.size() > 0) {
            if (this.escritorioSelecionados.get(0).getId() == 0L) {
                this.municipios = this.vwAcolhimentoDetalhadoDAO.obterPorEscritorioNaoImpl();
            } else {
                this.escritorioSelecionados.forEach(esc -> { if( esc.getId() != null ){ ids.add(esc.getId());} });
                this.municipios = this.vwAcolhimentoDetalhadoDAO.obterMunicipiosPorEscritorios(ids);
                this.municipiosSelecionados = new ArrayList<>();
            }
        } else {
            this.escritoriosRegionais.forEach(esc -> { if( esc.getId() != null ){ ids.add(esc.getId());} });
            this.municipios = this.vwAcolhimentoDetalhadoDAO.obterMunicipiosPorEscritorios(ids);
        }
        obterDemandantes();
    }
    
    /**
     * Obter demandantes
     */
    public void obterDemandantes() {
        List<Long> ids;
        demandanteSelecionados = new ArrayList<>();
        if (this.municipiosSelecionados != null && municipiosSelecionados.size() > 0) {
            ids = this.municipiosSelecionados.stream()
                    .map(Municipio::getId)
                    .collect(Collectors.toList());
        } else {
            ids = this.municipios.stream()
                    .map(Municipio::getId)
                    .collect(Collectors.toList());
        }
        this.demandantes = this.vwAcolhimentoDetalhadoDAO.obterDemandantesPorMunicipios(ids);
        obterCategoria();
    }
    
    /**
     * obtem lista de categorias<br>
     *
     * @author ermouta<br>
     */
    public void obterCategoria() {
        List<Long> ids = new ArrayList<>();
        List<Long> idsMunicipios = new ArrayList<>();
        categoriaSelecionadas = new ArrayList<>();
        if (this.demandanteSelecionados != null && demandanteSelecionados.size() > 0) {
            this.demandanteSelecionados.forEach(esc -> ids.add(esc.getId()));
        } else {
            this.demandantes.forEach(esc -> ids.add(esc.getId()));
        }
        if (this.municipiosSelecionados != null && municipiosSelecionados.size() > 0) {
            this.municipiosSelecionados.forEach(esc -> idsMunicipios.add(esc.getId()));
        } else {
            this.municipios.forEach(esc -> idsMunicipios.add(esc.getId()));
        }
        this.categorias = vwAcolhimentoDetalhadoDAO.obterCategoriasAcolhimento(ids, idsMunicipios);
        
    }

    /**
     * Selecionar todos os registros a partir de um combo
     * @param val 
     */
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
                this.obterMunicipios();
                break;
            case 2:
                if (selectMun) {
                    municipiosSelecionados = new ArrayList<>();
                    for (Municipio mun : municipios) {
                        municipiosSelecionados.add(mun);
                    }
                } else {
                    municipiosSelecionados.clear();
                }
                break;
            case 3:
                if (selectDem) {
                    demandanteSelecionados = new ArrayList<>();
                    for (Demandante mun : demandantes) {
                        demandanteSelecionados.add(mun);
                    }
                } else {
                    demandanteSelecionados.clear();
                }
                break;
            case 4:
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
    /**
     * Carregar Beneficiários por CPF
     */
    public void carregarEgressosPorCpf() {
        if (arquivoXLS != null) {
            try {

                DataFormatter formatador = new DataFormatter();

                Workbook workbook = new XSSFWorkbook(arquivoXLS.getInputstream());
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
                workbook.close();
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

    public List<VwAcolhimentoDetalhado> getListaImpressao() {
        return listaImpressao;
    }

    public void setListaImpressao(List<VwAcolhimentoDetalhado> listaImpressao) {
        this.listaImpressao = listaImpressao;
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

    public boolean isSelectEsc() {
        return selectEsc;
    }

    public void setSelectEsc(boolean selectEsc) {
        this.selectEsc = selectEsc;
    }

    public boolean isSelectMun() {
        return selectMun;
    }

    public void setSelectMun(boolean selectMun) {
        this.selectMun = selectMun;
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

    public VwAcolhimentoDetalhadoDAO getVwAcolhimentoDetalhadoDAO() {
        return vwAcolhimentoDetalhadoDAO;
    }

    public void setVwAcolhimentoDetalhadoDAO(VwAcolhimentoDetalhadoDAO vwAcolhimentoDetalhadoDAO) {
        this.vwAcolhimentoDetalhadoDAO = vwAcolhimentoDetalhadoDAO;
    }

    public String getAcolhido() {
        return acolhido;
    }

    public void setAcolhido(String acolhido) {
        this.acolhido = acolhido;
    }
    
    
}

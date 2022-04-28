package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.AcaoDAO;
import br.org.flem.primeiroemprego.dao.TipoDeAcaoDAO;
import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.RelatorioAcoesDTO;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
import br.org.flem.primeiroemprego.util.LazyGenericDataModel;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class RelatorioAcoesMB implements Serializable{
    
    private static final String PATH = "/br/org/flem/primeiroemprego/relatorios/acoes.jasper";
    private TipoDeAcao tipoDeAcao;
    private List<RelatorioAcoesDTO> acoes;
    private List<TipoDeAcao> tiposDeAcao;

    @ManagedProperty(value = "#{tipoDeAcaoDAO}")
    private TipoDeAcaoDAO tipoDeAcaoDAO;

    @ManagedProperty(value = "#{acaoDAO}")
    private AcaoDAO acaoDAO;
    
    private LazyGenericDataModel<RelatorioAcoesDTO> dataModel;

    @PostConstruct
    public void init(){
        acoes = acaoDAO.obterRelatorioAcoesDTOs();
        dataModel = new LazyGenericDataModel<>(acoes);
        tiposDeAcao = tipoDeAcaoDAO.obterLista();
    }
    
    //Gera o relatório
    public StreamedContent imprimir(){
        try{
            Map<String, Object> parametros = new HashMap();
            
            URL a = getClass().getClassLoader().getResource(PATH);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject( a );
            parametros.put("FOOTER_FLEM", "<span style=\"font-size: 6px;\">Rua Visconde de Itaborahy, nº 845, Ed. Amaralina Empresarial - CEP.: 41.900-031 - Salvador / Bahia - Tel.: +55 71 3103-7500 E-mail: flem@flem.org.br</span>");
            parametros.put("LOGO_FLEM", getClass().getClassLoader().getResource("br/org/flem/primeiroemprego/images/Marca_FLEM_horizontal.png").getFile());
            List<RelatorioAcoesDTO> listaImpressao = acaoDAO.obterListaImpressao(dataModel.getFilters());
            JasperPrint print = JasperFillManager.fillReport(jasperReport, parametros, new JRBeanCollectionDataSource(listaImpressao));
            byte[] b = JasperExportManager.exportReportToPdf(print);
            ByteArrayInputStream c = new ByteArrayInputStream(b);
            return new DefaultStreamedContent(c, "pdf", "acoes.pdf");
        }catch(Exception ex){
            Mensagem.lancarMensagemErro("Erro ao gerar relatório");
            ex.printStackTrace();
        }
        return null;

    }
    /**
     * Excluir Ação
     * @param acao 
     */
    public void excluir(Acao acao){
        Acao excluir = acaoDAO.obterPorPK(acao.getId());
        if( excluir != null){
            try {
                acaoDAO.excluir(acao);
                Mensagem.lancar("Ação excluída com sucesso!");
            } catch (Exception ex) {
                Logger.getLogger(RelatorioAcoesMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public TipoDeAcao getTipoDeAcao() {
        return tipoDeAcao;
    }

    public void setTipoDeAcao(TipoDeAcao tipoDeAcao) {
        this.tipoDeAcao = tipoDeAcao;
    }

    public List<TipoDeAcao> getTiposDeAcao() {
        return tiposDeAcao;
    }

    public void setTiposDeAcao(List<TipoDeAcao> tiposDeAcao) {
        this.tiposDeAcao = tiposDeAcao;
    }

    public TipoDeAcaoDAO getTipoDeAcaoDAO() {
        return tipoDeAcaoDAO;
    }

    public void setTipoDeAcaoDAO(TipoDeAcaoDAO tipoDeAcaoDAO) {
        this.tipoDeAcaoDAO = tipoDeAcaoDAO;
    }

    public AcaoDAO getAcaoDAO() {
        return acaoDAO;
    }

    public void setAcaoDAO(AcaoDAO acaoDAO) {
        this.acaoDAO = acaoDAO;
    }

    public LazyGenericDataModel<RelatorioAcoesDTO> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyGenericDataModel<RelatorioAcoesDTO> dataModel) {
        this.dataModel = dataModel;
    }

    public List<RelatorioAcoesDTO> getAcoes() {
        return acoes;
    }

    public void setAcoes(List<RelatorioAcoesDTO> acoes) {
        this.acoes = acoes;
    }
    
    

}

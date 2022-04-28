package br.org.flem.primeiroemprego.util.entity;

import br.org.flem.primeiroemprego.dao.AcaoDAO;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.dao.OficioDAO;
import br.org.flem.primeiroemprego.dao.TipoDeAcaoDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.ArquivoUtil;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.JPAUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 *
 * @author emsilva
 */
@ViewScoped
public class GerarOficiosUtil implements Runnable, Serializable {

    private final Long idModeloDeOficio;

    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService = new FileStorageService();

    public GerarOficiosUtil(Long idModeloDeOficio) {
        this.idModeloDeOficio = idModeloDeOficio;
    }

    @Override
    public void run() {
        
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = null;
        List<String> substituicoes = new ArrayList<>();
        ModeloDeOficioDAO modeloDeOficioDAO = new ModeloDeOficioDAO();
        OficioDAO oficioDAO = new OficioDAO();
        AcaoDAO acaoDAO = new AcaoDAO();
        Date dataGeracaoModeloOficio = new Date();
        TipoDeAcao tipoDeAcaoOficio = new TipoDeAcaoDAO().obterPorPK(4l);
        //Necessário obter um objeto do ModeloDeOficio que esteja na sessão do hibernate
        ModeloDeOficio modeloDeOficio = modeloDeOficioDAO.obterPorPK(idModeloDeOficio);

        try {
            //Foi necessário obter apenas o id do egresso para solucionar o problema e Java Heap Space
            List<BigInteger> idsEgresso = new EgressoDAO().obterEgressosPorModeloDeOficio(modeloDeOficio);
            for (BigInteger id : idsEgresso) {
                Response response = fileStorageService.getFileByPath(modeloDeOficio.getFilePath());
                try (InputStream stream = response.readEntity(InputStream.class)) {
                    XWPFDocument document = new XWPFDocument(stream);
                    Egresso egresso = new EgressoDAO().obterPorPK(Long.parseLong("" + id));
                    if (scet == null) {
                        scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(new XWPFWordExtractor(document).getText());
                        while (scet.procurarSubstituicao()) {
                            substituicoes.add(scet.textoParaSubstituir());
                        }
                    }
                    Oficio oficio = new Oficio();
                    oficio.setEgresso(egresso);
                    oficio.setTipo("application/pdf");
                    oficio.setModelo(modeloDeOficio);
                    oficio.setAssunto(modeloDeOficio.getAssunto());
                    synchronized (OficioDAO.LOCK) {
                        oficioDAO.atribuirNumeroDoOficio(oficio);
                        if (substituicoes.contains("Ofício")) {
                            replace(document, "{Ofício}", oficio.getIdentificacao());
                        }
                        for (String substituicao : substituicoes) {
                            replace(document, "{" + substituicao + "}", CamposEgressosUtil.getInformacao(egresso, substituicao));
                        }
                        PdfOptions options = PdfOptions.create();
                        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
                        PdfConverter.getInstance().convert(document, bOut, options);
                        String[] nomeArray = egresso.getNome().split(" ");

                        String nomeEgresso = ArquivoUtil.getBaseNameNormalizer(capitalize(nomeArray[0])) + ArquivoUtil.getBaseNameNormalizer(capitalize(nomeArray[nomeArray.length - 1]));
                        String matricula = egresso.getMatriculaFlem() != null ? egresso.getMatriculaFlem() : "SM";
                        String newFileName = "OF_" + modeloDeOficio.getTipoModelo().getCodigo() + "_" + matricula + "_" + nomeEgresso + "_" + oficio.getNumero() + "_" + oficio.getAno() + ".pdf";
                        ArquivoDTO dto = saveFileInStorage(bOut.toByteArray(), newFileName, "oficiosGerados/" + modeloDeOficio.getId());
                        oficio.setFilePath(dto.getFilePath());
                        oficio.setDataGeracao(dataGeracaoModeloOficio);
                        oficio.setFileLenght(dto.getFileLenght());
                        oficio.setNome(newFileName);
                        oficioDAO.inserir(oficio);
                    }

                    Acao a = new Acao();
                    a.setEgresso(egresso);
                    a.setTipoDeAcao(tipoDeAcaoOficio);
                    a.setData(new Date());
                    a.setDescricao("OF Nº:" + oficio.getIdentificacao() + " - " + oficio.getAssunto());
                    acaoDAO.inserir(a);

                    modeloDeOficio.getEgressosParaGerar().remove(egresso);
                    modeloDeOficioDAO.alterar(modeloDeOficio);
                }
            }

        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }

        modeloDeOficio.setStatus(ModeloDeOficio.Status.GERADO);
        modeloDeOficio.setDataGeracao(dataGeracaoModeloOficio);
        try {
            modeloDeOficioDAO.alterar(modeloDeOficio);
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        JPAUtil.closeEntityManager();
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    private void replace(XWPFDocument document, String busca, String substituir) {
        for (XWPFParagraph p : document.getParagraphs()) {
            List<XWPFRun> runs = p.getRuns();
            if (runs != null) {
                for (XWPFRun r : runs) {
                    String text = r.getText(0);
                    if (text != null && text.contains(busca)) {
                        text = text.replace(busca, substituir == null ? "" : substituir);
                        r.setText(text, 0);
                    }
                }
            }
        }
        for (XWPFTable tbl : document.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        for (XWPFRun r : p.getRuns()) {
                            String text = r.getText(0);
                            if (text != null && text.contains(busca)) {
                                text = text.replace(busca, substituir == null ? "" : substituir);
                                r.setText(text, 0);
                            }
                        }
                    }
                }
            }
        }
    }

    private ArquivoDTO saveFileInStorage(byte[] contents, String fileName, String path) throws IOException, BusinessException {
        Response response = fileStorageService.postFile(contents, fileName, path);
        return (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

}

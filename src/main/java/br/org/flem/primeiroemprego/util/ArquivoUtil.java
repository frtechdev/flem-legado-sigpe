package br.org.flem.primeiroemprego.util;

import br.org.flem.fwe.util.StringUtil;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.entity.Documento;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.mb.CampanhaVisualizarMB;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.Normalizer;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author emsilva
 */
public class ArquivoUtil {

    public static String ler(String file, boolean utf8) throws FileNotFoundException, IOException {
        StringBuilder builder = new StringBuilder();
        URL url = ArquivoUtil.class.getClassLoader().getResource(file);
        if (url != null) {
            BufferedReader br = new BufferedReader(new BufferedReader(utf8 ? new InputStreamReader(new FileInputStream(url.getPath()), "UTF8") : new InputStreamReader(new FileInputStream(url.getPath()))));
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                builder.append(sCurrentLine);
            }
        }
        return builder.toString();
    }

    public static String ler(String file) throws FileNotFoundException, IOException {
        return ler(file, false);
    }

    public static byte[] conteudo(String arquivo) {
        byte[] result = null;
        try {
            File file = new File(arquivo);
            FileInputStream is = new FileInputStream(file);
            result = new byte[(int) file.length()];
            is.read(result);
            is.close();
        } catch (IOException e) {
            Logger.getLogger(ArquivoUtil.class.getName()).log(Level.SEVERE, e.getMessage(), e);
        }
        return result;
    }

    /**
     * Cria um arquivo zipado a partir de uma lista de documentos passados por
     * parametro
     *
     * @param documentos List
     * @return array de bytes
     * @throws java.io.IOException
     * @throws br.org.flem.primeiroemprego.exception.BusinessException
     */
    public static byte[] criarZip(List<Documento> documentos) throws IOException, BusinessException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipfile = new ZipOutputStream(bos);
        for (Documento content : documentos) {
            ZipEntry zipentry = new ZipEntry(content.getNome());
            zipfile.putNextEntry(zipentry);
            if (StringUtil.stringNotNull(content.getFilePath())) {
                Response response = new FileStorageService().getFileByPath(content.getFilePath());
                InputStream stream = response.readEntity(InputStream.class);
                byte[] targetArray = new byte[stream.available()];
                zipfile.write(targetArray);
            }else{
//                zipfile.write(content.get);
            }
        }
        zipfile.close();
        return bos.toByteArray();
    }

    /**
     * Unir em um arquivo pdf vários arquivos
     *
     * @param list List
     * @return StreamedContent
     */
    public static StreamedContent unirArquivosPdf(List<InputStream> list) {
        Document document = new Document();
        ByteArrayOutputStream aquivoDeSaida = new ByteArrayOutputStream();
        try {

            PdfWriter writer = PdfWriter.getInstance(document, aquivoDeSaida);
            document.open();
            PdfContentByte cb = writer.getDirectContent();

            for (InputStream in : list) {
                PdfReader reader;

                reader = new PdfReader(in);

                for (int i = 1; i <= reader.getNumberOfPages(); i++) {
                    document.newPage();
                    //import the page from source pdf
                    PdfImportedPage page = writer.getImportedPage(reader, i);
                    //add the page to the destination pdf
                    cb.addTemplate(page, 0, 0);
                }
            }
            aquivoDeSaida.flush();
            document.close();
            aquivoDeSaida.close();
            return new DefaultStreamedContent(new ByteArrayInputStream(aquivoDeSaida.toByteArray()), "application/pdf", "arquivosPdf.pdf");
        } catch (IOException | DocumentException ex) {
            Logger.getLogger(CampanhaVisualizarMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getBaseName(String filename){
        if( StringUtils.isNotEmpty(filename))
            return FilenameUtils.getBaseName(filename);
        return null;
    }
    
    public static String getBaseNameNormalizer(String filename){
        if( StringUtils.isNotEmpty(filename))
            return Normalizer.normalize(getBaseName(filename), Normalizer.Form.NFD).replaceAll("[^aA-zZ-Z1-9]", "").trim();
        return null;
    }
    
    public static String getExtension(String filename){
        if( StringUtils.isNotEmpty(filename))
            return FilenameUtils.getExtension(filename);
        return null;
    }
}

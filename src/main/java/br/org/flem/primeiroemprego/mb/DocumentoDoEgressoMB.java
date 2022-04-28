package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.DocumentoDoEgressoDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.DocumentoDoEgresso;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.ArquivoUtil;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class DocumentoDoEgressoMB implements Serializable {
    
    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    @ManagedProperty(value = "#{documentoDoEgressoDAO}")
    private DocumentoDoEgressoDAO documentoDoEgressoDAO;
    
    public Response post(Long id, byte[] contents, String fileName, String matricula) throws IOException, BusinessException{
        if( StringUtils.isEmpty(matricula))
            matricula = "semmatricula";
        String path = "documentosDoEgresso/"+matricula;
        String finalFilename = ArquivoUtil.getBaseNameNormalizer(fileName)+"."+ArquivoUtil.getExtension(fileName);
        return fileStorageService.postFile(contents, finalFilename, path);
    }
    
//    public void update(Long id, Response response) throws Exception{
//        ArquivoDTO dto = (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
//        DocumentoDoEgresso oficioOld = documentoDoEgressoDAO.obterPorPK(id);
//        oficioOld.setArquivo(null);
//        oficioOld.setFilePath(dto.getFilePath());
//        documentoDoEgressoDAO.alterar(oficioOld);
//    }
    
    public void inserir(Response response) throws Exception{
        ArquivoDTO dto = (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
        DocumentoDoEgresso oficio = new DocumentoDoEgresso();
        oficio.setFilePath(dto.getFilePath());
        oficio.setNome(dto.getFileName());
        oficio.setTipo(dto.getContentType());
        oficio.setFileLenght(dto.getFileLenght());
        documentoDoEgressoDAO.inserir(oficio);
        Mensagem.lancar("Ofício salvo com sucesso");
    }
    
    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public DocumentoDoEgressoDAO getDocumentoDoEgressoDAO() {
        return documentoDoEgressoDAO;
    }

    public void setDocumentoDoEgressoDAO(DocumentoDoEgressoDAO documentoDoEgressoDAO) {
        this.documentoDoEgressoDAO = documentoDoEgressoDAO;
    }
    
    
    
}

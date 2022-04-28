package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.DocumentoDoEgressoDAO;
import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.DocumentoDoEgresso;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.util.ArquivoUtil;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class DocumentoDoEgressoRN extends DocumentoRN<DocumentoDoEgresso>{

    @ManagedProperty(value = "#{documentoDoEgressoDAO}")
    private DocumentoDoEgressoDAO documentoDoEgressoDAO;

    /**
     * 
     * @param egresso
     * @return 
     */
    public List<DocumentoDoEgresso> findByEgresso(Egresso egresso) {
        return documentoDoEgressoDAO.findByEgresso(egresso);
    }
    public void excluir(DocumentoDoEgresso documento) throws Exception {
        documentoDoEgressoDAO.excluir(documento);
    }

    public DocumentoDoEgressoDAO getDocumentoDoEgressoDAO() {
        return documentoDoEgressoDAO;
    }

    public void setDocumentoDoEgressoDAO(DocumentoDoEgressoDAO documentoDoEgressoDAO) {
        this.documentoDoEgressoDAO = documentoDoEgressoDAO;
    }
    
    public DocumentoDoEgresso postDocumentoDoEgresso(byte[] contents, String filename, Egresso egresso, Boolean sigiloso) throws Exception {
        DocumentoDoEgresso documento = new DocumentoDoEgresso();
        documento.setEgresso(egresso);
        String matricula = getMatricula(egresso);

        String finalFilename = ArquivoUtil.getBaseNameNormalizer(filename)+"."+ArquivoUtil.getExtension(filename);

        ArquivoDTO dto = post(contents, finalFilename, "documentosDoEgresso/" + matricula);
        documento.setNome(dto.getFileName());
        documento.setTipo(dto.getContentType());
        documento.setFilePath(dto.getFilePath());
        documento.setFileLenght(dto.getFileLenght());
        documento.setSigiloso(sigiloso);
        
        return documento;
    }
    
    public Oficio postOficio(byte[] contents, String filename, Egresso egresso) throws Exception {

        Oficio oficio = new Oficio();
        oficio.setEgresso(egresso);
        String matricula = getMatricula(egresso);
        String finalFilename = ArquivoUtil.getBaseNameNormalizer(filename)+"."+ArquivoUtil.getExtension(filename);
        ArquivoDTO dto = post(contents, finalFilename, "documentosDoEgresso/oficios/" + matricula);
        
        oficio.setTipo(dto.getContentType());
        oficio.setFilePath(dto.getFilePath());
        oficio.setFileLenght(dto.getFileLenght());
        oficio.setNome(dto.getFileName());
        return oficio;
    }
    
    public DocumentoDoEgresso salvarDocumento(DocumentoDoEgresso documento) throws Exception{
        return documentoDoEgressoDAO.inserir(documento);
    }
    
    private String getMatricula(Egresso egresso){
        String matricula = "semmatricula";
        if( egresso.getMatriculaFlem() != null ){
            matricula = egresso.getMatriculaFlem();
        }
        return matricula;
    }
    
    public boolean deleteDocumento(DocumentoDoEgresso documento) throws Exception{
        boolean deleted = false;
        if( documento != null){
            DocumentoDoEgresso documentoOld = documentoDoEgressoDAO.obterPorPK(documento.getId());
            if( documentoOld != null){
                deleted = delete(documentoOld.getFilePath());
                if( deleted ){
                    documentoDoEgressoDAO.excluir(documentoOld);
                }
            }
        }
        return deleted;
    }
    
    
}

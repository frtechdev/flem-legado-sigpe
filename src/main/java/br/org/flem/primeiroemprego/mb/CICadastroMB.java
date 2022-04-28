package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CIDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.CI;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.RedirectUtil;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CICadastroMB implements Serializable{

    public static String lock = "";

    @ManagedProperty(value = "#{usuarioLogadoMB}")
    private UsuarioLogadoMB usuarioLogadoMB;
    
    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    private CI ci = new CI();

    private UploadedFile documento;

    @ManagedProperty(value = "#{cIDAO}")
    private CIDAO ciDAO;

    @PostConstruct
    public void init(){
        synchronized(CICadastroMB.lock){
            if(CICadastroMB.lock.isEmpty() || CICadastroMB.lock.equals(usuarioLogadoMB.getUsuario().getUsername())){
                CICadastroMB.lock = usuarioLogadoMB.getUsuario().getUsername();
                ciDAO.atribuirNumeroDaCI(ci);
            }
        }
    }

    public void checkLock(){
        if(!CICadastroMB.lock.isEmpty() && !CICadastroMB.lock.equals(usuarioLogadoMB.getUsuario().getUsername())){
            Mensagem.lancar("Tela de Cadastro de CI só pode ser utilizada uma vez");
            RedirectUtil.redirect("/ci/lista.xhtml");
        }
    }

    public String salvar(){
        try{
            if(documento.getFileName().matches(".*\\.(docx|pdf)$")){
                
                Response response = fileStorageService.postFile(documento.getContents(), documento.getFileName(), "ci");
                ArquivoDTO dto = (ArquivoDTO)CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
//                ci.setArquivo(documento.getContents());
                ci.setFilePath(dto.getFilePath());
                ci.setNome(dto.getFileName());
                ci.setTipo(dto.getContentType());
                synchronized(CICadastroMB.lock){
                    ciDAO.inserir(ci);
                    CICadastroMB.lock = "";
                }
                Mensagem.lancar("CI salva com sucesso");

                return "lista.xhtml";
            }else{
                Mensagem.lancar("Formato incorreto para CI. Deve ser docx ou pdf.");
            }
        }catch(Exception e){
            Logger.getLogger(RedirectUtil.class.getName()).log(Level.SEVERE, null, e);
            Mensagem.lancar("Erro ao salvar o ofício");
        }
        return "";
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public CI getCi() {
        return ci;
    }

    public void setCi(CI ci) {
        this.ci = ci;
    }

    public UploadedFile getDocumento() {
        return documento;
    }

    public void setDocumento(UploadedFile documento) {
        this.documento = documento;
    }

    public CIDAO getCiDAO() {
        return ciDAO;
    }

    public void setCiDAO(CIDAO ciDAO) {
        this.ciDAO = ciDAO;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    
    

}
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.OficioDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.ArquivoUtil;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.RedirectUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class OficioCadastroMB implements Serializable {

    public static String lock = "";

    @ManagedProperty(value = "#{usuarioLogadoMB}")
    private UsuarioLogadoMB usuarioLogadoMB;

    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    private Oficio oficio = new Oficio();

    private UploadedFile arquivoOficio;

    private boolean foiObtidoLock = false;//Para poder utilizar 

    @ManagedProperty(value = "#{oficioDAO}")
    private OficioDAO oficioDAO;

    private boolean obterLock() {
        if (!OficioCadastroMB.lock.isEmpty() && !OficioCadastroMB.lock.equals(usuarioLogadoMB.getUsuario().getUsername())) {
            foiObtidoLock = false;
            return false;
        }

        synchronized (OficioCadastroMB.lock) {
            OficioCadastroMB.lock = usuarioLogadoMB.getUsuario().getUsername();
            oficioDAO.atribuirNumeroDoOficio(oficio);
        }
        foiObtidoLock = true;
        return true;
    }

    public void checkLock() {//Checagem de lock e atribuição de lock para usuario atual caso não exista um lock
        if (!this.obterLock()) {
            Mensagem.lancar("Tela de Cadastro de Ofício só pode ser utilizada uma vez");
            RedirectUtil.redirect("/oficio/lista.xhtml");
        }
    }

    public void novoOficio(Egresso egresso) {
        oficio = new Oficio();
        oficio.setEgresso(egresso);
        obterLock();
    }

    public void salvarTratamento() throws Exception {
        if (this.arquivoOficio.getFileName().matches(".*\\.pdf$")) {
            Response response = post(arquivoOficio.getContents(), arquivoOficio.getFileName());
            inserir(response);
        } else {
            throw new Exception("Formato de arquivo inválido. Deve ser PDF.");
        }
    }
    
    public Response post(byte[] contents, String fileName, Oficio oficio) throws IOException, BusinessException {
        
        this.oficio = oficio;
        String matricula = "M";
        String nome = "";
        if (oficio.getEgresso() != null) {
            if (oficio.getEgresso().getMatriculaFlem() != null) {
                matricula = "" + oficio.getEgresso().getMatriculaFlem();
            }
            if (oficio.getEgresso().getNome() != null) {
                String[] nomeArray = oficio.getEgresso().getNome().split(" ");
                nome = capitalize(nomeArray[0]) + capitalize(nomeArray[nomeArray.length - 1]);
            }
        }

        String newFileName = "OF_GE_" + matricula + "_" + nome + "_" + oficio.getNumero() + "_" + oficio.getAno();
        String path = "oficios/" + oficio.getNumero()+"/"+oficio.getAno();
        Response response = new FileStorageService().getFileByPath(path+"/"+newFileName+"."+FilenameUtils.getExtension(fileName));
        if( response != null ){
            newFileName = newFileName+"_1."+FilenameUtils.getExtension(fileName);
        }else{
            newFileName = newFileName+"."+FilenameUtils.getExtension(fileName);
        }
        return new FileStorageService().postFile(contents, newFileName, path);
    }

    public Response post(byte[] contents, String fileName) throws IOException, BusinessException {
        String matricula = "M";
        String nome = "";
        if (oficio.getEgresso() != null) {
            if (oficio.getEgresso().getMatriculaFlem() != null) {
                matricula = "" + oficio.getEgresso().getMatriculaFlem();
            }
            if (oficio.getEgresso().getNome() != null) {
                String[] nomeArray = oficio.getEgresso().getNome().split(" ");
                nome = capitalize(nomeArray[0]) + capitalize(nomeArray[nomeArray.length - 1]);
            }
        }
        String newFileName = "OF_GE_" + matricula + "_" + nome + "_" + oficio.getNumero() + "_" + oficio.getAno()+"."+ArquivoUtil.getExtension(fileName);
        String path = "oficios/";
        return fileStorageService.postFile(contents, newFileName, path);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public void update(Oficio oficio, Response response) throws Exception {
        ArquivoDTO dto = (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
//        Oficio oficioOld = oficioDAO.obterPorPK(id);
//        Oficio oficioOld = new OficioDAO().obterPorPK(id);
//        oficio.setArquivo(null);
        oficio.setFilePath(dto.getFilePath());
//        oficioDAO.alterar(oficioOld);
        new OficioDAO().alterar(oficio);
    }

    public void inserir(Response response) throws Exception {
        ArquivoDTO dto = (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
        oficio.setFilePath(dto.getFilePath());
        oficio.setNome(dto.getFileName());
        oficio.setTipo(dto.getContentType());
        oficio.setFileLenght(dto.getFileLenght());
        synchronized (OficioDAO.LOCK) {//Garantir que somente uma thread vai acessar a insersão para obter o numero do oficio
            OficioCadastroMB.lock = "";
            oficioDAO.inserir(oficio);
            Mensagem.lancar("Ofício salvo com sucesso");
        }
    }

    public String salvar() {
        try {
            salvarTratamento();
            return "lista.xhtml";
        } catch (Exception e) {
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

    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }

    public UploadedFile getArquivoOficio() {
        return arquivoOficio;
    }

    public void setArquivoOficio(UploadedFile modelo) {
        this.arquivoOficio = modelo;
    }

    public OficioDAO getOficioDAO() {
        return oficioDAO;
    }

    public void setOficioDAO(OficioDAO oficioDAO) {
        this.oficioDAO = oficioDAO;
    }

    public boolean isFoiObtidoLock() {
        return foiObtidoLock;
    }

    public void setFoiObtidoLock(boolean foiObtidoLock) {
        this.foiObtidoLock = foiObtidoLock;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

}

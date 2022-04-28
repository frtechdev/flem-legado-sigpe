package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.OficioDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.dto.OficioListaDTO;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class OficioListaMB implements Serializable{

    @ManagedProperty(value = "#{oficioDAO}")
    private OficioDAO oficioDAO;
    
    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    private List<OficioListaDTO> oficios;

    @PostConstruct
    public void init() {
        oficios = oficioDAO.obterTodosOficios();
    }
    
    public void destravarLockDeCadastro(){
        synchronized(OficioCadastroMB.lock){
            OficioCadastroMB.lock = "";
            Mensagem.lancar("Tela de Cadastro de Oficio externo liberada. A mesma só pode ser utilizada uma vez");
        }
    }

    public boolean isTelaDeCadastroTravada(){
        return !OficioCadastroMB.lock.isEmpty();
    }
    
    public StreamedContent download(OficioListaDTO dto){
        Oficio oficio = oficioDAO.obterPorPK(dto.getIdDocumento());
        
        if( oficio != null && StringUtils.isNotEmpty(oficio.getFilePath()) ){
            String filename = oficio.getNome();
            try {
                Response res = fileStorageService.getFileByPath(oficio.getFilePath());
                if( StringUtils.isNotEmpty(res.getHeaderString("filename"))){
                    filename = res.getHeaderString("filename");
                }
                InputStream stream = res.readEntity(InputStream.class);
                return new DefaultStreamedContent(stream, oficio.getTipo(), filename);
            } catch (BusinessException ex) {
                Logger.getLogger(OficioListaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return new DefaultStreamedContent(new ByteArrayInputStream(oficio.getArquivo()),oficio.getTipo(),oficio.getNome());
    }

    public OficioDAO getOficioDAO() {
        return oficioDAO;
    }

    public void setOficioDAO(OficioDAO oficioDAO) {
        this.oficioDAO = oficioDAO;
    }

    public List<OficioListaDTO> getOficios() {
        return oficios;
    }

    public void setOficios(List<OficioListaDTO> oficios) {
        this.oficios = oficios;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    

}
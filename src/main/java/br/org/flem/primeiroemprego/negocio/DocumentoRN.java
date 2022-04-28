/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.Documento;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.util.CoreUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;

/**
 *
 * @author tscortes
 * @param <D>
 */
@ManagedBean
@ViewScoped
public class DocumentoRN<D extends Documento> implements Serializable{

    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    public ArquivoDTO post(byte[] contents, String fileName, String path) throws IOException, BusinessException {
        Response response = fileStorageService.postFile(contents, fileName, path);
        return (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
    }
        
    public boolean delete(String filepath) {
        return fileStorageService.deleteFile(filepath);
    }  
    
    public Response get(String path){
        return fileStorageService.getFileByPath(path);
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
}

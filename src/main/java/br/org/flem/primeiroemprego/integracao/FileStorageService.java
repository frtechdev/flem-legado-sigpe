package br.org.flem.primeiroemprego.integracao;

import br.org.flem.primeiroemprego.exception.BusinessException;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class FileStorageService implements Serializable{

    private static final String URL = "http://arquivosapi.baixio.flem.org.br/files/";

    /**
     * @param path
     * @return
     * @throws BusinessException
     */
    public Response getFileByPath(String path) throws BusinessException {
        String urlPath = URL + "?path=";
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlPath + URLEncoder.encode(path));
        Response response = target.request().get();
        if (response.getStatus() != 200) {
            return null;
        }
        return response;
    }

    /**
     * @param file
     * @param path
     * @return
     * @throws BusinessException
     */
    public Response postFile(File file, String path) throws BusinessException {
        Response res = getFileByPath("uploads/sigpe/"+path+"/"+file.getName());
        if( res != null ){
            throw new BusinessException("Este arquivo já existe");
        }
        String urlPath = URL + "withReturn?path=sigpe/";
        try {
            final Client client = ClientBuilder.newBuilder().register(MultiPartFeature.class).build();
            WebTarget target = client.target(urlPath + path);
            final FileDataBodyPart filePart = new FileDataBodyPart("file", file);
            FormDataMultiPart formDataMultiPart = new FormDataMultiPart();
            final FormDataMultiPart multipart = (FormDataMultiPart) formDataMultiPart.bodyPart(filePart);
            final Response response = target.request().post(Entity.entity(multipart, multipart.getMediaType()));

            if (response.getStatus() != 200) {
                throw new BusinessException("Erro ao salvar o arquivo: " + response.getStatus());
            }

            return response;

        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
            throw new BusinessException(ex.getMessage());
        }
        
    }
    
    public boolean deleteFile(String path){
        String urlPath = URL + "?path=";
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(urlPath + URLEncoder.encode(path));
        Response response = target.request().delete();
        return response.getStatus() == 200;
    }

    /**
     *
     * @param contents
     * @param fileName
     * @param path
     * @return
     * @throws IOException
     * @throws BusinessException
     */
    public Response postFile(byte[] contents, String fileName, String path) throws IOException {
        String pathtemp = "/uploads/sigpetmp";
        if (!Files.isDirectory(Paths.get(pathtemp))) {
            Files.createDirectories(Paths.get(pathtemp));
        }
        File outputFile  = new File(pathtemp+"/"+fileName);
        try (FileOutputStream fileOuputStream = new FileOutputStream(outputFile);){
            fileOuputStream.write(contents);
        }
        Response response = postFile(outputFile , path);
        Files.deleteIfExists(Paths.get(outputFile.getAbsolutePath()));
        return response;
    }

}

package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CampanhaDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.EnvioDeEmail;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.ArquivoUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CampanhaVisualizarMB implements Serializable {

    @ManagedProperty(value = "#{campanhaDAO}")
    private CampanhaDAO campanhaDAO;
    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    private Campanha campanha;

    private String idCampanha;

    @PostConstruct
    public void init() {
        idCampanha = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        campanha = campanhaDAO.obterPorPK(Long.parseLong(idCampanha));
        if (campanha != null && campanha.getEnvios() != null && !campanha.getEnvios().isEmpty()) {
            List<EnvioDeEmail> envios = campanha.getEnvios();
            Collections.sort(envios);
        }

        campanhaDAO.carregarAnexos(campanha);
    }

    public StreamedContent downloadDocumento() throws BusinessException {
        List<InputStream> listaArquivos = new ArrayList<>();
        if (!StringUtils.isEmpty(idCampanha)) {
            Campanha pCampanha = campanhaDAO.obterPorPK(Long.parseLong(idCampanha));
            List<Oficio> oficios = pCampanha.getModeloDeOficio().getOficiosGerados();
            for (Oficio oficio : oficios) {
                if (StringUtils.isNotEmpty(oficio.getFilePath())) {
                    InputStream stream = fileStorageService.getFileByPath(oficio.getFilePath()).readEntity(InputStream.class);
                    listaArquivos.add(stream);
                }else{
                    listaArquivos.add(new ByteArrayInputStream(oficio.getArquivo()));
                }
            }
            return ArquivoUtil.unirArquivosPdf(listaArquivos);
        }
        return null;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public CampanhaDAO getCampanhaDAO() {
        return campanhaDAO;
    }

    public void setCampanhaDAO(CampanhaDAO campanhaDAO) {
        this.campanhaDAO = campanhaDAO;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    
    

}

package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.ArquivoUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.RedirectUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ModeloDeOficioDetalhesMB implements Serializable {

    private ModeloDeOficio modeloDeOficio;

    @ManagedProperty(value = "#{modeloDeOficioDAO}")
    private ModeloDeOficioDAO modeloDeOficioDAO;
    
    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            modeloDeOficio = modeloDeOficioDAO.obterPorPK(Long.parseLong(id));
        } else {
            Mensagem.lancar("Modelo do Oficio não identificado");
            RedirectUtil.redirect("/");
        }
    }

    public StreamedContent downloadDocumento() {
        List<InputStream> listaArquivos = new ArrayList<>();
        List<Oficio> oficios = modeloDeOficio.getOficiosGerados();
        if (oficios.size() > 0) {
            for (Oficio oficio : oficios) {
                if (StringUtils.isNotEmpty(oficio.getFilePath())) {
                    InputStream stream;
                    try {
                        stream = fileStorageService.getFileByPath(oficio.getFilePath()).readEntity(InputStream.class);
                        listaArquivos.add(stream);
                    } catch (BusinessException ex) {
                        Logger.getLogger(ModeloDeOficioDetalhesMB.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    listaArquivos.add(new ByteArrayInputStream(oficio.getArquivo()));
                }

            }
            return ArquivoUtil.unirArquivosPdf(listaArquivos);
        }
        return null;
    }

    public void removerEgresso(Egresso egresso) {
        try {
            modeloDeOficio.getEgressosParaGerar().remove(egresso);
            modeloDeOficioDAO.alterar(modeloDeOficio);
            Mensagem.lancar("Egresso removido do modelo do ofício");
        } catch (Exception e) {
            Mensagem.lancar("Erro ao remover egresso do modelo do ofício");
        }
    }

    public ModeloDeOficio getModeloDeOficio() {
        return modeloDeOficio;
    }

    public void setModeloDeOficio(ModeloDeOficio modeloDeOficio) {
        this.modeloDeOficio = modeloDeOficio;
    }

    public ModeloDeOficioDAO getModeloDeOficioDAO() {
        return modeloDeOficioDAO;
    }

    public void setModeloDeOficioDAO(ModeloDeOficioDAO modeloDeOficioDAO) {
        this.modeloDeOficioDAO = modeloDeOficioDAO;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }
    
    
}

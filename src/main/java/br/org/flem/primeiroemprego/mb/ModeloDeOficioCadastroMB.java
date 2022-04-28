package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.TipoModeloDeOficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.negocio.TipoModeloDeOficioRN;
import br.org.flem.primeiroemprego.util.ArquivoUtil;
import br.org.flem.primeiroemprego.util.CoreUtil;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.RedirectUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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
public class ModeloDeOficioCadastroMB implements Serializable {

    private UploadedFile modelo;

    private String assunto;

    @ManagedProperty(value = "#{modeloDeOficioDAO}")
    private ModeloDeOficioDAO modeloDeOficioDAO;
    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;
    @ManagedProperty(value = "#{tipoModeloDeOficioRN}")
    private TipoModeloDeOficioRN tipoModeloDeOficioRN;

    private List<TipoModeloDeOficio> tipos;

    private TipoModeloDeOficio tipoSelecionado;

    @PostConstruct
    public void init() {
        obterTiposModelos();
    }

    public String salvar() {
        try {
            if (tipoSelecionado == null) {
                Mensagem.lancarMensagemErro("O campo tipo de modelo é obrigatório");
            } else if (modelo.getFileName().matches(".*\\.docx$")) {
                Response response = post(modelo.getContents(), modelo.getFileName());
                save(response);
                Mensagem.lancar("Modelo do Ofício salvo com sucesso");
                return "lista.xhtml";
            } else {
                Mensagem.lancarMensagemErro("O Modelo do ofício tem que estar em formado docx");
            }

        } catch (IOException | BusinessException e) {
            Logger.getLogger(RedirectUtil.class.getName()).log(Level.SEVERE, null, e);
            Mensagem.lancarMensagemErro("Erro ao salvar o modelo do ofício: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ModeloDeOficioCadastroMB.class.getName()).log(Level.SEVERE, null, ex);
            Mensagem.lancarMensagemErro("Erro ao salvar o modelo do ofício: " + ex.getMessage());
        }
        return "";
    }

    private void obterTiposModelos() {
        tipos = tipoModeloDeOficioRN.obterListaAtivos();
    }

    public Response post(byte[] contents, String fileName) throws IOException, BusinessException {
        String finalFilename = ArquivoUtil.getBaseNameNormalizer(fileName) + "." + ArquivoUtil.getExtension(fileName);
        String path = "modelosdeoficio/";
        return fileStorageService.postFile(contents, finalFilename, path);
    }

    public void save(Response response) throws Exception {

        ModeloDeOficio modeloDeOficio = new ModeloDeOficio();

        ArquivoDTO dto = (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
        modeloDeOficio.setNome(dto.getFileName());
        modeloDeOficio.setTipo(dto.getContentType());
        modeloDeOficio.setFilePath(dto.getFilePath());
        modeloDeOficio.setAssunto(assunto);
        modeloDeOficio.setTipoModelo(tipoModeloDeOficioRN.obterPorId(tipoSelecionado.getId()));
        modeloDeOficio.setFileLenght(dto.getFileLenght());
        modeloDeOficioDAO.inserir(modeloDeOficio);
    }

    public UploadedFile getModelo() {
        return modelo;
    }

    public void setModelo(UploadedFile modelo) {
        this.modelo = modelo;
    }

    public ModeloDeOficioDAO getModeloDeOficioDAO() {
        return modeloDeOficioDAO;
    }

    public void setModeloDeOficioDAO(ModeloDeOficioDAO modeloDeOficioDAO) {
        this.modeloDeOficioDAO = modeloDeOficioDAO;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    public TipoModeloDeOficioRN getTipoModeloDeOficioRN() {
        return tipoModeloDeOficioRN;
    }

    public void setTipoModeloDeOficioRN(TipoModeloDeOficioRN tipoModeloDeOficioRN) {
        this.tipoModeloDeOficioRN = tipoModeloDeOficioRN;
    }

    public List<TipoModeloDeOficio> getTipos() {
        return tipos;
    }

    public void setTipos(List<TipoModeloDeOficio> tipos) {
        this.tipos = tipos;
    }

    public TipoModeloDeOficio getTipoSelecionado() {
        return tipoSelecionado;
    }

    public void setTipoSelecionado(TipoModeloDeOficio tipoSelecionado) {
        this.tipoSelecionado = tipoSelecionado;
    }

}

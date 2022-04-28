package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.ExecutorThreads;
import br.org.flem.primeiroemprego.util.entity.GerarOficiosUtil;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.ws.rs.core.Response;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class ModeloDeOficioRN extends DocumentoRN<ModeloDeOficio>{

    @ManagedProperty(value = "#{modeloDeOficioDAO}")
    private ModeloDeOficioDAO modeloDeOficioDAO;

    @ManagedProperty(value = "#{oficioRN}")
    private OficioRN oficioRN;

    public List<ModeloDeOficio> obterNaoGerados() {
        return modeloDeOficioDAO.naoGerados();
    }
    
    public List<ModeloDeOficio> obterLista(){
        return modeloDeOficioDAO.obterLista();
    }

    public boolean excluirArquivo(String filepath) {
        return delete(filepath);
    }

    public void excluir(ModeloDeOficio documento) throws Exception {
        if (!documento.getStatus().equals(ModeloDeOficio.Status.SALVO)) {
            throw new BusinessException("Não é possivel excluir um modelo de oficio já gerado");
        } else {
            excluirModeloDeOficio(documento);
        }
    }
    
    public void excluirGerado(ModeloDeOficio documento) throws Exception {
        if (!documento.getStatus().equals(ModeloDeOficio.Status.GERADO)) {
            throw new BusinessException("Não é possivel excluir um modelo de oficio ainda não gerado");
        }
        excluirOficioGeradosPorModelo(documento);
        excluirModeloDeOficio(documento);
    }
    
    public void excluirOficioGeradosPorModelo(ModeloDeOficio documento) throws Exception{
        List<Oficio> oficios = oficioRN.obterPorModeloDeOficio(documento);
        if (!oficios.isEmpty()) {
            for (Oficio oficio : oficios) {
                oficioRN.excluir(oficio);
            }
        }
    }
    
    public void excluirModeloDeOficio(ModeloDeOficio documento) throws Exception{
        if (excluirArquivo(documento.getFilePath())) {
            modeloDeOficioDAO.excluir(documento);
        }
    }

    public void excluir(Long id) throws Exception {
        ModeloDeOficio oficio = obterPorId(id);
        if (oficio != null) {
            excluir(oficio);
        }
    }

    public ModeloDeOficio obterPorId(Long id) {
        return modeloDeOficioDAO.obterPorPK(id);
    }

    private StreamedContent download(ModeloDeOficio modeloDeOficio) {
        return new DefaultStreamedContent(new ByteArrayInputStream(modeloDeOficio.getArquivo()), modeloDeOficio.getTipo(), modeloDeOficio.getNome());
    }

    public StreamedContent downloadResponse(Long id) {
        ModeloDeOficio documento = modeloDeOficioDAO.obterPorPK(id);
        if (documento.getFilePath() == null) {
            return download(documento);
        }
        String filename = documento.getNome();
        Response res = get(documento.getFilePath());
        if (StringUtils.isNotEmpty(res.getHeaderString("filename"))) {
            filename = res.getHeaderString("filename");
        }
        InputStream stream = res.readEntity(InputStream.class);
        return new DefaultStreamedContent(stream, documento.getTipo(), filename);
    }
    
    public void gerar(Long id) throws Exception {
        ModeloDeOficio modeloDeOficio = modeloDeOficioDAO.obterPorPK(id);
        modeloDeOficio.setStatus(ModeloDeOficio.Status.GERANDO);
        modeloDeOficioDAO.alterar(modeloDeOficio);

        ExecutorThreads.getInstancia().executar(new GerarOficiosUtil(id));
    }

    public ModeloDeOficioDAO getModeloDeOficioDAO() {
        return modeloDeOficioDAO;
    }

    public void setModeloDeOficioDAO(ModeloDeOficioDAO modeloDeOficioDAO) {
        this.modeloDeOficioDAO = modeloDeOficioDAO;
    }

    public OficioRN getOficioRN() {
        return oficioRN;
    }

    public void setOficioRN(OficioRN oficioRN) {
        this.oficioRN = oficioRN;
    }

}

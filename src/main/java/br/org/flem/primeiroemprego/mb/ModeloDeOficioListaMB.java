package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.negocio.ModeloDeOficioRN;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.jboss.logging.Logger;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class ModeloDeOficioListaMB implements Serializable {

    private List<ModeloDeOficio> modelosDeOficio;
    
    @ManagedProperty(value = "#{modeloDeOficioRN}")
    private ModeloDeOficioRN modeloDeOficioRN;

    @PostConstruct
    public void init() {
        modelosDeOficio = modeloDeOficioRN.obterLista();
    }
    
    public StreamedContent downloadResponse(Long id) {
        try {
            return modeloDeOficioRN.downloadResponse(id);
        } catch (BusinessException ex) {
            Mensagem.lancar(ex.getMessage());
            return null;
        }
    }

    public void gerar(ModeloDeOficio modeloDeOficio){
        try {
            modeloDeOficioRN.gerar(modeloDeOficio.getId());
            Mensagem.lancar("Geração de Ofícios inciada.");
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Logger.Level.FATAL, ex.getMessage(), ex);
            Mensagem.lancar("Não foi possível gerar o modelo de Oficio: "+ex.getMessage());
        }
        
    }

    public void remover(ModeloDeOficio modeloDeOficio) {
        try {
            modelosDeOficio.remove(modeloDeOficio);
            modeloDeOficioRN.excluir(modeloDeOficio);
            Mensagem.lancar("Modelo de Oficio removido");
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Logger.Level.FATAL, e.getMessage(), e);
            Mensagem.lancar("Não foi possível excluir o modelo de Oficio: "+e.getMessage());
        }
    }
    
    public void excluirGerado(ModeloDeOficio modeloDeOficio){
        try {
            modelosDeOficio.remove(modeloDeOficio);
            modeloDeOficioRN.excluirGerado(modeloDeOficio);
            Mensagem.lancar("Modelo de Oficio e todos os seus oficios gerados foram excluidos com sucesso");
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Logger.Level.FATAL, e.getMessage(), e);
            Mensagem.lancar("Não foi possível excluir o modelo de Oficio: "+e.getMessage());
        }
    }

    public List<ModeloDeOficio> getModelosDoOficio() {
        return modelosDeOficio;
    }

    public void setModelosDoOficio(List<ModeloDeOficio> modelosDoOficio) {
        this.modelosDeOficio = modelosDoOficio;
    }

    public ModeloDeOficioRN getModeloDeOficioRN() {
        return modeloDeOficioRN;
    }

    public void setModeloDeOficioRN(ModeloDeOficioRN modeloDeOficioRN) {
        this.modeloDeOficioRN = modeloDeOficioRN;
    }
    
    
}

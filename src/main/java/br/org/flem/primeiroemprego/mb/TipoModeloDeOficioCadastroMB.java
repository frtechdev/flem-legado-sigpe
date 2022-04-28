package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.entity.Colaborador;
import br.org.flem.primeiroemprego.entity.TipoModeloDeOficio;
import br.org.flem.primeiroemprego.negocio.TipoModeloDeOficioRN;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class TipoModeloDeOficioCadastroMB implements Serializable {
    
    @ManagedProperty(value = "#{tipoModeloDeOficioRN}")
    private TipoModeloDeOficioRN tipoModeloDeOficioRN;
    
    private TipoModeloDeOficio tipoModelo;
    
    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if(id != null && !id.isEmpty()){
            tipoModelo = tipoModeloDeOficioRN.obterPorId(Long.parseLong(id));
        }else{
            tipoModelo = new TipoModeloDeOficio();
        }
    }
    
    public String salvar(){
        try {
            if( tipoModelo.getId() != null ){
                tipoModeloDeOficioRN.atualizar(tipoModelo);
                Mensagem.lancarMensagemInfo("Tipo de modelo Atualizado com sucesso");
            }else{
                tipoModeloDeOficioRN.salvar(tipoModelo);
                Mensagem.lancarMensagemInfo("Tipo de modelo criado com sucesso");
            }
            return cancelar();
        } catch (Exception ex) {
            Mensagem.lancarMensagemErro("Erro ao salvar tipo de modelo: "+ex.getMessage());
        }
        return "";
    }
    
    public String cancelar(){
        tipoModelo = null;
        return "/tipoModeloDeOficio/lista.xhtml";
    }

    public TipoModeloDeOficioRN getTipoModeloDeOficioRN() {
        return tipoModeloDeOficioRN;
    }

    public void setTipoModeloDeOficioRN(TipoModeloDeOficioRN tipoModeloDeOficioRN) {
        this.tipoModeloDeOficioRN = tipoModeloDeOficioRN;
    }

    public TipoModeloDeOficio getTipoModelo() {
        return tipoModelo;
    }

    public void setTipoModelo(TipoModeloDeOficio tipoModelo) {
        this.tipoModelo = tipoModelo;
    }
    
}

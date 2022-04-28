package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.entity.TipoModeloDeOficio;
import br.org.flem.primeiroemprego.negocio.TipoModeloDeOficioRN;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class TipoModeloDeOficioMB implements Serializable {
    
    @ManagedProperty(value = "#{tipoModeloDeOficioRN}")
    private TipoModeloDeOficioRN tipoModeloDeOficioRN;
    
    private List<TipoModeloDeOficio> lista;
    
    @PostConstruct
    public void init() {
        obterList();
    }
    
    private void obterList(){
        lista = tipoModeloDeOficioRN.obterLista();
    }
    public void remover(Long id){
        try {
            tipoModeloDeOficioRN.deletePorId(id);
            obterList();
        } catch (Exception ex) {
            Mensagem.lancarMensagemErro("Erro ao excluir tipo de modelo");
        }
    }
    public String novo(){
        return "novo.xhtml";
    }
    public String edit(Long id) {
        return "novo.xhtml?id="+id+"faces-redirect=true";
    }

    public TipoModeloDeOficioRN getTipoModeloDeOficioRN() {
        return tipoModeloDeOficioRN;
    }

    public void setTipoModeloDeOficioRN(TipoModeloDeOficioRN tipoModeloDeOficioRN) {
        this.tipoModeloDeOficioRN = tipoModeloDeOficioRN;
    }

    public List<TipoModeloDeOficio> getLista() {
        return lista;
    }

    public void setLista(List<TipoModeloDeOficio> lista) {
        this.lista = lista;
    }

}

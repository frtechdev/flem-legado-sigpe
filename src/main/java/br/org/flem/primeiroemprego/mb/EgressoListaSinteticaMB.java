package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.entity.EstadoColunasEgressoDTO;
import br.org.flem.primeiroemprego.entity.ListaEgressoDTO;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.primefaces.model.Visibility;

/**
 *
 * @author TSCortes
 */
@ManagedBean
@ViewScoped
public class EgressoListaSinteticaMB implements Serializable {

    @ManagedProperty(value="#{egressoDAO}")
    private EgressoDAO egressoDAO;
    
    private LazyDataModel<ListaEgressoDTO> dataModel;
    
    private ListaEgressoDTO egresso = new ListaEgressoDTO();
    
    @ManagedProperty(value="#{estadoColunasEgressoDTO}")
    private EstadoColunasEgressoDTO estadoDasColunas;


    @PostConstruct
    public void init() {
        obterListaPaginada();
    }
    
    private void obterListaPaginada(){
        dataModel = new LazyDataModel<ListaEgressoDTO>(){
            private static final long serialVersionUID = 1L;

            @Override
            public List<ListaEgressoDTO> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
                setRowCount(egressoDAO.obterQuantidade(filters));
                return egressoDAO.obterListaPaginada(first, pageSize, sortField, sortOrder, filters);
            }  
        };
    }

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public LazyDataModel<ListaEgressoDTO> getDataModel() {
        return dataModel;
    }

    public void setDataModel(LazyDataModel<ListaEgressoDTO> dataModel) {
        this.dataModel = dataModel;
    }

    public ListaEgressoDTO getEgresso() {
        return egresso;
    }

    public void setEgresso(ListaEgressoDTO egresso) {
        this.egresso = egresso;
    }    
    public void onToggle(ToggleEvent e) {
        estadoDasColunas.set((Integer)e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    public EstadoColunasEgressoDTO getEstadoDasColunas() {
        return estadoDasColunas;
    }

    public void setEstadoDasColunas(EstadoColunasEgressoDTO estadoDasColunas) {
        this.estadoDasColunas = estadoDasColunas;
    }
    

}

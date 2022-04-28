package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.TipoModeloDeOficioDAO;
import br.org.flem.primeiroemprego.entity.TipoModeloDeOficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class TipoModeloDeOficioRN {

    @ManagedProperty(value = "#{tipoModeloDeOficioDAO}")
    private TipoModeloDeOficioDAO tipoModeloDeOficioDAO;

    public TipoModeloDeOficio salvar(TipoModeloDeOficio dto) throws BusinessException, Exception {
        TipoModeloDeOficio newTipo = new TipoModeloDeOficio();
        newTipo.setCodigo(dto.getCodigo().toUpperCase());
        newTipo.setDescricao(dto.getDescricao());
        verificarCamposObrigatorios(newTipo);
        return tipoModeloDeOficioDAO.inserir(newTipo);
    }
    
    public TipoModeloDeOficio atualizar(TipoModeloDeOficio dto) throws BusinessException, Exception {
        TipoModeloDeOficio oldTipo = tipoModeloDeOficioDAO.obterPorPK(dto.getId());
        oldTipo.setDescricao(dto.getDescricao());
        verificarCamposObrigatorios(oldTipo);
        return tipoModeloDeOficioDAO.alterar(oldTipo);
    }
    
    private TipoModeloDeOficio alterar(TipoModeloDeOficio tipo) throws Exception{
        return tipoModeloDeOficioDAO.alterar(tipo);
    }
    
    public List<TipoModeloDeOficio> obterLista(){
        return tipoModeloDeOficioDAO.obterLista();
    }
    public List<TipoModeloDeOficio> obterListaAtivos(){
        return tipoModeloDeOficioDAO.obterListaAtivos();
    }
    
    public TipoModeloDeOficio obterPorId(Long id){
        return tipoModeloDeOficioDAO.obterPorPK(id);
    }
    
    public void deletePorId(Long id) throws Exception{
        TipoModeloDeOficio tipo = obterPorId(id);
        if( tipo != null){
            tipo.setAtivo(!tipo.getAtivo());
            alterar(tipo);
        }  
    }

    private void verificarCodigo(TipoModeloDeOficio newTipo) {
        if (StringUtils.isEmpty(newTipo.getCodigo())) {
            throw new BusinessException("Código é um campo obrigatório");
        }
        if (newTipo.getCodigo().length() != 2) {
            throw new BusinessException("Código deve ter dois caracteres");
        }
        TipoModeloDeOficio tipoModelo = tipoModeloDeOficioDAO.obterPorCodigo(newTipo.getCodigo());

        if( tipoModelo != null && (newTipo.getId() == null || !tipoModelo.getId().equals(newTipo.getId()))){
            throw new BusinessException("Este Código para o Tipo de Modelo já está cadastrado");
        }
        
    }

    private void verificarCamposObrigatorios(TipoModeloDeOficio tipo) {
        verificarCodigo(tipo);
        if (StringUtils.isEmpty(tipo.getDescricao())) {
            throw new BusinessException("Descrição é um campo obrigatório");
        }
    }

    public TipoModeloDeOficioDAO getTipoModeloDeOficioDAO() {
        return tipoModeloDeOficioDAO;
    }

    public void setTipoModeloDeOficioDAO(TipoModeloDeOficioDAO tipoModeloDeOficioDAO) {
        this.tipoModeloDeOficioDAO = tipoModeloDeOficioDAO;
    }

}

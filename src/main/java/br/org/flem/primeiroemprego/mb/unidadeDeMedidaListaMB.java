/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MaterialEgressoDAO;
import br.org.flem.primeiroemprego.dao.UnidadeDeMedidaDAO;
import br.org.flem.primeiroemprego.entity.MaterialEgresso;
import br.org.flem.primeiroemprego.entity.UnidadeDeMedida;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author AJLima
 */

@ManagedBean
@ViewScoped
public class unidadeDeMedidaListaMB extends BaseMB implements Serializable{
    
    @ManagedProperty(value="#{unidadeDeMedidaDAO}")
    private UnidadeDeMedidaDAO unidadeDeMedidaDAO;
    
    @ManagedProperty(value="#{materialEgressoDAO}")
    private MaterialEgressoDAO materialEgressoDAO;
    
    private List<UnidadeDeMedida> unidades;
    
    private UnidadeDeMedida itemExcluir;

    @PostConstruct
    public void init() {
        unidades = unidadeDeMedidaDAO.obterLista();
    }
    
    public void excluir(UnidadeDeMedida item){
        itemExcluir = item;
        List<MaterialEgresso> materiaisEgresso = materialEgressoDAO.obterMateriasEgressoAssociados(item);
        
        if( materiaisEgresso == null || materiaisEgresso.isEmpty() ){
            abrirModal("deleteWG");
        }else{
            Mensagem.lancarMensagemErro("Unidade já atribuído a beneficiários, por isso não pode ser exlcuído.");
        }
    }
    
    public void excluirItem(){
        try{
            unidadeDeMedidaDAO.excluir(itemExcluir);
            unidades = unidadeDeMedidaDAO.obterLista();
            Mensagem.lancarMensagemInfo("Unidade excluído com sucesso");
            fecharModal("deleteWG");
        } catch (Exception ex) {
            Logger.getLogger(MaterialListaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public UnidadeDeMedidaDAO getUnidadeDeMedidaDAO() {
        return unidadeDeMedidaDAO;
    }

    public void setUnidadeDeMedidaDAO(UnidadeDeMedidaDAO unidadeDeMedidaDAO) {
        this.unidadeDeMedidaDAO = unidadeDeMedidaDAO;
    }

    public List<UnidadeDeMedida> getUnidades() {
        return unidades;
    }

    public void setUnidades(List<UnidadeDeMedida> unidades) {
        this.unidades = unidades;
    }

    public MaterialEgressoDAO getMaterialEgressoDAO() {
        return materialEgressoDAO;
    }

    public void setMaterialEgressoDAO(MaterialEgressoDAO materialEgressoDAO) {
        this.materialEgressoDAO = materialEgressoDAO;
    }

    public UnidadeDeMedida getItemExcluir() {
        return itemExcluir;
    }

    public void setItemExcluir(UnidadeDeMedida itemExcluir) {
        this.itemExcluir = itemExcluir;
    }
    
    
}

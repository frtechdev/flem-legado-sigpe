/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MaterialDAO;
import br.org.flem.primeiroemprego.dao.MaterialEgressoDAO;
import br.org.flem.primeiroemprego.entity.Material;
import br.org.flem.primeiroemprego.entity.MaterialEgresso;
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
public class MaterialListaMB extends BaseMB implements Serializable{
    
    @ManagedProperty(value="#{materialDAO}")
    private MaterialDAO materialDAO;
    
    @ManagedProperty(value="#{materialEgressoDAO}")
    private MaterialEgressoDAO materialEgressoDAO;
    
    private List<Material> materiais;
    
    private Material itemExcluir;

    @PostConstruct
    public void init() {
        materiais = materialDAO.obterLista();
    }

    public void excluir(Material material){
        itemExcluir = material;
        List<MaterialEgresso> materiaisEgresso = materialEgressoDAO.obterMateriasEgressoAssociados(material);
        
        if( materiaisEgresso == null || materiaisEgresso.isEmpty() ){
            abrirModal("deleteWG");
        }else{
            Mensagem.lancarMensagemErro("Material já atribuído a beneficiários, por isso não pode ser exlcuído.");
        }
    }
    
    public void excluirItem(){
        try{
            materialDAO.excluir(itemExcluir);
            materiais = materialDAO.obterLista();
            Mensagem.lancarMensagemInfo("Material excluído com sucesso");
            fecharModal("deleteWG");
        } catch (Exception ex) {
            Logger.getLogger(MaterialListaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public MaterialDAO getMaterialDAO() {
        return materialDAO;
    }

    public void setMaterialDAO(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    public List<Material> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<Material> materiais) {
        this.materiais = materiais;
    }

    public MaterialEgressoDAO getMaterialEgressoDAO() {
        return materialEgressoDAO;
    }

    public void setMaterialEgressoDAO(MaterialEgressoDAO materialEgressoDAO) {
        this.materialEgressoDAO = materialEgressoDAO;
    }

    public Material getItemExcluir() {
        return itemExcluir;
    }

    public void setItemExcluir(Material itemExcluir) {
        this.itemExcluir = itemExcluir;
    }
    
    
}

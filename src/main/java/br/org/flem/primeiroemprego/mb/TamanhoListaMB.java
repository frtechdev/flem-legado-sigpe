/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MaterialEgressoDAO;
import br.org.flem.primeiroemprego.dao.TamanhoDAO;
import br.org.flem.primeiroemprego.entity.MaterialEgresso;
import br.org.flem.primeiroemprego.entity.Tamanho;
import br.org.flem.primeiroemprego.util.Mensagem;
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
public class TamanhoListaMB extends BaseMB{
    
    @ManagedProperty(value="#{tamanhoDAO}")
    private TamanhoDAO tamanhoDAO;
    
    @ManagedProperty(value="#{materialEgressoDAO}")
    private MaterialEgressoDAO materialEgressoDAO;
    
    private List<Tamanho> tamanhos;
    private Tamanho itemExcluir;

    @PostConstruct
    public void init() {
        tamanhos = tamanhoDAO.obterLista();
    }
    
    public void excluir(Tamanho item){
        List<MaterialEgresso> materiaisEgresso = materialEgressoDAO.obterMateriasEgressoAssociados(item);
        this.itemExcluir = item;
        if( materiaisEgresso == null || materiaisEgresso.isEmpty() ){
            abrirModal("deleteWG");
        }else{
            Mensagem.lancarMensagemErro("Tamanho já atribuído a beneficiários, por isso não pode ser exlcuído.");
        }
    }
    
    public void excluirItem(){
        try{
            tamanhoDAO.excluir(itemExcluir);
            tamanhos = tamanhoDAO.obterLista();
            Mensagem.lancarMensagemInfo("Tamanho excluído com sucesso");
            fecharModal("deleteWG");
        } catch (Exception ex) {
            Logger.getLogger(MaterialListaMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public TamanhoDAO getTamanhoDAO() {
        return tamanhoDAO;
    }

    public void setTamanhoDAO(TamanhoDAO tamanhoDAO) {
        this.tamanhoDAO = tamanhoDAO;
    }

    public List<Tamanho> getTamanhos() {
        return tamanhos;
    }

    public void setTamanhos(List<Tamanho> tamanhos) {
        this.tamanhos = tamanhos;
    }

    public MaterialEgressoDAO getMaterialEgressoDAO() {
        return materialEgressoDAO;
    }

    public void setMaterialEgressoDAO(MaterialEgressoDAO materialEgressoDAO) {
        this.materialEgressoDAO = materialEgressoDAO;
    }

    public Tamanho getItemExcluir() {
        return itemExcluir;
    }

    public void setItemExcluir(Tamanho itemExcluir) {
        this.itemExcluir = itemExcluir;
    }
    
    
    
}

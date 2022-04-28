/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MaterialDAO;
import br.org.flem.primeiroemprego.entity.Material;
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
 * @author AJLima
 */

@ManagedBean
@ViewScoped
public class MaterialCadastroMB implements Serializable{
    
    @ManagedProperty(value = "#{materialDAO}")
    private MaterialDAO materialDAO;
    private Material material;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            material = materialDAO.obterPorPK(Long.parseLong(id));
        } else {
            material = new Material();
        }
    }
    
     public String salvar() {
        if (material.getId() != null) {
            try{
                materialDAO.alterar(material);
                Mensagem.lancarMensagemInfo("Material alterado com sucesso");
            }catch(Exception e){
                Mensagem.lancarMensagemErro("Erro ao alterar o material");
            }
        } else {
            try{
                materialDAO.inserir(material);
                Mensagem.lancarMensagemInfo("Material inserido com sucesso");
            }catch(Exception e){
                Mensagem.lancarMensagemErro("Erro ao inserir o material");
            }
        }

        return "lista.xhtml";
    }

    public MaterialDAO getMaterialDAO() {
        return materialDAO;
    }

    public void setMaterialDAO(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}

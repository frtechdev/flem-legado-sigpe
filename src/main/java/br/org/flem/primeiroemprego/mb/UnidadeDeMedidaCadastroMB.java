/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MaterialDAO;
import br.org.flem.primeiroemprego.dao.UnidadeDeMedidaDAO;
import br.org.flem.primeiroemprego.entity.Material;
import br.org.flem.primeiroemprego.entity.UnidadeDeMedida;
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
public class UnidadeDeMedidaCadastroMB implements Serializable{
    
    @ManagedProperty(value = "#{unidadeDeMedidaDAO}")
    private UnidadeDeMedidaDAO unidadeDeMedidaDAO;
    private UnidadeDeMedida unidade;

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            unidade = unidadeDeMedidaDAO.obterPorPK(Long.parseLong(id));
        } else {
            unidade = new UnidadeDeMedida();
        }
    }
    
     public String salvar() {
        if (unidade.getId() != null) {
            try{
                unidadeDeMedidaDAO.alterar(unidade);
                Mensagem.lancar("Unidade alterada com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao alterar a unidade");
            }
        } else {
            try{
                unidadeDeMedidaDAO.inserir(unidade);
                Mensagem.lancar("Unidade inserida com sucesso");
            }catch(Exception e){
                Mensagem.lancar("Erro ao inserir a unidade");
            }
        }

        return "lista.xhtml";
    }

    public UnidadeDeMedidaDAO getUnidadeDeMedidaDAO() {
        return unidadeDeMedidaDAO;
    }

    public void setUnidadeDeMedidaDAO(UnidadeDeMedidaDAO unidadeDeMedidaDAO) {
        this.unidadeDeMedidaDAO = unidadeDeMedidaDAO;
    }

    public UnidadeDeMedida getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeDeMedida unidade) {
        this.unidade = unidade;
    }

     
     
}

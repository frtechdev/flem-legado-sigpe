/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.RegraContratacaoDAO;
import br.org.flem.primeiroemprego.entity.RegraContratacao;
import java.io.Serializable;
import java.util.List;
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
public class RegraContratacaoListaMB implements Serializable{
    
    
    @ManagedProperty(value="#{regraContratacaoDAO}")
    private RegraContratacaoDAO regraContratacaoDAO;
    
    private List<RegraContratacao> regras;
    
    
    @PostConstruct
    public void init() {
        regras = regraContratacaoDAO.obterLista();
    }

    public RegraContratacaoDAO getRegraContratacaoDAO() {
        return regraContratacaoDAO;
    }

    public void setRegraContratacaoDAO(RegraContratacaoDAO regraContratacaoDAO) {
        this.regraContratacaoDAO = regraContratacaoDAO;
    }

    public List<RegraContratacao> getRegras() {
        return regras;
    }

    public void setRegras(List<RegraContratacao> regras) {
        this.regras = regras;
    }
    
    
    
    
}

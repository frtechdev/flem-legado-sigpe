/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.FormacaoDAO;
import br.org.flem.primeiroemprego.entity.Formacao;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class FormacaoRN extends BaseRN<Formacao>{
    
    @ManagedProperty(value = "#{formacaoDAO}")
    private FormacaoDAO formacaoDAO;

    public List<Formacao> obterLista(){
        return formacaoDAO.obterLista();
    }
    public FormacaoDAO getFormacaoDAO() {
        return formacaoDAO;
    }

    public void setFormacaoDAO(FormacaoDAO formacaoDAO) {
        this.formacaoDAO = formacaoDAO;
    }
    
    
}

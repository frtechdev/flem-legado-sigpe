/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.CategoriaDaSituacaoDAO;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
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
public class CategoriaDaSituacaoRN extends BaseRN<CategoriaDaSituacao>{
    
    @ManagedProperty(value = "#{categoriaDaSituacaoDAO}")
    private CategoriaDaSituacaoDAO categoriaDaSituacaoDAO;
    
    public List<CategoriaDaSituacao> obterLista(){
        return categoriaDaSituacaoDAO.obterLista();
    }

    public CategoriaDaSituacaoDAO getCategoriaDaSituacaoDAO() {
        return categoriaDaSituacaoDAO;
    }

    public void setCategoriaDaSituacaoDAO(CategoriaDaSituacaoDAO categoriaDaSituacaoDAO) {
        this.categoriaDaSituacaoDAO = categoriaDaSituacaoDAO;
    }

    
    
    
}

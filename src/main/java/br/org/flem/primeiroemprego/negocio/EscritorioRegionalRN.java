/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.EscritorioRegionalDAO;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
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
public class EscritorioRegionalRN extends BaseRN<EscritorioRegional>{
    
    @ManagedProperty(value = "#{escritorioRegionalDAO}")
    private EscritorioRegionalDAO escritorioRegionalDAO;
    
    public List<EscritorioRegional> obterLista(){
        return escritorioRegionalDAO.obterLista();
    }
    
    public EscritorioRegional obterPorId(Long id){
        return escritorioRegionalDAO.obterPorPK(id);
    }

    public EscritorioRegionalDAO getEscritorioRegionalDAO() {
        return escritorioRegionalDAO;
    }

    public void setEscritorioRegionalDAO(EscritorioRegionalDAO escritorioRegionalDAO) {
        this.escritorioRegionalDAO = escritorioRegionalDAO;
    }
    
    
}

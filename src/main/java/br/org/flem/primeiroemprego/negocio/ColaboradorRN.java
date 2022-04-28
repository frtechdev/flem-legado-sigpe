/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.ColaboradorDAO;
import br.org.flem.primeiroemprego.dao.EscritorioRegionalDAO;
import br.org.flem.primeiroemprego.entity.Colaborador;
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
public class ColaboradorRN extends BaseRN<Colaborador>{
    
    @ManagedProperty(value = "#{colaboradorDAO}")
    private ColaboradorDAO colaboradorDAO;
    
    public List<Colaborador> obterLista(){
        return colaboradorDAO.obterLista();
    }
    
    public Colaborador obterPorId(Long id){
        return colaboradorDAO.obterPorPK(id);
    }
    
    public List<Colaborador> obterPorEscritorioRegional(EscritorioRegional escritorio){
        return colaboradorDAO.obterPorEscritorioRegional(escritorio);
    }

    public ColaboradorDAO getColaboradorDAO() {
        return colaboradorDAO;
    }

    public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
        this.colaboradorDAO = colaboradorDAO;
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.VagaDAO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.Vaga;
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
public class VagaRN extends BaseRN<Vaga>{
    
    @ManagedProperty(value = "#{vagaDAO}")
    private VagaDAO vagaDAO;

    public List<Municipio> obterMunicipiosDasVagas(){
        return vagaDAO.obterMunicipios();
    } 
    
    public List<Demandante> obterDemandantesDasVagas(){
        return vagaDAO.obterDemandantes();
    } 
    public VagaDAO getVagaDAO() {
        return vagaDAO;
    }

    public void setVagaDAO(VagaDAO vagaDAO) {
        this.vagaDAO = vagaDAO;
    }
    
}

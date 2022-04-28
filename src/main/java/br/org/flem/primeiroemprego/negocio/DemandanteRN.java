/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.Municipio;
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
public class DemandanteRN extends BaseRN<Demandante>{
    
    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;
    
    @ManagedProperty(value = "#{vagaRN}")
    private VagaRN vagaRN;

    public List<Demandante> obterLista(){
        return demandanteDAO.obterLista();
    }
    
    public List<Demandante> obterPorMunicipio(Municipio municipio) {
        return demandanteDAO.obterPorMunicipio(municipio);
    }
    
    public List<Demandante> obterPorMunicipios(List<Municipio> municipios) {
        return demandanteDAO.obterDemandantePorMunicio(municipios);
    }
    
    public List<Demandante> obterDemandantesDasVagas(){
        return vagaRN.obterDemandantesDasVagas();
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
    }

    public VagaRN getVagaRN() {
        return vagaRN;
    }

    public void setVagaRN(VagaRN vagaRN) {
        this.vagaRN = vagaRN;
    }

    
    
}

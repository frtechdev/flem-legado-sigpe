/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
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
public class MunicipioRN extends BaseRN<Municipio>{
    
    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    
    @ManagedProperty(value = "#{vagaRN}")
    private VagaRN vagaRN;

    public List<Municipio> obterLista(){
        return municipioDAO.obterLista();
    }
    
    public List<Municipio> obterMuniciosVagas(){
        return vagaRN.obterMunicipiosDasVagas();
    }
    
    public List<Municipio> obterPorEscritorioRegional(EscritorioRegional escritorio){
        return municipioDAO.obterPorEscritorioRegional(escritorio);
    }
    
    public List<Municipio> obterPorEscritoriosRegionais(List<EscritorioRegional> escritorios){
        List<Municipio> municipios = municipioDAO.obterPorEscritorioRegional(escritorios);
        if(escritorios != null && !escritorios.isEmpty()){
            for(EscritorioRegional esc : escritorios){
                if(esc.getId() == null || esc.getId() == 0l){
                    municipios.addAll(this.municipioDAO.obterPorEscritorioNaoImpl());
                    break;
                }
            }
        }
        return municipios;
    }
    public List<Municipio> obterPorEscritorioNaoImpl(){
        return municipioDAO.obterPorEscritorioNaoImpl();
    }
    
    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public VagaRN getVagaRN() {
        return vagaRN;
    }

    public void setVagaRN(VagaRN vagaRN) {
        this.vagaRN = vagaRN;
    }
    
    
    
}

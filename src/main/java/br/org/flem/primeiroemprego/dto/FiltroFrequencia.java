
package br.org.flem.primeiroemprego.dto;

import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.entity.Territorio;
import java.util.List;

/**
 * Classe de filtro de Pesquisa de Frequência
 * Classe FiltroFrequencia
 * @author <code>tscortes@flem.org.br</code>
 * 19/10/2018
 * @version 1.0
 */
public class FiltroFrequencia {
    
    private List<EscritorioRegional> escritoriosRegionais;
    private List<Municipio> municipios;
    private List<Territorio> territorios;
    private List<Demandante> demandantes;
    private List<CategoriaDaSituacao> categorias;
    private String acolhido;
    
    private List<String> cpfs;

    public List<EscritorioRegional> getEscritoriosRegionais() {
        return escritoriosRegionais;
    }

    public void setEscritoriosRegionais(List<EscritorioRegional> escritoriosRegionais) {
        this.escritoriosRegionais = escritoriosRegionais;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public List<Territorio> getTerritorios() {
        return territorios;
    }

    public void setTerritorios(List<Territorio> territorios) {
        this.territorios = territorios;
    }

    public List<Demandante> getDemandantes() {
        return demandantes;
    }

    public void setDemandantes(List<Demandante> demandantes) {
        this.demandantes = demandantes;
    }

    public List<CategoriaDaSituacao> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDaSituacao> categorias) {
        this.categorias = categorias;
    }

    public List<String> getCpfs() {
        return cpfs;
    }

    public void setCpfs(List<String> cpfs) {
        this.cpfs = cpfs;
    }

    public String getAcolhido() {
        return acolhido;
    }

    public void setAcolhido(String acolhido) {
        this.acolhido = acolhido;
    }
    
    
    
}

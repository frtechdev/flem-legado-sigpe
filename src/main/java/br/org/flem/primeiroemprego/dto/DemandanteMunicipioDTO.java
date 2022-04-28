
package br.org.flem.primeiroemprego.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * Descreva Sua Classe
 * Classe DemandanteMunicipioDTO
 * @author <code>tscortes@flem.org.br</code>
 * 03/05/2019
 * @version 1.0
 */
public class DemandanteMunicipioDTO implements Serializable {
    
    private Long id;
    private Long idDemandante;
    private Long idMunicipio;
    private String nomeDemandante;
    private String nomeMunicipio;

    public DemandanteMunicipioDTO(Long idDemandante, Long idMunicipio, String nomeDemandante, String nomeMunicipio) {
        this.idDemandante = idDemandante;
        this.idMunicipio = idMunicipio;
        this.nomeDemandante = nomeDemandante;
        this.nomeMunicipio = nomeMunicipio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DemandanteMunicipioDTO() {
    }

    public Long getIdDemandante() {
        return idDemandante;
    }

    public void setIdDemandante(Long idDemandante) {
        this.idDemandante = idDemandante;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getNomeDemandante() {
        return nomeDemandante;
    }

    public void setNomeDemandante(String nomeDemandante) {
        this.nomeDemandante = nomeDemandante;
    }

    public String getNomeMunicipio() {
        return nomeMunicipio;
    }

    public void setNomeMunicipio(String nomeMunicipio) {
        this.nomeMunicipio = nomeMunicipio;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DemandanteMunicipioDTO other = (DemandanteMunicipioDTO) obj;
        if (!Objects.equals(this.idDemandante, other.idDemandante)) {
            return false;
        }
        if (!Objects.equals(this.idMunicipio, other.idMunicipio)) {
            return false;
        }
        return true;
    }
    
    
    
    
}

package br.org.flem.primeiroemprego.dto;

import java.io.Serializable;

/**
 * @author <code>tscortes@flem.org.br</code> 23/05/2019
 * @version 1.0
 */
public class MunicipioDTO implements Serializable {

    private Long id;
    private String nome;
    private String uf;
    private Long totalEgressos;

    public MunicipioDTO(Long id, String nome, String uf, Long totalEgressos) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
        this.totalEgressos = totalEgressos;
    }

    public MunicipioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Long getTotalEgressos() {
        return totalEgressos;
    }

    public void setTotalEgressos(Long totalEgressos) {
        this.totalEgressos = totalEgressos;
    }
    
    
}

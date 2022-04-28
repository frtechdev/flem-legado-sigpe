package br.org.flem.primeiroemprego.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author emsilva
 */
@Entity
public class CI extends Documento{

    @NotNull
    private Long numero;
    
    @NotNull
    private Integer ano;
    
    private Boolean fechada = false;
    
    @NotNull
    private String assunto;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "CI_Egressos",
            joinColumns = @JoinColumn(name="id_documento", referencedColumnName="id_documento"),
            inverseJoinColumns = @JoinColumn(name="id_egresso", referencedColumnName="id_egresso"))
    private List<Egresso> egressos;

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public List<Egresso> getEgressos() {
        return egressos;
    }

    public void setEgressos(List<Egresso> egressos) {
        this.egressos = egressos;
    }

    public Boolean getFechada() {
        return fechada;
    }

    public void setFechada(Boolean fechada) {
        this.fechada = fechada;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getIdentificacao(){
        return numero+"/"+ano;
    }

}

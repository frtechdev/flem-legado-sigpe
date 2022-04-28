package br.org.flem.primeiroemprego.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author emsilva
 */
@Entity
public class Oficio extends Documento{

    @NotNull
    private Long numero;

    @NotNull
    private Integer ano;

    private String assunto;
    
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataGeracao;

    @ManyToOne
    @JoinColumn(name="id_egresso")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name = "id_modelo")
    private ModeloDeOficio modelo;

    private String destinatarioExterno;
    
    public Oficio(){
        
    }
    
    public Oficio(Long id, String nome){
        super.setId(id);
        super.setNome(nome);
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public ModeloDeOficio getModelo() {
        return modelo;
    }

    public void setModelo(ModeloDeOficio modelo) {
        this.modelo = modelo;
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

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

    public String getDestinatarioExterno() {
        return destinatarioExterno;
    }

    public void setDestinatarioExterno(String destinatarioExterno) {
        this.destinatarioExterno = destinatarioExterno;
    }
    
    public String getIdentificacao(){
        return numero+"/"+ano;
    }

    public Date getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(Date dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    
}

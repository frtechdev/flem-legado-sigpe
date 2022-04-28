/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.entity;

import br.org.flem.primeiroemprego.util.annotation.Alias;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 *
 * @author AJLima
 */
@Entity
@Audited
public class MaterialEgresso extends UID<Long>{
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @ManyToOne
    @NotAudited
    @Alias(value="Material")
    private Material material;
    
    @Min(1)
    @NotNull
    @Alias(value="Quantidade")
    private int quantidade;
    @ManyToOne
    @NotNull
    @NotAudited
    @Alias(value="Tamanho")
    private Tamanho tamanho;
    @ManyToOne
    @NotNull
    @NotAudited
    @Alias(value="Unidade")
    private UnidadeDeMedida unidade;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Alias(value="Data de Recebimento")
    private Date dataRecebimento;
    @ManyToOne
    @JoinColumn(name="id_egresso")
    private Egresso egresso;
    
    @Alias(value="Observacão")
    private String observacao;
    @NotNull
    
    @Alias(value="Lote")
    private String lote;

    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public Date getDataRecebimento() {
        return dataRecebimento;
    }

    public void setDataRecebimento(Date dataRecebimento) {
        this.dataRecebimento = dataRecebimento;
    }

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public UnidadeDeMedida getUnidade() {
        return unidade;
    }

    public void setUnidade(UnidadeDeMedida unidade) {
        this.unidade = unidade;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }



 
    
    
    
    
    
    
}

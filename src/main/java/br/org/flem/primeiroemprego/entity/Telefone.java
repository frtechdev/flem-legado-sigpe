/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tscortes
 */
@Entity
public class Telefone extends UID<TelefoneID>{

    @EmbeddedId
    private TelefoneID id;
    @Enumerated(EnumType.STRING)
    private EnumTipoTelefone tipo;
    @NotNull
    @Column(length = 11)
    private String numero;
    private String descricao;

    public Telefone() {
    }
    
    public Telefone(Egresso egresso) {
        this.id = new TelefoneID(egresso);
    }
   
    @Override
    public TelefoneID getId() {
        return id;
    }
    @Override
    public void setId(TelefoneID id) {
        this.id = id;
    }

    public EnumTipoTelefone getTipo() {
        return tipo;
    }

    public void setTipo(EnumTipoTelefone tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + Objects.hashCode(this.id);
        hash = 31 * hash + Objects.hashCode(this.tipo);
        hash = 31 * hash + Objects.hashCode(this.numero);
        hash = 31 * hash + Objects.hashCode(this.descricao);
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
        final Telefone other = (Telefone) obj;
        if (!Objects.equals(this.numero, other.numero)) {
            return false;
        }
        if (!Objects.equals(this.descricao, other.descricao)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return this.tipo == other.tipo;
    }

    
    
}

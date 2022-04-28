/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dto;

import java.io.Serializable;

/**
 *
 * @author tscortes
 */
public class TelefoneDTO implements Serializable{
    
    private Long idEgresso;
    private Short idSeq;
    private String tipo;
    private String numero;
    private String descricao;

    public Long getIdEgresso() {
        return idEgresso;
    }

    public void setIdEgresso(Long idEgresso) {
        this.idEgresso = idEgresso;
    }

    public Short getIdSeq() {
        return idSeq;
    }

    public void setIdSeq(Short idSeq) {
        this.idSeq = idSeq;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
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
    
    
}

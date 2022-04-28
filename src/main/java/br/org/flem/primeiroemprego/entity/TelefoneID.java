/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.entity;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author tscortes
 */
@Embeddable
public class TelefoneID implements Serializable{
    
    private Short seq;
    
    @ManyToOne
    @JoinColumn(name = "id_egresso")
    private Egresso egresso;

    public TelefoneID(Egresso egresso) {
        this.egresso = egresso;
    }

    public TelefoneID() {
    }

    public TelefoneID(Egresso egresso, Short seq) {
        this.egresso = egresso;
        this.seq = seq;
    }

    public Short getSeq() {
        return seq;
    }
    public void setSeq(Short seq) {
        this.seq = seq;
    }
    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }
    
    
}

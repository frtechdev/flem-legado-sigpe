package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author emsilva
 */
@Entity
public class EgressoLista extends UID<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_egressolista")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_egresso")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name="id_lista")
    private Lista lista;

    private Boolean feito = false;
    
    private Boolean deAcordo;

    public EgressoLista(Long id, Egresso egresso, Lista lista, Boolean feito) {
        this.id = id;
        this.egresso = egresso;
        this.lista = lista;
        this.feito = feito;
    }

    public EgressoLista() {
    }
    
    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public Boolean getFeito() {
        return feito;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }

    public Boolean getDeAcordo() {
        return deAcordo;
    }

    public void setDeAcordo(Boolean deAcordo) {
        this.deAcordo = deAcordo;
    }
    
}

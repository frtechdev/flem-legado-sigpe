
package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Descreva Sua Classe
 * Classe MonitorDemandante
 * @author <code>tscortes@flem.org.br</code>
 * 03/05/2019
 * @version 1.0
 */
@Entity
public class MonitorDemandante extends UID<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name="id_demandante")
    private Demandante demandante;
    
    @ManyToOne
    @JoinColumn(name="id_colaborador")
    private Colaborador monitor;
    
    @ManyToOne
    @JoinColumn(name="id_municipio")
    private Municipio municipio;
    
    @ManyToOne
    @JoinColumn(name="id_vaga")
    private Vaga vaga;

    public MonitorDemandante(Demandante demandante, Colaborador monitor, Municipio municipio) {
        this.demandante = demandante;
        this.monitor = monitor;
        this.municipio = municipio;
    }

    public MonitorDemandante() {
    }

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Demandante getDemandante() {
        return demandante;
    }

    public void setDemandante(Demandante demandante) {
        this.demandante = demandante;
    }

    public Colaborador getMonitor() {
        return monitor;
    }

    public void setMonitor(Colaborador monitor) {
        this.monitor = monitor;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }
    
    
}

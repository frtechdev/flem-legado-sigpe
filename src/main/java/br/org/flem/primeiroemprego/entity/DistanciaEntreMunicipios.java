package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author emsilva
 */
@Entity
public class DistanciaEntreMunicipios extends UID<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_distanciaEntreMunicipios")
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_municipioOrigem")
    private Municipio municipioOrigem;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "id_municipioDestino")
    private Municipio municipioDestino;

    private Long distancia; //metros

    public Municipio getMunicipioOrigem() {
        return municipioOrigem;
    }

    public void setMunicipioOrigem(Municipio municipioOrigem) {
        this.municipioOrigem = municipioOrigem;
    }

    public Municipio getMunicipioDestino() {
        return municipioDestino;
    }

    public void setMunicipioDestino(Municipio municipioDestino) {
        this.municipioDestino = municipioDestino;
    }

    public Long getDistancia() {
        return distancia;
    }

    public void setDistancia(Long distancia) {
        this.distancia = distancia;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}

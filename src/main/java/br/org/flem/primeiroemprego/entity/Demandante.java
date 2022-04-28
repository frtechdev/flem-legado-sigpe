package br.org.flem.primeiroemprego.entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author emsilva
 */
@Entity
public class Demandante extends UID<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_demandante")
    private Long id;

    private String sigla;

    private String nome;
    
    @OneToMany(mappedBy = "demandante")
    private Set<MonitorDemandante> associacoes;
    
    @OneToMany(mappedBy = "demandante")
    private Set<Vaga> vagas = new HashSet<>(0);

    public Demandante(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
    public Demandante() {

    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Vaga> getVagas() {
        return vagas;
    }

    public void setVagas(Set<Vaga> vagas) {
        this.vagas = vagas;
    }

    public Set<MonitorDemandante> getAssociacoes() {
        return associacoes;
    }

    public void setAssociacoes(Set<MonitorDemandante> associacoes) {
        this.associacoes = associacoes;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Demandante other = (Demandante) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    
    

}

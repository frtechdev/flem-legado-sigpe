package br.org.flem.primeiroemprego.entity;

import java.util.HashSet;
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
public class Territorio extends UID<Long>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_territorio")
    private Long id;

    private String nome;
    @OneToMany(mappedBy = "territorio")
    private Set<Municipio> municipios  = new HashSet<>(0);

    public Territorio(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    
     public Territorio() {
     
     }
     
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) { 
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Set<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(Set<Municipio> municipios) {
        this.municipios = municipios;
    }
    

}

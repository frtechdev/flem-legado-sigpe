package br.org.flem.primeiroemprego.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emsilva
 */
@Entity
public class Municipio extends UID<Long>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_municipio")
    private Long id;

    @NotEmpty(message = "{municipio.nome.notempty}")
    private String nome;
    
    @NotEmpty(message = "{municipio.nome.notempty}")
    private String uf;
    
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_territorio")
    private Territorio territorio;
    
    @ManyToOne
    @JoinColumn(name="ID_ESC_REGIONAL")
    private EscritorioRegional escritorioRegional;
    
    @OneToMany(mappedBy = "municipio")
    private Set<MonitorDemandante> associacoes;

    public Municipio(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Municipio() {
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

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Territorio getTerritorio() {
        return territorio;
    }

    public void setTerritorio(Territorio territorio) {
        this.territorio = territorio;
    }

    public EscritorioRegional getEscritorioRegional() {
        return escritorioRegional;
    }

    public void setEscritorioRegional(EscritorioRegional escritorioRegional) {
        this.escritorioRegional = escritorioRegional;
    }

    public Set<MonitorDemandante> getAssociacoes() {
        return associacoes;
    }

    public void setAssociacoes(Set<MonitorDemandante> associacoes) {
        this.associacoes = associacoes;
    }
    
    
    
    
}
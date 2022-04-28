package br.org.flem.primeiroemprego.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author tscortes
 */
@Entity
public class Colaborador extends UID<Long> implements Comparable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_colaborador")
    private Long id;

    @NotEmpty
    private String nome;
    @NotEmpty
    private String cargo;
    
    private Long celular;
    
    private Long telefone;
    
    private Boolean ativo = Boolean.TRUE;
    
    @ManyToOne
    @JoinColumn(name="ID_ESC_REGIONAL")
    private EscritorioRegional escritorioRegional;
    
    private String email;
    
    @OneToMany(mappedBy = "monitor", fetch = FetchType.EAGER)
    private Set<MonitorDemandante> associacoes;
    
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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }
    public String getDescricaoAtivo(){
        return this.ativo ? "Ativo" : "Inativo";
    }

    public EscritorioRegional getEscritorioRegional() {
        return escritorioRegional;
    }

    public void setEscritorioRegional(EscritorioRegional escritorioRegional) {
        this.escritorioRegional = escritorioRegional;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public int compareTo(Object o) {
        Colaborador outroColaborador = (Colaborador) o;
        return this.nome.compareTo(outroColaborador.getNome());
    }

    public Set<MonitorDemandante> getAssociacoes() {
        return associacoes;
    }

    public void setAssociacoes(Set<MonitorDemandante> associacoes) {
        this.associacoes = associacoes;
    }

    
    
    
}
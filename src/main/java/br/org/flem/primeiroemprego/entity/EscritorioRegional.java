package br.org.flem.primeiroemprego.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author tscortes
 */
@Entity
public class EscritorioRegional extends UID<Long>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_esc_regional")
    private Long id;

    @NotEmpty
    private String nome;
    
    private Long cep;
    @NotEmpty
    private String logradouro;
    @NotEmpty
    private String bairro;
    @ManyToOne
    @JoinColumn(name = "ID_MUNICIPIO", nullable = false)
    @NotNull
    private Municipio cidade;
    
    private Long telefone;
    @NotNull
    private Long celular;
    
    private String email;
    
    @OneToMany(mappedBy = "escritorioRegional", fetch = FetchType.EAGER)
    private List<Colaborador> colaboradores = new ArrayList<>();
    
    @OneToMany(mappedBy = "escritorioRegional")
    private List<Municipio> municipios = new ArrayList<>();
    
    private Boolean ativo = Boolean.TRUE;

    public EscritorioRegional(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public EscritorioRegional() {
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Long getCep() {
        return cep;
    }

    public void setCep(Long cep) {
        this.cep = cep;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Colaborador> getColaboradores() {
        return colaboradores;
    }

    public void setColaboradores(List<Colaborador> colaboradores) {
        this.colaboradores = colaboradores;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public Municipio getCidade() {
        return cidade;
    }

    public void setCidade(Municipio cidade) {
        this.cidade = cidade;
    }

    public Long getCelular() {
        return celular;
    }

    public void setCelular(Long celular) {
        this.celular = celular;
    }
    
    @Transient
    public String getDescricaoEndereco() {
        return logradouro + ", "+bairro+" - "+cidade.getNome()+" - "+cep;
    }
}
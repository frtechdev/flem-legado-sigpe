package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emsilva
 */
@Entity
public class PontoFocal extends UID<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pontoFocal")
    private Long id;

    @NotEmpty(message="{pontoFocal.nome.notempty}")
    private String nome;
    
    private String telefone1;
    private String telefone2;
    private String telefone3;
    private String telefone4;
    private String telefone5;
    
    private String email;
    
    @ManyToOne
    @NotNull(message="{pontoFocal.unidadeDeLotacao.notnull}")
    @JoinColumn(name="id_unidadeDeLotacao")
    private UnidadeDeLotacao unidadeDeLotacao;

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

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public String getTelefone4() {
        return telefone4;
    }

    public void setTelefone4(String telefone4) {
        this.telefone4 = telefone4;
    }

    public String getTelefone5() {
        return telefone5;
    }

    public void setTelefone5(String telefone5) {
        this.telefone5 = telefone5;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UnidadeDeLotacao getUnidadeDeLotacao() {
        return unidadeDeLotacao;
    }

    public void setUnidadeDeLotacao(UnidadeDeLotacao unidadeDeLotacao) {
        this.unidadeDeLotacao = unidadeDeLotacao;
    }

}

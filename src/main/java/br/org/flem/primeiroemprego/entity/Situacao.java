package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emsilva
 */
@Entity
public class Situacao extends UID<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_situacao")
    private Long id;

    @NotEmpty(message="{situacao.nome.notempty}")
    private String nome;

    @ManyToOne
    @NotNull(message="{situacao.categoria.notnull}")
    @JoinColumn(name="id_categoria")
    @Fetch(FetchMode.SELECT)
    private CategoriaDaSituacao categoria;

    private String cor;

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

    public CategoriaDaSituacao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDaSituacao categoria) {
        this.categoria = categoria;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

}

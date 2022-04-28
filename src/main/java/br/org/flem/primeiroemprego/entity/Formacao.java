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

/**
 *
 * @author emsilva
 */
@Entity
public class Formacao extends UID<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_formacao")
    private Long id;
    
    private String nome;
    
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @NotNull(message = "{formacao.eixoDeFormacao.notnull}")
    @JoinColumn(name="id_eixoDeFormacao")
    private EixoDeFormacao eixoDeFormacao;

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

    public EixoDeFormacao getEixoDeFormacao() {
        return eixoDeFormacao;
    }

    public void setEixoDeFormacao(EixoDeFormacao eixoDeFormacao) {
        this.eixoDeFormacao = eixoDeFormacao;
    }

}

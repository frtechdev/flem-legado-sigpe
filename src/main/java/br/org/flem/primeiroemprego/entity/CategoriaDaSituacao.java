package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emsilva
 */
@Entity
public class CategoriaDaSituacao extends UID<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_categoriaDaSituacao")
    private Long id;

    @NotEmpty(message="{categoriaDaSituacao.nome.notempty}")
    private String nome;
    
    private String cor;

    public CategoriaDaSituacao(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public CategoriaDaSituacao() {
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

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

}

package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;


/**
 *
 * @author AJLima
 */
@Entity
public class Tamanho extends UID<Long>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_tamanho")
    private Long id;
    @NotEmpty
    private String descricao;
    @NotEmpty
    private String sigla;
    @Column(unique = true)
    private Byte ordem;
    

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Byte getOrdem() {
        return ordem;
    }

    public void setOrdem(Byte ordem) {
        this.ordem = ordem;
    }

}

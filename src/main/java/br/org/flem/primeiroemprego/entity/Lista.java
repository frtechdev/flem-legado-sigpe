package br.org.flem.primeiroemprego.entity;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;

@Entity
public class Lista extends UID<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_lista")
    private Long id;

    private String nome;
    
    @ElementCollection(fetch=FetchType.EAGER)
    @Column(name="usuarioResponsavel")
    @CollectionTable(name="Lista_UsuarioResponsavel", joinColumns={@JoinColumn(name="id_lista")})
    @Fetch(value= FetchMode.SELECT)
    private List<String> usuariosResponsaveis;
    
    @Formula("(SELECT CASE COUNT(*) WHEN 0 THEN 1 ELSE 0 END  FROM EgressoLista el WHERE el.feito = 0 AND el.id_lista = id_lista)")
    private boolean concluida;
    
    private Boolean ativo = Boolean.TRUE;
    @Transient
    public String situacao;

    @Override
    public Long getId() {
        return this.id;
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

    public List<String> getUsuariosResponsaveis() {
        return usuariosResponsaveis;
    }

    public void setUsuariosResponsaveis(List<String> usuariosResponsaveis) {
        this.usuariosResponsaveis = usuariosResponsaveis;
    }

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getSituacao() {
        if( concluida ){
           situacao = "Concluída"; 
        }else{
            if( ativo ){
                situacao = "Não Concluída"; 
            }else{
                situacao = "Inativa";
            }
        }
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }
    
    

}


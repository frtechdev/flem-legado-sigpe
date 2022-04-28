package br.org.flem.primeiroemprego.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;


/**
 *
 * @author emsilva
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Acao extends UID<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_acao")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_egresso")
    private Egresso egresso;

    @ManyToOne
    @JoinColumn(name="id_tipoDeAcao")
    @NotNull(message="{acao.tipoDeAcao.notnull}")
    @Fetch(FetchMode.SELECT)
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TipoDeAcao tipoDeAcao;

    @NotNull(message = "{acao.data.notnull}")
    @Temporal(TemporalType.TIMESTAMP)
    @Audited
    private Date data;

    @Type(type="text")
    @Audited
    private String descricao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

    public TipoDeAcao getTipoDeAcao() {
        return tipoDeAcao;
    }

    public void setTipoDeAcao(TipoDeAcao tipoDeAcao) {
        this.tipoDeAcao = tipoDeAcao;
    }
    
    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
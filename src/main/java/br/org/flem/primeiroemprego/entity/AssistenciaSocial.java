package br.org.flem.primeiroemprego.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
public class AssistenciaSocial extends UID<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_assitenciasocial")
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_egresso")
    private Egresso egresso;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_tipoAssistenciaSocial")
    @NotNull(message="{assistenciaSocial.tipoAssistenciaSocial.notnull}")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TipoAssistenciaSocial tipoAssistenciaSocial;

    @Audited
    @NotNull(message = "{assistenciaSocial.data.notnull}")
    @Temporal(TemporalType.TIMESTAMP)
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

    public TipoAssistenciaSocial getTipoAssistenciaSocial() {
        return tipoAssistenciaSocial;
    }

    public void setTipoAssistenciaSocial(TipoAssistenciaSocial tipoAssistenciaSocial) {
        this.tipoAssistenciaSocial = tipoAssistenciaSocial;
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
package br.org.flem.primeiroemprego.entity;

import br.org.flem.fwe.util.Data;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emsilva
 */
@Entity
public class Notificacao extends UID<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_notificacao")
    private Long id;

    @NotNull(message="{notificacao.usuarioNotificado.notempty}")
    private Integer usuarioNotificado;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataLimite;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dataConcluido;

    @Column(length = 1024)
    @NotEmpty(message="{notificacao.mensagem.notempty}")
    private String mensagem;

    private Boolean urgente = false;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUsuarioNotificado() {
        return usuarioNotificado;
    }

    public void setUsuarioNotificado(Integer usuarioNotificado) {
        this.usuarioNotificado = usuarioNotificado;
    }

    public Date getDataLimite() {
        return dataLimite;
    }

    public void setDataLimite(Date dataLimite) {
        this.dataLimite = dataLimite;
    }

    public Date getDataConcluido() {
        return dataConcluido;
    }

    public void setDataConcluido(Date dataConcluido) {
        this.dataConcluido = dataConcluido;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getUrgente() {
        return urgente;
    }

    public void setUrgente(Boolean urgente) {
        this.urgente = urgente;
    }
    
    public int qtdDiasAtiva(){
        return Data.dataDiff(getDataCriacao(), dataConcluido != null ? dataConcluido : new Date());
    }

}

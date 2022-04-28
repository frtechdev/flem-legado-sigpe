package br.org.flem.primeiroemprego.entity;

import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.BeansUtil;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 *
 * @author emsilva
 * @param <I>
 */

@MappedSuperclass
public abstract class UID<I extends Serializable> implements Serializable {
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;
    @Column(name = "usuario_criacao")
    private String usuarioCriacao;
    @Column(name = "usuario_atualizacao")
    private String usuarioAlteracao;
    @Version
    @Column(name="version", columnDefinition="bigint default 0", nullable = false)
    private long version = 0l;

    public abstract I getId() ;

    public abstract void setId(I id) ;

    public String getUsuarioCriacao() {
        return usuarioCriacao;
    }

    public void setUsuarioCriacao(String usuarioCriacao) {
        this.usuarioCriacao = usuarioCriacao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getUsuarioAlteracao() {
        return usuarioAlteracao;
    }

    public void setUsuarioAlteracao(String usuarioAlteracao) {
        this.usuarioAlteracao = usuarioAlteracao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UID other = (UID) obj;
        if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @PrePersist
    public void prePersist() {
        UsuarioLogadoMB usuarioLogadoMB = (UsuarioLogadoMB) BeansUtil.getBean("usuarioLogadoMB");
        this.setDataCriacao(new Date());
        if(getUsuarioCriacao() == null){//permite que seja informado o usuario criador em situações que estão fora do contexto do JSF
            this.setUsuarioCriacao(usuarioLogadoMB != null ? usuarioLogadoMB.getUsuario().getUsername() : "publico");
        }
    }

    @PreUpdate
    public void preUpdate() {
        UsuarioLogadoMB usuarioLogadoMB = (UsuarioLogadoMB) BeansUtil.getBean("usuarioLogadoMB");
        this.setDataAlteracao(new Date());
        this.setUsuarioAlteracao(usuarioLogadoMB != null ? usuarioLogadoMB.getUsuario().getUsername() : "publico");
    }
    
    
}
package br.org.flem.primeiroemprego.entity;

import java.util.Objects;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

/**
 *
 * @author emsilva
 */
@Entity
@Table(name = "Evento_Egresso")
public class EventoEgresso extends UID<EventoEgressoID> {

    @EmbeddedId
    private EventoEgressoID id;
    
    @Enumerated(EnumType.STRING)
    private EnumSituacaoEvento situacao;

    public EventoEgresso() {
        id = new EventoEgressoID();
    }

    public EventoEgresso(EventoEgressoID id) {
        this.id = id;
    }

    public EventoEgresso(EventoEgressoID id, EnumSituacaoEvento situacao) {
        this.id = id;
        this.situacao = situacao;
    }

    @Override
    public EventoEgressoID getId() {
        return id;
    }

    @Override
    public void setId(EventoEgressoID id) {
        this.id = id;
    }

    public EnumSituacaoEvento getSituacao() {
        return situacao;
    }

    public void setSituacao(EnumSituacaoEvento situacao) {
        this.situacao = situacao;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EventoEgresso other = (EventoEgresso) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
}

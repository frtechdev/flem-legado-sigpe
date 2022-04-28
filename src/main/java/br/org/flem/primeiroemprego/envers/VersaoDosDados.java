package br.org.flem.primeiroemprego.envers;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 *
 * @author emsilva
 */
@Entity
@RevisionEntity(VersaoDosDadosListener.class)
public class VersaoDosDados implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private int id_versao;

    @RevisionTimestamp
    private long timestamp;

    private String login;

    public int getId_versao() {
        return id_versao;
    }

    public void setId_versao(int id_versao) {
        this.id_versao = id_versao;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

}

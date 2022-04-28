package br.org.flem.primeiroemprego.entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emsilva
 */
@Entity
public class EnvioDeEmail extends UID<Long> implements Comparable{

    public EnvioDeEmail() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_campanha")
    private Campanha campanha;

    @Enumerated(EnumType.ORDINAL)
    private StatusEnvioDaCampanha status = StatusEnvioDaCampanha.SALVO;

    private String descricaoEnvio;
    @NotEmpty
    private String email;

    private String emailSecundario;

    @Transient
    private Map<String, String> tokensAdicionais = new HashMap<String, String>();

    @OneToOne
    private Egresso egresso;
    @OneToOne
    private Acao acao;

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public String getDescricaoEnvio() {
        return descricaoEnvio;
    }

    public void setDescricaoEnvio(String descricaoEnvio) {
        this.descricaoEnvio = descricaoEnvio;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public StatusEnvioDaCampanha getStatus() {
        return status;
    }

    public void setStatus(StatusEnvioDaCampanha status) {
        this.status = status;
    }

    public Map<String, String> getTokensAdicionais() {
        return tokensAdicionais;
    }

    public void adicionarToken(String nome, String valor) {
        tokensAdicionais.put(nome, valor);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

    public Acao getAcao() {
        return acao;
    }

    public void setAcao(Acao acao) {
        this.acao = acao;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.egresso.getNome());
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
        final EnvioDeEmail other = (EnvioDeEmail) obj;
        if (!Objects.equals(this.egresso.getNome(), other.egresso.getNome())) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Object envio) {
        EnvioDeEmail envioOutro = (EnvioDeEmail) envio;
        return this.egresso.getNome().compareTo(envioOutro.getEgresso().getNome());
    }

    public String getEmailSecundario() {
        return emailSecundario;
    }

    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

}

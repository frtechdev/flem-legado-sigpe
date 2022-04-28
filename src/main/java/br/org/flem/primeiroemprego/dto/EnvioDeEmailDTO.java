package br.org.flem.primeiroemprego.dto;

import br.org.flem.primeiroemprego.entity.StatusEnvioDaCampanha;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe EnvioDeEmailDTO
 * @author <code>tscortes@flem.org.br</code>
 * 18/04/2019
 * @version 1.0
 */
public class EnvioDeEmailDTO {
    
    private Long id;
    private Long idCampanha;
    private StatusEnvioDaCampanha status;
    private String descricaoEnvio;
    private String email;
    private String emailSecundario;
    private Long idEgresso;
    private Long idAcao;
    
    private Map<String,String> tokensAdicionais = new HashMap<String, String>();

    public EnvioDeEmailDTO() {
    }

    public EnvioDeEmailDTO(Long id, Long idCampanha, StatusEnvioDaCampanha status, String descricaoEnvio, String email, String emailSecundario, Long idEgresso, Long idAcao) {
        this.id = id;
        this.idCampanha = idCampanha;
        this.status = status;
        this.descricaoEnvio = descricaoEnvio;
        this.email = email;
        this.emailSecundario = emailSecundario;
        this.idEgresso = idEgresso;
        this.idAcao = idAcao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(Long idCampanha) {
        this.idCampanha = idCampanha;
    }

    public StatusEnvioDaCampanha getStatus() {
        return status;
    }

    public void setStatus(StatusEnvioDaCampanha status) {
        this.status = status;
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

    public String getEmailSecundario() {
        return emailSecundario;
    }

    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

    public Long getIdEgresso() {
        return idEgresso;
    }

    public void setIdEgresso(Long idEgresso) {
        this.idEgresso = idEgresso;
    }

    public Long getIdAcao() {
        return idAcao;
    }

    public void setIdAcao(Long idAcao) {
        this.idAcao = idAcao;
    }

    public Map<String, String> getTokensAdicionais() {
        return tokensAdicionais;
    }

    public void setTokensAdicionais(Map<String, String> tokensAdicionais) {
        this.tokensAdicionais = tokensAdicionais;
    }
    
    
}

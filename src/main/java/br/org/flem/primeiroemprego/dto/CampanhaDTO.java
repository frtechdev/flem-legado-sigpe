package br.org.flem.primeiroemprego.dto;

import br.org.flem.primeiroemprego.entity.AnexoEmail;
import br.org.flem.primeiroemprego.entity.StatusEnvioDaCampanha;
import java.util.Date;
import java.util.List;

/**
 * Descreva Sua Classe Classe CampanhaDTO
 *
 * @author <code>tscortes@flem.org.br</code> 18/04/2019
 * @version 1.0
 */
public class CampanhaDTO {

    private Long id;

    private String assunto;

    private String mensagem;

    private List<AnexoEmail> anexos;

    private StatusEnvioDaCampanha status;
    
    private Date dataCriacao;

    public CampanhaDTO(Long id, String assunto, String mensagem, List<AnexoEmail> anexos, StatusEnvioDaCampanha status) {
        this.id = id;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.anexos = anexos;
        this.status = status;
    }

    public CampanhaDTO(Long id, String assunto, String mensagem, StatusEnvioDaCampanha status, Date dataCriacao) {
        this.id = id;
        this.assunto = assunto;
        this.mensagem = mensagem;
        this.status = status;
        this.dataCriacao = dataCriacao;
    }

    public CampanhaDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<AnexoEmail> getAnexos() {
        return anexos;
    }

    public void setAnexos(List<AnexoEmail> anexos) {
        this.anexos = anexos;
    }

    public StatusEnvioDaCampanha getStatus() {
        return status;
    }

    public void setStatus(StatusEnvioDaCampanha status) {
        this.status = status;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    

}

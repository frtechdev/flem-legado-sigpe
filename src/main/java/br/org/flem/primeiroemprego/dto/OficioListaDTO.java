/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dto;

import java.util.Date;

/**
 *
 * @author tscortes
 */
public class OficioListaDTO {

    private Long idDocumento;
    private Long idEgresso;
    private String nomeEgresso;
    private String assunto;
    private Date dataCriacao;
    private Integer ano;
    private Long numero;
    private Long idModelo;
    private String nomeModelo;
    
    private String destinatarioExterno;

    public OficioListaDTO(Long idDocumento, Long idEgresso, String nomeEgresso, String assunto, Date dataCriacao, Integer ano, Long numero, Long idModelo, String nomeModelo, String destinatarioExterno) {
        this.idDocumento = idDocumento;
        this.idEgresso = idEgresso;
        this.nomeEgresso = nomeEgresso;
        this.assunto = assunto;
        this.dataCriacao = dataCriacao;
        this.ano = ano;
        this.numero = numero;
        this.idModelo = idModelo;
        this.nomeModelo = nomeModelo;
        this.destinatarioExterno = destinatarioExterno;
    }

    public OficioListaDTO() {
    }

    public Long getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(Long idDocumento) {
        this.idDocumento = idDocumento;
    }

    public Long getIdEgresso() {
        return idEgresso;
    }

    public void setIdEgresso(Long idEgresso) {
        this.idEgresso = idEgresso;
    }

    public String getNomeEgresso() {
        return nomeEgresso;
    }

    public void setNomeEgresso(String nomeEgresso) {
        this.nomeEgresso = nomeEgresso;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Long getNumero() {
        return numero;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public Long getIdModelo() {
        return idModelo;
    }

    public void setIdModelo(Long idModelo) {
        this.idModelo = idModelo;
    }

    public String getNomeModelo() {
        return nomeModelo;
    }

    public void setNomeModelo(String nomeModelo) {
        this.nomeModelo = nomeModelo;
    }

    public String getDestinatarioExterno() {
        return destinatarioExterno;
    }

    public void setDestinatarioExterno(String destinatarioExterno) {
        this.destinatarioExterno = destinatarioExterno;
    }
    
    
}

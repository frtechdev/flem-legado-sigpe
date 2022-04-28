
package br.org.flem.primeiroemprego.dto;

import java.util.Date;

/**
 * Classe OficioDTO
 * @author <code>tscortes@flem.org.br</code>
 * 18/04/2019
 * @version 1.0
 */
public class OficioDTO {
    
    private Long idDocumento;
    private Long idEgresso;
    private String nome;
    private byte[] arquivo;
    private String tipo;
    private Date dataCriacao;
    private Integer ano;
    private Long numero;
    private String filePath;

    public OficioDTO(Long idDocumento, Long idEgresso, String nome, byte[] arquivo, String tipo) {
        this.idDocumento = idDocumento;
        this.idEgresso = idEgresso;
        this.nome = nome;
        this.arquivo = arquivo;
        this.tipo = tipo;
    }
    
    public OficioDTO(Long idDocumento, Long idEgresso, String nome, String tipo, Date dataCriacao, Long numero, Integer ano) {
        this.idDocumento = idDocumento;
        this.idEgresso = idEgresso;
        this.nome = nome;
        this.tipo = tipo;
        this.dataCriacao = dataCriacao;
        this.numero = numero;
        this.ano = ano;
    }

    public OficioDTO() {
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
    
}

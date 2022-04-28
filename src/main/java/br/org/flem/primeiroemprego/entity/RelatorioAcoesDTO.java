package br.org.flem.primeiroemprego.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author emsilva
 */
public class RelatorioAcoesDTO implements Serializable{
    private Date data;
    private String tipo;
    private String descricao;
    private String usuario;
    private String egresso;
    private String cpf;

    public RelatorioAcoesDTO(Date data, String tipo, String descricao, String usuario, String egresso, String cpf) {
        this.data = data;
        this.tipo = tipo;
        this.descricao = descricao;
        this.usuario = usuario;
        this.egresso = egresso;
        this.cpf = cpf;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao.replaceAll("\\<.*?>","");
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEgresso() {
        return egresso;
    }

    public void setEgresso(String egresso) {
        this.egresso = egresso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }   
    

}

package br.org.flem.primeiroemprego.entity;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author emsilva
 */
public enum StatusEnvioDaCampanha {

    ERRO(0, "Erro"),
    SALVO(1, "Salvo"),
    ENVIANDO(2, "Transmitindo"),
    ENVIADO(3, "Enviado");

    private int id;
    private String descricao;
    
    private StatusEnvioDaCampanha(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public static List<StatusEnvioDaCampanha> getLista() {
        return Arrays.asList(StatusEnvioDaCampanha.values());
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
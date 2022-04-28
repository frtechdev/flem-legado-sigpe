package br.org.flem.primeiroemprego.entity;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tscortes
 */
public enum EnumSexo {

    M("M", "Masculino"),
    F("F", "Feminino");

    private String id;
    private String descricao;
    
    private EnumSexo(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public static List<EnumSexo> getLista() {
        return Arrays.asList(EnumSexo.values());
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
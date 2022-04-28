package br.org.flem.primeiroemprego.entity;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tscortes
 */
public enum EnumTipoInstituicaoEnsino {

    PRIVADA("PARTICULAR", "Particular"),
    PUBLICA("PUBLICA", "Pública");

    private String id;
    private String descricao;
    
    private EnumTipoInstituicaoEnsino(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }
    
    public static List<EnumTipoInstituicaoEnsino> getLista() {
        return Arrays.asList(EnumTipoInstituicaoEnsino.values());
    }
    
    public static EnumTipoInstituicaoEnsino getValue(String value){
        switch(value){
            case "Particular":
                return EnumTipoInstituicaoEnsino.PRIVADA;
            case "Pública":
                return EnumTipoInstituicaoEnsino.PUBLICA;
        }
        return EnumTipoInstituicaoEnsino.PUBLICA;
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
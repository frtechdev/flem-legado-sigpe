package br.org.flem.primeiroemprego.entity;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tscortes
 */
public enum EnumModalidadeEnsino {

    EAD("EAD", "EaD (Ensino a Distância)"),
    PRESENCIAL("PRESENCIAL", "Presencial"),
    SEMIPRESENCIAL("SEMIPRESENCIAL", "SEMI PRESENCIAL (EaD e Presencial)");

    private String id;
    private String descricao;

    private EnumModalidadeEnsino(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static EnumModalidadeEnsino getValue(String value) {
        switch (value) {
            case "EaD (Ensino a Distância)":
                return EnumModalidadeEnsino.EAD;
            case "Presencial":
                return EnumModalidadeEnsino.PRESENCIAL;
            case "PRESENCIAL":
                return EnumModalidadeEnsino.PRESENCIAL;
            case "SEMI PRESENCIAL (EaD e Presencial)":
                return EnumModalidadeEnsino.SEMIPRESENCIAL;
        }
        return EnumModalidadeEnsino.PRESENCIAL;
    }

    public static List<EnumModalidadeEnsino> getLista() {
        return Arrays.asList(EnumModalidadeEnsino.values());
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

package br.org.flem.primeiroemprego.entity;

import java.util.Arrays;
import java.util.List;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author tscortes
 */
public enum EnumSituacaoEvento {

    PRESENTE("P", "Presente"),
    AUSENTE("A", "Ausente");

    private String id;
    private String descricao;

    private EnumSituacaoEvento(String id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public static List<EnumSituacaoEvento> getLista() {
        return Arrays.asList(EnumSituacaoEvento.values());
    }

    public static EnumSituacaoEvento getById(String id) {
        if(StringUtils.isNotEmpty(id) && (id.equalsIgnoreCase("presente") || id.equalsIgnoreCase("p"))){
            return EnumSituacaoEvento.PRESENTE;
        }else if(StringUtils.isNotEmpty(id) && (id.equalsIgnoreCase("ausente") || id.equalsIgnoreCase("a"))){
            return EnumSituacaoEvento.AUSENTE;
        }
        return null;
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

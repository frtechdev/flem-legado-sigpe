package br.org.flem.primeiroemprego.entity;

/**
 *
 * @author tscortes
 */
public enum EnumTipoTelefone {

    FIXO,
    CELULAR;
    
    public static EnumTipoTelefone getByLength(int length){
        switch(length){
            case 10:
                return FIXO;
            case 11:
                return CELULAR;
            default:
                return FIXO;
        }
    }

}
package br.org.flem.primeiroemprego.entity;

/**
 *
 * @author emsilva
 */
public enum RacaCor {
    NAO_INFORMADA(0,"Não Informada"),
    BRANCA(1,"Branca"),
    INDIGENA(2,"Indígena"),
    //NEGRA(3,"Negra"),
    PRETA (3, "Preta"),
    PARDA(4,"Parda"),
    AMARELA(5,"Amarela");

    private int id;
    private String descricao;
    
    RacaCor(int id,String descricao){
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public static RacaCor obterPorDescricao(String descricao){
        for(RacaCor r: values()){
            if(r.getDescricao().equals(descricao)){
                return r;
            }
        }
        return null;
    }

}

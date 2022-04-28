package br.org.flem.primeiroemprego.entity;

import java.util.Arrays;
import java.util.List;

public enum Cargo{
    ADMINISTRATIVO(0, "Administrativo"),
    MONITOR(1, "Monitor");

    private int id;
    private String descricao;
    
    private Cargo(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    public int getId() {
        return id;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public static List<String> getListString(){
        Cargo[] cargos = values();
        String[] names = new String[cargos.length];

        for (int i = 0; i < cargos.length; i++) {
            names[i] = cargos[i].descricao;
        }
        return Arrays.asList(names);
    }
}

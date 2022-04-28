package br.org.flem.primeiroemprego.entity;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author emsilva
 */
public class RelatorioResumosDTO {
    private Map<String,RegistrosPorLote> registrosPorLote = new HashMap<String, RegistrosPorLote>();
    private Map<String,RegistrosPorCategoria> registrosPorCategoria = new HashMap<String, RegistrosPorCategoria>();
    private Integer egressosMasculino = 0;
    private Integer egressosFeminino = 0;
    private Integer egressosSexoNaoInformado = 0;

    public class RegistrosPorLote{
        private String lote;
        private String dataLote;
        private Integer total = 0;
        private Map<String,Integer> qtdPorDemandante = new HashMap<String, Integer>();

        public String getLote() {
            return lote;
        }

        public void setLote(String lote) {
            this.lote = lote;
        }

        public String getDataLote() {
            return dataLote;
        }

        public void setDataLote(String dataLote) {
            this.dataLote = dataLote;
        }

        public Integer getTotal() {
            return total;
        }

        public void setTotal(Integer total) {
            this.total = total;
        }

        public Map<String, Integer> getQtdPorDemandante() {
            return qtdPorDemandante;
        }

        public void setQtdPorDemandante(Map<String, Integer> qtdPorDemandante) {
            this.qtdPorDemandante = qtdPorDemandante;
        }
    }

    public class RegistrosPorCategoria{
        private String categoria;
        private Integer quantidade;
        private Map<String,Integer[]> situacoes;//nome da situação, qtd da Situação, qtdCapital, qtd Interior

        public String getCategoria() {
            return categoria;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public Integer getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(Integer quantidade) {
            this.quantidade = quantidade;
        }

        public Map<String, Integer[]> getSituacoes() {
            return situacoes;
        }

        public void setSituacoes(Map<String, Integer[]> situacoes) {
            this.situacoes = situacoes;
        }

    }

    public Map<String,RegistrosPorLote> getRegistrosPorLote() {
        return registrosPorLote;
    }

    public void setRegistrosPorLote(Map<String,RegistrosPorLote> registrosPorLote) {
        this.registrosPorLote = registrosPorLote;
    }

    public Integer getEgressosMasculino() {
        return egressosMasculino;
    }

    public void setEgressosMasculino(Integer egressosMasculino) {
        this.egressosMasculino = egressosMasculino;
    }

    public Integer getEgressosFeminino() {
        return egressosFeminino;
    }

    public void setEgressosFeminino(Integer egressosFeminino) {
        this.egressosFeminino = egressosFeminino;
    }

    public Integer getEgressosSexoNaoInformado() {
        return egressosSexoNaoInformado;
    }

    public void setEgressosSexoNaoInformado(Integer egressosSexoNaoInformado) {
        this.egressosSexoNaoInformado = egressosSexoNaoInformado;
    }

    public Map<String, RegistrosPorCategoria> getRegistrosPorCategoria() {
        return registrosPorCategoria;
    }

    public void setRegistrosPorCategoria(Map<String, RegistrosPorCategoria> registrosPorCategoria) {
        this.registrosPorCategoria = registrosPorCategoria;
    }

}

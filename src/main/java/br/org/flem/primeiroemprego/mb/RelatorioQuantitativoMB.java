package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.util.entity.CamposEgressosUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class RelatorioQuantitativoMB implements Serializable{

    private List<Egresso> egressos = new ArrayList<Egresso>();

    @ManagedProperty(value="#{egressoDAO}")
    private EgressoDAO egressoDAO;

    private String opcaoLinha;
    private Set<String> itensLinha = new HashSet<String>();
    private String opcaoColuna;
    private Set<String> itensColuna = new HashSet<String>();

    private Map<String,Map<String,Integer>> quantitativo = new HashMap<String,Map<String,Integer>>();
    
    
    @PostConstruct
    public void init(){
        egressos = egressoDAO.obterLista();
        
    }

    public List<String> camposPossiveis(){
        return CamposEgressosUtil.camposPossiveis();
    }

    public void calcular(){
        itensLinha.clear();
        itensColuna.clear();
        quantitativo.clear();

        if(opcaoLinha != null && opcaoColuna != null){
            for(Egresso c : egressos){
                itensLinha.add(CamposEgressosUtil.getInformacao(c, opcaoLinha));
                itensColuna.add(CamposEgressosUtil.getInformacao(c, opcaoColuna));
            }
            for(String itemLinha : itensLinha){
                for(String itemColuna : itensColuna){
                    if(!quantitativo.containsKey(itemLinha)){
                        quantitativo.put(itemLinha,new HashMap<String,Integer>());
                    }
                    if(!quantitativo.get(itemLinha).containsKey(itemColuna)){
                        quantitativo.get(itemLinha).put(itemColuna,0);;
                    }
                    for(Egresso egresso : egressos){
                        if(CamposEgressosUtil.igual(egresso,opcaoLinha,itemLinha) && CamposEgressosUtil.igual(egresso,opcaoColuna,itemColuna)){
                            quantitativo.get(itemLinha).put(itemColuna, quantitativo.get(itemLinha).get(itemColuna) +1); 
                        }
                    }
                }
            }
        }
    }

    public List<Egresso> getEgressos() {
        return egressos;
    }

    public void setEgressos(List<Egresso> egressos) {
        this.egressos = egressos;
    }

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public String getOpcaoLinha() {
        return opcaoLinha;
    }

    public void setOpcaoLinha(String opcaoLinha) {
        this.opcaoLinha = opcaoLinha;
    }

    public String getOpcaoColuna() {
        return opcaoColuna;
    }

    public void setOpcaoColuna(String opcaoColuna) {
        this.opcaoColuna = opcaoColuna;
    }

    public Map<String, Map<String, Integer>> getQuantitativo() {
        return quantitativo;
    }

    public void setQuantitativo(Map<String, Map<String, Integer>> quantitativo) {
        this.quantitativo = quantitativo;
    }

    public Set<String> getItensLinha() {
        return itensLinha;
    }

    public void setItensLinha(Set<String> itensLinha) {
        this.itensLinha = itensLinha;
    }

    public Set<String> getItensColuna() {
        return itensColuna;
    }

    public void setItensColuna(Set<String> itensColuna) {
        this.itensColuna = itensColuna;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.MaterialDAO;
import br.org.flem.primeiroemprego.dao.TamanhoDAO;
import br.org.flem.primeiroemprego.entity.Tamanho;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author AJLima
 */
@ManagedBean
@ViewScoped

public class TamanhoCadastroMB implements Serializable{
    
    @ManagedProperty(value = "#{tamanhoDAO}")
    private TamanhoDAO tamanhoDAO;
    
    @ManagedProperty(value = "#{materialDAO}")
    private MaterialDAO materialDAO;
    
    private Tamanho tamanho;
//    private List<Byte> ordens = new ArrayList<Byte>();
//    private List<Tamanho> tamanhos = new ArrayList<>();

    @PostConstruct
    public void init() {
        String id = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (id != null) {
            tamanho = tamanhoDAO.obterPorPK(Long.parseLong(id));
        } else {
            tamanho = new Tamanho();
        }
//        obterOrdens();
    }
    
     public String salvar() {
        String retorno = "";
        if(!isOrdemIgual()){
            if (tamanho.getId() != null) {
                try{
                    tamanhoDAO.alterar(tamanho);
                    Mensagem.lancarMensagemInfo("Tamanho alterado com sucesso");
                    retorno = "lista.xhtml";
                }catch(Exception e){
                    Mensagem.lancarMensagemErro("Erro ao alterar o tamanho");
                }
            } else {
                try{
                    tamanhoDAO.inserir(tamanho);
                    Mensagem.lancarMensagemInfo("Tamanho inserido com sucesso");
                    retorno = "lista.xhtml";
                }catch(Exception e){
                    Mensagem.lancarMensagemErro("Erro ao inserir o tamanho");
                }
            }
        }else{
            Mensagem.lancarMensagemErro("Ordem Informada em outro tamanho");
        }

        return retorno;
    }
     
    private boolean isOrdemIgual(){
        boolean existe = false;
        List<Tamanho> tamanhos = tamanhoDAO.obterLista();
        if( tamanhos != null && !tamanhos.isEmpty() ){
            for(Tamanho tam : tamanhos){
                if( tamanho.getOrdem() == null ){
                    break;
                }
                if( tam.getOrdem() != null && tamanho.getOrdem() != null && tamanho.getOrdem().equals(tam.getOrdem()) && 
                        (tamanho.getId() != null && !tamanho.getId().equals(tam.getId()))){
                    existe = true;
                    break;
                }
            }
        }
        return existe;
    }

    public TamanhoDAO getTamanhoDAO() {
        return tamanhoDAO;
    }

    public void setTamanhoDAO(TamanhoDAO tamanhoDAO) {
        this.tamanhoDAO = tamanhoDAO;
    }

    public Tamanho getTamanho() {
        return tamanho;
    }

    public void setTamanho(Tamanho tamanho) {
        this.tamanho = tamanho;
    }

    public MaterialDAO getMaterialDAO() {
        return materialDAO;
    }

    public void setMaterialDAO(MaterialDAO materialDAO) {
        this.materialDAO = materialDAO;
    }
//
//    public List<Byte> getOrdens() {
//        return ordens;
//    }
//
//    public void setOrdens(List<Byte> ordens) {
//        this.ordens = ordens;
//    }
    
    
}

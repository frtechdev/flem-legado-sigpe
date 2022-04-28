package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ParametroDAO;
import br.org.flem.primeiroemprego.entity.Parametro;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.util.logging.Logger;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
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
public class ParametroValoresMB implements Serializable{
    
    @ManagedProperty(value = "#{parametroDAO}")
    private ParametroDAO parametroDAO;
    
    private List<Parametro> parametros;
    
    @PostConstruct
    public void init(){
        parametros = parametroDAO.obterLista();
    }
    
    public void salvar(){
        try{
            parametroDAO.salvar(parametros);
            Mensagem.lancar("Parâmetros atualizados");
        }catch(Exception e){
            Mensagem.lancar("Erro ao atualizar os parâmetros");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage(),e);
        }
    }

    public ParametroDAO getParametroDAO() {
        return parametroDAO;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    public List<Parametro> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametro> parametros) {
        this.parametros = parametros;
    }

}

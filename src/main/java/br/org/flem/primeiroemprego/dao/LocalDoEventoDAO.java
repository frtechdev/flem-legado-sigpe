package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.LocalDoEvento;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class LocalDoEventoDAO extends GenericDAO<LocalDoEvento, Long> {
    
    public LocalDoEventoDAO() throws Exception{
        super(LocalDoEvento.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

}

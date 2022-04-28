package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.PontoFocal;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class PontoFocalDAO extends GenericDAO<PontoFocal, Long> {
    public PontoFocalDAO() throws Exception{
        super(PontoFocal.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

}

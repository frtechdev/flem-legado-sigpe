package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.EixoDeFormacao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class EixoDeFormacaoDAO extends  GenericDAO<EixoDeFormacao, Long> {
    
    public EixoDeFormacaoDAO() throws Exception{
        super(EixoDeFormacao.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

}

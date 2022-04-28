package br.org.flem.primeiroemprego.dao;


import br.org.flem.primeiroemprego.entity.ContaDeEmail;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.Query;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class ContaDeEmailDAO extends GenericDAO<ContaDeEmail, Long>{
    
    public ContaDeEmailDAO(){
        super(ContaDeEmail.class);
        this.nomeColunaParaOdemSimples = "dataCriacao";
    }

    public ContaDeEmail obterContaDeEmailPrincipal() {
        Query query = getEntityManager().createNativeQuery("SELECT c FROM ContaDeEmail c WHERE principal = 1");
        query.setMaxResults(1);
        return (ContaDeEmail)query.getSingleResult();
    }

    public void tornarPrincipal(ContaDeEmail contaDeEmail) throws Exception{
        Query query = getEntityManager().createNativeQuery("UPDATE ContaDeEmail m SET principal = 0");//Torna todos "não principal" para depois alterar o principal
        query.executeUpdate();
        contaDeEmail.setPrincipal(true);
        alterar(contaDeEmail);
    }

}
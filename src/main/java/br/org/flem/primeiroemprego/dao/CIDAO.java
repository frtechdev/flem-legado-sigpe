package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.CI;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CIDAO extends GenericDAO<CI, Long>{
    public static final Integer LOCK = 1;

    @ManagedProperty(value = "#{parametroDAO}")
    private ParametroDAO parametroDAO;

    public CIDAO(){
        super(CI.class);
        this.nomeColunaParaOdemSimples = "numero";
        this.ordemPadraoAscendente = false;
    }

    @Override
    public CI inserir(CI ci) throws Exception {
        if(ci.getNumero() == null || ci.getAno() == null){
            throw new Exception("O Número e ano da CI devem ser gerados antes do insert, em um bloco com acesso exclusivo (synchronized)");
        }
        if(ci.getNome() == null){
            ci.setNome("CI "+ci.getIdentificacao()+".pdf");
        }
        return super.inserir(ci);
    }

    public void atribuirNumeroDaCI(CI ci){
        Integer anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        if(parametroDAO == null){//Situação em que o CIDAO é instanciado "na mão" na thread
            parametroDAO = new ParametroDAO();
        }
        ci.setAno(anoAtual);
        try{
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
            Root<CI> root = criteria.from(CI.class);
            criteria.select(root.<Long>get(("numero")));
            criteria.where(cb.equal(root.get("ano"), anoAtual));
            criteria.orderBy(cb.desc(root.get("id")));
            ci.setNumero(getEntityManager().createQuery(criteria).setMaxResults(1).getSingleResult() + 1);
        }catch(Exception e){
            if(anoAtual > 2017){
                ci.setNumero(1l);
            }else{
                ci.setNumero(Long.parseLong(parametroDAO.obterValor("numeroInicialCi")) + 1);
            }
        }
    }

    public List<CI> obterNaoFechadas(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CI> query = cb.createQuery(CI.class);
        Root<CI> root = query.from(CI.class);
        query.where(cb.equal(root.get("fechada"), false));
        return getEntityManager().createQuery(query).getResultList();
    }

    public ParametroDAO getParametroDAO() {
        return parametroDAO;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

}

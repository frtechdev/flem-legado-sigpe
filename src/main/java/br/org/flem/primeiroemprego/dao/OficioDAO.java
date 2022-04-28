package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.dto.OficioListaDTO;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Oficio;
import java.util.Calendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class OficioDAO extends DocumentoDAO<Oficio> {

    public static final Integer LOCK = 1;

    @ManagedProperty(value = "#{parametroDAO}")
    private ParametroDAO parametroDAO;

    public OficioDAO() {
        super(Oficio.class);
        this.nomeColunaParaOdemSimples = "numero";
        this.ordemPadraoAscendente = false;
    }

    @Override
    public Oficio inserir(Oficio oficio) throws Exception {
        if (oficio.getNumero() == null || oficio.getAno() == null) {
            throw new Exception("O Número e ano do ofício devem ser gerados antes do insert, em um bloco com acesso exclusivo (synchronized)");
        }
        if (oficio.getNome() == null) {
            oficio.setNome("Ofício " + oficio.getIdentificacao() + ".pdf");
        }
        return super.inserir(oficio);
    }

    public void atribuirNumeroDoOficio(Oficio oficio) {
        Integer anoAtual = Calendar.getInstance().get(Calendar.YEAR);
        if (parametroDAO == null) {//Situação em que o OficioDAO é instanciado "na mão" na thread
            parametroDAO = new ParametroDAO();
        }
        oficio.setAno(anoAtual);
        try {
            CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
            CriteriaQuery<Long> criteria = cb.createQuery(Long.class);
            Root<Oficio> root = criteria.from(Oficio.class);
            criteria.select(root.<Long>get(("numero")));
            criteria.where(cb.equal(root.get("ano"), anoAtual));
            criteria.orderBy(cb.desc(root.get("id")));
            oficio.setNumero(getEntityManager().createQuery(criteria).setMaxResults(1).getSingleResult() + 1);
        } catch (Exception e) {
            if (anoAtual > 2018) {
                oficio.setNumero(1l);
            } else {
                oficio.setNumero(Long.parseLong(parametroDAO.obterValor("numeroInicialOficio")) + 1);
            }
        }
    }
    
    public List<Oficio> obterPorEgresso(Egresso egresso){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Oficio> criteria = cb.createQuery(Oficio.class);
        Root<Oficio> root = criteria.from(Oficio.class);
        criteria.where(cb.equal(root.get("egresso"), egresso));
        criteria.orderBy(cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    public ParametroDAO getParametroDAO() {
        return parametroDAO;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

    public List<Oficio> obterPorModeloDeOficio(ModeloDeOficio modeloDeOficio) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Oficio> criteria = cb.createQuery(Oficio.class);
        Root<Oficio> root = criteria.from(Oficio.class);
        criteria.where(cb.equal(root.get("modelo"), modeloDeOficio));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    public Oficio obterPorModeloDeOficio(ModeloDeOficio modeloDeOficio, Egresso egresso) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Oficio> criteria = cb.createQuery(Oficio.class);
        Root<Oficio> root = criteria.from(Oficio.class);
//        criteria.where(cb.equal(root.get("modelo"), modeloDeOficio));
        criteria.where(cb.equal(root.get("modelo"), modeloDeOficio), cb.equal(root.get("egresso"), egresso));
        List<Oficio> lista = getEntityManager().createQuery(criteria).getResultList();
        if( lista != null && !lista.isEmpty() ){
            return lista.get(0);
        }
        return null;
    }
    
    public List<OficioListaDTO> obterTodosOficios(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<OficioListaDTO> criteria = cb.createQuery(OficioListaDTO.class);
        Root<Oficio> root = criteria.from(Oficio.class);
        
        Join JoinEgresso = root.join("egresso", JoinType.LEFT);
        Join JoinModelo = root.join("modelo");
        
        criteria.select(cb.construct(OficioListaDTO.class,
                root.get("id"),
                JoinEgresso.get("id"),
                JoinEgresso.get("nome"),
                root.get("assunto"),
                root.get("dataCriacao"),
                root.get("ano"),
                root.get("numero"),
                JoinModelo.get("id"),
                JoinModelo.get("assunto"),
                root.get("destinatarioExterno")
        ));
        criteria.orderBy(cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }
    
    @Override
    public List<Oficio> findAll() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Oficio> query = cb.createQuery(Oficio.class);
        Root<Oficio> root = query.from(Oficio.class);
        
        query.select(cb.construct(Oficio.class,
                root.get("id"),
                root.get("nome")
        ));
        query.where(cb.isNull(root.get("filePath")));
        return getEntityManager().createQuery(query).getResultList();
    }

}

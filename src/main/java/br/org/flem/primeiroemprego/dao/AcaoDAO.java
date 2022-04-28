package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.RelatorioAcoesDTO;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class AcaoDAO extends GenericDAO<Acao, Long> {

    public AcaoDAO(){
        super(Acao.class);
        this.nomeColunaParaOdemSimples = "dataCriacao";
    }
    
    public List<RelatorioAcoesDTO> obterRelatorioAcoesDTOs(){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<RelatorioAcoesDTO> query = cb.createQuery(RelatorioAcoesDTO.class);
        Root<Acao> root = query.from(Acao.class);
        query.select(cb.construct(RelatorioAcoesDTO.class, 
                root.get("data"),
                root.get("tipoDeAcao").get("nome"),
                root.get("descricao"),
                root.get("usuarioCriacao"),
                root.get("egresso").get("nome"),
                root.get("egresso").get("cpf")
        ));
        query.orderBy(cb.desc(root.get("data")));
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<Acao> obterAcoesPorEgresso(Egresso egresso){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Acao> query = cb.createQuery(Acao.class);
        Root<Acao> root = query.from(Acao.class);
        query.where(cb.equal(root.get("egresso"), egresso));
        
        query.orderBy(cb.desc(root.get("data")));
        
        return getEntityManager().createQuery(query).getResultList();
    }
    
    public List<RelatorioAcoesDTO> obterListaImpressao(Map<String, Object> filtro) throws ParseException{
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<RelatorioAcoesDTO> query = cb.createQuery(RelatorioAcoesDTO.class);
        Root<Acao> root = query.from(Acao.class);
        query.select(cb.construct(RelatorioAcoesDTO.class, 
                root.get("data"),
                root.get("tipoDeAcao").get("nome"),
                root.get("descricao"),
                root.get("usuarioCriacao"),
                root.get("egresso").get("nome"),
                root.get("egresso").get("cpf")
        ));
        
        filtroPaginada(filtro, root, cb, query);
        
        List<Order> orderList = new ArrayList();
        
        orderList.add(cb.asc(root.get("tipoDeAcao").get("nome")));
        orderList.add(cb.desc(root.get("data")));
        
        query.orderBy(orderList);
        
        TypedQuery<RelatorioAcoesDTO> tq = getEntityManager().createQuery(query);
       
        return tq.getResultList();
    }
    private void filtroPaginada(Map<String, Object> filtro, Root<Acao> root, CriteriaBuilder cb, CriteriaQuery<?> query) throws ParseException{
        if (filtro.size() > 0){
            List<Predicate> predicates = new ArrayList<Predicate>();
            
            for ( String key : filtro.keySet() ){
                Predicate restNome = null;
                if(key.equalsIgnoreCase("tipo") || key.equalsIgnoreCase("data") || key.equalsIgnoreCase("cpf")|| key.equalsIgnoreCase("usuario")){
                    if(key.equalsIgnoreCase("tipo")){
                        restNome = cb.equal(root.get("tipoDeAcao").get("nome"), filtro.get(key));
                    }else if(key.equalsIgnoreCase("cpf")){
                        restNome = cb.equal(root.get("egresso").get("cpf"), filtro.get(key));
                    }else if(key.equalsIgnoreCase("usuario")){
                        restNome = cb.equal(root.get("usuarioCriacao"), filtro.get(key));
                    }else{
                        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                        
                        Date dataIni = (Date)formatter.parse(filtro.get(key).toString()+" 00:00");
                        Date dataFim = (Date)formatter.parse(filtro.get(key).toString()+" 23:59");
                        restNome = cb.between(root.get(key), dataIni, dataFim);
                    }
                }else{
                    String like = (String) filtro.get(key);
                    if( key.equalsIgnoreCase("egresso") ){
                        restNome = cb.like(root.get("egresso").get("nome"), "%"+like.trim()+"%");
                    }else{
                        restNome = cb.like(root.get(key), "%"+like.trim()+"%");
                    }
                }
                predicates.add(restNome);
            }
            query.where(predicates.toArray(new Predicate[]{}));
        }
    }
    
    public List<Acao> obterAcoesPorDescricao(String descricao){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Acao> query = cb.createQuery(Acao.class);
        Root<Acao> root = query.from(Acao.class);
        query.where(cb.like(root.get("descricao"), "%"+descricao.trim()+"%"));
        
        return getEntityManager().createQuery(query).getResultList();
    }

}

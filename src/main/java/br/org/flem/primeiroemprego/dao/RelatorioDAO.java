package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.RelatorioResumosDTO;
import br.org.flem.primeiroemprego.util.CommonDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.Query;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class RelatorioDAO extends CommonDAO {
    
    @ManagedProperty(value = "#{egressoDAO}")
    private EgressoDAO egressoDAO;
    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;

    public RelatorioResumosDTO obterRelatorioDeResumo(){
        RelatorioResumosDTO relatorioResumos = new RelatorioResumosDTO();
        preencherRegistrosPorLote(relatorioResumos);
        preencherPorSexo(relatorioResumos);
        preencherPorCategoria(relatorioResumos);
        return relatorioResumos;
    }

    private void preencherRegistrosPorLote(RelatorioResumosDTO relatorioResumosDTO){
        List<String> siglas = demandanteDAO.siglasDemandantes();
        String query = "SELECT dataRemessaSETRESINE,remessaSETRESINE,d.sigla,COUNT(*) FROM Vaga v "+
                       "LEFT JOIN Demandante d ON d.id_demandante = v.id_demandante GROUP BY dataRemessaSETRESINE,remessaSETRESINE,d.sigla "+
                       "ORDER BY CONVERT(DATE,v.dataRemessaSETRESINE,103),v.remessaSETRESINE,d.sigla";
        Query rs = getEntityManager().createNativeQuery(query);
        List<Object[]> dados = rs.getResultList();
        String lote;
        for(Object[] linha : dados){
            lote = (String)linha[1];
            RelatorioResumosDTO.RegistrosPorLote registrosPorLote = relatorioResumosDTO.getRegistrosPorLote().get(lote);//Obtendo o registro do lote
            if(registrosPorLote == null){//Caso não existe é necessário inicializar 
                registrosPorLote = relatorioResumosDTO.new RegistrosPorLote();
                registrosPorLote.setLote(lote);
                relatorioResumosDTO.getRegistrosPorLote().put(lote,registrosPorLote);
                registrosPorLote.setDataLote((String)linha[0]);
                for(String sigla : siglas){//Inicializa os valores por demandante
                    registrosPorLote.getQtdPorDemandante().put(sigla, 0);
                }
            }
            registrosPorLote.getQtdPorDemandante().put((String)linha[2], (Integer)linha[3]);
            registrosPorLote.setTotal(registrosPorLote.getTotal()+((Integer)linha[3]));
        }
    }

    private void preencherPorSexo(RelatorioResumosDTO relatorioResumos) {
        String sql = "SELECT sexo,COUNT(*) FROM Egresso GROUP BY sexo";
        Query rs = getEntityManager().createNativeQuery(sql);
        List<Object[]> dados = rs.getResultList();
        for(Object[] linha : dados){
            if(linha[0].toString().equals("F")){
                relatorioResumos.setEgressosFeminino((Integer)linha[1]);
            }else if (linha[0].toString().equals("M")){
                relatorioResumos.setEgressosMasculino((Integer)linha[1]);
            }else{
                relatorioResumos.setEgressosSexoNaoInformado((Integer)linha[1]);
            }
        }
    }
    
    private void preencherPorCategoria(RelatorioResumosDTO relatorioResumos) {
        String sql = "SELECT c.nome categoria,s.nome situacao,(SELECT COUNT(*) FROM Vaga vC WHERE vC.id_municipioDaVaga =336 AND vC.id_situacao = s.id_situacao) qtdCapital, " +
                    "(SELECT COUNT(*) FROM Vaga vI WHERE vI.id_municipioDaVaga !=336 AND vI.id_situacao = s.id_situacao) qtdInterior " +
                    "FROM CategoriaDaSituacao c INNER JOIN Situacao s ON s.id_categoria = c.id_categoriaDaSituacao ORDER BY c.nome,s.nome";
        Query rs = getEntityManager().createNativeQuery(sql);
        List<Object[]> dados = rs.getResultList();
        Integer qtdSituacaoCapital;
        Integer qtdSituacaoInterior;
        String categoria;
        String situacao;
        for(Object[] linha : dados){
            categoria = linha[0].toString();
            situacao = linha[1].toString();
            qtdSituacaoCapital = (Integer)linha[2];
            qtdSituacaoInterior = (Integer)linha[3];
            RelatorioResumosDTO.RegistrosPorCategoria registrosPorCategoria = relatorioResumos.getRegistrosPorCategoria().get(categoria);
            if(registrosPorCategoria == null){//Caso não existe o registro da Categoria é necessário inicializar 
                registrosPorCategoria = relatorioResumos.new RegistrosPorCategoria();
                registrosPorCategoria.setCategoria(categoria);
                relatorioResumos.getRegistrosPorCategoria().put(categoria,registrosPorCategoria);
            }
            registrosPorCategoria.getSituacoes().put(situacao, new Integer[]{qtdSituacaoCapital + qtdSituacaoInterior,qtdSituacaoCapital,qtdSituacaoInterior});
            registrosPorCategoria.setQuantidade(registrosPorCategoria.getQuantidade()+qtdSituacaoCapital + qtdSituacaoInterior);
        }
    }
    
    /*
    public RelatorioResumosDTO obterRelatorioDeResumo(){
        RelatorioResumosDTO relatorioResumos = new RelatorioResumosDTO();
        
        List<Egresso> egressos =  egressoDAO.obterLista();
        for(Egresso e : egressos){
            if(e.getSexo().toUpperCase().equals("F")){
                relatorioResumos.setEgressosFeminino(relatorioResumos.getEgressosFeminino()+1);
            }else if(e.getSexo().toUpperCase().equals("M")){
                relatorioResumos.setEgressosFeminino(relatorioResumos.getEgressosFeminino()+1);
            }else{
                relatorioResumos.setEgressosSexoNaoInformado(relatorioResumos.getEgressosSexoNaoInformado()+1);
            }
            RelatorioResumosDTO.RegistrosPorLote rL;
            if(relatorioResumos.getRegistrosPorLote().containsKey(e.getVaga().getRemessaSETRESINE())){
                rL =  relatorioResumos.new RegistrosPorLote();
                rL.setDataLote(e.getVaga().getDataRemessaSETRESINE());
                rL.setQtdPorDemandante(new HashMap<String,Integer>());
                relatorioResumos.getRegistrosPorLote().put(e.getVaga().getRemessaSETRESINE(), rL);
            }else{
                rL = relatorioResumos.getRegistrosPorLote().get(e.getVaga().getRemessaSETRESINE());
            }
            rL.setTotal(rL.getTotal()+1);
            if(!rL.getQtdPorDemandante().containsKey(e.getVaga().getDemandante().getSigla())){
                rL.getQtdPorDemandante().put(e.getVaga().getDemandante().getSigla(),0);
            }
            rL.getQtdPorDemandante().put(e.getVaga().getDemandante().getSigla(),rL.getQtdPorDemandante().get(e.getVaga().getDemandante().getSigla()+1));
            
            
        }
        return relatorioResumos;
    }*/

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
    }

}

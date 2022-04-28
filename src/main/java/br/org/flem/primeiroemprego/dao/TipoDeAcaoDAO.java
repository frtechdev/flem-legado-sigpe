package br.org.flem.primeiroemprego.dao;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author MPPassos
 */
@ManagedBean
@ViewScoped
public class TipoDeAcaoDAO extends GenericDAO<TipoDeAcao, Long> {
    
    public TipoDeAcaoDAO(){
        super(TipoDeAcao.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

    public TipoDeAcao tipoEmail(){
        return obterPorPK(Long.parseLong(PropertiesUtil.getProperty(("idTipoDeAcaoEmail"))));
    }
    
    public TipoDeAcao ativarRegra(){
        return obterPorPK(Long.parseLong(PropertiesUtil.getProperty(("idRegraAtivada"))));
    }
    
    public TipoDeAcao desativarregra(){
        return obterPorPK(Long.parseLong(PropertiesUtil.getProperty(("idRegraDesativada"))));
    }
    
    public TipoDeAcao obterPorNome(String nome){
        if(StringUtils.isNotEmpty(nome)){
            
            try{
                String str = "from TipoDeAcao t where t.nome = :nome";
     
                Query query = getEntityManager().createQuery(str);

                query.setParameter("nome", nome);

                return (TipoDeAcao) query.getSingleResult(); 
                
            }catch (NoResultException nre){
                return null;
            }
        }
        return null;
    }

}

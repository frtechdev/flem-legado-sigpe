package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.RelatorioDAO;
import br.org.flem.primeiroemprego.entity.RelatorioResumosDTO;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author emsilva
 */
@ManagedBean
public class RelatorioResumosMB implements Serializable{

    private RelatorioResumosDTO relatorioResumos = new RelatorioResumosDTO();

    @ManagedProperty(value = "#{relatorioDAO}")
    private RelatorioDAO relatorioDAO;
    
    @PostConstruct
    public void init(){
        relatorioResumos = relatorioDAO.obterRelatorioDeResumo();
    }

    public RelatorioResumosDTO getRelatorioResumos() {
        return relatorioResumos;
    }

    public void setRelatorioResumos(RelatorioResumosDTO relatorioResumos) {
        this.relatorioResumos = relatorioResumos;
    }

    public RelatorioDAO getRelatorioDAO() {
        return relatorioDAO;
    }

    public void setRelatorioDAO(RelatorioDAO relatorioDAO) {
        this.relatorioDAO = relatorioDAO;
    }

}

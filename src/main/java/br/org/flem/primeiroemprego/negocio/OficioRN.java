package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.AcaoDAO;
import br.org.flem.primeiroemprego.dao.OficioDAO;
import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Oficio;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class OficioRN extends DocumentoRN<Oficio>{
    
    @ManagedProperty(value = "#{oficioDAO}")
    private OficioDAO oficioDAO;
    @ManagedProperty(value = "#{acaoDAO}")
    private AcaoDAO acaoDAO;
    
    public List<Oficio> obterPorModeloDeOficio(ModeloDeOficio modeloDeOficio) {
        return oficioDAO.obterPorModeloDeOficio(modeloDeOficio);
    }
    
    public List<Oficio> obterPorEgresso(Egresso egresso) {
        return oficioDAO.obterPorEgresso(egresso);
    }
    
    public boolean excluirArquivo(String filepath){
        return delete(filepath);
    }
    
    public void excluir(Oficio oficio) throws Exception{
        if( excluirArquivo(oficio.getFilePath())){
            excluirAcoesDoOficio(oficio);
            oficioDAO.excluir(oficio);
        }
    }
    
    public void excluir(Long id) throws Exception{
        Oficio oficio = oficioDAO.obterPorPK(id);
        if( oficio != null ){
            excluir(oficio);
        }
    }
    
    private void excluirAcoesDoOficio(Oficio oficio){
        List<Acao> acoes = acaoDAO.obterAcoesPorDescricao(oficio.getIdentificacao() + " - " + oficio.getAssunto());
        if( !acoes.isEmpty() ){
            for(Acao acao : acoes){
                try {
                    acaoDAO.excluir(acao);
                } catch (Exception ex) {
                    Logger.getLogger(ModeloDeOficioRN.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public OficioDAO getOficioDAO() {
        return oficioDAO;
    }

    public void setOficioDAO(OficioDAO oficioDAO) {
        this.oficioDAO = oficioDAO;
    }

    public AcaoDAO getAcaoDAO() {
        return acaoDAO;
    }

    public void setAcaoDAO(AcaoDAO acaoDAO) {
        this.acaoDAO = acaoDAO;
    }
    
}

package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.TipoAssistenciaSocialDAO;
import br.org.flem.primeiroemprego.entity.TipoAssistenciaSocial;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author MPPassos
 */
@ManagedBean
@ViewScoped
public class TipoAssistenciaSocialListaMB implements Serializable {

    @ManagedProperty(value = "#{tipoAssistenciaSocialDAO}")
    private TipoAssistenciaSocialDAO tipoAssistenciaSocialDAO;
    private TipoAssistenciaSocial tipoAssistenciaSocial;

    public String excluir() {
        try {
            tipoAssistenciaSocialDAO.excluir(tipoAssistenciaSocial);
            Mensagem.lancar("Dados excluídos com sucesso");
        } catch (Exception e) {
            Mensagem.lancar("Erro ao excluir os Dados");
        }
        return "lista.xhtml";
    }

    public List<TipoAssistenciaSocial> listas() {
        return tipoAssistenciaSocialDAO.obterLista();
    }

    public TipoAssistenciaSocialDAO getTipoAssistenciaSocialDAO() {
        return tipoAssistenciaSocialDAO;
    }

    public void setTipoAssistenciaSocialDAO(TipoAssistenciaSocialDAO tipoAssistenciaSocialDAO) {
        this.tipoAssistenciaSocialDAO = tipoAssistenciaSocialDAO;
    }

    public TipoAssistenciaSocial getTipoAssistenciaSocial() {
        return tipoAssistenciaSocial;
    }

    public void setTipoAssistenciaSocial(TipoAssistenciaSocial tipoAssistenciaSocial) {
        this.tipoAssistenciaSocial = tipoAssistenciaSocial;
    }

}

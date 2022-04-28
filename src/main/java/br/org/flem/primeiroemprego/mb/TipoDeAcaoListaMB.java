package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.TipoDeAcaoDAO;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
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
public class TipoDeAcaoListaMB implements Serializable {

    @ManagedProperty(value = "#{tipoDeAcaoDAO}")
    private TipoDeAcaoDAO tipoDeAcaoDAO;
    private TipoDeAcao tipoDeacao;

    public String excluir() {
        try {
            tipoDeAcaoDAO.excluir(tipoDeacao);
            Mensagem.lancar("Dados excluídos com sucesso");
        } catch (Exception e) {
            Mensagem.lancar("Erro ao excluir os Dados");
        }
        return "lista.xhtml";
    }

    public List<TipoDeAcao> listas() {
        return tipoDeAcaoDAO.obterLista();
    }

    public TipoDeAcaoDAO getTipoDeAcaoDAO() {
        return tipoDeAcaoDAO;
    }

    public void setTipoDeAcaoDAO(TipoDeAcaoDAO tipoDeAcaoDAO) {
        this.tipoDeAcaoDAO = tipoDeAcaoDAO;
    }

    public TipoDeAcao getTipoDeacao() {
        return tipoDeacao;
    }

    public void setTipoDeacao(TipoDeAcao tipoDeacao) {
        this.tipoDeacao = tipoDeacao;
    }

}

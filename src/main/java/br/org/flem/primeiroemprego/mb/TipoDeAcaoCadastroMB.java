package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.TipoDeAcaoDAO;
import br.org.flem.primeiroemprego.entity.TipoDeAcao;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author MPPassos
 */
@ManagedBean
@ViewScoped
public class TipoDeAcaoCadastroMB implements Serializable {

    @ManagedProperty(value = "#{tipoDeAcaoDAO}")
    private TipoDeAcaoDAO tipoDeAcaoDAO;
    private TipoDeAcao tipoDeacao;

    @PostConstruct
    public void init() {
        String idTipoDeAcao = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (idTipoDeAcao != null) {
            tipoDeacao = tipoDeAcaoDAO.obterPorPK(Long.parseLong(idTipoDeAcao));
        } else {
            tipoDeacao = new TipoDeAcao();
        }
    }

    public String salvar() {
        if (tipoDeacao.getId() != null) {
            try {
                tipoDeAcaoDAO.alterar(tipoDeacao);
                Mensagem.lancar("Dados alterados com sucesso");
            } catch (Exception e) {
                Mensagem.lancar("Erro ao alterar os Dados");
            }
        } else {
            try {
                tipoDeAcaoDAO.inserir(tipoDeacao);
                Mensagem.lancar("Dados inseridos com sucesso");
            } catch (Exception e) {
                Mensagem.lancar("Erro ao inserir os Dados");
            }
        }

        return "lista.xhtml";
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

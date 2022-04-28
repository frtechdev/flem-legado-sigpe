package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.TipoAssistenciaSocialDAO;
import br.org.flem.primeiroemprego.entity.TipoAssistenciaSocial;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
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
public class TipoAssistenciaSocialCadastroMB implements Serializable {

    @ManagedProperty(value = "#{tipoAssistenciaSocialDAO}")
    private TipoAssistenciaSocialDAO tipoAssistenciaSocialDAO;
    private TipoAssistenciaSocial tipoAssistenciaSocial;

    @PostConstruct
    public void init() {
        String idTipoAssistenciaSocial = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if (idTipoAssistenciaSocial != null) {
            tipoAssistenciaSocial = tipoAssistenciaSocialDAO.obterPorPK(Long.parseLong(idTipoAssistenciaSocial));
        } else {
            tipoAssistenciaSocial = new TipoAssistenciaSocial();
        }
    }

    public String salvar() {
        if (tipoAssistenciaSocial.getId() != null) {
            try {
                tipoAssistenciaSocialDAO.alterar(tipoAssistenciaSocial);
                Mensagem.lancar("Dados alterados com sucesso");
            } catch (Exception e) {
                Mensagem.lancar("Erro ao alterar os Dados");
            }
        } else {
            try {
                tipoAssistenciaSocialDAO.inserir(tipoAssistenciaSocial);
                Mensagem.lancar("Dados inseridos com sucesso");
            } catch (Exception e) {
                Mensagem.lancar("Erro ao inserir os Dados");
            }
        }

        return "lista.xhtml";
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

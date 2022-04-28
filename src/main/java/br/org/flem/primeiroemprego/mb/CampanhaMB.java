package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CampanhaDAO;
import br.org.flem.primeiroemprego.dao.ContaDeEmailDAO;
import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.entity.AnexoEmail;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.ContaDeEmail;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.StatusEnvioDaCampanha;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.email.EnviadorEmail;
import br.org.flem.primeiroemprego.util.entity.CamposEgressosUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CampanhaMB implements Serializable {

    private EnviadorEmail enviadorEmail = new EnviadorEmail();

    @ManagedProperty(value = "#{campanhaDAO}")
    private CampanhaDAO campanhaDAO;

    @ManagedProperty(value = "#{contaDeEmailDAO}")
    private ContaDeEmailDAO contaDeEmailDAO;

    @ManagedProperty(value = "#{modeloDeOficioDAO}")
    private ModeloDeOficioDAO modeloDeOficioDAO;

    private List<ContaDeEmail> contasDeEmail;

    private Campanha campanha;

    private ModeloDeOficio modeloDeOficio;

    private String emailParaTeste = "";

    private Boolean alterar = false;

    @PostConstruct
    public void init() {
        contasDeEmail = contaDeEmailDAO.obterLista();

        String idModeloDeOficio = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("idModeloDeOficio");
        if (idModeloDeOficio != null) {
            modeloDeOficio = modeloDeOficioDAO.obterPorPK(Long.parseLong(idModeloDeOficio));
        }
        novaCampanha();

    }

    public void novaCampanha() {
        campanha = new Campanha();
        if (modeloDeOficio != null) {
            campanha.setModeloDeOficio(modeloDeOficio);
        }
    }

    public String salvar(List<Egresso> egressos) {
        try {
            if (campanha != null) {
                if (egressos != null && egressos.size() > 0) {
                    try {
                        campanhaDAO.adicionarEgressos(campanha, egressos);
                    } catch (Exception ex) {
                        Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                        Mensagem.lancarMensagemErro("Alguns egressos não foram adicionados na Campanha por estar com email inválido ou inexistente");
                    }
                }
                campanhaDAO.inserir(campanha);
                Mensagem.lancarMensagemInfo("Campanha salva com sucesso");
            } else {
                Mensagem.lancarMensagemWarn("Nenhum egresso informado para envio da Campanha");
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            Mensagem.lancarMensagemErro("Ocorreu um erro ao salvar a campanha, favor tentar novamente");
        }
        return "lista.xhtml";
    }

    public String enviar(List<Egresso> egressos) {
        try {
            if (campanha != null && egressos != null && egressos.size() > 0) {
                try {
                    campanhaDAO.adicionarEgressos(campanha, egressos);
                } catch (Exception ex) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
                    Mensagem.lancarMensagemWarn("Alguns egressos não foram adicionados na Campanha por estar com email inválido ou inexistente");
                }
                campanha.setStatus(StatusEnvioDaCampanha.ENVIANDO);
                campanhaDAO.inserir(campanha);
                campanhaDAO.carregarAnexos(campanha);
                enviadorEmail.enviar(campanha);
                Mensagem.lancarMensagemInfo("Envio da Campanha iniciado com sucesso");
            } else {
                Mensagem.lancarMensagemWarn("Nenhum egresso informado para envio da Campanha");
                return null;
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            Mensagem.lancarMensagemErro("Ocorreu um erro ao iniciar envio da campanha, favor tentar novamente");
        }
        return "lista.xhtml";
    }

    public String enviar(Campanha campanha) {
        try {
            if (campanha.getStatus().equals(StatusEnvioDaCampanha.SALVO)) {
                campanha.setStatus(StatusEnvioDaCampanha.ENVIANDO);
                campanhaDAO.alterar(campanha);
                campanhaDAO.carregarAnexos(campanha);
                enviadorEmail.enviar(campanha);
                Mensagem.lancarMensagemInfo("Envio da Campanha iniciado com sucesso");
            } else {
                Mensagem.lancarMensagemWarn("Só é possivel enviar uma campanha salva para envio posterior");
            }
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            Mensagem.lancarMensagemErro("Ocorreu um erro ao iniciar envio da campanha, favor tentar novamente");
        }
        return "lista.xhtml";
    }

    public String enviar(Long idCampanha) {

        Campanha obj = campanhaDAO.obterPorPK(idCampanha);
        if (obj != null) {
            return enviar(obj);
        } else {
            Mensagem.lancarMensagemWarn("Campanha não localizada");
        }
        return "lista.xhtml";
    }

    public void enviarTeste() throws Exception {
        Campanha cTemp = new Campanha();
        cTemp.setId(campanha.getId());
        cTemp.setAssunto(campanha.getAssunto());
        cTemp.setMensagem(campanha.getMensagem());
        cTemp.setAnexos(campanha.getAnexos());
        cTemp.setContaDeEmail(campanha.getContaDeEmail());
        Egresso e = new Egresso();
        e.setNome("Teste");
        e.setEmailParticular(emailParaTeste);
        campanhaDAO.adicionarEgressoTeste(cTemp, e);
        Mensagem.lancarMensagemInfo("Envio de Teste da Campanha iniciado com sucesso");
        enviadorEmail.enviarTeste(cTemp);
    }

    public void uploadAnexos(FileUploadEvent event) {
        AnexoEmail anexo = new AnexoEmail();
        anexo.setNome(event.getFile().getFileName());
        anexo.setConteudo(event.getFile().getContents());
        anexo.setTipoConteudo(event.getFile().getContentType());
        campanha.adicionarAnexo(anexo);
    }

    public boolean tipoDeAnexoImagem(AnexoEmail anexo) {
        return anexo.getTipoConteudo().startsWith("image");
    }

    public void removerAnexo(AnexoEmail anexo) {
        campanha.getAnexos().remove(anexo);
        if (anexo.getIdCampanha() != null) {
            campanhaDAO.removerAnexo(anexo);
        }
    }

    public List<String> camposPossiveis() {
        return CamposEgressosUtil.camposPossiveis();
    }

    public StreamedContent conteudoAnexo(AnexoEmail anexo) {
        return new DefaultStreamedContent(new ByteArrayInputStream(anexo.getConteudo()), anexo.getTipoConteudo());
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public String getEmailParaTeste() {
        return emailParaTeste;
    }

    public void setEmailParaTeste(String emailParaTeste) {
        this.emailParaTeste = emailParaTeste;
    }

    public String imagemBase64(AnexoEmail anexo) {
        return Base64.encode(anexo.getConteudo());
    }

    public EnviadorEmail getEnviadorEmail() {
        return enviadorEmail;
    }

    public void setEnviadorEmail(EnviadorEmail enviadorEmail) {
        this.enviadorEmail = enviadorEmail;
    }

    public CampanhaDAO getCampanhaDAO() {
        return campanhaDAO;
    }

    public void setCampanhaDAO(CampanhaDAO campanhaDAO) {
        this.campanhaDAO = campanhaDAO;
    }

    public ContaDeEmailDAO getContaDeEmailDAO() {
        return contaDeEmailDAO;
    }

    public void setContaDeEmailDAO(ContaDeEmailDAO contaDeEmailDAO) {
        this.contaDeEmailDAO = contaDeEmailDAO;
    }

    public List<ContaDeEmail> getContasDeEmail() {
        return contasDeEmail;
    }

    public void setContasDeEmail(List<ContaDeEmail> contasDeEmail) {
        this.contasDeEmail = contasDeEmail;
    }

    public ModeloDeOficioDAO getModeloDeOficioDAO() {
        return modeloDeOficioDAO;
    }

    public void setModeloDeOficioDAO(ModeloDeOficioDAO modeloDeOficioDAO) {
        this.modeloDeOficioDAO = modeloDeOficioDAO;
    }

    public ModeloDeOficio getModeloDeOficio() {
        return modeloDeOficio;
    }

    public void setModeloDeOficio(ModeloDeOficio modeloDeOficio) {
        this.modeloDeOficio = modeloDeOficio;
    }

    public Boolean getAlterar() {
        return alterar;
    }

    public void setAlterar(Boolean alterar) {
        this.alterar = alterar;
    }

}

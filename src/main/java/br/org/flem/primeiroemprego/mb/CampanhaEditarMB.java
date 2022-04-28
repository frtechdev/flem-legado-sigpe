/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CampanhaDAO;
import br.org.flem.primeiroemprego.dao.ContaDeEmailDAO;
import br.org.flem.primeiroemprego.dao.EnvioDeEmailDAO;
import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.entity.AnexoEmail;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.ContaDeEmail;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EnvioDeEmail;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.util.Mensagem;
import br.org.flem.primeiroemprego.util.RedirectUtil;
import br.org.flem.primeiroemprego.util.email.EnviadorEmail;
import br.org.flem.primeiroemprego.util.entity.CamposEgressosUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.ByteArrayInputStream;
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
 * @author AJLima
 */
@ManagedBean
@ViewScoped
public class CampanhaEditarMB {
    
    private EnviadorEmail enviadorEmail = new EnviadorEmail();

    @ManagedProperty(value = "#{campanhaDAO}")
    private CampanhaDAO campanhaDAO;

    @ManagedProperty(value = "#{contaDeEmailDAO}")
    private ContaDeEmailDAO contaDeEmailDAO;

    private List<ContaDeEmail> contasDeEmail;

    private Campanha campanha;

    private ModeloDeOficio modeloDeOficio;

    @PostConstruct
    public void init(){
        contasDeEmail = contaDeEmailDAO.obterLista();
        String idCampanha = ((Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()).get("id");
        if(idCampanha != null){
            campanha = campanhaDAO.obterPorPK(Long.parseLong(idCampanha));
            campanhaDAO.carregarAnexos(campanha);

        }else {
           campanha = new Campanha();
        }
        
    }
    
      public String salvar(){
        try {

            campanhaDAO.alterar(campanha);
                Mensagem.lancar("Campanha salva com sucesso");
                RedirectUtil.redirect("/campanha/lista.xhtml");
        } catch (Exception ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
            Mensagem.lancar("Ocorreu um erro ao salvar a campanha, favor tentar novamente");
        }
        return "lista.xhtml";
    }
    
    
    
    public void uploadAnexos(FileUploadEvent event) {
        AnexoEmail anexo = new AnexoEmail();
        anexo.setNome(event.getFile().getFileName());
        anexo.setConteudo(event.getFile().getContents());
        anexo.setTipoConteudo(event.getFile().getContentType());
        campanha.adicionarAnexo(anexo);
    }

    public boolean tipoDeAnexoImagem(AnexoEmail anexo){
        return anexo.getTipoConteudo().startsWith("image");
    }
    
    public void removerAnexo(AnexoEmail anexo){
        campanha.getAnexos().remove(anexo);
        if(anexo.getIdCampanha() != null){
            campanhaDAO.removerAnexo(anexo);
        }
    }
    
    public List<String> camposPossiveis(){
        return CamposEgressosUtil.camposPossiveis();
    }

    public StreamedContent conteudoAnexo(AnexoEmail anexo) {
        return new DefaultStreamedContent(new ByteArrayInputStream(anexo.getConteudo()), anexo.getTipoConteudo()); 
    }

    
       public String imagemBase64(AnexoEmail anexo){
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

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public ModeloDeOficio getModeloDeOficio() {
        return modeloDeOficio;
    }

    public void setModeloDeOficio(ModeloDeOficio modeloDeOficio) {
        this.modeloDeOficio = modeloDeOficio;
    }
    
    
}

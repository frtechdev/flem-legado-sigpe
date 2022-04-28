package br.org.flem.primeiroemprego.util.email;

import br.org.flem.primeiroemprego.dao.CampanhaDAO;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dao.EnvioDeEmailDAO;
import br.org.flem.primeiroemprego.dao.OficioDAO;
import br.org.flem.primeiroemprego.dto.EnvioDeEmailDTO;
import br.org.flem.primeiroemprego.entity.AnexoEmail;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EnvioDeEmail;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.entity.StatusEnvioDaCampanha;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.util.ExecutorThreads;
import br.org.flem.primeiroemprego.util.JPAUtil;
import br.org.flem.primeiroemprego.util.entity.CamposEgressosUtil;
import br.org.flem.primeiroemprego.util.entity.SubstituicaoDeCamposDoEgressoEmTextoUtil;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.apache.commons.mail.ByteArrayDataSource;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author emsilva
 */
public class EnviadorEmail implements Runnable, Serializable {

    private Campanha campanha;

    public EnviadorEmail() {
    }

    public EnviadorEmail(Campanha campanha) {
        this.campanha = campanha;
    }

    @Override
    public void run() {
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(campanha.getMensagem());
        List<String> substituicoes = new ArrayList<>();
        Campanha c = null;
        while (scet.procurarSubstituicao()) {
            substituicoes.add(scet.textoParaSubstituir());
        }
        try {
            try {
                c = new CampanhaDAO().obterPorPK(campanha.getId());
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
            if (c == null) {
                c = campanha;
            }
            List<EnvioDeEmailDTO> enviosDeEmail = new EnvioDeEmailDAO().obterEnviosDTOPorCampanha(c);
            for (EnvioDeEmailDTO envio : enviosDeEmail) {
                EnvioDeEmail envioOld = new EnvioDeEmailDAO().obterPorPK(envio.getId());
                try {
                    Egresso egresso = new EgressoDAO().obterPorPK(envio.getIdEgresso());
                    if (GenericValidator.isEmail(egresso.getEmailParticular())) {
                        HtmlEmail htmlMail = new HtmlEmail();
                        htmlMail.setCharset("iso-8859-1");
                        htmlMail.addTo(egresso.getEmailParticular(), egresso.getNome());
                        if (StringUtils.isNotEmpty(egresso.getEmailSecundario()) && GenericValidator.isEmail(egresso.getEmailSecundario())) {
                            htmlMail.addCc(egresso.getEmailSecundario());
                        }
                        htmlMail.setHostName(c.getContaDeEmail().getServidor());
                        htmlMail.setSmtpPort(c.getContaDeEmail().getPorta());
                        htmlMail.setFrom(c.getContaDeEmail().getEmailRemetente(), c.getContaDeEmail().getNomeRemetente());
                        htmlMail.setAuthentication(c.getContaDeEmail().getLogin(), c.getContaDeEmail().getSenha());

                        htmlMail.setSubject(c.getAssunto());

                        String htmlMsg = c.getMensagem();

                        for (String token : substituicoes) {
                            String valor = CamposEgressosUtil.getInformacao(egresso, token);
                            if (valor == null && envio.getTokensAdicionais().containsKey(token)) {
                                valor = envio.getTokensAdicionais().get(token);
                            }
                            htmlMsg = htmlMsg.replace("{" + token + "}", valor == null ? "" : valor);
                        }
                        if (c.getAnexos() == null) {
                            new CampanhaDAO().carregarAnexos(c);
                        }
                        if (c.getAnexos() != null && c.getAnexos().size() > 0) {
                            for (AnexoEmail anexo : c.getAnexos()) {
                                if (anexo.getTipoConteudo().substring(0, 5).equalsIgnoreCase("image")) {
                                    String cid = htmlMail.embed(new ByteArrayDataSource(anexo.getConteudo(), anexo.getTipoConteudo()), anexo.getNome());
                                    htmlMsg = htmlMsg.replace("cid:" + anexo.getNome(), "<img src='cid:" + cid + "'/>");
                                } else {
                                    htmlMail.attach(new ByteArrayDataSource(anexo.getConteudo(), anexo.getTipoConteudo()), anexo.getNome(), "");
                                }
                            }
                        }

                        Oficio oficio = new OficioDAO().obterPorModeloDeOficio(c.getModeloDeOficio(), egresso);
                        if (oficio != null) {
                            Response res = new FileStorageService().getFileByPath(oficio.getFilePath());
                            InputStream stream = res.readEntity(InputStream.class);
                            htmlMail.attach(new ByteArrayDataSource(stream, oficio.getTipo()), oficio.getNome(), "");
                        }

                        htmlMail.setHtmlMsg(htmlMsg);
                        htmlMail.setTextMsg("O seu leitor de e-mail não suporta mensagens HTML.");

                        htmlMail.send();
                        
                        envioOld.setStatus(StatusEnvioDaCampanha.ENVIADO);
                        envioOld.setDescricaoEnvio("Enviado com sucesso");
                    } else {
                        envioOld.setStatus(StatusEnvioDaCampanha.ERRO);
                        envioOld.setDescricaoEnvio("Email " + egresso.getEmailParticular() + " inválido");
                    }
                } catch (Exception e) {
                    envioOld.setStatus(StatusEnvioDaCampanha.ERRO);
                    envioOld.setDescricaoEnvio(e.getMessage());
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                }
                new EnvioDeEmailDAO().alterar(envioOld);
                Thread.sleep(500);
            }
            c.setStatus(StatusEnvioDaCampanha.ENVIADO);
            new CampanhaDAO().alterar(c);
            JPAUtil.closeEntityManager();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }

    }
    
    public void enviarTeste(Campanha campanha){
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(campanha.getMensagem());
        List<String> substituicoes = new ArrayList<>();
        while (scet.procurarSubstituicao()) {
            substituicoes.add(scet.textoParaSubstituir());
        }

        try {
            
            for (EnvioDeEmail envio : campanha.getEnvios()) {
                try {
                    Egresso egresso = envio.getEgresso();
                    if (GenericValidator.isEmail(egresso.getEmailParticular())) {
                        HtmlEmail htmlMail = new HtmlEmail();
                        htmlMail.setCharset("iso-8859-1");
                        htmlMail.addTo(egresso.getEmailParticular(), egresso.getNome());
                        if (StringUtils.isNotEmpty(egresso.getEmailSecundario()) && GenericValidator.isEmail(egresso.getEmailSecundario())) {
                            htmlMail.addCc(egresso.getEmailSecundario());
                        }

                        htmlMail.setHostName(campanha.getContaDeEmail().getServidor());
                        htmlMail.setSmtpPort(campanha.getContaDeEmail().getPorta());
                        htmlMail.setFrom(campanha.getContaDeEmail().getEmailRemetente(), campanha.getContaDeEmail().getNomeRemetente());
                        htmlMail.setAuthentication(campanha.getContaDeEmail().getLogin(), campanha.getContaDeEmail().getSenha());

                        htmlMail.setSubject(campanha.getAssunto());

                        String htmlMsg = campanha.getMensagem();

                        for (String token : substituicoes) {
                            String valor = CamposEgressosUtil.getInformacao(egresso, token);
                            if (valor == null && envio.getTokensAdicionais().containsKey(token)) {
                                valor = envio.getTokensAdicionais().get(token);
                            }
                            htmlMsg = htmlMsg.replace("{" + token + "}", valor == null ? "" : valor);
                        }

                        if (campanha.getAnexos() != null && campanha.getAnexos().size() > 0) {
                            for (AnexoEmail anexo : campanha.getAnexos()) {
                                if (anexo.getTipoConteudo().substring(0, 5).equalsIgnoreCase("image")) {
                                    String cid = htmlMail.embed(new ByteArrayDataSource(anexo.getConteudo(), anexo.getTipoConteudo()), anexo.getNome());
                                    htmlMsg = htmlMsg.replace("cid:" + anexo.getNome(), "<img src='cid:" + cid + "'/>");
                                } else {
                                    htmlMail.attach(new ByteArrayDataSource(anexo.getConteudo(), anexo.getTipoConteudo()), anexo.getNome(), "");
                                }
                            }
                        }

                        htmlMail.setHtmlMsg(htmlMsg);
                        htmlMail.setTextMsg("O seu leitor de e-mail não suporta mensagens HTML.");

                        htmlMail.send();
                    }
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
            JPAUtil.closeEntityManager();
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void enviar(Campanha campanha) {
        this.campanha = campanha;
        enviar();
    }

    public void enviar() {
        ExecutorThreads.getInstancia().executar(this);
    }
}

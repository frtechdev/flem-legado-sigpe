package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.CIDAO;
import br.org.flem.primeiroemprego.integracao.FileStorageService;
import br.org.flem.primeiroemprego.entity.CI;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CIListaMB implements Serializable {

    @ManagedProperty(value = "#{cIDAO}")
    private CIDAO ciDAO;

    @ManagedProperty(value = "#{fileStorageService}")
    private FileStorageService fileStorageService;

    private List<CI> cis;

    @PostConstruct
    public void init() {
        cis = ciDAO.obterLista();
    }

    public void fechar(CI ci) {
        ci.setFechada(true);
        try {
            ciDAO.alterar(ci);
            Mensagem.lancar("CI fechada");
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            Mensagem.lancar("Erro ao fechar CI");
        }
    }

    public void destravarLockDeCadastro() {
        synchronized (CICadastroMB.lock) {
            CICadastroMB.lock = "";
            Mensagem.lancar("Tela de Cadastro de CI liberada. A mesma só pode ser utilizada uma vez");
        }
    }

    public StreamedContent download(CI ci) {
        if (StringUtils.isNotEmpty(ci.getFilePath())) {
            try {
                InputStream stream = fileStorageService.getFileByPath(ci.getFilePath()).readEntity(InputStream.class);
                return new DefaultStreamedContent(stream, ci.getTipo(), ci.getNome());
            } catch (BusinessException ex) {
                Logger.getLogger(CIListaMB.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(ci.getArquivo()), ci.getTipo(), ci.getNome());
        }
        return null;
    }

    public boolean isTelaDeCadastroTravada() {
        return !CICadastroMB.lock.isEmpty();
    }

    public CIDAO getCiDAO() {
        return ciDAO;
    }

    public void setCiDAO(CIDAO ciDAO) {
        this.ciDAO = ciDAO;
    }

    public List<CI> getCis() {
        return cis;
    }

    public void setCis(List<CI> cis) {
        this.cis = cis;
    }

    public FileStorageService getFileStorageService() {
        return fileStorageService;
    }

    public void setFileStorageService(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

}

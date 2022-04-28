package br.org.flem.primeiroemprego.dao;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.primeiroemprego.dto.CampanhaDTO;
import br.org.flem.primeiroemprego.entity.Acao;
import br.org.flem.primeiroemprego.entity.AnexoEmail;
import br.org.flem.primeiroemprego.entity.Campanha;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EnvioDeEmail;
import br.org.flem.primeiroemprego.entity.StatusEnvioDaCampanha;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class CampanhaDAO extends GenericDAO<Campanha, Long> {

    public CampanhaDAO() {
        super(Campanha.class);
        this.ordemPadraoAscendente = false;//Ativar ordem padrão decrescente
        this.nomeColunaParaOdemSimples = "dataCriacao";
    }

    @ManagedProperty(value = "#{tipoDeAcaoDAO}")
    private TipoDeAcaoDAO tipoDeAcaoDAO;

    public List<AnexoEmail> obterAnexos(Campanha campanha) {
        List<AnexoEmail> anexos = new ArrayList<AnexoEmail>();
        File folder = new File(PropertiesUtil.getProperty("pathAnexos") + File.separator + campanha.getId() + File.separator);
        File[] files = folder.listFiles();
        if (files != null) {
            MimetypesFileTypeMap fileTypeMap = new MimetypesFileTypeMap();
            for (File file : files) {
                AnexoEmail anexo = new AnexoEmail();
                anexo.setIdCampanha(campanha.getId());
                anexo.setNome(file.getName());
                anexo.setTipoConteudo(fileTypeMap.getContentType(file));
                try {
                    anexo.setConteudo(FileUtils.readFileToByteArray(file));
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                }
                anexos.add(anexo);
            }
        }
        return anexos;
    }

    public void removerAnexo(AnexoEmail anexo) {
        if (anexo.getIdCampanha() != null) {
            try {
                File file = new File(anexo.getCaminhoCompleto());
                file.delete();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void carregarAnexos(Campanha campanha) {
        campanha.setAnexos(obterAnexos(campanha));
    }

    @Override
    public Campanha inserir(Campanha campanha) throws Exception {
        if (campanha != null) {
            super.inserir(campanha);
            if (campanha.getAnexos() != null) {
                for (AnexoEmail anexo : campanha.getAnexos()) {
                    anexo.setIdCampanha(campanha.getId());
                    salvarAnexo(anexo);
                }
            }
        }
        return campanha;
    }

    public void salvarAnexo(AnexoEmail anexo) {
        if (anexo.getIdCampanha() != null) {
            try {
                File file = new File(anexo.getCaminhoPasta());
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileOutputStream out = new FileOutputStream(anexo.getCaminhoCompleto());
                out.write(anexo.getConteudo());
                out.close();
            } catch (Exception e) {
                Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    public void adicionarEgresso(Campanha campanha, Egresso egresso) throws Exception {
        Campanha c = new CampanhaDAO().obterPorPK(campanha.getId());

        for (EnvioDeEmail e : c.getEnvios()) {//Garantindo não adicionar um egresso que já esteja na lista de enviados
            if (e.getEgresso().equals(egresso)) {
                return;
            }
        }
        if (egresso.getEmailParticular() == null || egresso.getEmailParticular().trim().isEmpty()) {
            throw new Exception("Egresso sem email");
        }
        if (!egresso.isEmailValido()) {
            throw new Exception("Email inválido");
        }
        EnvioDeEmail envioDaCampanha = new EnvioDeEmail();

        Acao acao = new Acao();
        acao.setData(new Date());
        acao.setDescricao("Envio de email da campanha::" + c.getAssunto());
        acao.setEgresso(egresso);
        acao.setTipoDeAcao(tipoDeAcaoDAO.tipoEmail());

        envioDaCampanha.setEgresso(egresso);
        envioDaCampanha.setEmail(egresso.getEmailParticular());
        envioDaCampanha.setEmailSecundario(egresso.getEmailSecundario());
        envioDaCampanha.setCampanha(c);
        envioDaCampanha.setAcao(new AcaoDAO().inserir(acao));
        c.getEnvios().add(envioDaCampanha);
    }

    public void adicionarEgressoTeste(Campanha campanha, Egresso egresso) throws Exception {
        for (EnvioDeEmail e : campanha.getEnvios()) {//Garantindo não adicionar um egresso que já esteja na lista de enviados
            if (e.getEgresso().equals(egresso)) {
                return;
            }
        }
        if (egresso.getEmailParticular() == null || egresso.getEmailParticular().trim().isEmpty()) {
            throw new Exception("Egreso sem email");
        }
        if (!egresso.isEmailValido()) {
            throw new Exception("Email inválido");
        }
        EnvioDeEmail envioDaCampanha = new EnvioDeEmail();

        //Integer id = new AcaoDAO().inserir(acao).getId().intValue();
        //envioDaCampanha.setDescricao("Envio de email da campanha:"+campanha.getAssunto());
        //envioDaCampanha.setData(new Date());
        envioDaCampanha.setEgresso(egresso);
        envioDaCampanha.setEmail(egresso.getEmailParticular());
        envioDaCampanha.setCampanha(campanha);
        campanha.getEnvios().add(envioDaCampanha);
    }

    public void adicionarEgressos(Campanha campanha, List<Egresso> egressos) throws Exception {
        if (egressos != null) {
            boolean exceptionEmAlgumEgresso = false;
            for (Egresso egresso : egressos) {
                try {
                    adicionarEgresso(campanha, egresso);
                } catch (Exception e) {
                    exceptionEmAlgumEgresso = true;
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
            if (exceptionEmAlgumEgresso) {
                throw new Exception("Email inválido ou inexistente em algum dos egressos");
            }
        }
    }

    public TipoDeAcaoDAO getTipoDeAcaoDAO() {
        return tipoDeAcaoDAO;
    }

    public void setTipoDeAcaoDAO(TipoDeAcaoDAO tipoDeAcaoDAO) {
        this.tipoDeAcaoDAO = tipoDeAcaoDAO;
    }

    public List<Campanha> obterComStatusSalva() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Campanha> query = cb.createQuery(Campanha.class);
        Root<Campanha> root = query.from(Campanha.class);
        query.where(cb.equal(root.get("status"), StatusEnvioDaCampanha.SALVO));

        return getEntityManager().createQuery(query).getResultList();
    }

    public List<CampanhaDTO> obterListaDTO() {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<CampanhaDTO> criteria = cb.createQuery(CampanhaDTO.class);
        Root<Campanha> root = criteria.from(Campanha.class);
        
        criteria.select(cb.construct(CampanhaDTO.class,
                root.get("id"),
                root.get("assunto"),
                root.get("mensagem"),
                root.get("status"),
                root.get("dataCriacao")
        ));
        criteria.orderBy(cb.desc(root.get("dataCriacao")));
        return getEntityManager().createQuery(criteria).getResultList();
    }

}

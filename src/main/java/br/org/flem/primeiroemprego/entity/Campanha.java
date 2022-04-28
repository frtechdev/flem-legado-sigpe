package br.org.flem.primeiroemprego.entity;

import br.org.flem.primeiroemprego.util.ArquivoUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author emsilva
 */
@Entity
public class Campanha extends UID<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_campanha")
    private Long id;

    @NotEmpty
    private String assunto = "";

    @NotEmpty
    @Type(type="text")
    private String mensagem = "";

    @ManyToOne
    @JoinColumn(name = "id_contadeemail")
    @NotNull(message = "{campanha.contaDeEmail.notnull}")
    private ContaDeEmail contaDeEmail;

    @OneToMany(mappedBy = "campanha", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<EnvioDeEmail> envios = new ArrayList<>();

    @Transient
    private List<AnexoEmail> anexos;
    
    @Enumerated(EnumType.ORDINAL)
    private StatusEnvioDaCampanha status = StatusEnvioDaCampanha.SALVO;

    @ManyToOne
    @JoinColumn(name = "id_modeloDeOficio")
    private ModeloDeOficio modeloDeOficio;

    public void carregarMensagemPorTemplate(String templateHtml) {
        try{
            mensagem = ArquivoUtil.ler(templateHtml,true);
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, e.getMessage(), e);
        }
    }

    
    public String getAssunto() {
        return assunto;
    }

    
    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    
    public String getMensagem() {
        return mensagem;
    }

    
    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    
    public List<EnvioDeEmail> getEnvios() {
        return envios;
    }

    public List<AnexoEmail> getAnexos() {
        return anexos;
    }
    
    
    public void setAnexos(List<AnexoEmail> anexos){
        this.anexos = anexos;
    }

    
    public void adicionarAnexo(AnexoEmail anexo) {
        if(anexos == null){
            anexos = new ArrayList<>();
        }
        anexos.add(anexo);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public StatusEnvioDaCampanha getStatus() {
        return status;
    }

    public void setStatus(StatusEnvioDaCampanha status) {
        this.status = status;
    }

    public ContaDeEmail getContaDeEmail() {
        return contaDeEmail;
    }

    public void setContaDeEmail(ContaDeEmail contaDeEmail) {
        this.contaDeEmail = contaDeEmail;
    }

    public ModeloDeOficio getModeloDeOficio() {
        return modeloDeOficio;
    }

    public void setModeloDeOficio(ModeloDeOficio modeloDeOficio) {
        this.modeloDeOficio = modeloDeOficio;
    }

    public void setEnvios(List<EnvioDeEmail> envios) {
        this.envios = envios;
    }

}
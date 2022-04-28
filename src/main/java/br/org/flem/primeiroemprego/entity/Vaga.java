package br.org.flem.primeiroemprego.entity;

import br.org.flem.primeiroemprego.util.annotation.Alias;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

/**
 *
 * @author emsilva
 */
@Entity
@Audited
public class Vaga extends UID<Long> {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_vaga")
    private Long id;

    //@ManyToOne
    //@JoinColumn(name="id_unidadeDeLotacao")
    //@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    //private UnidadeDeLotacao unidadeDeLotacao;

    @Alias(value="Demandante",valueAdicional="Sigla Demandante")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_demandante")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Demandante demandante;

    @Alias(value="Instituição")
    private String instituicao;

    @Alias(value="Unidade de Lotação")
    private String unidadeDeLotacao;
    @Alias(value="Logradouro da Unidade de Lotação")
    private String logradouroUnidadeDeLotacao;
    @Alias(value="Bairro da Unidade de Lotação")
    private String bairroUnidadeDeLotacao;
    @Alias(value="CEP da Unidade de Lotação")
    private String cepUnidadeDeLotacao;

    @Alias(value="Município da Unidade de Lotação")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_municipioUnidadeDeLotacao")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Municipio municipioUnidadeDeLotacao;

    //@ManyToOne
    //@JoinColumn(name="id_pontoFocal")
    //@NotAudited
    //@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    //private PontoFocal pontoFocal;
    @Alias(value="Ponto Focal na Unidade")
    private String pontoFocalNaUnidade;
    @Alias(value="Telefone 1 Ponto Focal")
    private String telefone1PontoFocal;
    @Alias(value="Telefone 2 Ponto Focal")
    private String telefone2PontoFocal;
    @Alias(value="Telefone 3 Ponto Focal")
    private String telefone3PontoFocal;
    @Alias(value="Telefone 4 Ponto Focal")
    private String telefone4PontoFocal;
    @Alias(value="Telefone 5 Ponto Focal")
    private String telefone5PontoFocal;
    @Alias(value="Email Ponto Focal")
    private String emailPontoFocal;

    @Alias(value="Município Vaga")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_municipioDaVaga")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Municipio municipio;

    @Alias(value="Data Convocação SETRE/SINE")
    private String dataConvocacaoSETRESINE;

    @Alias(value="Remessa SETRE/SINE")
    private String remessaSETRESINE;
    @Alias(value="Data Remessa SETRE/SINE")
    private String dataRemessaSETRESINE;

    @Alias(value="Mês Remessa SETRE/SINE")
    private String mesRemessaSETRESINE;

    @Alias(value="Data de Envio da Situação para SETRE/SINE")
    private String dataDeEnvioDaSituacaoParaSETRESINE;
    
    @Alias(value="Data Início Atividades")
    @Temporal(TemporalType.DATE)
    private Date dataInicioAtividades;
    private String observacaoInicioAtividades;

    @Alias(value="Publicação D.O")
    private String publicacaoDiarioOficial;

    @Alias(value="Situação")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @NotNull(message = "{vaga.situacao.notnull}")
    @JoinColumn(name="id_situacao")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Situacao situacao;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="id_egresso")//Por mais que o egresso não mude na vaga, é necessário autita-lo para ter o campo na tabela de alterações
    private Egresso egresso;

    public Vaga(Demandante demandante, Municipio municipio) {
        this.demandante = demandante;
        this.municipio = municipio;
    }

    public Vaga() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Demandante getDemandante() {
        return demandante;
    }

    public void setDemandante(Demandante demandante) {
        this.demandante = demandante;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public String getUnidadeDeLotacao() {
        return unidadeDeLotacao;
    }

    public void setUnidadeDeLotacao(String unidadeDeLotacao) {
        this.unidadeDeLotacao = unidadeDeLotacao;
    }

    public String getPontoFocalNaUnidade() {
        return pontoFocalNaUnidade;
    }

    public void setPontoFocalNaUnidade(String pontoFocalNaUnidade) {
        this.pontoFocalNaUnidade = pontoFocalNaUnidade;
    }

    public String getTelefone1PontoFocal() {
        return telefone1PontoFocal;
    }

    public void setTelefone1PontoFocal(String telefone1PontoFocal) {
        this.telefone1PontoFocal = telefone1PontoFocal;
    }

    public String getTelefone2PontoFocal() {
        return telefone2PontoFocal;
    }

    public void setTelefone2PontoFocal(String telefone2PontoFocal) {
        this.telefone2PontoFocal = telefone2PontoFocal;
    }

    public String getTelefone3PontoFocal() {
        return telefone3PontoFocal;
    }

    public void setTelefone3PontoFocal(String telefone3PontoFocal) {
        this.telefone3PontoFocal = telefone3PontoFocal;
    }

    public String getTelefone4PontoFocal() {
        return telefone4PontoFocal;
    }

    public void setTelefone4PontoFocal(String telefone4PontoFocal) {
        this.telefone4PontoFocal = telefone4PontoFocal;
    }

    public String getTelefone5PontoFocal() {
        return telefone5PontoFocal;
    }

    public void setTelefone5PontoFocal(String telefone5PontoFocal) {
        this.telefone5PontoFocal = telefone5PontoFocal;
    }

    public String getEmailPontoFocal() {
        return emailPontoFocal;
    }

    public void setEmailPontoFocal(String emailPontoFocal) {
        this.emailPontoFocal = emailPontoFocal;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getDataConvocacaoSETRESINE() {
        return dataConvocacaoSETRESINE;
    }

    public void setDataConvocacaoSETRESINE(String dataConvocacaoSETRESINE) {
        this.dataConvocacaoSETRESINE = dataConvocacaoSETRESINE;
    }

    public String getRemessaSETRESINE() {
        return remessaSETRESINE;
    }

    public void setRemessaSETRESINE(String remessaSETRESINE) {
        this.remessaSETRESINE = remessaSETRESINE;
    }

    public String getDataRemessaSETRESINE() {
        return dataRemessaSETRESINE;
    }

    public void setDataRemessaSETRESINE(String dataRemessaSETRESINE) {
        this.dataRemessaSETRESINE = dataRemessaSETRESINE;
    }

    public String getMesRemessaSETRESINE() {
        return mesRemessaSETRESINE;
    }

    public void setMesRemessaSETRESINE(String mesRemessaSETRESINE) {
        this.mesRemessaSETRESINE = mesRemessaSETRESINE;
    }

    public String getDataDeEnvioDaSituacaoParaSETRESINE() {
        return dataDeEnvioDaSituacaoParaSETRESINE;
    }

    public void setDataDeEnvioDaSituacaoParaSETRESINE(String dataDeEnvioDaSituacaoParaSETRESINE) {
        this.dataDeEnvioDaSituacaoParaSETRESINE = dataDeEnvioDaSituacaoParaSETRESINE;
    }

    public Date getDataInicioAtividades() {
        return dataInicioAtividades;
    }

    public void setDataInicioAtividades(Date dataInicioAtividades) {
        this.dataInicioAtividades = dataInicioAtividades;
    }

    public String getObservacaoInicioAtividades() {
        return observacaoInicioAtividades;
    }

    public void setObservacaoInicioAtividades(String observacaoInicioAtividades) {
        this.observacaoInicioAtividades = observacaoInicioAtividades;
    }

    public String getPublicacaoDiarioOficial() {
        return publicacaoDiarioOficial;
    }

    public void setPublicacaoDiarioOficial(String publicacaoDiarioOficial) {
        this.publicacaoDiarioOficial = publicacaoDiarioOficial;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public String getLogradouroUnidadeDeLotacao() {
        return logradouroUnidadeDeLotacao;
    }

    public void setLogradouroUnidadeDeLotacao(String logradouroUnidadeDeLotacao) {
        this.logradouroUnidadeDeLotacao = logradouroUnidadeDeLotacao;
    }

    public String getBairroUnidadeDeLotacao() {
        return bairroUnidadeDeLotacao;
    }

    public void setBairroUnidadeDeLotacao(String bairroUnidadeDeLotacao) {
        this.bairroUnidadeDeLotacao = bairroUnidadeDeLotacao;
    }

    public String getCepUnidadeDeLotacao() {
        return cepUnidadeDeLotacao;
    }

    public void setCepUnidadeDeLotacao(String cepUnidadeDeLotacao) {
        this.cepUnidadeDeLotacao = cepUnidadeDeLotacao;
    }

    public Municipio getMunicipioUnidadeDeLotacao() {
        return municipioUnidadeDeLotacao;
    }

    public void setMunicipioUnidadeDeLotacao(Municipio municipioUnidadeDeLotacao) {
        this.municipioUnidadeDeLotacao = municipioUnidadeDeLotacao;
    }

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

}

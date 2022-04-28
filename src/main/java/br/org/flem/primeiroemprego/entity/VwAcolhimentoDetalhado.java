
package br.org.flem.primeiroemprego.entity;

import br.org.flem.fwe.util.Data;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.Immutable;

/**
 * Descreva Sua Classe
 * Classe para mapeamento da view VwAcolhimentoDetalhado
 * @author <code>tscortes@flem.org.br</code>
 * 25/02/2019
 * @version 1.0
 */
@Entity
@Table(name = "vw_acolhimento_detalhado", schema = "dbo")
@Immutable
public class VwAcolhimentoDetalhado implements Serializable {
    
    @Id
    private Integer matriculaFlem;
    
    @Column(name="id_esc_regional")
    private Long idEscritorioRegional;
    
    private String escritorioRegional;
    
    @Column(name="idMunicipioVaga")
    private Long idMunicipio;
    
    private String municipioVaga;
    
    private String cpf;
    private String nome;
    private String email;
    private String telefones;
    @Temporal(TemporalType.DATE)
    private Date dataAdmissao;
    @Temporal(TemporalType.DATE)
    private Date dataDemissao;
    private String datas;
    @Column(name="id_demandante")
    private Long idDemandante;
    private String demandante;
    private String unidadeDeLotacao;
    private String logradouroUnidadeDeLotacao;
    private String bairroUnidadeDeLotacao;
    private String pontoFocalNaUnidade;
    private String municipioUnidadedeLotacao;
    private String telefonesPF;
    private String emailPontoFocal;
    private Long idCategoria;
    private String categoriaSituacao;
    
    private String participou;
    
    @ManyToOne
    @JoinColumn(name="idCategoria",insertable=false, updatable=false)
    private CategoriaDaSituacao categoria;
    @ManyToOne
    @JoinColumn(name="id_demandante",insertable=false, updatable=false)
    private Demandante demandanteObj;
    @ManyToOne
    @JoinColumn(name="idMunicipioVaga",insertable=false, updatable=false)
    private Municipio municipio;
    @ManyToOne
    @JoinColumn(name="id_esc_regional",insertable=false, updatable=false)
    private EscritorioRegional escritorioRegionalObj;

    public Integer getMatriculaFlem() {
        return matriculaFlem;
    }

    public void setMatriculaFlem(Integer matriculaFlem) {
        this.matriculaFlem = matriculaFlem;
    }

    public Long getIdEscritorioRegional() {
        return idEscritorioRegional;
    }

    public void setIdEscritorioRegional(Long idEscritorioRegional) {
        this.idEscritorioRegional = idEscritorioRegional;
    }

    public String getEscritorioRegional() {
        return escritorioRegional;
    }

    public void setEscritorioRegional(String escritorioRegional) {
        this.escritorioRegional = escritorioRegional;
    }

    public Long getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(Long idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getMunicipioVaga() {
        return municipioVaga;
    }

    public void setMunicipioVaga(String municipioVaga) {
        this.municipioVaga = municipioVaga;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefones() {
        return telefones;
    }

    public void setTelefones(String telefones) {
        this.telefones = telefones;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public Date getDataDemissao() {
        return dataDemissao;
    }

    public void setDataDemissao(Date dataDemissao) {
        this.dataDemissao = dataDemissao;
    }

    public String getDatas() {
        return datas;
    }

    public void setDatas(String datas) {
        this.datas = datas;
    }

    public Long getIdDemandante() {
        return idDemandante;
    }

    public void setIdDemandante(Long idDemandante) {
        this.idDemandante = idDemandante;
    }

    public String getDemandante() {
        return demandante;
    }

    public void setDemandante(String demandante) {
        this.demandante = demandante;
    }

    public String getUnidadeDeLotacao() {
        return unidadeDeLotacao;
    }

    public void setUnidadeDeLotacao(String unidadeDeLotacao) {
        this.unidadeDeLotacao = unidadeDeLotacao;
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

    public String getPontoFocalNaUnidade() {
        return pontoFocalNaUnidade;
    }

    public void setPontoFocalNaUnidade(String pontoFocalNaUnidade) {
        this.pontoFocalNaUnidade = pontoFocalNaUnidade;
    }

    public String getMunicipioUnidadedeLotacao() {
        return municipioUnidadedeLotacao;
    }

    public void setMunicipioUnidadedeLotacao(String municipioUnidadedeLotacao) {
        this.municipioUnidadedeLotacao = municipioUnidadedeLotacao;
    }

    public String getTelefonesPF() {
        return telefonesPF;
    }

    public void setTelefonesPF(String telefonesPF) {
        this.telefonesPF = telefonesPF;
    }

    public String getEmailPontoFocal() {
        return emailPontoFocal;
    }

    public void setEmailPontoFocal(String emailPontoFocal) {
        this.emailPontoFocal = emailPontoFocal;
    }

    public CategoriaDaSituacao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaDaSituacao categoria) {
        this.categoria = categoria;
    }

    public Demandante getDemandanteObj() {
        return demandanteObj;
    }

    public void setDemandanteObj(Demandante demandanteObj) {
        this.demandanteObj = demandanteObj;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public EscritorioRegional getEscritorioRegionalObj() {
        return escritorioRegionalObj;
    }

    public void setEscritorioRegionalObj(EscritorioRegional escritorioRegionalObj) {
        this.escritorioRegionalObj = escritorioRegionalObj;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoriaSituacao() {
        return categoriaSituacao;
    }

    public void setCategoriaSituacao(String categoriaSituacao) {
        this.categoriaSituacao = categoriaSituacao;
    }
    @Transient
    public String getMesAnoAdmissao(){
        String mesAno = "";
        if(dataAdmissao != null){
            mesAno = Data.formataData(dataAdmissao, "MM/yyyy");
        }
        return mesAno;
    }
    @Transient
    public String getMesAnoDesligamento(){
        String mesAno = "";
        if( dataDemissao != null ){
            mesAno = Data.formataData(dataDemissao, "MM/yyyy");
        }
        return mesAno;
    }
    
    @Transient
    public String getAcolhido(){
        return this.participou;
    }

    public String getParticipou() {
        return participou;
    }

    public void setParticipou(String participou) {
        this.participou = participou;
    }
    
}

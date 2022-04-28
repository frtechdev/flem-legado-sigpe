package br.org.flem.primeiroemprego.entity;

import br.org.flem.fw.service.IFuncionario;
import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.util.annotation.Alias;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.alfredlibrary.validadores.CPF;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.envers.RelationTargetAuditMode;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Audited
public class Egresso extends UID<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_egresso")
    private Long id;

    @NotEmpty(message = "{egresso.nome.notempty}")
    @Alias(value="Nome")
    private String nome;
    @Alias(value = "Matricula FLEM")
    private String matriculaFlem;

    @Transient
    private IFuncionario funcionario;

    @Alias(value="Data de Nascimento")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    @Alias(value = "CPF")
    @NotEmpty(message = "{egresso.cpf.notempty}")
    private String cpf; //validador

    @Alias(value="RG")
    private String rg;
    
    @Alias(value="CTPS")
    private String ctps;
    
    @Alias(value="PIS")
    private String pis;
    
    @Alias(value="Raça / Cor")
    @Enumerated
    @NotNull
    private RacaCor racaCor = RacaCor.NAO_INFORMADA;

    @Alias(value="Deficiência")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name = "id_deficiencia")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Deficiencia deficiencia;

    //Endereço
    @Alias(value="CEP")
    private String cep;
    @Alias(value="Bairro")            
    private String bairro;
    @Alias(value="Endereço")
    private String endereco;
    @Alias(value="Complemento")
    private String complemento;
    @Alias(value="Numero")
    private String numero;
    
    @Alias(value="Municipio Residência")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_municipio")
    @NotNull(message = "{egresso.municipio.notnull}")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Municipio municipio;

    //Contato
    @Alias(value="DDD Telefone 1")
    private String dddTelefone1;
    @Alias(value="Telefone 1")
    private String telefone1;

    @Alias(value="DDD Telefone 2")
    private String dddTelefone2;
    @Alias(value="Telefone 2")
    private String telefone2;

    @Alias(value="DDD Celular")
    private String dddCelular;
    @Alias(value="Celular")
    private String celular;

    @Alias(value="DDD Contato")
    private String dddContato;
    @Alias(value="Contato")
    private String contato;

    @Alias(value="Email")
    private String emailParticular;

    @Alias(value="Email Secundário")
    private String emailSecundario;

    @Alias(value="Sexo")
    private String sexo;

    @Alias(value="Matrícula SAEB")
    private String matriculaSAEB;

    @Alias(value="Município Escola")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_municipioEscola")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Municipio municipioEscola;
    
    @Alias(value="Escola Conclusao")
    private String escolaconclusao;
    
    @Alias(value="Formação Beneficiário")
    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_formacao")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private Formacao formacao;
    
    @Alias(value="Ava Informação")
    private String avaInformacao;

    @Alias(value="Anamnese?")
    private String anamnese;

    @Alias(value="Data Recebimento Fardamento")
    @Temporal(TemporalType.DATE)
    private Date fardamentoRecebido;

    @Alias(value="Vale Alimentação")
    private String valeAlimentacao;
    @Alias(value="Vale Transporte")
    private String valeTransporte;
    @Alias(value="ASO")
    private Boolean aso;
    @Alias(value="Tamanho Fardamento")
    private String tamanhoDeCamisa = "";

    @Alias(value="Observação")
    @Type(type="text")
    private String observacao;
    
    private Boolean relocacaoDeVagaEncaminhada = false;
    
    private Boolean relocacaoDeVagaAtendida = false;

    @OneToMany(mappedBy="egresso",fetch = FetchType.EAGER)
    @NotAudited
    @Fetch(FetchMode.SELECT)
    private List<EgressoLista> estadoNasListas;

    @Transient
    private List<Lista> listas;

    @OneToMany(mappedBy="egresso")
    @NotAudited
    private List<Acao> acoes;
    
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="egresso")
    @Fetch(FetchMode.SELECT)
    private List<MaterialEgresso> materiais;

    @OneToMany(cascade= CascadeType.ALL, mappedBy="egresso")
    @NotAudited
    @Fetch(FetchMode.SELECT)
    private List<AssistenciaSocial> assistenciasSociais;

    @OneToOne(cascade= CascadeType.ALL,mappedBy = "egresso",fetch = FetchType.EAGER)
    private Vaga vaga;

    @Transient
    private List<Vaga> historicoVaga;

    @NotAudited
    @OneToMany(mappedBy = "id.egresso", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    @Alias(value="Participou do Evento?")
    private List<EventoEgresso> eventosParticipados;
    
    @OneToMany(mappedBy = "egresso", fetch = FetchType.EAGER)
    @NotAudited
    private List<DocumentoDoEgresso> documentos;
    
    @NotAudited
    @Formula(value = "(SELECT d.distancia FROM DistanciaEntreMunicipios d LEFT JOIN Vaga v ON v.id_egresso = id_egresso WHERE id_municipio = d.id_municipioOrigem AND v.id_municipioDaVaga = d.id_municipioDestino)")
    private Long distanciaEntreMunicipios;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="egressos")
    @NotAudited
    @Fetch(FetchMode.SELECT)
    private List<CI> cis;
    
    @OneToMany(cascade= CascadeType.ALL, mappedBy="egresso")
    @NotAudited
    @Fetch(FetchMode.SELECT)
    private List<Oficio> oficios;
    
    
    //Documentos Pendentes
    @Alias(value="Ficha de Admissão Pendente")
    private Boolean fichaAdmissao=false;
    @Alias(value="CTPS Pendente")
    private Boolean ctpsPendente=false;
    @Alias(value="CPF Pendente")
    private Boolean cpfPendente=false;
    @Alias(value="Comprovante de residência Pendente")
    private Boolean comprovanteresidenciaPendente=false;
    @Alias(value="RG Pendente")
    private Boolean rgPendente=false;
    @Alias(value="Cartão Cidadão Pendente")
    private Boolean cartaCidadaoPendente=false;
    @Alias(value="Titulo de Eleitor Pendente")
    private Boolean tituloEleitorPendente=false;
    @Alias(value="Comprovante de Votação Pendente")
    private Boolean comprovanteVotacaoPendente=false;
    @Alias(value="Diploma Pendente")
    private Boolean diplomaPendente=false;
    @Alias(value="Curriculo Pendente")
    private Boolean curriculoPendente=false;
    @Alias(value="Dados Bancários Pendente")
    private Boolean dadosbancariosPendente=false;
    @Alias(value="Foto 3x4 Pendente")
    private Boolean foto3x4Pendente=false;
    @Alias(value="ASO Pendente Pendente")
    private Boolean asoPendente=false;
    @Alias(value="Certidao de Casamento Pendente")
    private Boolean certidaoCasamentoPendente=false;
    @Alias(value="Certidão de Nascimento Pendente")
    private Boolean certidaoNascimentoPendente=false;
    @Alias(value="Comprovante de Escolaridade Pendente")
    private Boolean comprovanteEscolaridadePendente=false;
    @Alias(value="Reservista Pendente")
    private Boolean reservistaPendente=false;
    @Alias(value="Cartão de Vacinação Pendente")
    private Boolean cartaoVacinaPendente=false;
    @Alias(value="Contrato De trabalho Pendente")
    private Boolean contratoTrabalhoFlem=false;
    
    //Beneficios
    @Alias(value="Vale Transporte")
    private Boolean vale_Transporte=false;
    @Alias(value="Vale Alimentação")
    private Boolean vale_Alimentacao=false;
    @Alias(value="Plano de Saúde")
    private Boolean plano_Saude=false;
    @Alias(value="Crachá")
    private Boolean cracha=false;
    
  ////////////////////////////////////////// 
    
    @Alias(value="Estagio Conluído")
    private Boolean estagioConcluido=false;
    @Alias(value="Carteira Assinada")
    private Boolean carteiraAssinada=false;
    
    @Transient
    private String tempoContratacao;
    @Transient
    private String mesAnoAdmissao;
    @Transient
    private String mesAnoDesligamento;
    @Alias(value="Curso Superior")
    private String cursoSuperior;
    @Alias(value="Curso Superior Concluído")
    private String cursoSuperiorConcluido;
    @Alias(value="Tipo de Instituição")
    @Enumerated(EnumType.STRING)
    private EnumTipoInstituicaoEnsino tipoInstituicaoSuperior;
    @Alias(value="Instituição de ensino superior")
    private String nomeInstituicaoSuperior;
    @Alias(value="Curso da Graduação")
    private String nomeCursoGraduacao;
    @Alias(value="Modalidade da Graduação")
    @Enumerated(EnumType.STRING)
    private EnumModalidadeEnsino modalidadeGraduacao;
    @Alias(value="Ano da Matricula")
    private String anoMatricula;
    @Alias(value="Ano da Conclusão")
    private String anoConclusaoCurso;
    @Alias(value="Semestre")
    private String semestre;
    @Alias(value="Renda do PPE Ajuda")
    private Boolean rendaPPEAjuda;
    @Alias(value="Data do Semestre")
    @Temporal(TemporalType.DATE)
    private Date dataRespostaSemestre;
    @Temporal(TemporalType.DATE)
    @Alias(value="Data Admissão")
    private Date dataAdmissao;
    @Alias(value="Pretende Fazer Curso Superior")
    private String pretendeFazerCursoSuperior;
    
    @Alias(value="Curso Técnico ou Profissionalizante")
    private String cursoTecnicoProfissionalizante;
    @Alias(value="Tipo de Instituição Técnico")
    @Enumerated(EnumType.STRING)
    private EnumTipoInstituicaoEnsino tipoInstituicaoTecnica;
    @Alias(value="Instituição de Ensino Técnico")
    private String nomeInstituicaoTecnico;
    @Alias(value="Curso da Técnico")
    private String nomeCursoTecnico;
    
    @Alias(value="Situação do Egresso")
    @Transient
    private String situacao;    

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getMatriculaFlem() {
        return matriculaFlem;
    }

    public void setMatriculaFlem(String matriculaFlem) {
        this.matriculaFlem = matriculaFlem;
    }

    public IFuncionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(IFuncionario funcionario) {
        this.funcionario = funcionario;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
    
    public Float getIdade() {
        if(dataNascimento == null){
            return null;
        }
        return Data.anoDiffDecimal(dataNascimento, new Date());
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public RacaCor getRacaCor() {
        return racaCor;
    }

    public void setRacaCor(RacaCor racaCor) {
        this.racaCor = racaCor;
    }

    public Deficiencia getDeficiencia() {
        return deficiencia;
    }

    public void setDeficiencia(Deficiencia deficiencia) {
        this.deficiencia = deficiencia;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Municipio getMunicipio() {
        return municipio;
    }

    public void setMunicipio(Municipio municipio) {
        this.municipio = municipio;
    }

    public String getDddTelefone1() {
        return dddTelefone1;
    }

    public void setDddTelefone1(String dddTelefone1) {
        this.dddTelefone1 = dddTelefone1;
    }

    public String getTelefone1() {
        return telefone1;
    }

    public void setTelefone1(String telefone1) {
        this.telefone1 = telefone1;
    }

    public String getDddTelefone2() {
        return dddTelefone2;
    }

    public void setDddTelefone2(String dddTelefone2) {
        this.dddTelefone2 = dddTelefone2;
    }

    public String getTelefone2() {
        return telefone2;
    }

    public void setTelefone2(String telefone2) {
        this.telefone2 = telefone2;
    }

    public String getDddCelular() {
        return dddCelular;
    }

    public void setDddCelular(String dddCelular) {
        this.dddCelular = dddCelular;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDddContato() {
        return dddContato;
    }

    public void setDddContato(String dddContato) {
        this.dddContato = dddContato;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getEmailParticular() {
        return emailParticular;
    }

    public void setEmailParticular(String emailParticular) {
        this.emailParticular = emailParticular;
    }

    public String getEmailSecundario() {
        return emailSecundario;
    }

    public void setEmailSecundario(String emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

    public boolean isEmailValido(){
        return GenericValidator.isEmail(emailParticular);
    }
    
    public boolean isEmailSecundarioValido(){
        return GenericValidator.isEmail(emailSecundario);
    }

    public boolean isCpfValido(){
        return cpf != null ? CPF.isValido(cpf) : false;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getMatriculaSAEB() {
        return matriculaSAEB;
    }

    public void setMatriculaSAEB(String matriculaSAEB) {
        this.matriculaSAEB = matriculaSAEB;
    }

    public Municipio getMunicipioEscola() {
        return municipioEscola;
    }

    public void setMunicipioEscola(Municipio municipioEscola) {
        this.municipioEscola = municipioEscola;
    }

    public Formacao getFormacao() {
        return formacao;
    }

    public void setFormacao(Formacao formacao) {
        this.formacao = formacao;
    }

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<EgressoLista> getEstadoNasListas() {
        return estadoNasListas;
    }

    public void setEstadoNasListas(List<EgressoLista> listas) {
        this.estadoNasListas = listas;
    }
    
    public List<Lista> getListas() {
        if(listas == null){
            listas = new ArrayList<Lista>();
            for(EgressoLista el : estadoNasListas){
                listas.add(el.getLista());
            }
        }
        return listas;
    }

    public List<Acao> getAcoes() {
        return acoes;
    }

    public void setAcoes(List<Acao> acoes) {
        this.acoes = acoes;
    }

    public List<AssistenciaSocial> getAssistenciasSociais() {
        return assistenciasSociais;
    }

    public void setAssistenciasSociais(List<AssistenciaSocial> assistenciasSociais) {
        this.assistenciasSociais = assistenciasSociais;
    }

    public Date getFardamentoRecebido() {
        return fardamentoRecebido;
    }

    public void setFardamentoRecebido(Date fardamentoRecebido) {
        this.fardamentoRecebido = fardamentoRecebido;
    }
    
    public boolean isDivergenciaMunicipio(){
        if(vaga != null && vaga.getMunicipio() != null && municipio != null){
            return !vaga.getMunicipio().equals(municipio);
        }
        if(vaga.getMunicipio() == null && municipio == null){
            return false;
        }
        return true;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public String getValeAlimentacao() {
        return valeAlimentacao;
    }

    public void setValeAlimentacao(String valeAlimentacao) {
        this.valeAlimentacao = valeAlimentacao;
    }

    public String getValeTransporte() {
        return valeTransporte;
    }

    public void setValeTransporte(String valeTransporte) {
        this.valeTransporte = valeTransporte;
    }

    public Boolean getAso() {
        return aso;
    }

    public void setAso(Boolean aso) {
        this.aso = aso;
    }

    public List<Vaga> getHistoricoVaga() {
        return historicoVaga;
    }

    public void setHistoricoVaga(List<Vaga> historicoVaga) {
        this.historicoVaga = historicoVaga;
    }

    public String getTamanhoDeCamisa() {
        return tamanhoDeCamisa;
    }

    public void setTamanhoDeCamisa(String tamanhoDeCamisa) {
        this.tamanhoDeCamisa = tamanhoDeCamisa;
    }

    public List<EventoEgresso> getEventosParticipados() {
        return eventosParticipados;
    }

    public void setEventosParticipados(List<EventoEgresso> eventosParticipados) {
        this.eventosParticipados = eventosParticipados;
    }

    public List<DocumentoDoEgresso> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoDoEgresso> documentos) {
        this.documentos = documentos;
    }

    public Boolean getRelocacaoDeVagaEncaminhada() {
        return relocacaoDeVagaEncaminhada;
    }

    public void setRelocacaoDeVagaEncaminhada(Boolean relocacaoDeVagaEncaminhada) {
        this.relocacaoDeVagaEncaminhada = relocacaoDeVagaEncaminhada;
    }

    public Boolean getRelocacaoDeVagaAtendida() {
        return relocacaoDeVagaAtendida;
    }

    public void setRelocacaoDeVagaAtendida(Boolean relocacaoDeVagaAtendida) {
        this.relocacaoDeVagaAtendida = relocacaoDeVagaAtendida;
    }

    public Long getDistanciaEntreMunicipios() {
        return distanciaEntreMunicipios;
    }

    public void setDistanciaEntreMunicipios(Long distanciaEntreMunicipios) {
        this.distanciaEntreMunicipios = distanciaEntreMunicipios;
    }

    public List<CI> getCis() {
        return cis;
    }

    public void setCis(List<CI> cis) {
        this.cis = cis;
    }

    public List<Oficio> getOficios() {
        return oficios;
    }

    public void setOficios(List<Oficio> oficios) {
        this.oficios = oficios;
    }

    public Boolean getEstagioConcluido() {
        return estagioConcluido;
    }

    public void setEstagioConcluido(Boolean estagioConcluido) {
        this.estagioConcluido = estagioConcluido;
    }

    public Boolean getCarteiraAssinada() {
        return carteiraAssinada;
    }

    public void setCarteiraAssinada(Boolean carteiraAssinada) {
        this.carteiraAssinada = carteiraAssinada;
    }

    public Boolean getCtpsPendente() {
        return ctpsPendente;
    }

    public void setCtpsPendente(Boolean ctpsPendente) {
        this.ctpsPendente = ctpsPendente;
    }

    public Boolean getRgPendente() {
        return rgPendente;
    }

    public void setRgPendente(Boolean rgPendente) {
        this.rgPendente = rgPendente;
    }

    public Boolean getCartaCidadaoPendente() {
        return cartaCidadaoPendente;
    }

    public void setCartaCidadaoPendente(Boolean cartaCidadaoPendente) {
        this.cartaCidadaoPendente = cartaCidadaoPendente;
    }

    public Boolean getTituloEleitorPendente() {
        return tituloEleitorPendente;
    }

    public void setTituloEleitorPendente(Boolean tituloEleitorPendente) {
        this.tituloEleitorPendente = tituloEleitorPendente;
    }

    public Boolean getComprovanteVotacaoPendente() {
        return comprovanteVotacaoPendente;
    }

    public void setComprovanteVotacaoPendente(Boolean comprovanteVotacaoPendente) {
        this.comprovanteVotacaoPendente = comprovanteVotacaoPendente;
    }

    public Boolean getDiplomaPendente() {
        return diplomaPendente;
    }

    public void setDiplomaPendente(Boolean diplomaPendente) {
        this.diplomaPendente = diplomaPendente;
    }

    public Boolean getCurriculoPendente() {
        return curriculoPendente;
    }

    public void setCurriculoPendente(Boolean curriculoPendente) {
        this.curriculoPendente = curriculoPendente;
    }

    public Boolean getDadosbancariosPendente() {
        return dadosbancariosPendente;
    }

    public void setDadosbancariosPendente(Boolean dadosbancariosPendente) {
        this.dadosbancariosPendente = dadosbancariosPendente;
    }

    public Boolean getFoto3x4Pendente() {
        return foto3x4Pendente;
    }

    public void setFoto3x4Pendente(Boolean foto3x4Pendente) {
        this.foto3x4Pendente = foto3x4Pendente;
    }

    public Boolean getAsoPendente() {
        return asoPendente;
    }

    public void setAsoPendente(Boolean asoPendente) {
        this.asoPendente = asoPendente;
    }

    public Boolean getCertidaoCasamentoPendente() {
        return certidaoCasamentoPendente;
    }

    public void setCertidaoCasamentoPendente(Boolean certidaoCasamentoPendente) {
        this.certidaoCasamentoPendente = certidaoCasamentoPendente;
    }

    public Boolean getCertidaoNascimentoPendente() {
        return certidaoNascimentoPendente;
    }

    public void setCertidaoNascimentoPendente(Boolean certidaoNascimentoPendente) {
        this.certidaoNascimentoPendente = certidaoNascimentoPendente;
    }

    public Boolean getComprovanteEscolaridadePendente() {
        return comprovanteEscolaridadePendente;
    }

    public void setComprovanteEscolaridadePendente(Boolean comprovanteEscolaridadePendente) {
        this.comprovanteEscolaridadePendente = comprovanteEscolaridadePendente;
    }

    public Boolean getReservistaPendente() {
        return reservistaPendente;
    }

    public void setReservistaPendente(Boolean reservistaPendente) {
        this.reservistaPendente = reservistaPendente;
    }

    public Boolean getCpfPendente() {
        return cpfPendente;
    }

    public void setCpfPendente(Boolean cpfPendente) {
        this.cpfPendente = cpfPendente;
    }

    public Boolean getComprovanteresidenciaPendente() {
        return comprovanteresidenciaPendente;
    }

    public void setComprovanteresidenciaPendente(Boolean comprovanteresidenciaPendente) {
        this.comprovanteresidenciaPendente = comprovanteresidenciaPendente;
    }

    public Boolean getCartaoVacinaPendente() {
        return cartaoVacinaPendente;
    }

    public Boolean getFichaAdmissao() {
        return fichaAdmissao;
    }

    public void setFichaAdmissao(Boolean fichaAdmissao) {
        this.fichaAdmissao = fichaAdmissao;
    }
   
    public void setCartaoVacinaPendente(Boolean cartaoVacinaPendente) {
        this.cartaoVacinaPendente = cartaoVacinaPendente;
    }

    public Boolean getContratoTrabalhoFlem() {
        return contratoTrabalhoFlem;
    }

    public void setContratoTrabalhoFlem(Boolean contratoTrabalhoFlem) {
        this.contratoTrabalhoFlem = contratoTrabalhoFlem;
    }

    public String getEscolaconclusao() {
        return escolaconclusao;
    }

    public void setEscolaconclusao(String escolaconclusao) {
        this.escolaconclusao = escolaconclusao;
    }

    public Boolean getVale_Transporte() {
        return vale_Transporte;
    }

    public void setVale_Transporte(Boolean vale_Transporte) {
        this.vale_Transporte = vale_Transporte;
    }

    public Boolean getVale_Alimentacao() {
        return vale_Alimentacao;
    }

    public void setVale_Alimentacao(Boolean vale_Alimentacao) {
        this.vale_Alimentacao = vale_Alimentacao;
    }

    public Boolean getPlano_Saude() {
        return plano_Saude;
    }

    public void setPlano_Saude(Boolean plano_Saude) {
        this.plano_Saude = plano_Saude;
    }

    public Boolean getCracha() {
        return cracha;
    }

    public void setCracha(Boolean cracha) {
        this.cracha = cracha;
    }

    public List<MaterialEgresso> getMateriais() {
        return materiais;
    }

    public void setMateriais(List<MaterialEgresso> materiais) {
        this.materiais = materiais;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public String getTempoContratacao() {
        return tempoContratacao;
    }

    public void setTempoContratacao(String tempoContratacao) {
        this.tempoContratacao = tempoContratacao;
    }

    public String getMesAnoAdmissao() {
        return mesAnoAdmissao;
    }

    public void setMesAnoAdmissao(String mesAnoAdmissao) {
        this.mesAnoAdmissao = mesAnoAdmissao;
    }

    public String getMesAnoDesligamento() {
        return mesAnoDesligamento;
    }

    public void setMesAnoDesligamento(String mesAnoDesligamento) {
        this.mesAnoDesligamento = mesAnoDesligamento;
    }
    @Transient
    public List<DocumentoDoEgresso> getDocumentosSigilogos(){
        List<DocumentoDoEgresso> lista = new ArrayList<>();
        if( documentos != null && !documentos.isEmpty() ){
            for(DocumentoDoEgresso doc : documentos){
                if( doc.getSigiloso() != null && doc.getSigiloso() ){
                    lista.add(doc);
                }
            }
        }
        
        return lista;
    }
    @Transient
    public List<DocumentoDoEgresso> getDocumentosNaoSigilogos(){
        List<DocumentoDoEgresso> lista = new ArrayList<>();
        if( documentos != null && !documentos.isEmpty() ){
            for(DocumentoDoEgresso doc : documentos){
                if( doc.getSigiloso() == null || !doc.getSigiloso() ){
                    lista.add(doc);
                }
            }
        }
        
        return lista;
    }

    public String getAvaInformacao() {
        return avaInformacao;
    }

    public void setAvaInformacao(String avaInformacao) {
        this.avaInformacao = avaInformacao;
    }

    public String getCursoSuperior() {
        return cursoSuperior;
    }

    public void setCursoSuperior(String cursoSuperior) {
        this.cursoSuperior = cursoSuperior;
    }

    public EnumTipoInstituicaoEnsino getTipoInstituicaoSuperior() {
        return tipoInstituicaoSuperior;
    }

    public void setTipoInstituicaoSuperior(EnumTipoInstituicaoEnsino tipoInstituicaoSuperior) {
        this.tipoInstituicaoSuperior = tipoInstituicaoSuperior;
    }

    public String getNomeInstituicaoSuperior() {
        return nomeInstituicaoSuperior;
    }

    public void setNomeInstituicaoSuperior(String nomeInstituicaoSuperior) {
        this.nomeInstituicaoSuperior = nomeInstituicaoSuperior;
    }

    public String getNomeCursoGraduacao() {
        return nomeCursoGraduacao;
    }

    public void setNomeCursoGraduacao(String nomeCursoGraduacao) {
        this.nomeCursoGraduacao = nomeCursoGraduacao;
    }

    public EnumModalidadeEnsino getModalidadeGraduacao() {
        return modalidadeGraduacao;
    }

    public void setModalidadeGraduacao(EnumModalidadeEnsino modalidadeGraduacao) {
        this.modalidadeGraduacao = modalidadeGraduacao;
    }

    public String getAnoMatricula() {
        return anoMatricula;
    }

    public void setAnoMatricula(String anoMatricula) {
        this.anoMatricula = anoMatricula;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public Boolean getRendaPPEAjuda() {
        return rendaPPEAjuda;
    }

    public void setRendaPPEAjuda(Boolean rendaPPEAjuda) {
        this.rendaPPEAjuda = rendaPPEAjuda;
    }

    public Date getDataRespostaSemestre() {
        return dataRespostaSemestre;
    }

    public void setDataRespostaSemestre(Date dataRespostaSemestre) {
        this.dataRespostaSemestre = dataRespostaSemestre;
    }

    public Date getDataAdmissao() {
        return dataAdmissao;
    }

    public void setDataAdmissao(Date dataAdmissao) {
        this.dataAdmissao = dataAdmissao;
    }

    public String getAnoConclusaoCurso() {
        return anoConclusaoCurso;
    }

    public void setAnoConclusaoCurso(String anoConclusaoCurso) {
        this.anoConclusaoCurso = anoConclusaoCurso;
    }

    public String getCursoSuperiorConcluido() {
        return cursoSuperiorConcluido;
    }

    public void setCursoSuperiorConcluido(String cursoSuperiorConcluido) {
        this.cursoSuperiorConcluido = cursoSuperiorConcluido;
    }

    public String getPretendeFazerCursoSuperior() {
        return pretendeFazerCursoSuperior;
    }

    public void setPretendeFazerCursoSuperior(String pretendeFazerCursoSuperior) {
        this.pretendeFazerCursoSuperior = pretendeFazerCursoSuperior;
    }

    public String getCursoTecnicoProfissionalizante() {
        return cursoTecnicoProfissionalizante;
    }

    public void setCursoTecnicoProfissionalizante(String cursoTecnicoProfissionalizante) {
        this.cursoTecnicoProfissionalizante = cursoTecnicoProfissionalizante;
    }

    public EnumTipoInstituicaoEnsino getTipoInstituicaoTecnica() {
        return tipoInstituicaoTecnica;
    }

    public void setTipoInstituicaoTecnica(EnumTipoInstituicaoEnsino tipoInstituicaoTecnica) {
        this.tipoInstituicaoTecnica = tipoInstituicaoTecnica;
    }

    public String getNomeInstituicaoTecnico() {
        return nomeInstituicaoTecnico;
    }

    public void setNomeInstituicaoTecnico(String nomeInstituicaoTecnico) {
        this.nomeInstituicaoTecnico = nomeInstituicaoTecnico;
    }

    public String getNomeCursoTecnico() {
        return nomeCursoTecnico;
    }

    public void setNomeCursoTecnico(String nomeCursoTecnico) {
        this.nomeCursoTecnico = nomeCursoTecnico;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

}

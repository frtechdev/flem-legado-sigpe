package br.org.flem.primeiroemprego.entity;

import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.dto.TelefoneDTO;
import br.org.flem.primeiroemprego.util.CoreUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.alfredlibrary.validadores.CPF;
import org.apache.commons.validator.GenericValidator;

/**
 *
 * @author emsilva
 */
public class ListaEgressoDTO implements Serializable {

    private Long idEgresso;
    private String nome;
    private String cpf;
    private String rg;
    private RacaCor racaCor;
    private String deficiencia;
    private Date dataNascimento;
    private String cep;
    private String bairro;
    private String endereco;
    private String complemento;
    private String numero;
    private String municipio;
    private String dddTelefone1;
    private String telefone1;
    private String dddTelefone2;
    private String telefone2;
    private String dddCelular;
    private String celular;
    private String dddContato;
    private String contato;
    private String emailParticular;
    private String emailSecundario;
    private List<String> listas = new ArrayList<>();
    private List<String> eventos = new ArrayList<>();
    private String matriculaFlem;
    private Date admissao;
    private String situacaoFLEM;
    private Date rescisao;
    private String observacao;
    private String sexo;
    private String matriculaSAEB;
    private String demandante;
    private String demandanteSigla;
    private String instituicao;
    private String unidadeDeLotacao;
    private String logradouroUnidadeDeLotacao;
    private String bairroUnidadeDeLotacao;
    private String municipioUnidadeDeLotacao;
    private String cepUnidadeDeLotacao;
    private String pontoFocalNaUnidade;
    private String telefone1PontoFocal;
    private String telefone2PontoFocal;
    private String telefone3PontoFocal;
    private String telefone4PontoFocal;
    private String telefone5PontoFocal;
    private String emailPontoFocal;
    private String territorio;
    private String municipioDaVaga;
    private Long distanciaMunicipios;
    private String dataConvocacaoSETRESINE;
    private String remessaSETRESINE;
    private String dataRemessaSETRESINE;
    private String mesRemessaSETRESINE;
    private String categoria;
    private String corCategoria;
    private String situacao;
    private String corSituacao;
    private String anamnese;
    private Date dataInicioAtividades;
    private String publicacaoDiarioOficial;
    private String dataDeEnvioDaSituacaoParaSETRESINE;
    private String formacao;
    private String eixoDeFormacao;
    private String municipioEscola;
    private String valeAlimentacao;
    private String valeTransporte;
    private Boolean aso;
    private String tamanhoDeCamisa;
    private Date dataRecebimentoFardamento;
    private String escolaconclusao;

    private Boolean fichaAdmissao;
    private Boolean ctpsPendente;
    private Boolean cpfPendente;
    private Boolean comprovanteresidenciaPendente;
    private Boolean rgPendente;
    private Boolean cartaCidadaoPendente;
    private Boolean tituloEleitorPendente;
    private Boolean comprovanteVotacaoPendente;
    private Boolean diplomaPendente;
    private Boolean curriculoPendente;
    private Boolean dadosbancariosPendente;
    private Boolean foto3x4Pendente;
    private Boolean asoPendente;
    private Boolean certidaoCasamentoPendente;
    private Boolean certidaoNascimentoPendente;
    private Boolean comprovanteEscolaridadePendente;
    private Boolean reservistaPendente;
    private Boolean cartaoVacinaPendente;
    private Boolean contratoTrabalhoFlem;
    //Beneficios
    private Boolean vale_Transporte;
    private Boolean vale_Alimentacao;
    private Boolean plano_Saude;
    private Boolean cracha;
    private Boolean estagioConcluido;
    private Boolean carteiraAssinada;
    private String pis;
    private String ctps;
    private String escRegional;

    private String cursoSuperiorAndamento;
    private String cursoSuperiorConcluido;
    private EnumTipoInstituicaoEnsino tipoInstituicaoSuperior;
    private String nomeInstituicaoSuperior;
    private String nomeCursoGraduacao;
    private EnumModalidadeEnsino modalidadeGraduacao;
    private String anoMatricula;
    private String anoConclusaoCurso;
    private String semestre;
    private Boolean rendaPPEAjuda;
    private Date dataRespostaSemestre;
    private String pretendeFazerCursoSuperior;
    private String cursoTecnicoProfissionalizante;
    private EnumTipoInstituicaoEnsino tipoInstituicaoTecnica;
    private String nomeInstituicaoTecnico;
    private String nomeCursoTecnico;
    private List<TelefoneDTO> telefones;

    public ListaEgressoDTO() {

    }

    public ListaEgressoDTO(Long idEgresso, String nome, String cpf, String rg, RacaCor racaCor, String deficiencia, Date dataNascimento, String cep, String bairro, String endereco, String complemento, String numero, String municipio, String dddTelefone1, String telefone1, String dddTelefone2, String telefone2, String dddCelular, String celular, String dddContato, String contato, String emailParticular, String emailSecundario,
            String matriculaFlem, String observacao, String sexo, String matriculaSAEB, String demandante, String demandanteSigla, String instituicao, String unidadeDeLotacao, String logradouroUnidadeDeLotacao, String bairroUnidadeDeLotacao, String municipioUnidadeDeLotacao, String cepUnidadeDeLotacao, String pontoFocalNaUnidade, String telefone1PontoFocal, String telefone2PontoFocal, String telefone3PontoFocal, String telefone4PontoFocal,
            String telefone5PontoFocal, String emailPontoFocal, String territorio, String municipioDaVaga, Long distanciaMunicipios, String dataConvocacaoSETRESINE, String remessaSETRESINE, String dataRemessaSETRESINE, String mesRemessaSETRESINE, String categoria, String corCategoria, String situacao, String corSituacao, String anamnese, Date dataInicioAtividades, String publicacaoDiarioOficial, String dataDeEnvioDaSituacaoParaSETRESINE,
            String formacao, String eixoDeFormacao, String municipioEscola, String valeAlimentacao, String valeTransporte, Boolean aso, String tamanhoDeCamisa, Date dataRecebimentoFardamento, String escolaconclusao, Boolean fichaAdmissao, Boolean ctpsPendente, Boolean cpfPendente, Boolean comprovanteresidenciaPendente, Boolean rgPendente, Boolean cartaCidadaoPendente, Boolean tituloEleitorPendente, Boolean comprovanteVotacaoPendente, Boolean diplomaPendente,
            Boolean curriculoPendente, Boolean dadosbancariosPendente, Boolean foto3x4Pendente, Boolean asoPendente, Boolean certidaoCasamentoPendente, Boolean certidaoNascimentoPendente, Boolean comprovanteEscolaridadePendente, Boolean reservistaPendente, Boolean cartaoVacinaPendente, Boolean contratoTrabalhoFlem, Boolean vale_Transporte, Boolean vale_Alimentacao, Boolean plano_Saude, Boolean cracha, Boolean estagioConcluido, Boolean carteiraAssinada,
            String pis, String ctps, String escRegional, Date admissao) {
        this.idEgresso = idEgresso;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.racaCor = racaCor;
        this.deficiencia = deficiencia;
        this.dataNascimento = dataNascimento;
        this.cep = cep;
        this.bairro = bairro;
        this.endereco = endereco;
        this.complemento = complemento;
        this.numero = numero;
        this.municipio = municipio;
        this.dddTelefone1 = dddTelefone1;
        this.telefone1 = telefone1;
        this.dddTelefone2 = dddTelefone2;
        this.telefone2 = telefone2;
        this.dddCelular = dddCelular;
        this.celular = celular;
        this.dddContato = dddContato;
        this.contato = contato;
        this.emailParticular = emailParticular;
        this.emailSecundario = emailSecundario;
        this.matriculaFlem = matriculaFlem;
        this.observacao = observacao;
        this.sexo = sexo;
        this.matriculaSAEB = matriculaSAEB;
        this.demandante = demandante;
        this.demandanteSigla = demandanteSigla;
        this.instituicao = instituicao;
        this.unidadeDeLotacao = unidadeDeLotacao;
        this.logradouroUnidadeDeLotacao = logradouroUnidadeDeLotacao;
        this.bairroUnidadeDeLotacao = bairroUnidadeDeLotacao;
        this.municipioUnidadeDeLotacao = municipioUnidadeDeLotacao;
        this.cepUnidadeDeLotacao = cepUnidadeDeLotacao;
        this.pontoFocalNaUnidade = pontoFocalNaUnidade;
        this.telefone1PontoFocal = telefone1PontoFocal;
        this.telefone2PontoFocal = telefone2PontoFocal;
        this.telefone3PontoFocal = telefone3PontoFocal;
        this.telefone4PontoFocal = telefone4PontoFocal;
        this.telefone5PontoFocal = telefone5PontoFocal;
        this.emailPontoFocal = emailPontoFocal;
        this.territorio = territorio;
        this.municipioDaVaga = municipioDaVaga;
        this.distanciaMunicipios = distanciaMunicipios;
        this.dataConvocacaoSETRESINE = dataConvocacaoSETRESINE;
        this.remessaSETRESINE = remessaSETRESINE;
        this.dataRemessaSETRESINE = dataRemessaSETRESINE;
        this.mesRemessaSETRESINE = mesRemessaSETRESINE;
        this.categoria = categoria;
        this.corCategoria = corCategoria;
        this.situacao = situacao;
        this.corSituacao = corSituacao;
        this.anamnese = anamnese;
        this.dataInicioAtividades = dataInicioAtividades;
        this.publicacaoDiarioOficial = publicacaoDiarioOficial;
        this.dataDeEnvioDaSituacaoParaSETRESINE = dataDeEnvioDaSituacaoParaSETRESINE;
        this.formacao = formacao;
        this.eixoDeFormacao = eixoDeFormacao;
        this.municipioEscola = municipioEscola;
        this.valeAlimentacao = valeAlimentacao;
        this.valeTransporte = valeTransporte;
        this.aso = aso;
        this.tamanhoDeCamisa = tamanhoDeCamisa;
        this.dataRecebimentoFardamento = dataRecebimentoFardamento;
        this.escolaconclusao = escolaconclusao;

        this.fichaAdmissao = fichaAdmissao;
        this.ctpsPendente = ctpsPendente;
        this.cpfPendente = cpfPendente;
        this.comprovanteresidenciaPendente = comprovanteresidenciaPendente;
        this.rgPendente = rgPendente;
        this.cartaCidadaoPendente = cartaCidadaoPendente;
        this.tituloEleitorPendente = tituloEleitorPendente;
        this.comprovanteVotacaoPendente = comprovanteVotacaoPendente;
        this.diplomaPendente = diplomaPendente;
        this.curriculoPendente = curriculoPendente;
        this.dadosbancariosPendente = dadosbancariosPendente;
        this.foto3x4Pendente = foto3x4Pendente;
        this.asoPendente = asoPendente;
        this.certidaoCasamentoPendente = certidaoCasamentoPendente;
        this.certidaoNascimentoPendente = certidaoNascimentoPendente;
        this.comprovanteEscolaridadePendente = comprovanteEscolaridadePendente;
        this.reservistaPendente = reservistaPendente;
        this.cartaoVacinaPendente = cartaoVacinaPendente;
        this.contratoTrabalhoFlem = contratoTrabalhoFlem;
        this.vale_Transporte = vale_Transporte;
        this.vale_Alimentacao = vale_Alimentacao;
        this.plano_Saude = plano_Saude;
        this.cracha = cracha;
        this.estagioConcluido = estagioConcluido;
        this.carteiraAssinada = carteiraAssinada;
        this.pis = pis;
        this.ctps = ctps;
        this.escRegional = escRegional;
        this.admissao = admissao;
    }

    public ListaEgressoDTO(Long idEgresso, String nome, String cpf, String rg, RacaCor racaCor, String deficiencia, Date dataNascimento, String cep, String bairro, String endereco, String complemento, String numero, String municipio, String dddTelefone1, String telefone1, String dddTelefone2, String telefone2, String dddCelular, String celular, String dddContato, String contato, String emailParticular, String emailSecundario,
            String matriculaFlem, String observacao, String sexo, String matriculaSAEB, String demandante, String demandanteSigla, String instituicao, String unidadeDeLotacao, String logradouroUnidadeDeLotacao, String bairroUnidadeDeLotacao, String municipioUnidadeDeLotacao, String cepUnidadeDeLotacao, String pontoFocalNaUnidade, String telefone1PontoFocal, String telefone2PontoFocal, String telefone3PontoFocal, String telefone4PontoFocal,
            String telefone5PontoFocal, String emailPontoFocal, String territorio, String municipioDaVaga, Long distanciaMunicipios, String dataConvocacaoSETRESINE, String remessaSETRESINE, String dataRemessaSETRESINE, String mesRemessaSETRESINE, String categoria, String corCategoria, String situacao, String corSituacao, String anamnese, Date dataInicioAtividades, String publicacaoDiarioOficial, String dataDeEnvioDaSituacaoParaSETRESINE,
            String formacao, String eixoDeFormacao, String municipioEscola, String valeAlimentacao, String valeTransporte, Boolean aso, String tamanhoDeCamisa, Date dataRecebimentoFardamento, String escolaconclusao, Boolean fichaAdmissao, Boolean ctpsPendente, Boolean cpfPendente, Boolean comprovanteresidenciaPendente, Boolean rgPendente, Boolean cartaCidadaoPendente, Boolean tituloEleitorPendente, Boolean comprovanteVotacaoPendente, Boolean diplomaPendente,
            Boolean curriculoPendente, Boolean dadosbancariosPendente, Boolean foto3x4Pendente, Boolean asoPendente, Boolean certidaoCasamentoPendente, Boolean certidaoNascimentoPendente, Boolean comprovanteEscolaridadePendente, Boolean reservistaPendente, Boolean cartaoVacinaPendente, Boolean contratoTrabalhoFlem, Boolean vale_Transporte, Boolean vale_Alimentacao, Boolean plano_Saude, Boolean cracha, Boolean estagioConcluido, Boolean carteiraAssinada,
            String pis, String ctps, String escRegional, String cursoSuperiorAndamento, EnumTipoInstituicaoEnsino tipoInstituicaoSuperior, String nomeInstituicaoSuperior, String nomeCursoGraduacao, EnumModalidadeEnsino modalidadeGraduacao, String anoMatricula, String semestre, Boolean rendaPPEAjuda, Date dataRespostaSemestre, Date admissao,
            String cursoSuperiorConcluido, String anoConclusaoCurso, String pretendeFazerCursoSuperior, String cursoTecnicoProfissionalizante, EnumTipoInstituicaoEnsino tipoInstituicaoTecnica, String nomeInstituicaoTecnico, String nomeCursoTecnico) {
        this.idEgresso = idEgresso;
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.racaCor = racaCor;
        this.deficiencia = deficiencia;
        this.dataNascimento = dataNascimento;
        this.cep = cep;
        this.bairro = bairro;
        this.endereco = endereco;
        this.complemento = complemento;
        this.numero = numero;
        this.municipio = municipio;
        this.dddTelefone1 = dddTelefone1;
        this.telefone1 = telefone1;
        this.dddTelefone2 = dddTelefone2;
        this.telefone2 = telefone2;
        this.dddCelular = dddCelular;
        this.celular = celular;
        this.dddContato = dddContato;
        this.contato = contato;
        this.emailParticular = emailParticular;
        this.emailSecundario = emailSecundario;
        this.matriculaFlem = matriculaFlem;
        this.admissao = admissao;
        this.situacaoFLEM = situacaoFLEM;
        this.rescisao = rescisao;
        this.observacao = observacao;
        this.sexo = sexo;
        this.matriculaSAEB = matriculaSAEB;
        this.demandante = demandante;
        this.demandanteSigla = demandanteSigla;
        this.instituicao = instituicao;
        this.unidadeDeLotacao = unidadeDeLotacao;
        this.logradouroUnidadeDeLotacao = logradouroUnidadeDeLotacao;
        this.bairroUnidadeDeLotacao = bairroUnidadeDeLotacao;
        this.municipioUnidadeDeLotacao = municipioUnidadeDeLotacao;
        this.cepUnidadeDeLotacao = cepUnidadeDeLotacao;
        this.pontoFocalNaUnidade = pontoFocalNaUnidade;
        this.telefone1PontoFocal = telefone1PontoFocal;
        this.telefone2PontoFocal = telefone2PontoFocal;
        this.telefone3PontoFocal = telefone3PontoFocal;
        this.telefone4PontoFocal = telefone4PontoFocal;
        this.telefone5PontoFocal = telefone5PontoFocal;
        this.emailPontoFocal = emailPontoFocal;
        this.territorio = territorio;
        this.municipioDaVaga = municipioDaVaga;
        this.distanciaMunicipios = distanciaMunicipios;
        this.dataConvocacaoSETRESINE = dataConvocacaoSETRESINE;
        this.remessaSETRESINE = remessaSETRESINE;
        this.dataRemessaSETRESINE = dataRemessaSETRESINE;
        this.mesRemessaSETRESINE = mesRemessaSETRESINE;
        this.categoria = categoria;
        this.corCategoria = corCategoria;
        this.situacao = situacao;
        this.corSituacao = corSituacao;
        this.anamnese = anamnese;
        this.dataInicioAtividades = dataInicioAtividades;
        this.publicacaoDiarioOficial = publicacaoDiarioOficial;
        this.dataDeEnvioDaSituacaoParaSETRESINE = dataDeEnvioDaSituacaoParaSETRESINE;
        this.formacao = formacao;
        this.eixoDeFormacao = eixoDeFormacao;
        this.municipioEscola = municipioEscola;
        this.valeAlimentacao = valeAlimentacao;
        this.valeTransporte = valeTransporte;
        this.aso = aso;
        this.tamanhoDeCamisa = tamanhoDeCamisa;
        this.dataRecebimentoFardamento = dataRecebimentoFardamento;
        this.escolaconclusao = escolaconclusao;
        this.fichaAdmissao = fichaAdmissao;
        this.ctpsPendente = ctpsPendente;
        this.cpfPendente = cpfPendente;
        this.comprovanteresidenciaPendente = comprovanteresidenciaPendente;
        this.rgPendente = rgPendente;
        this.cartaCidadaoPendente = cartaCidadaoPendente;
        this.tituloEleitorPendente = tituloEleitorPendente;
        this.comprovanteVotacaoPendente = comprovanteVotacaoPendente;
        this.diplomaPendente = diplomaPendente;
        this.curriculoPendente = curriculoPendente;
        this.dadosbancariosPendente = dadosbancariosPendente;
        this.foto3x4Pendente = foto3x4Pendente;
        this.asoPendente = asoPendente;
        this.certidaoCasamentoPendente = certidaoCasamentoPendente;
        this.certidaoNascimentoPendente = certidaoNascimentoPendente;
        this.comprovanteEscolaridadePendente = comprovanteEscolaridadePendente;
        this.reservistaPendente = reservistaPendente;
        this.cartaoVacinaPendente = cartaoVacinaPendente;
        this.contratoTrabalhoFlem = contratoTrabalhoFlem;
        this.vale_Transporte = vale_Transporte;
        this.vale_Alimentacao = vale_Alimentacao;
        this.plano_Saude = plano_Saude;
        this.cracha = cracha;
        this.estagioConcluido = estagioConcluido;
        this.carteiraAssinada = carteiraAssinada;
        this.pis = pis;
        this.ctps = ctps;
        this.escRegional = escRegional;
        this.cursoSuperiorAndamento = cursoSuperiorAndamento;
        this.tipoInstituicaoSuperior = tipoInstituicaoSuperior;
        this.nomeInstituicaoSuperior = nomeInstituicaoSuperior;
        this.nomeCursoGraduacao = nomeCursoGraduacao;
        this.modalidadeGraduacao = modalidadeGraduacao;
        this.anoMatricula = anoMatricula;
        this.semestre = semestre;
        this.rendaPPEAjuda = rendaPPEAjuda;
        this.dataRespostaSemestre = dataRespostaSemestre;
        this.cursoSuperiorConcluido = cursoSuperiorConcluido;
        this.tipoInstituicaoSuperior = tipoInstituicaoSuperior;
        this.nomeInstituicaoSuperior = nomeInstituicaoSuperior;
        this.nomeCursoGraduacao = nomeCursoGraduacao;
        this.modalidadeGraduacao = modalidadeGraduacao;
        this.anoMatricula = anoMatricula;
        this.anoConclusaoCurso = anoConclusaoCurso;
        this.pretendeFazerCursoSuperior = pretendeFazerCursoSuperior;
        this.cursoTecnicoProfissionalizante = cursoTecnicoProfissionalizante;
        this.tipoInstituicaoTecnica = tipoInstituicaoTecnica;
        this.nomeInstituicaoTecnico = nomeInstituicaoTecnico;
        this.nomeCursoTecnico = nomeCursoTecnico;
    }
    public ListaEgressoDTO(Long idEgresso, String nome, String cpf, String matriculaFlem, String demandante, String municipioDaVaga, String categoria, String corCategoria, String situacao, String corSituacao, String formacao, String escRegional) {
        this.idEgresso = idEgresso;
        this.nome = nome;
        this.cpf = cpf;
        this.demandante = demandante;
        this.matriculaFlem = matriculaFlem;
        this.municipioDaVaga = municipioDaVaga;
        this.categoria = categoria;
        this.corCategoria = corCategoria;
        this.situacao = situacao;
        this.corSituacao = corSituacao;
        this.formacao = formacao;
        this.escRegional = escRegional;
    }

    public Long getIdEgresso() {
        return idEgresso;
    }

    public void setIdEgresso(Long idEgresso) {
        this.idEgresso = idEgresso;
    }

    public boolean isCpfValido() {
        return cpf != null ? CPF.isValido(cpf) : false;
    }

    public boolean isEmailValido() {
        return GenericValidator.isEmail(emailParticular);
    }

    public boolean isEmailSecundarioValido() {
        return GenericValidator.isEmail(emailSecundario);
    }

    public Float getIdade() {
        if (dataNascimento == null) {
            return null;
        }
        return Data.anoDiffDecimal(dataNascimento, new Date());
    }

    public boolean isDivergenciaMunicipio() {
        if (municipio != null && municipioDaVaga != null) {
            return !municipioDaVaga.equals(municipio);
        }
        if (municipioDaVaga == null && municipio == null) {
            return false;
        }
        return true;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public String getDeficiencia() {
        return deficiencia;
    }

    public void setDeficiencia(String deficiencia) {
        this.deficiencia = deficiencia;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
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

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
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

    public List<String> getListas() {
        return listas;
    }

    public void setListas(List<String> listas) {
        this.listas = listas;
    }

    public List<String> getEventos() {
        return eventos;
    }

    public void setEventos(List<String> eventos) {
        this.eventos = eventos;
    }

    public String getMatriculaFlem() {
        return matriculaFlem;
    }

    public void setMatriculaFlem(String matriculaFlem) {
        this.matriculaFlem = matriculaFlem;
    }

    public Date getAdmissao() {
        return admissao;
    }

    public void setAdmissao(Date admissao) {
        this.admissao = admissao;
    }

    public String getSituacaoFLEM() {
        return situacaoFLEM;
    }

    public void setSituacaoFLEM(String situacaoFLEM) {
        this.situacaoFLEM = situacaoFLEM;
    }

    public Date getRescisao() {
        return rescisao;
    }

    public void setRescisao(Date rescisao) {
        this.rescisao = rescisao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
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

    public String getDemandante() {
        return demandante;
    }

    public void setDemandante(String demandante) {
        this.demandante = demandante;
    }

    public String getDemandanteSigla() {
        return demandanteSigla;
    }

    public void setDemandanteSigla(String demandanteSigla) {
        this.demandanteSigla = demandanteSigla;
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

    public String getMunicipioUnidadeDeLotacao() {
        return municipioUnidadeDeLotacao;
    }

    public void setMunicipioUnidadeDeLotacao(String municipioUnidadeDeLotacao) {
        this.municipioUnidadeDeLotacao = municipioUnidadeDeLotacao;
    }

    public String getCepUnidadeDeLotacao() {
        return cepUnidadeDeLotacao;
    }

    public void setCepUnidadeDeLotacao(String cepUnidadeDeLotacao) {
        this.cepUnidadeDeLotacao = cepUnidadeDeLotacao;
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

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public String getMunicipioDaVaga() {
        return municipioDaVaga;
    }

    public void setMunicipioDaVaga(String municipioDaVaga) {
        this.municipioDaVaga = municipioDaVaga;
    }

    public Long getDistanciaMunicipios() {
        return distanciaMunicipios;
    }

    public void setDistanciaMunicipios(Long distanciaMunicipios) {
        this.distanciaMunicipios = distanciaMunicipios;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCorCategoria() {
        return corCategoria;
    }

    public void setCorCategoria(String corCategoria) {
        this.corCategoria = corCategoria;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public String getCorSituacao() {
        return corSituacao;
    }

    public void setCorSituacao(String corSituacao) {
        this.corSituacao = corSituacao;
    }

    public String getAnamnese() {
        return anamnese;
    }

    public void setAnamnese(String anamnese) {
        this.anamnese = anamnese;
    }

    public Date getDataInicioAtividades() {
        return dataInicioAtividades;
    }

    public void setDataInicioAtividades(Date dataInicioAtividades) {
        this.dataInicioAtividades = dataInicioAtividades;
    }

    public String getPublicacaoDiarioOficial() {
        return publicacaoDiarioOficial;
    }

    public void setPublicacaoDiarioOficial(String publicacaoDiarioOficial) {
        this.publicacaoDiarioOficial = publicacaoDiarioOficial;
    }

    public String getDataDeEnvioDaSituacaoParaSETRESINE() {
        return dataDeEnvioDaSituacaoParaSETRESINE;
    }

    public void setDataDeEnvioDaSituacaoParaSETRESINE(String dataDeEnvioDaSituacaoParaSETRESINE) {
        this.dataDeEnvioDaSituacaoParaSETRESINE = dataDeEnvioDaSituacaoParaSETRESINE;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getEixoDeFormacao() {
        return eixoDeFormacao;
    }

    public void setEixoDeFormacao(String eixoDeFormacao) {
        this.eixoDeFormacao = eixoDeFormacao;
    }

    public String getMunicipioEscola() {
        return municipioEscola;
    }

    public void setMunicipioEscola(String municipioEscola) {
        this.municipioEscola = municipioEscola;
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

    public String getTamanhoDeCamisa() {
        return tamanhoDeCamisa;
    }

    public void setTamanhoDeCamisa(String tamanhoDeCamisa) {
        this.tamanhoDeCamisa = tamanhoDeCamisa;
    }

    public Date getDataRecebimentoFardamento() {
        return dataRecebimentoFardamento;
    }

    public void setDataRecebimentoFardamento(Date dataRecebimentoFardamento) {
        this.dataRecebimentoFardamento = dataRecebimentoFardamento;
    }

    public String getEscolaconclusao() {
        return escolaconclusao;
    }

    public void setEscolaconclusao(String escolaconclusao) {
        this.escolaconclusao = escolaconclusao;
    }

    public Boolean getFichaAdmissao() {
        return fichaAdmissao;
    }

    public void setFichaAdmissao(Boolean fichaAdmissao) {
        this.fichaAdmissao = fichaAdmissao;
    }

    public Boolean getCtpsPendente() {
        return ctpsPendente;
    }

    public void setCtpsPendente(Boolean ctpsPendente) {
        this.ctpsPendente = ctpsPendente;
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

    public Boolean getCartaoVacinaPendente() {
        return cartaoVacinaPendente;
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

    public String getTempoContratacao() {
        if (admissao != null) {
            return "" + CoreUtil.nvl(Data.mesDiff(admissao, new Date()), "");
        }
        return "";
    }

    public String getMesAnoAdmissao() {
        String mesAno = "";
        if (admissao != null) {
            mesAno = Data.formataData(admissao, "MM/yyyy");
        }
        return mesAno;
    }

    public String getMesAnoDesligamento() {
        String mesAno = "";
        if (rescisao != null) {
            mesAno = Data.formataData(rescisao, "MM/yyyy");
        }
        return mesAno;
    }

    public String getPis() {
        return pis;
    }

    public void setPis(String pis) {
        this.pis = pis;
    }

    public String getCtps() {
        return ctps;
    }

    public void setCtps(String ctps) {
        this.ctps = ctps;
    }

    public String getEscRegional() {
        return escRegional;
    }

    public void setEscRegional(String escRegional) {
        this.escRegional = escRegional;
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

    public String getCursoSuperiorAndamento() {
        return cursoSuperiorAndamento;
    }

    public void setCursoSuperiorAndamento(String cursoSuperiorAndamento) {
        this.cursoSuperiorAndamento = cursoSuperiorAndamento;
    }

    public String getCursoSuperiorConcluido() {
        return cursoSuperiorConcluido;
    }

    public void setCursoSuperiorConcluido(String cursoSuperiorConcluido) {
        this.cursoSuperiorConcluido = cursoSuperiorConcluido;
    }

    public String getAnoConclusaoCurso() {
        return anoConclusaoCurso;
    }

    public void setAnoConclusaoCurso(String anoConclusaoCurso) {
        this.anoConclusaoCurso = anoConclusaoCurso;
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

    public List<TelefoneDTO> getTelefones() {
        return telefones;
    }

    public void setTelefones(List<TelefoneDTO> telefones) {
        this.telefones = telefones;
    }
}

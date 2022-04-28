package br.org.flem.primeiroemprego.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@SessionScoped
public class EstadoColunasEgressoDTO implements Serializable{

    //A ordem aqui precisa ser a mesma da listagem de Egressos

    private boolean btnCadastro = false;
    private boolean checkbox = false;
    private boolean n = false;
    private boolean nome = true;
    private boolean cpf = true;
    private boolean cpfValido = false;
    private boolean rg = false;
    private boolean racaCor=false;
    private boolean deficiencia = false;
    private boolean dataNascimento = false;
    private boolean idade = false;
    private boolean cep = false;
    private boolean bairro = false;
    private boolean endereco = false;
    private boolean complemento = false;
    private boolean numero = false;
    private boolean municipio = false;
    private boolean dddTelefone1 = false;
    private boolean telefone1 = false;
    private boolean dddTelefone2 = false;
    private boolean telefone2 = false;
    private boolean dddCelular = false;
    private boolean celular = false;
    private boolean dddContato = false;
    private boolean contato = false;
    private boolean emailParticular = false;
    private boolean emailValido = false;
    private boolean emailSecundario = false;
    private boolean emailSecundarioValido = false;
    private boolean listas = false;
    private boolean eventos = false;
    private boolean matriculaFLEM = true;    
    private boolean admissao = false;
    private boolean rescisao = false;
    private boolean observacao = false;
    private boolean sexo = false;
    private boolean matriculaSAEB = false;
    private boolean demandante = true;
    private boolean demandanteSigla = false;
    private boolean instituicao = false;
    private boolean unidadeDeLotacao = false;
    private boolean logradouroUnidadeDeLotacao = false;
    private boolean bairroUnidadeDeLotacao = false;
    private boolean municipioUnidadeDeLotacao = false;
    private boolean cepUnidadeDeLotacao = false;
    private boolean pontoFocalNaUnidade = false;
    private boolean telefone1PontoFocal = false;
    private boolean telefone2PontoFocal = false;
    private boolean telefone3PontoFocal = false;
    private boolean telefone4PontoFocal = false;
    private boolean telefone5PontoFocal = false;
    private boolean emailPontoFocal = false;
    private boolean territorio = false;
    private boolean municipioDaVaga = true;
    private boolean divergenciaMunicipio = false;
    private boolean distanciaMunicipios = false;
    private boolean dataConvocacaoSETRESINE = false;
    private boolean remessaSETRESINE = false;
    private boolean dataRemessaSETRESINE = false;
    private boolean mesRemessaSETRESINE = false;
    private boolean categoria = true;
    private boolean situacao = true;
    private boolean anamnese = false;
    private boolean dataInicioAtividades = false;
    private boolean publicacaoDiarioOficial = false;
    private boolean dataDeEnvioDaSituacaoParaSETRESINE = false;
    private boolean formacao = false;
    private boolean eixoDeFormacao = false;
    private boolean municipioEscola = false;
    private boolean valeAlimentacao = false;
    private boolean valeTransporte = false;
    private boolean aso = false;
    private boolean tamanhoDeCamisa = false;
    private boolean dataRecebimentoFardamento = false;
    private boolean escolaconlusao = false;

    private boolean fichaAdmissao=false;
    private boolean ctpsPendente=false;
    private boolean cpfPendente=false;
    private boolean comprovanteresidenciaPendente=false;
    private boolean rgPendente=false;
    private boolean cartaCidadaoPendente=false;
    private boolean tituloEleitorPendente=false;
    private boolean comprovanteVotacaoPendente=false;
    private boolean diplomaPendente=false;
    private boolean curriculoPendente=false;
    //private boolean dadosbancariosPendente=false;
    private boolean foto3x4Pendente=false;
    private boolean asoPendente=false;
    private boolean certidaoCasamentoPendente=false;
    private boolean certidaoNascimentoPendente=false;
    private boolean comprovanteEscolaridadePendente=false;
    private boolean reservistaPendente=false;
    private boolean cartaoVacinaPendente=false;
    private boolean contratoTrabalhoFlem=false;
    //Beneficios
    private boolean vale_Transporte=false;
    private boolean vale_Alimentacao=false;
    private boolean plano_Saude=false;
    private boolean cracha=false;
    private boolean carteiraAssinada=false;
    private boolean estagioConcluido=false;
    
    private boolean tempoContratacao = false;
    private boolean mesAnoAdmissao = false;
    private boolean mesAnoDesligamento = false;
    private boolean ctps = false;
    private boolean pis = false;
    private boolean escRegional = true;
    private boolean cursoSuperior;
    private boolean tipoInstituicaoSuperior;
    private boolean nomeInstituicaoSuperior;
    private boolean nomeCursoGraduacao;
    private boolean modalidadeGraduacao;
    private boolean anoMatricula;
    private boolean semestre;
    private boolean rendaPPEAjuda;
    private boolean dataRespostaSemestre;
    private boolean cursoSuperiorConcluido;
    private boolean pretendeFazerCursoSuperior;
    private boolean cursoTecnicoProfissionalizante;
    private boolean tipoInstituicaoTecnica;
    private boolean nomeInstituicaoTecnico;
    private boolean nomeCursoTecnico;
    
    public boolean isBtnCadastro() {
        return btnCadastro;
    }

    public void setBtnCadastro(boolean btnCadastro) {
        this.btnCadastro = btnCadastro;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public boolean isN() {
        return n;
    }
    
    public void set(Integer pos,boolean visibilidade){
        try{
            Field[] campos = getClass().getDeclaredFields();
            if(pos < campos.length){
                Field field = getClass().getDeclaredFields()[pos];
                field.setBoolean(this, visibilidade);
            }
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void exibirTodas(){
        try{
            Field[] campos = getClass().getDeclaredFields();
            for(int i=0; i < campos.length; i++){
                campos[i].setBoolean(this, true);
            }
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void esconderTodas(){
        try{
            Field[] campos = getClass().getDeclaredFields();
            for (Field campo : campos) {
                campo.setBoolean(this, false);
            }
        }catch(Exception e){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void setN(boolean n) {
        this.n = n;
    }

    public boolean isNome() {
        return nome;
    }

    public void setNome(boolean nome) {
        this.nome = nome;
    }

    public boolean isCpf() {
        return cpf;
    }

    public void setCpf(boolean cpf) {
        this.cpf = cpf;
    }

    public boolean isCpfValido() {
        return cpfValido;
    }

    public void setCpfValido(boolean cpfValido) {
        this.cpfValido = cpfValido;
    }

    public boolean isRg() {
        return rg;
    }

    public void setRg(boolean rg) {
        this.rg = rg;
    }

    public boolean isRacaCor() {
        return racaCor;
    }

    public void setRacaCor(boolean racaCor) {
        this.racaCor = racaCor;
    }

    public boolean isDeficiencia() {
        return deficiencia;
    }

    public void setDeficiencia(boolean deficiencia) {
        this.deficiencia = deficiencia;
    }

    public boolean isDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(boolean dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isIdade() {
        return idade;
    }

    public void setIdade(boolean idade) {
        this.idade = idade;
    }

    public boolean isCep() {
        return cep;
    }

    public void setCep(boolean cep) {
        this.cep = cep;
    }

    public boolean isBairro() {
        return bairro;
    }

    public void setBairro(boolean bairro) {
        this.bairro = bairro;
    }

    public boolean isEndereco() {
        return endereco;
    }

    public void setEndereco(boolean endereco) {
        this.endereco = endereco;
    }

    public boolean isComplemento() {
        return complemento;
    }

    public void setComplemento(boolean complemento) {
        this.complemento = complemento;
    }

    public boolean isNumero() {
        return numero;
    }

    public void setNumero(boolean numero) {
        this.numero = numero;
    }

    public boolean isMunicipio() {
        return municipio;
    }

    public void setMunicipio(boolean municipio) {
        this.municipio = municipio;
    }

    public boolean isDddTelefone1() {
        return dddTelefone1;
    }

    public void setDddTelefone1(boolean dddTelefone1) {
        this.dddTelefone1 = dddTelefone1;
    }

    public boolean isTelefone1() {
        return telefone1;
    }

    public void setTelefone1(boolean telefone1) {
        this.telefone1 = telefone1;
    }

    public boolean isDddTelefone2() {
        return dddTelefone2;
    }

    public void setDddTelefone2(boolean dddTelefone2) {
        this.dddTelefone2 = dddTelefone2;
    }

    public boolean isTelefone2() {
        return telefone2;
    }

    public void setTelefone2(boolean telefone2) {
        this.telefone2 = telefone2;
    }

    public boolean isDddCelular() {
        return dddCelular;
    }

    public void setDddCelular(boolean dddCelular) {
        this.dddCelular = dddCelular;
    }

    public boolean isCelular() {
        return celular;
    }

    public void setCelular(boolean celular) {
        this.celular = celular;
    }

    public boolean isDddContato() {
        return dddContato;
    }

    public void setDddContato(boolean dddContato) {
        this.dddContato = dddContato;
    }

    public boolean isContato() {
        return contato;
    }

    public void setContato(boolean contato) {
        this.contato = contato;
    }

    public boolean isEmailParticular() {
        return emailParticular;
    }

    public void setEmailParticular(boolean emailParticular) {
        this.emailParticular = emailParticular;
    }

    public boolean isEmailValido() {
        return emailValido;
    }

    public void setEmailValido(boolean emailValido) {
        this.emailValido = emailValido;
    }

    public boolean isEmailSecundario() {
        return emailSecundario;
    }

    public void setEmailSecundario(boolean emailSecundario) {
        this.emailSecundario = emailSecundario;
    }

    public boolean isEmailSecundarioValido() {
        return emailSecundarioValido;
    }

    public void setEmailSecundarioValido(boolean emailSecundarioValido) {
        this.emailSecundarioValido = emailSecundarioValido;
    }

    public boolean isListas() {
        return listas;
    }

    public void setListas(boolean listas) {
        this.listas = listas;
    }

    public boolean isEventos() {
        return eventos;
    }

    public void setEventos(boolean eventos) {
        this.eventos = eventos;
    }

    public boolean isMatriculaFLEM() {
        return matriculaFLEM;
    }

    public void setMatriculaFLEM(boolean matriculaFLEM) {
        this.matriculaFLEM = matriculaFLEM;
    }

    public boolean isAdmissao() {
        return admissao;
    }

    public void setAdmissao(boolean admissao) {
        this.admissao = admissao;
    }

    public boolean isRescisao() {
        return rescisao;
    }

    public void setRescisao(boolean rescisao) {
        this.rescisao = rescisao;
    }

    public boolean isObservacao() {
        return observacao;
    }

    public void setObservacao(boolean observacao) {
        this.observacao = observacao;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public boolean isMatriculaSAEB() {
        return matriculaSAEB;
    }

    public void setMatriculaSAEB(boolean matriculaSAEB) {
        this.matriculaSAEB = matriculaSAEB;
    }

    public boolean isDemandante() {
        return demandante;
    }

    public void setDemandante(boolean demandante) {
        this.demandante = demandante;
    }

    public boolean isDemandanteSigla() {
        return demandanteSigla;
    }

    public void setDemandanteSigla(boolean demandanteSigla) {
        this.demandanteSigla = demandanteSigla;
    }

    public boolean isInstituicao() {
        return instituicao;
    }

    public void setInstituicao(boolean instituicao) {
        this.instituicao = instituicao;
    }

    public boolean isUnidadeDeLotacao() {
        return unidadeDeLotacao;
    }

    public void setUnidadeDeLotacao(boolean unidadeDeLotacao) {
        this.unidadeDeLotacao = unidadeDeLotacao;
    }

    public boolean isLogradouroUnidadeDeLotacao() {
        return logradouroUnidadeDeLotacao;
    }

    public void setLogradouroUnidadeDeLotacao(boolean logradouroUnidadeDeLotacao) {
        this.logradouroUnidadeDeLotacao = logradouroUnidadeDeLotacao;
    }

    public boolean isBairroUnidadeDeLotacao() {
        return bairroUnidadeDeLotacao;
    }

    public void setBairroUnidadeDeLotacao(boolean bairroUnidadeDeLotacao) {
        this.bairroUnidadeDeLotacao = bairroUnidadeDeLotacao;
    }

    public boolean isMunicipioUnidadeDeLotacao() {
        return municipioUnidadeDeLotacao;
    }

    public void setMunicipioUnidadeDeLotacao(boolean municipioUnidadeDeLotacao) {
        this.municipioUnidadeDeLotacao = municipioUnidadeDeLotacao;
    }

    public boolean isCepUnidadeDeLotacao() {
        return cepUnidadeDeLotacao;
    }

    public void setCepUnidadeDeLotacao(boolean cepUnidadeDeLotacao) {
        this.cepUnidadeDeLotacao = cepUnidadeDeLotacao;
    }

    public boolean isPontoFocalNaUnidade() {
        return pontoFocalNaUnidade;
    }

    public void setPontoFocalNaUnidade(boolean pontoFocalNaUnidade) {
        this.pontoFocalNaUnidade = pontoFocalNaUnidade;
    }

    public boolean isTelefone1PontoFocal() {
        return telefone1PontoFocal;
    }

    public void setTelefone1PontoFocal(boolean telefone1PontoFocal) {
        this.telefone1PontoFocal = telefone1PontoFocal;
    }

    public boolean isTelefone2PontoFocal() {
        return telefone2PontoFocal;
    }

    public void setTelefone2PontoFocal(boolean telefone2PontoFocal) {
        this.telefone2PontoFocal = telefone2PontoFocal;
    }

    public boolean isTelefone3PontoFocal() {
        return telefone3PontoFocal;
    }

    public void setTelefone3PontoFocal(boolean telefone3PontoFocal) {
        this.telefone3PontoFocal = telefone3PontoFocal;
    }

    public boolean isTelefone4PontoFocal() {
        return telefone4PontoFocal;
    }

    public void setTelefone4PontoFocal(boolean telefone4PontoFocal) {
        this.telefone4PontoFocal = telefone4PontoFocal;
    }

    public boolean isTelefone5PontoFocal() {
        return telefone5PontoFocal;
    }

    public void setTelefone5PontoFocal(boolean telefone5PontoFocal) {
        this.telefone5PontoFocal = telefone5PontoFocal;
    }

    public boolean isEmailPontoFocal() {
        return emailPontoFocal;
    }

    public void setEmailPontoFocal(boolean emailPontoFocal) {
        this.emailPontoFocal = emailPontoFocal;
    }

    public boolean isTerritorio() {
        return territorio;
    }

    public void setTerritorio(boolean territorio) {
        this.territorio = territorio;
    }

    public boolean isMunicipioDaVaga() {
        return municipioDaVaga;
    }

    public void setMunicipioDaVaga(boolean municipioDaVaga) {
        this.municipioDaVaga = municipioDaVaga;
    }

    public boolean isMunicipioEscola() {
        return municipioEscola;
    }

    public void setMunicipioEscola(boolean municipioEscola) {
        this.municipioEscola = municipioEscola;
    }

    public boolean isFormacao() {
        return formacao;
    }

    public void setFormacao(boolean formacao) {
        this.formacao = formacao;
    }

    public boolean isEixoDeFormacao() {
        return eixoDeFormacao;
    }

    public void setEixoDeFormacao(boolean eixoDeFormacao) {
        this.eixoDeFormacao = eixoDeFormacao;
    }

    public boolean isDataConvocacaoSETRESINE() {
        return dataConvocacaoSETRESINE;
    }

    public void setDataConvocacaoSETRESINE(boolean dataConvocacaoSETRESINE) {
        this.dataConvocacaoSETRESINE = dataConvocacaoSETRESINE;
    }

    public boolean isRemessaSETRESINE() {
        return remessaSETRESINE;
    }

    public void setRemessaSETRESINE(boolean remessaSETRESINE) {
        this.remessaSETRESINE = remessaSETRESINE;
    }

    public boolean isDataRemessaSETRESINE() {
        return dataRemessaSETRESINE;
    }

    public void setDataRemessaSETRESINE(boolean dataRemessaSETRESINE) {
        this.dataRemessaSETRESINE = dataRemessaSETRESINE;
    }

    public boolean isMesRemessaSETRESINE() {
        return mesRemessaSETRESINE;
    }

    public void setMesRemessaSETRESINE(boolean mesRemessaSETRESINE) {
        this.mesRemessaSETRESINE = mesRemessaSETRESINE;
    }

    public boolean isCategoria() {
        return categoria;
    }

    public void setCategoria(boolean categoria) {
        this.categoria = categoria;
    }

    public boolean isSituacao() {
        return situacao;
    }

    public void setSituacao(boolean situacao) {
        this.situacao = situacao;
    }

    public boolean isAnamnese() {
        return anamnese;
    }

    public void setAnamnese(boolean anamnese) {
        this.anamnese = anamnese;
    }

    public boolean isDataInicioAtividades() {
        return dataInicioAtividades;
    }

    public void setDataInicioAtividades(boolean dataInicioAtividades) {
        this.dataInicioAtividades = dataInicioAtividades;
    }

    public boolean isPublicacaoDiarioOficial() {
        return publicacaoDiarioOficial;
    }

    public void setPublicacaoDiarioOficial(boolean publicacaoDiarioOficial) {
        this.publicacaoDiarioOficial = publicacaoDiarioOficial;
    }

    public boolean isDivergenciaMunicipio() {
        return divergenciaMunicipio;
    }

    public void setDivergenciaMunicipio(boolean divergenciaMunicipio) {
        this.divergenciaMunicipio = divergenciaMunicipio;
    }

    public boolean isDistanciaMunicipios() {
        return distanciaMunicipios;
    }

    public void setDistanciaMunicipios(boolean distanciaMunicipios) {
        this.distanciaMunicipios = distanciaMunicipios;
    }

    public boolean isDataDeEnvioDaSituacaoParaSETRESINE() {
        return dataDeEnvioDaSituacaoParaSETRESINE;
    }

    public void setDataDeEnvioDaSituacaoParaSETRESINE(boolean dataDeEnvioDaSituacaoParaSETRESINE) {
        this.dataDeEnvioDaSituacaoParaSETRESINE = dataDeEnvioDaSituacaoParaSETRESINE;
    }

    public boolean isDataRecebimentoFardamento() {
        return dataRecebimentoFardamento;
    }

    public void setDataRecebimentoFardamento(boolean dataRecebimentoFardamento) {
        this.dataRecebimentoFardamento = dataRecebimentoFardamento;
    }

    public boolean isValeAlimentacao() {
        return valeAlimentacao;
    }

    public void setValeAlimentacao(boolean valeAlimentacao) {
        this.valeAlimentacao = valeAlimentacao;
    }

    public boolean isValeTransporte() {
        return valeTransporte;
    }

    public void setValeTransporte(boolean valeTransporte) {
        this.valeTransporte = valeTransporte;
    }

    public boolean isAso() {
        return aso;
    }

    public void setAso(boolean aso) {
        this.aso = aso;
    }

    public boolean isTamanhoDeCamisa() {
        return tamanhoDeCamisa;
    }

    public void setTamanhoDeCamisa(boolean tamanhoDeCamisa) {
        this.tamanhoDeCamisa = tamanhoDeCamisa;
    }

    public boolean isEscolaconlusao() {
        return escolaconlusao;
    }

    public void setEscolaconlusao(boolean escolaconlusao) {
        this.escolaconlusao = escolaconlusao;
    }

    public boolean isFichaAdmissao() {
        return fichaAdmissao;
    }

    public void setFichaAdmissao(boolean fichaAdmissao) {
        this.fichaAdmissao = fichaAdmissao;
    }

    public boolean isCtpsPendente() {
        return ctpsPendente;
    }

    public void setCtpsPendente(boolean ctpsPendente) {
        this.ctpsPendente = ctpsPendente;
    }

    public boolean isCpfPendente() {
        return cpfPendente;
    }

    public void setCpfPendente(boolean cpfPendente) {
        this.cpfPendente = cpfPendente;
    }

    public boolean isComprovanteresidenciaPendente() {
        return comprovanteresidenciaPendente;
    }

    public void setComprovanteresidenciaPendente(boolean comprovanteresidenciaPendente) {
        this.comprovanteresidenciaPendente = comprovanteresidenciaPendente;
    }

    public boolean isRgPendente() {
        return rgPendente;
    }

    public void setRgPendente(boolean rgPendente) {
        this.rgPendente = rgPendente;
    }

    public boolean isCartaCidadaoPendente() {
        return cartaCidadaoPendente;
    }

    public void setCartaCidadaoPendente(boolean cartaCidadaoPendente) {
        this.cartaCidadaoPendente = cartaCidadaoPendente;
    }

    public boolean isTituloEleitorPendente() {
        return tituloEleitorPendente;
    }

    public void setTituloEleitorPendente(boolean tituloEleitorPendente) {
        this.tituloEleitorPendente = tituloEleitorPendente;
    }

    public boolean isComprovanteVotacaoPendente() {
        return comprovanteVotacaoPendente;
    }

    public void setComprovanteVotacaoPendente(boolean comprovanteVotacaoPendente) {
        this.comprovanteVotacaoPendente = comprovanteVotacaoPendente;
    }

    public boolean isDiplomaPendente() {
        return diplomaPendente;
    }

    public void setDiplomaPendente(boolean diplomaPendente) {
        this.diplomaPendente = diplomaPendente;
    }

    public boolean isCurriculoPendente() {
        return curriculoPendente;
    }

    public void setCurriculoPendente(boolean curriculoPendente) {
        this.curriculoPendente = curriculoPendente;
    }

//    public boolean isDadosbancariosPendente() {
//        return dadosbancariosPendente;
//    }
//
//    public void setDadosbancariosPendente(boolean dadosbancariosPendente) {
//        this.dadosbancariosPendente = dadosbancariosPendente;
//    }

    public boolean isFoto3x4Pendente() {
        return foto3x4Pendente;
    }

    public void setFoto3x4Pendente(boolean foto3x4Pendente) {
        this.foto3x4Pendente = foto3x4Pendente;
    }

    public boolean isAsoPendente() {
        return asoPendente;
    }

    public void setAsoPendente(boolean asoPendente) {
        this.asoPendente = asoPendente;
    }

    public boolean isCertidaoCasamentoPendente() {
        return certidaoCasamentoPendente;
    }

    public void setCertidaoCasamentoPendente(boolean certidaoCasamentoPendente) {
        this.certidaoCasamentoPendente = certidaoCasamentoPendente;
    }

    public boolean isCertidaoNascimentoPendente() {
        return certidaoNascimentoPendente;
    }

    public void setCertidaoNascimentoPendente(boolean certidaoNascimentoPendente) {
        this.certidaoNascimentoPendente = certidaoNascimentoPendente;
    }

    public boolean isComprovanteEscolaridadePendente() {
        return comprovanteEscolaridadePendente;
    }

    public void setComprovanteEscolaridadePendente(boolean comprovanteEscolaridadePendente) {
        this.comprovanteEscolaridadePendente = comprovanteEscolaridadePendente;
    }

    public boolean isReservistaPendente() {
        return reservistaPendente;
    }

    public void setReservistaPendente(boolean reservistaPendente) {
        this.reservistaPendente = reservistaPendente;
    }

    public boolean isCartaoVacinaPendente() {
        return cartaoVacinaPendente;
    }

    public void setCartaoVacinaPendente(boolean cartaoVacinaPendente) {
        this.cartaoVacinaPendente = cartaoVacinaPendente;
    }

    public boolean isContratoTrabalhoFlem() {
        return contratoTrabalhoFlem;
    }

    public void setContratoTrabalhoFlem(boolean contratoTrabalhoFlem) {
        this.contratoTrabalhoFlem = contratoTrabalhoFlem;
    }

    public boolean isVale_Transporte() {
        return vale_Transporte;
    }

    public void setVale_Transporte(boolean vale_Transporte) {
        this.vale_Transporte = vale_Transporte;
    }

    public boolean isVale_Alimentacao() {
        return vale_Alimentacao;
    }

    public void setVale_Alimentacao(boolean vale_Alimentacao) {
        this.vale_Alimentacao = vale_Alimentacao;
    }

    public boolean isPlano_Saude() {
        return plano_Saude;
    }

    public void setPlano_Saude(boolean plano_Saude) {
        this.plano_Saude = plano_Saude;
    }

    public boolean isCracha() {
        return cracha;
    }

    public void setCracha(boolean cracha) {
        this.cracha = cracha;
    }
    
    public boolean isEstagioConcluido() {
        return estagioConcluido;
    }

    public void setEstagioConcluido(boolean estagioConcluido) {
        this.estagioConcluido = estagioConcluido;
    }

    public boolean isCarteiraAssinada() {
        return carteiraAssinada;
    }

    public void setCarteiraAssinada(boolean carteiraAssinada) {
        this.carteiraAssinada = carteiraAssinada;
    }

    public boolean isTempoContratacao() {
        return tempoContratacao;
    }

    public void setTempoContratacao(boolean tempoContratacao) {
        this.tempoContratacao = tempoContratacao;
    }

    public boolean isMesAnoAdmissao() {
        return mesAnoAdmissao;
    }

    public void setMesAnoAdmissao(boolean mesAnoAdmissao) {
        this.mesAnoAdmissao = mesAnoAdmissao;
    }

    public boolean isMesAnoDesligamento() {
        return mesAnoDesligamento;
    }

    public void setMesAnoDesligamento(boolean mesAnoDesligamento) {
        this.mesAnoDesligamento = mesAnoDesligamento;
    }

    public boolean isCtps() {
        return ctps;
    }

    public void setCtps(boolean ctps) {
        this.ctps = ctps;
    }

    public boolean isPis() {
        return pis;
    }

    public void setPis(boolean pis) {
        this.pis = pis;
    } 

    public boolean isEscRegional() {
        return escRegional;
    }

    public void setEscRegional(boolean escRegional) {
        this.escRegional = escRegional;
    }

    public boolean isCursoSuperior() {
        return cursoSuperior;
    }

    public void setCursoSuperior(boolean cursoSuperior) {
        this.cursoSuperior = cursoSuperior;
    }

    public boolean isTipoInstituicaoSuperior() {
        return tipoInstituicaoSuperior;
    }

    public void setTipoInstituicaoSuperior(boolean tipoInstituicaoSuperior) {
        this.tipoInstituicaoSuperior = tipoInstituicaoSuperior;
    }

    public boolean isNomeInstituicaoSuperior() {
        return nomeInstituicaoSuperior;
    }

    public void setNomeInstituicaoSuperior(boolean nomeInstituicaoSuperior) {
        this.nomeInstituicaoSuperior = nomeInstituicaoSuperior;
    }

    public boolean isNomeCursoGraduacao() {
        return nomeCursoGraduacao;
    }

    public void setNomeCursoGraduacao(boolean nomeCursoGraduacao) {
        this.nomeCursoGraduacao = nomeCursoGraduacao;
    }

    public boolean isModalidadeGraduacao() {
        return modalidadeGraduacao;
    }

    public void setModalidadeGraduacao(boolean modalidadeGraduacao) {
        this.modalidadeGraduacao = modalidadeGraduacao;
    }

    public boolean isAnoMatricula() {
        return anoMatricula;
    }

    public void setAnoMatricula(boolean anoMatricula) {
        this.anoMatricula = anoMatricula;
    }

    public boolean isSemestre() {
        return semestre;
    }

    public void setSemestre(boolean semestre) {
        this.semestre = semestre;
    }

    public boolean isRendaPPEAjuda() {
        return rendaPPEAjuda;
    }

    public void setRendaPPEAjuda(boolean rendaPPEAjuda) {
        this.rendaPPEAjuda = rendaPPEAjuda;
    }

    public boolean isDataRespostaSemestre() {
        return dataRespostaSemestre;
    }

    public void setDataRespostaSemestre(boolean dataRespostaSemestre) {
        this.dataRespostaSemestre = dataRespostaSemestre;
    }

    public boolean isCursoSuperiorConcluido() {
        return cursoSuperiorConcluido;
    }

    public void setCursoSuperiorConcluido(boolean cursoSuperiorConcluido) {
        this.cursoSuperiorConcluido = cursoSuperiorConcluido;
    }

    public boolean isPretendeFazerCursoSuperior() {
        return pretendeFazerCursoSuperior;
    }

    public void setPretendeFazerCursoSuperior(boolean pretendeFazerCursoSuperior) {
        this.pretendeFazerCursoSuperior = pretendeFazerCursoSuperior;
    }

    public boolean isCursoTecnicoProfissionalizante() {
        return cursoTecnicoProfissionalizante;
    }

    public void setCursoTecnicoProfissionalizante(boolean cursoTecnicoProfissionalizante) {
        this.cursoTecnicoProfissionalizante = cursoTecnicoProfissionalizante;
    }

    public boolean isTipoInstituicaoTecnica() {
        return tipoInstituicaoTecnica;
    }

    public void setTipoInstituicaoTecnica(boolean tipoInstituicaoTecnica) {
        this.tipoInstituicaoTecnica = tipoInstituicaoTecnica;
    }

    public boolean isNomeInstituicaoTecnico() {
        return nomeInstituicaoTecnico;
    }

    public void setNomeInstituicaoTecnico(boolean nomeInstituicaoTecnico) {
        this.nomeInstituicaoTecnico = nomeInstituicaoTecnico;
    }

    public boolean isNomeCursoTecnico() {
        return nomeCursoTecnico;
    }

    public void setNomeCursoTecnico(boolean nomeCursoTecnico) {
        this.nomeCursoTecnico = nomeCursoTecnico;
    }
    
    
    
    
}

package br.org.flem.primeiroemprego.dto;

/**
 * Classe para valores do relatório de frequência
 * @author <code>tscortes@flem.org.br</code>
 * @version 1.0
 */
public class ListaFrequenciaDTO implements Comparable{
    
    
    private String escritorioRegional;
    private String territorio;
    private String municipioVaga;
    private String demandante;
    private String unidadeLotacao;
    private String nomeEgresso;
    private String cpf;
    private String formacao;
    private String email;
    private String categoria;
    private String matricula;
    
    private String dataContratacao;
    private String demandanteSigla;
    

    public ListaFrequenciaDTO(String demandante, String nomeEgresso, String cpf, String formacao, String email, String municipioVaga) {
        this.demandante = demandante;
        this.nomeEgresso = nomeEgresso;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
        this.municipioVaga = municipioVaga;
    }

    public ListaFrequenciaDTO(String escritorioRegional, String territorio, String municipioVaga, String demandante, 
            String unidadeLotacao, String nomeEgresso, String cpf, String formacao, String email, String categoria, String matricula, String demandanteSigla) {
        this.escritorioRegional = escritorioRegional;
        this.territorio = territorio;
        this.municipioVaga = municipioVaga;
        this.demandante = demandante;
        this.unidadeLotacao = unidadeLotacao;
        this.nomeEgresso = nomeEgresso;
        this.cpf = cpf;
        this.formacao = formacao;
        this.email = email;
        this.categoria = categoria; 
        this.matricula = matricula;
        this.demandanteSigla = demandanteSigla;
    }
    
    
    public ListaFrequenciaDTO() {
        super();
    }
    
    public String getDemandante() {
        return demandante;
    }

    public void setDemandante(String demandante) {
        this.demandante = demandante;
    }

    public String getNomeEgresso() {
        return nomeEgresso;
    }

    public void setNomeEgresso(String nomeEgresso) {
        this.nomeEgresso = nomeEgresso;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFormacao() {
        return formacao;
    }

    public void setFormacao(String formacao) {
        this.formacao = formacao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMunicipioVaga() {
        return municipioVaga;
    }

    public void setMunicipioVaga(String municipioVaga) {
        this.municipioVaga = municipioVaga;
    }

    public String getEscritorioRegional() {
        return escritorioRegional;
    }

    public void setEscritorioRegional(String escritorioRegional) {
        this.escritorioRegional = escritorioRegional;
    }

    public String getTerritorio() {
        return territorio;
    }

    public void setTerritorio(String territorio) {
        this.territorio = territorio;
    }

    public String getUnidadeLotacao() {
        return unidadeLotacao;
    }

    public void setUnidadeLotacao(String unidadeLotacao) {
        this.unidadeLotacao = unidadeLotacao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getDataContratacao() {
        return dataContratacao;
    }

    public void setDataContratacao(String dataContratacao) {
        this.dataContratacao = dataContratacao;
    }

    public String getDemandanteSigla() {
        return demandanteSigla;
    }

    public void setDemandanteSigla(String demandanteSigla) {
        this.demandanteSigla = demandanteSigla;
    }
    @Override
    public int compareTo(Object other) {
        ListaFrequenciaDTO otherObj = (ListaFrequenciaDTO) other;
        return this.nomeEgresso.compareTo(otherObj.getNomeEgresso());
    }

    
}

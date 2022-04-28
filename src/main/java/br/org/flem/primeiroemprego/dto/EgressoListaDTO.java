
package br.org.flem.primeiroemprego.dto;

/**
 * Classe EgressoListaDTO
 * @author <code>tscortes@flem.org.br</code>
 * 19/02/2019
 * @version 1.0
 */
public class EgressoListaDTO {
    
    private Long id;
    private Long idEgresso;
    private String nomeEgresso;
    private String cpfEgresso;
    private Long idLista;
    private Boolean feito;
    private Boolean deAcordo;
    
    public EgressoListaDTO() {
    }

    public EgressoListaDTO(Long id, Long idEgresso, String nomeEgresso, String cpfEgresso, Long idLista, Boolean feito, Boolean deAcordo) {
        this.id = id;
        this.idEgresso = idEgresso;
        this.nomeEgresso = nomeEgresso;
        this.cpfEgresso = cpfEgresso;
        this.idLista = idLista;
        this.feito = feito;
        this.deAcordo = deAcordo;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeEgresso() {
        return nomeEgresso;
    }

    public void setNomeEgresso(String nomeEgresso) {
        this.nomeEgresso = nomeEgresso;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public Boolean getFeito() {
        return feito;
    }

    public void setFeito(Boolean feito) {
        this.feito = feito;
    }

    public String getCpfEgresso() {
        return cpfEgresso;
    }

    public void setCpfEgresso(String cpfEgresso) {
        this.cpfEgresso = cpfEgresso;
    }

    public Long getIdEgresso() {
        return idEgresso;
    }

    public void setIdEgresso(Long idEgresso) {
        this.idEgresso = idEgresso;
    }

    public Boolean getDeAcordo() {
        return deAcordo;
    }

    public void setDeAcordo(Boolean deAcordo) {
        this.deAcordo = deAcordo;
    }

    public String getTextoDeAcordo() {
        if( deAcordo == null)
            return "";
        if( deAcordo ){
            return "SIM";
        }
        return "NÃO";
    }    
    
}

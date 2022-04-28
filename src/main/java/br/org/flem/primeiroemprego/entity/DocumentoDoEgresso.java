package br.org.flem.primeiroemprego.entity;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author emsilva
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class DocumentoDoEgresso extends Documento{

    @ManyToOne
    @NotNull
    @JoinColumn(name="id_egresso")
    private Egresso egresso;
    
    //Diferenciar Documentos Sigilosos
    private Boolean sigiloso = false;
    
    public DocumentoDoEgresso(){
        
    }
    
    public DocumentoDoEgresso(Long id, String nome){
        super.setId(id);
        super.setNome(nome);
    }

    public Egresso getEgresso() {
        return egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

    public Boolean getSigiloso() {
        return sigiloso;
    }

    public void setSigiloso(Boolean sigiloso) {
        this.sigiloso = sigiloso;
    }

}

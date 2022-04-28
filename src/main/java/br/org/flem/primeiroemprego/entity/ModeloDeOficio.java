package br.org.flem.primeiroemprego.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author emsilva
 */
@Entity
public class ModeloDeOficio extends Documento{

    @Enumerated
    private Status status = Status.SALVO;
    
    private Date dataGeracao;
    private String assunto;
    @ManyToOne
    private TipoModeloDeOficio tipoModelo;

    @ManyToMany
    @JoinTable(name = "ModeloDeOficio_EgressosPendentes",
            joinColumns = @JoinColumn(name="id_documento", referencedColumnName="id_documento"),
            inverseJoinColumns = @JoinColumn(name="id_egresso", referencedColumnName="id_egresso"))
    private List<Egresso> egressosParaGerar;
    
    @OneToMany(mappedBy = "modelo")
    private List<Oficio> oficiosGerados;
    
    public ModeloDeOficio(){
        
    }
    
    public ModeloDeOficio(Long id, String nome){
        super.setId(id);
        super.setNome(nome);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Egresso> getEgressosParaGerar() {
        return egressosParaGerar;
    }

    public void setEgressosParaGerar(List<Egresso> egressosParaGerar) {
        this.egressosParaGerar = egressosParaGerar;
    }

    public List<Oficio> getOficiosGerados() {
        return oficiosGerados;
    }

    public void setOficiosGerados(List<Oficio> oficiosGerados) {
        this.oficiosGerados = oficiosGerados;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public enum Status{
        SALVO(0,"Salvo"),
        GERANDO(1,"Gerando"),
        GERADO(2,"Gerado");

        Status(int id,String descricao){
            this.id = id;
            this.descricao = descricao;
        }

        private int id;
        private String descricao;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getDescricao() {
            return descricao;
        }

        public void setDescricao(String descricao) {
            this.descricao = descricao;
        }
    }

    public Date getDataGeracao() {
        return dataGeracao;
    }

    public void setDataGeracao(Date dataGeracao) {
        this.dataGeracao = dataGeracao;
    }

    public TipoModeloDeOficio getTipoModelo() {
        return tipoModelo;
    }

    public void setTipoModelo(TipoModeloDeOficio tipoModelo) {
        this.tipoModelo = tipoModelo;
    }
    
    

}

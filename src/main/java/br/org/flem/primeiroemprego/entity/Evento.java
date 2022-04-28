package br.org.flem.primeiroemprego.entity;

import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author emsilva
 */
@Entity
public class Evento extends UID<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_evento")
    private Long id;

    private String nome;

    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    
    private String tipo;
    private String modalidade;

    @ManyToOne
    @Fetch(FetchMode.SELECT)
    @JoinColumn(name="id_localDoEvento")
    private LocalDoEvento local;

    @ManyToMany
    @JoinTable(name = "Evento_Municipio",
            joinColumns = @JoinColumn(name="id_evento", referencedColumnName="id_evento"),
            inverseJoinColumns = @JoinColumn(name="id_municipio", referencedColumnName="id_municipio"))
    private List<Municipio> municipiosEnvolvidos;

    @ManyToMany
    @JoinTable(name = "Evento_Demandante",
            joinColumns = @JoinColumn(name="id_evento", referencedColumnName="id_evento"),
            inverseJoinColumns = @JoinColumn(name="id_demandante", referencedColumnName="id_demandante"))
    private List<Demandante> demandantesEnvolvidos;

    @OneToMany(mappedBy = "id.evento", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SELECT)
    private List<EventoEgresso> egressosParticipantes;

    @Override
    public Long getId() {
        return id;
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

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModalidade() {
        return modalidade;
    }

    public void setModalidade(String modalidade) {
        this.modalidade = modalidade;
    }

    public LocalDoEvento getLocal() {
        return local;
    }

    public void setLocal(LocalDoEvento local) {
        this.local = local;
    }

    public List<Municipio> getMunicipiosEnvolvidos() {
        return municipiosEnvolvidos;
    }

    public void setMunicipiosEnvolvidos(List<Municipio> municipiosEnvolvidos) {
        this.municipiosEnvolvidos = municipiosEnvolvidos;
    }

    public List<Demandante> getDemandantesEnvolvidos() {
        return demandantesEnvolvidos;
    }

    public void setDemandantesEnvolvidos(List<Demandante> demandantesEnvolvidos) {
        this.demandantesEnvolvidos = demandantesEnvolvidos;
    }

    public List<EventoEgresso> getEgressosParticipantes() {
        return egressosParticipantes;
    }

    public void setEgressosParticipantes(List<EventoEgresso> egressosParticipantes) {
        this.egressosParticipantes = egressosParticipantes;
    }

}

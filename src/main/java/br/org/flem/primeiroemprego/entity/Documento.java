package br.org.flem.primeiroemprego.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;

/**
 *
 * @author emsilva
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Documento extends UID<Long>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_documento")
    private Long id;

    private String nome;

    private String tipo;
    
    private String filePath;

    @Lob
    private byte[] arquivo;
    
    private Long fileLenght;
    
    public Documento(){
        
    }
    
    public Documento(Long id, String nome, byte[] arquivo){
        this.id = id;
        this.nome = nome;
        this.arquivo = arquivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public byte[] getArquivo() {
        return arquivo;
    }
    
    public void setArquivo(byte [] arquivo) {
        this.arquivo = arquivo;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getFileLenght() {
        return fileLenght;
    }

    public void setFileLenght(Long fileLenght) {
        this.fileLenght = fileLenght;
    }
    
}

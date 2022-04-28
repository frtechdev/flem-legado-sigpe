package br.org.flem.primeiroemprego.entity;

import br.org.flem.commons.util.PropertiesUtil;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author emsilva
 */
public class AnexoEmail implements Serializable {

    private String nome;

    private Long idCampanha;

    private String tipoConteudo;

    private byte[] conteudo;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdCampanha() {
        return idCampanha;
    }

    public void setIdCampanha(Long idCampanha) {
        this.idCampanha = idCampanha;
    }

    public String getTipoConteudo() {
        return tipoConteudo;
    }

    public void setTipoConteudo(String tipoConteudo) {
        this.tipoConteudo = tipoConteudo;
    }

    public byte[] getConteudo() {
        return conteudo;
    }

    public void setConteudo(byte[] conteudo) {
        this.conteudo = conteudo;
    }
    
    public String getCaminhoCompleto(){
        return PropertiesUtil.getProperty("pathAnexos")+File.separator+getIdCampanha()+File.separator+getNome();
    }

    public String getCaminhoPasta(){
        return PropertiesUtil.getProperty("pathAnexos")+File.separator+getIdCampanha()+File.separator;
    }

    public boolean isImagem(){
        return getTipoConteudo().startsWith("image");
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.nome != null ? this.nome.hashCode() : 0);
        hash = 59 * hash + (this.idCampanha != null ? this.idCampanha.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AnexoEmail other = (AnexoEmail) obj;
        if ((this.nome == null) ? (other.nome != null) : !this.nome.equals(other.nome)) {
            return false;
        }
        if ((this.idCampanha == null) ? (other.idCampanha != null) : !this.idCampanha.equals(other.idCampanha)) {
            return false;
        }
        return true;
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dto;

import java.io.Serializable;

/**
 *
 * @author tscortes
 */
public class ArquivoDTO implements Serializable {

    private Long id;
    private String fileName;
    private String filePath;
    private Long fileLenght;
    private String contentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
    
    
}

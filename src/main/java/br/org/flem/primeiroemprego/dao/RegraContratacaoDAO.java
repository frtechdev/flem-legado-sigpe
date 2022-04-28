/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.RegraContratacao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author AJLima
 */
@ManagedBean
@ViewScoped
public class RegraContratacaoDAO extends  GenericDAO<RegraContratacao, Long> {
    
    public RegraContratacaoDAO() throws Exception{
        super(RegraContratacao.class);
    }
}

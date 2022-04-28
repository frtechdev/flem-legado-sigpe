/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.primeiroemprego.dao.TelefoneDAO;
import br.org.flem.primeiroemprego.dto.TelefoneDTO;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Telefone;
import br.org.flem.primeiroemprego.entity.TelefoneID;
import br.org.flem.primeiroemprego.exception.BusinessException;
import fr.opensagres.xdocreport.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class TelefoneRN extends BaseRN<Telefone> {

    @ManagedProperty(value = "#{telefoneDAO}")
    private TelefoneDAO telefoneDAO;

    public List<Telefone> obterPorEgresso(Egresso egresso) {
        return telefoneDAO.obterPorEgresso(egresso);
    }
    
    public void excluirPorId(TelefoneID id) throws Exception {
        Telefone telefone = telefoneDAO.obterPorPK(id);
        excluir(telefone);
    }
    
    public void excluirPorId(Egresso egresso, Short seq) throws Exception {
        excluirPorId(new TelefoneID(egresso, seq));
    }

    public Telefone inserir(Telefone telefone) throws Exception {
        telefone.getId().setSeq(obterNextSeq(telefone.getId().getEgresso()));
        validarNumeroDeTelefone(telefone);
        return telefoneDAO.inserir(telefone);
    }

    public Telefone atualizar(Telefone telefone) throws Exception {
        Telefone tel = obterPorId(telefone.getId());
        if( tel == null )
            throw new BusinessException("Telefone não localizado");
        if( tel.equals(telefone)){
            return tel;
        }
        tel.setDescricao(telefone.getDescricao());
        tel.setNumero(telefone.getNumero());
        tel.setTipo(telefone.getTipo());
        validarNumeroDeTelefone(tel);
        return telefoneDAO.alterar(tel);
    }
    
    public void excluir(Telefone telefone) throws Exception {
        telefoneDAO.excluir(telefone);
    }

    public TelefoneDTO toDTO(Telefone telefone) {
        TelefoneDTO dto = new TelefoneDTO();
        dto.setDescricao(telefone.getDescricao());
        dto.setNumero(telefone.getNumero());
        dto.setIdEgresso(telefone.getId().getEgresso().getId());
        dto.setIdSeq(telefone.getId().getSeq());
        dto.setTipo(telefone.getTipo().name());
        return dto;
    }

    public List<TelefoneDTO> toDTO(List<Telefone> telefones) {
        List<TelefoneDTO> lista = new ArrayList<>();
        for (Telefone telefone : telefones) {
            lista.add(toDTO(telefone));
        }
        return lista;
    }

    public void validarNumeroDeTelefone(Telefone telefone) {
        if (telefone.getTipo() == null) {
            throw new BusinessException("Tipo de Telefone não pode ser nulo");
        }
        if (!StringUtils.isEmpty(telefone.getNumero()) &&
                telefone.getNumero().length() != 10 && 
                telefone.getNumero().length() != 11) {
            throw new BusinessException("Telefone está com formato inválido");
        }
    }

    public Telefone obterPorId(TelefoneID id){
        return telefoneDAO.obterPorPK(id);
    }
    private Short obterNextSeq(Egresso egresso) {
        Short seq = telefoneDAO.obterMaxSeq(egresso);
        return Short.valueOf("" + (seq + Short.valueOf("" + 1)));
    }

    public TelefoneDAO getTelefoneDAO() {
        return telefoneDAO;
    }

    public void setTelefoneDAO(TelefoneDAO telefoneDAO) {
        this.telefoneDAO = telefoneDAO;
    }

}

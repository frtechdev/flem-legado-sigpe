/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.negocio;

import br.org.flem.fwe.util.CPF;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.dto.FiltroEgressoDTO;
import br.org.flem.primeiroemprego.dto.TelefoneDTO;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.EnumTipoTelefone;
import br.org.flem.primeiroemprego.entity.ListaEgressoDTO;
import br.org.flem.primeiroemprego.entity.Telefone;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class EgressoRN extends BaseRN<Egresso> {

    @ManagedProperty(value = "#{egressoDAO}")
    private EgressoDAO egressoDAO;
    @ManagedProperty(value = "#{telefoneRN}")
    private TelefoneRN telefoneRN;

    public Egresso alterarEgresso(Egresso egresso) throws Exception {
        Egresso old = egressoDAO.obterPorPK(egresso.getId());
        BeanUtilsBean.getInstance().getConvertUtils().register(false, false, 0);
        BeanUtils.copyProperties(old, egresso);
        return this.alterar(old);
    }

    public Egresso alterar(Egresso egresso) throws Exception {
        return this.egressoDAO.alterar(egresso);
    }

    public Egresso inserir(Egresso egresso) throws Exception {
        return this.egressoDAO.inserir(egresso);
    }

    public void atualizarTelefone(List<Telefone> lista, Egresso egresso) throws Exception {
        List<Telefone> telefones = telefoneRN.obterPorEgresso(egresso);
        if (telefones.isEmpty()) {
            if (lista != null && !lista.isEmpty()) {
                for (Telefone telefone : lista) {
                    telefoneRN.inserir(telefone);
                }
            }
        } else {
            for (Telefone telefone : lista) {
                if (telefone.getId().getSeq() == null) {
                    telefoneRN.inserir(telefone);
                } else {
                    telefoneRN.atualizar(telefone);
                }
            }
            telefones = telefoneRN.obterPorEgresso(egresso);
            for (Telefone telefone : telefones) {
                Telefone excluir = telefone;
                for (Telefone tel : lista) {
                    if (tel.getNumero().equalsIgnoreCase(telefone.getNumero()) && 
                            (tel.getId().getSeq() != null && 
                            tel.getId().getSeq().equals(telefone.getId().getSeq()))) {
                        excluir = null;
                    }
                }
                if( excluir != null )
                    telefoneRN.excluir(excluir);
            }
        }
    }

    public List<Telefone> obterTelefones(Egresso egresso) {
        return telefoneRN.obterPorEgresso(egresso);
    }

    public List<String> obterRespostas(String campo) {
        return egressoDAO.obterRespostasDoCampo(campo);
    }

    public List<String> obterRespostasDaVaga(String campo) {
        return egressoDAO.obterRespostasDaVaga(campo);
    }

    public List<ListaEgressoDTO> obterListaEgressoDTO(FiltroEgressoDTO filtro) {
        if (!StringUtils.isEmpty(filtro.getCpf())) {
            filtro.setCpf(CPF.formataCPF(filtro.getCpf()));
        }
        return egressoDAO.obterListaEgressoDTO(filtro);
    }

    public void carregarHistoricoDeVaga(Egresso egresso) {
        egressoDAO.carregarHistoricoDeVaga(egresso);
    }

    public List<TelefoneDTO> obterTelefonePorEgresso(Long id) {
        Egresso egresso = obterPorId(id);
        List<Telefone> telefones = telefoneRN.obterPorEgresso(egresso);
        return telefoneRN.toDTO(telefones);
    }

    public Egresso obterPorId(Long id) {
        Egresso egresso = egressoDAO.obterPorPK(id);
//        egresso.setTelefones(telefoneRN.obterPorEgresso(egresso));
        return egresso;
    }

    public Egresso obterPorCPF(String cpf) {
        return egressoDAO.obterPorCPF(cpf);
    }

    public EgressoDAO getEgressoDAO() {
        return egressoDAO;
    }

    public void setEgressoDAO(EgressoDAO egressoDAO) {
        this.egressoDAO = egressoDAO;
    }

    public TelefoneRN getTelefoneRN() {
        return telefoneRN;
    }

    public void setTelefoneRN(TelefoneRN telefoneRN) {
        this.telefoneRN = telefoneRN;
    }

//    private void carregarTelefones(Egresso egresso) {
//        String str = egresso.getTelefone1();
//        if( egresso.getTelefones() == null)
//            egresso.setTelefones(new ArrayList<>());
//        if (!StringUtils.isEmpty(str)) {
//            Telefone telefone = new Telefone();
//            telefone.setNumero(str);
//            telefone.setTipo(EnumTipoTelefone.getByLength(str.length()));
//            egresso.getTelefones().add(telefone);
//        }
//        str = egresso.getTelefone2();
//        if (!StringUtils.isEmpty(str)) {
//            Telefone telefone = new Telefone();
//            telefone.setNumero(str);
//            telefone.setTipo(EnumTipoTelefone.getByLength(str.length()));
//            egresso.getTelefones().add(telefone);
//        }
//        str = egresso.getCelular();
//        if (!StringUtils.isEmpty(str)) {
//            Telefone telefone = new Telefone();
//            telefone.setNumero(str);
//            telefone.setTipo(EnumTipoTelefone.getByLength(str.length()));
//            egresso.getTelefones().add(telefone);
//        }
//    }
}

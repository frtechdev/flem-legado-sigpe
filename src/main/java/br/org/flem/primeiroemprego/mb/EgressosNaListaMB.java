package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.EgressoListaDAO;
import br.org.flem.primeiroemprego.dao.ListaDAO;
import br.org.flem.primeiroemprego.dto.EgressoListaDTO;
import br.org.flem.primeiroemprego.entity.EgressoLista;
import br.org.flem.primeiroemprego.entity.Lista;
import br.org.flem.primeiroemprego.seguranca.UsuarioLogadoMB;
import br.org.flem.primeiroemprego.util.Mensagem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ViewScoped
@ManagedBean
public class EgressosNaListaMB implements Serializable {

    @ManagedProperty(value = "#{listaDAO}")
    private ListaDAO listaDAO;

    @ManagedProperty(value = "#{egressoListaDAO}")
    private EgressoListaDAO egressoListaDAO;

    private List<Lista> listas;

    private Lista lista;

    private List<EgressoListaDTO> egressosLista;

    @ManagedProperty(value = "#{usuarioLogadoMB}")
    private UsuarioLogadoMB usuarioLogadoMB;

    private Integer qtdContatosEfetuados;
    private String porcentagemContatosEfetuados;

    @PostConstruct
    public void init() {
        List<Lista> listaRetorno = listaDAO.obterAssociadaOuCriadaPor(usuarioLogadoMB.getUsuario().getCodigoDominio(), usuarioLogadoMB.getUsuario().getUsername());
        List<Lista> listaOrdenar = new ArrayList<>();
        listas = new ArrayList<>();
        for (Lista item : listaRetorno) {
            if (!item.isConcluida() && item.getAtivo()) {
                listas.add(item);
            } else {
                listaOrdenar.add(item);
            }
        }
        listaRetorno = new ArrayList<>();
        for (Lista item : listaOrdenar) {
            if (item.isConcluida()) {
                listaRetorno.add(item);
            } else {
                listas.add(item);
            }
        }
        listas.addAll(listaRetorno);
    }

    public void atualizarListaDeEgressos() {
        egressosLista = egressoListaDAO.obterDaListaDTO(lista);
        calcularContatos();
    }

    private void qtdContatosEfetuadosNestaLista() {
        qtdContatosEfetuados = 0;
        for (EgressoListaDTO egressoLista : egressosLista) {
            if (egressoLista.getFeito()) {
                qtdContatosEfetuados++;
            }
        }
    }

    private void porcentagemContatosEfetuados() {
        porcentagemContatosEfetuados = (qtdContatosEfetuados * 100 / egressosLista.size()) + " %";
    }

    private void calcularContatos() {
        if (egressosLista != null && egressosLista.size() > 0) {
            qtdContatosEfetuadosNestaLista();
            porcentagemContatosEfetuados();
        }
    }

    public void retirar(Long id) {
        try {
            EgressoLista egressoLista = obterEgressoListaPorId(id);
            egressoListaDAO.excluir(egressoLista);
            atualizarListaDeEgressos();
            Mensagem.lancar("Egresso removido da lista");
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            Mensagem.lancar("Erro ao remover egresso da lista");
        }
    }

    public void desfazerFeito(Long id) {
        try {
            EgressoLista egressoLista = obterEgressoListaPorId(id);
            egressoLista.setFeito(Boolean.FALSE);
            egressoLista.setDeAcordo(null);
            egressoListaDAO.alterar(egressoLista);
            atualizarListaDeEgressos();
            Mensagem.lancar("Desfeita a informação de egresso feito na lista");
        } catch (Exception e) {
            Mensagem.lancar("Erro ao desfazer informação de egresso feito na lista ");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void informarDeAcordo(EgressoListaDTO dto, Boolean deAcordo) {
        try {
            EgressoLista listaEgresso = obterEgressoListaPorId(dto.getId());
            listaEgresso.setFeito(dto.getFeito());
            if (listaEgresso.getFeito() != null && !listaEgresso.getFeito()) {
                Mensagem.lancarMensagemWarn("Primeiro você deve confirmar se o contato foi feito");
            } else {
                listaEgresso.setDeAcordo(deAcordo);
                egressoListaDAO.alterar(listaEgresso);
                atualizarListaDeEgressos();
                Mensagem.lancar("Informação atualizada com sucesso");
            }
        } catch (Exception e) {
            Mensagem.lancarMensagemErro("Erro ao atualizar registro");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void salvarListagem() {
        try {
            for (EgressoListaDTO el : egressosLista) {
                if (el.getFeito()) {//Só Deve salvar os que foram feitos.
                    EgressoLista egressoLista = egressoListaDAO.obterPorPK(el.getId());
                    egressoLista.setFeito(el.getFeito());
                    egressoListaDAO.alterar(egressoLista);
                }
            }
            Mensagem.lancar("Lista Atualizada");
        } catch (Exception e) {
            Mensagem.lancar("Erro ao atualizar lista");
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    private EgressoLista obterEgressoListaPorId(Long id) {
        return egressoListaDAO.obterPorPK(id);
    }

    public ListaDAO getListaDAO() {
        return listaDAO;
    }

    public void setListaDAO(ListaDAO listaDAO) {
        this.listaDAO = listaDAO;
    }

    public EgressoListaDAO getEgressoListaDAO() {
        return egressoListaDAO;
    }

    public void setEgressoListaDAO(EgressoListaDAO egressoListaDAO) {
        this.egressoListaDAO = egressoListaDAO;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }

    public Lista getLista() {
        return lista;
    }

    public void setLista(Lista lista) {
        this.lista = lista;
    }

    public List<EgressoListaDTO> getEgressosLista() {
        return egressosLista;
    }

    public void setEgressosLista(List<EgressoListaDTO> egressosLista) {
        this.egressosLista = egressosLista;
    }

    public UsuarioLogadoMB getUsuarioLogadoMB() {
        return usuarioLogadoMB;
    }

    public void setUsuarioLogadoMB(UsuarioLogadoMB usuarioLogadoMB) {
        this.usuarioLogadoMB = usuarioLogadoMB;
    }

    public Integer getQtdContatosEfetuados() {
        return qtdContatosEfetuados;
    }

    public void setQtdContatosEfetuados(Integer qtdContatosEfetuados) {
        this.qtdContatosEfetuados = qtdContatosEfetuados;
    }

    public String getPorcentagemContatosEfetuados() {
        return porcentagemContatosEfetuados;
    }

    public void setPorcentagemContatosEfetuados(String porcentagemContatosEfetuados) {
        this.porcentagemContatosEfetuados = porcentagemContatosEfetuados;
    }

}

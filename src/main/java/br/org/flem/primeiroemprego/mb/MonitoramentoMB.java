package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ColaboradorDAO;
import br.org.flem.primeiroemprego.dao.DemandanteDAO;
import br.org.flem.primeiroemprego.dao.EscritorioRegionalDAO;
import br.org.flem.primeiroemprego.dao.MonitorDemandanteDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.dao.VagaDAO;
import br.org.flem.primeiroemprego.dto.DemandanteMunicipioDTO;
import br.org.flem.primeiroemprego.dto.MunicipioDTO;
import br.org.flem.primeiroemprego.entity.Colaborador;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.MonitorDemandante;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.exception.BusinessException;
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
 * Classe MonitoramentoMB
 *
 * @author <code>tscortes@flem.org.br</code> 10/04/2019
 * @version 1.0
 */
@ManagedBean
@ViewScoped
public class MonitoramentoMB implements Serializable {

    @ManagedProperty(value = "#{escritorioRegionalDAO}")
    private EscritorioRegionalDAO escritorioRegionalDAO;
    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO;
    @ManagedProperty(value = "#{demandanteDAO}")
    private DemandanteDAO demandanteDAO;
    @ManagedProperty(value = "#{colaboradorDAO}")
    private ColaboradorDAO colaboradorDAO;
    @ManagedProperty(value = "#{monitorDemandanteDAO}")
    private MonitorDemandanteDAO monitorDemandanteDAO;
    @ManagedProperty(value = "#{vagaDAO}")
    private VagaDAO vagaDAO;

    private List<EscritorioRegional> escritoriosRegionais;
    private EscritorioRegional escritorioSelecionado;
    private List<MunicipioDTO> municipios;
    private List<MunicipioDTO> municipiosSelecionados;
    private List<DemandanteMunicipioDTO> demandantes;
    private List<DemandanteMunicipioDTO> demandanteSelecionados;

    private List<Colaborador> monitores;
    private Colaborador monitorSelecionado;

    private Boolean selectDemandantes = Boolean.FALSE;
    private Boolean selectMunicipios = Boolean.FALSE;
    private List<MonitorDemandante> listaExclusao = new ArrayList<>();

    @PostConstruct
    public void init() {
        this.obterEscritoriosRegionais();
    }

    /**
     * Obter escritórios regionais
     */
    private void obterEscritoriosRegionais() {
        escritorioSelecionado = null;
        escritoriosRegionais = escritorioRegionalDAO.obterLista();
        this.obterMunicipios();
    }

    /**
     * Obter Municipios
     */
    public void obterMunicipios() {
        this.municipiosSelecionados = new ArrayList<>();
        if (this.escritorioSelecionado != null) {
            this.municipios = this.vagaDAO.obterMunicipioDTOPorEscritorioRegional(escritorioSelecionado);
        } else {
            this.municipios = this.vagaDAO.obterMunicipioDTOPorEscritoriosRegional(escritoriosRegionais);
        }
        obterDemandantes();
        obterColaboradores();
    }

    /**
     * Obter demandantes
     */
    public void obterDemandantes() {
        this.demandanteSelecionados = new ArrayList<>();
        this.demandantes = new ArrayList<>();
        if (this.municipiosSelecionados != null && !municipiosSelecionados.isEmpty()) {
            List<Municipio> municipios = new ArrayList<>();
            municipiosSelecionados.forEach(item -> {
                Municipio mun = municipioDAO.obterPorPK(item.getId());
                municipios.add(mun);
            });
            this.demandantes = this.demandanteDAO.obterDTOPorMunicipio(municipios);
            retirarDemandantesAssociados();
            obterAssociacoes();
        }

    }

    /**
     * Obter colaboradores
     */
    public void obterColaboradores() {
        this.monitorSelecionado = null;
        if (this.escritorioSelecionado != null) {
            this.monitores = this.colaboradorDAO.obterPorEscritorioRegional(escritorioSelecionado);
        } else {
            this.monitores = this.colaboradorDAO.obterListaAtivosOuInativos(Boolean.TRUE);
        }
    }

    public void obterAssociacoes() {
        demandanteSelecionados = new ArrayList<>();
        listaExclusao = new ArrayList<>();
        if (monitorSelecionado != null) {
            if (this.demandantes != null && !this.demandantes.isEmpty() && monitorSelecionado.getAssociacoes() != null && !monitorSelecionado.getAssociacoes().isEmpty()) {
                monitorSelecionado.getAssociacoes().forEach(item -> {

                    demandantes.forEach(demandante -> {
                        if (demandante.getIdDemandante().compareTo(item.getDemandante().getId()) == 0
                                && demandante.getIdMunicipio().compareTo(item.getMunicipio().getId()) == 0) {
                            DemandanteMunicipioDTO associado = new DemandanteMunicipioDTO();
                            associado.setIdDemandante(item.getDemandante().getId());
                            associado.setNomeDemandante(item.getDemandante().getNome());
                            associado.setIdMunicipio(item.getMunicipio().getId());
                            demandanteSelecionados.add(associado);
                        }
                    });
                });
            }
        }
    }

    /**
     * Associar os demandantes selecionados ao monitor selecionado
     */
    public void associar() {
        try {
            if (monitorSelecionado != null) {
                Colaborador colaborador = colaboradorDAO.obterPorPK(monitorSelecionado.getId());

                for (DemandanteMunicipioDTO dem : demandanteSelecionados) {
                    Demandante demandante = demandanteDAO.obterPorPK(dem.getIdDemandante());
                    Municipio municipio = municipioDAO.obterPorPK(dem.getIdMunicipio());
                    MonitorDemandante monitorDemandante = new MonitorDemandante(demandante, colaborador, municipio);
                    List<MonitorDemandante> lista = monitorDemandanteDAO.obterPorMunicipioDemandante(municipio, demandante);
                    if (lista == null || lista.isEmpty()) {
                        monitorDemandanteDAO.inserir(monitorDemandante);
                    }
                }
                removerAssociacoes();
                init();
                Mensagem.lancarMensagemInfo("Demandantes Associados Com sucesso");
            } else {
                Mensagem.lancarMensagemErro("Monitor não informado");
            }

        } catch (BusinessException ex) {
            Mensagem.lancarMensagemErro(ex.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(MonitoramentoMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Selecionar todos os demandantes
     */
    public void onSelectAllDemandantes() {
        demandanteSelecionados = new ArrayList<>();
        if (selectDemandantes) {
            demandantes.forEach(item -> {
                Demandante demandante = demandanteDAO.obterPorPK(item.getIdDemandante());
                Municipio municipio = municipioDAO.obterPorPK(item.getIdMunicipio());
                List<MonitorDemandante> lista = monitorDemandanteDAO.obterPorMunicipioDemandante(municipio, demandante);
                if (lista == null || lista.isEmpty()) {
                    demandanteSelecionados.add(item);
                }
            });
        }
    }

    /**
     * Selecionar todos os vagas
     */
    public void onSelectAllMunicipios() {
        municipiosSelecionados = new ArrayList<>();
        if (selectMunicipios) {
            municipios.forEach(item -> {
                municipiosSelecionados.add(item);
            });
        }
        obterDemandantes();
    }

    private void removerAssociacoes() {
        desassociar();
        if (listaExclusao != null && !listaExclusao.isEmpty()) {
            listaExclusao.forEach(item -> {
                try {
                    monitorDemandanteDAO.excluir(item);
                } catch (Exception ex) {
                    Logger.getLogger(MonitoramentoMB.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

        }
    }

    private void desassociar() {
        List<Municipio> municipios = new ArrayList<>();
        municipiosSelecionados.forEach(item -> {
            Municipio mun = municipioDAO.obterPorPK(item.getId());
            municipios.add(mun);
        });
        List<MonitorDemandante> associacoes = monitorDemandanteDAO.obterPorMunicipiosColaborador(municipios, monitorSelecionado);
        associacoes.forEach(associacao -> {
            Boolean contem = Boolean.FALSE;
            for (DemandanteMunicipioDTO item : demandanteSelecionados) {
                if (item.getIdDemandante().equals(associacao.getDemandante().getId())
                        && item.getIdMunicipio().equals(associacao.getMunicipio().getId())) {
                    contem = Boolean.TRUE;
                }
            }
            if (!contem) {
                listaExclusao.add(associacao);
            }
        });

    }

    /**
     *
     */
    private void retirarDemandantesAssociados() {
        List<Municipio> municipios = new ArrayList<>();
        municipiosSelecionados.forEach(item -> {
            Municipio mun = municipioDAO.obterPorPK(item.getId());
            municipios.add(mun);
        });
        if (monitorSelecionado != null && !demandantes.isEmpty()) {
            List<MonitorDemandante> associacoes = monitorDemandanteDAO.obterPorMunicipiosNaoColaborador(municipios, monitorSelecionado);
            associacoes.forEach(ass -> {
                demandantes.removeIf(item -> item.getIdDemandante().equals(ass.getDemandante().getId()) && item.getIdMunicipio().equals(ass.getMunicipio().getId()));
            });
        }
    }

    public EscritorioRegionalDAO getEscritorioRegionalDAO() {
        return escritorioRegionalDAO;
    }

    public void setEscritorioRegionalDAO(EscritorioRegionalDAO escritorioRegionalDAO) {
        this.escritorioRegionalDAO = escritorioRegionalDAO;
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }

    public DemandanteDAO getDemandanteDAO() {
        return demandanteDAO;
    }

    public void setDemandanteDAO(DemandanteDAO demandanteDAO) {
        this.demandanteDAO = demandanteDAO;
    }

    public List<EscritorioRegional> getEscritoriosRegionais() {
        return escritoriosRegionais;
    }

    public void setEscritoriosRegionais(List<EscritorioRegional> escritoriosRegionais) {
        this.escritoriosRegionais = escritoriosRegionais;
    }

    public EscritorioRegional getEscritorioSelecionado() {
        return escritorioSelecionado;
    }

    public void setEscritorioSelecionado(EscritorioRegional escritorioSelecionado) {
        this.escritorioSelecionado = escritorioSelecionado;
    }

    public List<MunicipioDTO> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<MunicipioDTO> municipios) {
        this.municipios = municipios;
    }

    public List<MunicipioDTO> getMunicipiosSelecionados() {
        return municipiosSelecionados;
    }

    public void setMunicipiosSelecionados(List<MunicipioDTO> municipiosSelecionados) {
        this.municipiosSelecionados = municipiosSelecionados;
    }

    public List<DemandanteMunicipioDTO> getDemandantes() {
        return demandantes;
    }

    public void setDemandantes(List<DemandanteMunicipioDTO> demandantes) {
        this.demandantes = demandantes;
    }

    public List<DemandanteMunicipioDTO> getDemandanteSelecionados() {
        return demandanteSelecionados;
    }

    public void setDemandanteSelecionados(List<DemandanteMunicipioDTO> demandanteSelecionados) {
        this.demandanteSelecionados = demandanteSelecionados;
    }

    public List<Colaborador> getMonitores() {
        return monitores;
    }

    public void setMonitores(List<Colaborador> monitores) {
        this.monitores = monitores;
    }

    public Colaborador getMonitorSelecionado() {
        return monitorSelecionado;
    }

    public void setMonitorSelecionado(Colaborador monitorSelecionado) {
        this.monitorSelecionado = monitorSelecionado;
    }

    public ColaboradorDAO getColaboradorDAO() {
        return colaboradorDAO;
    }

    public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
        this.colaboradorDAO = colaboradorDAO;
    }

    public Boolean getSelectDemandantes() {
        return selectDemandantes;
    }

    public void setSelectDemandantes(Boolean selectDemandantes) {
        this.selectDemandantes = selectDemandantes;
    }

    public Boolean getSelectMunicipios() {
        return selectMunicipios;
    }

    public void setSelectMunicipios(Boolean selectMunicipios) {
        this.selectMunicipios = selectMunicipios;
    }

    public MonitorDemandanteDAO getMonitorDemandanteDAO() {
        return monitorDemandanteDAO;
    }

    public void setMonitorDemandanteDAO(MonitorDemandanteDAO monitorDemandanteDAO) {
        this.monitorDemandanteDAO = monitorDemandanteDAO;
    }

    public VagaDAO getVagaDAO() {
        return vagaDAO;
    }

    public void setVagaDAO(VagaDAO vagaDAO) {
        this.vagaDAO = vagaDAO;
    }

    public List<MonitorDemandante> getListaExclusao() {
        return listaExclusao;
    }

    public void setListaExclusao(List<MonitorDemandante> listaExclusao) {
        this.listaExclusao = listaExclusao;
    }

}

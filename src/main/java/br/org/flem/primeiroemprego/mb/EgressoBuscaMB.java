package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dto.FiltroEgressoDTO;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Demandante;
import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import br.org.flem.primeiroemprego.entity.EstadoColunasEgressoDTO;
import br.org.flem.primeiroemprego.entity.Formacao;
import br.org.flem.primeiroemprego.entity.ListaEgressoDTO;
import br.org.flem.primeiroemprego.entity.Municipio;
import br.org.flem.primeiroemprego.negocio.CategoriaDaSituacaoRN;
import br.org.flem.primeiroemprego.negocio.DemandanteRN;
import br.org.flem.primeiroemprego.negocio.EgressoRN;
import br.org.flem.primeiroemprego.negocio.EscritorioRegionalRN;
import br.org.flem.primeiroemprego.negocio.FormacaoRN;
import br.org.flem.primeiroemprego.negocio.MunicipioRN;
import br.org.flem.primeiroemprego.negocio.VagaRN;
import br.org.flem.primeiroemprego.util.CoreUtil;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class EgressoBuscaMB implements Serializable {

    @ManagedProperty(value = "#{egressoRN}")
    private EgressoRN egressoRN;
    @ManagedProperty(value = "#{formacaoRN}")
    private FormacaoRN formacaoRN;
    @ManagedProperty(value = "#{municipioRN}")
    private MunicipioRN municipioRN;
    @ManagedProperty(value = "#{vagaRN}")
    private VagaRN vagaRN;
    @ManagedProperty(value = "#{demandanteRN}")
    private DemandanteRN demandanteRN;
    @ManagedProperty(value = "#{escritorioRegionalRN}")
    private EscritorioRegionalRN escritorioRegionalRN;
    @ManagedProperty(value = "#{categoriaDaSituacaoRN}")
    private CategoriaDaSituacaoRN categoriaDaSituacaoRN;

    private List<ListaEgressoDTO> egressos;
    private FiltroEgressoDTO filtro;
    private EstadoColunasEgressoDTO estadoDasColunas;

    private List<EscritorioRegional> escritoriosRegionais;
    private List<Municipio> municipios;
    private List<Demandante> demandantes;
    private List<Formacao> formacoes;
    private List<CategoriaDaSituacao> categorias;

    private List<EscritorioRegional> escritoriosSelecionados;
    private List<Municipio> municipiosSelecionado;
    private List<Formacao> formacoesSelecionado;
    private List<Demandante> demandantesSelecionados;
    private List<CategoriaDaSituacao> categoriasSelecionadas;

    private boolean selectEsc;
//    private boolean selectTer;
    private boolean selectMun;
    private boolean selectDem;
    private boolean selectCat;
    private boolean selectFor;

    @PostConstruct
    public void init() {
        obterEscritoriosRegionais();
        obterCategoria();
        obterFormacoes();
        filtro = new FiltroEgressoDTO();
        estadoDasColunas = new EstadoColunasEgressoDTO();
    }

    /**
     * Obter escritórios regionais
     */
    private void obterEscritoriosRegionais() {
        escritoriosRegionais = escritorioRegionalRN.obterLista();
        EscritorioRegional escr = new EscritorioRegional();
        escr.setId(0l);
        escr.setNome("Escritório não Implementado");
        escritoriosRegionais.add(0, escr);
        obterMunicipios();
    }

    private void obterCategoria() {
        this.categorias = categoriaDaSituacaoRN.obterLista();
    }

    public void obterMunicipios() {
        if (this.escritoriosRegionais != null && !this.escritoriosRegionais.isEmpty()) {
            this.municipiosSelecionado = new ArrayList<>();
            this.municipios = this.municipioRN.obterPorEscritoriosRegionais((List<EscritorioRegional>) CoreUtil.nvl(this.escritoriosSelecionados, this.escritoriosRegionais));
        }
        obterDemandantes();
    }

    public void obterDemandantes() {
        if(this.municipios != null && !this.municipios.isEmpty()){
            demandantesSelecionados = new ArrayList<>();
            this.demandantes = this.demandanteRN.obterPorMunicipios((List<Municipio>) CoreUtil.nvl(this.municipiosSelecionado, this.municipios));
        }
    }

    private void obterFormacoes() {
        formacoes = formacaoRN.obterLista();
    }

    /**
     * Buscar egressos pelo filtro
     */
    public void buscarEgressos() {
        getFiltros();
        egressos = egressoRN.obterListaEgressoDTO(filtro);
    }

    public String limpar() {
        this.escritoriosSelecionados = new ArrayList<>();
        this.municipiosSelecionado = new ArrayList<>();
        this.categoriasSelecionadas = new ArrayList<>();
        this.demandantesSelecionados = new ArrayList<>();
        this.formacoesSelecionado = new ArrayList<>();
        this.selectDem = false;
        this.selectEsc = false;
        this.selectMun = false;
        this.selectFor = false;
        this.selectCat = false;
        this.init();
        return "";
    }

    /**
     * Selecionar colunas para exibição
     *
     * @param e
     */
    public void onToggle(ToggleEvent e) {
        estadoDasColunas.set((Integer) e.getData(), e.getVisibility() == Visibility.VISIBLE);
    }

    private void getFiltros() {
        
        if (formacoesSelecionado != null) {
            filtro.setFormacoes((List<Formacao>) CoreUtil.nvl(this.formacoesSelecionado, this.formacoes));
        }
        if (escritoriosSelecionados != null) {
            filtro.setEscritoriosRegionais((List<EscritorioRegional>) CoreUtil.nvl(this.escritoriosSelecionados, this.escritoriosRegionais));
        }
        if (demandantesSelecionados != null) {
            filtro.setDemandantes((List<Demandante>) CoreUtil.nvl(this.demandantesSelecionados, this.demandantes));
        }
        if (municipiosSelecionado != null) {
            filtro.setMunicipios((List<Municipio>) CoreUtil.nvl(this.municipiosSelecionado, this.municipios));
        }
        if (categoriasSelecionadas != null) {
            filtro.setCategorias((List<CategoriaDaSituacao>) CoreUtil.nvl(this.categoriasSelecionadas, this.categorias));
        }
        
    }

    public void onSelectAll(Integer val) {
        switch (val) {
            case 1:
                if (selectEsc) {
                    escritoriosSelecionados = new ArrayList<>();
                    escritoriosSelecionados.addAll(escritoriosRegionais);
                } else {
                    escritoriosSelecionados.clear();
                }
                this.obterMunicipios();
                break;
            case 2:
                if (selectMun) {
                    municipiosSelecionado = new ArrayList<>();
                    municipiosSelecionado.addAll(municipios);
                } else {
                    municipiosSelecionado.clear();
                }
                this.obterDemandantes();
                break;
            case 3:
                if (selectDem) {
                    demandantesSelecionados = new ArrayList<>();
                    demandantesSelecionados.addAll(demandantes);
                } else {
                    demandantesSelecionados.clear();
                }
                break;
            case 4:
                if (selectCat) {
                    categoriasSelecionadas = new ArrayList<>();
                    categoriasSelecionadas.addAll(categorias);
                } else {
                    categoriasSelecionadas.clear();
                }
                break;
            case 5:
                if (selectFor) {
                    formacoesSelecionado = new ArrayList<>();
                    formacoesSelecionado.addAll(formacoes);
                } else {
                    formacoesSelecionado.clear();
                }
                break;
        }
    }

    public EgressoRN getEgressoRN() {
        return egressoRN;
    }

    public void setEgressoRN(EgressoRN egressoRN) {
        this.egressoRN = egressoRN;
    }

    public List<ListaEgressoDTO> getEgressos() {
        return egressos;
    }

    public void setEgressos(List<ListaEgressoDTO> egressos) {
        this.egressos = egressos;
    }

    public FiltroEgressoDTO getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroEgressoDTO filtro) {
        this.filtro = filtro;
    }

    public EstadoColunasEgressoDTO getEstadoDasColunas() {
        return estadoDasColunas;
    }

    public void setEstadoDasColunas(EstadoColunasEgressoDTO estadoDasColunas) {
        this.estadoDasColunas = estadoDasColunas;
    }

    public List<Municipio> getMunicipios() {
        return municipios;
    }

    public void setMunicipios(List<Municipio> municipios) {
        this.municipios = municipios;
    }

    public List<Formacao> getFormacoes() {
        return formacoes;
    }

    public void setFormacoes(List<Formacao> formacoes) {
        this.formacoes = formacoes;
    }

    public FormacaoRN getFormacaoRN() {
        return formacaoRN;
    }

    public void setFormacaoRN(FormacaoRN formacaoRN) {
        this.formacaoRN = formacaoRN;
    }

    public MunicipioRN getMunicipioRN() {
        return municipioRN;
    }

    public void setMunicipioRN(MunicipioRN municipioRN) {
        this.municipioRN = municipioRN;
    }

    public VagaRN getVagaRN() {
        return vagaRN;
    }

    public void setVagaRN(VagaRN vagaRN) {
        this.vagaRN = vagaRN;
    }

    public List<Demandante> getDemandantes() {
        return demandantes;
    }

    public void setDemandantes(List<Demandante> demandantes) {
        this.demandantes = demandantes;
    }

    public DemandanteRN getDemandanteRN() {
        return demandanteRN;
    }

    public void setDemandanteRN(DemandanteRN demandanteRN) {
        this.demandanteRN = demandanteRN;
    }

    public EscritorioRegionalRN getEscritorioRegionalRN() {
        return escritorioRegionalRN;
    }

    public void setEscritorioRegionalRN(EscritorioRegionalRN escritorioRegionalRN) {
        this.escritorioRegionalRN = escritorioRegionalRN;
    }

    public CategoriaDaSituacaoRN getCategoriaDaSituacaoRN() {
        return categoriaDaSituacaoRN;
    }

    public void setCategoriaDaSituacaoRN(CategoriaDaSituacaoRN categoriaDaSituacaoRN) {
        this.categoriaDaSituacaoRN = categoriaDaSituacaoRN;
    }

    public List<EscritorioRegional> getEscritoriosRegionais() {
        return escritoriosRegionais;
    }

    public void setEscritoriosRegionais(List<EscritorioRegional> escritoriosRegionais) {
        this.escritoriosRegionais = escritoriosRegionais;
    }

    public List<CategoriaDaSituacao> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<CategoriaDaSituacao> categorias) {
        this.categorias = categorias;
    }

    public List<EscritorioRegional> getEscritoriosSelecionados() {
        return escritoriosSelecionados;
    }

    public void setEscritoriosSelecionados(List<EscritorioRegional> escritoriosSelecionados) {
        this.escritoriosSelecionados = escritoriosSelecionados;
    }

    public List<Municipio> getMunicipiosSelecionado() {
        return municipiosSelecionado;
    }

    public void setMunicipiosSelecionado(List<Municipio> municipiosSelecionado) {
        this.municipiosSelecionado = municipiosSelecionado;
    }

    public List<Formacao> getFormacoesSelecionado() {
        return formacoesSelecionado;
    }

    public void setFormacoesSelecionado(List<Formacao> formacoesSelecionado) {
        this.formacoesSelecionado = formacoesSelecionado;
    }

    public List<Demandante> getDemandantesSelecionados() {
        return demandantesSelecionados;
    }

    public void setDemandantesSelecionados(List<Demandante> demandantesSelecionados) {
        this.demandantesSelecionados = demandantesSelecionados;
    }

    public List<CategoriaDaSituacao> getCategoriasSelecionadas() {
        return categoriasSelecionadas;
    }

    public void setCategoriasSelecionadas(List<CategoriaDaSituacao> categoriasSelecionadas) {
        this.categoriasSelecionadas = categoriasSelecionadas;
    }

    public boolean isSelectEsc() {
        return selectEsc;
    }

    public void setSelectEsc(boolean selectEsc) {
        this.selectEsc = selectEsc;
    }

    public boolean isSelectMun() {
        return selectMun;
    }

    public void setSelectMun(boolean selectMun) {
        this.selectMun = selectMun;
    }

    public boolean isSelectDem() {
        return selectDem;
    }

    public void setSelectDem(boolean selectDem) {
        this.selectDem = selectDem;
    }

    public boolean isSelectCat() {
        return selectCat;
    }

    public void setSelectCat(boolean selectCat) {
        this.selectCat = selectCat;
    }

    public boolean isSelectFor() {
        return selectFor;
    }

    public void setSelectFor(boolean selectFor) {
        this.selectFor = selectFor;
    }
    
    
}

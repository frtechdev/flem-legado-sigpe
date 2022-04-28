package br.org.flem.primeiroemprego.mb;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.persistencia.dto.SituacaoFuncionarioEnum;
import br.org.flem.fw.service.RH;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.primeiroemprego.entity.Mes;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class RelatorioContratadosMB implements Serializable{

    private List<Funcionario> contratados = new ArrayList<Funcionario>();
    private List<Funcionario> contratadosDesligados = new ArrayList<Funcionario>();
    private Mes meses =  new Mes();

    private RH rhServico = new RHServico();
    private String mesAno;
    private String mesAnoRecisao;
    private String admissao;
    

    @PostConstruct
    public void init() {
        contratados = rhServico.obterFuncionariosPorDepartamentoSituacao(PropertiesUtil.getProperty(("departamentoPrimeiroEmprego")), SituacaoFuncionarioEnum.ATIVO );
        contratadosDesligados = rhServico.obterFuncionariosPorDepartamentoSituacao(PropertiesUtil.getProperty(("departamentoPrimeiroEmprego")), SituacaoFuncionarioEnum.DESLIGADO);
    }

    public List<Funcionario> getContratados() {
        return contratados;
    }

    public void setContratados(List<Funcionario> contratados) {
        this.contratados = contratados;
    }

    public List<Funcionario> getContratadosDesligados() {
        return contratadosDesligados;
    }

    public void setContratadosDesligados(List<Funcionario> contratadosDesligados) {
        this.contratadosDesligados = contratadosDesligados;
    }

    public Mes getMeses() {
        return meses;
    }

    public void setMeses(Mes meses) {
        this.meses = meses;
    }   

    public String getMesAno() {
        return mesAno;
    }

    public void setMesAno(String mesAno) {
        this.mesAno = mesAno;
    }

    public String getAdmissao() {
        return admissao;
    }

    public void setAdmissao(String admissao) {
        this.admissao = admissao; 
    }

    public String getMesAnoRecisao() {
        return mesAnoRecisao;
    }

    public void setMesAnoRecisao(String mesAnoRecisao) {
        this.mesAnoRecisao = mesAnoRecisao;
    }
    
    
    
}

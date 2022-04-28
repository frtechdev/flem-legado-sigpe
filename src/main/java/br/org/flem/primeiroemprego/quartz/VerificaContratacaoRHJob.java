package br.org.flem.primeiroemprego.quartz;

import br.org.flem.commons.util.PropertiesUtil;
import br.org.flem.fw.persistencia.dto.Funcionario;
import br.org.flem.fw.persistencia.dto.SituacaoFuncionarioEnum;
import br.org.flem.fw.service.impl.RHServico;
import br.org.flem.primeiroemprego.dao.EgressoDAO;
import br.org.flem.primeiroemprego.entity.Egresso;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author emsilva
 */
public class VerificaContratacaoRHJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try{
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("Inicio o JOB");
            System.out.println("");
            System.out.println("");
            System.out.println("");
            System.out.println("");

            RHServico rhServico = new RHServico();
            EgressoDAO egressoDAO = new EgressoDAO();
            String codigoDepartamento = PropertiesUtil.getProperty(("departamentoPrimeiroEmprego"));
            System.out.println("Departamento "+codigoDepartamento);
            List<Funcionario> funcionarios = rhServico.obterFuncionariosPorDepartamentoSituacao(codigoDepartamento, null);
            System.out.println("Funcionarios "+funcionarios.size());
            List<Egresso> egressosNaoContratado = egressoDAO.obterNaoContratados();
            System.out.println("Egressos não contratados "+egressosNaoContratado.size());

            for(Egresso egressoNaoContratado : egressosNaoContratado){
                for(Funcionario funcionario : funcionarios){
                    String cpf = egressoNaoContratado.getCpf().replace(".", "").replace("-", "");
                    if(cpf.equals(funcionario.getCpf()) 
                            && funcionario.getAdmissao().before(new Date()) && funcionario.getSituacao() != SituacaoFuncionarioEnum.DESLIGADO){
                        System.out.println(egressoNaoContratado.getNome());
                        egressoNaoContratado.setMatriculaFlem(funcionario.getCodigoDominio().toString());
                        egressoNaoContratado.setDataAdmissao(funcionario.getAdmissao());
                        egressoDAO.alterar(egressoNaoContratado);
                    }
                }
            }
        }catch(Exception e){
            Logger.getLogger(VerificaContratacaoRHJob.class.getName()).log(Level.SEVERE, null, e);
        }
        finally{
            Logger.getLogger(VerificaContratacaoRHJob.class.getName()).log(Level.INFO, "Fim job Verifica Contratacao Dominio");
        }
        
    }
    
}

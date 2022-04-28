package br.org.flem.primeiroemprego.quartz;

import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.dao.DistanciaEntreMunicipiosDAO;
import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.entity.Municipio;
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
public class CalculaDistanciaEntreMunicipiosJob implements Job{

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        
        Date horaInicial = new Date();
        Logger.getLogger(CalculaDistanciaEntreMunicipiosJob.class.getName()).log(Level.INFO, "Cálculo do Municipio Iniciado: {0} ", Data.formataData(horaInicial, "dd/MM/yyyy HH:mm"));
        
        MunicipioDAO municipioDAO = new MunicipioDAO();
        DistanciaEntreMunicipiosDAO distanciaEntreMunicipiosDAO = new DistanciaEntreMunicipiosDAO();
        List<Municipio> municipios = municipioDAO.obterLista();
        for(Municipio mOrigem : municipios){
            Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.INFO, "Cálculo do Municipio {0}", mOrigem.getNome());
            try {
                distanciaEntreMunicipiosDAO.calcularDistancias(mOrigem,municipios);
            } catch (Exception ex) {
                Logger.getLogger(DistanciaEntreMunicipiosDAO.class.getName()).log(Level.SEVERE, "Acabou a Cota", ex);
                break;
            }
        }
        Date horaFinal = new Date();
        Logger.getLogger(CalculaDistanciaEntreMunicipiosJob.class.getName()).log(Level.INFO, "Cálculo do Municipio Finalizado: {0} ", Data.formataData(horaFinal, "dd/MM/yyyy HH:mm"));
        Logger.getLogger(CalculaDistanciaEntreMunicipiosJob.class.getName()).log(Level.INFO, "Duração: {0} ", Data.dataDiff(horaInicial, horaFinal));
    }
    
}

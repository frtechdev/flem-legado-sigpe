package br.org.flem.primeiroemprego.util;

import br.org.flem.primeiroemprego.quartz.CalculaDistanciaEntreMunicipiosJob;
import br.org.flem.primeiroemprego.quartz.VerificaContratacaoRHJob;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.JobBuilder.*;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import static org.quartz.TriggerBuilder.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class ContextListener implements ServletContextListener {
    private static Scheduler sched;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            sched = StdSchedulerFactory.getDefaultScheduler();
            sched.start();
            
            JobDetail jobDetail = newJob(VerificaContratacaoRHJob.class).withIdentity("VerificaContratacaoRHJob", Scheduler.DEFAULT_GROUP).build();
            JobDetail jobDetail2 = newJob(CalculaDistanciaEntreMunicipiosJob.class).withIdentity("CalculaDistanciaEntreMunicipiosJob", Scheduler.DEFAULT_GROUP).build();
            
            Trigger trigger = newTrigger().withIdentity("VerificaContratacaoRHJob").withSchedule(cronSchedule("0 0 1 ? * MON-FRI")).build();
            //Trigger trigger = newTrigger().withIdentity("VerificaContratacaoRHJob").startAt(futureDate(1, IntervalUnit.MINUTE)).forJob("VerificaContratacaoRHJob").build();
            
            Trigger trigger2 = newTrigger().withIdentity("CalculaDistanciaEntreMunicipiosJob").withSchedule(cronSchedule("0 00 05 ? * MON-SUN")).build();

            sched.scheduleJob(jobDetail, trigger);
            sched.scheduleJob(jobDetail2, trigger2);
            
        } catch (Exception ex) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Aplicação Iniciada");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ExecutorThreads.shutdownNow();//Não fazer isto possivelmente estava gerando memory leak
        try{
            sched.shutdown();
        } catch (Exception e) {
            Logger.getLogger(ContextListener.class.getName()).log(Level.SEVERE, null, e);
        }
        JPAUtil.closeEntityFactory();
        System.out.println("Aplicação Parada");
    }
    
}

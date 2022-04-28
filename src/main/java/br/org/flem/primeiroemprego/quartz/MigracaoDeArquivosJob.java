package br.org.flem.primeiroemprego.quartz;

import br.org.flem.primeiroemprego.dao.DocumentoDoEgressoDAO;
import br.org.flem.primeiroemprego.dao.ModeloDeOficioDAO;
import br.org.flem.primeiroemprego.dao.OficioDAO;
import br.org.flem.primeiroemprego.dto.ArquivoDTO;
import br.org.flem.primeiroemprego.entity.DocumentoDoEgresso;
import br.org.flem.primeiroemprego.entity.ModeloDeOficio;
import br.org.flem.primeiroemprego.entity.Oficio;
import br.org.flem.primeiroemprego.exception.BusinessException;
import br.org.flem.primeiroemprego.mb.DocumentoDoEgressoMB;
import br.org.flem.primeiroemprego.mb.ModeloDeOficioCadastroMB;
import br.org.flem.primeiroemprego.mb.OficioCadastroMB;
import br.org.flem.primeiroemprego.util.CoreUtil;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Response;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 *
 * @author tscortes
 */
public class MigracaoDeArquivosJob implements Job{

    
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        migrarModelosDeOficio();
//        migrarOficio();
//        migrarDocumentoDoEgresso();
    }
        
    private void migrarModelosDeOficio(){
        List<ModeloDeOficio> modelos = new ModeloDeOficioDAO().findAll();
        if(!modelos.isEmpty()){
            for( ModeloDeOficio oficio : modelos){
                ModeloDeOficio old = new ModeloDeOficioDAO().obterPorPK(oficio.getId());
                if( old.getArquivo() != null && old.getArquivo().length > 0 ){
                    postModeloDeOficio(old.getId(), old.getArquivo(), old.getNome());
                }
            }
        }
    }
    
    private void migrarOficio(){
        List<Oficio> documents = new OficioDAO().findAll();
        if(!documents.isEmpty()){
            for( Oficio oficio : documents){
                Oficio old = new OficioDAO().obterPorPK(oficio.getId());
                if( old.getArquivo() != null && old.getArquivo().length > 0 )
                    postOficio(old, old.getArquivo(), old.getNome());
            }
        }
    }
        
    private void migrarDocumentoDoEgresso(){
        List<DocumentoDoEgresso> documents = new DocumentoDoEgressoDAO().findAll();
        if(!documents.isEmpty()){
            for( DocumentoDoEgresso oficio : documents){
                DocumentoDoEgresso old = new DocumentoDoEgressoDAO().obterPorPK(oficio.getId());
                if( old.getArquivo() != null && old.getArquivo().length > 0 )
                    postDocumentoDoEgresso(old.getId(), old.getArquivo(), old.getNome());
            }
        }
    }
    
    private void postModeloDeOficio(Long id, byte[] contents, String fileName){
        try {
            Response response = new ModeloDeOficioCadastroMB().post(contents, fileName);
            ArquivoDTO dto = (ArquivoDTO) CoreUtil.jsonToObject(response.readEntity(String.class), ArquivoDTO.class);
//            new ModeloDeOficioCadastroMB().update(id, dto.getFilePath());
        } catch (BusinessException ex) {
            Logger.getLogger(MigracaoDeArquivosJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MigracaoDeArquivosJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void postOficio(Oficio oficio, byte[] contents, String fileName){
        try {
            Response response = new OficioCadastroMB().post(contents, fileName, oficio);
            new OficioCadastroMB().update(oficio, response);
        } catch (BusinessException ex) {
            Logger.getLogger(MigracaoDeArquivosJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MigracaoDeArquivosJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void postDocumentoDoEgresso(Long id, byte[] contents, String fileName){
        try {
            DocumentoDoEgresso doc = new DocumentoDoEgressoDAO().obterPorPK(id);
            Response response = new DocumentoDoEgressoMB().post(id, contents, fileName, doc.getEgresso().getMatriculaFlem());
//            new DocumentoDoEgressoMB().update(id, response);
        } catch (BusinessException ex) {
            Logger.getLogger(MigracaoDeArquivosJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(MigracaoDeArquivosJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

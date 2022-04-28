
package br.org.flem.primeiroemprego.entity.util;

import br.org.flem.fwe.util.Data;
import br.org.flem.primeiroemprego.entity.CategoriaDaSituacao;
import br.org.flem.primeiroemprego.entity.Egresso;
import br.org.flem.primeiroemprego.entity.Situacao;
import br.org.flem.primeiroemprego.entity.Vaga;
import br.org.flem.primeiroemprego.util.entity.CamposEgressosUtil;
import java.text.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author emsilva
 */
public class CamposEgressoUtilTest {
    
    public CamposEgressoUtilTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testGetInformacaoSitaucao() {
        Egresso egresso = new Egresso();
        egresso.setVaga(new Vaga());
        
        Situacao situacao = new  Situacao();
        situacao.setNome("Teste");
        
        CategoriaDaSituacao categoria = new  CategoriaDaSituacao();
        categoria.setNome("Teste");
        situacao.setCategoria(categoria);

        egresso.getVaga().setSituacao(situacao);
        assertEquals(CamposEgressosUtil.getInformacao(egresso,"Situação"),"Teste - Teste");
    }
    
    @Test
    public void testGetInformacaoDate() throws ParseException {
        Egresso egresso = new Egresso();
        egresso.setVaga(new Vaga());
        egresso.setDataNascimento(Data.formataData("05/05/2017"));

        assertEquals(CamposEgressosUtil.getInformacao(egresso,"Data de Nascimento"),"05/05/2017");
    }

    @Test
    public void testSetInformacaoSitaucao() throws Exception {
        Egresso egresso = new Egresso();
        egresso.setVaga(new Vaga());
        
        Situacao situacao = new  Situacao();
        situacao.setNome("Teste");
        
        CategoriaDaSituacao categoria = new  CategoriaDaSituacao();
        categoria.setNome("Teste");
        situacao.setCategoria(categoria);
        
        CamposEgressosUtil.setInformacao(egresso,"Situação",situacao);
        assertEquals(egresso.getVaga().getSituacao().getNome(),"Teste");
    }
    
    @Test
    public void testSetInformacaoDate() throws Exception {
        Egresso egresso = new Egresso();
        egresso.setVaga(new Vaga());

        CamposEgressosUtil.setInformacao(egresso,"Data de Nascimento",Data.formataData("05/05/2017"));
        assertEquals(egresso.getDataNascimento(),Data.formataData("05/05/2017"));

    }

}    

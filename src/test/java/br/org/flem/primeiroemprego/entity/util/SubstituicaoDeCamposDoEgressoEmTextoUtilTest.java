/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.org.flem.primeiroemprego.entity.util;

import br.org.flem.primeiroemprego.util.entity.SubstituicaoDeCamposDoEgressoEmTextoUtil;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emsilva
 */
public class SubstituicaoDeCamposDoEgressoEmTextoUtilTest {
    
    public SubstituicaoDeCamposDoEgressoEmTextoUtilTest() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testUmGrupo() {
        String texto = "das das dasdasasdadsa {dasdasdasda}";
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(texto);
        assertTrue(scet.procurarSubstituicao());
        System.out.println("-"+scet.textoParaSubstituir()+"-");
        assertFalse(scet.procurarSubstituicao());
    }
    
    @Test
    public void testDoisGrupos() {
        String texto = "das das dasdasasdadsa {dasdasdasda} {das das}";
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(texto);
        assertTrue(scet.procurarSubstituicao());
        System.out.println("-"+scet.textoParaSubstituir()+"-");
        assertTrue(scet.procurarSubstituicao());
        System.out.println("-"+scet.textoParaSubstituir()+"-");
        assertFalse(scet.procurarSubstituicao());
    }
    
    @Test
    public void testNenhumGrupo() {
        String texto = "das das dasdasasdadsa {dasd \r\n asdasda}";
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(texto);
        boolean a = scet.procurarSubstituicao();
        assertFalse(a);
    }
    
    @Test
    public void testUmGrupoComNumero() {
        String texto = "das das dasdasasdadsa {dasd1 asdasda}";
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(texto);
        boolean a = scet.procurarSubstituicao();
        System.out.println(scet.textoParaSubstituir());
        assertTrue(a);
    }
    
    @Test
    public void testUmGrupoComAcento() {
        String texto = "Das das dasdasasdadsa {dasd1 asdaçda}";
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(texto);
        boolean a = scet.procurarSubstituicao();
        System.out.println(scet.textoParaSubstituir());
        assertTrue(a);
    }
    
    @Test
    public void testUmGrupoComDoisEspacos() {
        String texto = "Das das dasdasasdadsa {dasd1 asdada das}";
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(texto);
        boolean a = scet.procurarSubstituicao();
        System.out.println(scet.textoParaSubstituir());
        assertTrue(a);
    }
    
    
    @Test
    public void testComVirgula() {
        String texto = "Das das dasdasasdadsa {dasidj, aisdhausdha}";
        SubstituicaoDeCamposDoEgressoEmTextoUtil scet = new SubstituicaoDeCamposDoEgressoEmTextoUtil(texto);
        boolean a = scet.procurarSubstituicao();
        assertFalse(a);
    }
}

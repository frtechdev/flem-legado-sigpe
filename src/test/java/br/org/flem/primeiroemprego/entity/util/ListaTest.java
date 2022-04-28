package br.org.flem.primeiroemprego.entity.util;

import br.org.flem.primeiroemprego.dao.ListaDAO;
import br.org.flem.primeiroemprego.entity.Lista;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ListaTest {
    
    public ListaTest() {
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
    public void testCampoPendenteConcluido() throws Exception {
   /*     try{
            ListaDAO listaDAO = new ListaDAO();
            Lista listaConcluida = listaDAO.obterPorPK(3l);
            System.out.println(listaConcluida.isConcluida());
            assertEquals(listaConcluida.isConcluida(), true);
        }catch(Exception ex){
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
*/    }
}

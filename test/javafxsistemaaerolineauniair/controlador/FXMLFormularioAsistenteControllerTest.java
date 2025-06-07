/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafxsistemaaerolineauniair.modelo.dao.AsistenteVueloDAO;
import javafxsistemaaerolineauniair.modelo.pojo.AsistenteVuelo;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class FXMLFormularioAsistenteControllerTest {
    
    public FXMLFormularioAsistenteControllerTest() {
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

    /**
     * Test of initialize method, of class FXMLFormularioAsistenteController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FXMLFormularioAsistenteController instance = new FXMLFormularioAsistenteController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inicializarInformacion method, of class FXMLFormularioAsistenteController.
     */
    @Test
    public void testInicializarInformacion() {
        System.out.println("inicializarInformacion");
        AsistenteVuelo asistente = null;
        AsistenteVueloDAO dao = null;
        FXMLFormularioAsistenteController instance = new FXMLFormularioAsistenteController();
        instance.inicializarInformacion(asistente, dao);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConfirmado method, of class FXMLFormularioAsistenteController.
     */
    @Test
    public void testIsConfirmado() {
        System.out.println("isConfirmado");
        FXMLFormularioAsistenteController instance = new FXMLFormularioAsistenteController();
        boolean expResult = false;
        boolean result = instance.isConfirmado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

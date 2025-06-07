/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafxsistemaaerolineauniair.modelo.dao.VueloDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
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
public class FXMLFormularioVueloControllerTest {
    
    public FXMLFormularioVueloControllerTest() {
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
     * Test of initialize method, of class FXMLFormularioVueloController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FXMLFormularioVueloController instance = new FXMLFormularioVueloController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inicializarInformacion method, of class FXMLFormularioVueloController.
     */
    @Test
    public void testInicializarInformacion() {
        System.out.println("inicializarInformacion");
        Vuelo vuelo = null;
        VueloDAO vueloDAO = null;
        FXMLFormularioVueloController instance = new FXMLFormularioVueloController();
        instance.inicializarInformacion(vuelo, vueloDAO);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConfirmado method, of class FXMLFormularioVueloController.
     */
    @Test
    public void testIsConfirmado() {
        System.out.println("isConfirmado");
        FXMLFormularioVueloController instance = new FXMLFormularioVueloController();
        boolean expResult = false;
        boolean result = instance.isConfirmado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

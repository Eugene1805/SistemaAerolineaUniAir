/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafxsistemaaerolineauniair.modelo.dao.AeropuertoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
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
public class FXMLFormularioAeropuertoControllerTest {
    
    public FXMLFormularioAeropuertoControllerTest() {
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
     * Test of initialize method, of class FXMLFormularioAeropuertoController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FXMLFormularioAeropuertoController instance = new FXMLFormularioAeropuertoController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inicializarInformacion method, of class FXMLFormularioAeropuertoController.
     */
    @Test
    public void testInicializarInformacion() {
        System.out.println("inicializarInformacion");
        Aeropuerto aeropuerto = null;
        AeropuertoDAO aeropuertoDAO = null;
        FXMLFormularioAeropuertoController instance = new FXMLFormularioAeropuertoController();
        instance.inicializarInformacion(aeropuerto, aeropuertoDAO);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isConfirmado method, of class FXMLFormularioAeropuertoController.
     */
    @Test
    public void testIsConfirmado() {
        System.out.println("isConfirmado");
        FXMLFormularioAeropuertoController instance = new FXMLFormularioAeropuertoController();
        boolean expResult = false;
        boolean result = instance.isConfirmado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

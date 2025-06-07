/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.util.ResourceBundle;
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
public class FXMLCompraBoletoControllerTest {
    
    public FXMLCompraBoletoControllerTest() {
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
     * Test of initialize method, of class FXMLCompraBoletoController.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        URL url = null;
        ResourceBundle rb = null;
        FXMLCompraBoletoController instance = new FXMLCompraBoletoController();
        instance.initialize(url, rb);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of inicializarDatos method, of class FXMLCompraBoletoController.
     */
    @Test
    public void testInicializarDatos() {
        System.out.println("inicializarDatos");
        Vuelo vuelo = null;
        FXMLCompraBoletoController instance = new FXMLCompraBoletoController();
        instance.inicializarDatos(vuelo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

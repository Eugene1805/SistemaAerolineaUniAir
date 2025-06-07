/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.io.File;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;
import javafxsistemaaerolineauniair.controlador.FXMLAeropuertoController;
import javafxsistemaaerolineauniair.modelo.dao.AeropuertoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;

/**
 *
 * @author uriel
 */
public class FXMLAeropuertoControllerTest {
    
    @Test
    public void testExportarArchivoConExtensionValida() {
        try {
            AeropuertoDAO dao = new AeropuertoDAO();
            File archivoTemporal = File.createTempFile("aeropuerto", ".csv");
            dao.exportarAArchivo(archivoTemporal);
            assertTrue("El archivo debe existir", archivoTemporal.exists());
            archivoTemporal.delete();
        } catch (IOException | com.itextpdf.text.DocumentException e) {
            fail("Error al exportar archivo: " + e.getMessage());
        }
    }
    
    @Test
    public void testClonarAeropuerto() {
        Aeropuerto original = new Aeropuerto(1, "Nombre", "Dirección", "Contacto", "123456789", 3);
        FXMLAeropuertoController controller = new FXMLAeropuertoController();
        
        Aeropuerto copia = controller.clonarAeropuerto(original);
        
        assertNotSame(original, copia);
        assertEquals(original.getId(), copia.getId());
        assertEquals(original.getNombre(), copia.getNombre());
        assertEquals(original.getDireccion(), copia.getDireccion());
        assertEquals(original.getPersonaContacto(), copia.getPersonaContacto());
        assertEquals(original.getTelefono(), copia.getTelefono());
        assertEquals(original.getFlota(), copia.getFlota());
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import javafxsistemaaerolineauniair.modelo.dao.AvionDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class FXMLAvionControllerTest {
    
    @Test
    public void testExportarArchivoConExtensionValida() {
        try {
            AvionDAO dao = new AvionDAO();
            File archivoTemporal = File.createTempFile("avion", ".csv");
            dao.exportarAArchivo(archivoTemporal);
            assertTrue("El archivo debe existir", archivoTemporal.exists());
            archivoTemporal.delete();
        } catch (IOException | com.itextpdf.text.DocumentException e) {
            fail("Error al exportar archivo: " + e.getMessage());
        }
    }
    
    @Test
    public void testClonarAvion(){
        Avion original = new Avion(1, 10, "Boeing 747", 450f, "Realizado", LocalDate.MIN, 5, 2);
        FXMLAvionController controller = new FXMLAvionController();
        
        Avion copia = controller.clonarAvion(original);
        
        assertNotSame(original, copia);
        assertEquals(original.getIdAvion(), copia.getIdAvion());
        assertEquals(original.getCapacidad(), copia.getCapacidad());
        assertEquals(original.getModelo(), copia.getModelo());
        assertEquals(original.getPeso(), copia.getPeso(), 0.0001);
        assertEquals(original.getEstatus(), copia.getEstatus());
        assertEquals(original.getFechaDeIngreso(), copia.getFechaDeIngreso());
        assertEquals(original.getIdAerolinea(), copia.getIdAerolinea());
        assertEquals(original.getAsiento(), copia.getAsiento());
    }
    
}

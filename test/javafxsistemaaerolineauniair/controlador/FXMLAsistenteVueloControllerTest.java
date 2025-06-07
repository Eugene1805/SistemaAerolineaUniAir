/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
public class FXMLAsistenteVueloControllerTest {
    
    @Test
    public void testExportarArchivoConExtensionValida() {
        try {
            AsistenteVueloDAO dao = new AsistenteVueloDAO();
            File archivoTemporal = File.createTempFile("asistentes", ".csv");
            dao.exportarAArchivo(archivoTemporal);
            assertTrue("El archivo debe existir", archivoTemporal.exists());
            archivoTemporal.delete();
        } catch (IOException | com.itextpdf.text.DocumentException e) {
            fail("Error al exportar archivo: " + e.getMessage());
        }
    }
    
    @Test
    public void testClonarAsistenteVuelo() {
        // Crear objeto original
        AsistenteVuelo original = new AsistenteVuelo(
            4321,
            "Carlos",
            "Calle 9, Ciudad",
            LocalDate.of(1992, 3, 10),
            "Masculino",
            8900.50,
            "Hernández",
            "Soto",
            180,  // NoHorasAsistencia
            3     // NoIdiomas
        );

        FXMLAsistenteVueloController controller = new FXMLAsistenteVueloController();

        AsistenteVuelo copia = controller.clonarAsistente(original);

        // Asegurarse que son objetos diferentes
        assertNotSame(original, copia);

        // Validar que todos los campos sean iguales
        assertEquals(original.getNoPersonal(), copia.getNoPersonal());
        assertEquals(original.getNombre(), copia.getNombre());
        assertEquals(original.getDireccion(), copia.getDireccion());
        assertEquals(original.getFechaNacimiento(), copia.getFechaNacimiento());
        assertEquals(original.getGenero(), copia.getGenero());
        assertEquals(original.getSalario(), copia.getSalario(), 0.001);
        assertEquals(original.getApellidoPaterno(), copia.getApellidoPaterno());
        assertEquals(original.getApellidoMaterno(), copia.getApellidoMaterno());
        assertEquals(original.getNoHorasAsistencia(), copia.getNoHorasAsistencia());
        assertEquals(original.getNoIdiomas(), copia.getNoIdiomas());
    }
    
}

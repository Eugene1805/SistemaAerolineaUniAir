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
import javafxsistemaaerolineauniair.modelo.dao.PilotoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Piloto;
import javafxsistemaaerolineauniair.modelo.pojo.TipoLicencia;
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
public class FXMLPilotoControllerTest {
    
    @Test
    public void testExportarArchivoConExtensionValida() {
        try {
            PilotoDAO dao = new PilotoDAO();
            File archivoTemporal = File.createTempFile("pilotos", ".csv");
            dao.exportarAArchivo(archivoTemporal);
            assertTrue("El archivo debe existir", archivoTemporal.exists());
            archivoTemporal.delete();
        } catch (IOException | com.itextpdf.text.DocumentException e) {
            fail("Error al exportar archivo: " + e.getMessage());
        }
    }
    
    @Test
    public void testClonarPiloto() {
        // Datos de prueba
        TipoLicencia licencia = TipoLicencia.COMERCIAL; // Suponiendo un enum definido así
        Piloto original = new Piloto(
            1234,
            "Juan",
            "Calle Falsa 123",
            LocalDate.of(1990, 5, 15),
            "Masculino",
            15000.50,
            "Pérez",
            "Gómez",
            licencia,
            8,
            1200
        );

        FXMLPilotoController controller = new FXMLPilotoController();

        Piloto copia = controller.clonarPiloto(original);

        // Asegura que sea una nueva instancia
        assertNotSame(original, copia);

        // Verifica que todos los campos se hayan copiado correctamente
        assertEquals(original.getNoPersonal(), copia.getNoPersonal());
        assertEquals(original.getNombre(), copia.getNombre());
        assertEquals(original.getDireccion(), copia.getDireccion());
        assertEquals(original.getFechaNacimiento(), copia.getFechaNacimiento());
        assertEquals(original.getGenero(), copia.getGenero());
        assertEquals(original.getSalario(), copia.getSalario(), 0.001);
        assertEquals(original.getApellidoPaterno(), copia.getApellidoPaterno());
        assertEquals(original.getApellidoMaterno(), copia.getApellidoMaterno());
        assertEquals(original.getTipolicencia(), copia.getTipolicencia());
        assertEquals(original.getAniosExperiencia(), copia.getAniosExperiencia());
        assertEquals(original.getTotalHoras(), copia.getTotalHoras());
    }    
}

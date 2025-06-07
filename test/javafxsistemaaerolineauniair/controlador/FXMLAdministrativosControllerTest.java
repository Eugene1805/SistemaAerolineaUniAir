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
import javafxsistemaaerolineauniair.modelo.dao.AdministrativoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Administrativo;
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
public class FXMLAdministrativosControllerTest {
    
    @Test
    public void testExportarArchivoConExtensionValida() {
        try {
            AdministrativoDAO dao = new AdministrativoDAO();
            File archivoTemporal = File.createTempFile("administrativos", ".csv");
            dao.exportarAArchivo(archivoTemporal);
            assertTrue("El archivo debe existir", archivoTemporal.exists());
            archivoTemporal.delete();
        } catch (IOException | com.itextpdf.text.DocumentException e) {
            fail("Error al exportar archivo: " + e.getMessage());
        }
    }
    
    @Test
    public void testClonarAdministrativo() {
        // Datos de prueba
        Administrativo original = new Administrativo(
            5678,
            "Laura",
            "Av. Central 456",
            LocalDate.of(1985, 10, 20),
            "Femenino",
            12000.75,
            "Ramírez",
            "Martínez",
            "Recursos Humanos"
        );

        FXMLAdministrativosController controller = new FXMLAdministrativosController();

        Administrativo copia = controller.clonarAdministrativo(original);

        // Verifica que son objetos distintos
        assertNotSame(original, copia);

        // Verifica que todos los atributos sean iguales
        assertEquals(original.getNoPersonal(), copia.getNoPersonal());
        assertEquals(original.getNombre(), copia.getNombre());
        assertEquals(original.getDireccion(), copia.getDireccion());
        assertEquals(original.getFechaNacimiento(), copia.getFechaNacimiento());
        assertEquals(original.getGenero(), copia.getGenero());
        assertEquals(original.getSalario(), copia.getSalario(), 0.001);
        assertEquals(original.getApellidoPaterno(), copia.getApellidoPaterno());
        assertEquals(original.getApellidoMaterno(), copia.getApellidoMaterno());
        assertEquals(original.getDepartamento(), copia.getDepartamento());
    }
}

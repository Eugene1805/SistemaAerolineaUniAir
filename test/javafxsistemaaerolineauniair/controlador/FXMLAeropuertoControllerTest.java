/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import org.junit.Test;
import static org.junit.Assert.*;
import javafxsistemaaerolineauniair.controlador.FXMLAeropuertoController;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;

/**
 *
 * @author uriel
 */
public class FXMLAeropuertoControllerTest {
    
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

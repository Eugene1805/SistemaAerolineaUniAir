/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
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
public class FXMLVueloControllerTest {
    @Test
    public void testClonarVuelo() {
        LocalDate fechaSalida = LocalDate.parse("2025-05-27");
        LocalTime horaSalida = LocalTime.parse("14:00");
        LocalDate fechaLlegada = LocalDate.parse("2025-05-27");
        LocalTime horaLlegada = LocalTime.parse("16:30");
        Vuelo original = new Vuelo(1, 100, 2, 150f, "Ciudad A", "Ciudad B", fechaSalida, horaSalida, fechaLlegada, horaLlegada, 10);
        FXMLVueloController controller = new FXMLVueloController();
        
        Vuelo copia = controller.clonarVuelo(original);
        
        assertNotSame(original, copia);
        assertEquals(original.getIdVuelo(), copia.getIdVuelo());
        assertEquals(original.getNumPasajeros(), copia.getNumPasajeros());
        assertEquals(original.getTiempoRecorrido(), copia.getTiempoRecorrido(), 0.01);
        assertEquals(original.getCostoBoleto(), copia.getCostoBoleto(), 0.01);
        assertEquals(original.getCiudadSalida(), copia.getCiudadSalida());
        assertEquals(original.getCiudadLlegada(), copia.getCiudadLlegada());
        assertEquals(original.getFechaSalida(), copia.getFechaSalida());
        assertEquals(original.getHoraSalida(), copia.getHoraSalida());
        assertEquals(original.getFechaLlegada(), copia.getFechaLlegada());
        assertEquals(original.getHoraLlegada(), copia.getHoraLlegada());
        assertEquals(original.getIdAvion(), copia.getIdAvion());
    }
    
}

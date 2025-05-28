/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
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
public class VueloTest {
    /**
     * Test of calcularTiempoRecorrido method, of class Vuelo.
     */
    @Test
    public void testCalcularTiempoRecorrido() {
        System.out.println("calcularTiempoRecorrido");
        LocalDate fechaSalida = LocalDate.parse("2025-05-27");
        LocalTime horaSalida = LocalTime.parse("14:00");
        LocalDate fechaLlegada = LocalDate.parse("2025-05-27");
        LocalTime horaLlegada = LocalTime.parse("16:00");
        Vuelo vuelo = new Vuelo(1, 100, 0, 150.0, "Ciudad A", "Ciudad B", fechaSalida, horaSalida, fechaLlegada, horaLlegada, 10);
        vuelo.calcularTiempoRecorrido();
        assertEquals(2, vuelo.getTiempoRecorrido());
    }
}

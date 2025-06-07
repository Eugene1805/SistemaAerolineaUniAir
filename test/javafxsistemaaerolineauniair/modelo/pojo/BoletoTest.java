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
public class BoletoTest {
    
    private Boleto boleto;

    @Before
    public void setUp() {
        LocalDate fechaSalida = LocalDate.parse("2025-05-27");
        LocalTime horaSalida = LocalTime.parse("14:00");
        LocalDate fechaLlegada = LocalDate.parse("2025-05-27");
        LocalTime horaLlegada = LocalTime.parse("16:00");

        boleto = new Boleto(
            1,1,0,1500.50,"Ciudad de México","Cancún",fechaSalida,horaSalida,
            fechaLlegada,horaLlegada,"A12",101,"Boeing 737",1001,"Juan Pérez"
        );
    }
    @Test
    
    public void testCalcularTiempoRecorrido() {
        System.out.println("calcularTiempoRecorrido");
        boleto.calcularTiempoRecorrido();
        assertEquals(2, boleto.getTiempoRecorrido());
    }
}

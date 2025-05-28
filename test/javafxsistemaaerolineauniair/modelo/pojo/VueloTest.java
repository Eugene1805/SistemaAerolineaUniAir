package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase Vuelo.
 * Se asegura que todos los datos creados durante las pruebas se limpien después.
 * 
 * @author uriel
 */
public class VueloTest {

    private Vuelo vuelo;

    @Before
    public void setUp() {
        LocalDate fechaSalida = LocalDate.parse("2025-05-27");
        LocalTime horaSalida = LocalTime.parse("14:00");
        LocalDate fechaLlegada = LocalDate.parse("2025-05-27");
        LocalTime horaLlegada = LocalTime.parse("16:00");

        vuelo = new Vuelo(
            1, 100, 0, 150.0,
            "Ciudad A", "Ciudad B",
            fechaSalida, horaSalida,
            fechaLlegada, horaLlegada, 10
        );
    }

    @Test
    public void testCalcularTiempoRecorrido() {
        System.out.println("calcularTiempoRecorrido");
        vuelo.calcularTiempoRecorrido();
        assertEquals(2, vuelo.getTiempoRecorrido());
    }
}

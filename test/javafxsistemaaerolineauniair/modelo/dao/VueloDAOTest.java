/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class VueloDAOTest {

    @After
    public void limpiarDatosDePrueba() throws Exception {
        VueloDAO dao = new VueloDAO();

        // Lista de IDs que usas durante tus pruebas
        int[] idsPrueba = {777, 888, 999};

        for (int id : idsPrueba) {
            Vuelo vuelo = dao.buscarPorIdVuelo(id);
            if (vuelo != null) {
                dao.eliminar(id);
            }
        }
    }

    @Test
    public void testObtenerNombresColumnas() {
        System.out.println("obtenerNombresColumnas");
        VueloDAO dao = new VueloDAO();
        String[] expResultado = {"ID Vuelo", "Pasajeros", "Ciudad Salida", "Ciudad Llegada", "Fecha Salida", "Hora Salida", "Fecha Llegada", "Hora Llegada",
            "Tiempo Recorrido (hrs)", "Costo Boleto"};
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() {
        System.out.println("obtenerValoresFila");
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(1);
        vuelo.setNumPasajeros(150);
        vuelo.setCiudadSalida("Ciudad A");
        vuelo.setCiudadLlegada("Ciudad B");
        vuelo.setFechaSalida(LocalDate.of(2024, 6, 1));
        vuelo.setHoraSalida(LocalTime.of(10, 30));
        vuelo.setFechaLlegada(LocalDate.of(2024, 6, 1));
        vuelo.setHoraLlegada(LocalTime.of(13, 15));
        vuelo.setTiempoRecorrido(165);
        vuelo.setCostoBoleto(1200.5);

        VueloDAO dao = new VueloDAO();
        String[] result = dao.obtenerValoresFila(vuelo);

        String[] expected = {
            "1", "150", "Ciudad A", "Ciudad B",
            "2024-06-01", "10:30", "2024-06-01", "13:15",
            "165", "1200.5"
        };

        assertArrayEquals(expected, result);
    }

    @Test
    public void testBuscarPorIdVuelo() throws Exception {
        System.out.println("buscarPorIdVuelo");
        VueloDAO dao = new VueloDAO();
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(999);
        vuelo.setNumPasajeros(100);
        vuelo.setCiudadSalida("CDMX");
        vuelo.setCiudadLlegada("Monterrey");
        vuelo.setFechaSalida(LocalDate.of(2024, 7, 1));
        vuelo.setHoraSalida(LocalTime.of(8, 0));
        vuelo.setFechaLlegada(LocalDate.of(2024, 7, 1));
        vuelo.setHoraLlegada(LocalTime.of(10, 30));
        vuelo.setTiempoRecorrido(150);
        vuelo.setCostoBoleto(999.99);

        List<Vuelo> vuelos = dao.obtenerTodos();
        vuelos.add(vuelo);
        dao.guardarTodos(vuelos);

        Vuelo result = dao.buscarPorIdVuelo(999);
        assertNotNull(result);
        assertEquals(999, result.getIdVuelo());
    }

    @Test
    public void testActualizar() throws Exception {
        System.out.println("actualizar");
        VueloDAO dao = new VueloDAO();
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(888);
        vuelo.setNumPasajeros(90);
        vuelo.setCiudadSalida("GDL");
        vuelo.setCiudadLlegada("Cancún");
        vuelo.setFechaSalida(LocalDate.of(2024, 8, 15));
        vuelo.setHoraSalida(LocalTime.of(9, 0));
        vuelo.setFechaLlegada(LocalDate.of(2024, 8, 15));
        vuelo.setHoraLlegada(LocalTime.of(12, 0));
        vuelo.setTiempoRecorrido(180);
        vuelo.setCostoBoleto(1500);

        List<Vuelo> vuelos = dao.obtenerTodos();
        vuelos.add(vuelo);
        dao.guardarTodos(vuelos);

        vuelo.setCiudadLlegada("Tijuana");
        vuelo.setCostoBoleto(1600);
        dao.actualizar(vuelo);

        Vuelo actualizado = dao.buscarPorIdVuelo(888);
        assertEquals("Tijuana", actualizado.getCiudadLlegada());
        assertEquals(1600, actualizado.getCostoBoleto(), 0.001);
    }

    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        VueloDAO dao = new VueloDAO();
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(777);
        vuelo.setNumPasajeros(70);
        vuelo.setCiudadSalida("Puebla");
        vuelo.setCiudadLlegada("Oaxaca");
        vuelo.setFechaSalida(LocalDate.of(2024, 9, 1));
        vuelo.setHoraSalida(LocalTime.of(14, 0));
        vuelo.setFechaLlegada(LocalDate.of(2024, 9, 1));
        vuelo.setHoraLlegada(LocalTime.of(15, 30));
        vuelo.setTiempoRecorrido(90);
        vuelo.setCostoBoleto(850);

        List<Vuelo> vuelos = dao.obtenerTodos();
        vuelos.add(vuelo);
        dao.guardarTodos(vuelos);

        dao.eliminar(777);

        Vuelo eliminado = dao.buscarPorIdVuelo(777);
        assertNull(eliminado);
    }

    @Test
    public void testGenerarIdVueloUnico() throws Exception {
        System.out.println("generarIdVueloUnico");
        VueloDAO dao = new VueloDAO();
        int id1 = dao.generarIdVueloUnico();
        int id2 = dao.generarIdVueloUnico();

        assertNotEquals("Los IDs deben ser diferentes", id1, id2);
        assertTrue("ID debe ser positivo", id1 > 0 && id2 > 0);
    }
}

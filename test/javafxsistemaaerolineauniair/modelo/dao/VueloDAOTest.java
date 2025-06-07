package javafxsistemaaerolineauniair.modelo.dao;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase VueloDAO.
 * Se asegura que todos los datos creados durante las pruebas se limpien después.
 * 
 * @author uriel
 */
public class VueloDAOTest {

    private VueloDAO dao;
    private List<Integer> idsPrueba = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        dao = new VueloDAO();
    }

    @After
    public void limpiarDatosDePrueba() throws Exception {
        for (int id : idsPrueba) {
            Vuelo vuelo = dao.buscarPorIdVuelo(id);
            if (vuelo != null) {
                dao.eliminar(id);
            }
        }
        idsPrueba.clear();
    }

    @Test
    public void testObtenerNombresColumnas() {
        String[] expResultado = {
            "ID Vuelo", "Pasajeros", "Ciudad Salida", "Ciudad Llegada", 
            "Fecha Salida", "Hora Salida", "Fecha Llegada", "Hora Llegada",
            "Tiempo Recorrido (hrs)", "Costo Boleto", "Avion", "Pilotos", "Asistentes"
        };
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() {
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
        vuelo.setIdAvion(1);
        vuelo.setPilotos(new ArrayList<>());
        vuelo.setAsistentes(new ArrayList<>());

        String[] result = dao.obtenerValoresFila(vuelo);

        assertNotNull(result);
        assertEquals("1", result[0]);
        assertEquals("150", result[1]);
        assertEquals("Ciudad A", result[2]);
        assertEquals("Ciudad B", result[3]);
        assertEquals("2024-06-01", result[4]);
        assertEquals("10:30", result[5]);
    }

    @Test
    public void testBuscarPorIdVuelo() throws Exception {
        int idPrueba = dao.generarIdVueloUnico();
        idsPrueba.add(idPrueba);
        
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(idPrueba);
        vuelo.setNumPasajeros(100);
        vuelo.setCiudadSalida("CDMX");
        vuelo.setCiudadLlegada("Monterrey");
        vuelo.setFechaSalida(LocalDate.of(2024, 7, 1));
        vuelo.setHoraSalida(LocalTime.of(8, 0));
        vuelo.setFechaLlegada(LocalDate.of(2024, 7, 1));
        vuelo.setHoraLlegada(LocalTime.of(10, 30));
        vuelo.setTiempoRecorrido(150);
        vuelo.setCostoBoleto(999.99);
        vuelo.setIdAvion(1);
        vuelo.setPilotos(new ArrayList<>());
        vuelo.setAsistentes(new ArrayList<>());

        List<Vuelo> vuelos = dao.obtenerTodos();
        vuelos.add(vuelo);
        dao.guardarTodos(vuelos);

        Vuelo result = dao.buscarPorIdVuelo(idPrueba);
        assertNotNull(result);
        assertEquals(idPrueba, result.getIdVuelo());
    }

    @Test
    public void testActualizar() throws Exception {
        int idPrueba = dao.generarIdVueloUnico();
        idsPrueba.add(idPrueba);
        
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(idPrueba);
        vuelo.setNumPasajeros(90);
        vuelo.setCiudadSalida("GDL");
        vuelo.setCiudadLlegada("Cancún");
        vuelo.setFechaSalida(LocalDate.of(2024, 8, 15));
        vuelo.setHoraSalida(LocalTime.of(9, 0));
        vuelo.setFechaLlegada(LocalDate.of(2024, 8, 15));
        vuelo.setHoraLlegada(LocalTime.of(12, 0));
        vuelo.setTiempoRecorrido(180);
        vuelo.setCostoBoleto(1500);
        vuelo.setIdAvion(1);
        vuelo.setPilotos(new ArrayList<>());
        vuelo.setAsistentes(new ArrayList<>());

        List<Vuelo> vuelos = dao.obtenerTodos();
        vuelos.add(vuelo);
        dao.guardarTodos(vuelos);

        vuelo.setCiudadLlegada("Tijuana");
        vuelo.setCostoBoleto(1600);
        dao.actualizar(vuelo);

        Vuelo actualizado = dao.buscarPorIdVuelo(idPrueba);
        assertEquals("Tijuana", actualizado.getCiudadLlegada());
        assertEquals(1600, actualizado.getCostoBoleto(), 0.001);
    }

    @Test
    public void testEliminar() throws Exception {
        int idPrueba = dao.generarIdVueloUnico();
        
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(idPrueba);
        vuelo.setNumPasajeros(70);
        vuelo.setCiudadSalida("Puebla");
        vuelo.setCiudadLlegada("Oaxaca");
        vuelo.setFechaSalida(LocalDate.of(2024, 9, 1));
        vuelo.setHoraSalida(LocalTime.of(14, 0));
        vuelo.setFechaLlegada(LocalDate.of(2024, 9, 1));
        vuelo.setHoraLlegada(LocalTime.of(15, 30));
        vuelo.setTiempoRecorrido(90);
        vuelo.setCostoBoleto(850);
        vuelo.setIdAvion(1);
        vuelo.setPilotos(new ArrayList<>());
        vuelo.setAsistentes(new ArrayList<>());

        List<Vuelo> vuelos = dao.obtenerTodos();
        vuelos.add(vuelo);
        dao.guardarTodos(vuelos);

        dao.eliminar(idPrueba);

        Vuelo eliminado = dao.buscarPorIdVuelo(idPrueba);
        assertNull(eliminado);
    }

    @Test
    public void testGenerarIdVueloUnico() throws Exception {
        int id1 = dao.generarIdVueloUnico();
        int id2 = dao.generarIdVueloUnico();

        assertNotEquals("Los IDs deben ser diferentes", id1, id2);
        assertTrue("ID debe ser positivo", id1 > 0 && id2 > 0);
    }
}
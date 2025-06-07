package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.io.IOException;
import java.util.List;
import javafxsistemaaerolineauniair.excepciones.AeropuertoConVuelosException;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;

/**
 *
 * @author uriel
 */
public class AeropuertoDAOTest {
    
    private AeropuertoDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new AeropuertoDAO();
    }
    
    @After
    public void limpiarDatos() throws Exception {
        // Limpiar todos los aeropuertos creados en los tests
        List<Aeropuerto> aeropuertos = dao.obtenerTodos();
        for (Aeropuerto aeropuerto : aeropuertos) {
            if (aeropuerto.getNombre().contains("Test") || 
                aeropuerto.getNombre().contains("Actualizado") ||
                aeropuerto.getNombre().contains("Aeropuerto para eliminar") ||
                aeropuerto.getNombre().contains("Aeropuerto con vuelos")) {
                
                try {
                    // Intentamos eliminar el aeropuerto, si tiene aviones asociados
                    // simplemente continuamos con el siguiente
                    dao.verificarYEliminar(aeropuerto.getId());
                } catch (AeropuertoConVuelosException e) {
                    // Si tiene aviones asociados, eliminamos los aviones
                    AvionDAO avionDAO = new AvionDAO();
                    List<Avion> aviones = avionDAO.buscarPorAeropuerto(aeropuerto.getId());
                    for (Avion avion : aviones) {
                        avionDAO.eliminar(avion.getIdAvion());
                    }
                    // Ahora intentamos eliminar el aeropuerto nuevamente
                    dao.verificarYEliminar(aeropuerto.getId());
                }
            }
        }
    }

    @Test
    public void testObtenerNombresColumnas() {
        String[] expResultado = {"ID","Nombre", "Dirección", "Persona de Contacto", "Teléfono", "Flota"};
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals("Las columnas deben coincidir", expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() {
        Aeropuerto aeropuerto = new Aeropuerto();
        aeropuerto.setId(1);
        aeropuerto.setNombre("Aeropuerto Internacional");
        aeropuerto.setDireccion("Av. Principal 123");
        aeropuerto.setPersonaContacto("Juan Pérez");
        aeropuerto.setTelefono("555-1234");
        aeropuerto.setFlota(5);

        String[] expResultado = {
            "1", "Aeropuerto Internacional", "Av. Principal 123", "Juan Pérez", "555-1234", "5"
        };
        String[] resultado = dao.obtenerValoresFila(aeropuerto);
        assertArrayEquals("Los valores de la fila no coinciden", expResultado, resultado);
    }

    @Test
    public void testGenerarIdUnico() throws IOException {
        int id1 = dao.generarIdUnico();
        int id2 = dao.generarIdUnico();

        assertNotEquals("Los IDs generados deben ser diferentes", id1, id2);
        assertTrue("El ID generado debe ser positivo", id1 > 0 && id2 > 0);
    }

    @Test
    public void testBuscarPorId() throws Exception {
        int id = dao.generarIdUnico();

        Aeropuerto aeropuerto = new Aeropuerto(id, "Aeropuerto Test", "Dirección", "Contacto", "555-555", 3);
        dao.agregar(aeropuerto);

        Aeropuerto resultado = dao.buscarPorId(id);

        assertNotNull("El aeropuerto no debe ser nulo", resultado);
        assertEquals("El nombre debe coincidir", "Aeropuerto Test", resultado.getNombre());
    }

    @Test
    public void testActualizar() throws Exception {
        int id = dao.generarIdUnico();

        Aeropuerto original = new Aeropuerto(id, "Original", "Dirección", "Persona", "000", 2);
        dao.agregar(original);

        Aeropuerto actualizado = new Aeropuerto(id, "Actualizado", "Nueva Dirección", "Nuevo Contacto", "111", 5);
        dao.actualizar(actualizado);

        Aeropuerto resultado = dao.buscarPorId(id);
        assertEquals("El nombre debe haber sido actualizado", "Actualizado", resultado.getNombre());
    }

    @Test
    public void testVerificarYEliminar() throws Exception {
        int id = dao.generarIdUnico();

        Aeropuerto aeropuerto = new Aeropuerto(id, "Aeropuerto para eliminar", "Dirección", "Persona", "123", 3);
        dao.agregar(aeropuerto);

        // Test caso normal sin vuelos asociados
        dao.verificarYEliminar(id);

        Aeropuerto eliminado = dao.buscarPorId(id);
        assertNull("El aeropuerto debe haber sido eliminado", eliminado);
    }

    @Test(expected = AeropuertoConVuelosException.class)
    public void testVerificarYEliminarConVuelos() throws Exception {
        int idAeropuerto = dao.generarIdUnico();

        Aeropuerto aeropuerto = new Aeropuerto(idAeropuerto, "Aeropuerto con vuelos", "Dirección", "Persona", "123", 2);
        dao.agregar(aeropuerto);

        // Simulamos que el aeropuerto tiene aviones asociados
        AvionDAO avionDAO = new AvionDAO();
        Avion avion = new Avion();
        avion.setIdAerolinea(idAeropuerto);  // Asocia un avión al aeropuerto
        avionDAO.agregar(avion);

        // lanza AeropuertoConVuelosException
        dao.verificarYEliminar(idAeropuerto);
    }
}
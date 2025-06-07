package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.Piloto;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import javafxsistemaaerolineauniair.excepciones.EmpleadoConVuelosException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.TipoLicencia;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase PilotoDAO.
 * Se asegura que todos los datos creados durante las pruebas se limpien después.
 * 
 * @author uriel
 */
public class PilotoDAOTest {
    
    private PilotoDAO dao;
    private VueloDAO vueloDAO;

    @Before
    public void setUp() throws Exception {
        dao = new PilotoDAO();
        vueloDAO = new VueloDAO();
    }
    
    @After
    public void limpiarDatos() throws Exception {
        // Limpiar todos los pilotos creados en los tests
        List<Piloto> pilotos = dao.obtenerTodos();
        for (Piloto piloto : pilotos) {
            if (piloto.getNombre().contains("Test") || 
                piloto.getNombre().contains("Actualizado") ||
                piloto.getNombre().contains("Piloto para eliminar") ||
                piloto.getNombre().contains("Piloto con vuelos")) {
                
                try {
                    // Intentamos eliminar el piloto, si tiene vuelos asociados
                    // simplemente continuamos con el siguiente
                    dao.verificarYEliminar(piloto.getNoPersonal());
                } catch (EmpleadoConVuelosException e) {
                    // Si tiene vuelos asociados, eliminamos los vuelos
                    List<Vuelo> vuelosAsociados = vueloDAO.buscarVuelosPorPiloto(piloto.getNoPersonal());
                    for (Vuelo vuelo : vuelosAsociados) {
                        vueloDAO.eliminar(vuelo.getIdVuelo());
                    }
                    // Ahora intentamos eliminar el piloto nuevamente
                    dao.verificarYEliminar(piloto.getNoPersonal());
                }
            }
        }
    }

    @Test
    public void testObtenerNombresColumnas() {
        String[] expResultado = {
            "No. Personal", "Nombre", "Apellido Paterno", "Apellido Materno", "Dirección",
            "Fecha de Nacimiento", "Género", "Salario", "Tipo de Licencia",
            "Años de Experiencia", "Total de Horas"
        };
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals("Las columnas deben coincidir", expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() {
        Piloto piloto = new Piloto();
        piloto.setNoPersonal(1001);
        piloto.setNombre("Juan");
        piloto.setApellidoPaterno("Pérez");
        piloto.setApellidoMaterno("López");
        piloto.setDireccion("Calle Falsa 123");
        piloto.setFechaNacimiento(LocalDate.of(1980, 5, 15));
        piloto.setGenero("Masculino");
        piloto.setSalario(25000.0);
        piloto.setTipolicencia(TipoLicencia.COMERCIAL);
        piloto.setAniosExperiencia(10);
        piloto.setTotalHoras(5000);

        String[] expResultado = {
            "1001", "Juan", "Pérez", "López", "Calle Falsa 123",
            "1980-05-15", "Masculino", "25000.0", "Licencia Comercial",
            "10", "5000"
        };
        String[] resultado = dao.obtenerValoresFila(piloto);
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

        Piloto piloto = new Piloto();
        piloto.setNoPersonal(id);
        piloto.setNombre("Piloto Test");
        
        List<Piloto> pilotos = dao.obtenerTodos();
        pilotos.add(piloto);
        dao.guardarTodos(pilotos);

        Piloto resultado = dao.buscarPorId(id);

        assertNotNull("El piloto no debe ser nulo", resultado);
        assertEquals("El nombre debe coincidir", "Piloto Test", resultado.getNombre());
    }

    @Test
    public void testActualizar() throws Exception {
        int id = dao.generarIdUnico();

        Piloto original = new Piloto();
        original.setNoPersonal(id);
        original.setNombre("Original");
        
        List<Piloto> pilotos = dao.obtenerTodos();
        pilotos.add(original);
        dao.guardarTodos(pilotos);

        Piloto actualizado = new Piloto();
        actualizado.setNoPersonal(id);
        actualizado.setNombre("Actualizado");
        
        dao.actualizar(actualizado);

        Piloto resultado = dao.buscarPorId(id);
        assertEquals("El nombre debe haber sido actualizado", "Actualizado", resultado.getNombre());
    }

    @Test
    public void testVerificarYEliminar() throws Exception {
        int id = dao.generarIdUnico();

        Piloto piloto = new Piloto();
        piloto.setNoPersonal(id);
        piloto.setNombre("Piloto para eliminar");
        
        List<Piloto> pilotos = dao.obtenerTodos();
        pilotos.add(piloto);
        dao.guardarTodos(pilotos);

        // Test caso normal sin vuelos asociados
        dao.verificarYEliminar(id);

        Piloto eliminado = dao.buscarPorId(id);
        assertNull("El piloto debe haber sido eliminado", eliminado);
    }

    @Test(expected = EmpleadoConVuelosException.class)
    public void testVerificarYEliminarConVuelos() throws Exception {
        int idPiloto = dao.generarIdUnico();

        Piloto piloto = new Piloto();
        piloto.setNoPersonal(idPiloto);
        piloto.setNombre("Piloto con vuelos");
        
        List<Piloto> pilotos = dao.obtenerTodos();
        pilotos.add(piloto);
        dao.guardarTodos(pilotos);

        // Simulamos que el piloto tiene vuelos asociados
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(vueloDAO.generarIdVueloUnico());
        List<Integer> pilotosVuelo = new ArrayList<>();
        pilotosVuelo.add(idPiloto);
        vuelo.setPilotos(pilotosVuelo);
        
        List<Vuelo> vuelos = vueloDAO.obtenerTodos();
        vuelos.add(vuelo);
        vueloDAO.guardarTodos(vuelos);

        // lanzar EmpleadoConVuelosException
        dao.verificarYEliminar(idPiloto);
    }
}
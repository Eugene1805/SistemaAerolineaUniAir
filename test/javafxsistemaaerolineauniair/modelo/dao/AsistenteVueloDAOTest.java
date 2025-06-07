package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.AsistenteVuelo;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import javafxsistemaaerolineauniair.excepciones.EmpleadoConVuelosException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase AsistenteVueloDAO.
 * Se asegura que todos los datos creados durante las pruebas se limpien después.
 * 
 * @author uriel
 */
public class AsistenteVueloDAOTest {

    private AsistenteVueloDAO dao;
    private VueloDAO vueloDAO;

    @Before
    public void setUp() throws Exception {
        dao = new AsistenteVueloDAO();
        vueloDAO = new VueloDAO();
    }
    
    @After
    public void limpiarDatos() throws Exception {
        // Limpiar todos los asistentes creados en los tests
        List<AsistenteVuelo> asistentes = dao.obtenerTodos();
        for (AsistenteVuelo asistente : asistentes) {
            if (asistente.getNombre().contains("Test") || 
                asistente.getNombre().contains("Actualizado") ||
                asistente.getNombre().contains("Asistente para eliminar") ||
                asistente.getNombre().contains("Asistente con vuelos")) {
                
                try {
                    // Intentamos eliminar al asistente, si tiene vuelos asociados
                    // simplemente continuamos con el siguiente
                    dao.verificarYEliminar(asistente.getNoPersonal());
                } catch (EmpleadoConVuelosException e) {
                    // Si tiene vuelos asociados, eliminamos los vuelos
                    List<Vuelo> vuelosAsociados = vueloDAO.buscarVuelosPorAsistente(asistente.getNoPersonal());
                    for (Vuelo vuelo : vuelosAsociados) {
                        vueloDAO.eliminar(vuelo.getIdVuelo());
                    }
                    // Ahora intentamos eliminar el asistente nuevamente
                    dao.verificarYEliminar(asistente.getNoPersonal());
                }
            }
        }
    }

    @Test
    public void testObtenerNombresColumnas() {
        String[] expResultado = {
            "No. Personal", "Nombre", "Apellido Paterno", "Apellido Materno", "Dirección",
            "Fecha de Nacimiento", "Género", "Salario", "Horas de Asistencia", "No. Idiomas"
        };
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals("Las columnas deben coincidir", expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() {
        AsistenteVuelo asistente = new AsistenteVuelo();
        asistente.setNoPersonal(1001);
        asistente.setNombre("Carlos");
        asistente.setApellidoPaterno("Gómez");
        asistente.setApellidoMaterno("Ramírez");
        asistente.setDireccion("Av. Siempre Viva 123");
        asistente.setFechaNacimiento(LocalDate.of(1985, 3, 25));
        asistente.setGenero("Masculino");
        asistente.setSalario(30000.0);
        asistente.setNoHorasAsistencia(150);
        asistente.setNoIdiomas(3);

        String[] expResultado = {
            "1001", "Carlos", "Gómez", "Ramírez", "Av. Siempre Viva 123",
            "1985-03-25", "Masculino", "30000.0", "150", "3"
        };
        String[] resultado = dao.obtenerValoresFila(asistente);
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

        AsistenteVuelo asistente = new AsistenteVuelo();
        asistente.setNoPersonal(id);
        asistente.setNombre("Asistente Test");

        List<AsistenteVuelo> asistentes = dao.obtenerTodos();
        asistentes.add(asistente);
        dao.guardarTodos(asistentes);

        AsistenteVuelo resultado = dao.buscarPorId(id);

        assertNotNull("El asistente no debe ser nulo", resultado);
        assertEquals("El nombre debe coincidir", "Asistente Test", resultado.getNombre());
    }

    @Test
    public void testActualizar() throws Exception {
        int id = dao.generarIdUnico();

        AsistenteVuelo original = new AsistenteVuelo();
        original.setNoPersonal(id);
        original.setNombre("Original");
        
        List<AsistenteVuelo> asistentes = dao.obtenerTodos();
        asistentes.add(original);
        dao.guardarTodos(asistentes);

        AsistenteVuelo actualizado = new AsistenteVuelo();
        actualizado.setNoPersonal(id);
        actualizado.setNombre("Actualizado");
        
        dao.actualizar(actualizado);

        AsistenteVuelo resultado = dao.buscarPorId(id);
        assertEquals("El nombre debe haber sido actualizado", "Actualizado", resultado.getNombre());
    }

    @Test
    public void testVerificarYEliminar() throws Exception {
        int id = dao.generarIdUnico();

        AsistenteVuelo asistente = new AsistenteVuelo();
        asistente.setNoPersonal(id);
        asistente.setNombre("Asistente para eliminar");
        
        List<AsistenteVuelo> asistentes = dao.obtenerTodos();
        asistentes.add(asistente);
        dao.guardarTodos(asistentes);

        // Test caso normal sin vuelos asociados
        dao.verificarYEliminar(id);

        AsistenteVuelo eliminado = dao.buscarPorId(id);
        assertNull("El asistente debe haber sido eliminado", eliminado);
    }

    @Test(expected = EmpleadoConVuelosException.class)
    public void testVerificarYEliminarConVuelos() throws Exception {
        int idAsistente = dao.generarIdUnico();

        AsistenteVuelo asistente = new AsistenteVuelo();
        asistente.setNoPersonal(idAsistente);
        asistente.setNombre("Asistente con vuelos");
        
        List<AsistenteVuelo> asistentes = dao.obtenerTodos();
        asistentes.add(asistente);
        dao.guardarTodos(asistentes);

        // Simulamos que el asistente tiene vuelos asociados
        Vuelo vuelo = new Vuelo();
        vuelo.setIdVuelo(vueloDAO.generarIdVueloUnico());
        List<Integer> asistentesVuelo = new ArrayList<>();
        asistentesVuelo.add(idAsistente);
        vuelo.setAsistentes(asistentesVuelo);
        
        List<Vuelo> vuelos = vueloDAO.obtenerTodos();
        vuelos.add(vuelo);
        vueloDAO.guardarTodos(vuelos);

        // lanzar EmpleadoConVuelosException
        dao.verificarYEliminar(idAsistente);
    }
}

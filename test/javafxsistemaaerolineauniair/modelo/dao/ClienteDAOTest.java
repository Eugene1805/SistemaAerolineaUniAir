package javafxsistemaaerolineauniair.modelo.dao;

import java.util.ArrayList;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Cliente;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class ClienteDAOTest {
    
    private ClienteDAO dao;
    private List<Integer> idsTemporales;

    @Before
    public void setUp() {
        dao = new ClienteDAO();
        idsTemporales = new ArrayList<>();
    }

    @After
    public void limpiarDatos() throws Exception {
        for (int id : idsTemporales) {
            try {
                if (dao.buscarPorId(id) != null) { 
                    dao.eliminar(id);
                }
            } catch (Exception e) {
                System.err.println("Error al limpiar cliente temporal ID: " + id);
                e.printStackTrace();
            }
        }
        idsTemporales.clear();
    }

    @Test
    public void testObtenerNombresColumnas() {
        System.out.println("obtenerNombresColumnas");
        String[] expResultado = {"ID","Nombre", "Apellido paterno", "Apellido materno",
            "Nacionalidad", "Fecha de nacimiento"};
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() {
        System.out.println("obtenerValoresFila");
        Cliente cliente = new Cliente();
        cliente.setId(1);
        cliente.setNombre("Juan");
        cliente.setApellidoPaterno("Pérez");
        cliente.setApellidoMaterno("Gómez");
        cliente.setNacionalidad("Mexicana");
        cliente.setFechaNacimiento("2001-01-01");
        
        String[] expResultado = {
            "1", "Juan", "Pérez", "Gómez", "Mexicana", "2001-01-01"
        };
        String[] resultado = dao.obtenerValoresFila(cliente);
        assertArrayEquals(expResultado, resultado);
    }

    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("buscarPorId");
        int id = dao.generarIdUnico();
        idsTemporales.add(id); 

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Juan");
        cliente.setApellidoPaterno("Pérez");
        cliente.setApellidoMaterno("Gómez");
        cliente.setNacionalidad("Mexicana");
        cliente.setFechaNacimiento("2000-01-01");

        List<Cliente> listaOriginal = dao.obtenerTodos();
        List<Cliente> listaModificada = new ArrayList<>(listaOriginal);
        listaModificada.add(cliente);
        dao.guardarTodos(listaModificada);

        Cliente resultado = dao.buscarPorId(id);
        assertNotNull("El cliente debe existir", resultado);
        assertEquals("El ID debe coincidir", id, resultado.getId());
        
        dao.guardarTodos(listaOriginal);
    }

    @Test
    public void testActualizar() throws Exception {
        System.out.println("actualizar");
        int id = dao.generarIdUnico();

        // Crear cliente inicial
        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Ana");
        cliente.setApellidoPaterno("López");
        cliente.setApellidoMaterno("Ramírez");
        cliente.setNacionalidad("Argentina");
        cliente.setFechaNacimiento("1995-05-10");

        List<Cliente> listaOriginal = dao.obtenerTodos();
        List<Cliente> listaModificada = new ArrayList<>(listaOriginal);
        listaModificada.add(cliente);
        dao.guardarTodos(listaModificada);

        Cliente actualizado = new Cliente();
        actualizado.setId(id);
        actualizado.setNombre("Ana María");
        actualizado.setApellidoPaterno("López");
        actualizado.setApellidoMaterno("Ramírez");
        actualizado.setNacionalidad("Argentina");
        actualizado.setFechaNacimiento("1995-05-10");

        dao.actualizar(actualizado);

        Cliente resultado = dao.buscarPorId(id);
        assertEquals("El nombre debe haberse actualizado", "Ana María", resultado.getNombre());
        
        dao.guardarTodos(listaOriginal);
    }

    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        int id = dao.generarIdUnico();

        Cliente cliente = new Cliente();
        cliente.setId(id);
        cliente.setNombre("Carlos");
        cliente.setApellidoPaterno("Mendoza");
        cliente.setApellidoMaterno("Flores");
        cliente.setNacionalidad("Colombiana");
        cliente.setFechaNacimiento("1990-02-20");

        List<Cliente> listaOriginal = dao.obtenerTodos();
        List<Cliente> listaModificada = new ArrayList<>(listaOriginal);
        listaModificada.add(cliente);
        dao.guardarTodos(listaModificada);

        dao.eliminar(id);

        Cliente eliminado = dao.buscarPorId(id);
        assertNull("El cliente debería haber sido eliminado", eliminado);
        
        dao.guardarTodos(listaOriginal);
    }

    @Test
    public void testGenerarIdUnico() throws Exception {
        int id1 = dao.generarIdUnico();
        int id2 = dao.generarIdUnico();
        assertNotEquals("Los IDs deben ser diferentes", id1, id2);
        assertTrue("ID debe ser positivo", id1 > 0 && id2 > 0);
    }

    @Test
    public void testPuedeEliminarse() throws Exception {
        System.out.println("puedeEliminarse");
        int id = 0;
        boolean expResultado = true;
        boolean resultado = dao.puedeEliminarse(id);
        assertEquals(expResultado, resultado);
    }
}
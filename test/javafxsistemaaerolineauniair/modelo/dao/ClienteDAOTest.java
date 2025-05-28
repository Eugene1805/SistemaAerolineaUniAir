/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.dao;

import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Cliente;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class ClienteDAOTest {
    /**
     * Test of obtenerNombresColumnas method, of class ClienteDAO.
     */
    @Test
    public void testObtenerNombresColumnas() {
        System.out.println("obtenerNombresColumnas");
        ClienteDAO dao = new ClienteDAO();
        String[] expResultado = {"ID","Nombre", "Apellido paterno", "Apellido materno",
            "Nacionalidad", "Fecha de nacimiento"};
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }

    /**
     * Test of obtenerValoresFila method, of class ClienteDAO.
     */
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
        
        ClienteDAO dao = new ClienteDAO();
        String[] expResultado = {
            "1", "Juan", "Pérez", "Gómez", "Mexicana", "2001-01-01"
        };
        String[] resultado = dao.obtenerValoresFila(cliente);
        assertArrayEquals(expResultado, resultado);
    }

    /**
     * Test of buscarPorId method, of class ClienteDAO.
     */
    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("buscarPorId");
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente();
        int id = dao.generarIdUnico();
        cliente.setId(id);
        cliente.setNombre("Juan");
        cliente.setApellidoPaterno("Pérez");
        cliente.setApellidoMaterno("Gómez");
        cliente.setNacionalidad("Mexicana");
        cliente.setFechaNacimiento("2000-01-01");

        List<Cliente> lista = dao.obtenerTodos();
        lista.add(cliente);
        dao.guardarTodos(lista);

        Cliente resultado = dao.buscarPorId(id);
        assertNotNull("El cliente debe existir", resultado);
        assertEquals("El ID debe coincidir", id, resultado.getId());
    }

    /**
     * Test of actualizar method, of class ClienteDAO.
     */
    @Test
    public void testActualizar() throws Exception {
        System.out.println("actualizar");
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente();
        int id = dao.generarIdUnico();
        cliente.setId(id);
        cliente.setNombre("Ana");
        cliente.setApellidoPaterno("López");
        cliente.setApellidoMaterno("Ramírez");
        cliente.setNacionalidad("Argentina");
        cliente.setFechaNacimiento("1995-05-10");

        List<Cliente> lista = dao.obtenerTodos();
        lista.add(cliente);
        dao.guardarTodos(lista);

        Cliente actualizado = new Cliente();
        actualizado.setId(id);
        actualizado.setNombre("Ana María"); // Cambiado
        actualizado.setApellidoPaterno("López");
        actualizado.setApellidoMaterno("Ramírez");
        actualizado.setNacionalidad("Argentina");
        actualizado.setFechaNacimiento("1995-05-10");

        dao.actualizar(actualizado);

        Cliente resultado = dao.buscarPorId(id);
        assertEquals("El nombre debe haberse actualizado", "Ana María", resultado.getNombre());
    }

    /**
     * Test of eliminar method, of class ClienteDAO.
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        ClienteDAO dao = new ClienteDAO();

        Cliente cliente = new Cliente();
        int id = dao.generarIdUnico();
        cliente.setId(id);
        cliente.setNombre("Carlos");
        cliente.setApellidoPaterno("Mendoza");
        cliente.setApellidoMaterno("Flores");
        cliente.setNacionalidad("Colombiana");
        cliente.setFechaNacimiento("1990-02-20");

        List<Cliente> lista = dao.obtenerTodos();
        lista.add(cliente);
        dao.guardarTodos(lista);

        dao.eliminar(id);

        Cliente eliminado = dao.buscarPorId(id);
        assertNull("El cliente debería haber sido eliminado", eliminado);
    }

    /**
     * Test of generarIdUnico method, of class ClienteDAO.
     */
    @Test
    public void testGenerarIdUnico() throws Exception {
        ClienteDAO dao = new ClienteDAO();
        
        int id1 = dao.generarIdUnico();
        int id2 = dao.generarIdUnico();

        assertNotEquals("Los IDs deben ser diferentes", id1, id2);
        assertTrue("ID debe ser positivo", id1 > 0 && id2 > 0);
    }

    /**
     * Test of puedeEliminarse method, of class ClienteDAO.
     */
    @Test
    public void testPuedeEliminarse() throws Exception {
        //FIX
        System.out.println("puedeEliminarse");
        int id = 0;
        ClienteDAO dao = new ClienteDAO();
        boolean expResultado = true;
        boolean resultado = dao.puedeEliminarse(id);
        assertEquals(expResultado, resultado);
    }
    
}

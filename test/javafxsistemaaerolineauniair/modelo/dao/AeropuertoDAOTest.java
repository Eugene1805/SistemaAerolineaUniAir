/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class AeropuertoDAOTest {
    
    private AeropuertoDAO dao = new AeropuertoDAO();
    
    @After
    public void limpiarDatos() throws Exception {
        // Limpiar todos los aeropuertos creados en los tests
        for (Aeropuerto aeropuerto : dao.obtenerTodos()) {
            // Asegurarse de que no sean datos críticos del sistema
            if (aeropuerto.getNombre().contains("Test") || aeropuerto.getNombre().contains("Actualizado")) {
                dao.eliminar(aeropuerto.getId());
            }
        }
    }
    /**
     * Test of obtenerNombresColumnas method, of class AeropuertoDAO.
     */
    @Test
    public void testObtenerNombresColumnas() {
        System.out.println("obtenerNombresColumnas");
        String[] expResultado = {"ID","Nombre", "Dirección", "Persona de Contacto", "Teléfono", "Flota"};
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }


    /**
     * Test of obtenerValoresFila method, of class AeropuertoDAO.
     */
    @Test
    public void testObtenerValoresFila() {
        System.out.println("obtenerValoresFila");
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
        assertArrayEquals(expResultado, resultado);
    }

    /**
     * Test of buscarPorId method, of class AeropuertoDAO.
     */
    @Test
    public void testBuscarPorId() throws Exception {
        int id = dao.generarIdUnico();

        Aeropuerto aeropuerto = new Aeropuerto(id, "Aeropuerto Test", "Dirección", "Contacto", "555-555", 3);
        dao.agregar(aeropuerto);

        Aeropuerto resultado = dao.buscarPorId(id);

        assertNotNull("Debe encontrar el aeropuerto", resultado);
        assertEquals("El nombre debe coincidir", "Aeropuerto Test", resultado.getNombre());
    }

    /**
     * Test of actualizar method, of class AeropuertoDAO.
     */
    @Test
    public void testActualizar() throws Exception {
        int id = dao.generarIdUnico();

        Aeropuerto original = new Aeropuerto(id, "Original", "Dirección", "Persona", "000", 2);
        dao.agregar(original);

        Aeropuerto actualizado = new Aeropuerto(id, "Actualizado", "Nueva Dirección", "Nuevo Contacto", "111", 5);
        dao.actualizar(actualizado);

        Aeropuerto resultado = dao.buscarPorId(id);
        assertEquals("Nombre debe actualizarse", "Actualizado", resultado.getNombre());
    }

    /**
     * Test of eliminar method, of class AeropuertoDAO.
     */
    @Test
    public void testEliminar() throws Exception {
        int id = dao.generarIdUnico();

        Aeropuerto aeropuerto = new Aeropuerto(id, "Para eliminar", "Dirección", "Persona", "123", 1);
        dao.agregar(aeropuerto);

        dao.eliminar(id);

        Aeropuerto eliminado = dao.buscarPorId(id);
        assertNull("El aeropuerto debe haber sido eliminado", eliminado);
    }

    /**
     * Test of generarIdUnico method, of class AeropuertoDAO.
     */
    @Test
    public void testGenerarIdUnico() throws Exception {
        
        int id1 = dao.generarIdUnico();
        int id2 = dao.generarIdUnico();

        assertNotEquals("Los IDs deben ser diferentes", id1, id2);
        assertTrue("ID debe ser positivo", id1 > 0 && id2 > 0);
    }

    /**
     * Test of puedeEliminarse method, of class AeropuertoDAO.
     */
    @Test
    public void testPuedeEliminarse() {
        //FIX
        System.out.println("puedeEliminarse");
        int id = 0;
        boolean expResultado = true;
        boolean resultado = dao.puedeEliminarse(id);
        assertEquals(expResultado, resultado);
    }
    
}

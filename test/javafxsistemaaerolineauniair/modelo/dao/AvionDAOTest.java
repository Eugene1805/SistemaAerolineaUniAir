/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.dao;

import java.time.LocalDate;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class AvionDAOTest {

    /**
     * Test of obtenerNombresColumnas method, of class AvionDAO.
     */
    @Test
    public void testObtenerNombresColumnas() {
        System.out.println("obtenerNombresColumnas");
        AvionDAO dao = new AvionDAO();
        String[] expResultado = {"ID", "Capacidad", "Modelo", "Peso", "Estatus", "Fecha de Ingreso", "ID Aerolínea", "Asientos"};
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }

    /**
     * Test of obtenerValoresFila method, of class AvionDAO.
     */
    @Test
    public void testObtenerValoresFila() {
        System.out.println("obtenerValoresFila");
        Avion avion = new Avion();
        avion.setIdAvion(1);
        avion.setIdAerolinea(5);
        avion.setAsiento(2);
        avion.setCapacidad(10);
        avion.setEstatus("Realizado");
        avion.setFechaDeIngreso(LocalDate.MIN);
        avion.setModelo("Boeing 747");
        avion.setPeso(450);
        AvionDAO dao = new AvionDAO();
        String[] expResultado = {
            "1", "10", "Boeing 747", "450.0", "Realizado", LocalDate.MIN.toString(), "5", "2"
        };
        String[] resultado = dao.obtenerValoresFila(avion);
        assertArrayEquals(expResultado, resultado);
        
    }

    /**
     * Test of buscarPorId method, of class AvionDAO.
     */
    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("buscarPorId");
        int id = 1926901376;
        AvionDAO dao = new AvionDAO();

        Avion resultado = dao.buscarPorId(id);

        assertNotNull("El avión no debería ser null", resultado);
        assertEquals("El ID del avión no coincide", id, resultado.getIdAvion());
    }

    /**
     * Test of actualizar method, of class AvionDAO.
     */
    @Test
    public void testActualizar() throws Exception {
        System.out.println("actualizar");
        AvionDAO dao = new AvionDAO();

        Avion avionExistente = dao.buscarPorId(1926901376);
        assertNotNull("El avión debe existir para actualizarlo", avionExistente);

        avionExistente.setModelo("Airbus A320");

        dao.actualizar(avionExistente);

        Avion avionActualizado = dao.buscarPorId(1926901376);
        assertEquals("Airbus A320", avionActualizado.getModelo());
    }

    /**
     * Test of eliminar method, of class AvionDAO.
     */
    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        AvionDAO dao = new AvionDAO();

        Avion avionNuevo = new Avion();
        avionNuevo.setIdAvion(999);
        avionNuevo.setIdAerolinea(2);
        avionNuevo.setAsiento(1);
        avionNuevo.setCapacidad(100);
        avionNuevo.setEstatus("Disponible");
        avionNuevo.setFechaDeIngreso(LocalDate.now());
        avionNuevo.setModelo("Avion Test");
        avionNuevo.setPeso(500);

        dao.agregar(avionNuevo);
        
        dao.eliminar(999);

        Avion avionEliminado = dao.buscarPorId(999);
        assertNull("El avión debería haber sido eliminado", avionEliminado);
    }

    /**
     * Test of generarIdUnico method, of class AvionDAO.
     */
    @Test
    public void testGenerarIdUnico() throws Exception {
        AvionDAO dao = new AvionDAO();
        
        int id1 = dao.generarIdUnico();
        int id2 = dao.generarIdUnico();

        assertNotEquals("Los IDs deben ser diferentes", id1, id2);
        assertTrue("ID debe ser positivo", id1 > 0 && id2 > 0);
    }
    
}

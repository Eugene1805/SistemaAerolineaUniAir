package javafxsistemaaerolineauniair.modelo.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class AvionDAOTest {

    private AvionDAO dao = new AvionDAO();
    private List<Integer> idsTemporales = new ArrayList<>(); // Para registrar aviones creados

    @After
    public void limpiarDatos() throws Exception {
        for (int id : idsTemporales) {
            try {
                dao.eliminar(id);
            } catch (Exception e) {
                System.err.println("Error al limpiar avión temporal ID: " + id);
                e.printStackTrace();
            }
        }
        idsTemporales.clear();
    }

    @Test
    public void testObtenerNombresColumnas() {
        System.out.println("obtenerNombresColumnas");
        String[] expResultado = {"ID", "Capacidad", "Modelo", "Peso", 
            "Estatus", "Fecha de Ingreso", "ID Aerolínea", "Asientos"};
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }

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
        
        String[] expResultado = {
            "1", "10", "Boeing 747", "450.0", "Realizado", LocalDate.MIN.toString(), "5", "2"
        };
        String[] resultado = dao.obtenerValoresFila(avion);
        assertArrayEquals(expResultado, resultado);
    }

    @Test
    public void testBuscarPorId() throws Exception {
        System.out.println("buscarPorId");
        Avion avionTemp = new Avion();
        int idTemp = dao.generarIdUnico();
        avionTemp.setIdAvion(idTemp);
        avionTemp.setModelo("Avión Temporal");
        dao.agregar(avionTemp);
        idsTemporales.add(idTemp); // Registrar para limpieza

        Avion resultado = dao.buscarPorId(idTemp);
        assertNotNull("El avión no debería ser null", resultado);
        assertEquals("El ID del avión no coincide", idTemp, resultado.getIdAvion());
    }

    @Test
    public void testActualizar() throws Exception {
        System.out.println("actualizar");
        Avion avionTemp = new Avion();
        int idTemp = dao.generarIdUnico();
        avionTemp.setIdAvion(idTemp);
        avionTemp.setModelo("Modelo Original");
        dao.agregar(avionTemp);
        idsTemporales.add(idTemp); // Registrar para limpieza

        avionTemp.setModelo("Modelo Actualizado");
        dao.actualizar(avionTemp);

        Avion avionActualizado = dao.buscarPorId(idTemp);
        assertEquals("Modelo Actualizado", avionActualizado.getModelo());
    }

    @Test
    public void testEliminar() throws Exception {
        System.out.println("eliminar");
        int idTemp = dao.generarIdUnico();
        
        Avion avionNuevo = new Avion();
        avionNuevo.setIdAvion(idTemp);
        avionNuevo.setIdAerolinea(2);
        avionNuevo.setAsiento(1);
        avionNuevo.setCapacidad(100);
        avionNuevo.setEstatus("Disponible");
        avionNuevo.setFechaDeIngreso(LocalDate.now());
        avionNuevo.setModelo("Avion Test");
        avionNuevo.setPeso(500);

        dao.agregar(avionNuevo);
        
        dao.eliminar(idTemp);
        Avion avionEliminado = dao.buscarPorId(idTemp);
        assertNull("El avión debería haber sido eliminado", avionEliminado);
    }

    @Test
    public void testGenerarIdUnico() throws Exception {
        int id1 = dao.generarIdUnico();
        int id2 = dao.generarIdUnico();
        assertNotEquals("Los IDs deben ser diferentes", id1, id2);
        assertTrue("ID debe ser positivo", id1 > 0 && id2 > 0);
    }
}
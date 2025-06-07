package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.Administrativo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase AdministrativoDAO.
 * Se asegura que todos los datos creados durante las pruebas se limpien después.
 * 
 * @author meler
 */
public class AdministrativoDAOTest {

    private AdministrativoDAO dao;

    @Before
    public void setUp() throws Exception {
        dao = new AdministrativoDAO();
    }

    @After
    public void limpiarDatos() throws Exception {
        // Limpiar todos los administrativos creados en los tests
        List<Administrativo> administrativos = dao.obtenerTodos();
        for (Administrativo administrativo : administrativos) {
            if (administrativo.getNombre().contains("Test") || 
                administrativo.getNombre().contains("Actualizado") || 
                administrativo.getNombre().contains("Eliminar")) {
                dao.eliminar(administrativo.getNoPersonal());
            }
        }
    }

    @Test
    public void testObtenerNombresColumnas() {
        String[] expResultado = {
            "No. Personal", "Nombre", "Apellido Paterno", "Apellido Materno", "Dirección",
            "Fecha de Nacimiento", "Género", "Salario", "Departamento"
        };
        String[] resultado = dao.obtenerNombresColumnas();
        assertArrayEquals("Las columnas deben coincidir", expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() {
        Administrativo administrativo = new Administrativo();
        administrativo.setNoPersonal(1001);
        administrativo.setNombre("Carlos");
        administrativo.setApellidoPaterno("Ramírez");
        administrativo.setApellidoMaterno("Gómez");
        administrativo.setDireccion("Av. Siempre Viva 123");
        administrativo.setFechaNacimiento(LocalDate.of(1985, 8, 10));
        administrativo.setGenero("Masculino");
        administrativo.setSalario(30000.0);
        administrativo.setDepartamento("Recursos Humanos");

        String[] expResultado = {
            "1001", "Carlos", "Ramírez", "Gómez", "Av. Siempre Viva 123",
            "1985-08-10", "Masculino", "30000.0", "Recursos Humanos"
        };
        String[] resultado = dao.obtenerValoresFila(administrativo);
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

        Administrativo administrativo = new Administrativo();
        administrativo.setNoPersonal(id);
        administrativo.setNombre("Administrativo Test");
        
        List<Administrativo> administrativos = dao.obtenerTodos();
        administrativos.add(administrativo);
        dao.guardarTodos(administrativos);

        Administrativo resultado = dao.buscarPorId(id);

        assertNotNull("El administrativo no debe ser nulo", resultado);
        assertEquals("El nombre debe coincidir", "Administrativo Test", resultado.getNombre());
    }

    @Test
    public void testActualizar() throws Exception {
        int id = dao.generarIdUnico();

        Administrativo original = new Administrativo();
        original.setNoPersonal(id);
        original.setNombre("Original");
        
        List<Administrativo> administrativos = dao.obtenerTodos();
        administrativos.add(original);
        dao.guardarTodos(administrativos);

        Administrativo actualizado = new Administrativo();
        actualizado.setNoPersonal(id);
        actualizado.setNombre("Actualizado");
        
        dao.actualizar(actualizado);

        Administrativo resultado = dao.buscarPorId(id);
        assertEquals("El nombre debe haber sido actualizado", "Actualizado", resultado.getNombre());
    }

    @Test
    public void testEliminar() throws Exception {
        int id = dao.generarIdUnico();

        Administrativo administrativo = new Administrativo();
        administrativo.setNoPersonal(id);
        administrativo.setNombre("Eliminar");
        
        List<Administrativo> administrativos = dao.obtenerTodos();
        administrativos.add(administrativo);
        dao.guardarTodos(administrativos);

        // Eliminar el administrativo
        dao.eliminar(id);

        Administrativo eliminado = dao.buscarPorId(id);
        assertNull("El administrativo debe haber sido eliminado", eliminado);
    }
}

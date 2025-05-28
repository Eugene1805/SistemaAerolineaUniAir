/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Cliente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class GenericDAOTest {

    private static ClienteDAO clienteDAO;
    private static final String RUTA_TEMPORAL = "data/test_clientes.json";

    @BeforeClass
    public static void setUpClass() throws IOException {
        File file = new File(RUTA_TEMPORAL);
        file.getParentFile().mkdirs(); 
        if (!file.exists()) {
            Files.write(file.toPath(), "[]".getBytes()); 
        }
        clienteDAO = new ClienteDAO(RUTA_TEMPORAL);
    }

    @After
    public void limpiarArchivo() throws IOException {
        // Limpiar contenido entre cada prueba
        Files.write(Paths.get(RUTA_TEMPORAL), "[]".getBytes());
    }

    @AfterClass
    public static void tearDownClass() {
        new File(RUTA_TEMPORAL).delete();
    }

    @Test
    public void testGuardarYObtener() throws IOException {
        Cliente cliente = crearClienteEjemplo();
        clienteDAO.agregar(cliente);

        List<Cliente> lista = clienteDAO.obtenerTodos();
        assertEquals(1, lista.size());
        assertEquals("Juan", lista.get(0).getNombre());
    }

    @Test
    public void testBuscarPorId() throws IOException {
        Cliente cliente = crearClienteEjemplo();
        clienteDAO.agregar(cliente);

        Cliente encontrado = clienteDAO.buscarPorId(cliente.getId());
        assertNotNull(encontrado);
        assertEquals("Pérez", encontrado.getApellidoPaterno());
    }

    @Test
    public void testActualizar() throws IOException {
        Cliente cliente = crearClienteEjemplo();
        clienteDAO.agregar(cliente);

        cliente.setNombre("NombreModificado");
        clienteDAO.actualizar(cliente);

        Cliente actualizado = clienteDAO.buscarPorId(cliente.getId());
        assertEquals("NombreModificado", actualizado.getNombre());
    }

    @Test
    public void testEliminar() throws IOException {
        Cliente cliente = crearClienteEjemplo();
        clienteDAO.agregar(cliente);

        clienteDAO.eliminar(cliente.getId());

        Cliente eliminado = clienteDAO.buscarPorId(cliente.getId());
        assertNull(eliminado);
    }

    @Test
    public void testGenerarIdUnico() throws IOException {
        int nuevoId = clienteDAO.generarIdUnico();

        Cliente cliente = crearClienteEjemplo();
        cliente.setId(nuevoId);
        clienteDAO.agregar(cliente);

        assertEquals(nuevoId, clienteDAO.obtenerTodos().get(0).getId());
    }

    private Cliente crearClienteEjemplo() {
        Cliente cliente = new Cliente();
        cliente.setId(12345);
        cliente.setNombre("Juan");
        cliente.setApellidoPaterno("Pérez");
        cliente.setApellidoMaterno("López");
        cliente.setNacionalidad("Mexicana");
        cliente.setFechaNacimiento("1990-01-01");
        return cliente;
    }
}

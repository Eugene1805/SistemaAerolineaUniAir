package javafxsistemaaerolineauniair.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Pruebas unitarias para la clase LoginDAO.
 * Crea una copia del archivo JSON original para utilizarlos en su normalidad.
 * Se asegura que todos los datos creados durante las pruebas se limpien después.
 * 
 * @author uriel
 */
public class LoginDAOTest {
    
    private LoginDAO dao;
    private Path backupPath;
    private final String TEST_USERNAME = "testAdmin";
    private final String TEST_PASSWORD = "test1234";

    @Before
    public void setUp() throws IOException {
        // Crear backup del archivo original
        File originalFile = new File(LoginDAO.obtenerRutaCompleta());
        backupPath = Paths.get(LoginDAO.obtenerRutaCompleta() + ".backup");
        Files.copy(originalFile.toPath(), backupPath, StandardCopyOption.REPLACE_EXISTING);
        
        // 2. Inicializar DAO
        dao = new LoginDAO();
    }

    @After
    public void limpiarDatos() throws IOException {
        // Restaurar el backup original
        Files.copy(backupPath, Paths.get(LoginDAO.obtenerRutaCompleta()), StandardCopyOption.REPLACE_EXISTING);
        // Eliminar el archivo backup
        Files.deleteIfExists(backupPath);
    }

    @Test
    public void testVerificarCredenciales() throws Exception {
        // Crear usuario de prueba con TODOS los campos necesarios
        Usuario usuarioPrueba = new Usuario();
        usuarioPrueba.setUsuario(TEST_USERNAME);
        usuarioPrueba.setContraseña(TEST_PASSWORD);

        // Guardar usando el DAO real (no manipular listas directamente)
        List<Usuario> usuarios = dao.obtenerTodos();
        usuarios.add(usuarioPrueba);
        dao.guardarTodos(usuarios);

        // Verificar
        Usuario result = dao.verificarCredenciales(TEST_USERNAME, TEST_PASSWORD);
        assertNotNull("El usuario debería existir", result);
        assertEquals("El nombre de usuario debería coincidir", TEST_USERNAME, result.getUsuario());
        assertEquals("La contraseña debería coincidir", TEST_PASSWORD, result.getContraseña());
    }
    
    @Test
    public void testVerificarCredencialesIncorrectas() throws Exception {
        Usuario result = dao.verificarCredenciales("usuarioInexistente", "claveIncorrecta");
        assertNull("No debería encontrar usuario", result);
    }
}
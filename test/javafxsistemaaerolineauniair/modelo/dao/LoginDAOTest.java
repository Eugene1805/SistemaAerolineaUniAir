package javafxsistemaaerolineauniair.modelo.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Usuario;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class LoginDAOTest {
    
    private LoginDAO dao;
    private List<Usuario> usuariosOriginales;
    private List<String> usuariosTemporales; // Para registrar usuarios de prueba

    @Before
    public void setUp() throws IOException {
        dao = new LoginDAO();
        usuariosOriginales = new ArrayList<>(dao.obtenerTodos());
        usuariosTemporales = new ArrayList<>();
    }

    @After
    public void limpiarDatos() throws Exception {
        dao.guardarTodos(new ArrayList<>(usuariosOriginales));
        
        List<Usuario> usuariosActuales = dao.obtenerTodos();
        for (String username : usuariosTemporales) {
            usuariosActuales.removeIf(u -> u.getUsuario().equals(username));
        }
        dao.guardarTodos(usuariosActuales);
    }

    @Test
    public void testVerificarCredenciales() throws Exception {
        System.out.println("verificarCredenciales");
    
        String usernameTest = "testAdmin";
        String passwordTest = "test1234";
        usuariosTemporales.add(usernameTest);

        Usuario usuarioPrueba = new Usuario();
        usuarioPrueba.setUsuario(usernameTest);
        usuarioPrueba.setContraseña(passwordTest);

        List<Usuario> usuariosModificados = new ArrayList<>(dao.obtenerTodos());
        usuariosModificados.add(usuarioPrueba);
        dao.guardarTodos(usuariosModificados);

        Usuario result = dao.verificarCredenciales(usernameTest, passwordTest);

        assertNotNull("Debe encontrar al usuario", result);
        assertEquals(usernameTest, result.getUsuario());
        assertEquals(passwordTest, result.getContraseña());
    }
    
    @Test
    public void testVerificarCredencialesIncorrectas() throws Exception {
        System.out.println("verificarCredencialesIncorrectas");
        
        Usuario result = dao.verificarCredenciales("usuarioInexistente", "claveIncorrecta");
        assertNull("No debe encontrar al usuario", result);
    }
}
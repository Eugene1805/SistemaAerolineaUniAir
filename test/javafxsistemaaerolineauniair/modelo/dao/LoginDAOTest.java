/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.dao;

import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Usuario;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class LoginDAOTest {
    /**
     * Test of verificarCredenciales method, of class LoginDAO.
     */
    @Test
    public void testVerificarCredenciales() throws Exception {
        System.out.println("verificarCredenciales");
    
        Usuario usuarioPrueba = new Usuario();
        usuarioPrueba.setUsuario("admin");
        usuarioPrueba.setContraseña("1234");

        LoginDAO dao = new LoginDAO();
        List<Usuario> usuarios = dao.obtenerTodos();
        usuarios.add(usuarioPrueba);
        dao.guardarTodos(usuarios);

        Usuario result = dao.verificarCredenciales("admin", "1234");

        assertNotNull("Debe encontrar al usuario", result);
        assertEquals("admin", result.getUsuario());
        assertEquals("1234", result.getContraseña());
    }
    
}

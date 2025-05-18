/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javafxsistemaaerolineauniair.modelo.pojo.Usuario;

/**
 *
 * @author uriel
 */
public class LoginDAO extends GenericDAO<Usuario>{
    
    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "usuario.json";

    public LoginDAO() {
        super(obtenerRutaCompleta(), Usuario.class);
    }
    
    public Usuario verificarCredenciales(String usuario, String contrasena) throws IOException{
        return obtenerTodos().stream()
            .filter(u -> ((u.getUsuario() == null ? usuario == null : u.getUsuario().equals(usuario)) && (u.getContraseña() == null ? contrasena == null : u.getContraseña().equals(contrasena))))
            .findFirst()
            .orElse(null);
    }

    /**
     * Obtiene la ruta completa del archivo de datos
     * @return Ruta absoluta del archivo JSON
     */
    private static String obtenerRutaCompleta() {
        // Crear directorio si no existe
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        return Paths.get(DATA_DIR, FILE_NAME).toString();
    }
    
    @Override
    protected String[] obtenerNombresColumnas() {
        throw new UnsupportedOperationException("Not supported."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    protected String[] obtenerValoresFila(Usuario item) {
        throw new UnsupportedOperationException("Not supported."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}

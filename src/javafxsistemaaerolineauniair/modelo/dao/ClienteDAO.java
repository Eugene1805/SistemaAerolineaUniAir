package javafxsistemaaerolineauniair.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javafxsistemaaerolineauniair.excepciones.ClienteConVueloAsociadoException;
import javafxsistemaaerolineauniair.modelo.pojo.Cliente;


/**
 *
 * @author eugen
 */
public class ClienteDAO extends GenericDAO<Cliente>{

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "clientes.json";
    
    public ClienteDAO() {
        super(obtenerRutaCompleta(), Cliente.class);
    }
    
    public ClienteDAO(String rutaArchivo) {
        super(rutaArchivo, Cliente.class);
    }

    
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
        return new String[] {"ID","Nombre", "Apellido paterno", "Apellido materno",
            "Nacionalidad", "Fecha de nacimiento"};
    }

    @Override
    protected String[] obtenerValoresFila(Cliente cliente) {
        return new String [] {
            String.valueOf(cliente.getId()),
            cliente.getNombre(),
            cliente.getApellidoPaterno(),
            cliente.getApellidoMaterno(),
            cliente.getNacionalidad(),
            cliente.getFechaNacimiento()
        };
    }
    
    public Cliente buscarPorId(int id) throws IOException {
        return obtenerTodos().stream()
            .filter(c -> c.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    public void actualizar(Cliente clienteActualizado) throws IOException {
        List<Cliente> clientes = obtenerTodos();
        clientes.replaceAll(c -> 
            c.getId() == clienteActualizado.getId() ?
            clienteActualizado : c);
        guardarTodos(clientes);
    }
    
    public void eliminar(int id) throws IOException{
        List<Cliente> clientes = obtenerTodos();
        clientes.removeIf(c -> c.getId() ==  id);
        guardarTodos(clientes);
    }
    
    /**
     * Genera un ID único para nuevos clientes
     * @return ID generado
     * @throws IOException 
     */
    public int generarIdUnico() throws IOException {
        int nuevoId;
        do {
            nuevoId = UUID.randomUUID().hashCode() & Integer.MAX_VALUE; // Garantiza positivo
        } while (buscarPorId(nuevoId) != null);
        
        return nuevoId;
    }
    
    /**
     * Valida si un cliente puede ser eliminado (sin vuelos asociados)
     * @param id ID del cliente
     * @return true si se puede eliminar
     */
    public boolean puedeEliminarse(int id) throws ClienteConVueloAsociadoException{ //TODO
        //Lanzar excepcion en caso que no se pueda eliminar
        // Implementar lógica de verificación con VueloDAO
        try {
            // VueloDAO vueloDAO = DAOManager.getInstance().getVueloDAO();
            // return vueloDAO.buscarPorAeropuerto(id).isEmpty();
            return true; // Temporal - implementar la verificación real
        } catch (Exception e) {
            return false;
        }
    }
}

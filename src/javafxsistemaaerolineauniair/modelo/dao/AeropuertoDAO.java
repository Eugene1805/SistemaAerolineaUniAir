package javafxsistemaaerolineauniair.modelo.dao;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;

/**
 *
 * @author eugen
 */
public class AeropuertoDAO extends GenericDAO<Aeropuerto>{
    
    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "aeropuertos.json";
    
    public AeropuertoDAO() {
        super(obtenerRutaCompleta(), Aeropuerto.class);
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
        return new String[]{"ID","Nombre", "Dirección", "Persona de Contacto", "Teléfono", "Flota"};
    }
    
    @Override
    protected String[] obtenerValoresFila(Aeropuerto aeropuerto) {
        return new String[]{
            String.valueOf(aeropuerto.getId()),
            aeropuerto.getNombre(),
            aeropuerto.getDireccion(),
            aeropuerto.getPersonaContacto(),
            aeropuerto.getTelefono(),
            String.valueOf(aeropuerto.getFlota())
        };
    }
    
    // Métodos específicos para Aeropuerto
    
    /**
     * Busca un aeropuerto por su ID
     * @param id ID del aeropuerto
     * @return Aeropuerto encontrada o null
     * @throws IOException 
     */
    public Aeropuerto buscarPorId(int id) throws IOException {
        return obtenerTodos().stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .orElse(null);
    }
    
    /**
     * Actualiza un aeropuerto existente
     * @param aeropuertoActualizado  Aeropuerto con datos actualizados
     * @throws IOException 
     */
    public void actualizar(Aeropuerto aeropuertoActualizado) throws IOException {
        List<Aeropuerto> aeropuertos = obtenerTodos();
        aeropuertos.replaceAll(a -> 
            a.getId() == aeropuertoActualizado.getId() ? 
            aeropuertoActualizado : a);
        guardarTodos(aeropuertos);
    }
    
    /**
     * Elimina un aeropuerto por su ID
     * @param id ID del aeropuerto a eliminar
     * @throws IOException 
     */
    public void eliminar(int id) throws IOException {
        List<Aeropuerto> aeropuertos = obtenerTodos();
        aeropuertos.removeIf(a -> a.getId() == id);
        guardarTodos(aeropuertos);
    }
    
    /**
     * Genera un ID único para nuevos aeropuertos
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
     * Valida si un aeropuerto puede ser eliminado (sin vuelos asociados)
     * @param id ID del aeropuerto
     * @return true si se puede eliminar
     */
    public boolean puedeEliminarse(int id) { //TODO 
        //Posible nueva excepcion personalidada AeropuertoConVueloAsociadoException
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

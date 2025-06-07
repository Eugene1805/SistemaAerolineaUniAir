package javafxsistemaaerolineauniair.modelo.dao;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javafxsistemaaerolineauniair.excepciones.AeropuertoConVuelosException;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;

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
     * Verifica si un aeropuerto puede ser eliminado y, si es así, lo elimina.
     * Lanza una excepción si el aeropuerto tiene vuelos asociados.
     *
     * @param idAeropuertoParaEliminar El ID del aeropuerto a eliminar.
     * @throws IOException Si ocurre un error de lectura/escritura de archivos.
     * @throws AeropuertoConVuelosException Si el aeropuerto tiene vuelos asociados.
     */
    public void verificarYEliminar(int idAeropuertoParaEliminar) throws IOException, AeropuertoConVuelosException {
        // Verificar si hay vuelos asociados
        AvionDAO avionDAO = new AvionDAO(); // Asumo que VueloDAO existe
        List<Avion> vuelosAsociados = avionDAO.buscarPorAeropuerto(idAeropuertoParaEliminar);

        if (!vuelosAsociados.isEmpty()) {
            // Si la lista no está vacía, no se puede eliminar. Lanzamos nuestra excepción.
            String mensaje = String.format(
                "El aeropuerto no se puede eliminar. Está asociado a %d vuelo(s).",
                vuelosAsociados.size()
            );
            throw new AeropuertoConVuelosException(mensaje);
        }

        // Si no hay vuelos, proceder con la eliminación (lógica de tu antiguo método eliminar)
        List<Aeropuerto> aeropuertos = obtenerTodos();
        aeropuertos.removeIf(a -> a.getId() == idAeropuertoParaEliminar);
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
}

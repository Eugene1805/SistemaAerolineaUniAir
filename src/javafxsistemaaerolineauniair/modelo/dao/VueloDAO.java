package javafxsistemaaerolineauniair.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;

/**
 *
 * @author eugen
 */
public class VueloDAO extends GenericDAO<Vuelo>{
    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "vuelos.json";

    public VueloDAO() {
        super(obtenerRutaCompleta(), Vuelo.class);
    }

    private static String obtenerRutaCompleta(){
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        return Paths.get(DATA_DIR, FILE_NAME).toString();
    }

    @Override
    protected String[] obtenerNombresColumnas() {
        return new String[]{
          "ID Vuelo", "Pasajeros", "Ciudad Salida", "Ciudad Llegada", "Fecha Salida", "Hora Salida", "Fecha Llegada", "Hora Llegada",
            "Tiempo Recorrido (hrs)", "Costo Boleto"
        }; //, "Avion" //AGREGAR DESPUES DE COSTO BOLETO
    }

    @Override
    protected String[] obtenerValoresFila(Vuelo vuelo) {
        return new String[]{
            String.valueOf(vuelo.getIdVuelo()),
            String.valueOf(vuelo.getNumPasajeros()),
            vuelo.getCiudadSalida(),
            vuelo.getCiudadLlegada(),
            vuelo.getFechaSalida().toString(),
            vuelo.getHoraSalida().toString(),
            vuelo.getFechaLlegada().toString(),
            vuelo.getHoraLlegada().toString(),
            String.valueOf(vuelo.getTiempoRecorrido()),
            String.valueOf(vuelo.getCostoBoleto()),
            //vuelo.getIdAvion()
        
        };
    }

    public Vuelo buscarPorIdVuelo(int idVuelo) throws IOException{
        return (Vuelo) obtenerTodos().stream().filter(v -> v.getIdVuelo() == idVuelo).findFirst().orElse(null);
    }
    
    public void actualizar(Vuelo vueloActualizado) throws IOException{
        List<Vuelo> vuelos = obtenerTodos();
        vuelos.replaceAll(a ->
            a.getIdVuelo() == vueloActualizado.getIdVuelo() ?
                    vueloActualizado : a);
        guardarTodos(vuelos);
    }
    
    public void eliminar (int id) throws IOException{
        List<Vuelo> vuelos = obtenerTodos();
        vuelos.removeIf(a -> a.getIdVuelo() == id);
        guardarTodos(vuelos);
    }
    
    
    public int generarIdVueloUnico() throws IOException{
        int nuevoIdVuelo;
        do{
            nuevoIdVuelo = UUID.randomUUID().hashCode() & Integer.MAX_VALUE;
        }while (buscarPorIdVuelo(nuevoIdVuelo) != null);
        
        return nuevoIdVuelo;
    }
}

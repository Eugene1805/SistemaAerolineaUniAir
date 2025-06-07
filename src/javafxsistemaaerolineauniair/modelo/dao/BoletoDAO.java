package javafxsistemaaerolineauniair.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import javafxsistemaaerolineauniair.excepciones.VueloLlenoException;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Boleto;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;

/**
 *
 * @author eugen
 */
public class BoletoDAO extends GenericDAO<Boleto>{

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "boletos.json";
    
    public BoletoDAO() {
        super(obtenerRutaCompleta(), Boleto.class);
    }

    private static String obtenerRutaCompleta(){
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        return Paths.get(DATA_DIR, FILE_NAME).toString();
    }

    public void registrarCompraBoleto(Boleto nuevoBoleto) throws IOException, VueloLlenoException {
       
        VueloDAO vueloDAO = new VueloDAO();
        AvionDAO avionDAO = new AvionDAO();

        // Obtener el vuelo y el avión correspondientes
        Vuelo vuelo = vueloDAO.buscarPorIdVuelo(nuevoBoleto.getIdVuelo());
        if (vuelo == null) {
            throw new IOException("Error de integridad: El vuelo con ID " + nuevoBoleto.getIdVuelo() + " no existe.");
        }
        
        Avion avion = avionDAO.buscarPorId(vuelo.getIdAvion());
        if (avion == null) {
            throw new IOException("Error de integridad: El avión con ID " + vuelo.getIdAvion() + " no existe.");
        }

        // Contar los boletos ya vendidos para este vuelo
        long boletosVendidos = obtenerTodos().stream()
                .filter(b -> b.getIdVuelo() == nuevoBoleto.getIdVuelo())
                .count();

        //  Comparar con la capacidad del avión
        if (boletosVendidos >= avion.getCapacidad()) { // Asumiendo que Avion tiene getCapacidad()
            throw new VueloLlenoException("No se puede comprar el boleto. El vuelo " + nuevoBoleto.getIdVuelo() + " está lleno.");
        }

        //  Si todas las validaciones pasan, llamamos al método genérico para guardar.
        this.agregar(nuevoBoleto);
    }


    @Override
    protected String[] obtenerNombresColumnas() {
        return new String[]{
          "ID Vuelo", "Pasajeros", "Ciudad Salida", "Ciudad Llegada", "Fecha Salida", "Hora Salida", "Fecha Llegada", "Hora Llegada",
            "Tiempo Recorrido (hrs)", "Costo Boleto", "Avion", "Cliente", "Asiento"
        };
    }

    @Override
    protected String[] obtenerValoresFila(Boleto boleto) {
        AvionDAO avionDAO = new AvionDAO();
        
        //Construcción de cadena para Avion (modelo (id))
        String avionStr;
        try {
            Avion avion = avionDAO.buscarPorId(boleto.getIdAvion());
            if(avion != null){
                avionStr = avion.getModelo() + "(ID: )" + avion.getIdAvion() + ")";
            }else{
                avionStr = String.valueOf(boleto.getIdAvion());
            }
        } catch (IOException e) {
            avionStr = String.valueOf(boleto.getIdAvion());
        }
        
        return new String[]{
            String.valueOf(boleto.getIdVuelo()),
            String.valueOf(boleto.getNumPasajeros()),
            boleto.getCiudadSalida(),
            boleto.getCiudadLlegada(),
            boleto.getFechaSalida().toString(),
            boleto.getHoraSalida().toString(),
            boleto.getFechaLlegada().toString(),
            boleto.getHoraLlegada().toString(),
            String.valueOf(boleto.getTiempoRecorrido()),
            String.valueOf(boleto.getCostoBoleto()),
            avionStr,
            boleto.getNombreCliente(),
            boleto.getAsiento()
        };
    }
}

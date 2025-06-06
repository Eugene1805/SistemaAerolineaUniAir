package javafxsistemaaerolineauniair.modelo.dao;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javafxsistemaaerolineauniair.modelo.pojo.AsistenteVuelo;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Piloto;
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
    public void guardarTodos(List<Vuelo> lista) throws IOException{
        PilotoDAO pilotoDAO = new PilotoDAO();
        AsistenteVueloDAO asistenteDAO = new AsistenteVueloDAO();
        AvionDAO avionDAO = new AvionDAO();

        for (Vuelo v : lista) {
            List<String> nombresP = new ArrayList<>();
            for (Integer idPil : v.getPilotos()) {
                try {
                    Piloto p = pilotoDAO.buscarPorId(idPil);
                    if (p != null) {
                        nombresP.add(p.getNombre() + " " + p.getApellidoPaterno());
                    } else {
                        nombresP.add("ID:" + idPil);
                    }
                } catch (IOException e) {
                    nombresP.add("ID:" + idPil);
                }
            }
            v.setNombresPilotos(nombresP);

            List<String> nombresA = new ArrayList<>();
            for (Integer idAs : v.getAsistentes()) {
                try {
                    AsistenteVuelo a = asistenteDAO.buscarPorId(idAs);
                    if (a != null) {
                        nombresA.add(a.getNombre() + " " + a.getApellidoPaterno());
                    } else {
                        nombresA.add("ID:" + idAs);
                    }
                } catch (IOException e) {
                    nombresA.add("ID:" + idAs);
                }
            }
            v.setNombresAsistentes(nombresA);
            
            try {
                Avion avion = avionDAO.buscarPorId(v.getIdAvion());
                if (avion != null) {
                    v.setModeloAvion(avion.getModelo());
                }else{
                    v.setModeloAvion("ID:" + v.getIdAvion());
                }
            } catch (IOException e) {
                    v.setModeloAvion("ID:" + v.getIdAvion());
            }
        }
        super.guardarTodos(lista);
    }

    @Override
    protected String[] obtenerNombresColumnas() {
        return new String[]{
          "ID Vuelo", "Pasajeros", "Ciudad Salida", "Ciudad Llegada", "Fecha Salida", "Hora Salida", "Fecha Llegada", "Hora Llegada",
            "Tiempo Recorrido (hrs)", "Costo Boleto", "Avion", "Pilotos", "Asistentes"
        }; 
    }

    @Override
    protected String[] obtenerValoresFila(Vuelo vuelo) {
        PilotoDAO pilotoDAO = new PilotoDAO();
        AsistenteVueloDAO asistenteDAO = new AsistenteVueloDAO();
        AvionDAO avionDAO = new AvionDAO();
        
        //Construcción de cadena para Avion (modelo (id))
        String avionStr;
        try {
            Avion avion = avionDAO.buscarPorId(vuelo.getIdAvion());
            if(avion != null){
                avionStr = avion.getModelo() + "(ID: )" + avion.getIdAvion() + ")";
            }else{
                avionStr = String.valueOf(vuelo.getIdAvion());
            }
        } catch (IOException e) {
            avionStr = String.valueOf(vuelo.getIdAvion());
        }
        //Cadena de pilotos (Nombre apellido, nombre apellido )
        String pilotoStr = vuelo.getNombresPilotos().stream().collect(Collectors.joining(", "));
        
        //Cadena de asistentes (Nombre apellido)
        String asistesStr = vuelo.getNombresAsistentes().stream().collect(Collectors.joining(", "));
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
            avionStr,
            pilotoStr,
            asistesStr
        
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

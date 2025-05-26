package javafxsistemaaerolineauniair.modelo.dao;

import java.io.IOException;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;

public class AvionDAO extends GenericDAO<Avion> {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "aviones.json";

    public AvionDAO() {
        super(obtenerRutaCompleta(), Avion.class);
    }

    private static String obtenerRutaCompleta() {
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        return Paths.get(DATA_DIR, FILE_NAME).toString();
    }

    @Override
    protected String[] obtenerNombresColumnas() {
        return new String[]{"ID", "Capacidad", "Modelo", "Peso", "Estatus", "Fecha de Ingreso", "ID Aerolínea"};
    }

    @Override
    protected String[] obtenerValoresFila(Avion avion) {
        return new String[]{
            String.valueOf(avion.getIdAvion()),
            String.valueOf(avion.getCapacidad()),
            avion.getModelo(),
            String.valueOf(avion.getPeso()),
            avion.getEstatus(),
            avion.getFechaDeIngreso().toString(),
            String.valueOf(avion.getIdAerolinea())
        };
    }

    public Avion buscarPorId(int id) throws IOException {
        return obtenerTodos().stream()
            .filter(a -> a.getIdAvion() == id)
            .findFirst()
            .orElse(null);
    }

    public void actualizar(Avion avionActualizado) throws IOException {
        List<Avion> aviones = obtenerTodos();
        aviones.replaceAll(a -> a.getIdAvion() == avionActualizado.getIdAvion() ? avionActualizado : a);
        guardarTodos(aviones);
    }

    public void eliminar(int id) throws IOException {
        List<Avion> aviones = obtenerTodos();
        aviones.removeIf(a -> a.getIdAvion() == id);
        guardarTodos(aviones);
    }

    public int generarIdUnico() throws IOException {
        int nuevoId;
        do {
            nuevoId = UUID.randomUUID().hashCode() & Integer.MAX_VALUE;
        } while (buscarPorId(nuevoId) != null);
        return nuevoId;
    }
}

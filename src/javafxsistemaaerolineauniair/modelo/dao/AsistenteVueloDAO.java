package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.AsistenteVuelo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author meler
 */
public class AsistenteVueloDAO extends GenericDAO<AsistenteVuelo> {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "asistentes_vuelo.json";

    public AsistenteVueloDAO() {
        super(obtenerRutaCompleta(), AsistenteVuelo.class);
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
        return new String[]{
            "No. Personal", "Nombre", "Apellido Paterno", "Apellido Materno", "Dirección",
            "Fecha de Nacimiento", "Género", "Salario", "Horas de Asistencia", "No. Idiomas"
        };
    }

    @Override
    protected String[] obtenerValoresFila(AsistenteVuelo asistente) {
        return new String[]{
            String.valueOf(asistente.getNoPersonal()),
            asistente.getNombre(),
            asistente.getApellidoPaterno(),
            asistente.getApellidoMaterno(),
            asistente.getDireccion(),
            asistente.getFechaNacimiento().toString(),
            asistente.getGenero(),
            String.valueOf(asistente.getSalario()),
            String.valueOf(asistente.getNoHorasAsistencia()),
            String.valueOf(asistente.getNoIdiomas())
        };
    }

    public AsistenteVuelo buscarPorId(int id) throws IOException {
        return obtenerTodos().stream()
            .filter(a -> a.getNoPersonal() == id)
            .findFirst()
            .orElse(null);
    }

    public void actualizar(AsistenteVuelo actualizado) throws IOException {
        List<AsistenteVuelo> asistentes = obtenerTodos();
        asistentes.replaceAll(a -> a.getNoPersonal() == actualizado.getNoPersonal() ? actualizado : a);
        guardarTodos(asistentes);
    }

    public void eliminar(int id) throws IOException {
        List<AsistenteVuelo> asistentes = obtenerTodos();
        asistentes.removeIf(a -> a.getNoPersonal() == id);
        guardarTodos(asistentes);
    }

    public int generarIdUnico() throws IOException {
        int nuevoId;
        do {
            nuevoId = UUID.randomUUID().hashCode() & Integer.MAX_VALUE;
        } while (buscarPorId(nuevoId) != null);
        return nuevoId;
    }
}
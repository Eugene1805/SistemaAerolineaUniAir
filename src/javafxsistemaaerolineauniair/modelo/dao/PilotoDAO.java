package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.Piloto;
import javafxsistemaaerolineauniair.modelo.pojo.TipoLicencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
/**
 *
 * @author meler
 */
public class PilotoDAO extends GenericDAO<Piloto> {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "pilotos.json";

    public PilotoDAO() {
        super(obtenerRutaCompleta(), Piloto.class);
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
            "Fecha de Nacimiento", "Género", "Salario", "Tipo de Licencia",
            "Años de Experiencia", "Total de Horas"
        };
    }

    @Override
    protected String[] obtenerValoresFila(Piloto piloto) {
        return new String[]{
            String.valueOf(piloto.getNoPersonal()),
            piloto.getNombre(),
            piloto.getApellidoPaterno(),
            piloto.getApellidoMaterno(),
            piloto.getDireccion(),
            piloto.getFechaNacimiento().toString(),
            piloto.getGenero(),
            String.valueOf(piloto.getSalario()),
            piloto.getTipolicencia().getDescripcion(),
            String.valueOf(piloto.getAniosExperiencia()),
            String.valueOf(piloto.getTotalHoras())
        };
    }

    public Piloto buscarPorId(int id) throws IOException {
        return obtenerTodos().stream()
            .filter(p -> p.getNoPersonal() == id)
            .findFirst()
            .orElse(null);
    }

    public void actualizar(Piloto pilotoActualizado) throws IOException {
        List<Piloto> pilotos = obtenerTodos();
        pilotos.replaceAll(p -> p.getNoPersonal() == pilotoActualizado.getNoPersonal() ? pilotoActualizado : p);
        guardarTodos(pilotos);
    }

    public void eliminar(int id) throws IOException {
        List<Piloto> pilotos = obtenerTodos();
        pilotos.removeIf(p -> p.getNoPersonal() == id);
        guardarTodos(pilotos);
    }

    public int generarIdUnico() throws IOException {
        int nuevoId;
        do {
            nuevoId = UUID.randomUUID().hashCode() & Integer.MAX_VALUE;
        } while (buscarPorId(nuevoId) != null);
        return nuevoId;
    }
}

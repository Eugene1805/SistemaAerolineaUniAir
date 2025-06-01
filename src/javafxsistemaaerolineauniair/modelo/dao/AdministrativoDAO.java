package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.modelo.pojo.Administrativo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;


/**
 *
 * @author meler
 */
public class AdministrativoDAO extends GenericDAO<Administrativo> {

    private static final String DATA_DIR = "data";
    private static final String FILE_NAME = "administrativos.json";

    public AdministrativoDAO() {
        super(obtenerRutaCompleta(), Administrativo.class);
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
            "Fecha de Nacimiento", "Género", "Salario", "Departamento"
        };
    }

    @Override
    protected String[] obtenerValoresFila(Administrativo administrativo) {
        return new String[]{
            String.valueOf(administrativo.getNoPersonal()),
            administrativo.getNombre(),
            administrativo.getApellidoPaterno(),
            administrativo.getApellidoMaterno(),
            administrativo.getDireccion(),
            administrativo.getFechaNacimiento().toString(),
            administrativo.getGenero(),
            String.valueOf(administrativo.getSalario()),
            administrativo.getDepartamento()
        };
    }

    public Administrativo buscarPorId(int id) throws IOException {
        return obtenerTodos().stream()
            .filter(a -> a.getNoPersonal() == id)
            .findFirst()
            .orElse(null);
    }

    public void actualizar(Administrativo actualizado) throws IOException {
        List<Administrativo> administrativos = obtenerTodos();
        administrativos.replaceAll(a -> a.getNoPersonal() == actualizado.getNoPersonal() ? actualizado : a);
        guardarTodos(administrativos);
    }

    public void eliminar(int id) throws IOException {
        List<Administrativo> administrativos = obtenerTodos();
        administrativos.removeIf(a -> a.getNoPersonal() == id);
        guardarTodos(administrativos);
    }

    public int generarIdUnico() throws IOException {
        int nuevoId;
        do {
            nuevoId = UUID.randomUUID().hashCode() & Integer.MAX_VALUE;
        } while (buscarPorId(nuevoId) != null);
        return nuevoId;
    }
}

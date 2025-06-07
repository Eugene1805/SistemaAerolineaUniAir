package javafxsistemaaerolineauniair.excepciones;

/**
 *
 * @author eugen
 */
public class EmpleadoConVuelosException extends Exception {

    public EmpleadoConVuelosException() {
        super("El empleado no puede ser eliminado porque tiene uno o más vuelos asociados.");
    }

    public EmpleadoConVuelosException(String string) {
        super(string);
    }
    
}

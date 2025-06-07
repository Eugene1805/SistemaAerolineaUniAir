package javafxsistemaaerolineauniair.excepciones;

/**
 *
 * @author eugen
 */
public class AeropuertoConVuelosException extends Exception {
    public AeropuertoConVuelosException(String message) {
        super(message);
    }

    /**
     * Constructor con un mensaje por defecto.
     */
    public AeropuertoConVuelosException() {
        super("El aeropuerto no puede ser eliminado porque tiene uno o más vuelos asociados.");
    }
}

package javafxsistemaaerolineauniair.excepciones;

/**
 *
 * @author eugen
 */
public class VueloLlenoException extends Exception {
     public VueloLlenoException(String message) {
        super(message);
    }
     
    public VueloLlenoException(){
        super("El vuelo a alcanzado su capacidad maxima");
    }
}

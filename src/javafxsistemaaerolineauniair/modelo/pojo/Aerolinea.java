package javafxsistemaaerolineauniair.modelo.pojo;

/**
 *
 * @author eugen
 */
public class Aerolinea {
    private int id;
    private String nombre;
    private String direccion;
    private String personaContacto;
    private String telefono;
    private int flota;

    public Aerolinea() {
    }

    public Aerolinea(int id, String nombre, String direccion, String personaContacto, String telefono, int flota) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.personaContacto = personaContacto;
        this.telefono = telefono;
        this.flota = flota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getFlota() {
        return flota;
    }

    public void setFlota(int flota) {
        this.flota = flota;
    }
}

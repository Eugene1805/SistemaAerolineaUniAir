package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.LocalDate;

/**
 *
 * @author meler
 */
public class AsistenteVuelo extends Empleado {

    private int noHorasAsistencia;
    private int noIdiomas;

    public AsistenteVuelo() {
        super(); 
    }

    public AsistenteVuelo(int noPersonal, String nombre, String direccion, LocalDate fechaNacimiento,
                          String genero, double salario, String apellidoPaterno, String apellidoMaterno,
                          int noHorasAsistencia, int noIdiomas) {
        super(noPersonal, nombre, direccion, fechaNacimiento, genero, salario, apellidoPaterno, apellidoMaterno);
        this.noHorasAsistencia = noHorasAsistencia;
        this.noIdiomas = noIdiomas;
    }

    public int getNoHorasAsistencia() {
        return noHorasAsistencia;
    }

    public void setNoHorasAsistencia(int noHorasAsistencia) {
        this.noHorasAsistencia = noHorasAsistencia;
    }

    public int getNoIdiomas() {
        return noIdiomas;
    }

    public void setNoIdiomas(int noIdiomas) {
        this.noIdiomas = noIdiomas;
    }

    @Override
    public String toString() {
        return getNombre() + " " + getApellidoPaterno();
    }
}

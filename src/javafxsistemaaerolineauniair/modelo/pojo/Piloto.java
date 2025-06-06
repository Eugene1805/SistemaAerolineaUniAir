package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.LocalDate;

/**
 *
 * @author meler
 */
public class Piloto extends Empleado{
 private TipoLicencia tipolicencia;
 private int aniosExperiencia;
 private int totalHoras;

    public Piloto(int noPersonal, String nombre, String direccion, LocalDate fechaNacimiento, String genero, double salario, String apellidoPaterno, String apellidoMaterno) {
        super(noPersonal, nombre, direccion, fechaNacimiento, genero, salario, apellidoPaterno, apellidoMaterno);
    }
    
    public Piloto() {
    super(); // llama al constructor vacío de Empleado
}
    
    public Piloto(int noPersonal, String nombre, String direccion, java.time.LocalDate fechaNacimiento,
                  String genero, double salario, String apellidoPaterno, String apellidoMaterno,
                  TipoLicencia tipolicencia, int aniosExperiencia, int totalHoras) {
        super(noPersonal, nombre, direccion, fechaNacimiento, genero, salario, apellidoPaterno, apellidoMaterno);
        this.tipolicencia = tipolicencia;
        this.aniosExperiencia = aniosExperiencia;
        this.totalHoras = totalHoras;
    }
 
    public TipoLicencia getTipolicencia() {
        return tipolicencia;
    }

    public void setTipolicencia(TipoLicencia tipolicencia) {
        this.tipolicencia = tipolicencia;
    }

    public int getAniosExperiencia() {
        return aniosExperiencia;
    }

    public void setAniosExperiencia(int aniosExperiencia) {
        this.aniosExperiencia = aniosExperiencia;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

@Override
public String toString() {
        return getNombre() + " " + getApellidoPaterno();
}

}

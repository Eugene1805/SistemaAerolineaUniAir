package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.LocalDate;

/**
 *
 * @author meler
 */
public class Administrativo extends Empleado {
    private String departamento;

    public Administrativo() {
    super(); 
}

    public Administrativo(int noPersonal, String nombre, String direccion, LocalDate fechaNacimiento,
                          String genero, double salario, String apellidoPaterno, String apellidoMaterno,
                          String departamento) {
        super(noPersonal, nombre, direccion, fechaNacimiento, genero, salario, apellidoPaterno, apellidoMaterno);
        this.departamento = departamento;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "Administrativo{" +
                "departamento='" + departamento + '\'' +
                '}';
    }
}
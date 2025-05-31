/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.LocalDate;

/**
 *
 * @author meler
 */
public abstract class Empleado {
    private int noPersonal;
    private String nombre;
    private String direccion;
    private LocalDate fechaNacimiento;
    private String genero;
    private double salario;
    private String apellidoPaterno;
    private String apellidoMaterno;

    public Empleado() {
    }
   

    public Empleado(int noPersonal, String nombre, String direccion, LocalDate fechaNacimiento, String genero, double salario, String apellidoPaterno, String apellidoMaterno) {
        this.noPersonal = noPersonal;
        this.nombre = nombre;
        this.direccion = direccion;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.salario = salario;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }
    
    

    public int getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(int noPersonal) {
        this.noPersonal = noPersonal;
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

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
    
    
}

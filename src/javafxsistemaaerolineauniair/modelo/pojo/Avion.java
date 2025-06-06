package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.LocalDate;

/**
 *
 * @author eugen
 */
public class Avion {
    private int idAvion;
    private int capacidad;
    private String modelo;
    private float peso;
    private String estatus; 
    private LocalDate fechaDeIngreso;
    private int idAerolinea;
    private int asiento;

    public Avion() {
    }

    public Avion(int idAvion, int capacidad, String modelo, float peso, String estatus, LocalDate fechaDeIngreso, int idAerolinea,int asiento) {
        this.idAvion = idAvion;
        this.capacidad = capacidad;
        this.modelo = modelo;
        this.peso = peso;
        this.estatus = estatus;
        this.fechaDeIngreso = fechaDeIngreso;
        this.idAerolinea = idAerolinea;
        this.asiento = asiento;
        
    }



    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public LocalDate getFechaDeIngreso() {
        return fechaDeIngreso;
    }

    public void setFechaDeIngreso(LocalDate fechaDeIngreso) {
        this.fechaDeIngreso = fechaDeIngreso;
    }

    public int getIdAerolinea() {
        return idAerolinea;
    }

    public void setIdAerolinea(int idAerolinea) {
        this.idAerolinea = idAerolinea;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAsiento() {
        return asiento;
    }

    public void setAsiento(int asiento) {
        this.asiento = asiento;
    }

    @Override
    public String toString() {
        return modelo + " (ID: " + idAvion + ")";
    }
    
    
    
}

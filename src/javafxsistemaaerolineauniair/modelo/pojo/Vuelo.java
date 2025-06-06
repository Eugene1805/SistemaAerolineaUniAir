package javafxsistemaaerolineauniair.modelo.pojo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eugen
 */
public class Vuelo {
    private int idVuelo;
    private int numPasajeros;
    private int tiempoRecorrido;
    private double costoBoleto;
    private String ciudadSalida;
    private String ciudadLlegada;
    private LocalDate fechaSalida;
    private LocalTime horaSalida;
    private LocalDate fechaLlegada;
    private LocalTime horaLlegada;
    
    private int idAvion;
    
    private List<Integer> pilotos;
    private List<Integer> asistentes;
    
    private List<String> nombresPilotos;
    private List<String> nombresAsistentes;

    public Vuelo() {
        this.pilotos = new ArrayList<>();
        this.asistentes = new ArrayList<>();
        this.nombresPilotos = new ArrayList<>();
        this.nombresAsistentes = new ArrayList<>();
    }

    public Vuelo(int idVuelo, int numPasajeros, int tiempoRecorrido, double costoBoleto, String ciudadSalida, String ciudadLlegada, LocalDate fechaSalida, LocalTime horaSalida, LocalDate fechaLlegada, LocalTime horaLlegada, int idAvion, List<Integer> pilotos, List<Integer> asistentes, List<String> nombresPilotos, List<String> nombresAsistentes) {
        this.idVuelo = idVuelo;
        this.numPasajeros = numPasajeros;
        this.tiempoRecorrido = tiempoRecorrido;
        this.costoBoleto = costoBoleto;
        this.ciudadSalida = ciudadSalida;
        this.ciudadLlegada = ciudadLlegada;
        this.fechaSalida = fechaSalida;
        this.horaSalida = horaSalida;
        this.fechaLlegada = fechaLlegada;
        this.horaLlegada = horaLlegada;
        this.idAvion = idAvion;
        this.pilotos = new ArrayList<>(pilotos);
        this.asistentes = new ArrayList<>(asistentes);
        this.nombresPilotos = new ArrayList<>(nombresPilotos);
        this.nombresAsistentes = new ArrayList<>(nombresAsistentes);
    }

 

    
    
    public void calcularTiempoRecorrido(){
        LocalDateTime salida = LocalDateTime.of(fechaSalida, horaSalida);
        LocalDateTime llegada = LocalDateTime.of(fechaLlegada, horaLlegada);
        Duration duracion = Duration.between(salida, llegada);
        this.tiempoRecorrido = (int) duracion.toHours();
    }
    
    //Getters y setters
    public int getIdVuelo() {
        return idVuelo;
    }

    public void setIdVuelo(int idVuelo) {
        this.idVuelo = idVuelo;
    }

    public int getNumPasajeros() {
        return numPasajeros;
    }

    public void setNumPasajeros(int numPasajeros) {
        this.numPasajeros = numPasajeros;
    }

    public int getTiempoRecorrido() {
        return tiempoRecorrido;
    }

    public void setTiempoRecorrido(int tiempoRecorrido) {
        this.tiempoRecorrido = tiempoRecorrido;
    }

    public double getCostoBoleto() {
        return costoBoleto;
    }

    public void setCostoBoleto(double costoBoleto) {
        this.costoBoleto = costoBoleto;
    }

    public String getCiudadSalida() {
        return ciudadSalida;
    }

    public void setCiudadSalida(String ciudadSalida) {
        this.ciudadSalida = ciudadSalida;
    }

    public String getCiudadLlegada() {
        return ciudadLlegada;
    }

    public void setCiudadLlegada(String ciudadLlegada) {
        this.ciudadLlegada = ciudadLlegada;
    }

    public LocalDate getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDate fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public LocalDate getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(LocalDate fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public LocalTime getHoraLlegada() {
        return horaLlegada;
    }

    public void setHoraLlegada(LocalTime horaLlegada) {
        this.horaLlegada = horaLlegada;
    }

    public int getIdAvion() {
        return idAvion;
    }

    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }

    public List<Integer> getPilotos() {
        return pilotos;
    }

    public void setPilotos(List<Integer> pilotos) {
        this.pilotos = pilotos;
    }

    public List<Integer> getAsistentes() {
        return asistentes;
    }

    public void setAsistentes(List<Integer> asistentes) {
        this.asistentes = asistentes;
    }

    public List<String> getNombresPilotos() {
        return nombresPilotos;
    }

    public void setNombresPilotos(List<String> nombresPilotos) {
        this.nombresPilotos = nombresPilotos;
    }

    public List<String> getNombresAsistentes() {
        return nombresAsistentes;
    }

    public void setNombresAsistentes(List<String> nombresAsistentes) {
        this.nombresAsistentes = nombresAsistentes;
    }
    
    
}
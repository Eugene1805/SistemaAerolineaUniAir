package javafxsistemaaerolineauniair.modelo.pojo;

/**
 *
 * @author meler
 */
public enum TipoLicencia {
    DEPORTIVO("Licencia Deportiva"),
    COMERCIAL("Licencia Comercial"),
    DELINEAAEREA("Licencia de Línea Aérea");

    private final String descripcion;

    TipoLicencia(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

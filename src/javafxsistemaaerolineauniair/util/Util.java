package javafxsistemaaerolineauniair.util;

import javafx.scene.control.Alert;

/**
 *
 * @author eugen
 */
public class Util {
    public static void mostrarAlertaSimple(Alert.AlertType tipo, String titulo, String contenido){
        Alert alerta = new Alert(tipo);
                alerta.setTitle(titulo);
                alerta.setHeaderText(null);
                alerta.setContentText(contenido);
                alerta.showAndWait();
    }
}

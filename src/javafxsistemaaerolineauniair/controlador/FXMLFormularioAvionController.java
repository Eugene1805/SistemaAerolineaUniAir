package javafxsistemaaerolineauniair.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.dao.AeropuertoDAO;
import javafxsistemaaerolineauniair.modelo.dao.AvionDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author migue
 */
public class FXMLFormularioAvionController implements Initializable {

    @FXML
    private TextField tfCapacidad;
    @FXML
    private TextField tfModelo;
    @FXML
    private TextField tfPeso;
    @FXML
    private TextField tfEstatus;
    @FXML
    private ComboBox<Aeropuerto> cbAeropuerto;
    @FXML
    private DatePicker dpFechaDeIngreso;
    @FXML
    private TextField tfAsientos;
    
    private Avion avion;
    private AvionDAO avionDAO;
    private boolean confirmado = false;



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            List<Aeropuerto> aeropuertos = new AeropuertoDAO().obtenerTodos();
            cbAeropuerto.getItems().addAll(aeropuertos);
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No se pudo cargar la lista de aeropuertos.");
        }
    }   

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (tfCapacidad.getText().isEmpty() || tfModelo.getText().isEmpty() || tfPeso.getText().isEmpty() ||
            tfEstatus.getText().isEmpty() || dpFechaDeIngreso.getValue() == null || cbAeropuerto.getValue() == null || tfAsientos.getText().isEmpty()) {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos requeridos",
                    "Todos los campos son obligatorios.");
            return;
        }

        try {
            avion.setCapacidad(Integer.parseInt(tfCapacidad.getText()));
            avion.setModelo(tfModelo.getText());
            avion.setPeso(Float.parseFloat(tfPeso.getText()));
            avion.setEstatus(tfEstatus.getText());
            avion.setFechaDeIngreso(dpFechaDeIngreso.getValue());   
            avion.setIdAerolinea(cbAeropuerto.getValue().getId());
            avion.setAsiento(Integer.parseInt(tfAsientos.getText()));

            confirmado = true;
            ((Stage) tfCapacidad.getScene().getWindow()).close();
        } catch (Exception e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de entrada",
                    "Verifica los campos numéricos y la fecha (AAAA-MM-DD).");
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        confirmado = false;
        ((Stage) tfCapacidad.getScene().getWindow()).close();
    }

    public void inicializarInformacion(Avion avion, AvionDAO avionDAO) {
        this.avion = avion;
        this.avionDAO = avionDAO;

        if (avion.getFechaDeIngreso()!= null) {
            tfCapacidad.setText(String.valueOf(avion.getCapacidad()));
            tfModelo.setText(avion.getModelo());
            tfPeso.setText(String.valueOf(avion.getPeso()));
            tfEstatus.setText(avion.getEstatus());
            dpFechaDeIngreso.setValue(avion.getFechaDeIngreso());

            cbAeropuerto.getSelectionModel().select(
                cbAeropuerto.getItems().stream()
                    .filter(a -> a.getId() == avion.getIdAerolinea())
                    .findFirst()
                    .orElse(null)
            );
            
            tfAsientos.setText(String.valueOf(this.avion.getAsiento()));
        }
    }
    
    public boolean isConfirmado() {
        return confirmado;
    }
    
}
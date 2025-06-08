package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
import javafxsistemaaerolineauniair.modelo.dao.AeropuertoDAO;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLFormularioAeropuertoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfPersonaContacto;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfFlota;

    
    private Aeropuerto aeropuerto;
    private AeropuertoDAO aeropuertoDAO;
    private boolean confirmado = false;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAceptar(ActionEvent event) {
        if(validarCampos()){
            // Actualizar objeto aeropuerto con los valores del formulario
            aeropuerto.setNombre(tfNombre.getText());
            aeropuerto.setDireccion(tfDireccion.getText());
            aeropuerto.setPersonaContacto(tfPersonaContacto.getText());
            aeropuerto.setTelefono(tfTelefono.getText());
            aeropuerto.setFlota(Integer.parseInt(tfFlota.getText()));

            confirmado = true;
            ((Stage) tfNombre.getScene().getWindow()).close();  
        }
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        confirmado = false;
        ((Stage) tfNombre.getScene().getWindow()).close();  
    }

    void inicializarInformacion(Aeropuerto aeropuerto, AeropuertoDAO aeropuertoDAO) {
        this.aeropuerto = aeropuerto;
        this.aeropuertoDAO = aeropuertoDAO;
        
        // Cargar datos del aeropuerto en los campos
        if (aeropuerto.getNombre()!= null) { // Si es edición
            tfNombre.setText(aeropuerto.getNombre());
            tfDireccion.setText(aeropuerto.getDireccion());
            tfPersonaContacto.setText(aeropuerto.getPersonaContacto());
            tfTelefono.setText(aeropuerto.getTelefono());
            tfFlota.setText(String.valueOf(aeropuerto.getFlota()));
        }   
    }

    boolean isConfirmado() {
        return confirmado;
    }
    
    private boolean validarCampos(){
        if (tfNombre.getText().isEmpty() || tfDireccion.getText().isEmpty()
                || tfPersonaContacto.getText().isEmpty() 
                || tfTelefono.getText().isEmpty() || tfFlota.getText().isEmpty()) {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos faltantes", 
                    "No puede haber ningún campo vacío.");
            return false;
        }
        
        String regexLetras = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
        if (!tfNombre.getText().matches(regexLetras)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Nombre inválido",
                "ERROR: Por favor ingrese un nombre válido."
            );
            return false;
        }
        if (!tfPersonaContacto.getText().matches(regexLetras)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Persona de Contacto inválida",
                "ERROR: Por favor ingrese una persona de contacto válida."
            );
            return false;
        }
        if (!tfTelefono.getText().matches("\\d+")) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Númerp de teléfono inválido",
                "ERROR: Por favor ingrese un núero de teléfono válido"
            );
            return false;
        }
        if (!tfFlota.getText().matches("\\d+")) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Tamaño de Flota inválida",
                "ERROR: El tamaño de flota debe ser un número entero positivo."
            );
            return false;
        }
        
        return true;
    }
}

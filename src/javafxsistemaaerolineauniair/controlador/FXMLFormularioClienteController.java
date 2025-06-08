package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.dao.ClienteDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Cliente;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLFormularioClienteController implements Initializable {

    
    private boolean confirmado = false;
    private Cliente cliente;
    private ClienteDAO clienteDAO;
    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfApellidoPaterno;
    @FXML
    private TextField tfApellidoMaterno;
    @FXML
    private TextField tfNacionalidad;
    @FXML
    private DatePicker dpFechaNacimiento;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    boolean isConfirmado() {
        return confirmado;
    }

    void inicializarInformacion(Cliente cliente, ClienteDAO clienteDAO) {
        this.cliente = cliente;
        this.clienteDAO = clienteDAO; 
    }

    @FXML
    private void onAceptar(ActionEvent event) {
        if (validarCampos()){
        
            // Actualizar objeto aeropuerto con los valores del formulario
            cliente.setNombre(tfNombre.getText());
            cliente.setApellidoPaterno(tfApellidoPaterno.getText());
            cliente.setApellidoMaterno(tfApellidoMaterno.getText());
            cliente.setNacionalidad(tfNacionalidad.getText());

            LocalDate fecha = dpFechaNacimiento.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String fechaFormateada = fecha.format(formatter);
            cliente.setFechaNacimiento(fechaFormateada);

            confirmado = true;
            ((Stage) tfNombre.getScene().getWindow()).close();  
        }    
    }

    @FXML
    private void onCancelar(ActionEvent event) {
        confirmado = false;
        ((Stage) tfNombre.getScene().getWindow()).close(); 
    }
    
    private boolean validarCampos(){
        if(tfNombre.getText().isEmpty() || tfApellidoPaterno.getText().isEmpty() 
           || tfApellidoMaterno.getText().isEmpty()
           || tfNacionalidad.getText().isEmpty() || dpFechaNacimiento.getValue() == null){
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
        if (!tfNacionalidad.getText().matches(regexLetras)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Nacionalidad inválida",
                "ERROR: Por favor ingrese una nacionalidad válida."
            );
            return false;
        }
        if (!tfApellidoPaterno.getText().matches(regexLetras)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Apellido Paterno inválido",
                "ERROR: Por favor ingrese un apellido paterno válido."
            );
            return false;
        }
        if (!tfApellidoMaterno.getText().matches(regexLetras)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Apellido Materno inválido",
                "ERROR: Por favor ingrese un apellido materno válido."
            );
            return false;
        }
        LocalDate fechaSeleccionada = dpFechaNacimiento.getValue();
        LocalDate fechaActual = LocalDate.now();
        if (fechaSeleccionada.isAfter(fechaActual)) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Fecha inválida",
                    "ERROR: La fecha de nacimiento no puede ser posterior a la fecha actual.");
            return false;
        }
        return true;
    }
}

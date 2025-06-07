package javafxsistemaaerolineauniair.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.dao.AdministrativoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Administrativo;
import javafxsistemaaerolineauniair.util.Util;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author meler
 */
public class FXMLFormularioAdministrativoController implements Initializable {

    @FXML 
    public TextField tfNombre;
    @FXML 
    public TextField tfPaterno;
    @FXML 
    public TextField tfMaterno;
    @FXML 
    public TextField tfDIreccion;
    @FXML 
    public DatePicker dpFechaNacimiento;
    @FXML 
    public TextField tfGenero;
    @FXML 
    public TextField tfSalario;
    @FXML 
    public TextField tfDepartamento;

    private boolean confirmado = false;
    private Administrativo administrativo;
    private AdministrativoDAO administrativoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada por ahora
    }

    public void inicializarInformacion(Administrativo administrativo, AdministrativoDAO dao) {
        this.administrativo = administrativo;
        this.administrativoDAO = dao;

        if (administrativo.getNombre() != null) {
            tfNombre.setText(administrativo.getNombre());
            tfPaterno.setText(administrativo.getApellidoPaterno());
            tfMaterno.setText(administrativo.getApellidoMaterno());
            tfDIreccion.setText(administrativo.getDireccion());
            dpFechaNacimiento.setValue(administrativo.getFechaNacimiento());
            tfGenero.setText(administrativo.getGenero());
            tfSalario.setText(String.valueOf(administrativo.getSalario()));
            tfDepartamento.setText(administrativo.getDepartamento());
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (validarCampos()) {
            try {
                administrativo.setNombre(tfNombre.getText().trim());
                administrativo.setApellidoPaterno(tfPaterno.getText().trim());
                administrativo.setApellidoMaterno(tfMaterno.getText().trim());
                administrativo.setDireccion(tfDIreccion.getText().trim());
                administrativo.setFechaNacimiento(dpFechaNacimiento.getValue());
                administrativo.setGenero(tfGenero.getText().trim());
                administrativo.setSalario(Double.parseDouble(tfSalario.getText().trim()));
                administrativo.setDepartamento(tfDepartamento.getText().trim());

                confirmado = true;
                cerrarVentana();
            } catch (NumberFormatException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de formato",
                        "Revisa el valor numérico del salario.");
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        confirmado = false;
        cerrarVentana();
    }

    private boolean validarCampos() {
        if (tfNombre.getText().isEmpty() || tfPaterno.getText().isEmpty() || tfMaterno.getText().isEmpty()
                || tfDIreccion.getText().isEmpty() || dpFechaNacimiento.getValue() == null
                || tfGenero.getText().isEmpty() || tfSalario.getText().isEmpty()
                || tfDepartamento.getText().isEmpty()) {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos vacíos",
                    "Todos los campos son obligatorios.");
            return false;
        }
        return true;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}

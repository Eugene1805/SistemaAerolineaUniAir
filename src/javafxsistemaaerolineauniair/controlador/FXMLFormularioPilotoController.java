package javafxsistemaaerolineauniair.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.dao.PilotoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Piloto;
import javafxsistemaaerolineauniair.modelo.pojo.TipoLicencia;
import javafxsistemaaerolineauniair.util.Util;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML Controller classvents
 *
 * @author meler
 */
public class FXMLFormularioPilotoController implements Initializable {

    @FXML private TextField tfNombre;
    @FXML private TextField tfPaterno;
    @FXML private TextField tfMaterno;
    @FXML private TextField tfDIreccion;
    @FXML private DatePicker dpFechaNacimiento;
    @FXML private TextField tfGenero;
    @FXML private TextField tfSalario;
    @FXML private ComboBox<TipoLicencia> cbLicencia;
    @FXML private TextField tfExperiencia;
    @FXML private TextField tfHorasVuelo;

    private boolean confirmado = false;
    private Piloto piloto;
    private PilotoDAO pilotoDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbLicencia.getItems().setAll(TipoLicencia.values());
    }

    public void inicializarInformacion(Piloto piloto, PilotoDAO dao) {
        this.piloto = piloto;
        this.pilotoDAO = dao;

        if (piloto.getNombre() != null) {
            tfNombre.setText(piloto.getNombre());
            tfPaterno.setText(piloto.getApellidoPaterno());
            tfMaterno.setText(piloto.getApellidoMaterno());
            tfDIreccion.setText(piloto.getDireccion());
            dpFechaNacimiento.setValue(piloto.getFechaNacimiento());
            tfGenero.setText(piloto.getGenero());
            tfSalario.setText(String.valueOf(piloto.getSalario()));
            cbLicencia.setValue(piloto.getTipolicencia());
            tfExperiencia.setText(String.valueOf(piloto.getAniosExperiencia()));
            tfHorasVuelo.setText(String.valueOf(piloto.getTotalHoras()));
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (validarCampos()) {
            try {
                piloto.setNombre(tfNombre.getText().trim());
                piloto.setApellidoPaterno(tfPaterno.getText().trim());
                piloto.setApellidoMaterno(tfMaterno.getText().trim());
                piloto.setDireccion(tfDIreccion.getText().trim());
                piloto.setFechaNacimiento(dpFechaNacimiento.getValue());
                piloto.setGenero(tfGenero.getText().trim());
                piloto.setSalario(Double.parseDouble(tfSalario.getText().trim()));
                piloto.setTipolicencia(cbLicencia.getValue());
                piloto.setAniosExperiencia(Integer.parseInt(tfExperiencia.getText().trim()));
                piloto.setTotalHoras(Integer.parseInt(tfHorasVuelo.getText().trim()));

                confirmado = true;
                cerrarVentana();
            } catch (NumberFormatException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de formato",
                        "Revisa los valores numéricos: salario, años de experiencia, horas.");
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
                || cbLicencia.getValue() == null || tfExperiencia.getText().isEmpty()
                || tfHorasVuelo.getText().isEmpty()) {
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

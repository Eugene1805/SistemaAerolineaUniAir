package javafxsistemaaerolineauniair.controlador;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.dao.AsistenteVueloDAO;
import javafxsistemaaerolineauniair.modelo.pojo.AsistenteVuelo;
import javafxsistemaaerolineauniair.util.Util;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class FXMLFormularioAsistenteController implements Initializable {

    @FXML public TextField tfNombre;
    @FXML public TextField tfPaterno;
    @FXML public TextField tfMaterno;
    @FXML public TextField tfDIreccion;
    @FXML public DatePicker dpFechaNacimiento;
    @FXML public TextField tfGenero;
    @FXML public TextField tfSalario;
    @FXML public TextField tfHorasAsistencia;
    @FXML public TextField tfNoIdiomas;

    private boolean confirmado = false;
    private AsistenteVuelo asistente;
    private AsistenteVueloDAO asistenteDAO;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Nada específico aún
    }

    public void inicializarInformacion(AsistenteVuelo asistente, AsistenteVueloDAO dao) {
        this.asistente = asistente;
        this.asistenteDAO = dao;

        if (asistente.getNombre() != null) {
            tfNombre.setText(asistente.getNombre());
            tfPaterno.setText(asistente.getApellidoPaterno());
            tfMaterno.setText(asistente.getApellidoMaterno());
            tfDIreccion.setText(asistente.getDireccion());
            dpFechaNacimiento.setValue(asistente.getFechaNacimiento());
            tfGenero.setText(asistente.getGenero());
            tfSalario.setText(String.valueOf(asistente.getSalario()));
            tfHorasAsistencia.setText(String.valueOf(asistente.getNoHorasAsistencia()));
            tfNoIdiomas.setText(String.valueOf(asistente.getNoIdiomas()));
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (validarCampos()) {
            try {
                asistente.setNombre(tfNombre.getText().trim());
                asistente.setApellidoPaterno(tfPaterno.getText().trim());
                asistente.setApellidoMaterno(tfMaterno.getText().trim());
                asistente.setDireccion(tfDIreccion.getText().trim());
                asistente.setFechaNacimiento(dpFechaNacimiento.getValue());
                asistente.setGenero(tfGenero.getText().trim());
                asistente.setSalario(Double.parseDouble(tfSalario.getText().trim()));
                asistente.setNoHorasAsistencia(Integer.parseInt(tfHorasAsistencia.getText().trim()));
                asistente.setNoIdiomas(Integer.parseInt(tfNoIdiomas.getText().trim()));

                confirmado = true;
                cerrarVentana();
            } catch (NumberFormatException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de formato",
                        "Revisa los valores numéricos: salario, horas de asistencia, número de idiomas.");
            }
        }
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
        confirmado = false;
        cerrarVentana();
    }

    public boolean validarCampos() {
        if (tfNombre.getText().isEmpty() || tfPaterno.getText().isEmpty() || tfMaterno.getText().isEmpty()
                || tfDIreccion.getText().isEmpty() || dpFechaNacimiento.getValue() == null
                || tfGenero.getText().isEmpty() || tfSalario.getText().isEmpty()
                || tfHorasAsistencia.getText().isEmpty() || tfNoIdiomas.getText().isEmpty()) {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos vacíos",
                    "Todos los campos son obligatorios.");
            return false;
        }
        
        LocalDate fechaSeleccionada = dpFechaNacimiento.getValue();
        LocalDate fechaActual = LocalDate.now();
        if (fechaSeleccionada.isAfter(fechaActual)) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Fecha inválida",
                    "ERROR: La fecha de nacimiento no puede ser posterior a la fecha actual.");
            return false;
        } 
        if (!validarCadenas(tfNombre, "Nombre")) return false;
        if (!validarCadenas(tfPaterno, "Paterno")) return false;
        if (!validarCadenas(tfMaterno, "Materno")) return false;
        if (!validarCadenas(tfGenero, "Genero")) return false;
        
        return true;
    }
    
    private boolean validarCadenas(TextField campo, String nombreCampo){
        String regex = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
        if (!campo.getText().matches(regex)) {
        Util.mostrarAlertaSimple(
            Alert.AlertType.ERROR,
            nombreCampo + " inválido",
            "ERROR: Por favor ingrese un " + nombreCampo.toLowerCase() + " válido."
        );
        return false;
    }
        return true;
    }

    private void cerrarVentana() {
        Stage stage = (Stage) tfNombre.getScene().getWindow();
        stage.close();
    }
}

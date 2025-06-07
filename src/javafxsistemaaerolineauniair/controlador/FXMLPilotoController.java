package javafxsistemaaerolineauniair.controlador;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafxsistemaaerolineauniair.excepciones.EmpleadoConVuelosException;
import javafxsistemaaerolineauniair.modelo.dao.PilotoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Piloto;
import javafxsistemaaerolineauniair.util.Util;


/**
 * FXML Controller class
 *
 * @author meler
 */
public class FXMLPilotoController implements Initializable {

    @FXML private TableView<Piloto> tvPilotos;
    @FXML private TableColumn<Piloto, Integer> tcNoPersonal;
    @FXML private TableColumn<Piloto, String> tcNombre;
    @FXML private TableColumn<Piloto, String> tcPaterno;
    @FXML private TableColumn<Piloto, String> tcMaterno;
    @FXML private TableColumn<Piloto, String> tcDireccion;
    @FXML private TableColumn<Piloto, String> tcFechaNacimiento;
    @FXML private TableColumn<Piloto, String> tcGenero;
    @FXML private TableColumn<Piloto, Double> tcSalario;
    @FXML private TableColumn<Piloto, String> tcTipoLicencia;
    @FXML private TableColumn<Piloto, Integer> tcAñosExperiencia;
    @FXML private TableColumn<Piloto, Integer> tcTotalHoras;
    @FXML private Button btnAlta;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private MenuItem btnExportarCsv;
    @FXML private MenuItem btnExportarXLS;
    @FXML private MenuItem btnExportarXLSX;
    @FXML private MenuItem btnExportarPDF;
    @FXML private Button btnRegresar;

    private final ObservableList<Piloto> pilotos = FXCollections.observableArrayList();
    private final PilotoDAO pilotoDAO = new PilotoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();

        btnExportarCsv.disableProperty().bind(Bindings.isEmpty(tvPilotos.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvPilotos.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvPilotos.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvPilotos.getItems()));

        btnActualizar.disableProperty().bind(tvPilotos.getSelectionModel().selectedItemProperty().isNull());
        btnEliminar.disableProperty().bind(tvPilotos.getSelectionModel().selectedItemProperty().isNull());
    }

    private void configurarTabla() {
        tcNoPersonal.setCellValueFactory(new PropertyValueFactory<>("noPersonal"));
        tcNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tcPaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoPaterno"));
        tcMaterno.setCellValueFactory(new PropertyValueFactory<>("apellidoMaterno"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        tcFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
        tcGenero.setCellValueFactory(new PropertyValueFactory<>("genero"));
        tcSalario.setCellValueFactory(new PropertyValueFactory<>("salario"));
        tcTipoLicencia.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
            cellData.getValue().getTipolicencia().name()));
        tcAñosExperiencia.setCellValueFactory(new PropertyValueFactory<>("aniosExperiencia"));
        tcTotalHoras.setCellValueFactory(new PropertyValueFactory<>("totalHoras"));
    }

    private void cargarInformacion() {
        try {
            pilotos.setAll(pilotoDAO.obtenerTodos());
            tvPilotos.setItems(pilotos);
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar la información", ex.getMessage());
        }
    }

    @FXML
    private void btnAlta(ActionEvent event) {
        try {
            Piloto nuevo = new Piloto();
            nuevo.setNoPersonal(pilotoDAO.generarIdUnico());

            if (irFormularioPiloto(nuevo)) {
                pilotoDAO.agregar(nuevo);
                cargarInformacion();
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Piloto registrado correctamente.");
            }
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al generar ID", e.getMessage());
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        Piloto seleccionado = tvPilotos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Piloto copia = clonarPiloto(seleccionado);
            if (irFormularioPiloto(copia)) {
                try {
                    pilotoDAO.actualizar(copia);
                    cargarInformacion();
                } catch (IOException e) {
                    Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al actualizar", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        Piloto seleccionado = tvPilotos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
             // Esta alerta no es necesaria porque el botón ya está deshabilitado, pero es una buena práctica dejarla.
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Sin selección", "Por favor, seleccione un piloto a eliminar.");
            return;
        }

        // Pedir confirmación al usuario
        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Desea eliminar al piloto: " + seleccionado.getNombre()+ "?");
        confirmacion.setContentText("Esta acción es irreversible.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                // Llamamos al nuevo método que ya hace la validación
                pilotoDAO.verificarYEliminar(seleccionado.getNoPersonal());
                
                // Si llegamos aquí, fue exitoso
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "El piloto ha sido eliminado correctamente.");
                cargarInformacion(); // Actualizar la tabla

            } catch (EmpleadoConVuelosException e) {
                // Capturamos la excepción de negocio y mostramos el mensaje
                Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Operación Denegada", e.getMessage());
            } catch (IOException e) {
                // Capturamos errores de archivo
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Archivo", "No se pudo completar la eliminación: " + e.getMessage());
            }
        }
    }

    @FXML
    private void onExportarCSV(ActionEvent event) {
        exportarArchivo("CSV", "*.csv");
    }

    @FXML
    private void onExportarXLS(ActionEvent event) {
        exportarArchivo("Excel 97-2003", "*.xls");
    }

    @FXML
    private void onExportarXLSX(ActionEvent event) {
        exportarArchivo("Excel Workbook", "*.xlsx");
    }

    @FXML
    private void onExportarPDF(ActionEvent event) {
        exportarArchivo("PDF", "*.pdf");
    }

    private void exportarArchivo(String descripcion, String extensionFiltro) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo exportado");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extensionFiltro));

        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            try {
                pilotoDAO.exportarAArchivo(archivoSeleccionado);
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Exportación completada.");
            } catch (IOException | DocumentException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al exportar el archivo.");
            }
        }
    }

    @FXML
    private void onRegresar(ActionEvent event) {
        try {
            Stage escenarioBase = (Stage) tvPilotos.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            escenarioBase.setScene(new Scene(vista));
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLPilotoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean irFormularioPiloto(Piloto piloto) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLFormularioPiloto.fxml"));
            Parent vista = loader.load();

            FXMLFormularioPilotoController controlador = loader.getController();
            controlador.inicializarInformacion(piloto, pilotoDAO);

            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario Piloto");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();

            return controlador.isConfirmado();
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al abrir formulario", e.getMessage());
            return false;
        }
    }

    private Piloto clonarPiloto(Piloto original) {
        return new Piloto(
            original.getNoPersonal(),
            original.getNombre(),
            original.getDireccion(),
            original.getFechaNacimiento(),
            original.getGenero(),
            original.getSalario(),
            original.getApellidoPaterno(),
            original.getApellidoMaterno(),
            original.getTipolicencia(),
            original.getAniosExperiencia(),
            original.getTotalHoras()
        );
    }
}
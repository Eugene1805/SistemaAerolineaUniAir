package javafxsistemaaerolineauniair.controlador;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafxsistemaaerolineauniair.modelo.dao.AvionDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.util.Util;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;


public class FXMLAvionController implements Initializable {

    @FXML
    private TableView<Avion> tvAvion;
    @FXML
    private TableColumn<Avion, Integer> tcCapacidad;
    @FXML
    private TableColumn<Avion, String> tcModelo;
    @FXML
    private TableColumn<Avion, Float> tcPeso;
    @FXML
    private TableColumn<Avion, String> tcEstatus;
    @FXML
    private TableColumn<Avion, String> tcFechaIngreso;
    @FXML
    private TableColumn<Avion, Integer> tcAerolinea;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private MenuItem btnExportarCsv;
    @FXML
    private MenuItem btnExportarXLS;
    @FXML
    private MenuItem btnExportarXLSX;
    @FXML
    private MenuItem btnExportarPDF;

    private final ObservableList<Avion> aviones = FXCollections.observableArrayList();
    private final AvionDAO avionDAO = new AvionDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();

        btnExportarCsv.disableProperty().bind(Bindings.isEmpty(tvAvion.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvAvion.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvAvion.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvAvion.getItems()));

        btnActualizar.disableProperty().bind(tvAvion.getSelectionModel().selectedItemProperty().isNull());
        btnEliminar.disableProperty().bind(tvAvion.getSelectionModel().selectedItemProperty().isNull());
    }  

    @FXML
    private void btnAlta(ActionEvent event) {
        try {
            Avion nuevo = new Avion();
            nuevo.setIdAvion(avionDAO.generarIdUnico());

            if (irFormularioAvion(nuevo)) {
                avionDAO.agregar(nuevo);
                cargarInformacion();
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Avión registrado correctamente.");
            }
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al generar un ID único para el avión.");
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        Avion seleccionado = tvAvion.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Avion copia = clonarAvion(seleccionado);
            if (irFormularioAvion(copia)) {
                try {
                    avionDAO.actualizar(copia);
                    cargarInformacion();
                } catch (IOException e) {
                    Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al actualizar", e.getMessage());
                }
            }
        } else {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Seleccione un avión para actualizar",
                    "Debe seleccionar un avión para usar la opción de actualizar.");
        }
    }
    
    @FXML
    private void onEliminar(ActionEvent event) {
        Avion seleccionado = tvAvion.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                avionDAO.eliminar(seleccionado.getIdAvion());
                cargarInformacion();
            } catch (IOException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al eliminar", e.getMessage());
            }
        } else {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Seleccione un avión para eliminar",
                    "Debe seleccionar un avión para usar la opción de eliminar.");
        }
    }

    @FXML
    private void onExportarCSV(ActionEvent event) {
        exportarArchivoConExtension("CSV files (*.csv)", "*.csv");
    }

    @FXML
    private void onExportarXLS(ActionEvent event) {
        exportarArchivoConExtension("Excel 97-2003 (*.xls)", "*.xls");
    }

    @FXML
    private void onExportarXLSX(ActionEvent event) {
        exportarArchivoConExtension("Excel Workbook (*.xlsx)", "*.xlsx");
    }

    @FXML
    private void onExportarPDF(ActionEvent event) {
        exportarArchivoConExtension("PDF files (*.pdf)", "*.pdf");
    }

    @FXML
    private void onRegresar(ActionEvent event) {
        try {
            Stage escenarioBase = (Stage) tvAvion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAvionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void exportarArchivoConExtension(String descripcion, String extensionFiltro) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo exportado");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extensionFiltro));

        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            try {
                avionDAO.exportarAArchivo(archivoSeleccionado);
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito",
                        "Exportación completada correctamente.");
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al exportar el archivo.");
            }
        }
    }
    
    private void configurarTabla() {
        tcCapacidad.setCellValueFactory(new PropertyValueFactory<>("capacidad"));
        tcModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tcPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        tcEstatus.setCellValueFactory(new PropertyValueFactory<>("estatus"));
        tcFechaIngreso.setCellValueFactory(new PropertyValueFactory<>("fechaDeIngreso"));
        tcAerolinea.setCellValueFactory(new PropertyValueFactory<>("idAerolinea"));
    }
    
    private void cargarInformacion() {
        try {
            aviones.setAll(avionDAO.obtenerTodos());
            tvAvion.setItems(aviones);
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar la información", ex.getMessage());
        }
    }
    
    private boolean irFormularioAvion(Avion avion) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLFormularioAvion.fxml"));
            Parent vista = loader.load(); // 👈 importante: primero se carga

            FXMLFormularioAvionController controlador = loader.getController();
            if (controlador != null) {
                controlador.inicializarInformacion(avion, avionDAO);
            } else {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error crítico", "No se pudo obtener el controlador del formulario.");
                return false;
            }

            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle(avion.getIdAvion() != 0 ? "Nuevo Avión" : "Editar Avión");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();

            return controlador.isConfirmado();

        } catch (IOException e) {
            e.printStackTrace();
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar el formulario", e.getMessage());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error inesperado", "No se pudo abrir el formulario.");
            return false;
        }
    }

    
    private Avion clonarAvion(Avion original) {
        return new Avion(
            original.getIdAvion(),
            original.getCapacidad(),
            original.getModelo(),
            original.getPeso(),
            original.getEstatus(),
            original.getFechaDeIngreso(),
            original.getIdAerolinea()
        );
    }
}
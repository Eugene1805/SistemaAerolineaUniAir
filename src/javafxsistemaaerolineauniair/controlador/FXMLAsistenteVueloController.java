package javafxsistemaaerolineauniair.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.binding.Bindings;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import com.itextpdf.text.DocumentException;
import java.io.File;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

import javafxsistemaaerolineauniair.modelo.dao.AsistenteVueloDAO;
import javafxsistemaaerolineauniair.modelo.pojo.AsistenteVuelo;
import javafxsistemaaerolineauniair.util.Util;


/**
 * FXML Controller class
 *
 * @author meler
 */
public class FXMLAsistenteVueloController implements Initializable {

    @FXML
    private Button btnRegresar;
    @FXML 
    private TableView<AsistenteVuelo> tvAsistentes;
    @FXML 
    private TableColumn<AsistenteVuelo, Integer> tcNoPersonal;
    @FXML 
    private TableColumn<AsistenteVuelo, String> tcNombre;
    @FXML 
    private TableColumn<AsistenteVuelo, String> tcPaterno;
    @FXML 
    private TableColumn<AsistenteVuelo, String> tcMaterno;
    @FXML 
    private TableColumn<AsistenteVuelo, String> tcDireccion;
    @FXML 
    private TableColumn<AsistenteVuelo, String> tcFechaNacimiento;
    @FXML 
    private TableColumn<AsistenteVuelo, String> tcGenero;
    @FXML 
    private TableColumn<AsistenteVuelo, Double> tcSalario;
    @FXML 
    private TableColumn<AsistenteVuelo, Integer> tcHorasAsistencia;
    @FXML 
    private TableColumn<AsistenteVuelo, Integer> tcNoIdiomas;

    @FXML
    private Button btnAlta;
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
    
    private final ObservableList<AsistenteVuelo> asistentes = FXCollections.observableArrayList();
    private final AsistenteVueloDAO asistenteDAO = new AsistenteVueloDAO();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();

        btnExportarCsv.disableProperty().bind(Bindings.isEmpty(tvAsistentes.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvAsistentes.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvAsistentes.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvAsistentes.getItems()));

        btnActualizar.disableProperty().bind(tvAsistentes.getSelectionModel().selectedItemProperty().isNull());
        btnEliminar.disableProperty().bind(tvAsistentes.getSelectionModel().selectedItemProperty().isNull());
    }


    @FXML
    private void onRegresar(ActionEvent event) {
        try {
            Stage escenarioBase = (Stage) tvAsistentes.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAvionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnAlta(ActionEvent event) {
        try {
            AsistenteVuelo nuevo = new AsistenteVuelo();
            nuevo.setNoPersonal(asistenteDAO.generarIdUnico());

            if (irFormularioAsistente(nuevo)) {
                asistenteDAO.agregar(nuevo);
                cargarInformacion();
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Asistente de vuelo registrado correctamente.");
            }
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al generar ID", e.getMessage());
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        AsistenteVuelo seleccionado = tvAsistentes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            AsistenteVuelo copia = clonarAsistente(seleccionado);
            if (irFormularioAsistente(copia)) {
                try {
                    asistenteDAO.actualizar(copia);
                    cargarInformacion();
                } catch (IOException e) {
                    Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al actualizar", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        AsistenteVuelo seleccionado = tvAsistentes.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                asistenteDAO.eliminar(seleccionado.getNoPersonal());
                cargarInformacion();
            } catch (IOException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al eliminar", e.getMessage());
            }
        }
    }
    
    private void exportarArchivo(String descripcion, String extensionFiltro) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo exportado");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extensionFiltro));

        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            try {
                asistenteDAO.exportarAArchivo(archivoSeleccionado);
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Exportación completada.");
            } catch (IOException | DocumentException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al exportar el archivo.");
            }
        }
    }

    @FXML 
    private void onExportarCSV(ActionEvent event) { 
        exportarArchivo("CSV", "*.csv"); 
    }
    
    @FXML 
    private void onExportarXLS(ActionEvent event) { 
        exportarArchivo("Excel (XLS)", "*.xls"); 
    }
    
    @FXML 
    private void onExportarXLSX(ActionEvent event) { 
        exportarArchivo("Excel (XLSX)", "*.xlsx"); 
    }
    
    @FXML private void onExportarPDF(ActionEvent event) { 
        exportarArchivo("PDF", "*.pdf"); 
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
        tcHorasAsistencia.setCellValueFactory(new PropertyValueFactory<>("noHorasAsistencia"));
        tcNoIdiomas.setCellValueFactory(new PropertyValueFactory<>("noIdiomas"));
    }
    
    private void cargarInformacion() {
        try {
            asistentes.setAll(asistenteDAO.obtenerTodos());
            tvAsistentes.setItems(asistentes);
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar información", ex.getMessage());
        }
    }
    
    private boolean irFormularioAsistente(AsistenteVuelo asistente) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLFormularioAsistente.fxml"));
            Parent vista = loader.load();

            FXMLFormularioAsistenteController controlador = loader.getController();
            controlador.inicializarInformacion(asistente, asistenteDAO);

            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario Asistente de Vuelo");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();

            return controlador.isConfirmado();
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al abrir formulario", e.getMessage());
            return false;
        }
    }

    private AsistenteVuelo clonarAsistente(AsistenteVuelo original) {
        return new AsistenteVuelo(
            original.getNoPersonal(),
            original.getNombre(),
            original.getDireccion(),
            original.getFechaNacimiento(),
            original.getGenero(),
            original.getSalario(),
            original.getApellidoPaterno(),
            original.getApellidoMaterno(),
            original.getNoHorasAsistencia(),
            original.getNoIdiomas()
        );
    }
    
}



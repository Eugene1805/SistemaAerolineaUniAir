package javafxsistemaaerolineauniair.controlador;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import com.itextpdf.text.DocumentException;
import javafx.beans.binding.Bindings;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafxsistemaaerolineauniair.modelo.dao.AdministrativoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Administrativo;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author meler
 */
public class FXMLAdministrativosController implements Initializable {

    @FXML private TableView<Administrativo> tvAdministrativos;
    @FXML private TableColumn<Administrativo, Integer> tcNoPersonal;
    @FXML private TableColumn<Administrativo, String> tcNombre;
    @FXML private TableColumn<Administrativo, String> tcPaterno;
    @FXML private TableColumn<Administrativo, String> tcMaterno;
    @FXML private TableColumn<Administrativo, String> tcDireccion;
    @FXML private TableColumn<Administrativo, String> tcFechaNacimiento;
    @FXML private TableColumn<Administrativo, String> tcGenero;
    @FXML private TableColumn<Administrativo, Double> tcSalario;
    @FXML private TableColumn<Administrativo, String> tcDepartamento;

    @FXML private Button btnAlta;
    @FXML private Button btnActualizar;
    @FXML private Button btnEliminar;
    @FXML private MenuItem btnExportarCsv;
    @FXML private MenuItem btnExportarXLS;
    @FXML private MenuItem btnExportarXLSX;
    @FXML private MenuItem btnExportarPDF;

    private final ObservableList<Administrativo> administrativos = FXCollections.observableArrayList();
    private final AdministrativoDAO administrativoDAO = new AdministrativoDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();

        btnExportarCsv.disableProperty().bind(Bindings.isEmpty(tvAdministrativos.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvAdministrativos.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvAdministrativos.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvAdministrativos.getItems()));

        btnActualizar.disableProperty().bind(tvAdministrativos.getSelectionModel().selectedItemProperty().isNull());
        btnEliminar.disableProperty().bind(tvAdministrativos.getSelectionModel().selectedItemProperty().isNull());
    }

    @FXML
    private void btnAlta(ActionEvent event) {
        try {
            Administrativo nuevo = new Administrativo();
            nuevo.setNoPersonal(administrativoDAO.generarIdUnico());

            if (irFormularioAdministrativo(nuevo)) {
                administrativoDAO.agregar(nuevo);
                cargarInformacion();
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Administrativo registrado correctamente.");
            }
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al generar ID", e.getMessage());
        }
    }

    @FXML
    private void onActualizar(ActionEvent event) {
        Administrativo seleccionado = tvAdministrativos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Administrativo copia = clonarAdministrativo(seleccionado);
            if (irFormularioAdministrativo(copia)) {
                try {
                    administrativoDAO.actualizar(copia);
                    cargarInformacion();
                } catch (IOException e) {
                    Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al actualizar", e.getMessage());
                }
            }
        }
    }

    @FXML
    private void onEliminar(ActionEvent event) {
        Administrativo seleccionado = tvAdministrativos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            try {
                administrativoDAO.eliminar(seleccionado.getNoPersonal());
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
                administrativoDAO.exportarAArchivo(archivoSeleccionado);
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", "Exportación completada.");
            } catch (IOException | DocumentException e) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al exportar el archivo.");
            }
        }
    }

    @FXML private void onExportarCSV(ActionEvent event) { 
        exportarArchivo("CSV", "*.csv"); 
    }
    @FXML private void onExportarXLS(ActionEvent event) { 
        exportarArchivo("Excel (XLS)", "*.xls"); 
    }
    @FXML private void onExportarXLSX(ActionEvent event) { 
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
        tcDepartamento.setCellValueFactory(new PropertyValueFactory<>("departamento"));
    }

    private void cargarInformacion() {
        try {
            administrativos.setAll(administrativoDAO.obtenerTodos());
            tvAdministrativos.setItems(administrativos);
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar información", ex.getMessage());
        }
    }

    private boolean irFormularioAdministrativo(Administrativo administrativo) {
        try {
            FXMLLoader loader = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLFormularioAdministrativo.fxml"));
            Parent vista = loader.load();

            FXMLFormularioAdministrativoController controlador = loader.getController();
            controlador.inicializarInformacion(administrativo, administrativoDAO);

            Stage escenarioFormulario = new Stage();
            escenarioFormulario.setScene(new Scene(vista));
            escenarioFormulario.setTitle("Formulario Administrativo");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();

            return controlador.isConfirmado();
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al abrir formulario", e.getMessage());
            return false;
        }
    }

    public Administrativo clonarAdministrativo(Administrativo original) {
        return new Administrativo(
            original.getNoPersonal(),
            original.getNombre(),
            original.getDireccion(),
            original.getFechaNacimiento(),
            original.getGenero(),
            original.getSalario(),
            original.getApellidoPaterno(),
            original.getApellidoMaterno(),
            original.getDepartamento()
        );
    }

    @FXML
    private void onRegresar(ActionEvent event) {
        try {
            Stage escenarioBase = (Stage) tvAdministrativos.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAdministrativosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

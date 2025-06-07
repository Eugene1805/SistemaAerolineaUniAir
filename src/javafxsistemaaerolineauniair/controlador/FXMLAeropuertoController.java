package javafxsistemaaerolineauniair.controlador;

import com.itextpdf.text.DocumentException;
import java.io.File;
import javafxsistemaaerolineauniair.modelo.dao.AeropuertoDAO;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
import javafxsistemaaerolineauniair.util.Util;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafxsistemaaerolineauniair.excepciones.AeropuertoConVuelosException;


/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLAeropuertoController implements Initializable {

    @FXML
    public TableView<Aeropuerto> tvAeropuerto;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcDireccion;
    @FXML
    private TableColumn tcPersonaContacto;
    @FXML
    private TableColumn tcTelefono;
    @FXML
    private TableColumn tcFlota;
    
    private final ObservableList<Aeropuerto> aeropuertos = FXCollections.observableArrayList();
    
    public AeropuertoDAO aeropuertoDAO = new AeropuertoDAO();
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private MenuItem btnExportarCsv;
    @FXML
    private MenuItem btnExportarXLSX;
    @FXML
    private MenuItem btnExportarPDF;
    @FXML
    private MenuItem btnExportarXLS;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTable();
        loadInformation();
        //Desactivar los botones si la tabla esta vacia para no poder exportar archivos vacios
        
        btnExportarCsv.disableProperty().bind(Bindings.isEmpty(tvAeropuerto.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvAeropuerto.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvAeropuerto.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvAeropuerto.getItems()));

        //Desactivar los botones si no hay una fila seleccionada
        btnActualizar.disableProperty().bind(tvAeropuerto.getSelectionModel().selectedItemProperty().isNull());
        btnEliminar.disableProperty().bind(tvAeropuerto.getSelectionModel().selectedItemProperty().isNull());
    }    

    @FXML
    private void btnAlta(ActionEvent event) {
        try {
            Aeropuerto nuevo = new Aeropuerto();
            nuevo.setId(aeropuertoDAO.generarIdUnico());
            
            // Mostrar diálogo de edición
            if (irFormularioAeropuerto(nuevo)) {
                aeropuertoDAO.agregar(nuevo);
                loadInformation();
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", 
                    "Aeropuerto registrado correctamente");
            }
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al registrar un nuevo aeropuerto", 
                    "Error al generar un ID unico para el aeropuerto");
        }
    }
    @FXML
    private void onActualizar(ActionEvent event) {
        Aeropuerto seleccionado = tvAeropuerto.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            // Mostrar diálogo de edición (clonar para evitar modificar directamente)
            Aeropuerto copia = clonarAeropuerto(seleccionado);
            if (irFormularioAeropuerto(copia)) {
                try {
                    aeropuertoDAO.actualizar(copia);
                    loadInformation();
                } catch (IOException e) {
                    Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al actualizar",
                            e.getMessage());
                }
            }
        } else {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Seleccione un aeropuerto para actualizar",
                    "Debe seleccionar un aeropuerto para poder utilizar la opcion de actualizar");
        }
    }

    @FXML
    public void onEliminar(ActionEvent event) {
        Aeropuerto seleccionado = tvAeropuerto.getSelectionModel().getSelectedItem();
        
        if (seleccionado == null) {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Seleccione un aeropuerto para eliminar",
                    "Debe seleccionar un aeropuerto para poder utilizar la opción de eliminar");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar Eliminación");
        confirmacion.setHeaderText("¿Está seguro de que desea eliminar el aeropuerto: " + seleccionado.getNombre() + "?");
        confirmacion.setContentText("Esta acción no se puede deshacer.");

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            // Aquí reemplazamos tu antiguo if/else con un try/catch
            try {
                // Intentamos eliminar. El método del DAO hará la validación internamente.
                aeropuertoDAO.verificarYEliminar(seleccionado.getId());
                
                // Si no se lanza ninguna excepción, todo fue exitoso.
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", 
                        "El aeropuerto ha sido eliminado correctamente.");
                
                // Recargamos la tabla para reflejar el cambio.
                loadInformation();

            } catch (AeropuertoConVuelosException e) {
                // Capturamos nuestra excepción específica y mostramos el mensaje al usuario.
                Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Operación Denegada", e.getMessage());
                
            } catch (IOException e) {
                // Capturamos cualquier otro error de archivo.
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Archivo", 
                        "Ocurrió un error al intentar eliminar el aeropuerto: " + e.getMessage());
            }
        }
    }
    
    @FXML
    private void onExportarCSV(ActionEvent event) {
        exportarArchivoConExtension("CSV files (*.csv)", "*.csv");
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
    private void onExportarXLS(ActionEvent event) {
        exportarArchivoConExtension("Excel 97-2003 (*.xls)", "*.xls");
    }
    
    private void configureTable(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcDireccion.setCellValueFactory(new PropertyValueFactory("direccion"));
        tcPersonaContacto.setCellValueFactory(new PropertyValueFactory("personaContacto"));
        tcTelefono.setCellValueFactory(new PropertyValueFactory("telefono"));
        tcFlota.setCellValueFactory(new PropertyValueFactory("flota"));
    }
    
    private void loadInformation(){
        try {
            aeropuertos.setAll(aeropuertoDAO.obtenerTodos());
            tvAeropuerto.setItems(aeropuertos);
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar la informacion",
                    ex.getMessage());
        } 
    }

    private boolean irFormularioAeropuerto(Aeropuerto aeropuerto) {
        try {
            Stage escenarioFormulario = new Stage();
            FXMLLoader loader = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.
                    getResource("vista/FXMLFormularioAeropuerto.fxml"));
            Parent vista = loader.load();
            FXMLFormularioAeropuertoController controlador = loader.getController();

            // Configurar el controlador con el aeropuerto y el DAO
            controlador.inicializarInformacion(aeropuerto, aeropuertoDAO);

            Scene escena = new Scene(vista);
            escenarioFormulario.setScene(escena);
            escenarioFormulario.setTitle(aeropuerto.getId() != 0 ? 
                "Nuevo Aeropuerto" : "Editar Aeropuerto");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();

            // Retornar true si el usuario confirmó los cambios
            return controlador.isConfirmado();
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar el formulario", 
                    ex.getMessage());
            return false;
        }
    }

    public Aeropuerto clonarAeropuerto(Aeropuerto original) {
        return new Aeropuerto(
            original.getId(),
            original.getNombre(),
            original.getDireccion(),
            original.getPersonaContacto(),
            original.getTelefono(),
            original.getFlota()
        );
    }
    
    private void exportarArchivoConExtension(String descripcion, String extensionFiltro) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo exportado");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extensionFiltro));

        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            try {
                aeropuertoDAO.exportarAArchivo(archivoSeleccionado);
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Exito",
                        "Exportacion completada correctamente");
            } catch (IOException | DocumentException e) {
                e.printStackTrace();
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al exportar el archivo");
            }
        }
    }    

    @FXML
    private void onRegresar(ActionEvent event) {
        try {
            Stage escenarioBase = (Stage) tvAeropuerto.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.
                    getResource("vista/FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAeropuertoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

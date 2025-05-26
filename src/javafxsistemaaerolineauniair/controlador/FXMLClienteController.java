package javafxsistemaaerolineauniair.controlador;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafxsistemaaerolineauniair.modelo.dao.ClienteDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Cliente;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLClienteController implements Initializable {

    @FXML
    private TableView<Cliente> tvClientes;
    @FXML
    private TableColumn tcNombre;
    @FXML
    private TableColumn tcApellidoPaterno;
    @FXML
    private TableColumn tcApellidoMaterno;
    @FXML
    private TableColumn tcNacionalidad;
    @FXML
    private TableColumn<Cliente, Integer> tcEdad;
    @FXML
    private MenuItem btnExportarXLS;
    @FXML
    private MenuItem btnExportarXLSX;
    @FXML
    private MenuItem btnExportarPDF;
    @FXML
    private MenuItem btnExportarCSV;
    
    private final ObservableList<Cliente> clientes = FXCollections.observableArrayList();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    @FXML
    private Button btnRegresar;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTable();
        loadInformation();
        btnExportarCSV.disableProperty().bind(Bindings.isEmpty(tvClientes.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvClientes.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvClientes.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvClientes.getItems()));
    }    

    @FXML
    private void onRegistrar(ActionEvent event) {
        try {
            Cliente nuevo = new Cliente();
            nuevo.setId(clienteDAO.generarIdUnico());
            
            // Mostrar diálogo de edición
            if (irFormularioCliente(nuevo)) {
                clienteDAO.agregar(nuevo);
                loadInformation();
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", 
                    "Cliente registrado correctamente");
            }
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al registrar un nuevo cliente", 
                    "Error al generar un ID unico para el cliente");
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
    
    private void configureTable(){
        tcNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
        tcApellidoPaterno.setCellValueFactory(new PropertyValueFactory("apellidoPaterno"));
        tcApellidoMaterno.setCellValueFactory(new PropertyValueFactory("apellidoMaterno"));
        tcNacionalidad.setCellValueFactory(new PropertyValueFactory("nacionalidad"));
        tcEdad.setCellValueFactory(c -> {
            String fechaStr = c.getValue().getFechaNacimiento();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate fechaNacimiento = LocalDate.parse(fechaStr, formatter);
                int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();
                return new ReadOnlyObjectWrapper<>(edad);
            } catch (DateTimeParseException e) {
                return new ReadOnlyObjectWrapper<>(0); // o manejar el error como prefieras
            }
        });
    }
    
    private void loadInformation(){
        try {
            clientes.setAll(clienteDAO.obtenerTodos());
            tvClientes.setItems(clientes);
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar la informacion",
                    ex.getMessage());
        } 
    }

   private boolean irFormularioCliente(Cliente cliente) {
        try {
            Stage escenarioFormulario = new Stage();
            FXMLLoader loader = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.
                    getResource("vista/FXMLFormularioCliente.fxml"));
            Parent vista = loader.load();
            FXMLFormularioClienteController controlador = loader.getController();

            // Configurar el controlador con el aeropuerto y el DAO
            controlador.inicializarInformacion(cliente, clienteDAO);

            Scene escena = new Scene(vista);
            escenarioFormulario.setScene(escena);
            escenarioFormulario.setTitle(cliente.getId() != 0 ? 
                "Nuevo Cliente" : "Editar Cliente");
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
   
   private void exportarArchivoConExtension(String descripcion, String extensionFiltro) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo exportado");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extensionFiltro));

        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            try {
                clienteDAO.exportarAArchivo(archivoSeleccionado);
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
            Stage escenarioBase = (Stage) btnRegresar.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAvionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

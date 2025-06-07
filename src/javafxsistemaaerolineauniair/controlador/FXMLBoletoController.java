package javafxsistemaaerolineauniair.controlador;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafxsistemaaerolineauniair.modelo.dao.AvionDAO;
import javafxsistemaaerolineauniair.modelo.dao.VueloDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLBoletoController implements Initializable {

    @FXML
    private TableView<Vuelo> tvVuelos;
    @FXML
    private TableColumn<?, ?> colNumPasajeros;
    @FXML
    private TableColumn<?, ?> colCiudadSalida;
    @FXML
    private TableColumn<?, ?> colCiudadLlegada;
    @FXML
    private TableColumn<?, ?> colFechaSalida;
    @FXML
    private TableColumn<?, ?> colHoraSalida;
    @FXML
    private TableColumn<?, ?> colFechaLlegada;
    @FXML
    private TableColumn<?, ?> colHoraLlegada;
    @FXML
    private TableColumn<?, ?> colTiempoRecorrido;
    @FXML
    private TableColumn<?, ?> colCostoBoleto;
    @FXML
    private TableColumn<Vuelo, String> colAvion;
    @FXML
    private MenuItem btnExportarCsv;
    @FXML
    private MenuItem btnExportarXLS;
    @FXML
    private MenuItem btnExportarXLSX;
    @FXML
    private MenuItem btnExportarPDF;
    @FXML
    private Button btnRegresar;
    
    
    private final VueloDAO vueloDAO = new VueloDAO();
    private final AvionDAO avionDAO = new AvionDAO();
 
    private final ObservableList<Vuelo> vuelos = FXCollections.observableArrayList();
    private final Map<Integer, String> mAviones = new HashMap<>();
    @FXML
    private Button btnComprar;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();
        btnComprar.disableProperty().bind(tvVuelos.getSelectionModel().selectedItemProperty().isNull());
        btnExportarCsv.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
    }    

    @FXML
    private void btnClicRegresar(ActionEvent event) {
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

    @FXML
    private void btnComprar(ActionEvent event) {
        Vuelo vueloSeleccionado = tvVuelos.getSelectionModel().getSelectedItem();
        try {
            Stage escenarioBase = (Stage) btnRegresar.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLCompraBoleto.fxml"));
            Parent vista = cargador.load();
            FXMLCompraBoletoController controller = cargador.getController();
            controller.inicializarDatos(vueloSeleccionado);
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Comprar Boleto");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAvionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void btnClicExportarCSV(ActionEvent event) {
        exportarArchivoConExtension("CSVV files (*.csv)", "*.csv");
    }

    @FXML
    private void btnClicExportarXLS(ActionEvent event) {
        exportarArchivoConExtension("Excel 97-2003 (*.xls)", "*.xls");
    }

    @FXML
    private void btnClicExportarXLSX(ActionEvent event) {
        exportarArchivoConExtension("Excel Workbook (*.xlsx)", "*.xlsx");   
    }

    @FXML
    private void btnClicExportarPDF(ActionEvent event) {
        exportarArchivoConExtension("PDF files (*.pdf)", "*.pdf");    
    }
    
    private void configurarTabla() {
        colNumPasajeros.setCellValueFactory(new PropertyValueFactory("numPasajeros"));
        colCiudadSalida.setCellValueFactory(new PropertyValueFactory("ciudadSalida"));
        colCiudadLlegada.setCellValueFactory(new PropertyValueFactory("ciudadLlegada"));
        colFechaSalida.setCellValueFactory(new PropertyValueFactory("fechaSalida"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory("horaSalida"));
        colFechaLlegada.setCellValueFactory(new PropertyValueFactory("fechaLlegada"));
        colHoraLlegada.setCellValueFactory(new PropertyValueFactory("horaLlegada"));
        colTiempoRecorrido.setCellValueFactory(new PropertyValueFactory("tiempoRecorrido"));
        colCostoBoleto.setCellValueFactory(new PropertyValueFactory("costoBoleto"));
        
        cargarAviones();
        
        colAvion.setCellValueFactory(cellData ->{
            Vuelo v = cellData.getValue();
            String texto = mAviones.getOrDefault(v.getIdAvion(), String.valueOf(v.getIdAvion()));
            return new SimpleStringProperty(texto);
        });
    }

    private void cargarInformacion() {
        try{
            List<Vuelo> lista = vueloDAO.obtenerTodos();

            vuelos.setAll(lista);

            tvVuelos.setItems(vuelos);

        }catch(IOException ex){
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar la informacion", ex.getMessage());
        }
    }
    
    private void cargarAviones() {
        try {
            List<Avion> aviones = avionDAO.obtenerTodos();
            for (Avion a : aviones) {
                String avion = a.getModelo() + " (ID: " + a.getIdAvion() + ")";
                mAviones.put(a.getIdAvion(), avion);
            }
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar la informacion", e.getMessage());
        }
    }
    
    private void exportarArchivoConExtension(String descripcion, String extensionFiltro){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar archivo exportado");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(descripcion, extensionFiltro));

        File archivoSeleccionado = fileChooser.showSaveDialog(null);
        if (archivoSeleccionado != null) {
            try {
                vueloDAO.exportarAArchivo(archivoSeleccionado);
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Exito",
                        "Exportacion completada correctamente");
            } catch (IOException | DocumentException ex) {
                ex.printStackTrace();
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Error al exportar el archivo");
            }
        }        
    }
}

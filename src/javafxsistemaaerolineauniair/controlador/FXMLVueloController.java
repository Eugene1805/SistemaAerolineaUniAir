package javafxsistemaaerolineauniair.controlador;

import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafxsistemaaerolineauniair.modelo.dao.VueloDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import javafxsistemaaerolineauniair.util.Util;


/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLVueloController implements Initializable {

    @FXML
    private TableView<Vuelo> tvVuelos;
    @FXML
    private MenuItem btnExportarCsv;
    @FXML
    private MenuItem btnExportarXLS;
    @FXML
    private MenuItem btnExportarXLSX;
    @FXML
    private MenuItem btnExportarPDF;
    @FXML
    private TableColumn colNumPasajeros;
    @FXML
    private TableColumn colCiudadSalida;
    @FXML
    private TableColumn colCiudadLlegada;
    @FXML
    private TableColumn colFechaSalida;
    @FXML
    private TableColumn colHoraSalida;
    @FXML
    private TableColumn colFechaLlegada;
    @FXML
    private TableColumn colHoraLlegada;
    @FXML
    private TableColumn colAvion;
    @FXML
    private TableColumn colTiempoRecorrido;
    @FXML
    private TableColumn colCostoBoleto;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    
    private final ObservableList<Vuelo> vuelos = FXCollections.observableArrayList();
    private final VueloDAO vueloDAO = new VueloDAO();



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarTabla();
        cargarInformacion();
        
        btnExportarCsv.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
        btnExportarXLS.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
        btnExportarXLSX.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
        btnExportarPDF.disableProperty().bind(Bindings.isEmpty(tvVuelos.getItems()));
        
        btnActualizar.disableProperty().bind(tvVuelos.getSelectionModel().selectedItemProperty().isNull());
        btnEliminar.disableProperty().bind(tvVuelos.getSelectionModel().selectedItemProperty().isNull());
    }    

    @FXML
    private void btnClicAltaVuelos(ActionEvent event) {
        try{
            Vuelo vuelo = new Vuelo();
            vuelo.setIdVuelo(vueloDAO.generarIdVueloUnico());
            if (irFormularioVuelo(vuelo)) {
                vueloDAO.agregar(vuelo);
                cargarInformacion();
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Éxito", 
                    "Vuelo registrado correctamente");
            }
        }catch(IOException ex){
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al registrar un nuevo vuelo", 
                    "Error al generar un ID unico para el vuelo");
        }
    }
    
    private boolean irFormularioVuelo(Vuelo vuelo){
        try{
            Stage escenarioFormulario = new Stage();
            FXMLLoader loader = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.
                    getResource("/javafxsistemaaerolineauniair/vista/FXMLFormularioVuelo.fxml"));
            Parent vista = loader.load();
            FXMLFormularioVueloController controlador = loader.getController();
            controlador.inicializarInformacion(vuelo, vueloDAO);

            Scene escena = new Scene(vista);
            escenarioFormulario.setScene(escena);
            escenarioFormulario.setTitle(vuelo.getIdVuelo() != 0 ? 
                "Nuevo Vuelo" : "Editar Vuelo");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();

            // Retornar true si el usuario confirmó los cambios
            return controlador.isConfirmado();
    
        }catch(IOException ex){
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar el formulario", 
                    ex.getMessage());
            return false;   
        }
    }
    @FXML
    private void btnClicActualizarVuelo(ActionEvent event) {
    /*
   //Añadir en vueloDAO.actualizar
        Vuelo seleccionado = tvVuelos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            Vuelo copia = clonarVuelo(seleccionado);
            if (irFormularioVuelo(copia)) {
                try {
                    vueloDAO.actualizar(copia);
                    cargarInformacion();
                } catch (IOException e) {
                    Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al actualizar", e.getMessage());
                }
            }
        } else {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Seleccione un vuelo para actualizar", "Debe seleccionar un vuelo para editar.");
        }
        */
    }
    
    public Vuelo clonarVuelo(Vuelo original){
        return new Vuelo(original.getIdVuelo(),
                original.getNumPasajeros(),
                original.getTiempoRecorrido(),
                original.getCostoBoleto(),
                original.getCiudadSalida(),
                original.getCiudadLlegada(), 
                original.getFechaSalida(),
                original.getHoraSalida(),
                original.getFechaLlegada(),
                original.getHoraLlegada(),
                original.getIdAvion()
                );
    }

    @FXML
    private void btnClicEliminarVuelo(ActionEvent event) {
    /*
        //Añadir en VUELODAO .eliminar
        Vuelo seleccionado = tvVuelos.getSelectionModel().getSelectedItem();
    if (seleccionado != null) {
        try {
            vueloDAO.eliminar(seleccionado.getIdVuelo());
            cargarInformacion();
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al eliminar", e.getMessage());
        }
    } else {
        Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Seleccione un vuelo para eliminar", "Debe seleccionar un vuelo para eliminar.");
    }
        */
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
    @FXML
    private void btnClicRegresar(ActionEvent event) {
        try{
            Stage escenarioBase = (Stage) tvVuelos.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            Logger.getLogger(FXMLVueloController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        colAvion.setCellValueFactory(new PropertyValueFactory("avion"));
    }

    private void cargarInformacion() {
        try{
            vuelos.setAll(vueloDAO.obtenerTodos());
            tvVuelos.setItems(vuelos);
        }catch(IOException ex){
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar la informacion", ex.getMessage());
        }
    }
}

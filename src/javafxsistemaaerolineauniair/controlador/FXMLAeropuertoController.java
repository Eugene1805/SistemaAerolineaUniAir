package javafxsistemaaerolineauniair.controlador;

import javafxsistemaaerolineauniair.modelo.dao.AeropuertoDAO;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;


/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLAeropuertoController implements Initializable {

    @FXML
    private TableView<Aeropuerto> tvAeropuerto;
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
    
    private final AeropuertoDAO aeropuertoDAO = new AeropuertoDAO();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configureTable();
        loadInformation();
    }    

    @FXML
    private void btnAlta(ActionEvent event) {
        irFormularioAeropuerto();
    }

    @FXML
    private void btnActualizar(ActionEvent event) {
    }

    @FXML
    private void btnEliminar(ActionEvent event) {
    }

    @FXML
    private void btnExportarCSV(ActionEvent event) {
    }

    @FXML
    private void btnExportarXSL(ActionEvent event) {
    }

    @FXML
    private void btnExportarXLSX(ActionEvent event) {
    }

    @FXML
    private void btnExportarPDF(ActionEvent event) {
    }

    @FXML
    private void btnCerrarSesion(ActionEvent event) {
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
            ex.printStackTrace();
        } 
    }

    private void irFormularioAeropuerto() {
        try {
            Stage escenarioFormulario = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Parent vista = loader.load(getClass().getResource("vista/FXMLFormulariAeropuerto.fxml"));
            //TODO paso de parametros
            Scene escena = new Scene(vista);
            escenarioFormulario.setScene(escena);
            escenarioFormulario.setTitle("Formulario Aerolinea");
            escenarioFormulario.initModality(Modality.APPLICATION_MODAL);
            escenarioFormulario.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

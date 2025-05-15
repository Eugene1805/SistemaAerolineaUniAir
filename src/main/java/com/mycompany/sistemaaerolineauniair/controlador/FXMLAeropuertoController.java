package com.mycompany.sistemaaerolineauniair.controlador;

import com.mycompany.sistemaaerolineauniair.modelo.dao.AeropuertoDAO;
import com.mycompany.sistemaaerolineauniair.modelo.pojos.Aeropuerto;
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
import javafx.scene.control.cell.PropertyValueFactory;


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
}

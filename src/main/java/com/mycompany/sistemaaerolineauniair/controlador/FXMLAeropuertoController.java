package com.mycompany.sistemaaerolineauniair.controlador;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.Aeropuerto;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Nash
 */
public class FXMLFormularioVueloController implements Initializable {

    @FXML
    private TextField tfNumeroPasajeros;
    @FXML
    private TextField tfCiudadSalida;
    @FXML
    private Label tfCiudadLlegada;
    @FXML
    private DatePicker dpFechaLlegada;
    @FXML
    private DatePicker dpFechaHoraSalida;
    @FXML
    private TextField tfHoraLlegada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnClicAceptar(ActionEvent event) {
    }

    @FXML
    private void btnClicCancelar(ActionEvent event) {
    }
    
}

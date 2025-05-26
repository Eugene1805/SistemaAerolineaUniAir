/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
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
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLAdministrativoController implements Initializable {

    @FXML
    private Button btnRegresar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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

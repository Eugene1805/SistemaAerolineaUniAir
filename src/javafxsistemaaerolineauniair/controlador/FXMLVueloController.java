package javafxsistemaaerolineauniair.controlador;

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


/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLVueloController implements Initializable {

    @FXML
    private TableView<?> tvVuelos;
    @FXML
    private MenuItem btnExportarCsv;
    @FXML
    private MenuItem btnExportarXLS;
    @FXML
    private MenuItem btnExportarXLSX;
    @FXML
    private MenuItem btnExportarPDF;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnClicAltaVuelos(ActionEvent event) {
    }

    @FXML
    private void btnClicActualizarVuelo(ActionEvent event) {
    }

    @FXML
    private void btnClicEliminarVuelo(ActionEvent event) {
    }

    @FXML
    private void btnClicExportarCSV(ActionEvent event) {
    }

    @FXML
    private void btnClicExportarXLS(ActionEvent event) {
    }

    @FXML
    private void btnClicExportarXLSX(ActionEvent event) {
    }

    @FXML
    private void btnClicExportarPDF(ActionEvent event) {
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
            Logger.getLogger(FXMLAerolineaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        /*
        try {
            Stage escenarioBase = (Stage) tvAeropuerto.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Principal");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAerolineaController.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
    }
    
}

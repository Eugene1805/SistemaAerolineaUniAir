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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLBoletoController implements Initializable {

    private Button btnRegresar;
    @FXML
    private TableView<?> tvVuelos;
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
    private TableColumn<?, ?> colAvion;
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

    @FXML
    private void btnClicRegresar(ActionEvent event) {
    }

    @FXML
    private void btnComprar(ActionEvent event) {
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
    
}

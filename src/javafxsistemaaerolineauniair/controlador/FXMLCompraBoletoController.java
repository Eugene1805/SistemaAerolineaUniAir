package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLCompraBoletoController implements Initializable {

    @FXML
    private ComboBox<?> cbCliente;
    @FXML
    private ComboBox<?> cbAsiento;
    @FXML
    private Label lbPrecio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}

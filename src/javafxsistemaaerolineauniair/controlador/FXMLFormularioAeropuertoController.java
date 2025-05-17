package javafxsistemaaerolineauniair.controlador;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafxsistemaaerolineauniair.modelo.pojo.Aeropuerto;
import javafxsistemaaerolineauniair.modelo.dao.AeropuertoDAO;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLFormularioAeropuertoController implements Initializable {

    @FXML
    private TextField tfNombre;
    @FXML
    private TextField tfDireccion;
    @FXML
    private TextField tfPersonaContacto;
    @FXML
    private TextField tfTelefono;
    @FXML
    private TextField tfFlota;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnAceptar(ActionEvent event) {
        try {
            Aeropuerto aeropuerto = new Aeropuerto();
            AeropuertoDAO aeropuertoDAO = new AeropuertoDAO();
            aeropuerto.setId(aeropuertoDAO.generarIdUnico());
            aeropuerto.setNombre(tfNombre.getText());
            aeropuerto.setDireccion(tfDireccion.getText());
            aeropuerto.setPersonaContacto(tfPersonaContacto.getText());
            aeropuerto.setTelefono(tfTelefono.getText());
            aeropuerto.setFlota(Integer.parseInt(tfFlota.getText()));
            aeropuertoDAO.agregar(aeropuerto);
        } catch (IOException ex) {
            ex.printStackTrace();
        }    
    }

    @FXML
    private void btnCancelar(ActionEvent event) {
        
    }
    
}

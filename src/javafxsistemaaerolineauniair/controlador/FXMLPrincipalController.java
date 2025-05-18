package javafxsistemaaerolineauniair.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;

/**
 * FXML Controller class
 *
 * @author uriel
 */
public class FXMLPrincipalController implements Initializable {

    @FXML
    private Label lbCerrarSesion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void lbClicAdministrativo(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLAdministrativo.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Administrativo");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicPiloto(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPiloto.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Piloto");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicAsistentePiloto(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLAsistentePiloto.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Asistente Piloto");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicAeropuerto(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLAeropuerto.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Aeropuerto");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicAviones(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLAvion.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Aviones");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicVuelos(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLVuelo.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Vuelos");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicRegistroClientes(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLCliente.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Registro de clientes");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicComprarBoleto(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLBoleto.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Comprar boleto");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private void lbClicCerrarSesión(MouseEvent event) {
        try{
            Stage escenarioBase = (Stage) lbCerrarSesion.getScene().getWindow();
            Parent vista = FXMLLoader.load(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLLogin.fxml"));
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Inicio Sesión");
            escenarioBase.showAndWait();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
}

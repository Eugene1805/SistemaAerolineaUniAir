package javafxsistemaaerolineauniair.controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafxsistemaaerolineauniair.modelo.dao.LoginDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Usuario;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private Label lbUsername;
    @FXML
    private Label lbPassword;
    @FXML
    private TextField tfUsername;
    @FXML
    private PasswordField tfPassword;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void btnClicLogin(ActionEvent event) {
        String usuario = tfUsername.getText();
        String contrasena = tfPassword.getText();
        if(validarCampos(usuario, contrasena))
            validarCredenciales(usuario, contrasena);
    }
    
    private boolean validarCampos(String usuario, String contrasena){
        //Limpiar campos
        lbUsername.setText("");
        lbPassword.setText("");
        boolean camposValidos = true;
        if(usuario.isEmpty()){
            lbUsername.setText("Usuario obligatorio");
            camposValidos = false;
        }
        if(contrasena.isEmpty()){
            lbPassword.setText("Contraseña obligatoria");
            camposValidos = false;
        }
        return camposValidos;
    }
    
    private void validarCredenciales(String usuario, String contrasena){
        try{
            LoginDAO login = new LoginDAO();
            Usuario usuarioSesion = login.verificarCredenciales
            (usuario, contrasena);
            if (usuarioSesion != null){
                Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, 
                        "Credenciales correctas", "Bienvenido(a) " 
                        + usuarioSesion.toString() + " al sistema.");
                irPantallaPrincipal();
            }else{
                Util.mostrarAlertaSimple(Alert.AlertType.WARNING, 
                        "Credenciales incorrectas", "Usuario y/o contraseña "
                        + "incorrectos, por favor verifica tu información.");
            }
        } catch (IOException ex){
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, 
                    "Problemas de conexión", ex.getMessage());
        }
    }
    
    private void irPantallaPrincipal(){
        try {
            Stage escenarioBase = (Stage) tfUsername.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLPrincipal.fxml"));
            Parent vista = cargador.load();
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Home");
            escenarioBase.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
}
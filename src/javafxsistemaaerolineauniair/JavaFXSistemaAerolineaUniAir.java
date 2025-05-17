package javafxsistemaaerolineauniair;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author eugen
 */
public class JavaFXSistemaAerolineaUniAir extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent vista = FXMLLoader.load(getClass().getResource("vista/FXMLLogin.fxml"));
            Scene escenaInicioSesion = new Scene(vista);
            primaryStage.setScene(escenaInicioSesion);
            primaryStage.setTitle("Inicio de sesion");
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(JavaFXSistemaAerolineaUniAir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}

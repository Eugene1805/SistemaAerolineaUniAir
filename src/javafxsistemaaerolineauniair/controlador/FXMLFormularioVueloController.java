/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxsistemaaerolineauniair.controlador;

import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.dao.AvionDAO;
import javafxsistemaaerolineauniair.modelo.dao.GenericDAO;
import javafxsistemaaerolineauniair.modelo.dao.VueloDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import javafxsistemaaerolineauniair.util.Util;

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
    private TextField tfCiudadLlegada;
    @FXML
    private DatePicker dpFechaLlegada;
    @FXML
    private TextField tfHoraLlegada;
    @FXML
    private DatePicker dpFechaSalida;
    @FXML
    private TextField tfHoraSalida;
    @FXML
    private TextField tfCostoBoleto;
    @FXML
    private ComboBox<Avion> cbAvion;
    
    ObservableList<Avion> aviones;
    
    private Vuelo vuelo;
    private VueloDAO vueloDAO;
    private boolean confirmado = false;
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //cargarAviones(); //DESCOMENTAR
    }    

    @FXML
    private void btnClicGuardar(ActionEvent event) {
        if (validarCampos()) {
            //Actualizar objeto vuelo con los valores del formulario
            vuelo.setNumPasajeros(Integer.parseInt(tfNumeroPasajeros.getText()));
            vuelo.setCiudadSalida(tfCiudadSalida.getText());
            vuelo.setCiudadLlegada(tfCiudadLlegada.getText());
            vuelo.setFechaSalida(dpFechaSalida.getValue());
            vuelo.setHoraSalida(LocalTime.parse(tfHoraSalida.getText()));
            vuelo.setFechaLlegada(dpFechaLlegada.getValue());
            vuelo.setHoraLlegada( LocalTime.parse(tfHoraLlegada.getText()));
            vuelo.setCostoBoleto(Double.parseDouble(tfCostoBoleto.getText()));
            vuelo.setIdAvion("AV-0000"); //LO COLOQUÉ PARA PROBAR A VER SI SALIA EL FORMULARIO Y NO PASÓ NADA, SE TENDRÍA QUE QUITAR Y DEJAR EL DE ABAJO CUANDO ESTEN LOS AVIONES
//vuelo.setIdAvion(cbAvion.getValue().getIdentificador()); //Comprobar con el geter que usen como id
            vuelo.calcularTiempoRecorrido();           
            
            confirmado = true;
            ((Stage) tfNumeroPasajeros.getScene().getWindow()).close();           
        }
    }
    
    @FXML
    private void btnClicCancelar(ActionEvent event) {
        confirmado = false;
        ((Stage) tfNumeroPasajeros.getScene().getWindow()).close();  
    }
    
    void inicializarInformacion(Vuelo vuelo, VueloDAO vueloDAO){
        this.vuelo = vuelo;
        this.vueloDAO = vueloDAO;
        
        if (vuelo.getIdVuelo() != 0) { //Cargar datos de vuelo en los campos
            tfNumeroPasajeros.setText(String.valueOf(vuelo.getNumPasajeros()));
            tfCiudadSalida.setText(vuelo.getCiudadSalida());
            tfCiudadLlegada.setText(vuelo.getCiudadLlegada());
            dpFechaSalida.setValue(vuelo.getFechaSalida());
            tfHoraSalida.setText(vuelo.getHoraSalida().toString());
            dpFechaLlegada.setValue(vuelo.getFechaLlegada());
            tfHoraLlegada.setText(vuelo.getHoraLlegada().toString());
            tfCostoBoleto.setText(String.valueOf(vuelo.getCostoBoleto()));
            
            /*IMPLEMENTAR CUANDO SE TENGA LA CLASE AVION
            for(Avion avion : cbAvion.getItems()){
                if (avion.getId().equals(vuelo.getIdAvion())) { //Compraro ids
                    cbAvion.getSelectionModel().select(avion);
                    break;
                }
            }
           */  
        }        
    }
    

    
    boolean isConfirmado() {
        return confirmado;
    }

    private void cargarAviones() {
/*
        //IMPLEMENTAR CUANDO ESTÉ EL DAO DE AVIONES
        try{
        aviones = FXCollections.observableArrayList();
        List<Avion> avionesDAO = AvionDAO.obtenerTodos();
        aviones.addAll(avionesDAO);
        cbAvion.setItems(aviones);
        //List<Avion> aviones = AvionDAO.obtenerTodos(); // Obtener lista de aviones
        //cbAvion.setItems(FXCollections.observableArrayList(aviones)); // Simplificar la carga
        }catch(Exception ex){
        //IOException
        Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar aviones", "Lo sentimos, por el momento no podemos mostrar los aviones");
        }
         */
    }

    private boolean validarCampos() {
        if (tfNumeroPasajeros.getText().isEmpty() || tfCiudadSalida.getText().isEmpty() 
        || tfCiudadLlegada.getText().isEmpty() || dpFechaSalida.getValue() == null 
        || tfHoraSalida.getText().isEmpty() || dpFechaLlegada.getValue() == null 
        || tfHoraLlegada.getText().isEmpty() ) { //|| cbAvion.getValue() == null
        
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos requeridos", 
            "Todos los campos son obligatorios.");
            return false;
        }
        if (!tfHoraSalida.getText().matches("\\d{2}:\\d{2}") || !tfHoraLlegada.getText().matches("\\d{2}:\\d{2}") ) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "Formato de hora inválido (HH:mm).");
            return false;
        }
    
    // Validar formato de hora (HH:mm)
        try {
            LocalTime.parse(tfHoraSalida.getText());
            LocalTime.parse(tfHoraLlegada.getText());
        } catch (Exception e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Formato inválido", "El formato de hora debe ser HH:mm.");
            return false;
        }
        
        if (cbAvion.getItems().isEmpty()){
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error", "No hay aviones disponibles.");
            return false;
        }
        return true;
    }
}

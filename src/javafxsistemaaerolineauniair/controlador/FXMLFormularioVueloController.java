package javafxsistemaaerolineauniair.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafxsistemaaerolineauniair.modelo.dao.AsistenteVueloDAO;
import javafxsistemaaerolineauniair.modelo.dao.VueloDAO;
import javafxsistemaaerolineauniair.modelo.dao.AvionDAO;
import javafxsistemaaerolineauniair.modelo.dao.PilotoDAO;
import javafxsistemaaerolineauniair.modelo.pojo.AsistenteVuelo;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Piloto;
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
    @FXML
    private ComboBox<Piloto> cbPiloto1;
    @FXML
    private ComboBox<Piloto> cbPiloto2;
    @FXML
    private ComboBox<AsistenteVuelo> cbAsistente1;
    @FXML
    private ComboBox<AsistenteVuelo> cbAsistente2;
    @FXML
    private ComboBox<AsistenteVuelo> cbAsistente3;
    @FXML
    private ComboBox<AsistenteVuelo> cbAsistente4;
    
    private Vuelo vuelo;
    private VueloDAO vueloDAO;
    
    private AvionDAO avionDAO = new AvionDAO();
    
    private PilotoDAO pilotoDAO = new PilotoDAO();
    
    private AsistenteVueloDAO asistenteDAO = new AsistenteVueloDAO();
    
    private ObservableList<Avion> aviones;
    private ObservableList<Piloto> pilotos;
    private ObservableList<AsistenteVuelo> asistentes;
    
    private boolean confirmado = false;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarAviones(); 
        cargarPilotos();
        cargarAsistentes();
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
            vuelo.setIdAvion(cbAvion.getValue().getIdAvion());
            vuelo.calcularTiempoRecorrido();           
            
            Piloto piloto1 = cbPiloto1.getValue();
            Piloto piloto2 = cbPiloto2.getValue();
            if (piloto1 == null || piloto2 == null){
                Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Pilotos incompletos",
                        "Debe seleccionar ambos pilotos");
                return;
            }
            if (piloto1.getNoPersonal() == piloto2.getNoPersonal()) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Pilotos duplicados", 
                        "Los dos pilotos deben ser diferentes");
                return;
            }
            
            List<Integer> listaPilotos = new ArrayList<>();
            listaPilotos.add(piloto1.getNoPersonal());
            listaPilotos.add(piloto2.getNoPersonal());
            vuelo.setPilotos(listaPilotos);
            
            AsistenteVuelo as1 = cbAsistente1.getValue();
            AsistenteVuelo as2 = cbAsistente2.getValue();
            AsistenteVuelo as3 = cbAsistente3.getValue();
            AsistenteVuelo as4 = cbAsistente4.getValue();
            
            if (as1 == null || as2 == null || as3 == null || as4 == null) {
                Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Asistentes incompletos",
                        "Debe seleccionar los 4 asistentes");
                return;                
            }
            
            Set<Integer> setAsistentes = new HashSet<>();
            setAsistentes.add(as1.getNoPersonal());
            setAsistentes.add(as2.getNoPersonal());
            setAsistentes.add(as3.getNoPersonal());
            setAsistentes.add(as4.getNoPersonal());
            if(setAsistentes.size() < 4){
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Asistentes duplicados",
                        "Cada asistente debe ser diferente");
                return;
            }
            List<Integer> listaAsistentes = new ArrayList<>();
            listaAsistentes.add(as1.getNoPersonal());
            listaAsistentes.add(as2.getNoPersonal());
            listaAsistentes.add(as3.getNoPersonal());
            listaAsistentes.add(as4.getNoPersonal());
            vuelo.setAsistentes(listaAsistentes);
            
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
        
        if (vuelo.getCiudadLlegada() != null) { //Cargar datos de vuelo en los campos
            tfNumeroPasajeros.setText(String.valueOf(vuelo.getNumPasajeros()));
            tfCiudadSalida.setText(vuelo.getCiudadSalida());
            tfCiudadLlegada.setText(vuelo.getCiudadLlegada());
            dpFechaSalida.setValue(vuelo.getFechaSalida());
            tfHoraSalida.setText(vuelo.getHoraSalida() != null ? "" : vuelo.getHoraSalida().toString());
            dpFechaLlegada.setValue(vuelo.getFechaLlegada());
            tfHoraLlegada.setText(vuelo.getHoraLlegada() != null ? "" : vuelo.getHoraLlegada().toString());
            tfCostoBoleto.setText(String.valueOf(vuelo.getCostoBoleto()));
            
            for(Avion avion : cbAvion.getItems()){
                if(avion.getIdAvion() == vuelo.getIdAvion()){ //Compraro ids
                    cbAvion.getSelectionModel().select(avion);
                    break;
                }
            }
            
            List<Integer> listaPilotos =  vuelo.getPilotos();
            if (listaPilotos != null && listaPilotos.size() == 2){
                try {
                    Piloto p1 = pilotoDAO.buscarPorId(listaPilotos.get(0));
                    Piloto p2 = pilotoDAO.buscarPorId(listaPilotos.get(1));
                    if (p1 != null){
                        cbPiloto1.getSelectionModel().select(p1);
                    } 
                    if (p2 != null){
                        cbPiloto2.getSelectionModel().select(p2);
                    } 
                } catch (IOException e) {
                }
            }
            
            List<Integer> listaAsistentes = vuelo.getAsistentes();
            if (listaAsistentes != null && listaAsistentes.size() == 4) {
                try {
                    AsistenteVuelo a1 = asistenteDAO.buscarPorId(listaAsistentes.get(0));
                    AsistenteVuelo a2 = asistenteDAO.buscarPorId(listaAsistentes.get(1));
                    AsistenteVuelo a3 = asistenteDAO.buscarPorId(listaAsistentes.get(2));
                    AsistenteVuelo a4 = asistenteDAO.buscarPorId(listaAsistentes.get(3));
                    if (a1 != null) cbAsistente1.getSelectionModel().select(a1);
                    if (a2 != null) cbAsistente2.getSelectionModel().select(a2);
                    if (a3 != null) cbAsistente3.getSelectionModel().select(a3);
                    if (a4 != null) cbAsistente4.getSelectionModel().select(a4);
        
                } catch (IOException e) {
                } 
            }             
        }       
    }
   
    boolean isConfirmado() {
        return confirmado;
    }

    private void cargarAviones() {
        try{
            aviones = FXCollections.observableArrayList(avionDAO.obtenerTodos());
            cbAvion.setItems(aviones);
        }catch(IOException ex){
        Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar aviones",
                "Lo sentimos, por el momento no podemos mostrar los aviones en este momento, intente mas tarde");
        }
    }
    
    private void cargarPilotos(){
        try {
            pilotos = FXCollections.observableArrayList(pilotoDAO.obtenerTodos());
            cbPiloto1.setItems(pilotos);
            cbPiloto2.setItems(pilotos);
        } catch (IOException ex) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar pilotos",
                                     "Lo sentimos, por el momento no podemos mostrar los pilotos en este momento, intente mas tarde");
        }        
    }
    
    private void cargarAsistentes(){
        try {
            asistentes = FXCollections.observableArrayList(asistenteDAO.obtenerTodos());
            cbAsistente1.setItems(asistentes);
            cbAsistente2.setItems(asistentes);
            cbAsistente3.setItems(asistentes);
            cbAsistente4.setItems(asistentes);
        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error al cargar asistentes",
                                "Lo sentimos, por el momento no podemos mostrar los asistentes en este momento, intente mas tarde");
                   
        }
    }

    private boolean validarCampos() {
        if (tfNumeroPasajeros.getText().isEmpty() || tfCiudadSalida.getText().isEmpty() 
        || tfCiudadLlegada.getText().isEmpty() || dpFechaSalida.getValue() == null 
        || tfHoraSalida.getText().isEmpty() || dpFechaLlegada.getValue() == null 
        || tfHoraLlegada.getText().isEmpty() || tfCostoBoleto.getText().isEmpty()) { 
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos requeridos", 
            "Todos los campos son obligatorios.");
            return false;
        }
    
    
    // Validar formato de hora (HH:mm)
        try {
            LocalTime.parse(tfHoraSalida.getText());
            LocalTime.parse(tfHoraLlegada.getText());
        } catch (DateTimeParseException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Formato de hora inválido", "El formato de hora debe ser HH:mm.");
            return false;
        }
        
        if (cbAvion.getValue() == null) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                "Avión no seleccionado",
                "Debe seleccionar un Avión para guardar el Vuelo."
            );
            return false;
        }
        return true;
    }
}

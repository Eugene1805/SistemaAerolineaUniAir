package javafxsistemaaerolineauniair.controlador;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
        if (validarCampos() && validarPilotos() && validarAsistentes()) {
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
            
            Piloto p1 = cbPiloto1.getValue();
            Piloto p2 = cbPiloto2.getValue();
            List<Integer> listaPilotos = new ArrayList<>();
            listaPilotos.add(p1.getNoPersonal());
            listaPilotos.add(p2.getNoPersonal());
            vuelo.setPilotos(listaPilotos);
            
            AsistenteVuelo as1 = cbAsistente1.getValue();
            AsistenteVuelo as2 = cbAsistente2.getValue();
            AsistenteVuelo as3 = cbAsistente3.getValue();
            AsistenteVuelo as4 = cbAsistente4.getValue();
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
            tfHoraSalida.setText(vuelo.getHoraSalida() != null ? vuelo.getHoraSalida().toString() : "");
            dpFechaLlegada.setValue(vuelo.getFechaLlegada());
            tfHoraLlegada.setText(vuelo.getHoraLlegada() != null ? vuelo.getHoraLlegada().toString() : "");
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
                    if (p1 != null) cbPiloto1.getSelectionModel().select(p1);
                    if (p2 != null) cbPiloto2.getSelectionModel().select(p2);
                    
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
        || tfHoraLlegada.getText().isEmpty() || tfCostoBoleto.getText().isEmpty() 
        ) { 
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos Faltanres", 
            "No puede haber ningún campo vacío.");
            return false;
        }
        
        if (!tfNumeroPasajeros.getText().matches("\\d+")) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Número de pasajeros inválido",
                "ERROR: El número de pasajeros debe ser un número entero positivo."
            );
            return false;
        }

        try {
            double costo = Double.parseDouble(tfCostoBoleto.getText());
            if (costo < 0) {
                Util.mostrarAlertaSimple(
                    Alert.AlertType.ERROR,
                    "Costo de boleto inválido",
                    "ERROR: El costo del boleto debe ser un número positivo."
                );            
                return false;
            }
        } catch (NumberFormatException e) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Costo de boleto inválido",
                "ERROR: El costo del boleto debe ser un número válido."
            );
            return false;
        }
        
        String regexLetras = "^[A-Za-zÁÉÍÓÚáéíóúÑñ ]+$";
        if (!tfCiudadSalida.getText().matches(regexLetras)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Ciudad de salida inválida",
                "ERROR: Por favor ingrese una ciudad de salida válida."
            );
            return false;
        }
        if (!tfCiudadLlegada.getText().matches(regexLetras)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Ciudad de llegada inválida",
                "ERROR: Por favor ingrese una ciudad de llegada válida."
            );
            return false;
        }
        
        try {
            LocalTime.parse(tfHoraSalida.getText());
            LocalTime.parse(tfHoraLlegada.getText());
        } catch (DateTimeParseException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Formato de hora inválido",
                    "ERROR: El formato de hora debe ser HH:mm.");
            return false;
        }
        
        LocalDate fechaSalida = dpFechaSalida.getValue();
        LocalDate fechaLlegada = dpFechaLlegada.getValue();
        if (fechaLlegada.isBefore(fechaSalida)) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Fechas inconsistentes",
                "ERROR: La fecha de llegada no puede ser anterior a la fecha de salida."
            );
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
    
    private boolean validarPilotos(){
        Piloto p1 = cbPiloto1.getValue();
        Piloto p2 = cbPiloto2.getValue();
        if (p1 == null || p2 == null) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                "Pilotos Incompletos",
                "Por favor selccione a dos pilotos antes de guardar."
            );
            return false;
        }
        if (p1.getNoPersonal() == p2.getNoPersonal()) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Pilotos Duplicados",
                "ERROR: Por favor seleccione a dos pilotos diferentes."
            );
            return false;
        }
        return true; 
    }
    
    private boolean validarAsistentes(){
        AsistenteVuelo a1 = cbAsistente1.getValue();
        AsistenteVuelo a2 = cbAsistente2.getValue();
        AsistenteVuelo a3 = cbAsistente3.getValue();
        AsistenteVuelo a4 = cbAsistente4.getValue();
        if (a1 == null || a2 == null || a3 == null || a4 == null) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.WARNING,
                "Asistentes incompletos",
                "Por favor seleccione a los 4 asistentes antes de guardar."
            );
            return false;
        }
        Set<Integer> idsUnicos = new HashSet<>();
        idsUnicos.add(a1.getNoPersonal());
        idsUnicos.add(a2.getNoPersonal());
        idsUnicos.add(a3.getNoPersonal());
        idsUnicos.add(a4.getNoPersonal());
        if (idsUnicos.size() < 4) {
            Util.mostrarAlertaSimple(
                Alert.AlertType.ERROR,
                "Asistentes duplicados",
                "ERROR: Por favor seleccione a cada asistente diferente."
            );
            return false;
        }
        return true;
    }
}

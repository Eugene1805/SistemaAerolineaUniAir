package javafxsistemaaerolineauniair.controlador;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafxsistemaaerolineauniair.JavaFXSistemaAerolineaUniAir;
import javafxsistemaaerolineauniair.excepciones.VueloLlenoException;
import javafxsistemaaerolineauniair.modelo.dao.AvionDAO;
import javafxsistemaaerolineauniair.modelo.dao.BoletoDAO;
import javafxsistemaaerolineauniair.modelo.dao.ClienteDAO;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Boleto;
import javafxsistemaaerolineauniair.modelo.pojo.Cliente;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import javafxsistemaaerolineauniair.util.Util;

/**
 * FXML Controller class
 *
 * @author eugen
 */
public class FXMLCompraBoletoController implements Initializable {

    @FXML
    private ComboBox<Cliente> cbCliente;
    @FXML
    private ComboBox<String> cbAsiento;
    @FXML
    private Label lbPrecio;
    
    private Vuelo vueloSeleccionado;
    private ClienteDAO clienteDAO;
    private BoletoDAO boletoDAO;
    private AvionDAO avionDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clienteDAO = new ClienteDAO(); 
        boletoDAO = new BoletoDAO();
        avionDAO = new AvionDAO();
    }    

    @FXML
    private void onSeleccionar(ActionEvent event) {
        Cliente clienteSeleccionado = cbCliente.getSelectionModel().getSelectedItem();
        String asientoSeleccionado = cbAsiento.getSelectionModel().getSelectedItem();

        if (clienteSeleccionado == null || asientoSeleccionado == null || asientoSeleccionado.isEmpty()) {
            Util.mostrarAlertaSimple(Alert.AlertType.WARNING, "Campos obligatorios", "Seleccione un cliente y asiento.");
            return;
        }

        try {
            // Crear el nuevo boleto
            Boleto nuevoBoleto = new Boleto();
            nuevoBoleto.setIdVuelo(vueloSeleccionado.getIdVuelo());
            nuevoBoleto.setCiudadSalida(vueloSeleccionado.getCiudadSalida());
            nuevoBoleto.setCiudadLlegada(vueloSeleccionado.getCiudadLlegada());
            nuevoBoleto.setFechaSalida(vueloSeleccionado.getFechaSalida());
            nuevoBoleto.setHoraSalida(vueloSeleccionado.getHoraSalida());
            nuevoBoleto.setFechaLlegada(vueloSeleccionado.getFechaLlegada());
            nuevoBoleto.setHoraLlegada(vueloSeleccionado.getHoraLlegada());
            nuevoBoleto.setCostoBoleto(vueloSeleccionado.getCostoBoleto());
            nuevoBoleto.setIdAvion(vueloSeleccionado.getIdAvion());
            
            nuevoBoleto.setIdCliente(clienteSeleccionado.getId()); // Asumiendo getIdCliente()
            nuevoBoleto.setNombreCliente(clienteSeleccionado.getNombre());
            nuevoBoleto.setAsiento(asientoSeleccionado);
            
            try {
                // Guardar el boleto en el JSON
                boletoDAO.registrarCompraBoleto(nuevoBoleto);
            } catch (VueloLlenoException ex) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "El vuelo esta lleno", ex.getMessage());
            }
            
            // Generar el PDF
            generarPdfBoleto(nuevoBoleto);
            
            Util.mostrarAlertaSimple(Alert.AlertType.INFORMATION, "Compra exitosa", "Informacion guardada correctamente.");


            // Volver a la ventana de boletos
            regresarVentanaBoletos();

        } catch (IOException | DocumentException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error en la compra", "No se pudo generar el boleto.");
        }
    
    }

    void inicializarDatos(Vuelo vuelo) {
        this.vueloSeleccionado = vuelo;
        lbPrecio.setText(String.format("$%.2f", vuelo.getCostoBoleto()));
        cargarClientes();
        cargarAsientosDisponibles();
    }
    
    private void cargarClientes() {
        try {
            cbCliente.getItems().setAll(clienteDAO.obtenerTodos());

            // Usar un StringConverter para mostrar el nombre del cliente en el ComboBox
            cbCliente.setConverter(new StringConverter<Cliente>() {
                @Override
                public String toString(Cliente cliente) {
                    return cliente == null ? "" : cliente.getNombre() + " " + cliente.getApellidoPaterno();
                }
                @Override
                public Cliente fromString(String string) {
                    return null; // No es necesario para un ComboBox no editable
                }
            });

        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Carga", "No se pudieron cargar los clientes.");
        }
    }
    
    private void regresarVentanaBoletos(){
        try {
            Stage escenarioBase = (Stage) lbPrecio.getScene().getWindow();
            FXMLLoader cargador = new FXMLLoader(JavaFXSistemaAerolineaUniAir.class.getResource("vista/FXMLBoleto.fxml"));
            Parent vista = cargador.load();
            
            Scene escenaPrincipal = new Scene(vista);
            escenarioBase.setScene(escenaPrincipal);
            escenarioBase.setTitle("Vuelos disponibles");
            escenarioBase.show();
        } catch (IOException ex) {
            Logger.getLogger(FXMLAvionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargarAsientosDisponibles() {
        try {
            // 1. Obtener el avión del vuelo para saber la capacidad y distribución
            Avion avion = avionDAO.buscarPorId(vueloSeleccionado.getIdAvion());
            if (avion == null) {
                Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Carga", "No se pudo cargar el avion asociado al vuelo.");
                return;
            }

            // 2. Generar todos los asientos posibles para ese avión (ej. "1A", "1B", ...)
            // Asumiré una distribución simple de 6 asientos por fila (ABC-DEF)
            List<String> todosLosAsientos = new ArrayList<>();
            int filas = avion.getCapacidad() / 6; // Asumiendo que `getCapacidad` existe en Avion
            for (int i = 1; i <= filas; i++) {
                todosLosAsientos.add(i + "A");
                todosLosAsientos.add(i + "B");
                todosLosAsientos.add(i + "C");
                todosLosAsientos.add(i + "D");
                todosLosAsientos.add(i + "E");
                todosLosAsientos.add(i + "F");
            }

            // 3. Obtener los asientos ya ocupados para ESTE vuelo
            List<String> asientosOcupados = boletoDAO.obtenerTodos().stream()
                .filter(b -> b.getIdVuelo() == vueloSeleccionado.getIdVuelo())
                .map(Boleto::getAsiento)
                .collect(Collectors.toList());

            // 4. Filtrar los asientos disponibles
            todosLosAsientos.removeAll(asientosOcupados);
            cbAsiento.getItems().setAll(todosLosAsientos);

        } catch (IOException e) {
            Util.mostrarAlertaSimple(Alert.AlertType.ERROR, "Error de Carga", "No se pudieron cargar los boletos.");
        }
    }

    private void generarPdfBoleto(Boleto boleto) throws IOException, DocumentException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Guardar Pase de Abordar");
        fileChooser.setInitialFileName("Boleto-" + boleto.getNombreCliente().replace(" ", "") + "-" + boleto.getIdVuelo() + ".pdf");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Archivos PDF", "*.pdf"));
        File archivo = fileChooser.showSaveDialog(null);

        if (archivo != null) {
            Document documento = new Document();
            PdfWriter.getInstance(documento, new FileOutputStream(archivo));
            documento.open();
            
            try {
                // Cargar la imagen del logo desde los recursos.
               
                InputStream logoStream = getClass().getResourceAsStream("/javafxsistemaaerolineauniair/recursos/UniAir_Logo_Transparente.png");
                
                if (logoStream != null) {
                    // Convertir InputStream a byte array, que es lo que iText prefiere
                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    byte[] data = new byte[1024];
                    while ((nRead = logoStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    byte[] imageBytes = buffer.toByteArray();
                    
                    Image logo = Image.getInstance(imageBytes);
                    
                    // Escalar la imagen a un tamaño adecuado
                    logo.scaleToFit(120, 60);

                    // 3. Crear una tabla para alinear el logo y el título
                    PdfPTable headerTable = new PdfPTable(2);
                    headerTable.setWidthPercentage(100);
                    headerTable.setWidths(new float[]{1f, 3f}); // El logo ocupa 1/4 y el título 3/4 del ancho
                    headerTable.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
                    
                    // Celda para el logo
                    PdfPCell logoCell = new PdfPCell(logo);
                    logoCell.setBorder(PdfPCell.NO_BORDER);
                    logoCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    headerTable.addCell(logoCell);
                    
                    // Celda para el título
                    Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
                    Paragraph titulo = new Paragraph("Pase de Abordar", fontTitulo);
                    titulo.setAlignment(Element.ALIGN_RIGHT);
                    PdfPCell titleCell = new PdfPCell(titulo);
                    titleCell.setBorder(PdfPCell.NO_BORDER);
                    titleCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    titleCell.setPaddingRight(10);
                    headerTable.addCell(titleCell);
                    
                    documento.add(headerTable);
                } else {
                    // Si no se encuentra el logo, muestra el título para evitar un error.
                    Font fontTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
                    Paragraph titulo = new Paragraph("Pase de Abordar - UniAir", fontTitulo);
                    titulo.setAlignment(Element.ALIGN_CENTER);
                    documento.add(titulo);
                }
            } catch (DocumentException | IOException e) {
                System.err.println("Error al cargar el logo. Se continuará sin él.");
                e.printStackTrace();
            }
            
            // --- Fin de la sección del logo ---

            documento.add(new Paragraph("\n")); // Espacio después del encabezado
            
            // El resto del código para la información del boleto permanece igual
            Font fontSubtitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font fontDato = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            PdfPTable tabla = new PdfPTable(2);
            tabla.setWidthPercentage(100);
            tabla.setWidths(new float[]{1, 3});
            
            agregarCelda(tabla, "Pasajero:", fontNormal);
            agregarCelda(tabla, boleto.getNombreCliente(), fontDato);

            agregarCelda(tabla, "Vuelo:", fontNormal);
            agregarCelda(tabla, String.valueOf(boleto.getIdVuelo()), fontDato);

            agregarCelda(tabla, "Origen:", fontNormal);
            agregarCelda(tabla, boleto.getCiudadSalida(), fontDato);

            agregarCelda(tabla, "Destino:", fontNormal);
            agregarCelda(tabla, boleto.getCiudadLlegada(), fontDato);
            
            agregarCelda(tabla, "Fecha Salida:", fontNormal);
            agregarCelda(tabla, boleto.getFechaSalida().toString() + " " + boleto.getHoraSalida().toString(), fontDato);
            
            agregarCelda(tabla, "Asiento:", fontSubtitulo);
            agregarCelda(tabla, boleto.getAsiento(), fontSubtitulo);
            
            documento.add(tabla);

            documento.add(new Paragraph("\n\n¡Gracias por volar con UniAir!", fontNormal));
            
            documento.close();
        }
    }
    
    // Método de utilidad para añadir celdas a la tabla del PDF
    private void agregarCelda(PdfPTable tabla, String texto, Font fuente) {
        PdfPCell celda = new PdfPCell(new Phrase(texto, fuente));
        celda.setBorder(PdfPCell.NO_BORDER);
        celda.setPadding(5);
        tabla.addCell(celda);
    }
    
}

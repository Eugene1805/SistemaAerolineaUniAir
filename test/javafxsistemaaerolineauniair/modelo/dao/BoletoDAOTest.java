package javafxsistemaaerolineauniair.modelo.dao;

import javafxsistemaaerolineauniair.excepciones.VueloLlenoException;
import javafxsistemaaerolineauniair.modelo.pojo.Avion;
import javafxsistemaaerolineauniair.modelo.pojo.Boleto;
import javafxsistemaaerolineauniair.modelo.pojo.Vuelo;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author uriel
 */
public class BoletoDAOTest {
    
    private BoletoDAO boletoDAO;
    private VueloDAO vueloDAO;
    private AvionDAO avionDAO;
    private List<Boleto> boletosPrueba = new ArrayList<>();
    private int idAvionPrueba;
    private int idVueloPrueba;

    @Before
    public void setUp() throws Exception {
        boletoDAO = new BoletoDAO();
        vueloDAO = new VueloDAO();
        avionDAO = new AvionDAO();
        
        // Crear datos de prueba relacionados
        idAvionPrueba = avionDAO.generarIdUnico();
        idVueloPrueba = vueloDAO.generarIdVueloUnico();
        
        // Configurar avión de prueba
        Avion avionPrueba = new Avion();
        avionPrueba.setIdAvion(idAvionPrueba);
        avionPrueba.setModelo("Boeing 737");
        avionPrueba.setCapacidad(150);
        avionPrueba.setFechaDeIngreso(LocalDate.now());
        avionPrueba.setEstatus("Activo");
        avionPrueba.setPeso(50000);
        avionPrueba.setIdAerolinea(1);
        avionDAO.agregar(avionPrueba);
        
        // Configurar vuelo de prueba
        Vuelo vueloPrueba = new Vuelo();
        vueloPrueba.setIdVuelo(idVueloPrueba);
        vueloPrueba.setIdAvion(idAvionPrueba);
        vueloDAO.agregar(vueloPrueba);
    }

    @After
    public void limpiarDatos() throws Exception {
        // Limpiar boletos de prueba usando obtenerTodos() y guardarTodos()
        List<Boleto> boletosActuales = boletoDAO.obtenerTodos();
        List<Boleto> boletosFiltrados = new ArrayList<>();
        for (Boleto boleto : boletosActuales) {
            boolean esDePrueba = boletosPrueba.stream().anyMatch(b -> 
                b.getIdVuelo() == boleto.getIdVuelo() &&
                b.getNombreCliente().equals(boleto.getNombreCliente()) &&
                b.getAsiento().equals(boleto.getAsiento())
            );
            if (!esDePrueba) {
                boletosFiltrados.add(boleto);
            }
        }
        boletoDAO.guardarTodos(boletosFiltrados);
        
        // Limpiar datos relacionados de prueba
        vueloDAO.eliminar(idVueloPrueba);
        avionDAO.eliminar(idAvionPrueba);
    }

    private Boleto crearBoletoPrueba() {
        Boleto boleto = new Boleto();
        boleto.setIdVuelo(idVueloPrueba);
        boleto.setNumPasajeros(1);
        boleto.setCiudadSalida("Ciudad A");
        boleto.setCiudadLlegada("Ciudad B");
        boleto.setFechaSalida(LocalDate.now());
        boleto.setHoraSalida(LocalTime.now());
        boleto.setFechaLlegada(LocalDate.now().plusDays(1));
        boleto.setHoraLlegada(LocalTime.of(10, 30));
        boleto.setTiempoRecorrido(120);
        boleto.setCostoBoleto(1500.50);
        boleto.setIdAvion(idAvionPrueba);
        boleto.setModeloAvion("Boeing 737");
        boleto.setIdCliente(1001);
        boleto.setNombreCliente("Juan Pérez");
        boleto.setAsiento("A12");
        
        return boleto;
    }

    @Test
    public void testObtenerNombresColumnas() {
        String[] expResultado = {
            "ID Vuelo", "Pasajeros", "Ciudad Salida", "Ciudad Llegada", 
            "Fecha Salida", "Hora Salida", "Fecha Llegada", "Hora Llegada",
            "Tiempo Recorrido (hrs)", "Costo Boleto", "Avion", "Cliente", "Asiento"
        };
        String[] resultado = boletoDAO.obtenerNombresColumnas();
        assertArrayEquals(expResultado, resultado);
    }

    @Test
    public void testObtenerValoresFila() throws IOException {
        Boleto boleto = crearBoletoPrueba();
        
        String[] resultado = boletoDAO.obtenerValoresFila(boleto);
        
        assertNotNull(resultado);
        assertEquals(String.valueOf(idVueloPrueba), resultado[0]);
        assertEquals("1", resultado[1]);
        assertEquals("Ciudad A", resultado[2]);
        assertEquals("Ciudad B", resultado[3]);
        assertTrue(resultado[10].contains("Boeing 737")); // Verifica parte del string del avión
    }

    @Test
    public void testRegistrarCompraBoletoExitoso() throws Exception {
        Boleto boleto = crearBoletoPrueba();
        boletoDAO.registrarCompraBoleto(boleto);
        boletosPrueba.add(boleto);
        
        // Verificar que el boleto se agregó buscando en todos los boletos
        List<Boleto> boletos = boletoDAO.obtenerTodos();
        boolean encontrado = boletos.stream()
                .anyMatch(b -> b.getIdVuelo() == boleto.getIdVuelo() && 
                              b.getNombreCliente().equals(boleto.getNombreCliente()));
        assertTrue(encontrado);
    }

    @Test(expected = VueloLlenoException.class)
    public void testRegistrarCompraBoletoVueloLleno() throws Exception {
        // Configurar avión con capacidad 1
        Avion avion = avionDAO.buscarPorId(idAvionPrueba);
        avion.setCapacidad(1);
        avionDAO.actualizar(avion);
        
        // Registrar primer boleto (debería funcionar)
        Boleto boleto1 = crearBoletoPrueba();
        boletoDAO.registrarCompraBoleto(boleto1);
        boletosPrueba.add(boleto1);
        
        // Intentar registrar segundo boleto (debería fallar)
        Boleto boleto2 = crearBoletoPrueba();
        boletoDAO.registrarCompraBoleto(boleto2);
    }

    @Test(expected = IOException.class)
    public void testRegistrarCompraBoletoVueloInexistente() throws Exception {
        Boleto boleto = crearBoletoPrueba();
        boleto.setIdVuelo(99999); // ID que no existe
        boletoDAO.registrarCompraBoleto(boleto);
    }

    @Test(expected = IOException.class)
    public void testRegistrarCompraBoletoAvionInexistente() throws Exception {
        // Crear vuelo con avión que no existe
        Vuelo vuelo = new Vuelo();
        int idVueloFalso = vueloDAO.generarIdVueloUnico();
        vuelo.setIdVuelo(idVueloFalso);
        vuelo.setIdAvion(99999); // ID que no existe
        vueloDAO.agregar(vuelo);
        
        Boleto boleto = crearBoletoPrueba();
        boleto.setIdVuelo(idVueloFalso);
        boletoDAO.registrarCompraBoleto(boleto);
        
        // Limpiar
        vueloDAO.eliminar(idVueloFalso);
    }
}
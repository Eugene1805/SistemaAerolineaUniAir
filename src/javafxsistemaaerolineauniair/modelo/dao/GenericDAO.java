package javafxsistemaaerolineauniair.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.opencsv.CSVWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.Reader;
import java.io.Writer;
import java.io.IOException;
import java.lang.reflect.Type;


/**
 *
 * @author eugen
 */
public abstract class GenericDAO<T> {
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final File archivoJson;
    private final Type tipoLista;
    
    public GenericDAO(String rutaArchivo, Class<T> tipoClase) {
        this.archivoJson = new File(rutaArchivo);
        this.tipoLista = TypeToken.getParameterized(List.class, tipoClase).getType();
        inicializarArchivo();
    }
    
    private void inicializarArchivo() {
        if (!archivoJson.exists()) {
            try {
                guardarTodos(new ArrayList<>());
            } catch (IOException e) {
                throw new RuntimeException("Error al inicializar archivo JSON", e);
            }
        }
    }
    
    // Operaciones CRUD básicas
    public List<T> obtenerTodos() throws IOException {
        try (Reader reader = new FileReader(archivoJson)) {
            List<T> items = gson.fromJson(reader, tipoLista);
            return items != null ? items : new ArrayList<>();
        }
    }
    
    public void guardarTodos(List<T> items) throws IOException {
        try (Writer writer = new FileWriter(archivoJson)) {
            gson.toJson(items, writer);
        }
    }
    
    public void agregar(T item) throws IOException {
        List<T> items = obtenerTodos();
        items.add(item);
        guardarTodos(items);
    }
    
    // Métodos de exportación genéricos
    public void exportarAArchivo(File archivoDestino) throws IOException, DocumentException {
        List<T> datos = obtenerTodos();
        String jsonContent = gson.toJson(datos);
        
        String extension = archivoDestino.getName().substring(
            archivoDestino.getName().lastIndexOf(".")).toLowerCase();
            
        switch (extension) {
            case ".csv": exportarACsv(jsonContent, archivoDestino);
            case ".xlsx": exportarAExcel(jsonContent, archivoDestino, true);
            case ".xls": exportarAExcel(jsonContent, archivoDestino, false);
            case ".pdf": exportarAPdf(jsonContent, archivoDestino);
            default: throw new IllegalArgumentException("Formato no soportado");
        }
    }
    
    protected abstract String[] obtenerNombresColumnas();
    protected abstract String[] obtenerValoresFila(T item);
    
    private void exportarACsv(String jsonContent, File outputFile) throws IOException {
        List<T> data = gson.fromJson(jsonContent, tipoLista);
        
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputFile))) {
            writer.writeNext(obtenerNombresColumnas());
            
            for (T item : data) {
                writer.writeNext(obtenerValoresFila(item));
            }
        }
    }
    
    private void exportarAExcel(String jsonContent, File outputFile, boolean isXlsx) throws IOException {
        List<T> data = gson.fromJson(jsonContent, tipoLista);
        String[] headers = obtenerNombresColumnas();
        
        try (Workbook workbook = isXlsx ? new XSSFWorkbook() : new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Datos");
            
            // Estilo para encabezados
            CellStyle headerStyle = workbook.createCellStyle();
            org.apache.poi.ss.usermodel.Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);
            
            // Crear fila de encabezados
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
                sheet.autoSizeColumn(i);
            }
            
            // Llenar datos
            int rowNum = 1;
            for (T item : data) {
                Row row = sheet.createRow(rowNum++);
                String[] valores = obtenerValoresFila(item);
                
                for (int i = 0; i < valores.length; i++) {
                    Cell cell = row.createCell(i);
                    
                    // Intentar detectar números para formato adecuado
                    try {
                        double numValue = Double.parseDouble(valores[i]);
                        cell.setCellValue(numValue);
                    } catch (NumberFormatException e) {
                        cell.setCellValue(valores[i]);
                    }
                }
            }
            
            // Autoajustar columnas
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            // Guardar archivo
            try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                workbook.write(outputStream);
            }
        }
    }
    
    // Implementación para PDF
    private void exportarAPdf(String jsonContent, File outputFile) throws IOException, DocumentException {
        List<T> data = gson.fromJson(jsonContent, tipoLista);
        String[] headers = obtenerNombresColumnas();
        
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        
        // Título del documento
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Reporte Exportado", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20f);
        document.add(title);
        
        // Crear tabla
        PdfPTable table = new PdfPTable(headers.length);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        
        // Configurar anchos de columna (opcional)
        // float[] columnWidths = new float[]{2f, 3f, 3f, 2f};
        // table.setWidths(columnWidths);
        
        // Añadir encabezados
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(new BaseColor(200, 200, 200));
            table.addCell(cell);
        }
        
        // Añadir datos
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        for (T item : data) {
            String[] valores = obtenerValoresFila(item);
            for (String valor : valores) {
                PdfPCell cell = new PdfPCell(new Phrase(valor, dataFont));
                cell.setPadding(5f);
                table.addCell(cell);
            }
        }
        
        document.add(table);
        
        // Pie de página
        Font footerFont = FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 10);
        Paragraph footer = new Paragraph("Generado el: " + new java.util.Date(), footerFont);
        footer.setAlignment(Element.ALIGN_RIGHT);
        document.add(footer);
        
        document.close();
    }
}

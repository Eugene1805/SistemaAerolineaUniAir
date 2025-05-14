package com.mycompany.sistemaaerolineauniair.modelo.dao;

import java.io.IOException;
import com.mycompany.sistemaaerolineauniair.modelo.pojos.Aeropuerto;
import javafx.collections.ObservableList;

/**
 *
 * @author eugen
 */
public interface AeropuertoDAO {
    public void create(Aeropuerto aeropuerto) throws IOException;
    public Aeropuerto read(Integer id) throws IOException;
    public void update(Aeropuerto aeropuerto) throws IOException;
    public void delete (Integer id) throws IOException;
    public ObservableList<Aeropuerto> readAll() throws IOException;
    public void exportInformation(String extension) throws IOException;
}

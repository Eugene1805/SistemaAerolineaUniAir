/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import java.io.IOException;
import com.mycompany.sistemaaerolineauniair.modelo.pojos.Aeropuerto;
import java.util.List;
/**
 *
 * @author eugen
 */
public interface AeropuertoDAO {
    public void create(Aeropuerto aeropuerto) throws IOException;
    public Aeropuerto read(Integer id) throws IOException;
    public void update(Aeropuerto aeropuesto) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<Aeropuerto> readAll() throws IOException;
    public void exportInformation() throws IOException;
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.Vuelo;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eugen
 */
public interface VueloDAO {
    public void create(Vuelo vuelo) throws IOException;
    public Vuelo read(Integer id) throws IOException;
    public void update(Vuelo vuelo) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<Vuelo> readAll() throws IOException;
    public void exportInformation() throws IOException;
}

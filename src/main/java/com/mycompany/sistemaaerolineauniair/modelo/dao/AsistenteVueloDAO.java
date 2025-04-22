/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.AsistenteVuelo;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eugen
 */
public interface AsistenteVueloDAO {
    public void create(AsistenteVuelo asistente) throws IOException;
    public AsistenteVuelo read(Integer id) throws IOException;
    public void update(AsistenteVuelo asistente) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<AsistenteVuelo> readAll() throws IOException;
    public void exportInformation() throws IOException;
}
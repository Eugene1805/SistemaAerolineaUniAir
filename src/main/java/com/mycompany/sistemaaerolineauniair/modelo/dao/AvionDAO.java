/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.Avion;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eugen
 */
public interface AvionDAO {
    public void create(Avion avion) throws IOException;
    public Avion read(Integer id) throws IOException;
    public void update(Avion avion) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<Avion> readAll() throws IOException;
    public void exportInformation() throws IOException;
}

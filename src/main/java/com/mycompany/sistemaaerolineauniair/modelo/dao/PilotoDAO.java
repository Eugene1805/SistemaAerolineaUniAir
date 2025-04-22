/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.Piloto;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eugen
 */
public interface PilotoDAO {
    public void create(Piloto piloto) throws IOException;
    public Piloto read(Integer id) throws IOException;
    public void update(Piloto piloto) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<Piloto> readAll() throws IOException;
    public void exportInformation() throws IOException;
}

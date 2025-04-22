/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.Boleto;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eugen
 */
public interface BoletoDAO {
    public void create(Boleto boleto) throws IOException;
    public Boleto read(Integer id) throws IOException;
    public void update(Boleto boleto) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<Boleto> readAll() throws IOException;
    public void exportInformation() throws IOException;
}

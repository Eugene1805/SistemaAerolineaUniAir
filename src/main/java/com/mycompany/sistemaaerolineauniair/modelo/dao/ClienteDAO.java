/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.Cliente;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eugen
 */
public interface ClienteDAO {
    public void create(Cliente cliente) throws IOException;
    public Cliente read(Integer id) throws IOException;
    public void update(Cliente cliente) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<Cliente> readAll() throws IOException;
    public void exportInformation() throws IOException;
}

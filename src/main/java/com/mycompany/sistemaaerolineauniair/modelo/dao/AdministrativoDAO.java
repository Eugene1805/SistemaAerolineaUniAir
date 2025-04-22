/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.mycompany.sistemaaerolineauniair.modelo.pojos.Administrativo;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author eugen
 */
public interface AdministrativoDAO {
    public void create(Administrativo administrativo) throws IOException;
    public Administrativo read(Integer id) throws IOException;
    public void update(Administrativo administrativo) throws IOException;
    public void delete (Integer id) throws IOException;
    public List<Administrativo> readAll() throws IOException;
    public void exportInformation() throws IOException;
}

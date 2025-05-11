package com.mycompany.sistemaaerolineauniair.modelo.dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.mycompany.sistemaaerolineauniair.modelo.pojos.Aeropuerto;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Paths;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eugen
 */
public class AeropuertoDAOImp implements AeropuertoDAO{
    
    private static final File FILE_PATH = Paths.get("data", "aeropuertos.json").toFile();
    private Gson gson;

    public AeropuertoDAOImp() {
        this.gson = new GsonBuilder().setPrettyPrinting().create();
        new File("data").mkdirs();
    }
    
    @Override
    public void create(Aeropuerto aeropuerto) throws IOException {
        List<Aeropuerto> aeropuertos = readAll();
        boolean existe = false;
        
        for (int i = 0; i < aeropuertos.size(); i++) {
            if (aeropuertos.get(i).getNombre().equalsIgnoreCase(aeropuerto.getNombre())) {
                aeropuertos.set(i, aeropuerto);
                existe = true;
                break;
            }
        }
        
        if (!existe) {
            aeropuertos.add(aeropuerto);
        }
        
        guardarTodos(aeropuertos);
    }

    @Override
    public Aeropuerto read(Integer id) throws IOException {
        List<Aeropuerto> aeropuertos = readAll();

        for (int i = 0; i < aeropuertos.size(); i++) {
            if (id.equals(aeropuertos.get(i).getId())) {
                return aeropuertos.get(i);
            }
        }
        return null;
    }

    @Override
    public void update(Aeropuerto aeropuerto) throws IOException {
        //TODO
    }

    @Override
    public void delete(Integer id) throws IOException {
        List<Aeropuerto> recetas = readAll();
        recetas.removeIf(r -> id.equals(r.getId()));
        guardarTodos(recetas);
    }

    @Override
    public List<Aeropuerto> readAll() throws IOException {
        try (Reader reader = new FileReader(FILE_PATH)) {
            Type listType = new TypeToken<ArrayList<Aeropuerto>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo JSON", e);
        }
    }

    @Override
    public void exportInformation() throws IOException {
        //TODO
    }
    
    private void guardarTodos(List<Aeropuerto> aeropuertos) {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(aeropuertos, writer);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar en el archivo JSON", e);
        }
    } 
    
}

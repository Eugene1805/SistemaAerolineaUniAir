/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package javafxsistemaaerolineauniair.interfaz;

import java.util.List;


/**
 *
 * @author meler
 */
public interface CRUDEmpleado<T> {
    
    boolean registrar(T objeto);
    boolean actualizar(T objeto);
    boolean eliminar(int id);  // puede ser el noPersonal
    T obtenerPorId(int id);
    List<T> obtenerTodos();
    
}

package com.mycompany.proyectosupermecado.dao;

import com.mycompany.proyectosupermecado.modelo.Empleado;
import java.util.List;

/**
 * Interfaz DAO para operaciones CRUD de empleados
 */
public interface EmpleadoDAO {
    
    /**
     * Insertar un nuevo empleado en la base de datos
     * @param empleado Objeto empleado a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    boolean insertar(Empleado empleado);
    
    /**
     * Actualizar un empleado existente
     * @param empleado Objeto empleado con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizar(Empleado empleado);
    
    /**
     * Eliminar un empleado (baja lógica, cambia activo a false)
     * @param dni DNI del empleado a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminar(String dni);
    
    /**
     * Buscar un empleado por DNI
     * @param dni DNI del empleado a buscar
     * @return Objeto Empleado si se encuentra, null en caso contrario
     */
    Empleado buscarPorDni(String dni);
    
    /**
     * Buscar un empleado por ID
     * @param id ID del empleado a buscar
     * @return Objeto Empleado si se encuentra, null en caso contrario
     */
    Empleado buscarPorId(int id);
    
    /**
     * Listar todos los empleados activos
     * @return Lista de empleados activos
     */
    List<Empleado> listarTodos();
    
    /**
     * Listar todos los empleados (activos e inactivos)
     * @return Lista de todos los empleados
     */
    List<Empleado> listarTodosConInactivos();
    
    /**
     * Verificar si un DNI ya existe en la base de datos
     * @param dni DNI a verificar
     * @return true si existe, false si no existe
     */
    boolean existeDni(String dni);
    
    /**
     * Verificar si un ID ya existe en la base de datos
     * @param id ID a verificar
     * @return true si existe, false si no existe
     */
    boolean existeId(int id);
}

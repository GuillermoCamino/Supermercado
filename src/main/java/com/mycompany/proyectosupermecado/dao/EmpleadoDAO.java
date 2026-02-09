package com.mycompany.proyectosupermecado.dao;

import com.mycompany.proyectosupermecado.modelo.Empleado;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Empleado
 * @author Supermercado Team
 */
public interface EmpleadoDAO {
    
    /**
     * Inserta un nuevo empleado en la base de datos
     * @param empleado objeto Empleado a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    boolean insertar(Empleado empleado);
    
    /**
     * Actualiza los datos de un empleado existente
     * @param empleado objeto Empleado con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizar(Empleado empleado);
    
    /**
     * Elimina un empleado de la base de datos (eliminación lógica)
     * @param dni DNI del empleado a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminar(String dni);
    
    /**
     * Elimina físicamente un empleado de la base de datos
     * @param dni DNI del empleado a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminarFisico(String dni);
    
    /**
     * Busca un empleado por su DNI
     * @param dni DNI del empleado a buscar
     * @return objeto Empleado si se encuentra, null en caso contrario
     */
    Empleado buscarPorDni(String dni);
    
    /**
     * Busca un empleado por su ID
     * @param id ID del empleado a buscar
     * @return objeto Empleado si se encuentra, null en caso contrario
     */
    Empleado buscarPorId(int id);
    
    /**
     * Obtiene todos los empleados activos de la base de datos
     * @return Lista de empleados activos
     */
    List<Empleado> listarTodos();
    
    /**
     * Obtiene todos los empleados (activos e inactivos)
     * @return Lista de todos los empleados
     */
    List<Empleado> listarTodosConInactivos();
    
    /**
     * Busca empleados por nombre (búsqueda parcial)
     * @param criterio texto a buscar en el nombre
     * @return Lista de empleados que coinciden con el criterio
     */
    List<Empleado> buscarPorNombre(String criterio);
    
    /**
     * Actualiza solo el nombre de un empleado
     * @param dni DNI del empleado
     * @param nuevoNombre nuevo nombre del empleado
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizarNombre(String dni, String nuevoNombre);
    
    /**
     * Actualiza solo el ID de un empleado
     * @param dni DNI del empleado
     * @param nuevoId nuevo ID del empleado
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizarId(String dni, int nuevoId);
    
    /**
     * Verifica si existe un empleado con el DNI especificado
     * @param dni DNI a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorDni(String dni);
    
    /**
     * Verifica si existe un empleado con el ID especificado
     * @param id ID a verificar
     * @return true si existe, false en caso contrario
     */
    boolean existePorId(int id);
}

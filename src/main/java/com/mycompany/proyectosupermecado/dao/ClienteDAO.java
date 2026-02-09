package com.mycompany.proyectosupermecado.dao;

import com.mycompany.proyectosupermecado.modelo.Cliente;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Cliente
 * @author Supermercado Team
 */
public interface ClienteDAO {
    
    /**
     * Inserta un nuevo cliente en la base de datos
     * @param cliente objeto Cliente a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    boolean insertar(Cliente cliente);
    
    /**
     * Actualiza los datos de un cliente existente
     * @param cliente objeto Cliente con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizar(Cliente cliente);
    
    /**
     * Elimina un cliente de la base de datos (eliminación lógica)
     * @param idCliente ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminar(int idCliente);
    
    /**
     * Elimina físicamente un cliente de la base de datos
     * @param idCliente ID del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminarFisico(int idCliente);
    
    /**
     * Busca un cliente por su ID
     * @param idCliente ID del cliente a buscar
     * @return objeto Cliente si se encuentra, null en caso contrario
     */
    Cliente buscarPorId(int idCliente);
    
    /**
     * Busca un cliente por su DNI
     * @param dni DNI del cliente a buscar
     * @return objeto Cliente si se encuentra, null en caso contrario
     */
    Cliente buscarPorDni(String dni);
    
    /**
     * Obtiene todos los clientes activos de la base de datos
     * @return Lista de clientes activos
     */
    List<Cliente> listarTodos();
    
    /**
     * Obtiene todos los clientes (activos e inactivos)
     * @return Lista de todos los clientes
     */
    List<Cliente> listarTodosConInactivos();
    
    /**
     * Busca clientes por nombre o apellido (búsqueda parcial)
     * @param criterio texto a buscar en nombre o apellido
     * @return Lista de clientes que coinciden con el criterio
     */
    List<Cliente> buscarPorNombre(String criterio);
    
    /**
     * Actualiza los puntos acumulados de un cliente
     * @param idCliente ID del cliente
     * @param puntos puntos a agregar (pueden ser negativos para restar)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizarPuntos(int idCliente, int puntos);
    
    /**
     * Obtiene las compras realizadas por un cliente
     * @param idCliente ID del cliente
     * @return Lista con el historial de compras (implementar según necesidad)
     */
    // List<Venta> obtenerHistorialCompras(int idCliente);
}

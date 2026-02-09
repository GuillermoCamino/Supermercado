package com.mycompany.proyectosupermecado.dao;

import com.mycompany.proyectosupermecado.modelo.Producto;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD para la entidad Producto
 * @author Supermercado Team
 */
public interface ProductoDAO {
    
    /**
     * Inserta un nuevo producto en la base de datos
     * @param producto objeto Producto a insertar
     * @return true si se insertó correctamente, false en caso contrario
     */
    boolean insertar(Producto producto);
    
    /**
     * Actualiza los datos de un producto existente
     * @param producto objeto Producto con los datos actualizados
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizar(Producto producto);
    
    /**
     * Elimina un producto de la base de datos (eliminación lógica)
     * @param codigoReferencia código de referencia del producto a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminar(int codigoReferencia);
    
    /**
     * Elimina físicamente un producto de la base de datos
     * @param codigoReferencia código de referencia del producto a eliminar
     * @return true si se eliminó correctamente, false en caso contrario
     */
    boolean eliminarFisico(int codigoReferencia);
    
    /**
     * Busca un producto por su código de referencia
     * @param codigoReferencia código de referencia del producto a buscar
     * @return objeto Producto si se encuentra, null en caso contrario
     */
    Producto buscarPorCodigo(int codigoReferencia);
    
    /**
     * Obtiene todos los productos activos de la base de datos
     * @return Lista de productos activos
     */
    List<Producto> listarTodos();
    
    /**
     * Obtiene todos los productos (activos e inactivos)
     * @return Lista de todos los productos
     */
    List<Producto> listarTodosConInactivos();
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     * @param criterio texto a buscar en el nombre
     * @return Lista de productos que coinciden con el criterio
     */
    List<Producto> buscarPorNombre(String criterio);
    
    /**
     * Obtiene todos los productos con restricción de edad
     * @return Lista de productos con restricción de edad
     */
    List<Producto> listarConRestriccionEdad();
    
    /**
     * Obtiene todos los productos sin restricción de edad
     * @return Lista de productos sin restricción de edad
     */
    List<Producto> listarSinRestriccionEdad();
    
    /**
     * Actualiza el stock de un producto
     * @param codigoReferencia código de referencia del producto
     * @param cantidad cantidad a agregar o restar del stock (positivo suma, negativo resta)
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizarStock(int codigoReferencia, int cantidad);
    
    /**
     * Obtiene productos con stock bajo (menos de un umbral especificado)
     * @param umbral cantidad mínima de stock
     * @return Lista de productos con stock bajo
     */
    List<Producto> listarConStockBajo(int umbral);
    
    /**
     * Actualiza el precio de un producto
     * @param codigoReferencia código de referencia del producto
     * @param nuevoPrecio nuevo precio del producto
     * @return true si se actualizó correctamente, false en caso contrario
     */
    boolean actualizarPrecio(int codigoReferencia, double nuevoPrecio);
}

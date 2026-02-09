package com.mycompany.proyectosupermecado.servicio;

import com.mycompany.proyectosupermecado.dao.ProductoDAO;
import com.mycompany.proyectosupermecado.dao.impl.ProductoDAOImpl;
import com.mycompany.proyectosupermecado.modelo.Producto;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase de servicio que gestiona la lógica de negocio de Productos
 * Actúa como intermediario entre la interfaz gráfica y la capa DAO
 * @author Supermercado Team
 */
public class ProductoServicio {
    
    private ProductoDAO productoDAO;
    
    public ProductoServicio() {
        this.productoDAO = new ProductoDAOImpl();
    }
    
    /**
     * Registra un nuevo producto en el sistema
     * @param codigoReferencia Código de referencia del producto (8 dígitos)
     * @param nombre Nombre del producto
     * @param precio Precio del producto
     * @param restriccionEdad Si tiene restricción de edad (alcohol)
     * @param stock Stock inicial del producto
     * @param imagen Ruta de la imagen del producto
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarProducto(int codigoReferencia, String nombre, double precio, 
                                    boolean restriccionEdad, int stock, String imagen) {
        // Validaciones
        if (!validarDatos(codigoReferencia, nombre, precio)) {
            return false;
        }
        
        // Verificar si el código de referencia ya existe
        if (productoDAO.buscarPorCodigo(codigoReferencia) != null) {
            JOptionPane.showMessageDialog(null, 
                "Ya existe un producto registrado con el código: " + codigoReferencia, 
                "Código Duplicado", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Crear el objeto Producto
        Producto producto = new Producto();
        producto.setCodigoReferencia(codigoReferencia);
        producto.setNombre(nombre.trim());
        producto.setPrecio(precio);
        producto.setRestriccionEdad(restriccionEdad);
        producto.setStock(stock);
        producto.setImagen(imagen);
        producto.setActivo(true);
        
        // Insertar en la base de datos
        boolean resultado = productoDAO.insertar(producto);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Producto registrado exitosamente\nNombre: " + nombre + "\nCódigo: " + codigoReferencia, 
                "Registro Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al registrar el producto. Intente nuevamente.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Modifica los datos de un producto existente
     * @param producto objeto Producto con los datos actualizados
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarProducto(Producto producto) {
        if (producto == null || producto.getCodigoReferencia() <= 0) {
            JOptionPane.showMessageDialog(null, 
                "Datos de producto inválidos", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar datos básicos
        if (!validarDatos(producto.getCodigoReferencia(), producto.getNombre(), producto.getPrecio())) {
            return false;
        }
        
        // Actualizar
        boolean resultado = productoDAO.actualizar(producto);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Producto modificado exitosamente", 
                "Modificación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al modificar el producto", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Da de baja (lógica) a un producto
     * @param codigoReferencia código de referencia del producto a dar de baja
     * @return true si se dio de baja correctamente, false en caso contrario
     */
    public boolean darDeBajaProducto(int codigoReferencia) {
        // Confirmar acción
        int respuesta = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de dar de baja este producto?", 
            "Confirmar Baja", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            boolean resultado = productoDAO.eliminar(codigoReferencia);
            
            if (resultado) {
                JOptionPane.showMessageDialog(null, 
                    "Producto dado de baja exitosamente", 
                    "Baja Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al dar de baja el producto", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            return resultado;
        }
        return false;
    }
    
    /**
     * Busca un producto por su código de referencia
     * @param codigoReferencia código a buscar
     * @return objeto Producto si se encuentra, null en caso contrario
     */
    public Producto buscarPorCodigo(int codigoReferencia) {
        if (codigoReferencia <= 0) {
            JOptionPane.showMessageDialog(null, 
                "Debe ingresar un código de referencia válido para buscar", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        Producto producto = productoDAO.buscarPorCodigo(codigoReferencia);
        
        if (producto == null) {
            JOptionPane.showMessageDialog(null, 
                "No se encontró ningún producto con el código: " + codigoReferencia, 
                "Producto No Encontrado", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        return producto;
    }
    
    /**
     * Obtiene la lista de todos los productos activos
     * @return Lista de productos activos
     */
    public List<Producto> listarProductosActivos() {
        return productoDAO.listarTodos();
    }
    
    /**
     * Busca productos por nombre
     * @param criterio texto a buscar
     * @return Lista de productos que coinciden
     */
    public List<Producto> buscarPorNombre(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return listarProductosActivos();
        }
        return productoDAO.buscarPorNombre(criterio.trim());
    }
    
    /**
     * Obtiene productos con restricción de edad
     * @return Lista de productos con restricción de edad
     */
    public List<Producto> listarConRestriccionEdad() {
        return productoDAO.listarConRestriccionEdad();
    }
    
    /**
     * Obtiene productos sin restricción de edad
     * @return Lista de productos sin restricción de edad
     */
    public List<Producto> listarSinRestriccionEdad() {
        return productoDAO.listarSinRestriccionEdad();
    }
    
    /**
     * Agrega stock a un producto
     * @param codigoReferencia código del producto
     * @param cantidad cantidad de unidades a agregar
     * @return true si se agregaron correctamente, false en caso contrario
     */
    public boolean agregarStock(int codigoReferencia, int cantidad) {
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, 
                "La cantidad debe ser mayor a cero", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        boolean resultado = productoDAO.actualizarStock(codigoReferencia, cantidad);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Stock actualizado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Reduce stock de un producto (para ventas)
     * @param codigoReferencia código del producto
     * @param cantidad cantidad de unidades a reducir
     * @return true si se redujo correctamente, false en caso contrario
     */
    public boolean reducirStock(int codigoReferencia, int cantidad) {
        if (cantidad <= 0) {
            JOptionPane.showMessageDialog(null, 
                "La cantidad debe ser mayor a cero", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Verificar que el producto tenga suficiente stock
        Producto producto = productoDAO.buscarPorCodigo(codigoReferencia);
        if (producto == null) {
            JOptionPane.showMessageDialog(null, 
                "Producto no encontrado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (producto.getStock() < cantidad) {
            JOptionPane.showMessageDialog(null, 
                "Stock insuficiente. El producto tiene: " + producto.getStock() + " unidades", 
                "Stock Insuficiente", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return productoDAO.actualizarStock(codigoReferencia, -cantidad);
    }
    
    /**
     * Lista productos con stock bajo
     * @param umbral cantidad mínima de stock
     * @return Lista de productos con stock bajo
     */
    public List<Producto> listarConStockBajo(int umbral) {
        return productoDAO.listarConStockBajo(umbral);
    }
    
    /**
     * Actualiza el precio de un producto
     * @param codigoReferencia código del producto
     * @param nuevoPrecio nuevo precio
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarPrecio(int codigoReferencia, double nuevoPrecio) {
        if (nuevoPrecio <= 0) {
            JOptionPane.showMessageDialog(null, 
                "El precio debe ser mayor a cero", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        boolean resultado = productoDAO.actualizarPrecio(codigoReferencia, nuevoPrecio);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Precio actualizado correctamente", 
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Valida los datos básicos del producto
     * @param codigoReferencia código del producto
     * @param nombre nombre del producto
     * @param precio precio del producto
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatos(int codigoReferencia, String nombre, double precio) {
        // Validar código de referencia (debe ser un número de 8 dígitos)
        if (codigoReferencia < 10000000 || codigoReferencia > 99999999) {
            JOptionPane.showMessageDialog(null, 
                "El código de referencia debe ser un número de 8 dígitos", 
                "Código Inválido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El nombre del producto es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (precio <= 0) {
            JOptionPane.showMessageDialog(null, 
                "El precio debe ser mayor a cero", 
                "Precio Inválido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
}

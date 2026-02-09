package com.mycompany.proyectosupermecado.dao.impl;

import com.mycompany.proyectosupermecado.dao.ProductoDAO;
import com.mycompany.proyectosupermecado.modelo.Producto;
import com.mycompany.proyectosupermecado.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de Producto usando JDBC
 * @author Supermercado Team
 */
public class ProductoDAOImpl implements ProductoDAO {

    private ConexionBD conexionBD;

    public ProductoDAOImpl() {
        this.conexionBD = ConexionBD.getInstancia();
    }

    @Override
    public boolean insertar(Producto producto) {
        String sql = "INSERT INTO productos (codigo_referencia, nombre, precio, restriccion_edad, stock, imagen, fecha_registro, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, NOW(), ?)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, producto.getCodigoReferencia());
            pstmt.setString(2, producto.getNombre());
            pstmt.setDouble(3, producto.getPrecio());
            pstmt.setBoolean(4, producto.isRestriccionEdad());
            pstmt.setInt(5, producto.getStock());
            pstmt.setString(6, producto.getImagen());
            pstmt.setBoolean(7, producto.isActivo());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Producto insertado correctamente: " + producto.getCodigoReferencia());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, restriccion_edad = ?, " +
                     "stock = ?, imagen = ?, activo = ? " +
                     "WHERE codigo_referencia = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, producto.getNombre());
            pstmt.setDouble(2, producto.getPrecio());
            pstmt.setBoolean(3, producto.isRestriccionEdad());
            pstmt.setInt(4, producto.getStock());
            pstmt.setString(5, producto.getImagen());
            pstmt.setBoolean(6, producto.isActivo());
            pstmt.setInt(7, producto.getCodigoReferencia());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Producto actualizado correctamente: " + producto.getCodigoReferencia());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int codigoReferencia) {
        // Eliminación lógica (cambiar activo a false)
        String sql = "UPDATE productos SET activo = false WHERE codigo_referencia = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, codigoReferencia);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Producto dado de baja (lógica): " + codigoReferencia);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminarFisico(int codigoReferencia) {
        String sql = "DELETE FROM productos WHERE codigo_referencia = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, codigoReferencia);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Producto eliminado físicamente: " + codigoReferencia);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar físicamente producto: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Producto buscarPorCodigo(int codigoReferencia) {
        String sql = "SELECT * FROM productos WHERE codigo_referencia = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, codigoReferencia);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerProductoDeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto por código: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Producto> listarTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE activo = true ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(extraerProductoDeResultSet(rs));
            }
            System.out.println("Se encontraron " + productos.size() + " productos activos");
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Producto> listarTodosConInactivos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(extraerProductoDeResultSet(rs));
            }
            System.out.println("Se encontraron " + productos.size() + " productos (total)");
        } catch (SQLException e) {
            System.err.println("Error al listar todos los productos: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Producto> buscarPorNombre(String criterio) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE nombre LIKE ? AND activo = true ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String criterioBusqueda = "%" + criterio + "%";
            pstmt.setString(1, criterioBusqueda);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(extraerProductoDeResultSet(rs));
                }
            }
            System.out.println("Búsqueda '" + criterio + "': " + productos.size() + " resultados");
        } catch (SQLException e) {
            System.err.println("Error al buscar productos por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Producto> listarConRestriccionEdad() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE restriccion_edad = true AND activo = true ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(extraerProductoDeResultSet(rs));
            }
            System.out.println("Se encontraron " + productos.size() + " productos con restricción de edad");
        } catch (SQLException e) {
            System.err.println("Error al listar productos con restricción: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public List<Producto> listarSinRestriccionEdad() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE restriccion_edad = false AND activo = true ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                productos.add(extraerProductoDeResultSet(rs));
            }
            System.out.println("Se encontraron " + productos.size() + " productos sin restricción de edad");
        } catch (SQLException e) {
            System.err.println("Error al listar productos sin restricción: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public boolean actualizarStock(int codigoReferencia, int cantidad) {
        String sql = "UPDATE productos SET stock = stock + ? WHERE codigo_referencia = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, cantidad);
            pstmt.setInt(2, codigoReferencia);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Stock actualizado para producto: " + codigoReferencia);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar stock: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Producto> listarConStockBajo(int umbral) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos WHERE stock < ? AND activo = true ORDER BY stock ASC";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, umbral);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    productos.add(extraerProductoDeResultSet(rs));
                }
            }
            System.out.println("Se encontraron " + productos.size() + " productos con stock bajo");
        } catch (SQLException e) {
            System.err.println("Error al listar productos con stock bajo: " + e.getMessage());
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public boolean actualizarPrecio(int codigoReferencia, double nuevoPrecio) {
        String sql = "UPDATE productos SET precio = ? WHERE codigo_referencia = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDouble(1, nuevoPrecio);
            pstmt.setInt(2, codigoReferencia);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Precio actualizado para producto: " + codigoReferencia);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar precio: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método auxiliar para extraer un objeto Producto de un ResultSet
     * @param rs ResultSet con los datos del producto
     * @return objeto Producto
     * @throws SQLException si hay error al leer los datos
     */
    private Producto extraerProductoDeResultSet(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setCodigoReferencia(rs.getInt("codigo_referencia"));
        producto.setNombre(rs.getString("nombre"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setRestriccionEdad(rs.getBoolean("restriccion_edad"));
        producto.setStock(rs.getInt("stock"));
        producto.setImagen(rs.getString("imagen"));
        producto.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        producto.setActivo(rs.getBoolean("activo"));
        return producto;
    }
}

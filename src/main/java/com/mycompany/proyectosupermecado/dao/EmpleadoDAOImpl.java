package com.mycompany.proyectosupermecado.dao.impl;

import com.mycompany.proyectosupermecado.dao.EmpleadoDAO;
import com.mycompany.proyectosupermecado.modelo.Empleado;
import com.mycompany.proyectosupermecado.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de Empleado usando JDBC
 * @author Supermercado Team
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    private ConexionBD conexionBD;

    public EmpleadoDAOImpl() {
        this.conexionBD = ConexionBD.getInstancia();
    }

    @Override
    public boolean insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (dni, nombre, id, fecha_registro, activo) " +
                     "VALUES (?, ?, ?, NOW(), ?)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, empleado.getDni());
            pstmt.setString(2, empleado.getNombre());
            pstmt.setInt(3, empleado.getId());
            pstmt.setBoolean(4, empleado.isActivo());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Empleado insertado correctamente: " + empleado.getDni());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, id = ?, activo = ? WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, empleado.getNombre());
            pstmt.setInt(2, empleado.getId());
            pstmt.setBoolean(3, empleado.isActivo());
            pstmt.setString(4, empleado.getDni());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Empleado actualizado correctamente: " + empleado.getDni());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(String dni) {
        // Eliminación lógica (cambiar activo a false)
        String sql = "UPDATE empleados SET activo = false WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dni);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Empleado dado de baja (lógica): " + dni);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminarFisico(String dni) {
        String sql = "DELETE FROM empleados WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dni);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Empleado eliminado físicamente: " + dni);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar físicamente empleado: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Empleado buscarPorDni(String dni) {
        String sql = "SELECT * FROM empleados WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerEmpleadoDeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por DNI: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Empleado buscarPorId(int id) {
        String sql = "SELECT * FROM empleados WHERE id = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerEmpleadoDeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados WHERE activo = true ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                empleados.add(extraerEmpleadoDeResultSet(rs));
            }
            System.out.println("Se encontraron " + empleados.size() + " empleados activos");
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public List<Empleado> listarTodosConInactivos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                empleados.add(extraerEmpleadoDeResultSet(rs));
            }
            System.out.println("Se encontraron " + empleados.size() + " empleados (total)");
        } catch (SQLException e) {
            System.err.println("Error al listar todos los empleados: " + e.getMessage());
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public List<Empleado> buscarPorNombre(String criterio) {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT * FROM empleados WHERE nombre LIKE ? AND activo = true ORDER BY nombre";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String criterioBusqueda = "%" + criterio + "%";
            pstmt.setString(1, criterioBusqueda);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    empleados.add(extraerEmpleadoDeResultSet(rs));
                }
            }
            System.out.println("Búsqueda '" + criterio + "': " + empleados.size() + " resultados");
        } catch (SQLException e) {
            System.err.println("Error al buscar empleados por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return empleados;
    }

    @Override
    public boolean actualizarNombre(String dni, String nuevoNombre) {
        String sql = "UPDATE empleados SET nombre = ? WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, nuevoNombre);
            pstmt.setString(2, dni);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Nombre actualizado para empleado: " + dni);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualizarId(String dni, int nuevoId) {
        String sql = "UPDATE empleados SET id = ? WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, nuevoId);
            pstmt.setString(2, dni);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("ID actualizado para empleado: " + dni);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar ID: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existePorDni(String dni) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia por DNI: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean existePorId(int id) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE id = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método auxiliar para extraer un objeto Empleado de un ResultSet
     * @param rs ResultSet con los datos del empleado
     * @return objeto Empleado
     * @throws SQLException si hay error al leer los datos
     */
    private Empleado extraerEmpleadoDeResultSet(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setDni(rs.getString("dni"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setId(rs.getInt("id"));
        empleado.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        empleado.setActivo(rs.getBoolean("activo"));
        return empleado;
    }
}

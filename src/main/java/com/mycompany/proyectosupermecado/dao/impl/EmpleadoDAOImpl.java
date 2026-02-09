package com.mycompany.proyectosupermecado.dao.impl;

import com.mycompany.proyectosupermecado.dao.EmpleadoDAO;
import com.mycompany.proyectosupermecado.modelo.Empleado;
import com.mycompany.proyectosupermecado.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO para empleados
 */
public class EmpleadoDAOImpl implements EmpleadoDAO {

    @Override
    public boolean insertar(Empleado empleado) {
        String sql = "INSERT INTO empleados (dni, nombre, id, activo) VALUES (?, ?, ?, ?)";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, empleado.getDni());
            pstmt.setString(2, empleado.getNombre());
            pstmt.setInt(3, empleado.getId());
            pstmt.setBoolean(4, empleado.isActivo());
            
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al insertar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean actualizar(Empleado empleado) {
        String sql = "UPDATE empleados SET nombre = ?, id = ?, activo = ? WHERE dni = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, empleado.getNombre());
            pstmt.setInt(2, empleado.getId());
            pstmt.setBoolean(3, empleado.isActivo());
            pstmt.setString(4, empleado.getDni());
            
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al actualizar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean eliminar(String dni) {
        // Baja lógica: solo cambiamos el estado a inactivo
        String sql = "UPDATE empleados SET activo = FALSE WHERE dni = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, dni);
            
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Error al eliminar empleado: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Empleado buscarPorDni(String dni) {
        String sql = "SELECT dni, nombre, id, fecha_registro, activo FROM empleados WHERE dni = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, dni);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Empleado empleado = crearEmpleadoDesdeResultSet(rs);
                rs.close();
                pstmt.close();
                return empleado;
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por DNI: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public Empleado buscarPorId(int id) {
        String sql = "SELECT dni, nombre, id, fecha_registro, activo FROM empleados WHERE id = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Empleado empleado = crearEmpleadoDesdeResultSet(rs);
                rs.close();
                pstmt.close();
                return empleado;
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.err.println("Error al buscar empleado por ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return null;
    }

    @Override
    public List<Empleado> listarTodos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT dni, nombre, id, fecha_registro, activo FROM empleados WHERE activo = TRUE ORDER BY nombre";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                empleados.add(crearEmpleadoDesdeResultSet(rs));
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.err.println("Error al listar empleados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return empleados;
    }

    @Override
    public List<Empleado> listarTodosConInactivos() {
        List<Empleado> empleados = new ArrayList<>();
        String sql = "SELECT dni, nombre, id, fecha_registro, activo FROM empleados ORDER BY nombre";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                empleados.add(crearEmpleadoDesdeResultSet(rs));
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.err.println("Error al listar todos los empleados: " + e.getMessage());
            e.printStackTrace();
        }
        
        return empleados;
    }

    @Override
    public boolean existeDni(String dni) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE dni = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, dni);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                rs.close();
                pstmt.close();
                return count > 0;
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de DNI: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    @Override
    public boolean existeId(int id) {
        String sql = "SELECT COUNT(*) FROM empleados WHERE id = ?";
        
        try {
            Connection conn = ConexionBD.getInstancia().getConexion();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, id);
            
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                rs.close();
                pstmt.close();
                return count > 0;
            }
            
            rs.close();
            pstmt.close();
            
        } catch (SQLException e) {
            System.err.println("Error al verificar existencia de ID: " + e.getMessage());
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Método auxiliar para crear un objeto Empleado desde un ResultSet
     */
    private Empleado crearEmpleadoDesdeResultSet(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setDni(rs.getString("dni"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setId(rs.getInt("id"));
        empleado.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        empleado.setActivo(rs.getBoolean("activo"));
        return empleado;
    }
}

package com.mycompany.proyectosupermecado.dao.impl;

import com.mycompany.proyectosupermecado.dao.ClienteDAO;
import com.mycompany.proyectosupermecado.modelo.Cliente;
import com.mycompany.proyectosupermecado.util.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación del DAO de Cliente usando JDBC
 * @author Supermercado Team
 */
public class ClienteDAOImpl implements ClienteDAO {

    private ConexionBD conexionBD;

    public ClienteDAOImpl() {
        this.conexionBD = ConexionBD.getInstancia();
    }

    @Override
    public boolean insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (dni, nombre, apellidos, telefono, email, direccion, puntos_acumulados, fecha_registro, activo) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, NOW(), ?)";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellidos());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getEmail());
            pstmt.setString(6, cliente.getDireccion());
            pstmt.setInt(7, cliente.getPuntosAcumulados());
            pstmt.setBoolean(8, cliente.isActivo());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                // Obtener el ID generado
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cliente.setIdCliente(generatedKeys.getInt(1));
                    }
                }
                System.out.println("Cliente insertado correctamente: " + cliente.getDni());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET dni = ?, nombre = ?, apellidos = ?, telefono = ?, " +
                     "email = ?, direccion = ?, puntos_acumulados = ?, activo = ? " +
                     "WHERE id_cliente = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cliente.getDni());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getApellidos());
            pstmt.setString(4, cliente.getTelefono());
            pstmt.setString(5, cliente.getEmail());
            pstmt.setString(6, cliente.getDireccion());
            pstmt.setInt(7, cliente.getPuntosAcumulados());
            pstmt.setBoolean(8, cliente.isActivo());
            pstmt.setInt(9, cliente.getIdCliente());
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Cliente actualizado correctamente: " + cliente.getIdCliente());
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminar(int idCliente) {
        // Eliminación lógica (cambiar activo a false)
        String sql = "UPDATE clientes SET activo = false WHERE id_cliente = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Cliente dado de baja (lógica): " + idCliente);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean eliminarFisico(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id_cliente = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Cliente eliminado físicamente: " + idCliente);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al eliminar físicamente cliente: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Cliente buscarPorId(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE id_cliente = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCliente);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerClienteDeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Cliente buscarPorDni(String dni) {
        String sql = "SELECT * FROM clientes WHERE dni = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, dni);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerClienteDeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por DNI: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cliente> listarTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE activo = true ORDER BY apellidos, nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(extraerClienteDeResultSet(rs));
            }
            System.out.println("Se encontraron " + clientes.size() + " clientes activos");
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public List<Cliente> listarTodosConInactivos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes ORDER BY apellidos, nombre";
        
        try (Connection conn = conexionBD.getConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                clientes.add(extraerClienteDeResultSet(rs));
            }
            System.out.println("Se encontraron " + clientes.size() + " clientes (total)");
        } catch (SQLException e) {
            System.err.println("Error al listar todos los clientes: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public List<Cliente> buscarPorNombre(String criterio) {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE (nombre LIKE ? OR apellidos LIKE ?) AND activo = true " +
                     "ORDER BY apellidos, nombre";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            String criterioBusqueda = "%" + criterio + "%";
            pstmt.setString(1, criterioBusqueda);
            pstmt.setString(2, criterioBusqueda);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    clientes.add(extraerClienteDeResultSet(rs));
                }
            }
            System.out.println("Búsqueda '" + criterio + "': " + clientes.size() + " resultados");
        } catch (SQLException e) {
            System.err.println("Error al buscar clientes por nombre: " + e.getMessage());
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public boolean actualizarPuntos(int idCliente, int puntos) {
        String sql = "UPDATE clientes SET puntos_acumulados = puntos_acumulados + ? WHERE id_cliente = ?";
        
        try (Connection conn = conexionBD.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, puntos);
            pstmt.setInt(2, idCliente);
            
            int filasAfectadas = pstmt.executeUpdate();
            
            if (filasAfectadas > 0) {
                System.out.println("Puntos actualizados para cliente: " + idCliente);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error al actualizar puntos: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Método auxiliar para extraer un objeto Cliente de un ResultSet
     * @param rs ResultSet con los datos del cliente
     * @return objeto Cliente
     * @throws SQLException si hay error al leer los datos
     */
    private Cliente extraerClienteDeResultSet(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setIdCliente(rs.getInt("id_cliente"));
        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidos(rs.getString("apellidos"));
        cliente.setTelefono(rs.getString("telefono"));
        cliente.setEmail(rs.getString("email"));
        cliente.setDireccion(rs.getString("direccion"));
        cliente.setPuntosAcumulados(rs.getInt("puntos_acumulados"));
        cliente.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        cliente.setActivo(rs.getBoolean("activo"));
        return cliente;
    }
}

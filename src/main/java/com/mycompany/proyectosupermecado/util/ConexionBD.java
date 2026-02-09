package com.mycompany.proyectosupermecado.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos
 * Implementa el patrón Singleton
 * @author Supermercado Team
 */
public class ConexionBD {
    
    // Configuración de la base de datos
    private static final String URL = "jdbc:mysql://localhost:3306/supermercado";
    private static final String USUARIO = "root";  
    private static final String PASSWORD = "root";     
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    private static ConexionBD instancia;
    private Connection conexion;

    /**
     * Constructor privado para implementar Singleton
     */
    private ConexionBD() {
        try {
            // Cargar el driver de MySQL
            Class.forName(DRIVER);
            System.out.println("Driver MySQL cargado correctamente");
        } catch (ClassNotFoundException e) {
            System.err.println("Error al cargar el driver de MySQL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la instancia única de ConexionBD
     * @return instancia de ConexionBD
     */
    public static ConexionBD getInstancia() {
        if (instancia == null) {
            synchronized (ConexionBD.class) {
                if (instancia == null) {
                    instancia = new ConexionBD();
                }
            }
        }
        return instancia;
    }

    /**
     * Obtiene una conexión a la base de datos
     * @return Connection objeto de conexión
     * @throws SQLException si hay error en la conexión
     */
    public Connection getConexion() throws SQLException {
        if (conexion == null || conexion.isClosed()) {
            try {
                conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
                System.out.println("Conexión establecida correctamente");
            } catch (SQLException e) {
                System.err.println("Error al conectar con la base de datos: " + e.getMessage());
                throw e;
            }
        }
        return conexion;
    }

    /**
     * Cierra la conexión a la base de datos
     */
    public void cerrarConexion() {
        if (conexion != null) {
            try {
                if (!conexion.isClosed()) {
                    conexion.close();
                    System.out.println("Conexión cerrada correctamente");
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Verifica si la conexión está activa
     * @return true si la conexión está activa, false en caso contrario
     */
    public boolean isConexionActiva() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Prueba la conexión a la base de datos
     * @return true si la conexión es exitosa, false en caso contrario
     */
    public boolean probarConexion() {
        try {
            Connection conn = getConexion();
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Error en prueba de conexión: " + e.getMessage());
            return false;
        }
    }
}

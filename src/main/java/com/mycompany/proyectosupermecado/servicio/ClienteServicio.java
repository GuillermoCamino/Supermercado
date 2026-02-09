package com.mycompany.proyectosupermecado.servicio;

import com.mycompany.proyectosupermecado.dao.ClienteDAO;
import com.mycompany.proyectosupermecado.dao.impl.ClienteDAOImpl;
import com.mycompany.proyectosupermecado.modelo.Cliente;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * Clase de servicio que gestiona la lógica de negocio de Clientes
 * Actúa como intermediario entre la interfaz gráfica y la capa DAO
 * @author Supermercado Team
 */
public class ClienteServicio {
    
    private ClienteDAO clienteDAO;
    
    public ClienteServicio() {
        this.clienteDAO = new ClienteDAOImpl();
    }
    
    /**
     * Registra un nuevo cliente en el sistema
     * @param dni DNI del cliente
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @param telefono Teléfono del cliente
     * @param email Email del cliente
     * @param direccion Dirección del cliente
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarCliente(String dni, String nombre, String apellidos, 
                                   String telefono, String email, String direccion) {
        // Validaciones
        if (!validarDatos(dni, nombre, apellidos)) {
            return false;
        }
        
        // Verificar si el DNI ya existe
        if (clienteDAO.buscarPorDni(dni) != null) {
            JOptionPane.showMessageDialog(null, 
                "Ya existe un cliente registrado con el DNI: " + dni, 
                "DNI Duplicado", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Crear el objeto Cliente
        Cliente cliente = new Cliente();
        cliente.setDni(dni.trim().toUpperCase());
        cliente.setNombre(nombre.trim());
        cliente.setApellidos(apellidos.trim());
        cliente.setTelefono(telefono != null ? telefono.trim() : null);
        cliente.setEmail(email != null ? email.trim().toLowerCase() : null);
        cliente.setDireccion(direccion != null ? direccion.trim() : null);
        cliente.setPuntosAcumulados(0);
        cliente.setActivo(true);
        
        // Insertar en la base de datos
        boolean resultado = clienteDAO.insertar(cliente);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Cliente registrado exitosamente\nNombre: " + nombre + " " + apellidos, 
                "Registro Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al registrar el cliente. Intente nuevamente.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Modifica los datos de un cliente existente
     * @param cliente objeto Cliente con los datos actualizados
     * @return true si se modificó correctamente, false en caso contrario
     */
    public boolean modificarCliente(Cliente cliente) {
        if (cliente == null || cliente.getIdCliente() <= 0) {
            JOptionPane.showMessageDialog(null, 
                "Datos de cliente inválidos", 
                "Error de Validación", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar datos básicos
        if (!validarDatos(cliente.getDni(), cliente.getNombre(), cliente.getApellidos())) {
            return false;
        }
        
        // Actualizar
        boolean resultado = clienteDAO.actualizar(cliente);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Cliente modificado exitosamente", 
                "Modificación Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al modificar el cliente", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Da de baja (lógica) a un cliente
     * @param idCliente ID del cliente a dar de baja
     * @return true si se dio de baja correctamente, false en caso contrario
     */
    public boolean darDeBajaCliente(int idCliente) {
        // Confirmar acción
        int respuesta = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de dar de baja este cliente?", 
            "Confirmar Baja", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            boolean resultado = clienteDAO.eliminar(idCliente);
            
            if (resultado) {
                JOptionPane.showMessageDialog(null, 
                    "Cliente dado de baja exitosamente", 
                    "Baja Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al dar de baja el cliente", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            return resultado;
        }
        return false;
    }
    
    /**
     * Busca un cliente por su DNI
     * @param dni DNI a buscar
     * @return objeto Cliente si se encuentra, null en caso contrario
     */
    public Cliente buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Debe ingresar un DNI para buscar", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        Cliente cliente = clienteDAO.buscarPorDni(dni.trim().toUpperCase());
        
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, 
                "No se encontró ningún cliente con el DNI: " + dni, 
                "Cliente No Encontrado", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        return cliente;
    }
    
    /**
     * Busca un cliente por su ID
     * @param idCliente ID del cliente
     * @return objeto Cliente si se encuentra, null en caso contrario
     */
    public Cliente buscarPorId(int idCliente) {
        return clienteDAO.buscarPorId(idCliente);
    }
    
    /**
     * Obtiene la lista de todos los clientes activos
     * @return Lista de clientes activos
     */
    public List<Cliente> listarClientesActivos() {
        return clienteDAO.listarTodos();
    }
    
    /**
     * Busca clientes por nombre o apellido
     * @param criterio texto a buscar
     * @return Lista de clientes que coinciden
     */
    public List<Cliente> buscarPorNombre(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return listarClientesActivos();
        }
        return clienteDAO.buscarPorNombre(criterio.trim());
    }
    
    /**
     * Agrega puntos a un cliente
     * @param idCliente ID del cliente
     * @param puntos cantidad de puntos a agregar
     * @return true si se agregaron correctamente, false en caso contrario
     */
    public boolean agregarPuntos(int idCliente, int puntos) {
        if (puntos <= 0) {
            JOptionPane.showMessageDialog(null, 
                "La cantidad de puntos debe ser mayor a cero", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return clienteDAO.actualizarPuntos(idCliente, puntos);
    }
    
    /**
     * Canjea puntos de un cliente
     * @param idCliente ID del cliente
     * @param puntos cantidad de puntos a canjear
     * @return true si se canjearon correctamente, false en caso contrario
     */
    public boolean canjearPuntos(int idCliente, int puntos) {
        if (puntos <= 0) {
            JOptionPane.showMessageDialog(null, 
                "La cantidad de puntos debe ser mayor a cero", 
                "Error de Validación", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Verificar que el cliente tenga suficientes puntos
        Cliente cliente = clienteDAO.buscarPorId(idCliente);
        if (cliente == null) {
            JOptionPane.showMessageDialog(null, 
                "Cliente no encontrado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (cliente.getPuntosAcumulados() < puntos) {
            JOptionPane.showMessageDialog(null, 
                "Puntos insuficientes. El cliente tiene: " + cliente.getPuntosAcumulados() + " puntos", 
                "Puntos Insuficientes", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return clienteDAO.actualizarPuntos(idCliente, -puntos);
    }
    
    /**
     * Valida los datos básicos del cliente
     * @param dni DNI del cliente
     * @param nombre Nombre del cliente
     * @param apellidos Apellidos del cliente
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatos(String dni, String nombre, String apellidos) {
        if (dni == null || dni.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El DNI es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (nombre == null || nombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El nombre es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (apellidos == null || apellidos.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Los apellidos son obligatorios", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar formato de DNI (ejemplo español)
        if (!dni.matches("^[0-9]{8}[A-Z]$")) {
            JOptionPane.showMessageDialog(null, 
                "El DNI debe tener 8 números seguidos de una letra (ej: 12345678A)", 
                "Formato Incorrecto", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
}

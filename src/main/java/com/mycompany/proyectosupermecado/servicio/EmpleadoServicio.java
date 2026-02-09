package com.mycompany.proyectosupermecado.servicio;

import com.mycompany.proyectosupermecado.dao.EmpleadoDAO;
import com.mycompany.proyectosupermecado.dao.impl.EmpleadoDAOImpl;
import com.mycompany.proyectosupermecado.modelo.Empleado;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 * Clase de servicio que gestiona la lógica de negocio de Empleados
 * Actúa como intermediario entre la interfaz gráfica y la capa DAO
 * @author Supermercado Team
 */
public class EmpleadoServicio {
    
    private EmpleadoDAO empleadoDAO;
    
    public EmpleadoServicio() {
        this.empleadoDAO = new EmpleadoDAOImpl();
    }
    
    /**
     * Registra un nuevo empleado en el sistema
     * @param dni DNI del empleado (9 caracteres)
     * @param nombre Nombre completo del empleado
     * @param id ID único del empleado
     * @return true si se registró correctamente, false en caso contrario
     */
    public boolean registrarEmpleado(String dni, String nombre, int id) {
        // Validaciones
        if (!validarDatos(dni, nombre, id)) {
            return false;
        }
        
        // Verificar si el DNI ya existe - CORREGIDO: existeDni() en vez de existePorDni()
        if (empleadoDAO.existeDni(dni.trim().toUpperCase())) {
            JOptionPane.showMessageDialog(null, 
                "Ya existe un empleado registrado con el DNI: " + dni, 
                "DNI Duplicado", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Verificar si el ID ya existe - CORREGIDO: existeId() en vez de existePorId()
        if (empleadoDAO.existeId(id)) {
            JOptionPane.showMessageDialog(null, 
                "Ya existe un empleado registrado con el ID: " + id, 
                "ID Duplicado", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Crear el objeto Empleado
        Empleado empleado = new Empleado();
        empleado.setDni(dni.trim().toUpperCase());
        empleado.setNombre(nombre.trim());
        empleado.setId(id);
        empleado.setActivo(true);
        
        // Insertar en la base de datos
        boolean resultado = empleadoDAO.insertar(empleado);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Empleado registrado exitosamente\nNombre: " + nombre + "\nDNI: " + dni + "\nID: " + id, 
                "Registro Exitoso", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al registrar el empleado. Intente nuevamente.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Actualiza los datos de un empleado existente (nombre e ID)
     * @param dni DNI del empleado a actualizar
     * @param nuevoNombre Nuevo nombre del empleado
     * @param nuevoId Nuevo ID del empleado
     * @return true si se actualizó correctamente, false en caso contrario
     */
    public boolean actualizarEmpleado(String dni, String nuevoNombre, int nuevoId) {
        if (dni == null || dni.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El DNI es obligatorio para actualizar", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Buscar el empleado
        Empleado empleado = empleadoDAO.buscarPorDni(dni.trim().toUpperCase());
        if (empleado == null) {
            JOptionPane.showMessageDialog(null, 
                "No se encontró ningún empleado con el DNI: " + dni, 
                "Empleado No Encontrado", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        // Validar datos
        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El nombre es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        if (nuevoId <= 0) {
            JOptionPane.showMessageDialog(null, 
                "El ID debe ser mayor a cero", 
                "ID Inválido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Verificar si el nuevo ID ya existe (y no es del mismo empleado)
        Empleado empleadoConMismoId = empleadoDAO.buscarPorId(nuevoId);
        if (empleadoConMismoId != null && !empleadoConMismoId.getDni().equals(dni.trim().toUpperCase())) {
            JOptionPane.showMessageDialog(null, 
                "Ya existe otro empleado con el ID: " + nuevoId, 
                "ID Duplicado", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Actualizar los datos
        empleado.setNombre(nuevoNombre.trim());
        empleado.setId(nuevoId);
        
        boolean resultado = empleadoDAO.actualizar(empleado);
        
        if (resultado) {
            JOptionPane.showMessageDialog(null, 
                "Empleado actualizado exitosamente", 
                "Actualización Exitosa", 
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, 
                "Error al actualizar el empleado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        return resultado;
    }
    
    /**
     * Da de baja (lógica) a un empleado
     * @param dni DNI del empleado a dar de baja
     * @return true si se dio de baja correctamente, false en caso contrario
     */
    public boolean darDeBajaEmpleado(String dni) {
        // Confirmar acción
        int respuesta = JOptionPane.showConfirmDialog(null, 
            "¿Está seguro de dar de baja este empleado?", 
            "Confirmar Baja", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.QUESTION_MESSAGE);
        
        if (respuesta == JOptionPane.YES_OPTION) {
            boolean resultado = empleadoDAO.eliminar(dni.trim().toUpperCase());
            
            if (resultado) {
                JOptionPane.showMessageDialog(null, 
                    "Empleado dado de baja exitosamente", 
                    "Baja Exitosa", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, 
                    "Error al dar de baja el empleado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
            
            return resultado;
        }
        return false;
    }
    
    /**
     * Busca un empleado por su DNI
     * @param dni DNI a buscar
     * @return objeto Empleado si se encuentra, null en caso contrario
     */
    public Empleado buscarPorDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "Debe ingresar un DNI para buscar", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        Empleado empleado = empleadoDAO.buscarPorDni(dni.trim().toUpperCase());
        
        if (empleado == null) {
            JOptionPane.showMessageDialog(null, 
                "No se encontró ningún empleado con el DNI: " + dni, 
                "Empleado No Encontrado", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        return empleado;
    }
    
    /**
     * Busca un empleado por su ID
     * @param id ID del empleado
     * @return objeto Empleado si se encuentra, null en caso contrario
     */
    public Empleado buscarPorId(int id) {
        Empleado empleado = empleadoDAO.buscarPorId(id);
        
        if (empleado == null) {
            JOptionPane.showMessageDialog(null, 
                "No se encontró ningún empleado con el ID: " + id, 
                "Empleado No Encontrado", 
                JOptionPane.INFORMATION_MESSAGE);
        }
        
        return empleado;
    }
    
    /**
     * Obtiene la lista de todos los empleados activos
     * @return Lista de empleados activos
     */
    public List<Empleado> listarEmpleadosActivos() {
        return empleadoDAO.listarTodos();
    }
    
    /**
     * Busca empleados por nombre (filtrado en memoria)
     * @param criterio texto a buscar
     * @return Lista de empleados que coinciden
     */
    public List<Empleado> buscarPorNombre(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return listarEmpleadosActivos();
        }
        
        // Obtener todos los empleados activos
        List<Empleado> todosEmpleados = empleadoDAO.listarTodos();
        List<Empleado> empleadosFiltrados = new ArrayList<>();
        
        // Filtrar por nombre (búsqueda que contenga el criterio)
        String criterioBusqueda = criterio.trim().toLowerCase();
        for (Empleado emp : todosEmpleados) {
            if (emp.getNombre().toLowerCase().contains(criterioBusqueda)) {
                empleadosFiltrados.add(emp);
            }
        }
        
        return empleadosFiltrados;
    }
    
    /**
     * Valida los datos básicos del empleado
     * @param dni DNI del empleado
     * @param nombre Nombre del empleado
     * @param id ID del empleado
     * @return true si los datos son válidos, false en caso contrario
     */
    private boolean validarDatos(String dni, String nombre, int id) {
        if (dni == null || dni.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, 
                "El DNI es obligatorio", 
                "Campo Requerido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        // Validar formato de DNI (9 caracteres: 8 números y 1 letra)
        String dniUpper = dni.trim().toUpperCase();
        if (!dniUpper.matches("^[0-9]{8}[A-Z]$")) {
            JOptionPane.showMessageDialog(null, 
                "El DNI debe tener 8 números seguidos de una letra (ej: 12345678A)", 
                "Formato Incorrecto", 
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
        
        if (id <= 0) {
            JOptionPane.showMessageDialog(null, 
                "El ID debe ser un número positivo mayor a cero", 
                "ID Inválido", 
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        return true;
    }
}
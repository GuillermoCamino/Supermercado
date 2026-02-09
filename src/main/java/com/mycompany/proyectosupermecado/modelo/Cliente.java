package com.mycompany.proyectosupermecado.modelo;

import java.util.Date;

/**
 * Clase que representa a un Cliente en el sistema
 * @author Supermercado Team
 */
public class Cliente {
    private int idCliente;
    private String dni;
    private String nombre;
    private String apellidos;
    private String telefono;
    private String email;
    private String direccion;
    private int puntosAcumulados;
    private Date fechaRegistro;
    private boolean activo;

    // Constructor vacío
    public Cliente() {
        this.activo = true;
        this.puntosAcumulados = 0;
    }

    // Constructor con parámetros principales
    public Cliente(String dni, String nombre, String apellidos) {
        this();
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    // Constructor completo
    public Cliente(int idCliente, String dni, String nombre, String apellidos, 
                   String telefono, String email, String direccion, 
                   int puntosAcumulados, Date fechaRegistro, boolean activo) {
        this.idCliente = idCliente;
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.telefono = telefono;
        this.email = email;
        this.direccion = direccion;
        this.puntosAcumulados = puntosAcumulados;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    // Getters y Setters
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getPuntosAcumulados() {
        return puntosAcumulados;
    }

    public void setPuntosAcumulados(int puntosAcumulados) {
        this.puntosAcumulados = puntosAcumulados;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Método para agregar puntos al cliente
     * @param puntos cantidad de puntos a agregar
     */
    public void agregarPuntos(int puntos) {
        if (puntos > 0) {
            this.puntosAcumulados += puntos;
        }
    }

    /**
     * Método para canjear puntos del cliente
     * @param puntos cantidad de puntos a canjear
     * @return true si se pudieron canjear, false si no hay suficientes puntos
     */
    public boolean canjearPuntos(int puntos) {
        if (puntos > 0 && this.puntosAcumulados >= puntos) {
            this.puntosAcumulados -= puntos;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "idCliente=" + idCliente +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", puntosAcumulados=" + puntosAcumulados +
                ", activo=" + activo +
                '}';
    }
}

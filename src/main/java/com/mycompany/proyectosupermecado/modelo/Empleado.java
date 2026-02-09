package com.mycompany.proyectosupermecado.modelo;

import java.util.Date;

/**
 * Clase que representa a un Empleado en el sistema
 * @author Supermercado Team
 */
public class Empleado {
    private String dni;
    private String nombre;
    private int id;
    private Date fechaRegistro;
    private boolean activo;

    // Constructor vacío
    public Empleado() {
        this.activo = true;
    }

    // Constructor con parámetros principales
    public Empleado(String dni, String nombre, int id) {
        this();
        this.dni = dni;
        this.nombre = nombre;
        this.id = id;
    }

    // Constructor completo
    public Empleado(String dni, String nombre, int id, Date fechaRegistro, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    // Getters y Setters
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
     * Obtiene el nombre completo del empleado
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return this.nombre;
    }

    /**
     * Obtiene la información resumida del empleado
     * @return String con formato "ID: [id] - [nombre]"
     */
    public String getInfoResumida() {
        return "ID: " + this.id + " - " + this.nombre;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", id=" + id +
                ", fechaRegistro=" + fechaRegistro +
                ", activo=" + activo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Empleado empleado = (Empleado) o;
        return dni != null ? dni.equals(empleado.dni) : empleado.dni == null;
    }

    @Override
    public int hashCode() {
        return dni != null ? dni.hashCode() : 0;
    }
}

package com.mycompany.proyectosupermecado.modelo;

import java.sql.Timestamp;

/**
 * Clase modelo que representa un empleado del supermercado
 */
public class Empleado {
    private String dni;
    private String nombre;
    private int id;
    private Timestamp fechaRegistro;
    private boolean activo;

    // Constructor vacío
    public Empleado() {
    }

    // Constructor completo
    public Empleado(String dni, String nombre, int id, Timestamp fechaRegistro, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.id = id;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    // Constructor sin fecha (para nuevos empleados)
    public Empleado(String dni, String nombre, int id, boolean activo) {
        this.dni = dni;
        this.nombre = nombre;
        this.id = id;
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

    public Timestamp getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Timestamp fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
}

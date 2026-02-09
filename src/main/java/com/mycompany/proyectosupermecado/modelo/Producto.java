package com.mycompany.proyectosupermecado.modelo;

import java.util.Date;

/**
 * Clase que representa a un Producto en el sistema
 * @author Supermercado Team
 */
public class Producto {
    private int codigoReferencia;
    private String nombre;
    private double precio;
    private boolean restriccionEdad;
    private int stock;
    private String imagen;
    private Date fechaRegistro;
    private boolean activo;

    // Constructor vacío
    public Producto() {
        this.activo = true;
        this.stock = 0;
        this.restriccionEdad = false;
    }

    // Constructor con parámetros principales
    public Producto(int codigoReferencia, String nombre, double precio) {
        this();
        this.codigoReferencia = codigoReferencia;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Constructor completo
    public Producto(int codigoReferencia, String nombre, double precio, 
                   boolean restriccionEdad, int stock, String imagen, 
                   Date fechaRegistro, boolean activo) {
        this.codigoReferencia = codigoReferencia;
        this.nombre = nombre;
        this.precio = precio;
        this.restriccionEdad = restriccionEdad;
        this.stock = stock;
        this.imagen = imagen;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
    }

    // Getters y Setters
    public int getCodigoReferencia() {
        return codigoReferencia;
    }

    public void setCodigoReferencia(int codigoReferencia) {
        this.codigoReferencia = codigoReferencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean isRestriccionEdad() {
        return restriccionEdad;
    }

    public void setRestriccionEdad(boolean restriccionEdad) {
        this.restriccionEdad = restriccionEdad;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
     * Método para agregar stock al producto
     * @param cantidad cantidad de unidades a agregar
     */
    public void agregarStock(int cantidad) {
        if (cantidad > 0) {
            this.stock += cantidad;
        }
    }

    /**
     * Método para reducir stock del producto
     * @param cantidad cantidad de unidades a reducir
     * @return true si se pudo reducir, false si no hay suficiente stock
     */
    public boolean reducirStock(int cantidad) {
        if (cantidad > 0 && this.stock >= cantidad) {
            this.stock -= cantidad;
            return true;
        }
        return false;
    }

    /**
     * Verifica si el producto tiene stock disponible
     * @return true si hay stock disponible, false en caso contrario
     */
    public boolean tieneStock() {
        return this.stock > 0;
    }

    /**
     * Verifica si el producto tiene stock suficiente
     * @param cantidad cantidad a verificar
     * @return true si hay suficiente stock, false en caso contrario
     */
    public boolean tieneStockSuficiente(int cantidad) {
        return this.stock >= cantidad;
    }

    /**
     * Calcula el valor total del stock del producto
     * @return valor total (precio * stock)
     */
    public double getValorStock() {
        return this.precio * this.stock;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "codigoReferencia=" + codigoReferencia +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                ", restriccionEdad=" + restriccionEdad +
                ", stock=" + stock +
                ", imagen='" + imagen + '\'' +
                ", activo=" + activo +
                '}';
    }
}

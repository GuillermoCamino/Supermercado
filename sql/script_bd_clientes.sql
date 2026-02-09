-- ================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS - SUPERMERCADO
-- MÓDULO: CLIENTES
-- ================================================

-- Crear base de datos si no existe
CREATE DATABASE IF NOT EXISTS supermercado
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE supermercado;

-- ================================================
-- TABLA: clientes
-- Descripción: Almacena la información de los clientes del supermercado
-- ================================================
CREATE TABLE IF NOT EXISTS clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    dni VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(150) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(250),
    puntos_acumulados INT DEFAULT 0,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    activo BOOLEAN DEFAULT TRUE,
    
    -- Índices para mejorar el rendimiento
    INDEX idx_dni (dni),
    INDEX idx_nombre (nombre),
    INDEX idx_apellidos (apellidos),
    INDEX idx_activo (activo)
) ENGINE=InnoDB;

-- ================================================
-- DATOS DE PRUEBA
-- ================================================
INSERT INTO clientes (dni, nombre, apellidos, telefono, email, direccion, puntos_acumulados, activo) 
VALUES 
    ('12345678A', 'Juan', 'García López', '600111222', 'juan.garcia@email.com', 'Calle Mayor 1, Madrid', 150, TRUE),
    ('87654321B', 'María', 'Rodríguez Pérez', '600333444', 'maria.rodriguez@email.com', 'Avenida Libertad 25, Madrid', 320, TRUE),
    ('11223344C', 'Pedro', 'Martínez Sánchez', '600555666', 'pedro.martinez@email.com', 'Plaza España 10, Madrid', 75, TRUE),
    ('44332211D', 'Ana', 'López Fernández', '600777888', 'ana.lopez@email.com', 'Calle Sol 15, Madrid', 450, TRUE),
    ('99887766E', 'Carlos', 'González Ruiz', '600999000', 'carlos.gonzalez@email.com', 'Avenida Constitución 30, Madrid', 0, TRUE);

-- ================================================
-- CONSULTAS ÚTILES PARA VERIFICACIÓN
-- ================================================

-- Ver todos los clientes activos
SELECT * FROM clientes WHERE activo = TRUE ORDER BY apellidos, nombre;

-- Ver total de clientes y puntos acumulados
SELECT 
    COUNT(*) as total_clientes,
    SUM(puntos_acumulados) as total_puntos
FROM clientes 
WHERE activo = TRUE;

-- Clientes con más puntos
SELECT 
    CONCAT(nombre, ' ', apellidos) as nombre_completo,
    dni,
    puntos_acumulados
FROM clientes 
WHERE activo = TRUE
ORDER BY puntos_acumulados DESC
LIMIT 10;

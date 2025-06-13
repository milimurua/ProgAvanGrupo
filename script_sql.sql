-- 1.Crear la base de datos --
CREATE DATABASE IF NOT EXISTS bd_aplicacion_bancaria;

-- 2.Seleccionamos la base de datos --
USE bd_aplicacion_bancaria;

-- 3.Crear la tabla usuario --
CREATE TABLE IF NOT EXISTS usuario(
	id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(20) NOT NULL,
    contraseña VARCHAR(50) NOT NULL
);

-- 4.Crear la tabla cuenta --
CREATE TABLE IF NOT EXISTS cuenta(
	id_cuenta INT AUTO_INCREMENT PRIMARY KEY,
    usuario_id INT NOT NULL,
    saldo DECIMAL(15,2) NOT NULL DEFAULT 0,
    FOREIGN KEY(usuario_id) REFERENCES usuario(id_usuario)
);

-- 5.Crear la tabla transaccion --
CREATE TABLE IF NOT EXISTS transaccion(
	id_transaccion BIGINT AUTO_INCREMENT PRIMARY KEY,
    cuenta_id INT NOT NULL,
    tipo VARCHAR(30) NOT NULL,
    monto DECIMAL(15,2) NOT NULL,
    fecha TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cuenta_destino INT(50),
    FOREIGN KEY(cuenta_id) REFERENCES cuenta(id_cuenta),
    FOREIGN KEY(cuenta_destino) REFERENCES cuenta(id_cuenta)
);

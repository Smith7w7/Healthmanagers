-- Crear base de datos
CREATE DATABASE IF NOT EXISTS healthmanager;
USE healthmanager;

-- Tabla especialidades
CREATE TABLE IF NOT EXISTS especialidades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

-- Tabla pacientes
CREATE TABLE IF NOT EXISTS pacientes (
    dni VARCHAR(8) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    genero ENUM('Masculino', 'Femenino') NOT NULL,
    telefono VARCHAR(15),
    direccion VARCHAR(200),
    grupo_sanguineo VARCHAR(5),
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla médicos
CREATE TABLE IF NOT EXISTS medicos (
    cmp VARCHAR(10) PRIMARY KEY,
    dni VARCHAR(8) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    especialidad_id INT,
    telefono VARCHAR(15),
    fecha_nacimiento DATE,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (especialidad_id) REFERENCES especialidades(id)
);

-- Tabla citas
CREATE TABLE IF NOT EXISTS citas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    paciente_dni VARCHAR(8) NOT NULL,
    medico_cmp VARCHAR(10) NOT NULL,
    fecha DATE NOT NULL,
    hora VARCHAR(5) NOT NULL,
    motivo TEXT,
    diagnostico TEXT,
    estado ENUM('PENDIENTE', 'ATENDIDA', 'CANCELADA') DEFAULT 'PENDIENTE',
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paciente_dni) REFERENCES pacientes(dni),
    FOREIGN KEY (medico_cmp) REFERENCES medicos(cmp),
    UNIQUE KEY unica_cita (medico_cmp, fecha, hora)
);

-- Tabla tratamientos
CREATE TABLE IF NOT EXISTS tratamientos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cita_id INT NOT NULL,
    medicamento VARCHAR(200) NOT NULL,
    dosis VARCHAR(100),
    duracion_dias INT,
    indicaciones TEXT,
    fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cita_id) REFERENCES citas(id) ON DELETE CASCADE
);

-- Insertar especialidades
INSERT INTO especialidades (nombre, descripcion) VALUES
('Medicina General', 'Atención primaria de salud'),
('Cardiología', 'Especialista en corazón y sistema circulatorio'),
('Pediatría', 'Atención médica de niños'),
('Traumatología', 'Tratamiento de lesiones músculo-esqueléticas'),
('Dermatología', 'Enfermedades de la piel');

-- Insertar pacientes de ejemplo
INSERT INTO pacientes VALUES
('12345678', 'Juan', 'Pérez', '1985-05-15', 'Masculino', '987654321', 'Calle Principal 123', 'O+', NOW()),
('87654321', 'María', 'García', '1990-08-22', 'Femenino', '987654322', 'Av. Secundaria 456', 'A-', NOW()),
('11223344', 'Carlos', 'López', '1978-12-10', 'Masculino', '987654323', 'Jr. Tercera 789', 'B+', NOW());

-- Insertar médicos de ejemplo
INSERT INTO medicos VALUES
('MP12345', '12112233', 'Roberto', 'Quispe', 1, '987654331', '1970-03-20', NOW()),
('MP67890', '12223344', 'Ana', 'Morales', 2, '987654332', '1975-07-10', NOW());
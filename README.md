# HealthManager - Sistema de Gestión Hospitalaria

## Descripción
Sistema integral de gestión médica para centros de salud pequeños y medianos, desarrollado en Java con interfaz gráfica Swing y base de datos MySQL.

## Características
- ✓ Gestión completa de pacientes (CRUD)
- ✓ Gestión de médicos y especialidades
- ✓ Asignación y control de citas médicas
- ✓ Registro de tratamientos y diagnósticos
- ✓ Interfaz gráfica intuitiva
- ✓ Aplicación de principios POO

## Requisitos Previos

### Software
- Java JDK 8 o superior
- MySQL Server 8.0 o compatible
- IDE: NetBeans / Eclipse / IntelliJ IDEA (opcional)

### Hardware
- RAM: 2 GB mínimo
- Disco: 500 MB libres
- Pantalla: 1024x768 mínimo

## Instalación

### 1. Clonar o Descargar el Proyecto
```bash
git clone https://github.com/usuario/healthmanager-java.git
cd healthmanager
```

### 2. Crear Base de Datos
1. Abrir MySQL Workbench o línea de comandos MySQL
2. Ejecutar:
```sql
source database/schema.sql;
```

### 3. Configurar Conexión (Opcional)
Si tu usuario MySQL es diferente, editar:
`src/com/healthmanager/dao/ConexionBD.java`

```java
private static final String USUARIO = "tu_usuario";
private static final String PASSWORD = "tu_contraseña";
```

### 4. Compilar el Proyecto
```bash
cd src
javac -cp ../lib/mysql-connector-java-8.0.33.jar com/healthmanager/**/*.java
```

### 5. Ejecutar el Sistema
```bash
java -cp .:../lib/mysql-connector-java-8.0.33.jar com.healthmanager# PROYECTO HEALTHMANAGER - ESTRUCTURA COMPLETA

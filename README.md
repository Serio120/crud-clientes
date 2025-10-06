
# CRUD Java SE, Maven, MySQL y JDBC

<img width="945" height="500" alt="image" src="https://github.com/user-attachments/assets/5176ff7e-39e0-4a57-8719-5c7a4a7c40bc" />


## 1. BD: 

```sql
CREATE DATABASE ejemplo_clientes;

USE ejemplo_clientes;

CREATE TABLE clientes (
    id INT AUTO_INCREMENT,
    dni VARCHAR(10) NOT NULL UNIQUE,
    nombre VARCHAR(50) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    PRIMARY KEY(id)
);

```

## 2. Estructura del proyecto

```
crud-clientes/
├── pom.xml
└── src/
    └── main/
        └── java/
            └── org/
                └── example/
                    ├── Cliente.java
                    ├── Conexion.java
                    ├── ClienteDAO.java
                    └── Main.java

```


## 3. Crea un proyecto Maven y deja el pom.xml con este contenido

```xml

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>CRUDClientes</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>17</maven.compiler.source>
        <maven.compiler.target>17</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>
<dependencies>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.0.33</version>
    </dependency>
</dependencies>

</project>

```

## 4. Clase Cliente (Representa al modelo de datos)

```java
package org.example;

public class Cliente {
    private int id;
    private String dni;
    private String nombre;
    private String apellidos;

    public Cliente() {}

    public Cliente(String dni, String nombre, String apellidos) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
}

```

## 5. Clase Conexion

```java
package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/ejemplo_clientes";
    private static final String USER = "root";
    private static final String PASS = "tu_contraseña";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}

```

## 6. Clase ClienteDAO

```java
package org.example;

import java.sql.*;
import java.util.*;

public class ClienteDAO {

    public void crear(Cliente cliente) throws Exception {
        String sql = "INSERT INTO clientes (dni, nombre, apellidos) VALUES (?, ?, ?)";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getDni());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellidos());
            stmt.executeUpdate();
        }
    }

    public Cliente leer(int id) throws Exception {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setDni(rs.getString("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellidos(rs.getString("apellidos"));
                return c;
            }
        }
        return null;
    }

    public List<Cliente> listar() throws Exception {
        String sql = "SELECT * FROM clientes";
        List<Cliente> lista = new ArrayList<>();
        try (Connection conn = Conexion.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setId(rs.getInt("id"));
                c.setDni(rs.getString("dni"));
                c.setNombre(rs.getString("nombre"));
                c.setApellidos(rs.getString("apellidos"));
                lista.add(c);
            }
        }
        return lista;
    }

    public void actualizar(Cliente cliente) throws Exception {
        String sql = "UPDATE clientes SET dni = ?, nombre = ?, apellidos = ? WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cliente.getDni());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getApellidos());
            stmt.setInt(4, cliente.getId());
            stmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws Exception {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

```

## 7. Main

```java
package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        ClienteDAO dao = new ClienteDAO();

        // Crear cliente
        Cliente nuevo = new Cliente("12345678A", "Juan", "Pérez Gómez");
        dao.crear(nuevo);

        // Leer cliente
        Cliente cliente = dao.leer(1);
        if (cliente != null) {
            System.out.println("Cliente leído: " + cliente.getNombre());
        }

        // Listar clientes
        for (Cliente c : dao.listar()) {
            System.out.println(c.getId() + " - " + c.getNombre() + " " + c.getApellidos());
        }

        // Actualizar cliente
        if (cliente != null) {
            cliente.setNombre("Juanito");
            dao.actualizar(cliente);
        }

        // Eliminar cliente
        if (cliente != null) {
            dao.eliminar(cliente.getId());
        }
    }
}

``` 

## Configuración Test

<img width="786" height="467" alt="image" src="https://github.com/user-attachments/assets/37c03063-d315-4b9d-b583-4dd113e2f54c" />


## Ejercicio 

Haz un CRUD de esta tabla de BD: 

```sql
CREATE TABLE Libros (
  id INT NOT NULL AUTO_INCREMENT,
  titulo VARCHAR(255) NOT NULL,
  autor VARCHAR(255) NOT NULL,
  isbn VARCHAR(20) NOT NULL UNIQUE,
  paginas INT NOT NULL,
  precio DECIMAL(10, 2) NOT NULL,
  PRIMARY KEY (id)
);

```

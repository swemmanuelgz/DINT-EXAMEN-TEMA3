package com.example.demo1.pruebaBBDD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InsertUser {


        public static void main(String[] args) {
            // URL de conexión a la base de datos SQLite
            String url = "jdbc:sqlite:data/bbdd_prueba.db";
           // Nombre del archivo de la base de datos SQLite
            // Datos a insertar
            String nombre = "juan"; // El nombre del usu
            String pwd = "secreto"; // La contraseña
            // Conectar a la base de datos y realizar la inserción
            try (Connection conn =
                         DriverManager.getConnection(url)) {
                // Sentencia SQL para insertar una nueva fila
                String sql = "INSERT INTO USER (NOMBRE, PWD) VALUES ( ?, ?)";
                // Preparar la sentencia con los valores
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, nombre);
                    pstmt.setString(2, pwd);
                    // Ejecutar la inserción
                    int filasAfectadas = pstmt.executeUpdate();
                    System.out.println("Filas insertadas: " +
                            filasAfectadas);
                }


            } catch(SQLException e){
                e.printStackTrace();
            }
    }
}

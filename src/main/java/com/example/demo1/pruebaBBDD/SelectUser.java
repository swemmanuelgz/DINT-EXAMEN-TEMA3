package com.example.demo1.pruebaBBDD;
import java.sql.*;

public class SelectUser {
            //TODO: usando el patron mvc hay que hacer que se muestren los datos nombre de usuario , actividad y calorias en la tableView de los datos de la base de dat
        //  public Usuario selectUserByName(String name){
        public static void main(String[] args) {
            //Connection conn = ConexionSQLite.conectar();
            String url = "jdbc:sqlite:data/bbdd_prueba.db";
            String query = "SELECT nombre, pwd FROM USER ";


            // Conexión a la base de datos y ejecución de la consulta
            try {
                Connection conn = DriverManager.getConnection(url);
                PreparedStatement pstmt = conn.prepareStatement(query);

                // Ejecutar la consulta
                ResultSet rs = pstmt.executeQuery();

                // Verificar si el usuario fue encontrado
                while (rs.next()) {
                    // Obtener los valores del resultado
                    String nombre = rs.getString("nombre");
                    String pwd = rs.getString("pwd");


                    // Mostrar los resultados
                    System.out.println("Usuario: " + nombre);
                    System.out.println("Contraseña: " + pwd);
                }


            } catch (SQLException e) {
                System.out.println("Error al conectar con la base de datos: " + e.getMessage());
            }
        }
    }



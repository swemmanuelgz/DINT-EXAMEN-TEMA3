package com.example.demo1;

import com.example.demo1.modelo.Modelo;
import com.example.demo1.utils.Constantes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class Test {
    //Conexion
    public static void testConexion() {
        try (Connection conn = DriverManager.getConnection(Constantes.RUTA_BBDD_CALORIAS.getDescripcion())) {
            if (conn != null) {
                System.out.println("Conexión a la base de datos establecida.");
            } else {
                System.out.println("Error al conectar a la base de datos.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //inserciomn
    public static void testInsertarDatos() {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        Modelo usuario = new Modelo("Juan", "Correr", "500");
        usuarioDAO.insertarDatos(usuario);
        System.out.println("Datos insertados correctamente.");
    }
    //select
    public static void testSelectDatos() {
        UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
        List<Modelo> usuarios = usuarioDAO.selectDatos();
        //imprimimos la lista de usuarios
        for (Modelo u : usuarios) {
            System.out.println("Nombre: " + u.getNombre() + " Actividad: " + u.getActividad() + " Calorias: " + u.getCalorias());
        }
    }

    public static void main(String[] args) {
        System.out.println("Probando la conexión a la base de datos:");
        testConexion();

        System.out.println("\nProbando la inserción de datos:");
        testInsertarDatos();

        System.out.println("\nProbando la selección de datos:");
        testSelectDatos();
    }
}
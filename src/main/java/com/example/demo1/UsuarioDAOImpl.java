package com.example.demo1;

import com.example.demo1.modelo.Modelo;
import com.example.demo1.UsuarioDAO;
import com.example.demo1.utils.Constantes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAOImpl implements UsuarioDAO {
    //no se que es el BBDDBASE entonces usé el DAO como lo suelo hacer con spring
    @Override
    public void insertarDatos(Modelo usuario) {
        try (Connection conn = DriverManager.getConnection(Constantes.RUTA_BBDD_CALORIAS.getDescripcion())) {
            String query = "INSERT INTO USUARIO (nombre, actividad, calorias) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getActividad());
            pstmt.setString(3, usuario.getCalorias());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Modelo> selectDatos() {
        List<Modelo> usuarios = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(Constantes.RUTA_BBDD_CALORIAS.getDescripcion())) {
            String query = "SELECT nombre, actividad, calorias FROM USUARIO";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String actividad = rs.getString("actividad");
                String calorias = rs.getString("calorias");
                usuarios.add(new Modelo(nombre, actividad, calorias));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuarios;
    }
}
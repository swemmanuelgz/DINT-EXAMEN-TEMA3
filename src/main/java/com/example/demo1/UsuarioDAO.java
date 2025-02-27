package com.example.demo1;

import com.example.demo1.modelo.Modelo;
import java.util.List;

public interface UsuarioDAO {
    void insertarDatos(Modelo usuario);
    List<Modelo> selectDatos();
}
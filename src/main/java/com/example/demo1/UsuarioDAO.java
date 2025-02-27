package com.example.demo1;

import com.example.demo1.modelo.Modelo;
import java.util.List;

public interface UsuarioDAO {
    //no sé si está bien pero es la manera que suelo hacerlo
    void insertarDatos(Modelo usuario);
    List<Modelo> selectDatos();
}
package com.example.demo1.modelo;

public class Modelo{
    private String nombre;
    private String actividad;
    private String calorias;

    public Modelo(String nombre, String actividad, String calorias) {
        this.nombre = nombre;
        this.actividad = actividad;
        this.calorias = calorias;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }
}

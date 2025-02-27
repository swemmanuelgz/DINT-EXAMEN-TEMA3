package com.example.demo1.utils;

/**
 * Esta clase es un enumerado para albergar las constantes de la aplicación.
 */
public enum Constantes {
    PAGINA_CALORIAS("calorias.fxml"),

    PAGINA_INICIAL("first.fxml"),
    PAGINA_SEGUNDA_PANTALLA("second.fxml"),
    TITULO_PAGINA_INICIAL("Página Inicial"),
    TITULO_SEGUNDA_PANTALLA("Página Segunda"),

    RUTA_CSS_GENERAL("/css/application.css"),
    RUTA_BBDD("jdbc:sqlite:data/bbdd_prueba.db"),
    RUTA_BBDD_CALORIAS("jdbc:sqlite:data/BBDD_calorias.db"),
    SELECT_USER_ALL("SELECT nombre, pwd FROM USER "),
    SELECT_USER_WHERE_NOMBRE("SELECT nombre, pwd FROM USER WHERE NOMBRE = ? "),

    INSERT_INTO_USER_VALUES("INSERT INTO USER (NOMBRE, PWD) VALUES ( ?, ?)");

    private final String descripcion;

    // Constructor para asociar una descripción a cada constante
    Constantes(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}

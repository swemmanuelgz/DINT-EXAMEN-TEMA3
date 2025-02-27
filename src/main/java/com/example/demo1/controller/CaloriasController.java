package com.example.demo1.controller;

import com.example.demo1.UsuarioDAO;
import com.example.demo1.UsuarioDAOImpl;
import com.example.demo1.modelo.Modelo;
import com.example.demo1.pruebasReports.ReportGenerating;
import com.example.demo1.utils.Constantes;
import com.example.demo1.utils.PantallaUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class CaloriasController {

    @FXML
    private Button btnCalcularCalorias;

    @FXML
    private ComboBox<String> cbActividad;

    @FXML
    private TextField duracion;

    @FXML
    private DatePicker fecha;

    @FXML
    private Label lblActividad;

    @FXML
    private Label lblDuracion;

    @FXML
    private Label lblFecha;

    @FXML
    private Label lblPeso;

    @FXML
    private Button btnInforme;

    @FXML
    private Label lblUsuario;

    @FXML
    private TextField nombreUsuario;

    @FXML
    private TextField peso;

    @FXML
    private TableView<Modelo> tablaDatos;
    @FXML
    private TableColumn<Modelo, String> celdaActividad;

    @FXML
    private TableColumn<Modelo, Integer> celdaCalorias;

    @FXML
    private TableColumn<Modelo, String> celdaNombre;

    @FXML
    private AnchorPane root;

    private Scene scene;
    private UsuarioDAO usuarioDAO = new UsuarioDAOImpl();
    private ReportGenerating reportGenerating = new ReportGenerating();


    public CaloriasController showEstaPantalla(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new PantallaUtils().showEstaPantalla(stage, Constantes.PAGINA_CALORIAS.getDescripcion(), Constantes.TITULO_PAGINA_INICIAL.getDescripcion(), 600, 600);
        //OBTENER EL CONTROLADOR DE ESTA VENTANA, PARA PODER REFRESCAR DATOS DE COMPONENTES
        CaloriasController controller = fxmlLoader.getController();

        //ESTABLECER LA CSS PARA ESTA PANTALLA - en este caso es la ruta general -
        //Se puede incluir de modo general en pantallaUtils
        scene = stage.getScene();
        scene.getStylesheets().add(getClass().getResource(Constantes.RUTA_CSS_GENERAL.getDescripcion()).toExternalForm());

        return controller;
    }

    @FXML
    public void initialize() {
        celdaNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        celdaActividad.setCellValueFactory(new PropertyValueFactory<>("actividad"));
        celdaCalorias.setCellValueFactory(new PropertyValueFactory<>("calorias"));
            //añadimos los valores al combobox de actividad
        cbActividad.getItems().addAll("Caminar", "Correr", "Nadar");

        btnInforme.setOnAction(event -> {
            try(Connection connection = DriverManager.getConnection(Constantes.RUTA_BBDD_CALORIAS.getDescripcion())) {
                reportGenerating.generateReport(connection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cargarDatos();
    }

    private void cargarDatos() {
        List<Modelo> usuarios = usuarioDAO.selectDatos();
        //hacemos el observable
        ObservableList<Modelo> usuariosObservable = FXCollections.observableArrayList(usuarios);
        //añadimos cada usuario a la tabla
        tablaDatos.setItems(usuariosObservable);
    }

    @FXML
    void handleCalcularCalorias(ActionEvent event) {
        // hacer que el boton calcule las calorias gastadas con la formula caloriasPorMinuto x duracion x peso
        validarCampos();

        String actividad = cbActividad.getValue();
        int caloriasPorMinuto = 0;
        switch (actividad) {
            case "Caminar":
                caloriasPorMinuto = 3;
                break;
            case "Correr":
                caloriasPorMinuto = 7;
                break;
            case "Nadar":
                caloriasPorMinuto = 6;
                break;
        }
        System.out.println("Actividad: "+cbActividad.getValue());
        //calcular las calorias
        int calorias = caloriasPorMinuto * Integer.parseInt(duracion.getText()) * Integer.parseInt(peso.getText());

        System.out.println("Calorias: "+calorias);
        //mostrar las calorias
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Calorias");
        alert.setHeaderText("Calorias quemadas");
        alert.setContentText("Has quemado " + calorias + " calorias");
        alert.showAndWait();
        //guardar en la base de datos
        usuarioDAO.insertarDatos(new Modelo(nombreUsuario.getText(), actividad, String.valueOf(calorias)));

        //actualizar la tabla
        cargarDatos();

    }
    public void validarCampos(){
            //aqui validamos que se introduzcan numeros en los campos de duracion y peso y que no esten vacios
        if (duracion.getText().isEmpty() || peso.getText().isEmpty() || nombreUsuario.getText().isEmpty() || cbActividad.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error al introducir los dats");
            alert.setContentText("Por favor, rellena todos los campos");
            alert.showAndWait();
        } else {
            try {
                Integer.parseInt(duracion.getText());
                Integer.parseInt(peso.getText());
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error al introducir los datos");
                alert.setContentText("Por favor, introduce números en los campos de duración y peso");
                alert.showAndWait();
            }
        }
    }
}



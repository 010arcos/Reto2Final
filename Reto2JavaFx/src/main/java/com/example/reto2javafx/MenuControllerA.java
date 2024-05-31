package com.example.reto2javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MenuControllerA {

    @FXML
    private Button btnJugadores;

    @FXML
    private Button btnPremios;

    @FXML
    private Button btnClasificacion;

    @FXML
    private Button btnVolver;

    @FXML
    public void initialize() {
        setUpButtonHandlers();
    }

    public void setUpButtonHandlers() {
        btnJugadores.setOnAction(event -> loadScene(event, "JugadoresOpenA.fxml"));
        btnPremios.setOnAction(event -> loadScene(event, "VerPremiosOpenA.fxml"));
        btnClasificacion.setOnAction(event -> loadScene(event, "ClasificacionGeneralOpenA.fxml"));
        btnVolver.setOnAction(event -> goBack(event));
    }

    private void loadScene(ActionEvent event, String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Obtener la ventana actual desde el botón que disparó el evento
            Scene scene = new Scene(root);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void goBack(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("menu.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow(); // Obtener la ventana actual desde el botón que disparó el evento
            Scene scene = new Scene(root);
            stage.setScene(scene);

            // Obtener el controlador y configurar los manejadores de botones
            MenuController controller = loader.getController();
            controller.setUpButtonHandlers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

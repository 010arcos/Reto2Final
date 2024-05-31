package com.example.reto2javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnOpenA;

    @FXML
    private Button btnOpenB;

    @FXML
    public void initialize() {
        // Aquí solo configura TableViews y otros componentes
        setUpButtonHandlers();
    }

    public void setUpButtonHandlers() {
        btnOpenA.setOnAction(event -> loadScene(event, "menu2OpenA.fxml"));
        btnOpenB.setOnAction(event -> loadScene(event, "menu2OpenB.fxml"));
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
}
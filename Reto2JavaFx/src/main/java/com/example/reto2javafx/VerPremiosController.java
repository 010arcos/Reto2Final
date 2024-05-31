package com.example.reto2javafx;


import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


import static com.example.reto2javafx.Metodos.cnx;

public class VerPremiosController {

    @FXML
    private Button btnVolver;
    // Controlador de evento para el botón "Volver"
    public void volverClicked() {
        try {
            // Cargar la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu2OpenA.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Nueva Ventana");
            stage.show();
            Stage currentStage = (Stage) btnVolver.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void volverBVistaB() {
        try {
            // Cargar la nueva ventana B
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("menu2OpenB.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Nueva Ventana B");
            stage.show();
            Stage currentStage = (Stage) btnVolver.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



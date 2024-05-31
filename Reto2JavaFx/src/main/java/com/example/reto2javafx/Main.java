package com.example.reto2javafx;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Formattable;
import java.util.List;

public class Main extends Application {
    public static void main(String[] args) {

        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        ImportarInsertarCSV csv = new ImportarInsertarCSV();
        try{
            //Cargo la vista
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("menu.fxml"));

            Pane ventana = (Pane) loader.load();
            Scene scene = new Scene(ventana);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Reto2");
            primaryStage.show();

            MenuController controller = loader.getController();
            controller.setUpButtonHandlers();
            List<Jugador> listaPremioOpta = JugadorController.listaPremioOpta();

            JugadorController.leerGuardarArchivo(listaPremioOpta, "premioOpta");


            List<Premio> listaPremiosF = PremiosGanadosController.listaPremiosTodo();
            JugadorController.leerGuardarArchivoPremio(listaPremiosF, "premios");

            if (csv.isDatabaseEmpty()){
                List<Jugador> listaJugadoresA = csv.importarjugadoresA("/home/ALU1J/Escritorio/Reto2FinalProvisional/BaseDatos/AOpen.csv");
                List<Jugador> listaJugadoresB = csv.importarjugadoresB("/home/ALU1J/Escritorio/Reto2FinalProvisional/BaseDatos/BOpen.csv");
                csv.insertarJugador(listaJugadoresB);
                csv.insertarJugador(listaJugadoresA);

                List<Jugador> jugadoresRankFA= csv.RankFExportarA("/home/ALU1J/Escritorio/Reto2FinalProvisional/BaseDatos/clasificacionFinalOpenA.csv");
                List<Jugador> jugadoresRankFB= csv.RankFExportarB("/home/ALU1J/Escritorio/Reto2FinalProvisional/BaseDatos/clasificacionFinalOpenB.csv");
                csv.updateRankF(jugadoresRankFA, jugadoresRankFB);

                JugadorController.calcularPremios();







            }else{
                System.out.println("No se puedo insertar y actualizar en Base de Datos, ya tiene datos ");
            }










        } catch (IOException e) {
            System.out.println(e.getMessage());
        }  catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

}
package com.example.reto2javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static com.example.reto2javafx.Metodos.cnx;

public class ClasificacionGeneralOpenController {

    @FXML
    private Button btnVolver;
    @FXML
    private Button btnGenerarPremios;
    @FXML
    private Button btnBuscar;

    @FXML
    private Button btnLimpiarTabla;

    @FXML
    private TableView<Jugador> tblClasificacionB= new TableView<>();

    @FXML
    private TableView<Jugador> tblClasificacionA= new TableView<>();

    // Creamos sus columnas
    @FXML
    private TableColumn<Jugador, Integer> rankingFColumn;
    @FXML
    private TableColumn<Jugador, String> nombreColumn;
    @FXML
    private TableColumn<Jugador, Integer> fideColumn;
    @FXML
    private TableColumn<Jugador, Boolean> hotelColumn;
    @FXML
    private TableColumn<Jugador, Boolean> cvColumn;
    @FXML
    private TableColumn<Jugador, Integer> rankingIColumn;
    @FXML
    private TableColumn<Jugador, Boolean> descalificadoColumn;
    @FXML
    private TableColumn<Jugador, String> fideIDColumn;

    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtFederacion;
    @FXML
    private TextField txtFide;
    @FXML
    private TextField txtFideID;
    @FXML
    private TextField txtImporteP;
    @FXML
    private TextField txtTipoTorneo;
    @FXML
    private TextField txtRankingI;  // TextField para el ranking inicial
    @FXML
    private TextField txtRankingF;  // TextField para el ranking final
    @FXML
    private TextField txtTitulo;
    @FXML
    private TextField txtDescalificado;
    @FXML
    private TextField txtHotel;
    @FXML
    private TextField txtCV;
    @FXML
    private TextField txtBuscar;


    //añadir descalifcado

    @FXML
    public void initialize() throws SQLException {
        initTabla(tblClasificacionA, listaClasificacionA());
        initTabla(tblClasificacionB, listaClasificacionB());
    }
    private void initTabla(TableView<Jugador> tabla, ObservableList<Jugador> listaClasificacion){
        try {
            if (tabla == null) {
                System.out.println("Error: La tabla es null.");
                return;
            }

            rankingFColumn.setCellValueFactory(new PropertyValueFactory<>("rankingF"));
            rankingIColumn.setCellValueFactory(new PropertyValueFactory<>("rankingI"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            fideColumn.setCellValueFactory(new PropertyValueFactory<>("fide"));
            hotelColumn.setCellValueFactory(new PropertyValueFactory<>("hotel"));
            cvColumn.setCellValueFactory(new PropertyValueFactory<>("cv"));
            descalificadoColumn.setCellValueFactory(new PropertyValueFactory<>("descalificado"));
            fideIDColumn.setCellValueFactory(new PropertyValueFactory<>("fideID"));
            tabla.setItems(listaClasificacion);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    public ObservableList<Jugador> listaClasificacionA() throws SQLException {
        ObservableList<Jugador> clasificacionA = FXCollections.observableArrayList();
        try (
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM jugador WHERE categoria='A' ORDER BY rankingF"); //Consulta para seleccionar lo que queremos
        ) {
            while (rs.next()) {
                Jugador jugador = new Jugador(
                        rs.getInt("rankingF"),
                        rs.getInt("rankingI"),
                        rs.getString("titulo"),
                        rs.getString("nombre"),
                        rs.getString("federacion"),
                        rs.getInt("fide"),
                        rs.getString("fideID"),
                        rs.getBoolean("hotel"),
                        rs.getBoolean("cv"),
                        rs.getInt("importeP"),
                        Jugador.categoria.valueOf(rs.getString("categoria")),
                        rs.getBoolean("descalificado")
                );
                clasificacionA.add(jugador);
            }
        }
        return clasificacionA;
    }
    // Creamos una lista para guardar los jugadoresB que extraeremos mediante la consulta
    public ObservableList<Jugador> listaClasificacionB() throws SQLException {
        ObservableList<Jugador> clasificacionB = FXCollections.observableArrayList();
        try (
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM jugador WHERE categoria='B' ORDER BY rankingF"); //Consulta para seleccionar lo que queremos
        ) {
            while (rs.next()) {
                Jugador jugador = new Jugador(
                        rs.getInt("rankingI"),
                        rs.getInt("rankingF"),
                        rs.getString("titulo"),
                        rs.getString("nombre"),
                        rs.getString("federacion"),
                        rs.getInt("fide"),
                        rs.getString("fideID"),
                        rs.getBoolean("hotel"),
                        rs.getBoolean("cv"),
                        rs.getInt("importeP"),
                        Jugador.categoria.valueOf(rs.getString("categoria")),
                        rs.getBoolean("descalificado")
                );
                clasificacionB.add(jugador);
            }
        }
        return clasificacionB;
    }
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

    // Controlador de evento para el botón "Generar Premios"
    public void generarPremiosClicked() {
        try {
            // Cargar la nueva ventana
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GanadoresA.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Nueva Ventana");
            stage.show();
            Stage currentStage = (Stage) btnGenerarPremios.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
    public void generarPremiosBVistaB() {
        try {
            // Cargar la nueva ventana B
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GanadoresB.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.setTitle("Nueva Ventana B");
            stage.show();
            Stage currentStage = (Stage) btnGenerarPremios.getScene().getWindow();
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


    @FXML
    private void mostrarTodosLosJugadores(ActionEvent event) {
        // Vaciar el campo txtBuscar
        txtBuscar.setText("");

        try {
            // Mostrar todos los jugadores en ambas tablas
            tblClasificacionA.setItems(listaClasificacionA());
            tblClasificacionB.setItems(listaClasificacionB());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buscarPorFideID() {
        String fideID = txtBuscar.getText();
        if (fideID == null || fideID.isEmpty()) {
            // Si el campo está vacío, mostramos todos los jugadores
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campo Vacío");
            alert.setHeaderText("No se ha introducido un fide ID");
            alert.setContentText("Por favor ingresa un fide ID.");
            alert.showAndWait();
            return;
        }

        ObservableList<Jugador> jugadoresFiltradosA = FXCollections.observableArrayList();
        ObservableList<Jugador> jugadoresFiltradosB = FXCollections.observableArrayList();

        try {
            PreparedStatement ps = cnx.prepareStatement("SELECT * FROM jugador WHERE fideID = ?");
            ps.setString(1, fideID);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Jugador jugador = new Jugador(
                        rs.getInt("rankingI"),
                        rs.getInt("rankingF"),
                        rs.getString("titulo"),
                        rs.getString("nombre"),
                        rs.getString("federacion"),
                        rs.getInt("fide"),
                        rs.getString("fideID"),
                        rs.getBoolean("hotel"),
                        rs.getBoolean("cv"),
                        rs.getInt("importeP"),
                        Jugador.categoria.valueOf(rs.getString("categoria")),
                        rs.getBoolean("descalificado")
                );
                if (jugador.getTipoTorneo() == Jugador.categoria.A) {
                    jugadoresFiltradosA.add(jugador);
                } else if (jugador.getTipoTorneo() == Jugador.categoria.B) {
                    jugadoresFiltradosB.add(jugador);
                }
            }
            if (jugadoresFiltradosA.isEmpty() && jugadoresFiltradosB.isEmpty()) {
                // Si no se encuentran jugadores con el fideID especificado, mostramos una alerta
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Jugador no encontrado");
                alert.setContentText("No se encontró ningún jugador con el fideID especificado.");
                alert.showAndWait();
                txtBuscar.setText("");
            } else {
                tblClasificacionA.setItems(jugadoresFiltradosA);
                tblClasificacionB.setItems(jugadoresFiltradosB);
            }
            tblClasificacionA.setItems(jugadoresFiltradosA);
            tblClasificacionB.setItems(jugadoresFiltradosB);

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}


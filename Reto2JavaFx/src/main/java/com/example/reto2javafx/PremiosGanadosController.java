package com.example.reto2javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class PremiosGanadosController {
    //importante!! hacemos la conexion, podemos hacer codigo o podemos importar la conexion del archivo metodos
    public static Connection cnx;
    private static Connection getConnexion() throws SQLException {
        String url = "jdbc:mariadb://localhost:3306/AjedrezOpen";
        String user = "root";
        String password = "Debian";
        return DriverManager.getConnection(url, user, password);
    }

    static{

        try {
            cnx= getConnexion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML
    private Button btnVolver;
    @FXML
    private TableView<Premio> tblClasificacionGeneralA= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacion2200A= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacion2400A= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacionHotelA= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacionCVA= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacionGeneralB= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacion1800B= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacion1600B= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacion1400B= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacionCVB= new TableView<>();

    @FXML
    private TableView<Premio> tblClasificacionHotelB= new TableView<>();


    //TABLA PREMIOS A (COLUMNAS)
    @FXML
    private TableColumn<Premio, Integer> puestoClmGeneralA = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClmGeneralA = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClmGeneralA = new TableColumn<>();

    // Creamos sus columnas
    @FXML
    private TableColumn<Premio, Integer> puestoClmCVA = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClmCVA = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClmCVA = new TableColumn<>();


    @FXML
    private TableColumn<Premio, Integer> puestoClmHA = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClmHA = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClmHA = new TableColumn<>();



    @FXML
    private TableColumn<Premio, Integer> puestoClm2400A = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClm2400A = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClm2400A = new TableColumn<>();


    @FXML
    private TableColumn<Premio, Integer> puestoClm2200A = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClm2200A = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClm2200A = new TableColumn<>();




    //TABLA PREMIOS B (COLUMNAS)
    @FXML
    private TableColumn<Premio, Integer> puestoClmGeneralB = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClmGeneralB = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClmGeneralB = new TableColumn<>();

    // Creamos sus columnas
    @FXML
    private TableColumn<Premio, Integer> puestoClmCVB = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClmCVB = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClmCVB = new TableColumn<>();


    @FXML
    private TableColumn<Premio, Integer> puestoClmHB = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClmHB = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClmHB = new TableColumn<>();


    @FXML
    private TableColumn<Premio, Integer> puestoClm1800B = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClm1800B = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClm1800B = new TableColumn<>();

    @FXML
    private TableColumn<Premio, Integer> puestoClm1600B = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClm1600B = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClm1600B = new TableColumn<>();

    @FXML
    private TableColumn<Premio, Integer> puestoClm1400B = new TableColumn<>();
    @FXML
    private TableColumn<Premio, String> nombreClm1400B = new TableColumn<>();
    @FXML
    private TableColumn<Premio, Integer> importeClm1400B = new TableColumn<>();





    @FXML
    public void initialize() throws SQLException {


        initTablaA(tblClasificacionGeneralA, listaPremios('A', "general"));
        initTablaA(tblClasificacion2400A, listaPremios('A', "sub2400"));
        initTablaA(tblClasificacion2200A, listaPremios('A', "sub2200"));
        initTablaA(tblClasificacionCVA, listaPremios('A', "cv"));
        initTablaA(tblClasificacionHotelA, listaPremios('A', "h"));




        initTablaB(tblClasificacionGeneralB, listaPremios('B', "general"));
        initTablaB(tblClasificacion1800B, listaPremios('B', "SUB1800"));
        initTablaB(tblClasificacion1600B, listaPremios('B', "sub1600"));
        initTablaB(tblClasificacion1400B, listaPremios('B', "sub1400"));
        initTablaB(tblClasificacionCVB, listaPremios('B', "cv"));
        initTablaB(tblClasificacionHotelB, listaPremios('B', "h"));










    }


    private void initTablaA(TableView<Premio> tabla, ObservableList<Premio> listajugadores) {
        try {
            if (tabla == null) {
                System.out.println("Error: La tabla es null.");
                return;
            }





            puestoClmGeneralA.setCellValueFactory(new PropertyValueFactory<>("puesto"));
            nombreClmGeneralA.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            importeClmGeneralA.setCellValueFactory(new PropertyValueFactory<>("importe"));

            puestoClmCVA.setCellValueFactory(new PropertyValueFactory<>("puesto"));
            nombreClmCVA.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            importeClmCVA.setCellValueFactory(new PropertyValueFactory<>("importe"));

            importeClmHA.setCellValueFactory(new PropertyValueFactory<>("importe"));
            nombreClmHA.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            puestoClmHA.setCellValueFactory(new PropertyValueFactory<>("puesto"));


            importeClm2200A.setCellValueFactory(new PropertyValueFactory<>("importe"));
            nombreClm2200A.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            puestoClm2200A.setCellValueFactory(new PropertyValueFactory<>("puesto"));


            importeClm2400A.setCellValueFactory(new PropertyValueFactory<>("importe"));
            nombreClm2400A.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            puestoClm2400A.setCellValueFactory(new PropertyValueFactory<>("puesto"));





            tabla.setItems(listajugadores);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initTablaB(TableView<Premio> tabla, ObservableList<Premio> listajugadores) {
        try {
            if (tabla == null) {
                System.out.println("Error: La tabla es null.");
                return;
            }







            puestoClmGeneralB.setCellValueFactory(new PropertyValueFactory<>("puesto"));
            nombreClmGeneralB.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            importeClmGeneralB.setCellValueFactory(new PropertyValueFactory<>("importe"));


            puestoClmCVB.setCellValueFactory(new PropertyValueFactory<>("puesto"));
            nombreClmCVB.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            importeClmCVB.setCellValueFactory(new PropertyValueFactory<>("importe"));



            importeClmHB.setCellValueFactory(new PropertyValueFactory<>("importe"));
            nombreClmHB.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            puestoClmHB.setCellValueFactory(new PropertyValueFactory<>("puesto"));



            importeClm1800B.setCellValueFactory(new PropertyValueFactory<>("importe"));
            nombreClm1800B.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            puestoClm1800B.setCellValueFactory(new PropertyValueFactory<>("puesto"));

            importeClm1600B.setCellValueFactory(new PropertyValueFactory<>("importe"));
            nombreClm1600B.setCellValueFactory(new PropertyValueFactory<>("nombreF"));
            puestoClm1600B.setCellValueFactory(new PropertyValueFactory<>("puesto"));


            importeClm1400B.setCellValueFactory(new PropertyValueFactory<>("importe"));
            puestoClm1400B.setCellValueFactory(new PropertyValueFactory<>("puesto"));
            nombreClm1400B.setCellValueFactory(new PropertyValueFactory<>("nombreF"));



            tabla.setItems(listajugadores);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Creamos una lista para guardar los jugadoresB que extraeremos mediante la consulta
    public static ObservableList<Premio> listaPremios(char categoria, String tipo) throws SQLException {
        ObservableList<Premio> premios = FXCollections.observableArrayList();
        String sql = "SELECT puesto, nombre, importe FROM premio WHERE categoria = ? AND tipo = ? ";

        try (
                PreparedStatement pst = cnx.prepareStatement(sql)
        ) {
            pst.setString(1, String.valueOf(categoria));
            pst.setString(2, tipo);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Premio premio = new Premio(
                            rs.getInt("puesto"),
                            rs.getString("nombre"),
                            rs.getInt("importe")
                    );
                    premios.add(premio);
                }
            }
        }
        return premios;
    }


    // Creamos una lista para guardar los jugadoresB que extraeremos mediante la consulta
    // Método para obtener la lista de todos los premios y ordenarlos por rankingI
    public static ObservableList<Premio> listaPremiosTodo() throws SQLException {
        ObservableList<Premio> premios = FXCollections.observableArrayList();
        String sql = "select puesto, categoria, tipo, nombre, importe from premio";

        try (
                PreparedStatement pst = cnx.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()
        ) {
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                Premio.Categoria categoria = Premio.Categoria.valueOf(rs.getString("categoria")); // Suponiendo que la clase Categoria tiene un método valueOf
                int puesto = rs.getInt("puesto");
                int importe = rs.getInt("importe");
                String nombre = rs.getString("nombre");
                Premio premio = new Premio(tipo, categoria, puesto, importe, nombre);
                premios.add(premio);
            }
        }
        return premios;
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

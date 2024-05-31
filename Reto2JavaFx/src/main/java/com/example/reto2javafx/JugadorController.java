package com.example.reto2javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class JugadorController {
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
    private TableView<Jugador> tblJugadoresB = new TableView<>();

    @FXML
    private TableView<Jugador> tblJugadoresA = new TableView<>();


    // Creamos sus columnas
    @FXML
    private TableColumn<Jugador, Integer> rankingIColumn = new TableColumn<>();
    // @FXML comentado por pruebas
    //private TableColumn<Jugador, Integer> rankingFColumn;
    //  @FXML
    // private TableColumn<Jugador, String> tituloColumn;
    @FXML
    private TableColumn<Jugador, String> nombreColumn = new TableColumn<>();
    //@FXML
    // private TableColumn<Jugador, String> apellidosColumn;
    @FXML
    private TableColumn<Jugador, String> federacionColumn = new TableColumn<>();
    @FXML
    private TableColumn<Jugador, Integer> fideColumn = new TableColumn<>();
    @FXML
    private TableColumn<Jugador, String> fideIDColumn = new TableColumn<>();
    @FXML
    private TableColumn<Jugador, Boolean> hotelColumn = new TableColumn<>();
    @FXML
    private TableColumn<Jugador, Boolean> cvColumn = new TableColumn<>();
    //@FXML
    // private TableColumn<Jugador, Integer> importePColumn;
    @FXML
    private TableColumn<Jugador, Jugador.categoria> tipoTorneoColumn = new TableColumn<>();
    @FXML
    private TableColumn<Jugador, Boolean> descalificadoColumn = new TableColumn<>();
    @FXML
    private TextField txtNombre = new TextField();
    @FXML
    private TextField txtApellidos = new TextField();
    @FXML
    private TextField txtFederacion = new TextField();
    @FXML
    private TextField txtFide = new TextField();
    @FXML
    private TextField txtFideID = new TextField();
    @FXML
    private TextField txtImporteP = new TextField();
    @FXML
    private TextField txtTipoTorneo = new TextField();
    @FXML
    private TextField txtRankingI = new TextField();  // TextField para el ranking inicial
    @FXML
    private TextField txtRankingF = new TextField();  // TextField para el ranking final
    @FXML
    private TextField txtTitulo = new TextField();
    @FXML
    private TextField txtDescalificado = new TextField();
    @FXML
    private TextField txtHotel = new TextField();
    @FXML
    private TextField txtCV = new TextField();
    @FXML
    private TextField txtBuscar = new TextField();
    //añadir descalifcado


    @FXML
    private Button btnVolver;
    @FXML
    private Button btnBuscar;
    @FXML
    private Button btnOptar;

    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnModificar;


    @FXML
    public void initialize() throws SQLException {
        initTabla(tblJugadoresA, listaJugadoresA());
        initTabla(tblJugadoresB, listaJugadoresB());
    }


    private void initTabla(TableView<Jugador> tabla, ObservableList<Jugador> listajugadores) {
        try {
            if (tabla == null) {
                System.out.println("Error: La tabla es null.");
                return;
            }
            rankingIColumn.setCellValueFactory(new PropertyValueFactory<>("rankingI"));
            nombreColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            fideColumn.setCellValueFactory(new PropertyValueFactory<>("fide"));
            fideIDColumn.setCellValueFactory(new PropertyValueFactory<>("fideID"));
            tipoTorneoColumn.setCellValueFactory(new PropertyValueFactory<>("tipoTorneo"));
            federacionColumn.setCellValueFactory(new PropertyValueFactory<>("federacion"));
            hotelColumn.setCellValueFactory(new PropertyValueFactory<>("hotel"));
            cvColumn.setCellValueFactory(new PropertyValueFactory<>("cv"));
            descalificadoColumn.setCellValueFactory(new PropertyValueFactory<>("descalificado"));

            tabla.setItems(listajugadores);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Creamos una lista para guardar los jugadoresA que extraeremos mediante la consulta
//ver si marco lo retorna como lista y no como observable list
    public ObservableList<Jugador> listaJugadoresA() throws SQLException {
        ObservableList<Jugador> jugadoresA = FXCollections.observableArrayList();
        try (
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM jugador WHERE categoria='A' ORDER BY rankingI"); //Consulta para seleccionar lo que queremos
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
                jugadoresA.add(jugador);
            }
        }
        return jugadoresA;
    }

    // Creamos una lista para guardar los jugadoresB que extraeremos mediante la consulta
    public ObservableList<Jugador> listaJugadoresB() throws SQLException {
        ObservableList<Jugador> jugadoresB = FXCollections.observableArrayList();
        try (
                Statement st = cnx.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM jugador WHERE categoria='B' ORDER BY rankingI"); //Consulta para seleccionar lo que queremos
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
                jugadoresB.add(jugador);
            }
        }
        return jugadoresB;
    }


    @FXML
    private void seleccionarJugadorA(MouseEvent event) {
        Jugador jugador = tblJugadoresA.getSelectionModel().getSelectedItem();
        if (jugador != null) {
            txtNombre.setText(jugador.getNombre());
            txtFederacion.setText(jugador.getFederacion());
            txtFide.setText(String.valueOf(jugador.getFide()));
            txtFideID.setText(jugador.getFideID());
            txtHotel.setText(jugador.isHotel() ? "True" : "False"); // Usamos 'is' porque cuando creamos el objeto jugador se genera de manera predeterminada
            txtCV.setText(String.valueOf(jugador.isCv())); // Este caso realiza la misma funcion que el 'is', solo que en este caso usamos un operador ternario. (cuando es true muestra tru y cuando es false muestra false).
            txtImporteP.setText(String.valueOf(jugador.getImporteP()));
            txtTipoTorneo.setText(jugador.getTipoTorneo().toString());
            txtRankingI.setText(String.valueOf(jugador.getRankingI())); // Para mostrar el ranking inicial
            txtRankingF.setText(String.valueOf(jugador.getRankingF())); // Para mostrar el ranking final
            txtTitulo.setText(jugador.getTitulo());
            txtDescalificado.setText(jugador.isDescalificado() ? "True" : "False");
        }
    }

    @FXML
    private void seleccionarJugadorB(MouseEvent event) {
        Jugador jugador = tblJugadoresB.getSelectionModel().getSelectedItem();
        if (jugador != null) {
            txtNombre.setText(jugador.getNombre());
            txtFederacion.setText(jugador.getFederacion());
            txtFide.setText(String.valueOf(jugador.getFide()));
            txtFideID.setText(jugador.getFideID());
            txtHotel.setText(jugador.isHotel() ? "True" : "False"); // Usamos 'is' porque cuando creamos el objeto jugador se genera de manera predeterminada
            txtCV.setText(String.valueOf(jugador.isCv())); // Este caso realiza la misma funcion que el 'is', solo que en este caso usamos un operador ternario. (cuando es true muestra tru y cuando es false muestra false).
            txtImporteP.setText(String.valueOf(jugador.getImporteP()));
            txtTipoTorneo.setText(jugador.getTipoTorneo().toString());
            txtRankingI.setText(String.valueOf(jugador.getRankingI())); // Para mostrar el ranking inicial
            txtRankingF.setText(String.valueOf(jugador.getRankingF())); // Para mostrar el ranking final
            txtTitulo.setText(jugador.getTitulo());
            txtDescalificado.setText(jugador.isDescalificado() ? "True" : "False");
        }
    }


    @FXML
    public void altaJugador() {
        try {
            PreparedStatement ps = cnx.prepareStatement(
                    "INSERT INTO jugador(rankingI, rankingF, titulo, nombre, federacion, fide, fideID, hotel, CV, importeP, categoria,descalificado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");

            String nombre = txtNombre.getText();
            String federacion = txtFederacion.getText();
            int fide = Integer.parseInt(txtFide.getText());
            String fideID = txtFideID.getText();
            boolean hotel = Boolean.parseBoolean(txtHotel.getText());
            boolean cv = Boolean.parseBoolean(txtCV.getText());
            int importeP = Integer.parseInt(txtImporteP.getText());
            String categoria = txtTipoTorneo.getText();

            int rankingI = Integer.parseInt(txtRankingI.getText()); // Obtener el valor del ranking inicial desde el campo de texto
            int rankingF = Integer.parseInt(txtRankingF.getText()); // Obtener el valor del ranking final desde el campo de texto
            String titulo = txtTitulo.getText(); // Obtener el valor del título desde el campo de texto
            boolean descalificado = Boolean.parseBoolean(txtDescalificado.getText());

            ps.setInt(1, rankingI);
            ps.setInt(2, rankingF);
            ps.setString(3, titulo);
            ps.setString(4, nombre);
            ps.setString(5, federacion);
            ps.setInt(6, fide);
            ps.setString(7, fideID);
            ps.setBoolean(8, hotel);
            ps.setBoolean(9, cv);
            ps.setInt(10, importeP);
            ps.setString(11, categoria);
            ps.setBoolean(12, descalificado);

            ps.executeUpdate();

            // Actualizar la tabla
            tblJugadoresA.setItems(listaJugadoresA());
            tblJugadoresB.setItems(listaJugadoresB());

            System.out.println("Jugador registrado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Manejar la excepción adecuadamente aquí
        }
    }


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

            try {
                tblJugadoresA.setItems(listaJugadoresA());
                tblJugadoresB.setItems(listaJugadoresB());
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            } else {
                tblJugadoresA.setItems(jugadoresFiltradosA);
                tblJugadoresB.setItems(jugadoresFiltradosB);
            }

            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void mostrarTodosLosJugadores(ActionEvent event) {
        // Vaciar el campo txtBuscar
        txtBuscar.setText("");

        try {
            // Mostrar todos los jugadores en ambas tablas
            tblJugadoresA.setItems(listaJugadoresA());
            tblJugadoresB.setItems(listaJugadoresB());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Premio> optarPremio(String fideID) throws SQLException { // ver si en vez de poner el fide id, ponemos el rnakig etc, otra si se pincha en el jugador q aparezca esto, ines y alex tiene q hacerlo
        List<Premio> premios = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            cnx = DriverManager.getConnection("jdbc:mariadb://localhost:3306/AjedrezOpen", "root", "Debian");

            String sql = "SELECT fide, hotel, cv, categoria, descalificado FROM jugador WHERE fideID = ?";
            ps = cnx.prepareStatement(sql);
            ps.setString(1, fideID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int fide = rs.getInt("fide");
                int hotel = rs.getInt("hotel");
                int cv = rs.getInt("CV");
                String categoria = rs.getString("categoria");
                int descalificado = rs.getInt("descalificado");


                if (descalificado==0) {


                    if (categoria.equals("A")) {
                        premios.add(new Premio("general", "General"));

                        if (fide < 2400) {
                            premios.add(new Premio("sub2400", "Torneosub 2400"));
                        }

                        if (fide < 2200) {
                            premios.add(new Premio("sub2200", "Torneosub 2200"));
                        }

                        if (hotel == 1) {
                            premios.add(new Premio("h", "Hotel"));
                        }

                        if (cv == 1) {
                            premios.add(new Premio("cv", "CV"));
                        }
                    } else if (categoria.equals("B")) {

                        premios.add(new Premio("general", "General"));
                        if (fide < 1800) {
                            premios.add(new Premio("sub1800", "Torneosub 1800"));
                        }

                        if (fide < 1600) {
                            premios.add(new Premio("sub1600", "Torneosub 1600"));
                        }
                        if (fide < 1400) {
                            premios.add(new Premio("sub1400", "Torneosub 1400"));
                        }

                        if (hotel == 1) {
                            premios.add(new Premio("h", "Hotel"));
                        }

                        if (cv == 1) {
                            premios.add(new Premio("cv", "CV"));
                        }

                    }
                }else {
                    premios.add(new Premio("NoPremio", "No opta a ningún Premio"));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        }
        return premios;
    }

    //premios a los que opta un jugador
    public static List<Jugador> listaPremioOpta() throws SQLException { // este metodo me devuelve una lista de jugadores con su nombre rannking su categoria y sobretod un array de la lista de los premiso a los que opta
        List<Jugador> jugadores = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT fide, hotel, cv, categoria, nombre, rankingI, rankingF FROM jugador ORDER BY rankingF "; // saco las columnas que me interesan y las saco ordenados por categegoria (aunq creoq es indiferente)
            ps = cnx.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int fide = rs.getInt("fide");
                int hotel = rs.getInt("hotel");
                int cv = rs.getInt("CV");
                int rankingF = rs.getInt("rankingF");
                Jugador.categoria categoria = Jugador.categoria.valueOf(rs.getString("categoria"));
                String nombre = rs.getString("nombre");// obtengo nombre y ranking para luego mostrarlo y sea mas visual
                int rankingI = rs.getInt("rankingI");
                Jugador jugador = new Jugador(rankingI, nombre, categoria, rankingF); // creo el nuevo objeto jugador con los atributos que me interesan mostrar, no hace falta poner el array, ya esta inizializado en la clase

                List<Premio> premios = new ArrayList<>(); // creo una lista de premios para ir añadiendo los premios
                // Todos optan al premio general


                //Con los datos obtenidos de la cosnulta realizaos este cribado, para decidir que premios puede recibir
                if (categoria.equals(Jugador.categoria.A)) {

                    premios.add(new Premio("general", "General"));// Todos optan al premio general

                    if (fide < 2400) {
                        premios.add(new Premio("sub2400", "Torneosub2400"));
                    }

                    if (fide < 2200) {
                        premios.add(new Premio("sub2200", "Torneosub2200"));
                    }

                    if (hotel == 1) {
                        premios.add(new Premio("h", "Hotel"));
                    }

                    if (cv == 1) {
                        premios.add(new Premio("cv", "CV"));
                    }
                } else if (categoria.equals(Jugador.categoria.B)) {
                    premios.add(new Premio("general", "General"));

                    if (fide < 1800) {
                        premios.add(new Premio("sub1800", "Torneosub1800"));
                    }

                    if (fide < 1600) {
                        premios.add(new Premio("sub1600", "Torneosub1600"));
                    }
                    if (fide < 1400) {
                        premios.add(new Premio("sub1400", "Torneosub1400"));
                    }

                    if (hotel == 1) {
                        premios.add(new Premio("h", "Hotel"));
                    }

                    if (cv == 1) {
                        premios.add(new Premio("cv", "CV"));
                    }

                }
                //meto los premios en la lista de premios, previamente se he tenido que retocar el la clase jugador para que esto funcione
                jugador.setPremios(premios);

                // Agregamos los jugadores con sus atributos y el array de premios que hemos añadido antes
                jugadores.add(jugador);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            /*cnx.close(); si pongo esto se va a la mrd*/

        }
        return jugadores;
    }

    @FXML
    private void btnOptarClicked() {
        Jugador jugadorSeleccionado = null;

        // Comprueba si hay un jugador seleccionado en cualquiera de las tablas
        if (tblJugadoresA.getSelectionModel().getSelectedItem() != null) {
            jugadorSeleccionado = tblJugadoresA.getSelectionModel().getSelectedItem();
        } else if (tblJugadoresB.getSelectionModel().getSelectedItem() != null) {
            jugadorSeleccionado = tblJugadoresB.getSelectionModel().getSelectedItem();
        }

        if (jugadorSeleccionado != null) {
            try {
                List<Premio> premios = optarPremio(jugadorSeleccionado.getFideID());
                // Cargar el nuevo FXML y mostrar los premios
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PremiosOpta.fxml"));
                AnchorPane premiosOptaPane = fxmlLoader.load();
                PremiosOptaController premiosOptaController = fxmlLoader.getController();
                premiosOptaController.mostrarPremios(premios); // Pasa los premios al controlador de la ventana de premios
                Stage stage = new Stage();
                stage.setScene(new Scene(premiosOptaPane));
                stage.setTitle("Premios");
                stage.show();
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No hay ningún jugador seleccionado.");
        }
    }

    @FXML
    private void limpiar(ActionEvent event) {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtFederacion.setText("");
        txtFide.setText("");
        txtFideID.setText("");
        txtRankingI.setText("");
        txtCV.setText("");
        txtHotel.setText("");


    }

    @FXML
    public void eliminarJugador() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String fideId = txtFideID.getText(); // Obtener fideID desde TextField

            if (fideId == null || fideId.isEmpty()) {
                System.out.println("FideID no válido. Operación cancelada.");
                return;
            }

            // Consultar si existe un jugador con el fideID especificado
            ps = cnx.prepareStatement("SELECT * FROM jugador WHERE fideID = ?");
            ps.setString(1, fideId);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Si se encuentra el jugador, proceder con la eliminación
                ps = cnx.prepareStatement("DELETE FROM jugador WHERE fideID = ?");
                ps.setString(1, fideId);
                int filasAfectadas = ps.executeUpdate(); // devuelve el numero de filas, deberia ser 1, por eso ponemos que sea mayor que 0

                if (filasAfectadas > 0) {
                    System.out.println("Jugador eliminado correctamente.");
                    // Actualizar la tabla, si es necesario
                    tblJugadoresA.setItems(listaJugadoresA());
                    tblJugadoresB.setItems(listaJugadoresB());
                } else {
                    System.out.println("No se pudo eliminar el jugador.");
                }
                ps.close(); // Cerramos
            } else {
                System.out.println("No se encontró ningún jugador con " + fideId);
            }
        } catch (SQLException e) {

            e.printStackTrace();
            System.out.println("Error al eliminar jugador: " + e.getMessage());
        } finally {
            // Cerramos la conexion, el statement y el result
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void modificarJugador() {
        Jugador jugadorSeleccionado = null;

        // Comprueba si hay un jugador seleccionado en cualquiera de las tablas
        if (tblJugadoresA.getSelectionModel().getSelectedItem() != null) {
            jugadorSeleccionado = tblJugadoresA.getSelectionModel().getSelectedItem();
        } else if (tblJugadoresB.getSelectionModel().getSelectedItem() != null) {
            jugadorSeleccionado = tblJugadoresB.getSelectionModel().getSelectedItem();
        }

        if (jugadorSeleccionado != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("modificarJug.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Modificar Jugador");

                ModificarController controller = loader.getController();
                controller.setJugador(jugadorSeleccionado);

                stage.setOnHidden(event -> {
                    try {
                        actualizarTablas();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Añadir un listener al evento onHidden de la ventana de modificación
                stage.show();  // Mostrar la nueva ventana

            } catch (IOException e) {
                throw new RuntimeException(e);

            }
        }
    }
    @FXML
    public void actualizarTablas() throws SQLException {
        tblJugadoresA.getItems().clear();
        tblJugadoresB.getItems().clear();

        // Asume que hay métodos para obtener los jugadores de ambas listas
        List<Jugador> jugadoresA = listaJugadoresA();
        List<Jugador> jugadoresB = listaJugadoresB();

        tblJugadoresA.getItems().addAll(jugadoresA);
        tblJugadoresB.getItems().addAll(jugadoresB);
    }

    //guardar en un archivo los premios a los que opta un jugador
    public static void leerGuardarArchivo(List<Jugador> jugadores, String nombreArchivo) throws IOException {
        try (FileWriter fw = new FileWriter(nombreArchivo)) {
            fw.write("Premios a los que optan los jugadores\n\n");
            for (Jugador jugador : jugadores) {
                fw.write(jugador.getRankingI() + " " + "Torneo: " + jugador.getTipoTorneo() + " - " + jugador.getNombre() + "\n");
                for (Premio premio : jugador.getPremios()) {
                    fw.write("\tPremio: " + premio.getTipo() + "\n");
                }
                fw.write("\n"); // Esapacio despues de mostrar el ultimo premio del jugador
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //guardar en un archivo los premios a los que opta un jugador
    public static void leerGuardarArchivoPremio(List<Premio> jugadores, String nombreArchivo) throws IOException {
        try (FileWriter fw = new FileWriter(nombreArchivo)) {
            fw.write("Premios jugadores\n\n");
            for (Premio premio : jugadores) {
                fw.write(   premio.getTipoTorneo() +  " " + premio.getPuesto() + " " + "Torneo: " + premio.getTipo()+ "  " +premio.getNombreF()+ " "  + premio.getImporte()+  "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }




    public static List<Jugador> calcularPremios() throws SQLException {
        List<Jugador> jugadoresClasificacion = listaPremioOpta();
        for (Jugador jugador : jugadoresClasificacion) {
            List<Premio> premios2 = jugador.getPremios();
            PreparedStatement ps = null;
            ResultSet rs = null;
            try {
                String[] tipos = new String[premios2.size()];
                for (int i = 0; i < premios2.size(); i++) {
                    tipos[i] = premios2.get(i).getTipo();
                }

                String tiposParaInClause = "\"" + String.join("\", \"", tipos) + "\"";

                String sql = String.format("""
                        SELECT p.*
                        FROM premio p
                        WHERE p.categoria = '%s'
                        AND p.tipo IN (%s)
                        AND p.rankingI IS NULL
                        ORDER BY p.importe DESC, p.prioridad ASC
                        LIMIT 1;
                        """, jugador.getTipoTorneo().toString(), tiposParaInClause);

                ps = cnx.prepareStatement(sql);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String tipo = rs.getString("tipo");
                    Premio.Categoria tipoTorneo = Premio.Categoria.valueOf(rs.getString("categoria"));
                    int puesto = rs.getInt("puesto");
                    int importe = rs.getInt("importe");
                    int prioridad = rs.getInt("prioridad");

                    Premio premioGanado = new Premio(tipo, tipoTorneo, puesto, importe, jugador.getRankingI(), prioridad);

                    // actualizar premio en bbdd asignando rankingI jugador.getRankingI del jugador
                    sql = "UPDATE premio SET rankingI = ?, nombre = ? WHERE tipo = ? AND categoria = ? AND puesto = ?";
                    ps = cnx.prepareStatement(sql);
                    ps.setInt(1, premioGanado.getRankingI());
                    ps.setString(2, jugador.getNombre());
                    ps.setString(3, premioGanado.getTipo());
                    ps.setString(4, premioGanado.getTipoTorneo().toString());
                    ps.setInt(5, premioGanado.getPuesto());

                    int rowsUpdated = ps.executeUpdate();
                    if (rowsUpdated > 0) {
                        System.out.println("El premio se actualizó correctamente.");
                    }
                    jugador.setPremioGanado(premioGanado);

                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();


                }
            }
        }
        return jugadoresClasificacion;
    }
}

package com.example.reto2javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;




public class ModificarController {
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
    private Button btnLimpiar;

    @FXML
    private Button btnModificar;

    @FXML
    private Label labTitulo;

    @FXML
    private Label lblFideID;

    @FXML
    private TextField txtCV;

    @FXML
    private TextField txtDescalificado;

    @FXML
    private TextField txtFide;

    @FXML
    private TextField txtHotel;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtTipoTorneo;

    @FXML
    private TextField txtFideID;

    private Jugador jugador;

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
        txtNombre.setText(jugador.getNombre());
        txtFide.setText(String.valueOf(jugador.getFide()));
        txtFideID.setText(jugador.getFideID());
        txtHotel.setText(String.valueOf(jugador.isHotel()));
        txtCV.setText(String.valueOf(jugador.isCv()));
        txtTipoTorneo.setText(String.valueOf(jugador.getTipoTorneo()));
        txtDescalificado.setText(String.valueOf(jugador.isDescalificado()));


    }

    @FXML
    void limpiar(ActionEvent event) {
        txtNombre.clear();
        txtFide.clear();
        txtFideID.clear();
        txtHotel.clear();
        txtCV.clear();
        txtDescalificado.clear();
        txtTipoTorneo.clear();
    }

    @FXML
    public void modificarJugador() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String fideId = txtFideID.getText(); // Obtener fideID desde TextField

            if (fideId == null || fideId.isEmpty()) {
                System.out.println("No se encontró ningún jugador con fideID: " + fideId);
                return;
            }

            // Consultar si existe un jugador con el fideID especificado
            ps = cnx.prepareStatement("SELECT * FROM jugador WHERE fideID = ?");
            ps.setString(1, fideId);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Si se encuentra el jugador, proceder con la actualización
                String sqlUpdate = "UPDATE jugador SET nombre = ?, fide = ?, hotel = ?, CV = ?, categoria = ?, descalificado = ? WHERE fideID = ?";
                ps = cnx.prepareStatement(sqlUpdate);

                // Obtener los valores desde los TextField
                String nombre = txtNombre.getText();
                int fide = Integer.parseInt(txtFide.getText());
                boolean hotel = Boolean.parseBoolean(txtHotel.getText());
                boolean cv = Boolean.parseBoolean(txtCV.getText());
                String categoria = txtTipoTorneo.getText();
                boolean descalificado = Boolean.parseBoolean(txtDescalificado.getText());

                // Establecer los valores en el PreparedStatement
                ps.setString(1, nombre);
                ps.setInt(2, fide);
                ps.setBoolean(3, hotel);
                ps.setBoolean(4, cv);
                ps.setString(5, categoria);
                ps.setBoolean(6, descalificado);
                ps.setString(7, fideId);

                int filasAfectadas = ps.executeUpdate();

                if (filasAfectadas > 0) {
                    System.out.println("Jugador modificado correctamente.");
                    //cerar ventana
                    Stage stage = (Stage) btnModificar.getScene().getWindow();
                    stage.close();

                    //act
                    JugadorController jugadorController = new JugadorController();
                    jugadorController.actualizarTablas();
                } else {
                    System.out.println("No se pudo modificar el jugador.");
                }
            } else {
                System.out.println("No se encontró ningún jugador con fideID: " + fideId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al modificar jugador: " + e.getMessage());
        } finally {
            // Cerrar ResultSet y PreparedStatement
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}

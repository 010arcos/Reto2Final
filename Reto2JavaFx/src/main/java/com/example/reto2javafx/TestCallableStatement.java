package com.example.reto2javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestCallableStatement {

	public static void main(String[] args) {
		try (Connection cnx = getConnexion();
				CallableStatement cstm = cnx.prepareCall("{call numeroPersonasPorNombre ( ?, ? )}",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {
			System.out.println("rellenar la ordenación del resultado (ASC o DESC) :");
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String ordenar= br.readLine();
			// Aplicación de las parámetros
			cstm.setString(1, ordenar);
			cstm.registerOutParameter(2, java.sql.Types.INTEGER);
			// Ejecución de la consulta
			boolean existeResultado = cstm.execute();
			// Recuperación del parámetro del tipo out
			int numeroPersonas = cstm.getInt(2);
			System.out.println("número de personas: " + numeroPersonas);

			if (existeResultado) {
				ResultSet rs = cstm.getResultSet();
				while (rs.next()) {
					System.out.println(rs.getString("nombre") + ": " + rs.getInt("numeroPersonas") + " personas");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static Connection getConnexion() throws SQLException {
		String url = "jdbc:mariadb://localhost:3306/AjedrezOpen";
		String user = "root";
		String password = "Debian";
		return DriverManager.getConnection(url, user, password);
	}

}

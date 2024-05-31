package com.example.reto2javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestPreparedStatement {

	public static void main(String[] args) {
		try (Connection cnx = getConnexion();
				PreparedStatement pstm = cnx.prepareStatement("select * from PERSONAS where nombre = ?",
						ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("rellenar el nombre de la persona buscada :");
			String nombre = br.readLine();
			//Aplicar los parámetros
			pstm.setString(1, nombre);
			//Ejecutar la consulta y obtener el resultado
			ResultSet rs = pstm.executeQuery();
			//Mientras hay registros
			while (rs.next()) {

				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					System.out.print(rs.getString(i) + "\t");
				}
				System.out.println();
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

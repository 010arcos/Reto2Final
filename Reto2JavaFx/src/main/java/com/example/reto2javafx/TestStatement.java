package com.example.reto2javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public class TestStatement {

	public static void main(String[] args) {
		try (Connection cnx = getConnexion();
				Statement stm = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("rellenar su instrucción SQL :");
			String consulta = br.readLine();
			boolean resultado = stm.execute(consulta);




			if (resultado) {
				System.out.println("su instrucción ha generado un juego de registros");
				//Recuperación del juego de resultado
				ResultSet rs = stm.getResultSet();
				//Posicionamiento en el último registro


				rs.last();
				//Reading de número de la línea
				System.out.println("contiene " + rs.getRow() + " registros");

			} else {
				System.out.println("su instrucción ha modificado de las registros en la base");
				System.out.println("número de registros modificados :" + stm.getUpdateCount());
			}
		} catch (SQLException e) {
			System.out.println("su instrucción no ha funcionado correctamente");
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

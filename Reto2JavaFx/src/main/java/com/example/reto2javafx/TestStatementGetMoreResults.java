package com.example.reto2javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestStatementGetMoreResults {

	public static void main(String[] args) {
		boolean resultado;
		ResultSet rs;
		try (Connection cnx = getConnexion();
				Statement stm = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("rellenar sus instrucciones SQL separadas por ; :");
			String consulta = br.readLine();
			resultado = stm.execute(consulta);
			int i = 1;
			// tratamiento del resultado generado por la primera
			// instrucción
			if (resultado) {
				System.out.println("su instrucción N° " + i + " ha generado un juego de registros");
				rs = stm.getResultSet();
				rs.last();
				System.out.println("contiene " + rs.getRow() + " registros");
			} else {
				System.out.println("su instrucción N° " + i + " ha modificado los registros en la base");
				System.out.println("número de registros modificados :" + stm.getUpdateCount());
			}
			i++;
			// movimiento del puntero sobre el eventual resultado
			// siguiente
			resultado = stm.getMoreResults();
			// bucle miestra que haya un resultado de tipo de juego
			// de registro -> resultado==true
			// o que haya un resultado de tipo número
			// de registros modificados -> getUpdateCount != -1
			
			while (resultado || stm.getUpdateCount() != -1) {
				if (resultado) {
					System.out.println("su instrucción N° " + i + " ha generado un juego de registros");
					rs = stm.getResultSet();
					rs.last();
					System.out.println("contiene " + rs.getRow() + " registros");
				} else {
					System.out.println("su instrucción N° " + i + " ha modificado los registros en la base");
					System.out.println("número de registros modificados :" + stm.getUpdateCount());
				}
				i++;
				// movimiento del puntero sobre el eventual resultado
				// siguiente
				resultado = stm.getMoreResults();
			}
		} catch (SQLException e) {
			System.out.println("su instruccion no ha funcionado correctamente");
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

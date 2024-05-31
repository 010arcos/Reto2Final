package com.example.reto2javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestStatementBatch {

	public static void main(String[] args) {
		try (Connection cnx = getConnexion();
				Statement stm = cnx.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY)) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("rellenar sus instrucciones SQL y ejecutarlas en lote :");
			String consulta = br.readLine();
			while (!consulta.equalsIgnoreCase("run")) {
				stm.addBatch(consulta);
				consulta = br.readLine();
			}
			System.out.println("ejecución del lote de instrucciones");
			int[] resultados = stm.executeBatch();
			for (int i = 0; i < resultados.length; i++) {
				switch (resultados[i]) {
				case Statement.EXECUTE_FAILED:
					System.out.println("la ejecución de la instrucción " + i + " ha fallado");
					break;
				case Statement.SUCCESS_NO_INFO:
					System.out.println("la ejecución de la instrucción " + i + " ha tenido éxito");
					System.out.println("el número de registros modificados es desconocido");
					break;
				default:
					System.out.println("la ejecución de la instrucción " + i + " ha tenido éxito");
					System.out.println("ha modificado " + resultados[i] + " registros");
					break;
				}
			}

		} catch (SQLException e) {
			System.out.println("su batch no ha funcionado correctamente");
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

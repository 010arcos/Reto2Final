package com.example.reto2javafx;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestInsertionViaPreparedStatement {

	public static void main(String[] args) {
		try (Connection cnx = getConnexion();
				//Consulta de insertion con los campos obligatorios
				PreparedStatement pstm = cnx.prepareStatement("insert into PERSONAS(nombre, apellido) values(?,?)",
						Statement.RETURN_GENERATED_KEYS)) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("introducir el nombre de la persona a agregar :");
			String nombre = br.readLine();
			System.out.println("introducir el apellido de la persona a agregar :");
			String apellido = br.readLine();
			//Aplicar los parámetros
			pstm.setString(1, nombre);
			pstm.setString(2, apellido);
			//Ejecutar la consulta y obtener el resultado
			pstm.executeUpdate();
			//Recuperar el ResultSet que eventualmente contiene una clave
			ResultSet rsClaveGenerada = pstm.getGeneratedKeys();
			//Si hay un registro, es el que tiene una clave
			if(rsClaveGenerada.next())
			{
				System.out.println("La clave de la registro generado es " + rsClaveGenerada.getInt(1));
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

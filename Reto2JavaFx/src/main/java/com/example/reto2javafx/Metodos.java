package com.example.reto2javafx;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Scanner;

public class Metodos {
    static Scanner sc= new Scanner(System.in);

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


}


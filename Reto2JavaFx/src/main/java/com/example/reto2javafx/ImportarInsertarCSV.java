package com.example.reto2javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImportarInsertarCSV {
   static Connection cnx;


   private static Connection getConnexion() throws SQLException {
      String url = "jdbc:mariadb://localhost:3306/AjedrezOpen";
      String user = "root";
      String pass = "Debian";
      return DriverManager.getConnection(url, user, pass);

   }

   static {
      try {
         cnx = getConnexion();
         ;
      } catch (SQLException e) {
         throw new RuntimeException(e);
      }
   }

   public List<Jugador> importarjugadoresA(String csvFile) throws FileNotFoundException {
      List<Jugador> listaJugadoresOpen = FXCollections.observableArrayList(); // creo una lista
      String linea;
      String titulo;
      int lineaActual = 0; // Contador de líneas

      String info ;
      String cvxSplit = ";";  // Defino el delimitador que he utilizado para separar los datos

      // Intenta abrir el archivo CSV con un BufferedReader dentro de un bloque try-with-resources
      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
         // Incrementa el contador de línea cada vez que se lee una línea


         // Leo el archivo línea por línea
         while ((linea = br.readLine()) != null) {
            lineaActual++;
            if (lineaActual<6){
               continue;
            }

            // Divido cada línea en partes, utilizando el delimitador ";"
            String[] datos = linea.split(cvxSplit, -1); //esto permite tener campos vacios

            if (datos.length >= 7) {

               // saco la linea, spliteo y trim para quitar esapacios en blancos q haya al principio y final
               int rankingI = Integer.parseInt(datos[0].trim());
               titulo =datos[1].trim();
               String nombre = datos[2].trim();
               String federacion = datos[3].trim();
               int fide = Integer.parseInt(datos[4].trim());
               String fideID = datos[5].trim();
               info =  datos[6].trim();
               int rankingF = 0;
               int importeP = 0;
               boolean descalificado=false;

               // Asigna la categoría del torneo, en este caso siempre es A
               Jugador.categoria tipoTorneo = Jugador.categoria.A;

               boolean hotel;
               boolean comVal;


               /*Con if mas sucio pero para q lo entendais
               if (info.contains("CVH")) {
                  hotel = true;
                  comVal = true;
               } else {
                  if (info.contains("CV")) {
                     comVal = true;
                  }
                  if (info.contains("H")) {
                     hotel = true;
                  }
               }
               */
               // hotel será verdadero si la cadena contiene "H" o "CVH"
               hotel = info.contains("H") || info.contains("CVH");
               // comVal será verdadero si la cadena contiene "CV" o "CVH"
               comVal = info.contains("CV") || info.contains("CVH");










               // Crea un nuevo objeto Jugador con los datos extraídos y parseados
               Jugador jugador = new Jugador(rankingI, rankingF, titulo, nombre, federacion, fide, fideID, hotel, comVal, importeP, tipoTorneo, descalificado);
               // Añade el objeto Jugador a la lista observable
               listaJugadoresOpen.add(jugador);
            }else {
               System.err.println("Línea ignorada (no tiene suficientes campos): " + linea);
            }
         }

      } catch (IOException e) {
         // Captura cualquier error de E/S que pueda ocurrir durante la lectura del archivo
         e.printStackTrace();
      }

      // Devuelve la lista de jugadores
      return listaJugadoresOpen;
   }


   public List<Jugador> importarjugadoresB(String csvFile) throws FileNotFoundException {
      List<Jugador> listaJugadoresOpen = FXCollections.observableArrayList(); // creo una lista
      String linea;
      String titulo = null;
      int lineaActual = 0;
      String info = "";
      String cvxSplit = ";";  // Defino el delimitador que he utilizado para separar los datos

      // Intenta abrir el archivo CSV con un BufferedReader dentro de un bloque try-with-resources
      try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {

         // Lee el archivo línea por línea
         while ((linea = br.readLine()) != null) {
            // Divido cada línea en partes, utilizando el delimitador ";"
            String[] datos = linea.split(cvxSplit, -1); //esto permite tener campos vacios

            if (datos.length >= 7) {
               lineaActual++;
               if (lineaActual < 6) {
                  continue;
               }

               // saco la linea, spliteo y trim para quitar esapacios en blancos q haya al principio y final
               int rankingI = Integer.parseInt((datos[0].trim()));
               titulo = datos[1].trim();
               String nombre = datos[2].trim();
               String federacion = datos[3].trim();
               int fide = Integer.parseInt(datos[4].trim());
               String fideID = datos[5].trim();
               info = datos[6].trim();
               int rankingF = 0;
               int importeP = 0;

               // Asigna la categoría del torneo, en este caso siempre es B
               Jugador.categoria tipoTorneo = Jugador.categoria.B;
               boolean hotel;
               boolean comVal;
               boolean descalificado=false;

               // hotel será verdadero si la cadena contiene "H" o "CVH"
               hotel = info.contains("H") || info.contains("CVH");
               // comVal será verdadero si la cadena contiene "CV" o "CVH"
               comVal = info.contains("CV") || info.contains("CVH");










               // Crea un nuevo objeto Jugador
               Jugador jugador = new Jugador(rankingI, rankingF, titulo, nombre, federacion, fide, fideID, hotel, comVal, importeP, tipoTorneo, descalificado);
               // Añade el objeto Jugador a la lista observable
               listaJugadoresOpen.add(jugador);
            }else {
               System.err.println("Línea ignorada (no tiene suficientes campos): " + linea);
            }
         }

      } catch (IOException e) {
         //CAPTURO CUALQUIER ERROR
         e.printStackTrace();
      }


      return listaJugadoresOpen;
   }

   public void insertarJugador(List<Jugador> listaJugadores) throws SQLException{
      String sql = "INSERT INTO jugador (rankingI, rankingF, titulo, nombre, federacion, fide, fideID, hotel, CV, importeP, categoria, descalificado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      try (Connection cnx = getConnexion();
           PreparedStatement ps = cnx.prepareStatement(sql)) {
         for (Jugador jugador : listaJugadores) {
            ps.setInt(1, jugador.getRankingI());
            ps.setInt(2, jugador.getRankingF());
            ps.setString(3, jugador.getTitulo());
            ps.setString(4, jugador.getNombre());
            ps.setString(5, jugador.getFederacion());
            ps.setInt(6, jugador.getFide());
            ps.setString(7, jugador.getFideID());
            ps.setBoolean(8, jugador.isHotel());
            ps.setBoolean(9, jugador.isCv());
            ps.setInt(10, jugador.getImporteP());
            ps.setString(11, jugador.getTipoTorneo().toString());
            ps.setBoolean(12, jugador.isDescalificado());
            ps.executeUpdate();
         }
      } catch (SQLException e) {
         e.printStackTrace();
      }


   }


   // de aqui para abajo lo demas

   public static List<Jugador> RankFExportarA( String listaJugadoresACSV) throws SQLException{
      List<Jugador> listaJugadoresOpen = FXCollections.observableArrayList(); // creo una lista
      String linea;
      int lineaActual = 0;
      String cvxSplit = ";";  // Defino el delimitador que he utilizado para separar los datos

      // Intenta abrir el archivo CSV con un BufferedReader dentro de un bloque try-with-resources
      try (BufferedReader br = new BufferedReader(new FileReader(listaJugadoresACSV))) {

         // Lee el archivo línea por línea
         while ((linea = br.readLine()) != null) {
            // Divido cada línea en partes, utilizando el delimitador ";"
            String[] datos = linea.split(cvxSplit, -1); //esto permite tener campos vacios

            if (datos.length >= 1) {
               lineaActual++;
               if (lineaActual < 6) {
                  continue;
               }

               // saco la linea, spliteo y trim para quitar esapacios en blancos q haya al principio y final
               int rankingF = Integer.parseInt((datos[0].trim()));
               int rankingI = Integer.parseInt((datos[1].trim()));


               // Asigna la categoría del torneo, en este caso siempre es A
               Jugador.categoria tipoTorneo = Jugador.categoria.A;

               // Crea un nuevo objeto Jugador
               Jugador jugador = new Jugador(rankingI, rankingF, tipoTorneo);
               // Añade el objeto Jugador a la lista observable
               listaJugadoresOpen.add(jugador);
            }else {
               System.err.println("Línea ignorada (no tiene suficientes campos): " + linea);
            }
         }

      } catch (IOException e) {
         //CAPTURO CUALQUIER ERROR
         e.printStackTrace();
      }


      return listaJugadoresOpen;
   }


   public static List<Jugador> RankFExportarB( String listaJugadoresACSV) throws SQLException{
      List<Jugador> listaJugadoresOpen = FXCollections.observableArrayList(); // creo una lista
      String linea;
      String titulo = null;
      int lineaActual = 0;
      String info = "";
      String cvxSplit = ";";  // Defino el delimitador que he utilizado para separar los datos

      // Intenta abrir el archivo CSV con un BufferedReader dentro de un bloque try-with-resources
      try (BufferedReader br = new BufferedReader(new FileReader(listaJugadoresACSV))) {

         // Lee el archivo línea por línea
         while ((linea = br.readLine()) != null) {
            // Divido cada línea en partes, utilizando el delimitador ";"
            String[] datos = linea.split(cvxSplit, -1); //esto permite tener campos vacios

            if (datos.length >= 1) {
               lineaActual++;
               if (lineaActual < 6) {
                  continue;
               }

               // saco la linea, spliteo y trim para quitar esapacios en blancos q haya al principio y final
               int rankingF = Integer.parseInt((datos[0].trim()));
               int rankingI = Integer.parseInt((datos[1].trim()));


               // Asigna la categoría del torneo, en este caso siempre es B
               Jugador.categoria tipoTorneo = Jugador.categoria.B;

               // Crea un nuevo objeto Jugador
               Jugador jugador = new Jugador(rankingI, rankingF, tipoTorneo);
               // Añade el objeto Jugador a la lista observable
               listaJugadoresOpen.add(jugador);
            }else {
               System.err.println("Línea ignorada (no tiene suficientes campos): " + linea);
            }
         }

      } catch (IOException e) {
         //CAPTURO CUALQUIER ERROR
         e.printStackTrace();
      }


      return listaJugadoresOpen;
   }


   public void updateRankF(List<Jugador> listaJugadoresA, List<Jugador> listaJugadoresB) throws SQLException {

      // Preparar la consulta de selección
      String sqlSelect = "SELECT rankingI, categoria FROM jugador";
      PreparedStatement psSelect = cnx.prepareStatement(sqlSelect);
      ResultSet rs = psSelect.executeQuery();

      // Preparar las consultas de actualización para cada categoría
      String sqlUpdateA = "UPDATE jugador SET rankingF = ? WHERE rankingI = ? AND categoria = 'A'";
      PreparedStatement psUpdateA = cnx.prepareStatement(sqlUpdateA);

      String sqlUpdateB = "UPDATE jugador SET rankingF = ? WHERE rankingI = ? AND categoria = 'B'";
      PreparedStatement psUpdateB = cnx.prepareStatement(sqlUpdateB);

      while (rs.next()) {
         int rankingI = rs.getInt("rankingI");
         Jugador.categoria tipoTorneo = Jugador.categoria.valueOf(rs.getString("categoria"));

         for (Jugador jugador : listaJugadoresA) {
            if (tipoTorneo.equals(Jugador.categoria.A)) {
               if (rankingI == jugador.getRankingI()) {
                  psUpdateA.setInt(1, jugador.getRankingF());
                  psUpdateA.setInt(2, rankingI);
                  psUpdateA.executeUpdate();

               }
            }
         }
         for (Jugador jugador : listaJugadoresB) {
            if (tipoTorneo.equals(Jugador.categoria.B)){
               if (rankingI == jugador.getRankingI()){
                  psUpdateB.setInt(1, jugador.getRankingF());
                  psUpdateB.setInt(2, rankingI);
                  psUpdateB.executeUpdate();

               }
            }

         }




            /*if (rankingI == jugador.getRankingI()) {
               if (tipoTorneo.equals(Jugador.categoria.A)) {
                  psUpdateA.setInt(1, jugador.getRankingF());
                  psUpdateA.setInt(2, rankingI);
                  psUpdateA.executeUpdate();
               } else if (tipoTorneo.equals(Jugador.categoria.B)) {
                  psUpdateB.setInt(1, jugador.getRankingF());
                  psUpdateB.setInt(2, rankingI);
                  psUpdateB.executeUpdate();
               }
            }*/

      }

      // Cerrar recursos después de terminar el bucle
      rs.close();
      psSelect.close();
      psUpdateA.close();
      psUpdateB.close();
   }



      /*Método para que no importe e inserte la base de datos en el caso de q este llena y no de errores*/
      public boolean isDatabaseEmpty() throws SQLException {
         String sql = "SELECT COUNT(*) FROM jugador";
         try (Statement statement = cnx.createStatement(); // siempre try resources para q cierre automaticamente el bloque
              ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) { // si existe fila, sigue iterando*/
               int count = resultSet.getInt(1); /*obtenemos el valor de la primera columna de la fila y lo guardo en count*/
               return count ==0 ; // devuelve 0 indicando que la tabla jugador esta vacia
            }
         }
         return false;
      }

}

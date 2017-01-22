package quiz;

/**
 * PuF WS 2016/17
 * Gruppe Kaffesachsen()
 * 
 * @author Höger/Richter
 * @version 1.1
 * 
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class DatabaseConnection {
	
	private ArrayList<String> arrListFragen;	
	private static Connection conn = null;
		 
	// Hostname
	final String dbHost = "188.138.88.74";
		 
	// Port Standard: 3306
	final String dbPort = "3306";
		 
	// Datenbankname
	final String database = "admin_autoquiz";
		 
	// Datenbankuser
	final String dbUser = "lightning";
		 
	// Datenbankpasswort
	final String dbPassword = "mcqueen";
	
	public DatabaseConnection() { 
			
		arrListFragen = new ArrayList<String>();
			
		try {
		 
			// Datenbanktreiber für ODBC Schnittstellen laden.
		      
			Class.forName("com.mysql.jdbc.Driver");
		 
			// Verbindung zur ODBC-Datenbank 'admin_autoquiz' herstellen.
			// Es wird die JDBC-ODBC-Brücke verwendet.
			conn = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + database + "?" + "user=" + dbUser + "&" + "password=" + dbPassword); } 
		    
		catch (ClassNotFoundException e) {
			System.out.println("Treiber nicht gefunden");  //Datenbanktreiber muss in Classpath geladen werden (mysql-connector-java-5.1.40-bin.jar)
		}   
		catch (SQLException e) {
			System.out.println("Connect nicht moeglich");  //Datenbank erreichbar? War Mr. Robot am Werk? Server down? Panik!!!
		}
	}
		   
	/**
	* Lese Fragen aus der Datenbank ein und schreibe sie in ArrayList
	* 
	* @return  ArrayList arrListFragen
	*/
	public ArrayList<String> getQuestions() {

		if(conn != null) {
			
			// Anfrage-Statement erzeugen.
			
			Statement query;
			
			try {
				query = conn.createStatement();
		   
				// Ergebnistabelle erzeugen, nach ID sortieren und abholen.
				String sql = "SELECT * FROM fragen " + "ORDER BY ID";
				
				ResultSet result = query.executeQuery(sql);
		            
				while (result.next()) {
					String frage = result.getString("frage"); 

		            // packe eine nach der anderen Frage in die Array List "arrListFragen"
		            arrListFragen.add(frage);
		  			}	
		        } 
			catch (SQLException e) {
		          e.printStackTrace();
		        }
		}
		      
//	            // Debug - print out Arraylist
//
//	            for(int i=0; i < arrListFragen.size(); i++) {
//	            	System.out.println( arrListFragen.get( i ) );
//	            }
		
	     	return arrListFragen;
	}
		    
	/**
	* Fügt einen neuen Highscore in die Datenbank ein
	*/
	
	public void insertHighscore(String Name, int Punkte) {
		   
		if(conn != null) {
			
			try {
		   
				// Insert-Statement erzeugen (Fragezeichen werden später ersetzt).
				String sql = "INSERT INTO highscore(name, punkte) " + "VALUES(?, ?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
		          
				// Erstes Fragezeichen durch "Name" Parameter ersetzen
				preparedStatement.setString(1, Name);
		          
				// Zweites Fragezeichen durch "Punkte" Parameter ersetzen
				preparedStatement.setLong(2, Punkte);
		          
				// SQL ausführen.
				preparedStatement.executeUpdate();
		   
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		    
	/**
	* Lese Fragen aus der Datenbank ein
	 * @return 
	* 
	* @return  Highscore
	*/

	public void getHighscore() {
		   
		if(conn != null) {
			
			// Anfrage-Statement erzeugen.
			Statement query;
			try {
				query = conn.createStatement();
		   
				// Ergebnistabelle erzeugen und abholen.
				String sql = "SELECT * FROM highscore " + "ORDER BY punkte DESC LIMIT 10"; //Limit 10 bewirkt das nur die besten 10 Spieler nach Punkten sortiert angezeigt werden
				ResultSet result = query.executeQuery(sql);
		   
				while (result.next()) {
					String name = result.getString("name"); 
					long punkte = result.getLong("punkte");
					String highscore = name + "\t\t\t" + punkte;
//		        // Debug
					System.out.println(highscore);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
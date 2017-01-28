package Models;

/**
 * 
 * Klasse stellt Methoden bereit um eine Datenbankverbindung (MySql) herzustellen.
 * Benoetigt Treiber mysql-connector https://dev.mysql.com/downloads/connector/j/
 * 
 * ToDo Highscore/Fragen-Methoden raus und diese Klasse vererben
 *  
 * @author Robert/Markus
 * 
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class ConnectionModel {
	
	private ArrayList<String> arrListFragen;	
	private ArrayList<String> arrListHighscore;
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
	
	public ConnectionModel() { 
			
		arrListFragen = new ArrayList<String>();
		arrListHighscore = new ArrayList<String>();
			
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
	* Lese Fragen aus der Datenbank aus und schreibe diese in ArrayList
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
	     	return arrListFragen;
	}
		    
	/**
	* Fuegt einen neuen Highscore in die Datenbank ein
	*/
	
	public void insertHighscore(String Name, String Punkte) {
		Double punkteDouble =  Double.parseDouble(Punkte);		   
		if(conn != null) {
			
			try {
		   
				// Insert-Statement erzeugen (Fragezeichen werden später ersetzt).
				String sql = "INSERT INTO highscore(name, punkte) " + "VALUES(?, ?)";
				PreparedStatement preparedStatement = conn.prepareStatement(sql);
		          
				// Erstes Fragezeichen durch "Name" Parameter ersetzen
				preparedStatement.setString(1, Name);
		          
				// Zweites Fragezeichen durch "Punkte" Parameter ersetzen
				preparedStatement.setDouble(2, punkteDouble);
		          
				// SQL ausführen.
				preparedStatement.executeUpdate();
		   
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
		    
	/**
	* Lese Highscore aus der Datenbank aus
	 * @return 
	* 
	* @return  Highscore
	*/

	public ArrayList<String> getHighscore() {		   
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
					Double punkte = result.getDouble("punkte");
					String highscore = name + "#" + punkte;
					//System.out.println(highscore);
					arrListHighscore.add(highscore);
				}				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return arrListHighscore; //kaese
	}
}
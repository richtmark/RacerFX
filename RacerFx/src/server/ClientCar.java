package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import Car.AbstractCar;

public class ClientCar extends AbstractCar implements Runnable{
	
	//Instanzvariable fuer den Thread
	Thread clientCarThread; 
	
	public ClientCar() {
		clientCarThread = new Thread(this, "carThread"); //Thread verbunden mit eigenem Objekt
		clientCarThread.start();
	}
	
	
	// liest alle vom Server geschickten Daten
	static void zeigeWasKommt(BufferedReader sin) throws IOException {
		String str = null;
			try {
				while ((str = sin.readLine()) != null)
					System.out.println(str);
			} 
			catch (SocketTimeoutException sto) {
			}
	}
	
	static void zeigePrompt() {
		System.out.print("> ");
		System.out.flush();
	}	  
   
    
	@Override
	public void run() {
		String host = "localhost";
		int socketNr = 3333;
	    try {    	
	      System.out.println("Client laeuft. Beenden mit QUIT");
	      Socket c = new Socket(host, socketNr); //Socket c = new Socket(args[0], Integer.parseInt(args[1]));
	      c.setSoTimeout(500); // setze Timeout auf eine halbe Sekunde
	      BufferedReader vomServer = new BufferedReader(new InputStreamReader(c.getInputStream()));
	      PrintWriter zumServer = new PrintWriter(c.getOutputStream(), true);
	      BufferedReader vonTastatur = new BufferedReader(new InputStreamReader(System.in));
	      String zeile;

	      do {
	        zeigeWasKommt(vomServer);
	        zeigePrompt();
	        zeile = vonTastatur.readLine();
	        zumServer.println(zeile);
	      } while(!zeile.equalsIgnoreCase("quit"));
	      
	      c.close();         // Socket (und damit auch Stroeme) schliessen
	    } catch (ArrayIndexOutOfBoundsException ae) {
	        System.out.println("Aufruf: java MyClient" + socketNr); //  System.out.println("Aufruf: java MyClient <Port-Nummer>");
	    } catch (UnknownHostException ux) {
	        System.out.println("Kein DNS-Eintrag fuer " + "FUBA");
	    } catch (IOException e) {
	        e.printStackTrace();
    }
  }
		
}
	
	


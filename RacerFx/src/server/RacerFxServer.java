package server;
import java.io.*;
import java.net.*;
class RacerFxServer {
  public static void main(String[] args) {
	  
    try {
        int port = 3333;         // Port-Nummer int port = Integer.parseInt(args[0]);   
        ServerSocket server = new ServerSocket(port); // Server-Socket
        System.out.println("RacerFxServer laeuft");  // Statusmeldung
        while (true) {
          Socket s = server.accept(); // Client-Verbindung akzeptieren
          RaceDienst myRaceDienst = new RaceDienst(s);
          //new RaceDienst(s).run();             // Dienst startet im KOnstruktor Dienstklasse
        }
    } catch (ArrayIndexOutOfBoundsException ae) {
        System.out.println("Aufruf: java DateTimeServer <Port>");
    } catch (IOException e) {
        e.printStackTrace();
    }
  }
}

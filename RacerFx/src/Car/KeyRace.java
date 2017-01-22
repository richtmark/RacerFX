/**
 * Klasse erzeugt Tastenkobimation die der Spieler in einem bestimmten zeitrahmen eingeben muss
 * 
 */


package Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class KeyRace implements Runnable  {		
	
	//######################################################################################
	//########################### Klassenvariablen ######################################
	//######################################################################################
	private boolean correctInput; //korrekte Kombination?
	private List<Character> userInputAl; //die Kombination des Spielers in einer Arraylist
	private List<Character> requestedAl; //die geforderte Kombination in einer Arraylist
	//private ArrayList<String> alphabetAl;
	private char[] alphabet; //eine char-Array fuer das Alphabet 
	private Random rndAlphaIndex; //ein Random-Objekt zur Auswahl der Buchstaben(Tasten)
	private ClientCar car;
	private String inputString; 
	Thread keyRaceThread; //das ganze als eigener Thread siehe Konstruktor
	private boolean isPlaying; //keyRace aktiv?
	
	
		
	
	//######################################################################################
	//########################### Kontruktoren  ######################################
	//######################################################################################
	/**
	 * Konstuktorcode
	 * 
	 */
	public KeyRace() {
		isPlaying = false;
		alphabet = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		requestedAl = new ArrayList<Character>();
		userInputAl = new ArrayList<Character>();
		rndAlphaIndex = new Random();
		correctInput = false;		
		inputString = "";
		keyRaceThread = new Thread(this, "keyRaceThread");
	}	
	
	
	//######################################################################################
	//########################### Oeffentliche Methoden ######################################
	//######################################################################################
	
	//RunnableObjekt Startmethode, hier beginnt der Thread
	public void start() {
		keyRaceThread.start();
		isPlaying = true;
	}		
	
	/**
	 * Getter - Thread/Spiel noch laeuft
	 * @return boolean 
	 */
	public boolean getIsPlaying() {
		return isPlaying;
	}

	/**
	 * Setter - Thread/Spiel stoppen
	 * ToDo logik quatsch
	 * @param isPlaying
	 */
	public void stopPlaying() {
		this.isPlaying = false;
		this.keyRaceThread.interrupt();
		//countInstance = 0;  //TODO macht hier keinen Sinn da im static und im Konstruktor		
	}
	
	
	/** 
	 * der Input des Nutzers
	 * 
	 * Wirft Exception wenn die Laenge nicht 4 ist es muss also in der Controller/Gui Klasse die 
	 * diese Methode aufruft sichergestellt werden das die Methode nur dann aufgerufen wird wenn 
	 * die Laenge des Strings 4 ist!
	 * ToDo Exception implementieren
	 * 
	 */
	public void setInputString (String argInputString) {
		//pseudoExceptio
		if (argInputString.length() == 4) {
			this.inputString = argInputString;
		} else {
			System.out.println("Pseudoexception: Laenge argInputString != 4. String zurueckgesetzt.");
			this.inputString = "";
		}				
	}
	
	
	/**
	 * Die gefordert Tastenkombination als eine Stringrepraesentation fuer die Ausgabe z.B. 
	 * in einem Textfield
	 * @return String
	 */
	public String getRequestedString() {
	    StringBuilder builder = new StringBuilder(requestedAl.size());
	    for(Character ch: requestedAl)
	    {
	        builder.append(ch);
	    }
	    return builder.toString();
	}
	
	
	
	//######################################################################################
	//########################### private Methoden ######################################
	//######################################################################################
	/**
	 * Vergleicht jeden Char des InputStrings mit der geforderten Reihenfolge aus der requestedAl.
	 * Gibt true zurueck wenn die Laenge 4 ist und die Listen uebereinstimmen. 
	 * Gibt false zurueck wenn die Laenge 4 ist und die Listen uebereinstimmen.
	 * 
	 * Achtung setInputString() prueft vorher die Laenge und wirft eine Exception
	 * hier sollten also nur Strings ankommen mit einer string.length von 4
	 * 
	 * @param inputString String Eingabe des Spielers
	 * @return boolean Eingabe entspricht Anforderung
	 */
	private boolean isInputCorrect (String inputString) {
		int trueCounter = 0; //Wir erwarten 4 Elemente also 3 muesste da rauskommen im Moment		
		//String inputString zerlegen und in die List<Character> inputAl
		for(char charElement : inputString.toCharArray()) {
			userInputAl.add(charElement);			
		}
		
		//Vergleiche die List(en)<>
		for(int i = 0; i < 4; i++) {			
			if (requestedAl.get(i) == userInputAl.get(i)) {
				trueCounter++;				
			}
		}		
		if (trueCounter == 3 ) {
			correctInput = true;
			return correctInput;
		} else {
			correctInput = false;
			return correctInput;
		}	
	}
	
	
	/**
	 * Methode zum randomisieren der Tastaturfolge die gefordert ist.
	 * Loescht bestehende requestedAl und ersetzt diese durch eine neue.  
	 *
	 */
	private void randomizeAndResetRequestedAl() {
		requestedAl.clear();		
		int intRndIndex = 0;
		for (int i = 0; i < 4; i++) {
			intRndIndex = rndAlphaIndex.nextInt(26);	
			requestedAl.add(alphabet[intRndIndex]);			
		}
	}
	
		
	/**
	 * Methode setzt irgendwann die Beschleunigung des Cars
	 * 
	 */
	private void keyDrive() {
		if (correctInput == true) {
			System.out.println("Correct Input!");
			randomizeAndResetRequestedAl(); 
			correctInput = false;
		} else {
			System.out.println("Wrong Input!");
			randomizeAndResetRequestedAl();				
		}			
	}	

	@Override
	public void run() {
		//Declare the timer
		Timer t = new Timer();
		//Set the schedule function and rate
		t.scheduleAtFixedRate(new TimerTask() {
		    @Override
		    public void run() {
		        //Called each time when 1000 milliseconds (1 second) (the period parameter)
		    	System.out.println("This Timer is called every 5 Seconds");
		    }

		},
		//Set how long before to start calling the TimerTask (in milliseconds)
		0,
		//Set the amount of time between each execution (in milliseconds)
		1);
		
		// TODO Auto-generated method stub
		while (isPlaying) {
			//System.out.println("Run laueft in eigenem Thread");
			//keyDrive();		
		}
	}

}




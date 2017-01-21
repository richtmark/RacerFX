package Car;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KeyRace {	
	private boolean correctInput;
	private List<Character> userInputAl;
	private List<Character> wantToPressAl;
	//private ArrayList<String> alphabetAl;
	private char[] alphabet;
	private Random rndAlphaIndex; //der Ball
	private ClientCar car;
		
	/**
	 * Konstuktorcode
	 */
	public KeyRace() {
		alphabet = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
		wantToPressAl = new ArrayList<Character>();
		userInputAl = new ArrayList<Character>();
		rndAlphaIndex = new Random();
		correctInput = false;		
	}
	
	
	/**
	 * Die gefordert Tastenkombi in einen String fuer die Ausgabe z.B. Textfield
	 * @return
	 */
	public String getwantToPressAlasString() {
	    StringBuilder builder = new StringBuilder(wantToPressAl.size());
	    for(Character ch: wantToPressAl)
	    {
	        builder.append(ch);
	    }
	    return builder.toString();
	}

	/**
	 * Vergleicht jeden Char des InputStrings mit der geforderten Reihenfolge
	 * @param inputString
	 * @return int (0 = bis jetzt ok mach weiter; 1 = fail fang von vorne an; 2 = richtig)
	 */
	private boolean isInputCorrect (String inputString) {
		int trueCounter = 0; //Wir erwarten 4 Elemente also 3 muesste da rauskommen im Moment		
		//String inputString zerlegen und in die List<Character> inputAl
		for(char charElement : inputString.toCharArray()) {
			userInputAl.add(charElement);			
		}
		
		//Vergleiche die List(en)<>
		for(int i = 0; i < 4; i++) {			
			if (wantToPressAl.get(i) == userInputAl.get(i)) {
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
	 * Methode zum randomisieren der Tastaturfolge die gefordert ist 
	 */
	private void randomizeWantToPressAl() {
		wantToPressAl.clear();		
		int intRndIndex = 0;
		for (int i = 0; i < 4; i++) {
			intRndIndex = rndAlphaIndex.nextInt(26);	
			wantToPressAl.add(alphabet[intRndIndex]);			
		}
	}
	
	public void drive() {
		if (correctInput == true) {
			randomizeWantToPressAl();
			correctInput = false;
		}
		randomizeWantToPressAl();		
	}
	
	
	//################ Testmethoden #####################
	public void testAlphabet() {

		
	}
}




package application;

import java.net.URL;
import java.util.ResourceBundle;
import Models.HighscoreModel;
import Models.HighscorePool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * Controller die Anzeige der Highscore.
 *
 * @author Robert/Markus
 */
public class HighscoreScreenController implements Initializable, InterfaceControllScreen {
   MultiScreenController objMultiController;
    
   @FXML
   private TextArea idHighScoreText; //ToDo TableView implementieren  
   private HighscorePool objHighscorePool;            
    
    /**
     * Initializieren der Controllerklasse
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {    	
    	loadHighScore();
    }
    
    /**
     * Laden der Highscore aus der Datenbank (siehe HighscorePool und HighscoreModel)
     * Schreiben der 
     */
    public void loadHighScore() {
    	objHighscorePool = new HighscorePool();          
    	String myString = "\t" + "NAME" + "\t\t\t\t\t" + "ZEIT" +"\n\n";
    	for (HighscoreModel element : objHighscorePool ) {
    		if (element.getName().length() <= 5 && element.getName().length() > 1) {
    			myString = myString + "\t" + element.getName() + "\t\t\t\t\t\t" + element.getPoints() +"\n"; 
    		} 
    		if (element.getName().length() == 1) {
    			myString = myString + "\t" + element.getName() + "\t\t\t\t\t\t" + element.getPoints() +"\n";     			
    		}
    		if (element.getName().length() <=7  && element.getName().length() > 5) {
    			myString = myString + "\t" + element.getName() + "\t\t\t\t\t" + element.getPoints() +"\n"; 
    		}
    		//System.out.println(element.getName() + " laenge " + element.getName() .length());    		   		
    	}
    	idHighScoreText.setText(myString);
    }
    
    
    public void setScreenParent(MultiScreenController screenParent){
        objMultiController = screenParent;    	
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       objMultiController.setScreen(RacerFxMain.screenSplash);
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
       objMultiController.loadScreen(RacerFxMain.screenGame, "Game.fxml");   
       objMultiController.setScreen(RacerFxMain.screenGame);
    }    
}

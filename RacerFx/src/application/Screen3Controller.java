//Controller 3 Highscore oder sowas

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
 * FXML Controller class
 *
 * @author Robert/Markus
 */
public class Screen3Controller implements Initializable, ControlledScreen {
   ScreensController myController;
    
   @FXML
   private TextArea idHighScoreText; //ToDo TableView implementieren     
   //private HighscoreModel objHighscoreModel;
   private HighscorePool objHighscorePool;            
    
    /**
     * Initializieren der ControllerKlasse
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	System.out.println("FUBA");
    	refreshHighScore();
    }    

    //zum refreshen aus anderen Screens heraus 
    public void refreshHighScore() {
    	objHighscorePool = new HighscorePool();          
    	String myString = "NAME" + "\t\t\t\t" + "ZEIT" +"\n\n";
    	for (HighscoreModel element : objHighscorePool ) {
    		myString = myString + element.getName() + "\t\t\t\t" + element.getPoints() +"\n";    		
    	}
    	idHighScoreText.setText(myString);
    }
    
    
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;    	
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(ScreensFramework.screen1ID);
    }
    
    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(ScreensFramework.screen2ID);    
    }    
}

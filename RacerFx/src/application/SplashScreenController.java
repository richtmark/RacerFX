package application;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * Controller SplashScreen
 *
 * @author Robert/Markus
 */
public class SplashScreenController implements Initializable, InterfaceControllScreen {

    MultiScreenController myController;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setScreenParent(MultiScreenController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen2(ActionEvent event){
       myController.setScreen(RacerFxMain.screenGame);
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(RacerFxMain.screenHighscore);
    }
}

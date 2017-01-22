/**
Controller Hauptbildschirm
*/ 

package application;

import java.net.URL;
import java.util.ResourceBundle;
import Car.KeyRace;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import quiz.QuizRace;

/**
 * Controller Hauptbildschirm 2
 */
public class Screen2Controller implements Initializable , ControlledScreen {
	private Image carImage = new Image (getClass().getResource("/resources/dragcarpic.jpg").toString());
	ImageView iv = new ImageView(carImage);
	
    ScreensController myController; //Der Controller fuer die Szenenwechsel
	
    
    @FXML //  fx:id="playButton" den playbutton aus der FXML holen
    private Button playbutton; // Value injected by FXMLLoader
    @FXML 
    private Canvas gamecanvas;
    @FXML
    private TextField idTxfInput;
    @FXML
    private TextField idTxfWanted;
    @FXML
    private Label idLblCountdown;
    @FXML
    private RadioButton idRadioButtonAnswer1;    
    @FXML
    private RadioButton idRadioButtonAnswer2;
    @FXML
    private RadioButton idRadioButtonAnswer3;
    @FXML
    private RadioButton idRadioButtonAnswer4;
    @FXML
    private ToggleGroup answerToggleGroup; //RadioButtons ueber FXML zu Grp
    @FXML
    private TextArea idTxtAreaQuestion;
    
    // fuer den Counter der Tastenkombination
    private static final Integer STARTTIMEKEYCOUNTDOWN = 10; //Countdown Eingabe sek
    private Timeline timelineKeyCountdown; //Timeline Eingabe Tastenkombination
    private IntegerProperty timeSecondsCountdownProperty = new SimpleIntegerProperty(STARTTIMEKEYCOUNTDOWN);
    //private StringProperty inputStringProperty;
    

    
        
    private GraphicsContext gc;
        

    /**
     * Initializieren der Controllerklasse
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	idRadioButtonAnswer1.setUserData("1"); //value zuweisen
    	idRadioButtonAnswer2.setUserData("2");
    	idRadioButtonAnswer3.setUserData("3");
    	idRadioButtonAnswer4.setUserData("4");
    	
    	KeyRace keyRaceObj = new KeyRace();
    	QuizRace quizRaceObj = new QuizRace();
        // Bind the timerLabel text property to the timeSeconds property
    	idLblCountdown.textProperty().bind(timeSecondsCountdownProperty.asString());
    	//idTxfInput.textProperty().bind(inputStringProperty);
    	
    	playbutton.setOnAction(new EventHandler<ActionEvent>() {   //Eventhandler playbutton 		
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Playbutton aufgerufen");
                keyRaceObj.start();   
                idTxfWanted.setText(keyRaceObj.getNewRequestedString());        
                
                                
                answerToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                    public void changed(ObservableValue<? extends Toggle> ov,
                        Toggle old_toggle, Toggle new_toggle) {
                            if (answerToggleGroup.getSelectedToggle() != null) {
                            	System.out.println(answerToggleGroup.getSelectedToggle().getUserData().toString());
                            	//group.getSelectedToggle().getUserData().toString();
                            	quizRaceObj.getRandomQuestion();
                            }                
                        }
                });                
                
                
                
                //################## Listener auf Textfield noch einfacher als unten ###########                
                idTxfInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    System.out.println("textfield changed from " + oldValue + " to " + newValue);
                    if(idTxfInput.getText().length() == 4) {
                    	String tempInput = idTxfInput.getText();  
                    	//idTxfInput.clear(); 
                    	//naja notlösung exception zuruecksetzen textfield in listener
                    	//http://stackoverflow.com/questions/30465313/javafx-textfield-with-listener-gives-java-lang-illegalargumentexception-the-s
                    	Platform.runLater(() -> { 
                        	idTxfInput.clear(); 
                        });         
                    	boolean tempBool = keyRaceObj.isInputCorrect(tempInput);
                    	System.out.println("Input String ist korrekt: "  + tempBool);   
                    	if (tempBool == true) {
                    		idTxfWanted.setText(keyRaceObj.getNewRequestedString());   
                    		timelineKeyCountdown.stop();
                            timeSecondsCountdownProperty.set(STARTTIMEKEYCOUNTDOWN);
                            timelineKeyCountdown = new Timeline();
                            timelineKeyCountdown.getKeyFrames().add(
                                    new KeyFrame(Duration.seconds(STARTTIMEKEYCOUNTDOWN+1),
                                    new KeyValue(timeSecondsCountdownProperty, 0)));
                            timelineKeyCountdown.setCycleCount(Timeline.INDEFINITE); //ewig wiederholen
                            //timeline.cycleCountProperty().set(5); // 5 mal wiederholen
                            timelineKeyCountdown.playFromStart(); 
                    	}                    	
                    	//timelineKeyCountdown.playFromStart(); 
                    }
                });                
              //#############################################################################
                
                
                
                
              //##########################Changelistener Countdown###########################
                /*
                 * Einen ChangeListener erstellen (der dann eine Property gebunden wird)
                 * in diesem Fall später an timeSeconds eine IntegerProperty 
                 */
                final ChangeListener changeListenerCountdown = new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                      //System.out.println("oldValue:"+ oldValue + ", newValue = " + newValue);
                      if ((int)newValue == 0) {
                    	  //Anfrage neue Tastenkombination
                    	  idTxfWanted.setText(keyRaceObj.getNewRequestedString());
                      }
                    }	
                };                
                /*
                 * Hier wird der Listener bei der property angemeldet
                 */
                timeSecondsCountdownProperty.addListener(changeListenerCountdown);
              //#############################################################################
                
                
                
              //#############################################################################
                /*
                 * 
                 * Die Timeline fuer den Countdown steuern
                 * 
                 */
                /*
                if (timelineKeyCountdown != null) {
                    timelineKeyCountdown.stop();
                }
                */
                timeSecondsCountdownProperty.set(STARTTIMEKEYCOUNTDOWN);
                timelineKeyCountdown = new Timeline();
                timelineKeyCountdown.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(STARTTIMEKEYCOUNTDOWN+1),
                        new KeyValue(timeSecondsCountdownProperty, 0)));
                timelineKeyCountdown.setCycleCount(Timeline.INDEFINITE); //ewig wiederholen
                //timeline.cycleCountProperty().set(5); // 5 mal wiederholen
                timelineKeyCountdown.playFromStart();           
              //#############################################################################
                

                

            }
        });
    	iv.setX(10); //Imageview
    	gc = gamecanvas.getGraphicsContext2D();	//die Canvas     	    	
    }
    
    //#########################################################
    //Zum Zeichnen des Cars ...
    private void paintCar() {
	    new AnimationTimer()
	    {
	        public void handle(long currentNanoTime) {	        	
//				if (isViewPlaying == true) {		        	
//	        	    long startNanoTime = System.nanoTime();   
//		            double t = (currentNanoTime - startNanoTime) / 1000000000.0; //weiche Bewegungzeichnen        
//		            System.out.println(t);
//		            for (int i = 0; i <= 500; i++) { 	
//		            	gc.clearRect(0, 0, 1000, 1000); //Zeichenflaeche loeschen vor neuzeichnen	
//		               	gc.drawImage(carImage, i, 10); 			               	
//		            } 		       
//	        	}		            
	        }
	    }.start();		
    }    
    
    
    //####################### SCREENWECHSEL
    
    //Screenparent setzen
    public void setScreenParent(ScreensController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(ScreensFramework.screen1ID);
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(ScreensFramework.screen3ID);
    }
    
    
    
    
    
}

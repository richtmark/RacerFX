/**
Controller Hauptbildschirm
*/ 

package application;

import java.net.URL;
import java.util.ResourceBundle;
import Car.KeyRace;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ClosePath;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;
import quiz.QuestionModel;
import quiz.QuizRace;

/**
 * Controller Hauptbildschirm 2
 */
public class Screen2Controller implements Initializable , ControlledScreen {
	private Image carImage = new Image (getClass().getResource("/resources/car_blue.png").toString());
	ImageView iv = new ImageView(carImage);
	
	ScreensController myController; //Der Controller fuer die Szenenwechsel
	    
    @FXML //  fx:id="playButton" den playbutton aus der FXML holen
    private Button playbutton; // Value injected by FXMLLoader
    //@FXML 
    //private Canvas gamecanvas;
    @FXML
    private TextField idTxfInput;
    @FXML
    private TextField idTxfWanted;
    @FXML
    private Label idLblKeyCountdown;
    @FXML
    private Label idLblQuizCountdown;
    @FXML
    private RadioButton idRadioButtonAnswer1;    
    @FXML
    private RadioButton idRadioButtonAnswer2;
    @FXML
    private RadioButton idRadioButtonAnswer3;
    @FXML
    private RadioButton idRadioButtonAnswer4;
    @FXML
    private ToggleGroup answerToggleGroup; //RadioButtons ueber FXML zu Grp!
    @FXML
    private TextArea idTxtAreaQuestion;
    @FXML
    private ImageView idBackgroundImageView; 
    @FXML
    private Canvas idCanvas;
    @FXML 
    private ImageView idimagecar;

    
    private static final Integer KEYSTARTTIMECOUNTDOWN = 10; //Countdown Eingabe sek
    private static final Integer QUIZSTARTTIMECOUNTDOWN = 10; //Countdown Eingabe sek
    private Timeline timelineKeyCountdown; //Timeline Eingabe Tastenkombination
    private Timeline timelineQuizCountdown; //Timeline fuer das Quiz
    private IntegerProperty propertyKeySecondsCountdown = new SimpleIntegerProperty(KEYSTARTTIMECOUNTDOWN);
    private IntegerProperty propertyQuizSecondsCountdown = new SimpleIntegerProperty(QUIZSTARTTIMECOUNTDOWN);
    private DoubleProperty propertyYValueBackground;
    private QuizRace quizRaceObj;
    private KeyRace keyRaceObj;
    private QuestionModel questionObjekt;    
    private double stop;
    private double speedduration;
    private PathTransition anim;

        

    /**
     * Initializieren der Controllerklasse
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
    	speedduration = 0;
    	idRadioButtonAnswer1.setUserData("1"); //value zuweisen nicht da auf die schnelle nicht gefunden in FXML...
    	idRadioButtonAnswer2.setUserData("2");
    	idRadioButtonAnswer3.setUserData("3");
    	idRadioButtonAnswer4.setUserData("4");   	
    	
    	//ToDo Keydown event immer focus auf eingabefeld wenn moeglich...
    	//myController.getScene().addEventFilter(KeyEvent.KEY_PRESSED, event -> System.out.println("Pressed: "+event.getCode()));   
    	    	
    	keyRaceObj = new KeyRace();    	
    	quizRaceObj = new QuizRace(); //QuizRaceObjekt mit Zugriff auf QuestionPool und QuestionModel
    	questionObjekt = new QuestionModel();
    	questionObjekt = quizRaceObj.getRandomQuestion(); //neues QuestionModel anfragen 
    	idTxtAreaQuestion.setText(questionObjekt.getQuestiontext());
    	idRadioButtonAnswer1.setText(questionObjekt.getAnswerOne());
    	idRadioButtonAnswer2.setText(questionObjekt.getAnswerTwo());
    	idRadioButtonAnswer3.setText(questionObjekt.getAnswerThree());
    	idRadioButtonAnswer4.setText(questionObjekt.getAnswerFour());
    	
    	
    	
    	
        //das Label idLblKeyCountdown(FXML) text property binden an eigene property Integer propertyKeySecondsCountdown 
    	idLblKeyCountdown.textProperty().bind(propertyKeySecondsCountdown.asString());
    	idLblQuizCountdown.textProperty().bind(propertyQuizSecondsCountdown.asString());
    	//idTxfInput.textProperty().bind(inputStringProperty);
    	    	
    	playbutton.setOnAction(new EventHandler<ActionEvent>() {   //Eventhandler playbutton 		
            @Override
            public void handle(ActionEvent event) {
            	//###########################################################################
            	//###########################a allgemeine Initialisierung ###################
            	//###########################################################################
                System.out.println("Playbutton aufgerufen");
                //keyRaceObj.start();   
                idTxfWanted.setText(keyRaceObj.getNewRequestedString());        
              //#################################################################################
        
                
                
                //#######################Timeline Countdown Key##################################
                propertyKeySecondsCountdown.set(KEYSTARTTIMECOUNTDOWN);
                timelineKeyCountdown = new Timeline();
                timelineKeyCountdown.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(KEYSTARTTIMECOUNTDOWN+1),
                        new KeyValue(propertyKeySecondsCountdown, 0)));
                timelineKeyCountdown.setCycleCount(Timeline.INDEFINITE); //ewig wiederholen
                //timeline.cycleCountProperty().set(5); // 5 mal wiederholen
                timelineKeyCountdown.playFromStart();           
              //############################################################################
                
                
                
                //#######################Timeline Countdown Quiz############################
                /*
                 * 
                 * Die Timeline fuer den Countdown Quiz steuern
                 * 
                 */
                /*
                if (timelineKeyCountdown != null) {
                    timelineKeyCountdown.stop();
                }
                */
                propertyQuizSecondsCountdown.set(QUIZSTARTTIMECOUNTDOWN);
                timelineQuizCountdown = new Timeline();
                timelineQuizCountdown.getKeyFrames().add(
                        new KeyFrame(Duration.seconds(QUIZSTARTTIMECOUNTDOWN+1),
                        new KeyValue(propertyQuizSecondsCountdown, 0)));
                timelineQuizCountdown.setCycleCount(Timeline.INDEFINITE); //ewig wiederholen
                //timeline.cycleCountProperty().set(5); // 5 mal wiederholen
                timelineQuizCountdown.playFromStart();           
              //#############################################################################

                
                
                
            	//###########################################################################
            	//########################### Listener alle #################################
            	//###########################################################################
                
                                
              //################ Listener ToggelAntworten + Timelinereset via neue Timeline ##################
                answerToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
                    public void changed(ObservableValue<? extends Toggle> ov,
                        Toggle old_toggle, Toggle new_toggle) {
                            if (answerToggleGroup.getSelectedToggle() != null) {
                            	System.out.println("Toggle");
                            	//pruefe Antwort
                            	if (answerToggleGroup.getSelectedToggle().getUserData().equals(questionObjekt.getTrueAnswerString())) {
                            		System.out.println("Answert true Quiz");
                            		speedduration = speedduration+1;
                            		anim.setRate(speedduration);

                            	} else {
                            		System.out.println("Answert false Quiz");                            		
                            		speedduration = speedduration-1;
                            		anim.setRate(speedduration);
                            	}
                            	//System.out.println(answerToggleGroup.getSelectedToggle().getUserData().toString());
                            	answerToggleGroup.getSelectedToggle().setSelected(false); 
                            	idTxfInput.requestFocus();
                            	//group.getSelectedToggle().getUserData().toString();
                            	questionObjekt = quizRaceObj.getRandomQuestion(); //neues QuestionModel anfragen 
                            	idTxtAreaQuestion.setText(questionObjekt.getQuestiontext());
                            	idRadioButtonAnswer1.setText(questionObjekt.getAnswerOne());
                            	idRadioButtonAnswer2.setText(questionObjekt.getAnswerTwo());
                            	idRadioButtonAnswer3.setText(questionObjekt.getAnswerThree());
                            	idRadioButtonAnswer4.setText(questionObjekt.getAnswerFour());
                            	//und auch hier direkt timeline nach antwort zuruecksetzen
                        		timelineQuizCountdown.stop();
                                propertyQuizSecondsCountdown.set(QUIZSTARTTIMECOUNTDOWN);
                                timelineQuizCountdown = new Timeline();
                                timelineQuizCountdown.getKeyFrames().add(
                                        new KeyFrame(Duration.seconds(QUIZSTARTTIMECOUNTDOWN+1),
                                        new KeyValue(propertyQuizSecondsCountdown, 0)));
                                timelineQuizCountdown.setCycleCount(Timeline.INDEFINITE); //ewig wiederholen
                                //timeline.cycleCountProperty().set(5); // 5 mal wiederholen
                                timelineQuizCountdown.playFromStart();   
                            }                
                        }
                });     
              //#############################################################################
                
                
                //################## Listener auf Textfield  Keyeingabe (noch einfacher als unten) + Timelinereset via neue Timeline ###########                
                idTxfInput.textProperty().addListener((observable, oldValue, newValue) -> {
                    //System.out.println("textfield changed from " + oldValue + " to " + newValue);
                    if(idTxfInput.getText().length() == 4) {
                    	String tempInput = idTxfInput.getText();  
                    	//idTxfInput.clear(); 
                    	//naja notlösung exception zuruecksetzen textfield in listener
                    	//http://stackoverflow.com/questions/30465313/javafx-textfield-with-listener-gives-java-lang-illegalargumentexception-the-s
                    	Platform.runLater(() -> { 
                        	idTxfInput.clear(); 
                        });         
                    	boolean tempBool = keyRaceObj.isInputCorrect(tempInput);
                    	  
                    	if (tempBool == true) {
                    		System.out.println("KeyString korrekt: "  + tempBool); 
                    		speedduration = speedduration+1;
                    		anim.setRate(speedduration);
                    		idTxfWanted.setText(keyRaceObj.getNewRequestedString());   
                    		timelineKeyCountdown.stop();
                            propertyKeySecondsCountdown.set(KEYSTARTTIMECOUNTDOWN);
                            timelineKeyCountdown = new Timeline();
                            timelineKeyCountdown.getKeyFrames().add(
                                    new KeyFrame(Duration.seconds(KEYSTARTTIMECOUNTDOWN+1),
                                    new KeyValue(propertyKeySecondsCountdown, 0)));
                            timelineKeyCountdown.setCycleCount(Timeline.INDEFINITE); //ewig wiederholen
                            //timeline.cycleCountProperty().set(5); // 5 mal wiederholen
                            timelineKeyCountdown.playFromStart(); 
                    	} else {
                    		//ToDo speed manipulieren
                    		System.out.println("KeyString false: "  + tempBool); 
                    		speedduration = speedduration-1;
                    		anim.setRate(speedduration);
                    	}
                    	//timelineKeyCountdown.playFromStart(); 
                    }
                });                
              //#############################################################################
                
                
                
                
              //######################## Changelistener Countdown Timeline Key (label) #########################
                /*
                 * Einen ChangeListener erstellen (der dann eine Property gebunden wird)
                 * in diesem Fall später an timeSeconds eine IntegerProperty 
                 */
                final ChangeListener changeListenerCountdownKey = new ChangeListener() {
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
                propertyKeySecondsCountdown.addListener(changeListenerCountdownKey);
                
                
              //#############################################################################
                
                //######################## Changelistener Countdown Quiz (label) #########################
                /*
                 * Einen ChangeListener erstellen (der dann eine Property gebunden wird)
                 * in diesem Fall später an timeSeconds eine IntegerProperty 
                 */
                final ChangeListener changeListenerCountdownQuiz = new ChangeListener() {
                    @Override
                    public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                    	//System.out.println("oldValue:"+ oldValue + ", newValue = " + newValue);
                    	if ((int)newValue == 0) {
	                    	  //Anfrage neue Tastenkombination
	                      	questionObjekt = quizRaceObj.getRandomQuestion(); //neues QuestionModel anfragen 
	                    	idTxtAreaQuestion.setText(questionObjekt.getQuestiontext());
	                    	idRadioButtonAnswer1.setText(questionObjekt.getAnswerOne());
	                    	idRadioButtonAnswer2.setText(questionObjekt.getAnswerTwo());
	                    	idRadioButtonAnswer3.setText(questionObjekt.getAnswerThree());
	                    	idRadioButtonAnswer4.setText(questionObjekt.getAnswerFour());                    		
                      }
                    }	
                };                
                /*
                 * Hier wird der Listener bei der property angemeldet
                 */
                propertyQuizSecondsCountdown.addListener(changeListenerCountdownQuiz);
              //#############################################################################
                                             
            }
        });
    	
    	//####################################Rollbild ###############################	

    	/*
        PathElement[] path = 
        {
        	new MoveTo(300, -8790), //bild bei 300, -8850 im Container
            new LineTo(300, 10000), // bild ende oben bei 300, 9000 container
            new ClosePath()
        };        
        
        //System.out.println(idBackgroundImageView.boundsInParentProperty()); alle
        
        Path road = new Path();
        road.getElements().addAll(path);
        anim = new PathTransition();
        anim.setNode(idBackgroundImageView);  
        stop = 17700;
        propertyYValueBackground = new SimpleDoubleProperty();
        propertyYValueBackground.bind(idBackgroundImageView.translateYProperty());
        propertyYValueBackground.addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {
               //System.out.println((double) t1);
            	System.out.println(speedduration);
               if (idBackgroundImageView.translateYProperty().doubleValue() > stop) {
            	   anim.pause();
               }
            }
        }); 
           
        
        
        
        anim.setPath(road);
        anim.setOrientation(OrientationType.NONE);
        anim.setInterpolator(Interpolator.LINEAR);
        anim.setDuration(new Duration(400000)); //speed hier je höher desto langsamer
        anim.setCycleCount(Timeline.INDEFINITE);  
        anim.setRate(speedduration);
        playbutton.setOnMouseClicked(me -> 
                    {
                      Animation.Status status = anim.getStatus();
                      if (status == Animation.Status.RUNNING &&
                          status != Animation.Status.PAUSED)
                          anim.pause();
                      else
                          anim.play();     
                    });
        
         */ 
    	//BACKGROUND_WIDTH=500;
        TranslateTransition translateTransition =
                new TranslateTransition(Duration.millis(5000), idBackgroundImageView);
         translateTransition.setFromY(0);
         translateTransition.setToY(1 * 17700);
         translateTransition.setInterpolator(Interpolator.LINEAR);
         translateTransition.setCycleCount(1);
         translateTransition.play();
         
         
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
    
    
    //####################### SCREENWECHSEL #########################
    
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

/**
 * 
 * Hauptbildschirm. Implementiert die gesamte Spiellogik.
 * 
 * @author Robert/Markus
 * 
 *
*/ 

package application;

import java.net.URL;
import java.util.ResourceBundle;

import Models.ConnectionModel;
import Models.QuestionModel;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import race.KeyRace;
import race.QuizRace;
//import server.ClientCar;  //erstmal alles raus
//import server.raceDienst;

public class GameScreenController implements Initializable , InterfaceControllScreen {
	MultiScreenController myController; //Der Controller fuer die Szenenwechsel	
    @FXML //  fx:id="playButton" den playbutton aus der FXML holen
    private Button playbutton; // Value injected via FXMLLoader
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
    private ImageView idBackgroundImageView; //Hintergrund Rollbild
    @FXML
    private Canvas idCanvas;
    @FXML 
    private ImageView idimagecar; //Auto
    @FXML 
    private Label idRaceTimerLabel;
    @FXML
    private Button idOkHighscore;
    @FXML
    private Label idInfoLabelHighscore;
    @FXML
    private TextField idTxfHighscore;
    @FXML
    private Pane idPaneHighscore;    
        
    
    private static final Integer KEYSTARTTIMECOUNTDOWN = 10; //Countdown Eingabe sek
    private static final Integer QUIZSTARTTIMECOUNTDOWN = 10; //Countdown Eingabe sek
    private Timeline timelineKeyCountdown; //Timeline Eingabe Tastenkombination
    private Timeline timelineQuizCountdown; //Timeline fuer das Quiz
    private IntegerProperty propertyKeySecondsCountdown = new SimpleIntegerProperty(KEYSTARTTIMECOUNTDOWN); //Keycounter
    private IntegerProperty propertyQuizSecondsCountdown = new SimpleIntegerProperty(QUIZSTARTTIMECOUNTDOWN); //Quizcounter
    private QuizRace quizRaceObj; //Steuerung Quiz
    private KeyRace keyRaceObj; //Steuerung Tastenkomination
    private QuestionModel questionObjekt; //Ein einzelnes Frageobjekt   
    private double speedduration;   //die Animationsrate der Prallax-Animation
    private TranslateTransition translateTransitionParalaxAnim; //Parallax Hintergrund Animation
    private Timeline timelineRaceTime; //Die Zeit vom Start bis zur Zielankunft
    private DoubleProperty timeSecondsProperty = new SimpleDoubleProperty();
    private Duration timeDurrationRace = Duration.ZERO;   
    private BooleanProperty finishProperty = new SimpleBooleanProperty();    
    //private ClientCar myClientcar; //ClientServer erst mal alles rausgenommen
                

    /**
     * Initializieren der Controllerklasse
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {     	
    	//myClientcar = new ClientCar();    	
    	//myCLientcar.run();
    	finishProperty.set(false);
    	idRaceTimerLabel.textProperty().bind(timeSecondsProperty.asString());    	  	    	
    	speedduration = 0;
    	idRadioButtonAnswer1.setUserData("1"); //value zuweisen ToDo per FXML
    	idRadioButtonAnswer2.setUserData("2");
    	idRadioButtonAnswer3.setUserData("3");
    	idRadioButtonAnswer4.setUserData("4");       	
    	idTxfInput.requestFocus(); //Eingabefeld    	
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

    	
    	    	
    	playbutton.setOnAction(new EventHandler<ActionEvent>() {   //Eventhandler playbutton 		
            @Override
            public void handle(ActionEvent event) {            	
            	
         	   translateTransitionParalaxAnim.play(); 
         	   playbutton.setDisable(true);
            	
            	//###########################################################################
            	//###########################a allgemeine Initialisierung ###################
            	//###########################################################################
                System.out.println("Playbutton aufgerufen");               
                idTxfWanted.setText(keyRaceObj.getNewRequestedString());                      
                
                
              //############################TIMELINE RACE#####################################
                timelineRaceTime = new Timeline(
                        new KeyFrame(Duration.millis(10),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent t) {
                                Duration duration = ((KeyFrame)t.getSource()).getTime();
                                timeDurrationRace = timeDurrationRace.add(duration);                              
                                timeSecondsProperty.set(timeDurrationRace.toSeconds());
                                //System.out.println(translateTransitionParalaxAnim.toYProperty());
                                System.out.println(idBackgroundImageView.translateYProperty());
                             
                            }
                        })
                    );
                    timelineRaceTime.setCycleCount(Timeline.INDEFINITE);
                    timelineRaceTime.play();
        
                
                
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
                            		//System.out.println("Answer true Quiz");
                            		setSpeed(1);                            		

                            	} else {
                            		//System.out.println("Answer false Quiz");                            		
                            		setSpeed(-1);                            		
                            	}
                            	//System.out.println(answerToggleGroup.getSelectedToggle().getUserData().toString());
                            	answerToggleGroup.getSelectedToggle().setSelected(false); 
                            	idTxfInput.requestFocus(); //Focus am besten immer auf das Textfeld
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
                    		setSpeed(1);   
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
                    		setSpeed(-1);                    		
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
    	
    	
    	//##############AKTIONEN BEI RENNENDE ##########################
    	finishProperty.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // Only if completed
                if (finishProperty.get()) {
                	raceIsFinished();
                	setHighscore();                	
                	//myController.setScreen(ScreensFramework.screen4ID);
                }
            }
        });    	
    	
    	
        //######################## Changelistener Racetime #########################
        /*
         * Einen ChangeListener erstellen (der dann eine Property gebunden wird)
         */
        final ChangeListener changeListenerRaceTime= new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
              //System.out.println("oldValue:"+ oldValue + ", newValue = " + newValue);
              Animation.Status status = translateTransitionParalaxAnim.getStatus();
              //System.out.println(idBackgroundImageView.getTranslateY());
              //myClientcar.setPositionY(idBackgroundImageView.getTranslateY());
              if (status == Animation.Status.STOPPED) {            	  
            	  finishProperty.set(true);
              }              
            }	
        };                
        /*
         * Hier wird der Listener bei der property angemeldet
         */
        timeSecondsProperty.addListener(changeListenerRaceTime);                
      //#############################################################################   	
         translateTransitionParalaxAnim =  new TranslateTransition(Duration.millis(250000), idBackgroundImageView);
         translateTransitionParalaxAnim.setRate(speedduration);
         //translateTransition.setOrientation(OrientationType.NONE);
         translateTransitionParalaxAnim.setFromY(0);
         translateTransitionParalaxAnim.setToY(20305); //bei 17700 px ist das Auto ueber der Ziellinie das nehmen wir mal als Endpunkt
         translateTransitionParalaxAnim.setInterpolator(Interpolator.LINEAR);
         translateTransitionParalaxAnim.setCycleCount(1);
    }
    
    /**
     * Setzt die "Geschwindigkeit" des  Hintergrundbildes. 
     * Als Integer in 1er Schritten. +1 schneller / -1 langsamer mit max 16; min 0
     * Letztendlich beeinflusst man hier die Animationsgeschwindigkeit um den Faktor
     * animation.setRate()...welcher mit animation(Duration.millis(12345) einhergeht.
     * Das Hintergrundbild wir auf bis 17700px auf der Y-Achse nach unten verschoben.
     * 
     * @param speed
     */
    private void setSpeed(int speed) {
        switch(speed){
        case 1:
        	if (speedduration <= 15) {
        		speedduration = speedduration+1;
        		translateTransitionParalaxAnim.setRate(speedduration);
        		//System.out.println(speedduration);        		
        	}
        	break;
        case -1:
        	if (speedduration > 0) {
        		speedduration = speedduration-1;
        		//System.out.println(speedduration);
        		translateTransitionParalaxAnim.setRate(speedduration);
        	}
        	break;
    	default:
            System.out.println("PseudoException ungültiger Parameter. -1/ 1 erwartet"); //ToDo Diese und andere Exceptions implementieren
        }
    }   
    
    
    
    /**
     * Eintragen logik Highscore. Einblenden/Ausblenden des Inputpanels und der zugehoerigen
     * Steuerelemente. Screenwechsel zur Highscoreuebersicht
     */
    private void setHighscore() {
    	ConnectionModel highScoreConnection = new ConnectionModel();
    	idPaneHighscore.setVisible(true);
    	idOkHighscore.setOnAction(new EventHandler<ActionEvent>() {
    	    @Override public void handle(ActionEvent e) {
    	    	if (idTxfHighscore.getText().length() > 0 && idTxfHighscore.getText().length() <=7) {
    	    		highScoreConnection.insertHighscore(idTxfHighscore.getText(), idRaceTimerLabel.getText());
    	    		idPaneHighscore.setVisible(false);
    	    		idInfoLabelHighscore.setText("Trage Dich in die Highscore ein!"); //falls schonmal falsch eingegeben wurde fuer naechsten eintrag  
    	    		myController.loadScreen(RacerFxMain.screenHighscore, "ScreenHighscore.fxml"); //bewirkt highscorerefresh ToDo flex. Parameteruebergabe ScreensFramwork
    	    		myController.setScreen(RacerFxMain.screenHighscore);  
    	    	} else {
    	    		idInfoLabelHighscore.setText("Bitte Namen eingeben mit maximal 7 Zeichen");    		
    	    	}
    	    }
    	});    	
    }    
        
    /**
     * Wird aufgerufen wenn das Rennenbeendet ist.
     * Logik Rundenende. Stoppt die Timelines (Threads).  
     * Steuerelemente zuruecksetzen.
     */
    private void raceIsFinished() {
    	timelineRaceTime.stop();
    	timelineQuizCountdown.stop();
    	timelineKeyCountdown.stop();    	
    }    
    
    
    //####################### SCREENWECHSEL #########################    
    public void setScreenParent(MultiScreenController screenParent){
        myController = screenParent;
    }

    @FXML
    private void goToScreen1(ActionEvent event){
       myController.setScreen(RacerFxMain.screenSplash);
    }
    
    @FXML
    private void goToScreen3(ActionEvent event){
       myController.setScreen(RacerFxMain.screenHighscore);
    }    
}

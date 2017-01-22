/**
Controller Hauptbildschirm
*/ 

package application;

import java.net.URL;
import java.util.ResourceBundle;

import Car.KeyRace;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
    
    private GraphicsContext gc;
    
    

    /**
     * Initializieren der Controllerklasse
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	KeyRace raceObj = new KeyRace();
    	playbutton.setOnAction(new EventHandler<ActionEvent>() {    		
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Playbutton aufgerufen");
                raceObj.start();
                
                //paintCar(); //WENN ES SOWEIT IST
                //raceObj.drive();
                //idTxfWanted.setText(raceObj.getwantToPressAlasString());
                /*
                //Beispiel alle 5 Sekunden dauerhaft funktion aufrufen
                Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("this is called every 5 seconds on UI thread");
                        raceObj.run();
                        idTxfWanted.setText(raceObj.getRequestedString());                        
                        //System.out.println(raceObj.isInputCorrect(idTxfWanted.getText().toString()));
                    }
                }));
                fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
                fiveSecondsWonder.play();
                */
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

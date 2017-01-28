package application;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * Startpunkt der Application. Deklarieren der Ansichten (Screens).
 * Laden aller Screens/Controller.
 * 
 * @author Robert/Markus
 */
public class RacerFxMain extends Application {
    
    public static String screenSplash = "splash";
    public static String screenSplashFXML = "SplashScreen.fxml";
    public static String screenGame= "game";
    public static String screenGameFXML = "Game.fxml";
    public static String screenHighscore = "highscore";
    public static String screenHighscoreFXML = "Highscore.fxml";    
    
    /**
     * Startpunkt JavaFX. Die Screens werden hier vorab geladen!
     */
    @Override
    public void start(Stage primaryStage) {        
        MultiScreenController screenContainer = new MultiScreenController();
        screenContainer.loadScreen(RacerFxMain.screenSplash, RacerFxMain.screenSplashFXML);
        screenContainer.loadScreen(RacerFxMain.screenGame, RacerFxMain.screenGameFXML);
        screenContainer.loadScreen(RacerFxMain.screenHighscore, RacerFxMain.screenHighscoreFXML);     
        screenContainer.setScreen(RacerFxMain.screenSplash);        
        Group root = new Group();
        root.getChildren().addAll(screenContainer);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();         
    }


    public static void main(String[] args) {
        launch(args);
    }
}

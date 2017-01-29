package application;

import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/** 
 * MultiScreenController. Diese Controllerklasse steuert den wechsel zwischen 
 * den einzelnen Controllern  (und damit verbunden der verschiedenen Ansichten).
 * Verwaltet in HashMap. Stellt Methoden bereit zum laden, wechseln und entfernen
 * einzelner Screens. 
 * 
 * @author Robert/Markus
 */
public class MultiScreenController extends StackPane {
  
    //Key-Value zum verwalten der Ansichten 
    private HashMap<String, Node> screens = new HashMap<>();
    
    public MultiScreenController() {
        super();
    }

    /**
     * Fuege einen Screen der HashMap zu
     * @param name
     * @param screen
     */
    public void addScreen(String name, Node screen) {
        screens.put(name, screen);
    }

    /**
     * Gibt die Node zurueck (also 
     * ein Item aus dem Scene-Graph) in diesem Fall den
     * Screen
     * @param name Screenname
     * @return boolean
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     * Lade die FXML-Datei und fuege sie der Hashmap zu.
     * Verbindet den Screen mit dem Controller. Resource 
     * ist hier die FXML.
     * 
     */
    public boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader objParentLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = (Parent) objParentLoader.load();
            InterfaceControllScreen objScreenController = ((InterfaceControllScreen) objParentLoader.getController());
            objScreenController.setScreenParent(this);
            addScreen(name, loadScreen);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * Zur Anzeige (also auch Ansichtwechsel) eines bereits geladenen Screens.
     * Fading-Effekt Screenwechsel/Screenanzeige.
     * @param name Screenname
     * @return boolean
     */
    public boolean setScreen(final String name) {       
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(1000), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                    	//alten Screen entfernen
                        getChildren().remove(0);   
                        //neuen Screen zufuegen
                        getChildren().add(0, screens.get(name));     
                        Timeline fadeIn = new Timeline(
                                new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0)));
                        fadeIn.play();
                    }
                }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(2500), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            return true;
        } else {
        	/*
        	 * Ursachen:
        	 * - FXML fehlerhaft
        	 * - falsche @FXML referenzen in Controller
        	 * - Fehler in der Initialize-Methode eines konkreten Controllers
        	 */
            System.out.println("setScreen()-Error. Fehler beim Laden des Screens.");
            return false;
        }
    }           
    
    /**
     * Methode zum entfernen eines Screens aus der HashMap-Sammlung
     * @param name Screenname
     * @return boolean 
     */
    public boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }
}


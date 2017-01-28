/**
 * Datenmodel fuer ein konkretes Highscoreobjekt also einen Eintrag
 * 
 * @author Robert/Markus
 */

package Models;

import javafx.beans.property.SimpleStringProperty;

public class HighscoreModel {
	private String name;
	private String points;	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPoints() {
		return points;
	}
	public void setPoints(String points) {
		this.points = points;
	}
}

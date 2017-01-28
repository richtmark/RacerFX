package Models;

import java.util.ArrayList;
import java.util.Iterator;

public class HighscorePool implements Iterable<HighscoreModel> {
	private ConnectionModel objDbConnection; 
	private ArrayList<HighscoreModel> objHighscorePoolList = new ArrayList<HighscoreModel>();
	private ArrayList<String> rawArrayListFromDb;
		
	/**
	 * Konstruktor Standard
	 */
	public HighscorePool() {
		objDbConnection = new ConnectionModel();
		rawArrayListFromDb = new ArrayList<String>();
		rawArrayListFromDb = objDbConnection.getHighscore();
		rawDbToHighscorePool(); 
	}
	
	/**
	 * Schreibt alle Highscore (Strings) aus der Datenbank in den HighscorePool
	 * Siehe unbedingt rawDbToHighscorePool()
	 */
	private void rawDbToHighscorePool() {
		for (String stringObject: rawArrayListFromDb) {
			rawDbHighscoreToHighscoreModel(stringObject);
		}
	}
	
	/**
	 * Wird nur durch rawDbToHighscorePool() aufgerufen!
	 * Uebertraegt EINEN String aus der Datenbank in das HigscoreModel.
	 * Fügt direkt HighscoreModel der Klassenvariable ArrayList<HighscoreModel> objHighscorePoolList zu.
	 * 
	 * @param rawSingleDbHighcore Schema: name#punkte
	 */
	private void rawDbHighscoreToHighscoreModel (String rawSingleDbHighscore) {
		HighscoreModel objHighscoreModel = new HighscoreModel();
		if (rawSingleDbHighscore.contains("#")) {			
			String[] parts = rawSingleDbHighscore.split("\\#"); // trenne String bei Trennzeichen #
			objHighscoreModel.setName(parts[0]);  // Name
			objHighscoreModel.setPoints(parts[1]); // Punkte
			objHighscorePoolList.add(objHighscoreModel);
		} else {
			System.out.println("PseudoException: no # in Question");
		}
	}
	
	/**
	 * Lesender Zugriff auf Object ArrayList<QuestionModel>
	 * 
	 * @return  ArrayList<QuestionModel>
	 */
	public ArrayList<HighscoreModel> getObjQuestionList() {
		return objHighscorePoolList;
	}

	/**
	 * Aus Iterable Interface Methode. Damit das ArrayList<QuestionModel> - Object
	 * iterierbar ist.
	 */
	@Override
	public Iterator<HighscoreModel> iterator() {
		// TODO Auto-generated method stub
		return objHighscorePoolList.iterator();
	}
}

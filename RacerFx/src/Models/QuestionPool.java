package Models;


import java.util.ArrayList;
import java.util.Iterator;

/**
 * verwaltet eine Pool aus beliebig vielen Fragen vom Typ ArrayList<QuestionModel>.
 * Implementiert das Interface Iterable<QuestionModel> zum iterieren
 * der einzelenen enthaltenen Objekte.
 * Holt sich die Daten aus der Datenbank und "befuellt" das konkrete Datenmodel
 * QuestionModel.
 * 
 * @author Robert/Markus
 */
public class QuestionPool implements Iterable<QuestionModel> {	
	private ConnectionModel objDbConnection; 
	private ArrayList<QuestionModel> objQuestionPoolList = new ArrayList<QuestionModel>();
	private ArrayList<String> rawArrayListFromDb;
		
	/**
	 * Konstruktor Standard
	 */
	public QuestionPool() {
		objDbConnection = new ConnectionModel();
		rawArrayListFromDb = new ArrayList<String>();
		rawArrayListFromDb = objDbConnection.getQuestions();
		rawDbToQuestionPool(); 
	}
	
	/**
	 * Wird nur durch rawDbToQuestionPool() aufgerufen!
	 * Uebertraegt EINEN String aus der Datenbank in das QuestionModel.
	 * Fügt direkt QuestionModel der Klassenvariable ArrayList<QuestionModel> objQuestionPoolList zu.
	 * 
	 * @param rawSingleDbQuestion Schema: Frage?#Antwort1#Antwort2#Antwort3#Antwort4#1" letzte Zahl nach # richtige Antwort
	 */
	private void rawDbQuestionToQuestionModel (String rawSingleDbQuestion) {
		QuestionModel objQuestionModel = new QuestionModel();
		if (rawSingleDbQuestion.contains("#")) {			
			String[] parts = rawSingleDbQuestion.split("\\#"); // trenne String bei Trennzeichen #
			objQuestionModel.setQuestiontext(parts[0]); // Fragetext
			objQuestionModel.setAnswerOne(parts[1]); // Antwortoption 1
			objQuestionModel.setAnswerTwo(parts[2]); // Antwortoption 2
			objQuestionModel.setAnswerThree(parts[3]); // Antwortoption 3
			objQuestionModel.setAnswerFour(parts[4]); // Antwortoption 4
			objQuestionModel.setTrueAnswerString(parts[5]); // Richtige Antwort	als StringNr
			objQuestionPoolList.add(objQuestionModel);
		} else {
			System.out.println("PseudoException: no # in Question");
		}
	}
	
	/**
	 * Schreibt alle Fragen (Strings) aus der Datenbank in den QuestionPool
	 * Siehe unbedingt rawDbToQuestionPool()
	 */
	private void rawDbToQuestionPool() {
		for (String stringObject: rawArrayListFromDb) {
			rawDbQuestionToQuestionModel(stringObject);
		}
	}

	/**
	 * Lesender Zugriff auf Object ArrayList<QuestionModel>
	 * 
	 * @return  ArrayList<QuestionModel>
	 */
	public ArrayList<QuestionModel> getObjQuestionList() {
		return objQuestionPoolList;
	}

	/**
	 * Aus Iterable Interface Methode. Damit das ArrayList<QuestionModel> - Object
	 * iterierbar ist.
	 */
	@Override
	public Iterator<QuestionModel> iterator() {
		// TODO Auto-generated method stub
		return objQuestionPoolList.iterator();
	}

	/*
	public void setObjQuestionList(ArrayList<QuestionModel> objQuestionList) {
		this.objQuestionPoolList = objQuestionList;
	}
	*/
}

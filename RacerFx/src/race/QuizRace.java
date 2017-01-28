/**
 * Stellt Methoden bereit um die Fragen aus der Datenbank zu holen. 
 * (siehe QuestionModel/QuestionPool)
 * Stellt eine Methode bereit um eine zufaellige Frage aus den Fragepool zu erhalten.
 * 
 * ToDo Controller/Quiz-Refractoring evt. ein bisschen Logik aus den Controller hier auslagern
 * die direkt das Quiz betreffen, der Übersicht halber.
 * 
 * @author Robert/Markus
 */

package race;

import java.util.Random;

import Models.QuestionModel;
import Models.QuestionPool;

public class QuizRace {
	private QuestionPool objQuestionPool;
	private QuestionModel objCurrentModel;
	private Random randomQuestionIndex;
	
	
	/**
	 * Standardkonstruktor
	 */
	public QuizRace() {
		objQuestionPool = new QuestionPool();
		randomQuestionIndex = new Random();
	}
	
	/**
	 * gibt ein zufaelliges QuestionModel -Objekt zurueck
	 * 
	 * @return QuestionModel
	 */
	public QuestionModel getRandomQuestion() {
        int index = randomQuestionIndex.nextInt(objQuestionPool.getObjQuestionList().size());
        objCurrentModel = objQuestionPool.getObjQuestionList().get(index);
        //System.out.println(objCurrentModel.getQuestiontext());
        return objCurrentModel;
	}
	

	

}

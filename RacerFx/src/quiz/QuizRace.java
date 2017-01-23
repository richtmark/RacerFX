package quiz;

import java.util.Random;

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

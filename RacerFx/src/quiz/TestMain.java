package quiz;

import java.util.ArrayList;

public class TestMain {

	public static void main(String[] args) {
		
		QuestionPool myQuestionPool = new QuestionPool();
		//ArrayList<QuestionModel> objAlQuestionPool = new ArrayList<QuestionModel>();		
		//DatabaseConnection  myDbConnection = new DatabaseConnection();
		// myDbConnection.insertHighscore("Streber", 550);
		
		//myarrList= myDbConnection.getQuestions();
		
		//HighScoreprint
		//myDbConnection.getHighscore();
		
		for (QuestionModel eintrag : myQuestionPool) {			
		    System.out.println(eintrag.getQuestiontext());
		    System.out.println(eintrag.getAnswerOne());
		    System.out.println(eintrag.getAnswerTwo());
		    System.out.println(eintrag.getAnswerThree());
		    System.out.println(eintrag.getAnswerFour());
		    System.out.println(eintrag.getTrueAnswerString());
		    System.out.println("###############################");
		}	

	}

}
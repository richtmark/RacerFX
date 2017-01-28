/**
 * Das einfache Datenmodel einer einzelnen Frage. Vorerst fest mit einer moegl. wahren Antwort
 * und immer vier Antwortmoeglichkeiten. 
 * 
 */

package Models;

public class QuestionModel {
	
	private String questiontext; 	 //Der Fragetext
	private String answerOne;		 //Antwort ...
	private String answerTwo;
	private String answerThree;
	private String answerFour;	
	private String trueAnswerString; //nummer der korrekten Antwort erstmal als String
		
	public String getQuestiontext() {
		return questiontext;
	}
	public void setQuestiontext(String questiontext) {
		this.questiontext = questiontext;
	}

	public String getAnswerOne() {
		return answerOne;
	}
	public void setAnswerOne(String answerOne) {
		this.answerOne = answerOne;
	}
	public String getAnswerTwo() {
		return answerTwo;
	}
	public void setAnswerTwo(String answerTwo) {
		this.answerTwo = answerTwo;
	}
	public String getAnswerThree() {
		return answerThree;
	}
	public void setAnswerThree(String answerThree) {
		this.answerThree = answerThree;
	}
	public String getAnswerFour() {
		return answerFour;
	}
	public void setAnswerFour(String answerFour) {
		this.answerFour = answerFour;
	}
	public String getTrueAnswerString() {
		return trueAnswerString;
	}
	public void setTrueAnswerString(String trueAnswerString) {
		this.trueAnswerString = trueAnswerString;
	}

}

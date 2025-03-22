package Teacher.experiment;

import java.util.ArrayList;

public class QuestionItem {

    private String quizName;
    private int point;
    private String correctChoice;
    private ArrayList<String> choices;
    private int questionID;

    public QuestionItem(String quizName, int point, String correctChoice){
        this.setQuizName(quizName);
        this.setPoint(point);
        this.setCorrectChoice(correctChoice);
        choices = new ArrayList<String>();
    }

    public void setQuestionID(int questionID){
        this.questionID = questionID;
    }

    public int getQuestionID(){
        return this.questionID;
    }

    public String getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(String correctChoice) {
        this.correctChoice = correctChoice;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public void setChoices(String choice) {
        this.choices.add(choice);
    }

    @Override
    public String toString(){
        return this.getQuizName() + "\n" + this.getPoint() + "\n" + this.getCorrectChoice() + "\n" + this.getChoices();
    }
}

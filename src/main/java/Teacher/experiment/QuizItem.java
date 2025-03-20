package Teacher.experiment;

import com.beust.ah.A;

import java.util.ArrayList;

public class QuizItem {

    private String quizName;
    private int point;
    private String correctChoice;
    private ArrayList<String> choices;

    public QuizItem(String quizName, int point, String correctChoice){
        this.setQuizName(quizName);
        this.setPoint(point);
        this.setCorrectChoice(correctChoice);
        choices = new ArrayList<String>();
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

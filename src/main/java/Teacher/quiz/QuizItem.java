package Teacher.quiz;

import com.beust.ah.A;

import java.util.ArrayList;

public class QuizItem {
    private int quizID;
    private String header;
    private int minScore;
    private int maxScore;
    private String level;
    private ArrayList<QuestionItem> questionList;

    public QuizItem(int quizID){
        this.setQuizID(quizID);
        questionList = new ArrayList<QuestionItem>();
    }

    public QuizItem(int quizID, String header, int minScore, int maxScore, String level){
        this.setQuizID(quizID);
        this.setHeader(header);
        this.setMinScore(minScore);
        this.setMaxScore(maxScore);
        this.setLevel(level);
        questionList = new ArrayList<QuestionItem>();
    }

    public void setQuizID(int quizID){
        this.quizID = quizID;
    }

    public int getQuizID(){
        return this.quizID;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public ArrayList<QuestionItem> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(QuestionItem questionItem) {
        this.questionList.add(questionItem);
    }

    @Override
    public String toString(){
        return this.quizID + "";
    }
}

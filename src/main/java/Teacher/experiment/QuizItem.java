package Teacher.experiment;

public class QuizItem {
    private int quizID;

    public QuizItem(int quizID){
        this.setQuizID(quizID);
    }

    public void setQuizID(int quizID){
        this.quizID = quizID;
    }

    public int getQuizID(){
        return this.quizID;
    }
}

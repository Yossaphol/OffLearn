package Student.Quiz;

public class Question {
    private String questionText;
    private String optionA, optionB, optionC, optionD;
    private int type;
    private String corectness;
    private String selectedChoice;
    private String correctAns;

    public Question(int type, String questionText, String optionA, String optionB, String optionC, String optionD, String corectness, String selectedChoice ,String correctAns) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.type = type;
        this.corectness = corectness;
        this.selectedChoice = selectedChoice;
        this.correctAns = correctAns;
    }

    public String getQuestionText() {
        return questionText;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public int getType(){
        return type;
    }

    public String getCorrectness(){
        return corectness;
    }

    public String getSelectedChoice(){
        return selectedChoice;
    }

    public String getCorrectAns(){
        return correctAns;
    }

}

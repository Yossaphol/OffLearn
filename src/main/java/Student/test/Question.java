package Student.test;

public class Question {
    private String questionText;
    private String optionA, optionB, optionC, optionD;
    private int type;

    public Question(int type, String questionText, String optionA, String optionB, String optionC, String optionD) {
        this.questionText = questionText;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.type = type;
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
}

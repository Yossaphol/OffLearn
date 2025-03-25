package Teacher.quiz;

public class QuizBoxItem {
    private String name;
    private int questionCount;
    private int maxScore;
    private int minScore;

    public QuizBoxItem(String name, int cnt, int maxScore, int minScore){
        this.setName(name);
        this.setQuestionCount(cnt);
        this.setMaxScore(maxScore);
        this.setMinScore(minScore);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }

    public int getMinScore() {
        return minScore;
    }

    public void setMinScore(int minScore) {
        this.minScore = minScore;
    }

    @Override
    public String toString(){
        return this.getName() + "\n" +
                this.getMaxScore() + "\n" +
                this.getMinScore() + "\n" +
                this.getQuestionCount();
    }
}

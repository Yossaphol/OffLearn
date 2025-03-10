package Database;

public class test {
    static QuestionDB con = new QuestionDB();
    public static void main(String[] args) {
        con.saveQuestion("test", "test", 5);
    }
}

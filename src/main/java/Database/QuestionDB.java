package Database;

import Teacher.quiz.QuestionItem;

import java.sql.*;
import java.util.ArrayList;

public class QuestionDB extends ConnectDB{

    @Override
    public void saveToDB() {

    }

    @Override
    public void deleteFromDB() {

    }

    public int saveToDB(int quiz_id, String question, String corr, int point, String imgUrl) {
        String checkSql = "SELECT Question_ID FROM question WHERE Quiz_ID = ? AND questionText = ?";
        String updateSql = "UPDATE question SET correctAns = ?, point = ?, question_img = ? WHERE Question_ID = ?";
        String insertSql = "INSERT INTO question (Quiz_ID, questionText, correctAns, point, question_img) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = this.connectToDB();
             PreparedStatement checkPstm = conn.prepareStatement(checkSql)) {

            checkPstm.setInt(1, quiz_id);
            checkPstm.setString(2, question);

            try (ResultSet rs = checkPstm.executeQuery()) {
                if (rs.next()) {
                    int questionID = rs.getInt("Question_ID");
                    try (PreparedStatement updatePstm = conn.prepareStatement(updateSql)) {
                        updatePstm.setString(1, corr);
                        updatePstm.setInt(2, point);
                        updatePstm.setString(3, imgUrl);
                        updatePstm.setInt(4, questionID);
                        updatePstm.executeUpdate();
                    }
                    return questionID;
                }
            }

            try (PreparedStatement insertPstm = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS)) {
                insertPstm.setInt(1, quiz_id);
                insertPstm.setString(2, question);
                insertPstm.setString(3, corr);
                insertPstm.setInt(4, point);
                insertPstm.setString(5, imgUrl);
                insertPstm.executeUpdate();

                try (ResultSet rs = insertPstm.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }

    
    public void deleteFromDB(int questionID) {
        String sql = "DELETE FROM question WHERE Question_ID = ?";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, questionID);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<QuestionItem> getQuestionsByQuizID(int quizID) {
        QuestionItem questionItem;
        String questionSql = "SELECT * FROM question WHERE Quiz_ID = ?";
        String choiceSql = "SELECT text FROM choices WHERE Question_ID = ?";
        ArrayList<QuestionItem> questionsList = new ArrayList<>();

        try (
                Connection conn = this.connectToDB();
                PreparedStatement questionPstm = conn.prepareStatement(questionSql)
        ) {
            questionPstm.setInt(1, quizID);
            try (ResultSet questionRs = questionPstm.executeQuery()) {
                while (questionRs.next()) {
                    int questionID = questionRs.getInt("Question_ID");
                    String name = questionRs.getString("questionText");
                    int point = questionRs.getInt("point");
                    String correct = questionRs.getString("correctAns");
                    String img = questionRs.getString("question_img");

                    questionItem = new QuestionItem(name, point, correct, img);
                    questionItem.setQuestionID(questionID);

                    try (PreparedStatement choicePstm = conn.prepareStatement(choiceSql)) {
                        choicePstm.setInt(1, questionID);
                        try (ResultSet choiceRs = choicePstm.executeQuery()) {
                            questionItem.clearList();
                            while (choiceRs.next()) {
                                questionItem.setChoices(choiceRs.getString("text"));
                            }
                        }
                    }
                    questionsList.add(questionItem);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionsList;
    }

    public boolean isQuestionExists(int questionID) {
        String sql = "SELECT COUNT(*) FROM question WHERE Question_ID = ?";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, questionID);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int countQuestionsByQuizID(int quizID) {
        String sql = "SELECT COUNT(*) FROM question WHERE Quiz_ID = ?";
        try (
                Connection conn = this.connectToDB();
                PreparedStatement pstm = conn.prepareStatement(sql)
        ) {
            pstm.setInt(1, quizID);
            try (ResultSet rs = pstm.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



}

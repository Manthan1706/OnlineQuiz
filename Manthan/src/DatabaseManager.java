import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/onlinequiz";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connect() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void addQuestionToDatabase(Question question, String category) {
        String sql = "INSERT INTO questions_" + category + " (question, option1, option2, option3, option4, correct_answer) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, question.getQuestion());
            pstmt.setString(2, question.getOption1());
            pstmt.setString(3, question.getOption2());
            pstmt.setString(4, question.getOption3());
            pstmt.setString(5, question.getOption4());
            pstmt.setString(6, question.getCorrectAnswer());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void removeQuestionFromDatabase(int questionId, String category) {
        String sql = "DELETE FROM questions_" + category + " WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, questionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateQuestionInDatabase(Question updatedQuestion, String category, int questionId) {
        String sql = "UPDATE questions_" + category + " SET question = ?, option1 = ?, option2 = ?, option3 = ?, option4 = ?, correct_answer = ? WHERE id = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, updatedQuestion.getQuestion());
            pstmt.setString(2, updatedQuestion.getOption1());
            pstmt.setString(3, updatedQuestion.getOption2());
            pstmt.setString(4, updatedQuestion.getOption3());
            pstmt.setString(5, updatedQuestion.getOption4());
            pstmt.setString(6, updatedQuestion.getCorrectAnswer());
            pstmt.setInt(7, questionId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void saveUserScore(String username, String category, int score) {
        String sql = "INSERT INTO user_scores (username, category, score) VALUES (?, ?, ?)";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, category);
            pstmt.setInt(3, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void DataHistory(){
        String sql = "SELECT * FROM user_scores";
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("+----------+----------+-------+----------------------+");
            System.out.println("| Username | Category | Score | Quiz Date & Time     |");
            System.out.println("+----------+----------+-------+----------------------+");
            while (rs.next()) {
                System.out.println("| " + String.format("%-8s", rs.getString("username")) + " | " + String.format("%-8s", rs.getString("category"))
                + " | " + String.format("%-5s", rs.getInt("score")) + " | " + rs.getDate("quiz_date") + " |" + rs.getTime("quiz_date") + " |" );
            }
            System.out.println("+----------+----------+-------+----------------------+");
        } catch (SQLException e) {
            System.out.println("Enter Valid UserName in UserRecord");
            e.printStackTrace();
        }
    }

    public List<Question> getQuestions(String category) {
        List<Question> questions = new ArrayList<>();
        String query = "SELECT * FROM questions_" + category + " ORDER BY RAND()"; // Adjust query based on your schema
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                // Assuming you have columns: id, question, option1, option2, option3, option4, correctAnswer
                int id = resultSet.getInt("id");
                String questionText = resultSet.getString("question");
                String option1 = resultSet.getString("option1");
                String option2 = resultSet.getString("option2");
                String option3 = resultSet.getString("option3");
                String option4 = resultSet.getString("option4");
                String correctAnswer = resultSet.getString("correct_answer");

                Question question = new Question( questionText, option1, option2, option3, option4, correctAnswer);
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    public void getPercentage(String username, String category) {
        String sql = "SELECT score, id FROM user_scores WHERE username = ? AND category = ?";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int score = rs.getInt("score");
                    int id = rs.getInt("id");
                    double percentage = score * id*10 /  id;
                    System.out.println(percentage + "%");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Question> getRankings(String username , String category) {
        List<Question> rankings = new ArrayList<>();
        String sql = "SELECT username, score, id FROM user_scores WHERE category = ? ORDER BY score DESC";
        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, category);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int score = rs.getInt("score");
                    int id = rs.getInt("id");
                    double percentage = (double) score * id*10 / id;
                    Question userScore = Question(username , category);
                    rankings.add(userScore);
                    System.out.println(percentage);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankings;
    }

    private Question Question(String username, String category) {
        throw new UnsupportedOperationException("Unimplemented method 'Question'");
    }
}
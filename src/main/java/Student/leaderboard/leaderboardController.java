package Student.leaderboard;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class leaderboardController implements Initializable {

    public HBox searhbar_container;
    public VBox leftWrapper;
    public Label welcomeText;
    public VBox leaderboardTable_container;
    public VBox studentList;
    public ComboBox select_subject;

    //First
    public Circle pic1;
    public Label name_nd1;
    public Label pts_nd1;
    //Second
    public Circle pic2;
    public Label name_nd;
    public Label pts_nd;
    //Third
    public Circle pic3;
    public Label name_nd2;
    public Label pts_nd2;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcomeText.setText("Want to see your name on the leaderboard?\n" +
                "Join a course and test your skills!");

        loadStd();
        loadStdFromSubject();
        setFirst();
        setSecond();
        setThird();
    }

    private void loadStd() {
       try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Student/studentNode.fxml"));
           studentNodeController controller = loader.getController();
           Node stdBox = loader.load();

           //controller.setDetail(); Data from table
           studentList.getChildren().add(stdBox);
       }catch (IOException e){
           e.printStackTrace();
       }
    }

    private void loadStdFromSubject(){
        //select_subject
    }

    private void setFirst(){

    }

    private void setSecond(){

    }
    private void setThird(){

    }





    
}

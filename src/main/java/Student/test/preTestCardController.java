package Student.test;

import Student.HomeAndNavigation.HomeController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class preTestCardController implements Initializable {

    public Label teacherName;
    public Label expertIn;
    public Circle teacherPic;
    public Label duration;
    public Label amount;
    public Label hardness;
    public Label shortDescription;
    public Label name;
    HomeController ef = new HomeController();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setPreTestName(String n){
        name.setText(n);
    }

    public void setAmount(int n){
        amount.setText(String.valueOf(n)+" ข้อ");
    }

    public void setPretestDescription(String d){
        shortDescription.setText(d);
    }

    public void setHardness(String l){
        hardness.setText(l);
    }

    public void setPretestDuration(int d){
        duration.setText(String.valueOf(d)+" นาที");
    }

    public void setTeacherName(String name){
        teacherName.setText(name);
    }

    public void setTeacherPic(String path){
        ef.loadAndSetImage(teacherPic, path);
    }

    public void setTeacherExpertise(String e){
        expertIn.setText(e);
    }
}

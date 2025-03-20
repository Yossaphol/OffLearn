package Student.roadmap;

import Student.HomeAndNavigation.HomeController;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class myRoadmapCard implements Initializable {
    public Label name;
    public Label shortDescription;
    public Label progression;
    public Label finish;
    public Label duration;
    public Circle teacherPic;
    public Label teacherName;
    public Label expertIn;
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setRoadmapName(String n){
        name.setText(n);
    }

    public void setShortDescription(String d){
        shortDescription.setText(d);
    }

    public void setProgression(int current, int total){
        progression.setText(String.valueOf(current)+"/"+String.valueOf(total));
    }

    public void setDurationRoadmap(int d){
        duration.setText(String.valueOf(d));
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

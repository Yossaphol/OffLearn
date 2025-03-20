package Student.courseManage;

import Student.HomeAndNavigation.HomeController;
import Student.HomeAndNavigation.Navigator;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class cartCardController implements Initializable {
    public Rectangle picture;
    public Label name;
    public Label description;
    public Circle categoryPic;
    public Label category;
    public ImageView one;
    public ImageView two;
    public ImageView three;
    public ImageView four;
    public Label review;
    public Label price;
    public Button enrollBtn;
    @FXML
    public Button deleteBtn;
    public Spinner amount;
    HomeController ef = new HomeController();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        four.setVisible(false);

        ef.hoverEffect(enrollBtn);
        deleteBtn.setVisible(false);
        setupSpinner();
        route();
    }

    private void route(){
        Navigator nav = new Navigator();
        enrollBtn.setOnMouseClicked(nav::courseEnrollRoute);
    }

    public void setName(String n){
        name.setText(n);
    }
    public void setDescription(String n){
        description.setText(n);
    }
    public void setCategory(String picturePath, String catagoryName){
        category.setText(catagoryName);
        ef.loadAndSetImage(categoryPic, picturePath);

    }
    public void setPrice(double n){
        price.setText(n +" บาท");
    }
    public void setRating(double rating , int totalReview){
        review.setText(String.valueOf(rating)+" ("+String.valueOf(totalReview)+")");

        switch ((int) rating){
            case 1:
                one.setVisible(true);
                break;
            case 2:
                one.setVisible(true);
                two.setVisible(true);
                break;
            case 3:
                one.setVisible(true);
                two.setVisible(true);
                three.setVisible(true);
                break;
            case 4:
                one.setVisible(true);
                two.setVisible(true);
                three.setVisible(true);
                four.setVisible(true);
                break;
        }
    }

    public void setPicture(String path){
        ef.loadAndSetImage(picture, path);
    }


    public void displayDeleteBtn(boolean isVisible) {
        FadeTransition fade = new FadeTransition(Duration.millis(300), deleteBtn);

        if (isVisible) {
            deleteBtn.setVisible(true);
            deleteBtn.setOpacity(0);
            fade.setToValue(1.0);
        } else {
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fade.setOnFinished(event -> deleteBtn.setVisible(false));
        }

        fade.setInterpolator(Interpolator.EASE_OUT);
        fade.play();
    }


    private void setupSpinner() {
        amount.setEditable(true);
        amount.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE, 1));

        amount.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                amount.getEditor().setText(oldValue);
            }
        });
    }

    public void deleteCourse(ActionEvent e){  //ยังไม่ได้ทำให้ลบได้
        System.out.println("Delete success!");
    }


}

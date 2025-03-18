package Student.task;

import Student.HomeAndNavigation.HomeController;
import javafx.animation.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class taskController implements Initializable {
    public VBox navBar;
    public HBox searhbar_container;
    public HBox MainFrame;
    public TabPane tabPane;
    public Tab btnUpComing;
    public VBox containerUpComing;
    public VBox boxUpComing;
    public Tab btnLate;
    public VBox containerLate;
    public Tab btnComplete;
    public VBox containerComplete;
    public Label point;
    public Label taskDetail;
    public Label taskInformation;
    public Button task;
    public Label labelUpComingDate;
    public VBox boxUpComing1;
    public Label labelUpComingDate1;
    public Button task1;
    public Label taskInformation1;
    public Label taskDetail1;
    public Label point1;
    public VBox boxUpComing2;
    public Button task2;
    public Label taskInformation2;
    public Label taskDetail2;
    public Label point2;
    public Label labelLateDate;
    public Label labelDone;
    public Label labelUpComingDay;
    //พวกที่มีเลขตามหลังเช่น task1 point2 1 คือแท็บหน้าเลยกำหนด 2 คือแท็บหน้าเสร็จ ส่วนที่ไม่มีเลขคือแท็บกำลังจะมาถึง

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

       hoverEffect(task);
       hoverEffect(task1);
       hoverEffect(task2);

        setUpComing();
        setLate();
        setComplete();
        setTabAnimation();
    }

    private void setUpComing(){
        labelUpComingDate.setText("เสาร์ 8 มีนาคม 2568");
        taskInformation.setText("ส่งหน้า GUI Teacher");
        taskDetail.setText("คุณต้องส่งงานเดี๋ยวนี้");
        point.setText("0.5 pst");
    }

    private void setLate(){
        labelLateDate.setText("เสาร์ 5 มีนาคม 2568");
        taskInformation1.setText("ส่งหน้า GUI ไคแอ้น");
        taskDetail1.setText("คุณต้องส่งงานเดี๋ยวนี้");
        point1.setText("0.5 pst");
    }

    private void setComplete(){
        labelDone.setText("เสาร์ 5 มีนาคม 2568");
        taskInformation2.setText("ยังส่งกันไม่ครบ!!");
        taskDetail2.setText("คุณต้องส่งงานเดี๋ยวนี้!!!");
        point2.setText("-1,000,000 pst");
    }


    public void hoverEffect(Button btn) {
        ScaleTransition scaleUp = new ScaleTransition(Duration.millis(200), btn);
        scaleUp.setFromX(1);
        scaleUp.setFromY(1);
        scaleUp.setToX(1.02);
        scaleUp.setToY(1.02);
        ScaleTransition scaleDown = new ScaleTransition(Duration.millis(200), btn);
        scaleDown.setFromX(1.02);
        scaleDown.setFromY(1.02);
        scaleDown.setToX(1);
        scaleDown.setToY(1);

        btn.setOnMouseEntered(mouseEvent -> {
            scaleUp.play();
        });
        btn.setOnMouseExited(mouseEvent -> {
            scaleDown.play();
        });
    }

    public void setTabAnimation() {
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != null && newTab.getContent() != null) {
                newTab.getContent().getStyleClass().add("tab-content");

                newTab.getContent().setTranslateX(10);
                newTab.getContent().setOpacity(0);

                TranslateTransition slide = new TranslateTransition(Duration.millis(300), newTab.getContent());
                slide.setFromX(10);
                slide.setToX(0);

                Timeline opacityAnimation = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(newTab.getContent().opacityProperty(), 0)),
                        new KeyFrame(Duration.millis(500), new KeyValue(newTab.getContent().opacityProperty(), 1))
                );

                ParallelTransition animation = new ParallelTransition(slide, opacityAnimation);
                animation.play();
            }
        });
    }


}

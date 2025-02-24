package inbox.gChat;

import inbox.DataBase.topicDB;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class topicContent implements Initializable {

    @FXML
    private Label name;

    @FXML
    private ImageView profile;

    @FXML
    private Label time;

    @FXML
    private Text messages;

    @FXML
    private ImageView favourite;

    @FXML
    private Label favourite_count;

    private boolean fav;
    private int topicId;
    private topicDB database = new topicDB();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fav_clicked();
    }

    public void setTopicId(int id){
        this.topicId = id;
    }
    public void setMessages(String messages) {
        this.messages.setText(messages);
    }

    public void setName(String name){
        this.name.setText(name);
    }

    public void setTime(String time){
        this.time.setText(time);
    }

    public void setFavourite_count(int count){
        this.favourite_count.setText(count + "");
    }

    public void fav_clicked(){
        favourite.setOnMouseClicked(mouseEvent -> {
            if (!this.getFav()) {
                this.setLike(true);
                this.setFav(true);
                updateFavouriteCount();
            } else {
                this.setLike(false);
                this.setFav(false);
                decreaseFavouriteCount();
            }
        });
    }

    public void setFav(boolean b){
        this.fav = b;
    }

    public boolean getFav(){
        return this.fav;
    }


    public void setLike(boolean like){
        Image image;
        if (like) {
            image = new Image(getClass().getResource("/img/icon/favourite1.png").toExternalForm());
        } else{
            image = new Image(getClass().getResource("/img/icon/favourite.png").toExternalForm());
        }
        this.favourite.setImage(image);
    }

    public void updateFavouriteCount() {
        database.updateFavCount(topicId);
        int currentCount = Integer.parseInt(favourite_count.getText()) + 1;
        favourite_count.setText(String.valueOf(currentCount));
    }

    public void decreaseFavouriteCount(){
        database.decreseFavCount(topicId);
        int currentCount = Integer.parseInt(favourite_count.getText()) - 1;
        favourite_count.setText(String.valueOf(currentCount));
    }
}

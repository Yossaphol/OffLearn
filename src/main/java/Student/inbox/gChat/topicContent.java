package Student.inbox.gChat;

import Database.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class topicContent implements Initializable {

    @FXML
    private Label name;

    @FXML
    private Circle profile;

    @FXML
    private Label time;

    @FXML
    private Text messages;

    @FXML
    private ImageView favourite;

    @FXML
    private Label favourite_count;

    @FXML
    private VBox topicView;


    private boolean fav;
    private int topicId;
    private topicDB database = new topicDB();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fav_clicked();
    }

    public void setTopicId(int id) {
        this.topicId = id;
        boolean wasFavorited = database.isTopicFavorited(id);
        this.setFav(wasFavorited);
        this.setLike(wasFavorited);
        int storedCount = database.getFavoriteCount(id);
        if (storedCount > 0) {
            this.setFavourite_count(storedCount);
        }
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

    public void setFavourite_count(int count) {
        this.favourite_count.setText(count + "");
        if (this.topicId > 0) {
            database.initializeFavoriteCount(this.topicId, count);
        }
    }

    public void setProfile(String Url){
        Image img;
        if (Url.startsWith("http") || Url.startsWith("https")) {
            img = new Image(Url);
        } else {
            img = new Image(getClass().getResource(Url).toExternalForm());
        }

        this.profile.setStroke(Color.TRANSPARENT);
        this.profile.setFill(new ImagePattern(img));
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
        int currentCount = database.getFavoriteCount(topicId);
        favourite_count.setText(String.valueOf(currentCount));
    }

    public void decreaseFavouriteCount() {
        database.decreseFavCount(topicId);
        int currentCount = database.getFavoriteCount(topicId);
        favourite_count.setText(String.valueOf(currentCount));
    }
}
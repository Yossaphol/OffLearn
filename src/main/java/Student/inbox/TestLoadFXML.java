package Student.inbox;

import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.util.Objects;

public class TestLoadFXML {
    public static void main(String[] args) {
        try {
            FXMLLoader.load(Objects.requireNonNull(TestLoadFXML.class.getResource("/fxml/Student/Teacher.inbox/pChat.fxml")));
            System.out.println("FXML Loaded Successfully!");
        } catch (IOException e) {
            System.out.println("Failed to load FXML!");
        }
    }
}

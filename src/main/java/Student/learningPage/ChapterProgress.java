package Student.learningPage;

import Database.MyProgressDB;
import a_Session.SessionManager;
import javafx.scene.media.MediaPlayer;

public class ChapterProgress extends MyProgressDB{


    public void updateProgress(MediaPlayer mediaPlayer,int chapID) {
        double currentTime = mediaPlayer.getCurrentTime().toSeconds();
        double totalTime = mediaPlayer.getTotalDuration().toSeconds();
        double tmp = (currentTime / totalTime) * 100;
        double progress = Double.parseDouble(String.format("%.2f", tmp));
        System.out.println(progress + " " + totalTime + " " + currentTime);
        saveToDB(String.valueOf(chapID), SessionManager.getInstance().getUserID(), progress, currentTime);
    }

}

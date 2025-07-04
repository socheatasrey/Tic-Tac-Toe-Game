
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private Clip backgroundClip;
    //private boolean isMuted = false;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Load Scene directly since your FXML starts with <Scene>
        Scene scene = FXMLLoader.load(getClass().getResource("Home.fxml"));
        stage.setTitle("Tic Tac Toe Game");
        stage.setScene(scene);
        stage.show();

        playBackgroundMusic("Harmony-of-the-Earth-16bit.wav");

    }

    private void playBackgroundMusic(String filePath) {
        try {
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            backgroundClip = AudioSystem.getClip();
            backgroundClip.open(audioStream);
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundClip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}



import javax.print.DocFlavor.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class SettingController {

    @FXML
    private Button button_return;

    @FXML
    private ToggleButton togglebutton_music;

    @FXML
    private ToggleButton togglebutton_sound;

    @FXML
    void button_return_action(ActionEvent event) {

        try {

            Stage stage = (Stage) button_return.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("Home.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }

    private Clip backgroundClip;

    @FXML
    void togglebutton_music_action(ActionEvent event) {
        if (togglebutton_music.isSelected()) {
        // Turn ON music
        togglebutton_music.setText("ON");
        if (backgroundClip != null) {
            backgroundClip.start(); // Start or resume music
            backgroundClip.loop(Clip.LOOP_CONTINUOUSLY); // Loop it
        }
    } else {
        // Turn OFF music
        togglebutton_music.setText("OFF");
        if (backgroundClip != null && backgroundClip.isRunning()) {
            backgroundClip.stop(); // Stop the music
        }
     }
    }

    @FXML
    void togglebutton_sound_action(ActionEvent event) {

    }

    private void playClickSound() {
    try {
        java.net.URL soundURL = getClass().getResource("click_soundeffect.wav");
        if (soundURL == null) {
            System.out.println("Sound file not found!");
            return;
        }

        AudioInputStream audioInput = AudioSystem.getAudioInputStream(soundURL);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class PauseController {

    @FXML
    private Button button_continue;

    @FXML
    private Button button_home;

    @FXML
    private Button button_playagain;

    @FXML
    private ToggleButton togglebutton_music;

    @FXML
    private ToggleButton togglebutton_sound;

    private Clip clickSound;

    @FXML
    public void initialize() {
        // Load click sound
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(
                getClass().getResource("click_soundeffect.wav")
            );
            clickSound = AudioSystem.getClip();
            clickSound.open(audioInput);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set toggle state based on sound manager
        togglebutton_sound.setSelected(!SoundManager.isSoundEnabled);
        togglebutton_music.setSelected(!SoundManager.isMusicEnabled);

        // Apply sound to all buttons once the scene is fully loaded
        Platform.runLater(() -> {
            applyClickSoundToAllButtons(button_continue.getScene().getRoot());
        });
    }

    // Automatically play click sound for all buttons
    private void applyClickSoundToAllButtons(Parent root) {
        for (Node node : root.lookupAll(".button")) {
            if (node instanceof Button button) {
                button.addEventFilter(ActionEvent.ACTION, e -> {
                    if (SoundManager.isSoundEnabled && clickSound != null) {
                        clickSound.setFramePosition(0); // rewind
                        clickSound.start();
                    }
                });
            }
        }
    }

    @FXML
    void button_continue_action(ActionEvent event) {
        // Just close the pause scene, no need to manually play sound
        Stage stage = (Stage) button_continue.getScene().getWindow();
        stage.close();
    }

    @FXML
    void button_home_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_home.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Home.fxml")));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void button_playagain_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_playagain.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("TicTacToe.fxml"))); // Change if your game file is different
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void togglebutton_music_action(ActionEvent event) {
        SoundManager.isMusicEnabled = !togglebutton_music.isSelected(); // selected = muted

        if (SoundManager.backgroundMusic != null) {
            if (SoundManager.isMusicEnabled) {
                ((Clip) SoundManager.backgroundMusic).setFramePosition(0);
                ((DataLine) SoundManager.backgroundMusic).start();
                ((Clip) SoundManager.backgroundMusic).loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                ((DataLine) SoundManager.backgroundMusic).stop();
            }
        }
    }

    @FXML
    void togglebutton_sound_action(ActionEvent event) {
        SoundManager.isSoundEnabled = !togglebutton_sound.isSelected(); // selected = muted
    }
}

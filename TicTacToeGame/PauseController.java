

import javax.sound.sampled.Clip;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

    @FXML
    void button_continue_action(ActionEvent event) {

    }

    @FXML
    void button_home_action(ActionEvent event) {

        try {

            Stage stage = (Stage) button_home.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("Home.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private Clip backgroundClip;
    //private boolean isMuted = false;

    @FXML
    void button_playagain_action(ActionEvent event) {

    }

    @FXML
    void togglebutton_music_action(ActionEvent event) {
        if (togglebutton_music.isSelected()) {
            backgroundClip.start();
            togglebutton_music.setText("OFF");
        } else {
            backgroundClip.stop();
            togglebutton_music.setText("ON");
        }
    }

    @FXML
    void togglebutton_sound_action(ActionEvent event) {

    }

}

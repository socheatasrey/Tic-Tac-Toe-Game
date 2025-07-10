package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import util.SoundUtil;

public class PauseController {

    @FXML private Button button_continue;
    @FXML private Button button_home;
    @FXML private Button button_playagain;
    @FXML private ToggleButton togglebutton_music;
    @FXML private ToggleButton togglebutton_sound;

    private String previousSceneFxml;

    public void setPreviousScene(String fxml) {
        this.previousSceneFxml = fxml;
    }

    @FXML
    public void initialize() {
        togglebutton_music.setSelected(SoundUtil.isMusicMuted());
        togglebutton_sound.setSelected(SoundUtil.isSoundMuted());

        togglebutton_music.setText(SoundUtil.isMusicMuted() ? "OFF" : "ON");
        togglebutton_sound.setText(SoundUtil.isSoundMuted() ? "OFF" : "ON");
    }

    @FXML
    void button_continue_action(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) button_continue.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource(previousSceneFxml));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void button_home_action(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) button_home.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void button_playagain_action(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) button_playagain.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource(previousSceneFxml));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void togglebutton_music_action(ActionEvent event) {
        boolean isMuted = togglebutton_music.isSelected();
        SoundUtil.setMusicMuted(isMuted);
        togglebutton_music.setText(isMuted ? "OFF" : "ON");
        SoundUtil.clik();
    }

    @FXML
    void togglebutton_sound_action(ActionEvent event) {
        boolean isMuted = togglebutton_sound.isSelected();
        SoundUtil.setSoundMuted(isMuted);
        togglebutton_sound.setText(isMuted ? "OFF" : "ON");
        SoundUtil.clik();
    }
}

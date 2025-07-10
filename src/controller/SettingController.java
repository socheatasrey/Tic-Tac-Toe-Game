package controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import util.SoundUtil;

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
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

    }

    @FXML
    void togglebutton_music_action(ActionEvent event) {
        boolean newMuteState = !SoundUtil.isMusicMuted();
        SoundUtil.setMusicMuted(newMuteState);
        togglebutton_music.setText(newMuteState ? "OFF" : "ON");
        if (!newMuteState) {
            SoundUtil.loopMusic("Music_Background.wav"); // resume music if unmuted
        }
    }

    @FXML
    void togglebutton_sound_action(ActionEvent event) {
        boolean newMuteState = !SoundUtil.isSoundMuted();
        SoundUtil.setSoundMuted(newMuteState);
        togglebutton_sound.setText(newMuteState ? "OFF" : "ON");
    }

}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.SoundUtil;

public class OptionController {

    @FXML
    private Button button_profile;

    @FXML
    private Button button_setting;

    @FXML
    void bttn_3x3_option(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) button_profile.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Option3x3.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void bttn_6x6_option(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) button_profile.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Option6x6.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void button_profile_action(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) button_profile.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Profile.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void button_setting_action(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) button_profile.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Setting.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
    }

}

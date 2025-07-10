package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import util.CurrentTempUtil;
import util.SoundUtil;

public class Option3x3Controller {

    @FXML
    private Button bttn_back;

    @FXML
    void bttn_ai_3x3_play(ActionEvent event) {
        CurrentTempUtil.currentProgress.setOpponetName("AI");

        System.out.println("Game type: " + CurrentTempUtil.currentProgress.getGameType());
        SoundUtil.clik();
        try {
            Stage stage = (Stage) bttn_back.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Ai3x3.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void bttn_back_action(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) bttn_back.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Option.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @FXML
    void bttn_pvp_play(ActionEvent event) {
        System.out.println("Game type: " + CurrentTempUtil.currentProgress.getGameType());
        SoundUtil.clik();
        try {
            Stage stage = (Stage) bttn_back.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/PVP3x3.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

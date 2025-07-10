package  controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.GameHistoryEntry;
import util.CurrentTempUtil;
import util.SoundUtil;

public class Option6x6Controller {

    @FXML
    private Button bttn_back;

    @FXML
    void bttn_6x6_ai(ActionEvent event) {
        CurrentTempUtil.currentProgress.setOpponetName("AI");
        SoundUtil.clik();
        try {
            Stage stage = (Stage) bttn_back.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Ai6x6.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void bttn_6x6_pvp(ActionEvent event) {
        SoundUtil.clik();
        try {
            Stage stage = (Stage) bttn_back.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/PVP6x6.fxml"));
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

}

package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Option3x3Controller {

    @FXML
    private Button bttn_back;

    @FXML
    void bttn_ai_3x3_play(ActionEvent event) {
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

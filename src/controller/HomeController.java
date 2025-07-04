package controller;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button button_profile;

    @FXML
    private Button button_setting;

    @FXML
    private Button button_start;

    @FXML
    void button_profile_action(ActionEvent event) {

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

        try {
            Stage stage = (Stage) button_setting.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Setting.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void button_start_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_start.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Option.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

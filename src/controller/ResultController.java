package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ResultController {

    @FXML
    private Button btn_home;

    @FXML
    private Button btn_restart;

    @FXML
    private Button button_3x3;

    @FXML
    private Button button_6x6;

    @FXML
    private Label label_win_or_lose;

    @FXML
    private Text resultText;


    public void setResultText(String resultMessage) {
        resultText.setText(resultMessage);
    }


    @FXML
    void btn_home_on_action(ActionEvent event) {
        try {
            Stage stage = (Stage) btn_home.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String fromSceneFXML;

    public void setFromScene(String fxmlFile) {
        this.fromSceneFXML = fxmlFile;
    }

    @FXML
    void btn_restart_on_action(ActionEvent event) {
        try {
            Stage stage = (Stage) btn_restart.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource(fromSceneFXML));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void button_3x3_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_3x3.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/PVP3x3.fxml"));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void button_6x6_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_6x6.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/PVP6x6.fxml"));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

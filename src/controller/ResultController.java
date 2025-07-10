package controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import dao.GameHistoryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GameHistoryEntry;
import util.CurrentTempUtil;

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

        String labelMessage;
        String status = "draw";

        switch (resultMessage.toUpperCase()) {
            case "WIN":
                labelMessage = "Player (" + CurrentTempUtil.currentUser.getUsername() + ") Won!";
                status = "win";
                break;
            case "LOSE":
                labelMessage = "Player (" + CurrentTempUtil.currentProgress.getOpponentName() + ") Won!";
                status = "lose";
                break;
            case "DRAW":
                labelMessage = "It's a Draw!";
                break;
            case "CONCEDE":
                labelMessage = "Game Conceded!";
                break;
            default:
                labelMessage = "Game Over!";
        }

        label_win_or_lose.setText(labelMessage);

        System.out.println("GameType: " + CurrentTempUtil.currentProgress.getGameType());
        System.out.println("OpponentName: " + CurrentTempUtil.currentProgress.getOpponentName());


        // 🔥 Save game history
        GameHistoryEntry entry = new GameHistoryEntry();
        entry.setUserID(CurrentTempUtil.currentUser.getId());
        entry.setOpponetName(CurrentTempUtil.currentProgress.getOpponentName());
        entry.setGameType(CurrentTempUtil.currentProgress.getGameType());
        entry.setGameStatus(status);
        entry.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        entry.toString();
        GameHistoryDAO.saveGameHistory(entry);
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
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Option3x3.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void button_6x6_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_6x6.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Option6x6.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

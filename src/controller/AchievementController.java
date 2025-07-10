package controller;

import java.net.URL;
import java.util.ResourceBundle;

import dao.GameHistoryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import util.CurrentTempUtil;

public class AchievementController implements Initializable{
    @FXML private Button button_return;
    @FXML private Label lbl_win;
    @FXML private Label lbl_lose;
    @FXML private Label lbl_draws;
    @FXML private Label lbl_totals;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int userId = CurrentTempUtil.currentUser.getId(); // get current user ID

        int winCount = GameHistoryDAO.countGamesByStatus(userId, "win");
        int loseCount = GameHistoryDAO.countGamesByStatus(userId, "lose");
        int drawCount = GameHistoryDAO.countGamesByStatus(userId, "draw");

        int totalGames = winCount + loseCount + drawCount;

        lbl_win.setText(String.valueOf(winCount));
        lbl_lose.setText(String.valueOf(loseCount));
        lbl_draws.setText(String.valueOf(drawCount));
        lbl_totals.setText(String.valueOf(totalGames));
    }

    @FXML
    void button_return_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_return.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Profile.fxml"));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

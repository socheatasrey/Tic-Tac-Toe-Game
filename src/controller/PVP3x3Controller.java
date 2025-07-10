package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import util.CurrentTempUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PVP3x3Controller implements Initializable {

    @FXML private Button button_0x0, button_0x1, button_0x2;
    @FXML private Button button_1x0, button_1x1, button_1x2;
    @FXML private Button button_2x0, button_2x1, button_2x2;
    @FXML private Button button_redo, button_concrede, button_pause;
    @FXML private Text winnerText;

    private List<Button> buttons;
    private int playerTurn = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TextInputDialog dialog = new TextInputDialog("Player 2");
        dialog.setTitle("Enter Opponent Name");
        dialog.setHeaderText("Player vs Player");
        dialog.setContentText("Enter Player 2's name:");

        dialog.showAndWait().ifPresent(opponentName -> {
            CurrentTempUtil.currentProgress.setOpponetName(opponentName);
        });

        CurrentTempUtil.printCurrentProgress();
        buttons = Arrays.asList(
            button_0x0, button_0x1, button_0x2,
            button_1x0, button_1x1, button_1x2,
            button_2x0, button_2x1, button_2x2
        );

        buttons.forEach(button -> {
            button.setText("");
            button.setDisable(false);
            button.setFocusTraversable(false);
            button.setOnMouseClicked(mouseEvent -> {
                setPlayerSymbol(button);
                button.setDisable(true);
                checkIfGameIsOver();
            });
        });

        winnerText.setText("Tic-Tac-Toe");
    }

    private void setPlayerSymbol(Button button) {
        if (playerTurn % 2 == 0) {
            button.setText("X");
            playerTurn = 1;
        } else {
            button.setText("O");
            playerTurn = 0;
        }
    }

    private void checkIfGameIsOver() {
        String[][] board = {
            {button_0x0.getText(), button_0x1.getText(), button_0x2.getText()},
            {button_1x0.getText(), button_1x1.getText(), button_1x2.getText()},
            {button_2x0.getText(), button_2x1.getText(), button_2x2.getText()}
        };

        String winner = null;

        for (int i = 0; i < 3; i++) {
            if (!board[i][0].isEmpty() &&
                board[i][0].equals(board[i][1]) &&
                board[i][1].equals(board[i][2])) {
                winner = board[i][0];
            }

            if (!board[0][i].isEmpty() &&
                board[0][i].equals(board[1][i]) &&
                board[1][i].equals(board[2][i])) {
                winner = board[0][i];
            }
        }

        if (!board[0][0].isEmpty() &&
            board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2])) {
            winner = board[0][0];
        }

        if (!board[0][2].isEmpty() &&
            board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0])) {
            winner = board[0][2];
        }

        if (winner != null) {
            goToResultScene("WON");
        } else if (buttons.stream().allMatch(b -> !b.getText().isEmpty())) {
            goToResultScene("DRAW");
        }
    }

    @FXML
    void button_redo_on_action(ActionEvent event) {
        buttons.forEach(b -> {
            b.setText("");
            b.setDisable(false);
        });
        playerTurn = 0;
        // winnerText.setText("Tic-Tac-Toe");
    }

    @FXML
    void button_concrede_on_action(ActionEvent event) {
        goToResultScene("CONCEDE");
    }

    @FXML
    void button_pause_on_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_0x0.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Pause.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void goToResultScene(String resultMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Result.fxml"));
            Parent root = loader.load();

            ResultController controller = loader.getController();
            controller.setResultText(resultMessage);
            controller.setFromScene("/fxml/PVP3x3.fxml"); 

            Stage stage = (Stage) button_0x0.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

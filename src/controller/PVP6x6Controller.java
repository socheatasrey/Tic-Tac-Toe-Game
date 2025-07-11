package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import util.CurrentTempUtil;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PVP6x6Controller implements Initializable {

    @FXML private Button button_concede;
    @FXML private Button button_pause;
    @FXML private Button button_redo;

    private List<Button> buttons;
    private int playerTurn = 0;

    @FXML private Button button_0x0, button_0x1, button_0x2, button_0x3, button_0x4, button_0x5;
    @FXML private Button button_1x0, button_1x1, button_1x2, button_1x3, button_1x4, button_1x5;
    @FXML private Button button_2x0, button_2x1, button_2x2, button_2x3, button_2x4, button_2x5;
    @FXML private Button button_3x0, button_3x1, button_3x2, button_3x3, button_3x4, button_3x5;
    @FXML private Button button_4x0, button_4x1, button_4x2, button_4x3, button_4x4, button_4x5;
    @FXML private Button button_5x0, button_5x1, button_5x2, button_5x3, button_5x4, button_5x5;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        TextInputDialog dialog = new TextInputDialog("Player 2");
        dialog.setTitle("Enter Opponent Name");
        dialog.setHeaderText("Player vs Player");
        dialog.setContentText("Enter Player 2's name:");

        dialog.showAndWait().ifPresent(opponentName -> {
            CurrentTempUtil.currentProgress.setOpponetName(opponentName);
        });

        CurrentTempUtil.currentProgress.toString();
        buttons = Arrays.asList(
            button_0x0, button_0x1, button_0x2, button_0x3, button_0x4, button_0x5,
            button_1x0, button_1x1, button_1x2, button_1x3, button_1x4, button_1x5,
            button_2x0, button_2x1, button_2x2, button_2x3, button_2x4, button_2x5,
            button_3x0, button_3x1, button_3x2, button_3x3, button_3x4, button_3x5,
            button_4x0, button_4x1, button_4x2, button_4x3, button_4x4, button_4x5,
            button_5x0, button_5x1, button_5x2, button_5x3, button_5x4, button_5x5
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
    }

    private void setPlayerSymbol(Button button) {
        if (playerTurn % 2 == 0) {
            button.setText("X");
        } else {
            button.setText("O");
        }
        playerTurn++;
    }

    private void checkIfGameIsOver() {
        String[][] board = new String[6][6];
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                Button btn = buttons.get(r * 6 + c);
                board[r][c] = btn.getText();
            }
        }

        String winner = null;

        // Check rows, columns, diagonals for 4 in a row
        for (int r = 0; r < 6; r++) {
            for (int c = 0; c <= 2; c++) {
                String s = board[r][c];
                if (!s.isEmpty() && s.equals(board[r][c+1]) && s.equals(board[r][c+2]) && s.equals(board[r][c+3])) {
                    winner = s;
                }
            }
        }

        for (int c = 0; c < 6; c++) {
            for (int r = 0; r <= 2; r++) {
                String s = board[r][c];
                if (!s.isEmpty() && s.equals(board[r+1][c]) && s.equals(board[r+2][c]) && s.equals(board[r+3][c])) {
                    winner = s;
                }
            }
        }

        for (int r = 0; r <= 2; r++) {
            for (int c = 0; c <= 2; c++) {
                String s = board[r][c];
                if (!s.isEmpty() && s.equals(board[r+1][c+1]) && s.equals(board[r+2][c+2]) && s.equals(board[r+3][c+3])) {
                    winner = s;
                }
            }
        }

        for (int r = 0; r <= 2; r++) {
            for (int c = 3; c <= 5; c++) {
                String s = board[r][c];
                if (!s.isEmpty() && s.equals(board[r+1][c-1]) && s.equals(board[r+2][c-2]) && s.equals(board[r+3][c-3])) {
                    winner = s;
                }
            }
        }

        if (winner != null) {
            goToResultScene("WON");
        } else if (buttons.stream().allMatch(b -> !b.getText().isEmpty())) {
            goToResultScene("DRAW");
        }
    }

    @FXML
    void button_concede_on_action(ActionEvent event) {
        goToResultScene("CONCEDE");
    }

    @FXML
    void button_redo_on_action(ActionEvent event) {
        buttons.forEach(b -> {
            b.setText("");
            b.setDisable(false);
        });
        playerTurn = 0;
    }

    @FXML
    void button_pause_on_action(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pause.fxml"));
            Scene scene = new Scene(loader.load());

            PauseController controller = loader.getController();
            controller.setPreviousScene("/fxml/PVP6x6.fxml");

            Stage stage = (Stage) button_0x0.getScene().getWindow();
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
            controller.setFromScene("/fxml/PVP6x6.fxml"); // Tell ResultController where we came from

            Stage stage = (Stage) button_0x0.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

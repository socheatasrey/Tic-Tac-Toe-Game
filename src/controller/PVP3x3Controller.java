package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

        // Rows, Columns, Diagonals
        for (int i = 0; i < 3; i++) {
            // Rows
            if (!board[i][0].isEmpty() &&
                board[i][0].equals(board[i][1]) &&
                board[i][1].equals(board[i][2]))
                winner = board[i][0];

            // Columns
            if (!board[0][i].isEmpty() &&
                board[0][i].equals(board[1][i]) &&
                board[1][i].equals(board[2][i]))
                winner = board[0][i];
        }

        // Diagonals
        if (!board[0][0].isEmpty() &&
            board[0][0].equals(board[1][1]) &&
            board[1][1].equals(board[2][2]))
            winner = board[0][0];

        if (!board[0][2].isEmpty() &&
            board[0][2].equals(board[1][1]) &&
            board[1][1].equals(board[2][0]))
            winner = board[0][2];

        if (winner != null) {
            winnerText.setText(winner + " won!");
            disableBoard();
        } else if (buttons.stream().allMatch(b -> !b.getText().isEmpty())) {
            winnerText.setText("Draw!");
        }
    }

    private void disableBoard() {
        buttons.forEach(b -> b.setDisable(true));
    }

    @FXML
    void button_redo_on_action(ActionEvent event) {
        buttons.forEach(b -> {
            b.setText("");
            b.setDisable(false);
        });
        playerTurn = 0;
        winnerText.setText("Tic-Tac-Toe");
    }

    @FXML
    void button_concrede_on_action(ActionEvent event) {
        loadScene("/fxml/Result.fxml");
    }

    @FXML
    void button_pause_on_action(ActionEvent event) {
        loadScene("/fxml/Pause.fxml");
    }

    private void loadScene(String fxmlFile) {
        try {
            Stage stage = (Stage) button_0x0.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxmlFile)));
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

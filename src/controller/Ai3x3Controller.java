package controller;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ai3x3Controller {

    @FXML private Button Concede_button, Pause_button, Redo_button;
    @FXML private Button button_play1, button_play2, button_play3;
    @FXML private Button button_play4, button_play5, button_play6;
    @FXML private Button button_play7, button_play8, button_play9;

    private Button[] buttons;
    private String[] board;
    private Random random = new Random();

    @FXML
    public void initialize() { 
        buttons = new Button[]{
            button_play1, button_play2, button_play3,
            button_play4, button_play5, button_play6,
            button_play7, button_play8, button_play9
        };
        board = new String[9];
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 9; i++) {
            board[i] = "";
            buttons[i].setText("");
            buttons[i].setDisable(false);
        }
    }

    private void handlePlayerMove(int index, Button button) {
        if (!board[index].isEmpty()) return;

        board[index] = "X";
        button.setText("X");
        button.setDisable(true);

        if (checkWin("X")) {
            goToResultScene("WIN");
            return;
        }

        if (isBoardFull()) {
            goToResultScene("DRAW");
            return;
        }

        aiMoveWithDelay();
    }

    private void aiMoveWithDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
        pause.setOnFinished(e -> runAiLogic());
        pause.play();
    }

    private void runAiLogic() {
        int move = -1;

        move = findWinningMove("O");
        if (move != -1) {
            placeMove(move, "O");
            if (checkWin("O")) {
                goToResultScene("LOSE");
            }
            return;
        }

        if (random.nextInt(100) < 60) {
            move = findWinningMove("X");
            if (move != -1) {
                placeMove(move, "O");
                return;
            }
        }

        if (random.nextInt(100) < 30) {
            move = getRandomAvailableMove();
            placeMove(move, "O");
        } else {
            if (board[4].isEmpty()) {
                move = 4;
            } else {
                int[] smartMoves = {0, 2, 6, 8, 1, 3, 5, 7};
                for (int i : smartMoves) {
                    if (board[i].isEmpty()) {
                        move = i;
                        break;
                    }
                }
            }

            if (move != -1) placeMove(move, "O");
        }

        if (checkWin("O")) {
            goToResultScene("LOSE");
        } else if (isBoardFull()) {
            goToResultScene("DRAW");
        }
    }

    private int findWinningMove(String symbol) {
        for (int i = 0; i < 9; i++) {
            if (board[i].isEmpty()) {
                board[i] = symbol;
                boolean win = checkWin(symbol);
                board[i] = "";
                if (win) return i;
            }
        }
        return -1;
    }

    private int getRandomAvailableMove() {
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            if (board[i].isEmpty()) available.add(i);
        }
        return available.get(random.nextInt(available.size()));
    }

    private void placeMove(int index, String symbol) {
        board[index] = symbol;
        buttons[index].setText(symbol);
        buttons[index].setDisable(true);
    }

    private boolean checkWin(String symbol) {
        int[][] wins = {
            {0,1,2}, {3,4,5}, {6,7,8},
            {0,3,6}, {1,4,7}, {2,5,8},
            {0,4,8}, {2,4,6}
        };
        for (int[] wc : wins) {
            if (symbol.equals(board[wc[0]]) &&
                symbol.equals(board[wc[1]]) &&
                symbol.equals(board[wc[2]])) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (String s : board) {
            if (s.isEmpty()) return false;
        }
        return true;
    }


    @FXML void button_play1_action(ActionEvent e) { handlePlayerMove(0, button_play1); }
    @FXML void button_play2_action(ActionEvent e) { handlePlayerMove(1, button_play2); }
    @FXML void button_play3_action(ActionEvent e) { handlePlayerMove(2, button_play3); }
    @FXML void button_play4_action(ActionEvent e) { handlePlayerMove(3, button_play4); }
    @FXML void button_play5_action(ActionEvent e) { handlePlayerMove(4, button_play5); }
    @FXML void button_play6_action(ActionEvent e) { handlePlayerMove(5, button_play6); }
    @FXML void button_play7_action(ActionEvent e) { handlePlayerMove(6, button_play7); }
    @FXML void button_play8_action(ActionEvent e) { handlePlayerMove(7, button_play8); }
    @FXML void button_play9_action(ActionEvent e) { handlePlayerMove(8, button_play9); }

    @FXML void Concede_button_action(ActionEvent event) {
        goToResultScene("CONCEDE");
    }

    @FXML void bttn_pause_action(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Pause.fxml"));
            Scene scene = new Scene(loader.load());

            PauseController controller = loader.getController();
            controller.setPreviousScene("/fxml/Ai3x3.fxml");

            Stage stage = (Stage) Concede_button.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML void Redo_button_action(ActionEvent event) {
        resetBoard();
    }

    private void goToResultScene(String resultMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Result.fxml"));
            Parent root = loader.load();

            ResultController controller = loader.getController();
            controller.setResultText(resultMessage);
            controller.setFromScene("/fxml/Ai3x3.fxml");

            Stage stage = (Stage) button_play1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

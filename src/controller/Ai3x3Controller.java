package controller;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
            System.out.println("You win!");
            disableAllButtons();
            return;
        }

        if (isBoardFull()) {
            System.out.println("It's a draw!");
            return;
        }

        aiMoveWithDelay(); // NEW: AI move with delay
    }

    private void aiMoveWithDelay() {
        PauseTransition pause = new PauseTransition(Duration.seconds(0.6));
        pause.setOnFinished(e -> runAiLogic());
        pause.play();
    }

    private void runAiLogic() {
        int move = -1;

        // 1. Try to win
        move = findWinningMove("O");
        if (move != -1) {
            placeMove(move, "O");
            if (checkWin("O")) {
                System.out.println("AI wins!");
                disableAllButtons();
            }
            return;
        }

        // 2. Block player (60% of the time)
        if (random.nextInt(100) < 60) {
            move = findWinningMove("X");
            if (move != -1) {
                placeMove(move, "O");
                return;
            }
        }

        // 3. Random mistake (30% chance)
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
            System.out.println("AI wins!");
            disableAllButtons();
        } else if (isBoardFull()) {
            System.out.println("It's a draw!");
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

    private void disableAllButtons() {
        for (Button b : buttons) {
            b.setDisable(true);
        }
    }

    // Cell actions
    @FXML void button_play1_action(ActionEvent e) { handlePlayerMove(0, button_play1); }
    @FXML void button_play2_action(ActionEvent e) { handlePlayerMove(1, button_play2); }
    @FXML void button_play3_action(ActionEvent e) { handlePlayerMove(2, button_play3); }
    @FXML void button_play4_action(ActionEvent e) { handlePlayerMove(3, button_play4); }
    @FXML void button_play5_action(ActionEvent e) { handlePlayerMove(4, button_play5); }
    @FXML void button_play6_action(ActionEvent e) { handlePlayerMove(5, button_play6); }
    @FXML void button_play7_action(ActionEvent e) { handlePlayerMove(6, button_play7); }
    @FXML void button_play8_action(ActionEvent e) { handlePlayerMove(7, button_play8); }
    @FXML void button_play9_action(ActionEvent e) { handlePlayerMove(8, button_play9); }

    // Game controls
    @FXML void Concede_button_action(ActionEvent event) {
        System.out.println("You conceded.");
        disableAllButtons();
    }

    @FXML void Pause_button_action(ActionEvent event) {
        System.out.println("Game paused.");
    }

    @FXML void Redo_button_action(ActionEvent event) {
        System.out.println("Game restarted.");
        resetBoard();
    }
}

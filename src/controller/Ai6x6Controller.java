// Balanced AI with strategic play and small chance to err
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

import java.util.*;

public class Ai6x6Controller {

    @FXML private Button Concede_button, Pause_button, Redo_button;
    @FXML private Button button_play1, button_play2, button_play3, button_play4, button_play5, button_play6,
            button_play7, button_play8, button_play9, button_play10, button_play11, button_play12,
            button_play13, button_play14, button_play15, button_play16, button_play17, button_play18,
            button_play19, button_play20, button_play21, button_play22, button_play23, button_play24,
            button_play25, button_play26, button_play27, button_play28, button_play29, button_play30,
            button_play31, button_play32, button_play33, button_play34, button_play35, button_play36;

    private Button[] buttons;
    private String[] board;
    private Random random = new Random();

    @FXML
    public void initialize() {
        buttons = new Button[]{
                button_play1, button_play2, button_play3, button_play4, button_play5, button_play6,
                button_play7, button_play8, button_play9, button_play10, button_play11, button_play12,
                button_play13, button_play14, button_play15, button_play16, button_play17, button_play18,
                button_play19, button_play20, button_play21, button_play22, button_play23, button_play24,
                button_play25, button_play26, button_play27, button_play28, button_play29, button_play30,
                button_play31, button_play32, button_play33, button_play34, button_play35, button_play36
        };
        board = new String[36];
        resetBoard();
    }

    private void resetBoard() {
        for (int i = 0; i < 36; i++) {
            board[i] = "";
            buttons[i].setText("");
            buttons[i].setDisable(false);
        }
    }

    private void handlePlayerMove(int index) {
        if (!board[index].isEmpty()) return;

        board[index] = "X";
        buttons[index].setText("X");
        buttons[index].setDisable(true);

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
        PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
        pause.setOnFinished(e -> runBalancedAiLogic());
        pause.play();
    }

    private void runBalancedAiLogic() {
        boolean makeMistake = random.nextDouble() < 0.2;

        List<Integer> emptyCells = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            if (board[i].isEmpty()) emptyCells.add(i);
        }

        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i : emptyCells) {
            board[i] = "O";
            int score = evaluateBoard("O") - evaluateBoard("X");
            board[i] = "";

            if (score > bestScore || (score == bestScore && random.nextBoolean())) {
                bestScore = score;
                bestMove = i;
            }
        }

        if (makeMistake && emptyCells.size() > 1) {
            emptyCells.remove((Integer) bestMove);
            bestMove = emptyCells.get(random.nextInt(emptyCells.size()));
        }

        if (bestMove != -1) {
            applyAIMove(bestMove);
        }
    }

    private int evaluateBoard(String symbol) {
        int score = 0;
        int[][] directions = {{0,1}, {1,0}, {1,1}, {-1,1}};

        for (int r = 0; r < 6; r++) {
            for (int c = 0; c < 6; c++) {
                for (int[] dir : directions) {
                    int count = 0;
                    int blocked = 0;
                    for (int i = 0; i < 4; i++) {
                        int nr = r + i * dir[0];
                        int nc = c + i * dir[1];
                        if (nr < 0 || nr >= 6 || nc < 0 || nc >= 6) {
                            blocked++;
                            break;
                        }
                        String cell = board[nr * 6 + nc];
                        if (cell.equals(symbol)) count++;
                        else if (!cell.isEmpty()) {
                            blocked++;
                            break;
                        }
                    }
                    if (count == 4) return 10000;
                    if (count == 3 && blocked == 0) score += 500;
                    else if (count == 2 && blocked == 0) score += 100;
                    else if (count == 1 && blocked == 0) score += 30;
                }
            }
        }
        return score;
    }

    private void applyAIMove(int index) {
        board[index] = "O";
        buttons[index].setText("O");
        buttons[index].setDisable(true);

        if (checkWin("O")) {
            goToResultScene("LOSE");
        } else if (isBoardFull()) {
            goToResultScene("DRAW");
        }
    }

    private boolean checkWin(String symbol) {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col <= 2; col++) {
                if (match(symbol, row, col, 0, 1)) return true;
            }
        }
        for (int col = 0; col < 6; col++) {
            for (int row = 0; row <= 2; row++) {
                if (match(symbol, row, col, 1, 0)) return true;
            }
        }
        for (int row = 0; row <= 2; row++) {
            for (int col = 0; col <= 2; col++) {
                if (match(symbol, row, col, 1, 1)) return true;
            }
        }
        for (int row = 3; row < 6; row++) {
            for (int col = 0; col <= 2; col++) {
                if (match(symbol, row, col, -1, 1)) return true;
            }
        }
        return false;
    }

    private boolean match(String symbol, int row, int col, int dRow, int dCol) {
        for (int i = 0; i < 4; i++) {
            int r = row + i * dRow;
            int c = col + i * dCol;
            if (r < 0 || r >= 6 || c < 0 || c >= 6 || !symbol.equals(board[r * 6 + c])) {
                return false;
            }
        }
        return true;
    }

    private boolean isBoardFull() {
        for (String s : board) {
            if (s.isEmpty()) return false;
        }
        return true;
    }


    @FXML void button_play1_action(ActionEvent e) { handlePlayerMove(0); }
    @FXML void button_play2_action(ActionEvent e) { handlePlayerMove(1); }
    @FXML void button_play3_action(ActionEvent e) { handlePlayerMove(2); }
    @FXML void button_play4_action(ActionEvent e) { handlePlayerMove(3); }
    @FXML void button_play5_action(ActionEvent e) { handlePlayerMove(4); }
    @FXML void button_play6_action(ActionEvent e) { handlePlayerMove(5); }
    @FXML void button_play7_action(ActionEvent e) { handlePlayerMove(6); }
    @FXML void button_play8_action(ActionEvent e) { handlePlayerMove(7); }
    @FXML void button_play9_action(ActionEvent e) { handlePlayerMove(8); }
    @FXML void button_play10_action(ActionEvent e) { handlePlayerMove(9); }
    @FXML void button_play11_action(ActionEvent e) { handlePlayerMove(10); }
    @FXML void button_play12_action(ActionEvent e) { handlePlayerMove(11); }
    @FXML void button_play13_action(ActionEvent e) { handlePlayerMove(12); }
    @FXML void button_play14_action(ActionEvent e) { handlePlayerMove(13); }
    @FXML void button_play15_action(ActionEvent e) { handlePlayerMove(14); }
    @FXML void button_play16_action(ActionEvent e) { handlePlayerMove(15); }
    @FXML void button_play17_action(ActionEvent e) { handlePlayerMove(16); }
    @FXML void button_play18_action(ActionEvent e) { handlePlayerMove(17); }
    @FXML void button_play19_action(ActionEvent e) { handlePlayerMove(18); }
    @FXML void button_play20_action(ActionEvent e) { handlePlayerMove(19); }
    @FXML void button_play21_action(ActionEvent e) { handlePlayerMove(20); }
    @FXML void button_play22_action(ActionEvent e) { handlePlayerMove(21); }
    @FXML void button_play23_action(ActionEvent e) { handlePlayerMove(22); }
    @FXML void button_play24_action(ActionEvent e) { handlePlayerMove(23); }
    @FXML void button_play25_action(ActionEvent e) { handlePlayerMove(24); }
    @FXML void button_play26_action(ActionEvent e) { handlePlayerMove(25); }
    @FXML void button_play27_action(ActionEvent e) { handlePlayerMove(26); }
    @FXML void button_play28_action(ActionEvent e) { handlePlayerMove(27); }
    @FXML void button_play29_action(ActionEvent e) { handlePlayerMove(28); }
    @FXML void button_play30_action(ActionEvent e) { handlePlayerMove(29); }
    @FXML void button_play31_action(ActionEvent e) { handlePlayerMove(30); }
    @FXML void button_play32_action(ActionEvent e) { handlePlayerMove(31); }
    @FXML void button_play33_action(ActionEvent e) { handlePlayerMove(32); }
    @FXML void button_play34_action(ActionEvent e) { handlePlayerMove(33); }
    @FXML void button_play35_action(ActionEvent e) { handlePlayerMove(34); }
    @FXML void button_play36_action(ActionEvent e) { handlePlayerMove(35); }

    @FXML void Concede_button_action(ActionEvent e) { goToResultScene("CONCEDE!"); }
    @FXML void Pause_button_action(ActionEvent event) {
        try {
            Stage stage = (Stage) button_play1.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Pause.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML void Redo_button_action(ActionEvent e) { resetBoard(); }

    private void goToResultScene(String resultMessage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Result.fxml"));
            Parent root = loader.load();

            ResultController controller = loader.getController();
            controller.setResultText(resultMessage);
            controller.setFromScene("/fxml/Ai6x6.fxml");

            Stage stage = (Stage) button_play1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

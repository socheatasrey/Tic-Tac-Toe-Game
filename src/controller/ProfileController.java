package controller;

import dao.UserDAO;
import dao.GameHistoryDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import model.GameHistoryEntry;
import model.User;

import java.io.File;
import java.net.URL;
import java.util.*;

public class ProfileController implements Initializable {

    @FXML private Label lbl_name, lbl_ID, lbl_nationality;
    @FXML private ImageView edit_profile;
    @FXML private ScrollPane scrollpane_history;
    @FXML private VBox historyContainer;

    private User currentUser; // ← you should pass this from login/register
    private final UserDAO userDAO = new UserDAO();
    private final GameHistoryDAO historyDAO = new GameHistoryDAO();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUser = userDAO.getUserById(1);  

        displayUserInfo();
        loadHistory();
        scrollpane_history.setContent(historyContainer);
    }

    private void displayUserInfo() {
        lbl_name.setText(currentUser.getUsername());
        lbl_ID.setText(String.valueOf(currentUser.getId()));
        lbl_nationality.setText(currentUser.getNationality());
    }

    @FXML
    void editName() {
        TextInputDialog dialog = new TextInputDialog(currentUser.getUsername());
        dialog.setHeaderText("Edit Name");
        dialog.showAndWait().ifPresent(newName -> {
            currentUser.setUsername(newName);
            userDAO.updateUsername(currentUser.getId(), newName);
            lbl_name.setText(newName);
        });
    }

    @FXML
    void editNationality() {
        List<String> countries = Arrays.asList("Cambodia", "Thailand", "Vietnam", "USA", "France");
        ChoiceDialog<String> dialog = new ChoiceDialog<>(currentUser.getNationality(), countries);
        dialog.setHeaderText("Edit Nationality");
        dialog.showAndWait().ifPresent(newNat -> {
            currentUser.setNationality(newNat);
            userDAO.updateNationality(currentUser.getId(), newNat);
            lbl_nationality.setText(newNat);
        });
    }

    @FXML
    void changeProfilePicture(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(lbl_name.getScene().getWindow());
        if (file != null) {
            String path = file.toURI().toString();
            edit_profile.setImage(new Image(path));
            currentUser.setPhotoPath(path);
            userDAO.updatePhotoPath(currentUser.getId(), path);
        }
    }

    private void loadHistory() {
        historyContainer = new VBox(10);
        historyContainer.setStyle("-fx-padding: 10;");
        List<GameHistoryEntry> historyList = historyDAO.getHistoryByUserId(currentUser.getId());

        for (GameHistoryEntry entry : historyList) {
            HBox row = new HBox(10);
            row.setStyle("-fx-background-color: #ffffffcc; -fx-background-radius: 10; -fx-padding: 10;");
            row.setPrefWidth(300);
            row.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label opponent = new Label("vs " + entry.getOpponentName());
            Label type = new Label(entry.getGameType());
            Label result = new Label(entry.getGameStatus());
            Label time = new Label(entry.getTimestamp().toString());

            opponent.setStyle("-fx-font-weight: bold;");
            result.setStyle("-fx-text-fill: " + (entry.getGameStatus().equals("win") ? "green" : "red") + ";");

            row.getChildren().addAll(opponent, type, result, time);
            historyContainer.getChildren().add(row);
        }
    }
}

package controller;

import dao.UserDAO;
import dao.GameHistoryDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.GameHistoryEntry;
import model.User;
import util.CurrentTempUtil;
import util.SoundUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
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
        currentUser = userDAO.getUserById(CurrentTempUtil.currentUser.getId());  

        displayUserInfo();
        loadHistory();
        scrollpane_history.setContent(historyContainer);
    }

    private void displayUserInfo() {
        lbl_name.setText(currentUser.getUsername());
        lbl_ID.setText(String.valueOf(currentUser.getId()));
        lbl_nationality.setText(currentUser.getNationality());
        edit_profile.setImage(new Image(CurrentTempUtil.getResourcePath(CurrentTempUtil.currentUser.getPhotoPath())));

    }

    @FXML
    void editName(ActionEvent event) {
        SoundUtil.clik();
        TextInputDialog dialog = new TextInputDialog(currentUser.getUsername());
        dialog.setHeaderText("Edit Name");
        dialog.showAndWait().ifPresent(newName -> {
            currentUser.setUsername(newName);
            userDAO.updateUsername(currentUser.getId(), newName);
            lbl_name.setText(newName);
        });
    }

    @FXML
    void editNationality(ActionEvent event) {
        SoundUtil.clik();
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
        SoundUtil.clik();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Profile Picture");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(lbl_name.getScene().getWindow());
        if (file != null) {
            try {
                String username = CurrentTempUtil.currentUser.getUsername();
                String extension = getFileExtension(file.getName());
                if (extension == null) {
                    showAlert("Invalid image file.");
                    return;
                }

                String newFilename = "src/resources/images/profile_imgs/" + username + "_photo." + extension;
                
                File destFile = new File(newFilename);
                
                // Copy selected file to project directory
                Files.copy(file.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Update image path (relative for GameSettings)
                String resourcePath = CurrentTempUtil.getResourcePath(newFilename);

                // Set user info
                CurrentTempUtil.currentUser.setPhotoPath(newFilename);
                edit_profile.setImage(new Image(resourcePath));

                // Save to DB
                int currentId = CurrentTempUtil.currentUser.getId();
                UserDAO.updatePhotoPath(currentId, newFilename);
            } catch (IOException e) {
                showAlert("Failed to upload image: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < filename.length() - 1) {
            return filename.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Input");
        alert.setContentText(msg);
        alert.showAndWait();
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
    @FXML
    void bttn_to_go_achivement_action(ActionEvent event){
        try {
            Stage stage = (Stage) lbl_ID.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Achievement.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void bttn_back_to_home(ActionEvent event){
        try {
            Stage stage = (Stage) lbl_ID.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Home.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

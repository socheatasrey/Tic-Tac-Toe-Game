package controller;

import dao.UserDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import util.*;

public class RegisterController {

    @FXML
    private TextField Textfield_password_register;

    @FXML
    private TextField Textfield_username_register;

    @FXML
    private TextField Textfield_verify_register;

    @FXML
    private Button bttn_go_to_sign_in;

    @FXML
    private Button bttn_register;

    @FXML
    private CheckBox checkbox_show_password;

    @FXML
    private Label lbl_match_password;

    @FXML
    private Label lbl_register;

    @FXML
    private Label lbl_register_status;

    @FXML
    private PasswordField passwordfield_register;

    @FXML
    private PasswordField passwordfield_verify_register;

     @FXML
    public void initialize() {
        // Listen to password field changes
        passwordfield_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());
        Textfield_password_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());

        // Listen to verify field changes
        passwordfield_verify_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());
        Textfield_verify_register.textProperty().addListener((obs, oldText, newText) -> checkPasswordsMatch());
    }
    @FXML
    void bttn_go_to_sign_in_action(ActionEvent event) {
        try {
            Stage stage = (Stage) lbl_register.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void bttn_register_action(ActionEvent event) {
        String username = Textfield_username_register.getText().trim();
        String password = checkbox_show_password.isSelected() ?
                        Textfield_password_register.getText() :
                        passwordfield_register.getText();
        String confirmPassword = checkbox_show_password.isSelected() ?
                                Textfield_verify_register.getText() :
                                passwordfield_verify_register.getText();

        // Create user object for validation
        User user = new User(username, password, "/resources/"); // No photo yet

        String validationError = user.validateRegistration(confirmPassword);
        if (validationError != null) {
            lbl_register_status.setText(validationError);
            lbl_register_status.setStyle("-fx-text-fill: red;");
            return;
        }

        UserDAO userDAO = new UserDAO();

        // Check for duplicate username
        if (userDAO.usernameExists(username)) {
            lbl_register_status.setText("Username already exists.");
            lbl_register_status.setStyle("-fx-text-fill: red;");
            return;
        }


        // Save user
        boolean success = userDAO.registerUser(user);

        if (success) {
            lbl_register_status.setText("Registration successful!");
            lbl_register_status.setStyle("-fx-text-fill: green;");
            
            SoundUtil.loopMusic("Music_Background.wav");

            try {
                Stage stage = (Stage) lbl_register.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
                Scene scene = loader.load();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            lbl_register_status.setText("Failed to register. Try again.");
            lbl_register_status.setStyle("-fx-text-fill: red;");
        }
    }


    @FXML
    void checkbox_show_password_action(ActionEvent event) {
        if (checkbox_show_password.isSelected()) {
            Textfield_password_register.setText(passwordfield_register.getText());
            Textfield_password_register.setVisible(true);
            passwordfield_register.setVisible(false);

            Textfield_verify_register.setText(passwordfield_verify_register.getText());
            Textfield_verify_register.setVisible(true);
            passwordfield_verify_register.setVisible(false);
        } else {
            passwordfield_register.setText(passwordfield_register.getText());
            passwordfield_register.setVisible(true);
            Textfield_password_register.setVisible(false);

            passwordfield_verify_register.setText(Textfield_verify_register.getText());
            passwordfield_verify_register.setVisible(true);
            Textfield_verify_register.setVisible(false);
        }

    }
    private void checkPasswordsMatch() {
        String password = checkbox_show_password.isSelected()
            ? Textfield_password_register.getText()
            : passwordfield_register.getText();

        String verifyPassword = checkbox_show_password.isSelected()
            ? Textfield_verify_register.getText()
            : passwordfield_verify_register.getText();

        if (verifyPassword.isEmpty()) {
            lbl_register_status.setText("");
        } else if (password.equals(verifyPassword)) {
            lbl_match_password.setText("Passwords match");
            lbl_match_password.setStyle("-fx-text-fill: green;");
        } else {
            lbl_match_password.setText("Passwords do not match");
            lbl_match_password.setStyle("-fx-text-fill: red;");
        }
    }


}

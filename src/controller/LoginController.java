package controller;

import java.util.Optional;

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
import util.UserSession;

public class LoginController {

    @FXML
    private TextField Textfield_password_login;

    @FXML
    private TextField Textfield_username_login;

    @FXML
    private Button bttn_go_to_register;

    @FXML
    private Button bttn_login;

    @FXML
    private CheckBox checkbox_show_password;

    @FXML
    private Label lbl_login_status;

    @FXML
    private Label lbl_login;

    @FXML
    private PasswordField passwordfield_login;

    @FXML
    void bttn_go_to_register_action(ActionEvent event) {
        try {
            Stage stage = (Stage) lbl_login.getScene().getWindow();
            Scene scene = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @FXML
    void bttn_login_action(ActionEvent event) {
        String username = Textfield_username_login.getText();
        String password = checkbox_show_password.isSelected() ?
                        Textfield_password_login.getText() :
                        passwordfield_login.getText();

        if (username.isEmpty() || password.isEmpty()) {
            lbl_login_status.setText("Please enter username and password.");
            return;
        }

    
        UserDAO userDAO = new UserDAO();

        Optional<User> user = userDAO.loginUser(username, password); 

        if (user.isPresent()) {
            UserSession.init(user.get()); // ✅ unwraps Optional<User>
            lbl_login_status.setText("Login successful!");

            try {
                Stage stage = (Stage) lbl_login.getScene().getWindow();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Home.fxml"));
                Scene scene = loader.load();
                stage.setScene(scene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
                lbl_login_status.setText("Failed to load home.");
            }
        } else {
            lbl_login_status.setText("Invalid username or password.");
        }

    }


    @FXML
    void checkbox_show_password_action(ActionEvent event) {
         if (checkbox_show_password.isSelected()) {
            Textfield_password_login.setText(passwordfield_login.getText());
            Textfield_password_login.setVisible(true);
            passwordfield_login.setVisible(false);
        } else {
            passwordfield_login.setText(Textfield_password_login.getText());
            passwordfield_login.setVisible(true);
            Textfield_password_login.setVisible(false);
        }
    }

}

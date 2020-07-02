package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.Bounce;
import animatefx.animation.FadeIn;
import animatefx.animation.Swing;
import gui.animations.Shaking;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.GlobalUser;
import user.User;
import user.UserCreator;
import utils.I18N;

public class SignIn {

    @FXML
    private TextField login;

    @FXML
    private PasswordField password;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private Pane langPane;

    @FXML
    void initialize() {
        signInButton.textProperty().bind(I18N.createStringBinding("sign_in"));
        signUpButton.textProperty().bind(I18N.createStringBinding("sign_up"));
        login.promptTextProperty().bind(I18N.createStringBinding("login"));
        password.promptTextProperty().bind(I18N.createStringBinding("password"));
        signUpButton.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/signUp.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            new FadeIn(root).play();
            Stage stage = (Stage) signUpButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        });

        signInButton.setOnAction(event -> {
            String loginText = login.getText().trim();
            String passwordText = password.getText().trim();

            long userId = 0;
            boolean flag = false;
            if (!loginText.equals("") && !passwordText.equals("")) {
                String answer = UserCreator.login(loginText, passwordText);
                if (!answer.equals("Wrong login or password")) {
                    flag = true;
                    userId = Long.parseLong(answer);
                }
            }
            if (flag) {
                // Create and give user
                User user = new User();
                user.setRegistered(true);
                user.setLogin(loginText);
                user.setPassword(passwordText);
                user.setId(userId);
                GlobalUser.setUser(user);

                System.out.println("Come in");
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/gui/appWindow.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                new FadeIn(root).play();
                Stage stage = (Stage) signUpButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } else {
                Shaking loginAnimation = new Shaking(login);
                Shaking passwordAnimation = new Shaking(password);
                loginAnimation.playAnimation();
                passwordAnimation.playAnimation();
            }
        });

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gui/langBox.fxml"));
            Parent comboRoot = loader.load();

            langPane.getChildren().add(comboRoot);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

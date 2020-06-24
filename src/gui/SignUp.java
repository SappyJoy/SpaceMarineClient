package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import animatefx.animation.*;
import com.sun.xml.internal.bind.v2.TODO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import user.UserCreator;
import utils.I18N;

public class SignUp {

    @FXML
    private TextField login;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField email;

    @FXML
    private Button signInButton;

    @FXML
    private Pane langPane;

    @FXML
    void initialize() {
        signInButton.textProperty().bind(I18N.createStringBinding("sign_in"));
        signUpButton.textProperty().bind(I18N.createStringBinding("sign_up"));
        login.promptTextProperty().bind(I18N.createStringBinding("login"));
        signUpButton.setOnAction(event -> {
            String loginText = login.getText().trim();
            String emailText = email.getText().trim();

            boolean flag = false;
            if (!loginText.equals("") && !emailText.equals("")) {
                flag = UserCreator.register(loginText, emailText);
            }

            if (flag) {
                /*
                    TODO this code doesn't word properly
                    FIXME It shows now a black window
                     - I need to make switch between scenes
                     - After I need to check my registration
                     - Fix close operation
                     - And eventually make start to make mane window
                 */
                returnToSignInScene();

                
            }
        });

        signInButton.setOnAction(event -> {
            returnToSignInScene();
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

    private void returnToSignInScene() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/gui/signIn.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        new FadeIn(root).play();
        Stage stage = (Stage) signInButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}

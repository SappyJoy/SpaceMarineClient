package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.I18N;

public class RemoveKeyCommand implements CommandController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField key;

    @FXML
    private Label keyLabel;

    @FXML
    void initialize() {
        keyLabel.textProperty().bind(I18N.createStringBinding("key"));
    }

    @Override
    public String getInformation() {
        return " " + key.getText();
    }
}

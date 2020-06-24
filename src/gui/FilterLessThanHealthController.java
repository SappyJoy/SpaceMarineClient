package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.I18N;

public class FilterLessThanHealthController implements CommandController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField health;

    @FXML
    private Label healthLabel;

    @FXML
    void initialize() {
        healthLabel.textProperty().bind(I18N.createStringBinding("health"));
    }

    @Override
    public String getInformation() {
        return " " + health.getText();
    }
}

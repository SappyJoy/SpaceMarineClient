package gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.I18N;

public class ExecuteScriptCommand implements CommandController {

    @FXML
    private TextField filename;

    @FXML
    private Label filenameLabel;

    @FXML
    void initialize() {
        filenameLabel.textProperty().bind(I18N.createStringBinding("filename"));
    }


    @Override
    public String getInformation() {
        return " " + filename.getText();
    }
}

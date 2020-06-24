package gui;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import utils.I18N;

public class LangBoxController {

    @FXML
    private ComboBox<String> langBox;

    ResourceBundle bundle = ResourceBundle.getBundle("res.GuiLablels");

    ObservableList<String> langList = FXCollections.observableArrayList(
            "English", "Русский", "Slovenský", "Svenka");

    @FXML
    void initialize() {
        langBox.setPromptText(I18N.get("lang"));
        langBox.setItems(langList);
        langBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            switch (newValue) {
                case "English": {
                    I18N.setLocale(new Locale("en", "UK"));
                    break;
                }
                case "Русский": {
                    I18N.setLocale(new Locale("ru"));
                    break;
                }
                case "Slovenský": {
                    I18N.setLocale(new Locale("sk"));
                    break;
                }
                case "Svenka": {
                    I18N.setLocale(new Locale("sv"));
                    break;
                }
            }
        });
    }
}

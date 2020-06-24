package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import utils.I18N;

public class CountByWeaponTypeController implements CommandController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> weapon;

    @FXML
    private Label weaponLabel;

    ObservableList<String> weaponList = FXCollections.observableArrayList(
            "PLASMA_GUN", "COMBI_PLASMA_GUN", "FLAMER", "INFERNO_PISTOL", "HEAVY_FLAMER");

    @FXML
    void initialize() {
        weapon.setItems(weaponList);
        weaponLabel.textProperty().bind(I18N.createStringBinding("weapon"));
    }

    @Override
    public String getInformation() {
        return " " + weapon.getValue();
    }
}

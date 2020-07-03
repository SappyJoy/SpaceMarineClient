package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.I18N;

public class RemoveFilterCommandController implements CommandController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField name;

    @FXML
    private TextField x;

    @FXML
    private TextField y;

    @FXML
    private TextField health;

    @FXML
    private TextField chapter;

    @FXML
    private TextField count;

    @FXML
    private TextField world;

    @FXML
    private ComboBox<String> loyal;

    @FXML
    private ComboBox<String> weapon;

    @FXML
    private ComboBox<String> meleeWeapon;

    @FXML
    private Label healthLabel;

    @FXML
    private Label loyalLabel;

    @FXML
    private Label weaponLabel;

    @FXML
    private Label meleeWeaponLabel;

    @FXML
    private Label chapteLabel;

    @FXML
    private Label countLabel;

    @FXML
    private Label worldLabel;

    @FXML
    private Label nameLabel;

    ObservableList<String> booleanList = FXCollections.observableArrayList("true", "false");
    ObservableList<String> weaponList = FXCollections.observableArrayList(
            "PLASMA_GUN", "COMBI_PLASMA_GUN", "FLAMER", "INFERNO_PISTOL", "HEAVY_FLAMER");
    ObservableList<String> meleeWeaponList = FXCollections.observableArrayList(
            "CHAIN_SWORD", "CHAIN_AXE", "POWER_BLADE");

    @FXML
    void initialize() {
        loyal.setItems(booleanList);
        weapon.setItems(weaponList);
        meleeWeapon.setItems(meleeWeaponList);
        nameLabel.textProperty().bind(I18N.createStringBinding("name"));
        healthLabel.textProperty().bind(I18N.createStringBinding("health"));
        loyalLabel.textProperty().bind(I18N.createStringBinding("loyal"));
        weaponLabel.textProperty().bind(I18N.createStringBinding("weapon"));
        meleeWeaponLabel.textProperty().bind(I18N.createStringBinding("melee_weapon"));
        chapteLabel.textProperty().bind(I18N.createStringBinding("chapter"));
        countLabel.textProperty().bind(I18N.createStringBinding("count"));
        worldLabel.textProperty().bind(I18N.createStringBinding("world"));
    }

    @Override
    public String getInformation() {
        return " " + name.getText() + " " + x.getText() + " " +  y.getText() + " " +
                health.getText() + " " + loyal.getValue() + " " + weapon.getValue() + " " + meleeWeapon.getValue() +
                " " + chapter.getText() + " " + count.getText() + " " + world.getText();
    }
}

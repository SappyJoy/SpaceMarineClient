package gui;

import client.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import utils.I18N;

import java.io.IOException;

public class Visual {

    @FXML
    private TextField id;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField name;

    @FXML
    private TextField x;

    @FXML
    private TextField y;

    @FXML
    private Label healthLabel;

    @FXML
    private TextField health;

    @FXML
    private Label loyalLabel;

    @FXML
    private ComboBox<String> loyal;

    @FXML
    private Label weaponLabel;

    @FXML
    private ComboBox<String> weapon;

    @FXML
    private Label meleeWeaponLabel;

    @FXML
    private ComboBox<String> meleeWeapon;

    @FXML
    private Label chapterLabel;

    @FXML
    private TextField chapter;

    @FXML
    private Label countLabel;

    @FXML
    private TextField count;

    @FXML
    private Label worldLabel;

    @FXML
    private TextField world;

    @FXML
    private TextArea console;

    @FXML
    private Label dateLabel;

    @FXML
    private TextField date;

    ObservableList<String> booleanList = FXCollections.observableArrayList("true", "false");
    ObservableList<String> weaponList = FXCollections.observableArrayList(
            "PLASMA_GUN", "COMBI_PLASMA_GUN", "FLAMER", "INFERNO_PISTOL", "HEAVY_FLAMER");
    ObservableList<String> meleeWeaponList = FXCollections.observableArrayList(
            "CHAIN_SWORD", "CHAIN_AXE", "POWER_BLADE");

    @FXML
    void initialize() {
        name.setOnAction(event -> {
            update();
        });
        x.setOnAction(event -> {
            update();
        });
        y.setOnAction(event -> {
            update();
        });
        health.setOnAction(event -> {
            update();
        });
        loyal.setOnMouseClicked(event -> {
            update();
        });
        weapon.setOnMouseClicked(event -> {
            update();
        });
        meleeWeapon.setOnMouseClicked(event -> {
            update();
        });
        chapter.setOnAction(event -> {
            update();
        });
        count.setOnAction(event -> {
            update();
        });
        world.setOnAction(event -> {
            update();
        });

        nameLabel.textProperty().bind(I18N.createStringBinding("name"));
        dateLabel.textProperty().bind(I18N.createStringBinding("time"));
        healthLabel.textProperty().bind(I18N.createStringBinding("health"));
        loyalLabel.textProperty().bind(I18N.createStringBinding("loyal"));
        weaponLabel.textProperty().bind(I18N.createStringBinding("weapon"));
        meleeWeaponLabel.textProperty().bind(I18N.createStringBinding("melee_weapon"));
        chapterLabel.textProperty().bind(I18N.createStringBinding("chapter"));
        countLabel.textProperty().bind(I18N.createStringBinding("count"));
        worldLabel.textProperty().bind(I18N.createStringBinding("world"));
        loyal.setItems(booleanList);
        weapon.setItems(weaponList);
        meleeWeapon.setItems(meleeWeaponList);
    }

    private void update() {
        String request = "update " + id.getText() + " " + name.getText() + " " +
                x.getText() + " " + y.getText() + " " + health.getText() + " " + loyal.getValue() + " " +
                weapon.getValue() + " " + meleeWeapon.getValue() + " " + chapter.getText() + " " +
                count.getText() + " " + world.getText();
        System.out.println(request);
        String answer = Client.getInstance().get(request);
        console.setText(answer);
    }

    public TextField getId() {
        return id;
    }

    public TextField getName() {
        return name;
    }

    public TextField getX() {
        return x;
    }

    public TextField getY() {
        return y;
    }

    public TextField getDate() {
        return date;
    }

    public TextField getHealth() {
        return health;
    }

    public ComboBox<String> getLoyal() {
        return loyal;
    }

    public ComboBox<String> getWeapon() {
        return weapon;
    }

    public ComboBox<String> getMeleeWeapon() {
        return meleeWeapon;
    }

    public TextField getChapter() {
        return chapter;
    }

    public TextField getCount() {
        return count;
    }

    public TextField getWorld() {
        return world;
    }
}

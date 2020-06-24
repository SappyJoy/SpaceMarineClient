package gui;

import client.Client;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    private TextField loyal;

    @FXML
    private Label weaponLabel;

    @FXML
    private TextField weapon;

    @FXML
    private Label meleeWeaponLabel;

    @FXML
    private TextField meleeWeapon;

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
        loyal.setOnAction(event -> {
            update();
        });
        weapon.setOnAction(event -> {
            update();
        });
        meleeWeapon.setOnAction(event -> {
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
        healthLabel.textProperty().bind(I18N.createStringBinding("health"));
        loyalLabel.textProperty().bind(I18N.createStringBinding("loyal"));
        weaponLabel.textProperty().bind(I18N.createStringBinding("weapon"));
        meleeWeaponLabel.textProperty().bind(I18N.createStringBinding("melee_weapon"));
        chapterLabel.textProperty().bind(I18N.createStringBinding("chapter"));
        countLabel.textProperty().bind(I18N.createStringBinding("count"));
        worldLabel.textProperty().bind(I18N.createStringBinding("world"));
    }

    private void update() {
        String request = "update " + id.getText() + " " + name.getText() + " " +
                x.getText() + " " + y.getText() + " " + health.getText() + " " + loyal.getText() + " " +
                weapon.getText() + " " + meleeWeapon.getText() + " " + chapter.getText() + " " +
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

    public TextField getHealth() {
        return health;
    }

    public TextField getLoyal() {
        return loyal;
    }

    public TextField getWeapon() {
        return weapon;
    }

    public TextField getMeleeWeapon() {
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

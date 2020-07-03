package gui.animations;


import gui.SpaceMarineAdapter;
import gui.Visual;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.List;

public class MouseGestures {

    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;
    HashMap<Node, SpaceMarineAdapter> map = new HashMap<>();
    Visual controller = null;

    public void makeClickable(Node node, SpaceMarineAdapter sm) {
        map.put(node, sm);
        node.setOnMouseClicked(circleOnMouseClickedEventHandler);
    }

    public void makeClickableAll(List<Node> nodes) {
        for (Node node : nodes) {
            node.setOnMouseClicked(circleOnMouseClickedEventHandler);
        }
    }

    EventHandler<MouseEvent> circleOnMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            SpaceMarineAdapter sm = map.get((Node)event.getSource());
            controller.getId().setText(String.valueOf(sm.getId()));
            controller.getName().setText(sm.getName());
            controller.getX().setText(String.valueOf(sm.getX()));
            controller.getY().setText(String.valueOf(sm.getY()));
            controller.getDate().setText(String.valueOf(sm.getCreationDateString()));
            controller.getHealth().setText(String.valueOf(sm.getHealth()));
            controller.getLoyal().setValue(String.valueOf(sm.isLoyal()));
            controller.getWeapon().setValue(sm.getWeapon());
            controller.getMeleeWeapon().setValue(sm.getMeleeWeapon());
            controller.getChapter().setText(sm.getChapter());
            controller.getCount().setText(String.valueOf(sm.getCount()));
            controller.getWorld().setText(sm.getWorld());
        }
    };

    public void setController(Visual controller) {
        this.controller = controller;
    }
}

package gui.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class CircleTransition {

    TranslateTransition transition;

    public CircleTransition(Circle node) {
        transition = new TranslateTransition();
        transition.setNode(node);
        transition.setFromY(730 - node.getCenterY());
        transition.setFromX(90 - node.getCenterX());
        transition.setToX(0);
        transition.setToY(0);
        transition.setDuration(Duration.seconds(1 + Math.random() * 2));
    }

    public void playAnimation() {
        transition.play();
    }
}

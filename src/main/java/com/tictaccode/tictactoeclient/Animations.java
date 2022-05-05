package com.tictaccode.tictactoeclient;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Animations {
    public static Timeline getFadeInTimeline(Circle fade, double waitTime) {
        double width = fade.getScene().getWidth();
        double height = fade.getScene().getHeight();
        fade.setCenterX(width / 2);
        fade.setCenterY(height / 2);
        fade.setRadius(Math.max(width, height) / 2 + 250);
    
        return new Timeline(
                new KeyFrame(Duration.millis(waitTime),
                        new KeyValue(fade.radiusProperty(), fade.getRadius())),
                new KeyFrame(Duration.millis(waitTime + 500),
                        new KeyValue(fade.radiusProperty(), 0))
        );
    }
    
    public static Timeline getFadeOutTimeline(Circle fade, double waitTime) {
        double width = fade.getScene().getWidth();
        double height = fade.getScene().getHeight();
    
        fade.setCenterX(width / 2);
        fade.setCenterY(height / 2);
        fade.setRadius(0.1);
    
        return new Timeline(
                new KeyFrame(Duration.millis(waitTime),
                        new KeyValue(fade.radiusProperty(), 0.1)),
                new KeyFrame(Duration.millis(waitTime + 500),
                        new KeyValue(fade.radiusProperty(), Math.max(width, height) / 2 + 250))
        );
    }
    
    
    public static void uiButtonHover(Button button, double size) {
        double sceneWidth = button.getScene().getWidth();
        double sceneHeight = button.getScene().getHeight();
        button.setFont(new Font(Fonts.GAME_FONT.getFamily(), size * Math.min(sceneWidth, sceneHeight) / 600));
    }
    
    
    public static void uiButtonIdle(Button button, double size) {
        double sceneWidth = button.getScene().getWidth();
        double sceneHeight = button.getScene().getHeight();
        button.setFont(new Font(Fonts.GAME_FONT.getFamily(), size * Math.min(sceneWidth, sceneHeight) / 600));
    }
}

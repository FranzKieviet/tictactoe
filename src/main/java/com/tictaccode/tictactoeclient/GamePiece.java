package com.tictaccode.tictactoeclient;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class GamePiece {
    
    private PlayType playType;
    
    private ImageView xImage, oImage;
    
    private Button gameButton;
    
    private Circle fade;
    
    public GamePiece(ImageView xImage, ImageView oImage, Button gameButton, Circle fade) {
        this.xImage = xImage;
        this.oImage = oImage;
        this.gameButton = gameButton;
        this.fade = fade;
        
        playType = PlayType.NOTHING;
    }
    
    public void setPiece(PlayType playType) {
        this.playType = playType;
    }
    
    public void updatePiece(double newX, double newY, double pieceSize) {
        gameButton.setLayoutX(newX);
        gameButton.setLayoutY(newY);
        gameButton.setPrefWidth(pieceSize);
        gameButton.setPrefHeight(pieceSize);
        
        xImage.setX(newX + 25);
        xImage.setY(newY + 25);
        xImage.setFitWidth(pieceSize - 50);
        
        oImage.setX(newX + 25);
        oImage.setY(newY + 25);
        oImage.setFitWidth(pieceSize - 50);
        
        fade.setCenterX(newX + pieceSize / 2);
        fade.setCenterY(newY + pieceSize / 2);
    }
    
    public void showPiece(double boardWidth, TicTacToeView controller, boolean playerCurrentTurn,
                          boolean hasLost) {
        if (playType == PlayType.NOTHING) return;
    
        Timeline timeline;
        
        fade.setRadius(boardWidth / 6 - 10);
        fade.setVisible(true);
    
        if (playType == PlayType.X) {
            xImage.setOpacity(1);
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), new KeyValue(fade.radiusProperty(), 0))
            );
        } else {
            oImage.setOpacity(1);
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), new KeyValue(fade.radiusProperty(),
                            oImage.getFitWidth() / 3))
            );
        }
    
        timeline.play();
        timeline.setOnFinished(e -> {
            fade.setVisible(false);
        
            controller.getTurnLabel().setText((controller.getTurnCount() % 2 == 1 ? "X's Turn" : "O's Turn"));
            
            if (!hasLost && !playerCurrentTurn)
                controller.enableAvailableGameButtons();
        });
    }
    
    public void hidePiece() {
        xImage.setOpacity(0);
        oImage.setOpacity(0);
    }
    
    public void toggleGameButton(boolean doEnable) {
        gameButton.setDisable(!doEnable);
    }
    
    public double getX() {
        return gameButton.getLayoutX();
    }
    
    public double getY() {
        return gameButton.getLayoutY();
    }
    
    public double getWidth() {
        return gameButton.getWidth();
    }
    
    public double getHeight() {
        return gameButton.getHeight();
    }
    
    public ImageView getImage() {
        if (playType == PlayType.X)
            return xImage;
        else if (playType == PlayType.O)
            return oImage;
        else
            return null;
    }
    
    public PlayType getPlayType() {
        return playType;
    }
}

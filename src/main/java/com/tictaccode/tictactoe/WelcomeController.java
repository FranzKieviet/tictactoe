package com.tictaccode.tictactoe;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeController extends Controller {
    
    @FXML
    private Pane pane;
    
    @FXML
    private Text title, versionLabel;
    
    @FXML
    private Button singleplayerButton, localMultiplayerButton, onlineMultiplayerButton, exitButton;
    
    @FXML
    private ImageView backgroundPattern;
    
    @FXML
    private Circle fade;
    
    
    public void initialize() {
        // sets the text of the version label based on the current version of the application
        versionLabel.setText(TicTacToeApplication.VERSION);
        
        title.setFont(new Font(Fonts.GAME_FONT.getFamily(), 60));
        
        singleplayerButton.setFont(Fonts.GAME_FONT);
        localMultiplayerButton.setFont(Fonts.GAME_FONT);
        onlineMultiplayerButton.setFont(Fonts.GAME_FONT);
        exitButton.setFont(Fonts.GAME_FONT);
    
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
        
        singleplayerButton.setOnMouseEntered(e -> uiButtonHover(singleplayerButton));
        singleplayerButton.setOnMouseExited(e -> uiButtonIdle(singleplayerButton));
    
        localMultiplayerButton.setOnMouseEntered(e -> uiButtonHover(localMultiplayerButton));
        localMultiplayerButton.setOnMouseExited(e -> uiButtonIdle(localMultiplayerButton));
    
        onlineMultiplayerButton.setOnMouseEntered(e -> uiButtonHover(onlineMultiplayerButton));
        onlineMultiplayerButton.setOnMouseExited(e -> uiButtonIdle(onlineMultiplayerButton));
    
        exitButton.setOnMouseEntered(e -> uiButtonHover(exitButton));
        exitButton.setOnMouseExited(e -> uiButtonIdle(exitButton));
    }
    
    
    @Override
    public void startUI() {
        updateUI();
    
        TranslateTransition backgroundAnimation = new TranslateTransition(Duration.seconds(150), backgroundPattern);
        backgroundAnimation.setFromX(-backgroundPattern.getScene().getWidth());
        backgroundAnimation.setFromY(-backgroundPattern.getScene().getHeight());
        backgroundAnimation.setToX(0);
        backgroundAnimation.setToY(0);
        backgroundAnimation.setAutoReverse(true);
        backgroundAnimation.setCycleCount(Animation.INDEFINITE);
        backgroundAnimation.play();
    }
    
    
    public void fadeIn() {
        Animations.getFadeInTimeline(fade, 500).play();
    }
    
    
    @Override
    public void updateUI() {
        double sceneWidth = backgroundPattern.getScene().getWidth();
        double sceneHeight = backgroundPattern.getScene().getHeight();
        
        backgroundPattern.setFitWidth(Math.max(sceneWidth, sceneHeight) * 2);
    
        title.setFont(new Font(Fonts.GAME_FONT.getFamily(), 60 * Math.min(sceneWidth, sceneHeight) / 600));
        title.setX(sceneWidth / 2 - title.getFont().getSize() * 3.4);
        title.setY(title.getFont().getSize() * 1.5);
    
        fade.setCenterX(sceneWidth / 2);
        fade.setCenterY(sceneHeight / 2);
    
        System.out.println("BEFORE:\nFont Size: " + singleplayerButton.getFont().getSize());
        System.out.println("Width: " + singleplayerButton.getWidth() + "  Height: " + singleplayerButton.getHeight());
        System.out.println("X: " + singleplayerButton.getLayoutX() + "  Y: " + singleplayerButton.getLayoutY());
        
        singleplayerButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        singleplayerButton.setPrefWidth(singleplayerButton.getFont().getSize() * 12);
        singleplayerButton.setPrefHeight(sceneHeight / 8);
        singleplayerButton.setLayoutX(sceneWidth / 2 - singleplayerButton.getPrefWidth() / 2);
        singleplayerButton.setLayoutY(title.getY() + 50);
    
        localMultiplayerButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        localMultiplayerButton.setPrefWidth(localMultiplayerButton.getFont().getSize() * 14);
        localMultiplayerButton.setPrefHeight(sceneHeight / 8);
        localMultiplayerButton.setLayoutX(sceneWidth / 2 - localMultiplayerButton.getPrefWidth() / 2);
        localMultiplayerButton.setLayoutY(singleplayerButton.getLayoutY() + singleplayerButton.getPrefHeight() + 20);
    
        onlineMultiplayerButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        onlineMultiplayerButton.setPrefWidth(onlineMultiplayerButton.getFont().getSize() * 14);
        onlineMultiplayerButton.setPrefHeight(sceneHeight / 8);
        onlineMultiplayerButton.setLayoutX(sceneWidth / 2 - onlineMultiplayerButton.getPrefWidth() / 2);
        onlineMultiplayerButton.setLayoutY(localMultiplayerButton.getLayoutY() +
                localMultiplayerButton.getPrefHeight() + 20);
    
        exitButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        exitButton.setPrefWidth(exitButton.getFont().getSize() * 5);
        exitButton.setPrefHeight(sceneHeight / 8);
        exitButton.setLayoutX(sceneWidth / 2 - exitButton.getPrefWidth() / 2);
        exitButton.setLayoutY(onlineMultiplayerButton.getLayoutY() + onlineMultiplayerButton.getPrefHeight() + 20);
    
        versionLabel.setY(sceneHeight - 10);
    }
    
    
    public void uiButtonHover(Button button) {
        double sceneWidth = backgroundPattern.getScene().getWidth();
        double sceneHeight = backgroundPattern.getScene().getHeight();
        button.setFont(new Font(Fonts.GAME_FONT.getFamily(), 35 * Math.min(sceneWidth, sceneHeight) / 600));
    }
    
    
    public void uiButtonIdle(Button button) {
        double sceneWidth = backgroundPattern.getScene().getWidth();
        double sceneHeight = backgroundPattern.getScene().getHeight();
        button.setFont(new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
    }
    
    
    @Override
    public void setApplication(TicTacToeApplication application) {
        this.application = application;
    }
    
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    public void goToSingleplayer() {
        // TODO implement go to singleplayer game here
    }
    
    
    public void goToLocalMultiplayer() {
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);
        
        fadeOut.play();
        fadeOut.setOnFinished(e -> {
            try {
                application.startLocalMultiplayerGame(stage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    
    public void goToOnlineMultiplayer() {
        // TODO implement go to online multiplayer game here
    }
    
    
    public void exitGame() {
        System.exit(0);
    }
}

package com.tictaccode.tictactoeclient;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WelcomeView extends Controller {
    
    @FXML
    private Pane pane;
    
    @FXML
    private Text title, versionLabel;
    
    @FXML
    private Button singleplayerButton, localMultiplayerButton, onlineMultiplayerButton, exitButton, createServerButton,
            joinServerButton, backButton;
    
    @FXML
    private ImageView backgroundPattern;
    
    @FXML
    private Circle fade;
    
    private double minBackgroundSize;
    
    
    public void initialize() {
        // sets the text of the version label based on the current version of the application
        versionLabel.setText(TicTacToeApplication.VERSION);
        
        title.setFont(new Font(Fonts.GAME_FONT.getFamily(), 60));
        
        singleplayerButton.setFont(Fonts.GAME_FONT);
        localMultiplayerButton.setFont(Fonts.GAME_FONT);
        onlineMultiplayerButton.setFont(Fonts.GAME_FONT);
        exitButton.setFont(Fonts.GAME_FONT);
        createServerButton.setFont(Fonts.GAME_FONT);
        joinServerButton.setFont(Fonts.GAME_FONT);
        backButton.setFont(Fonts.GAME_FONT);
    
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
        
        singleplayerButton.setOnMouseEntered(e -> Animations.uiButtonHover(singleplayerButton, 35));
        singleplayerButton.setOnMouseExited(e -> Animations.uiButtonIdle(singleplayerButton, 30));
    
        localMultiplayerButton.setOnMouseEntered(e -> Animations.uiButtonHover(localMultiplayerButton, 35));
        localMultiplayerButton.setOnMouseExited(e -> Animations.uiButtonIdle(localMultiplayerButton, 30));
    
        onlineMultiplayerButton.setOnMouseEntered(e -> Animations.uiButtonHover(onlineMultiplayerButton, 35));
        onlineMultiplayerButton.setOnMouseExited(e -> Animations.uiButtonIdle(onlineMultiplayerButton, 30));
    
        exitButton.setOnMouseEntered(e -> Animations.uiButtonHover(exitButton, 35));
        exitButton.setOnMouseExited(e -> Animations.uiButtonIdle(exitButton, 30));
    
        createServerButton.setOnMouseEntered(e -> Animations.uiButtonHover(createServerButton, 35));
        createServerButton.setOnMouseExited(e -> Animations.uiButtonIdle(createServerButton, 30));
    
        joinServerButton.setOnMouseEntered(e -> Animations.uiButtonHover(joinServerButton, 35));
        joinServerButton.setOnMouseExited(e -> Animations.uiButtonIdle(joinServerButton, 30));
    
        backButton.setOnMouseEntered(e -> Animations.uiButtonHover(backButton, 35));
        backButton.setOnMouseExited(e -> Animations.uiButtonIdle(backButton, 30));
    }
    
    
    @Override
    public void startUI() {
        minBackgroundSize = Math.max(backgroundPattern.getScene().getWidth(),
                backgroundPattern.getScene().getHeight()) * 2;
        
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
        
        backgroundPattern.setFitWidth(Math.max(minBackgroundSize, (Math.max(sceneWidth, sceneHeight) * 2)));
    
        title.setFont(new Font(Fonts.GAME_FONT.getFamily(), 60 * Math.min(sceneWidth, sceneHeight) / 600));
        title.setX(sceneWidth / 2 - title.getFont().getSize() * 3.4);
        title.setY(title.getFont().getSize() * 1.5);
    
        fade.setCenterX(sceneWidth / 2);
        fade.setCenterY(sceneHeight / 2);
        
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
    
        createServerButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        createServerButton.setPrefWidth(createServerButton.getFont().getSize() * 12);
        createServerButton.setPrefHeight(sceneHeight / 8);
        createServerButton.setLayoutX(sceneWidth / 2 - createServerButton.getPrefWidth() / 2);
        createServerButton.setLayoutY(title.getY() + 60);
    
        joinServerButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        joinServerButton.setPrefWidth(joinServerButton.getFont().getSize() * 10);
        joinServerButton.setPrefHeight(sceneHeight / 8);
        joinServerButton.setLayoutX(sceneWidth / 2  - joinServerButton.getPrefWidth() / 2);
        joinServerButton.setLayoutY(createServerButton.getLayoutY() + createServerButton.getPrefHeight() + 30);
    
        backButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        backButton.setPrefWidth(backButton.getFont().getSize() * 5);
        backButton.setPrefHeight(sceneHeight / 8);
        backButton.setLayoutX(sceneWidth / 2  - backButton.getPrefWidth() / 2);
        backButton.setLayoutY(joinServerButton.getLayoutY() + joinServerButton.getPrefHeight() + 30);
    
        versionLabel.setY(sceneHeight - 10);
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
        toggleButtons(true);
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);

        fadeOut.setOnFinished(e -> {
            try {
                application.startSinglePlayerGame(stage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        fadeOut.play();
    }
    
    
    public void goToLocalMultiplayer() {
        toggleButtons(true);
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);
    
        fadeOut.setOnFinished(e -> {
            try {
                application.startLocalMultiplayerGame(stage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        fadeOut.play();
    }
    
    
    public void goToOnlineMultiplayer() {
        toggleOnlineOptions(true);
    }
    
    
    public void goToCreateServer() {
        toggleButtons(true);
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);
    
        fadeOut.setOnFinished(e -> application.startCreateServer(stage));
        fadeOut.play();
    }
    
    
    public void goToJoinServer() {
        toggleButtons(true);
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);
    
        fadeOut.setOnFinished(e -> application.startJoinServer(stage));
        fadeOut.play();
    }
    
    
    public void goBack() {
        toggleOnlineOptions(false);
    }
    
    
    public void exitGame() {
        toggleButtons(true);
        application.exitGame();
    }
    
    
    public void toggleOnlineOptions(boolean toggle) {
        singleplayerButton.setVisible(!toggle);
        localMultiplayerButton.setVisible(!toggle);
        onlineMultiplayerButton.setVisible(!toggle);
        exitButton.setVisible(!toggle);
    
        createServerButton.setVisible(toggle);
        joinServerButton.setVisible(toggle);
        backButton.setVisible(toggle);
    }
    
    
    public void toggleButtons(boolean disable) {
        singleplayerButton.setDisable(disable);
        localMultiplayerButton.setDisable(disable);
        onlineMultiplayerButton.setDisable(disable);
        exitButton.setDisable(disable);
        
        createServerButton.setDisable(disable);
        joinServerButton.setDisable(disable);
        backButton.setDisable(disable);
    }
}

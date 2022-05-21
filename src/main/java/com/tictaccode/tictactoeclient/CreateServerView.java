package com.tictaccode.tictactoeclient;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CreateServerView extends Controller {
    @FXML
    private Pane pane;
    
    @FXML
    private ImageView backgroundPattern;
    
    @FXML
    private Text title, serverNameLabel;
    
    @FXML
    private TextField serverNameInput;
    
    @FXML
    private Button createServerButton, backButton;
    
    @FXML
    private Circle fade;
    
    private double minBackgroundSize;
    
    private TicTacToeApplication application;
    private Stage stage;
    private String serverName;
    
    public void initialize() {
        title.setFont(new Font(Fonts.GAME_FONT.getFamily(), 60));
        serverNameLabel.setFont(Fonts.GAME_FONT);
        serverNameInput.setFont(Fonts.GAME_FONT);
        
        createServerButton.setFont(Fonts.GAME_FONT);
        backButton.setFont(Fonts.GAME_FONT);
    
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
    
        createServerButton.setOnMouseEntered(e -> Animations.uiButtonHover(createServerButton, 35));
        createServerButton.setOnMouseExited(e -> Animations.uiButtonIdle(createServerButton, 30));
    
        backButton.setOnMouseEntered(e -> Animations.uiButtonHover(backButton, 35));
        backButton.setOnMouseExited(e -> Animations.uiButtonIdle(backButton, 30));
    }
    
    
    @Override
    public void startUI() {
        minBackgroundSize = Math.max(backgroundPattern.getScene().getWidth(),
                backgroundPattern.getScene().getHeight()) * 2;
        
        updateUI();
    
        double width = fade.getScene().getWidth();
        double height = fade.getScene().getHeight();
        fade.setCenterX(width / 2);
        fade.setCenterY(height / 2);
        fade.setRadius(Math.max(width, height) / 2 + 250);
    
        TranslateTransition backgroundAnimation = new TranslateTransition(Duration.seconds(150), backgroundPattern);
        backgroundAnimation.setFromX(-width);
        backgroundAnimation.setFromY(-height);
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
        title.setX(sceneWidth / 2 - title.getFont().getSize() * 3.8);
        title.setY(title.getFont().getSize() * 1.5);
    
        fade.setCenterX(sceneWidth / 2);
        fade.setCenterY(sceneHeight / 2);
    
        serverNameInput.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        serverNameInput.setPrefWidth(serverNameInput.getFont().getSize() * 12);
        serverNameInput.setPrefHeight(sceneHeight / 8);
        serverNameInput.setLayoutX(sceneWidth / 2 - serverNameInput.getPrefWidth() / 2);
        serverNameInput.setLayoutY(sceneHeight / 2 - serverNameInput.getPrefHeight() / 2);
    
        serverNameLabel.setFont(new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        serverNameLabel.setX(serverNameInput.getLayoutX());
        serverNameLabel.setY(sceneHeight / 2 - serverNameLabel.getFont().getSize() * 1.5 - 10);
        
        createServerButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        createServerButton.setPrefWidth(createServerButton.getFont().getSize() * 12);
        createServerButton.setPrefHeight(sceneHeight / 8);
        createServerButton.setLayoutX(sceneWidth - createServerButton.getPrefWidth() - 10);
        createServerButton.setLayoutY(sceneHeight - createServerButton.getPrefHeight() - 30);
    
        backButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        backButton.setPrefWidth(backButton.getFont().getSize() * 5);
        backButton.setPrefHeight(sceneHeight / 8);
        backButton.setLayoutX(30);
        backButton.setLayoutY(sceneHeight - backButton.getPrefHeight() - 30);
    }
    
    
    @Override
    public void setApplication(TicTacToeApplication application) {
        this.application = application;
    }
    
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    public void createServer() {
        createServerButton.setDisable(true);
        
        if (createServerButton.getText().equals("Create Server")) {
            serverNameInput.setEditable(false);
            backButton.setDisable(true);
            
            String tempName = serverNameInput.getText().trim();
            if (tempName.length() >= 3 && tempName.length() <= 15)
                serverName = tempName;
            else {
                serverNameInput.setEditable(true);
                backButton.setDisable(false);
                createServerButton.setDisable(false);
                return;
            }
            
            try {
                application.createServer(serverName);
            }
            catch (RuntimeException e) {
                serverNameInput.setEditable(true);
                backButton.setDisable(false);
                createServerButton.setDisable(false);
                return;
            }
            
            createServerButton.setText("Cancel Server");
        }
        else {
            application.cancelServer(serverName);
            
            createServerButton.setText("Create Server");
            backButton.setDisable(false);
            serverNameInput.setEditable(true);
        }
        
        createServerButton.setDisable(false);
    }
    
    
    public void creatingServer() {
        backButton.setDisable(true);
        createServerButton.setDisable(true);
        
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);
        fadeOut.setOnFinished(e -> application.startGame(stage, GameType.ONLINE_MULTIPLAYER));
        fadeOut.play();
    }
    
    
    public void cancelServer() {
        if (createServerButton.getText().equals("Cancel Server"))
            application.cancelServer(serverNameInput.getText().trim());
    }
    
    
    public void goBack() {
        backButton.setDisable(true);
        createServerButton.setDisable(true);
        
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);
    
        fadeOut.setOnFinished(e -> {
            try {
                application.startWelcomeScreen(stage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        fadeOut.play();
    }
}

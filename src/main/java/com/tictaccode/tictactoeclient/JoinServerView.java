package com.tictaccode.tictactoeclient;

import javafx.animation.Animation;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JoinServerView extends Controller {
    @FXML
    private Pane pane;
    
    @FXML
    private ImageView backgroundPattern;
    
    @FXML
    private Text title, availableServersLabel;
    
    @FXML
    private ScrollPane serverScrollPane;
    
    @FXML
    private VBox serverContainer;
    
    @FXML
    private Button joinServerButton, backButton;
    
    @FXML
    private Circle fade;
    
    private double minBackgroundSize;
    
    private TicTacToeApplication application;
    private Stage stage;
    private Button selectedButton;
    
    public void initialize() {
        title.setFont(new Font(Fonts.GAME_FONT.getFamily(), 60));
        availableServersLabel.setFont(Fonts.GAME_FONT);
    
        joinServerButton.setFont(Fonts.GAME_FONT);
        backButton.setFont(Fonts.GAME_FONT);
    
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
    
        joinServerButton.setOnMouseEntered(e -> Animations.uiButtonHover(joinServerButton, 35));
        joinServerButton.setOnMouseExited(e -> Animations.uiButtonIdle(joinServerButton, 30));
    
        backButton.setOnMouseEntered(e -> Animations.uiButtonHover(backButton, 35));
        backButton.setOnMouseExited(e -> Animations.uiButtonIdle(backButton, 30));
        
        selectedButton = null;
        joinServerButton.setDisable(true);
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
        title.setX(sceneWidth / 2 - title.getFont().getSize() * 3.2);
        title.setY(title.getFont().getSize() * 1.5);
    
        fade.setCenterX(sceneWidth / 2);
        fade.setCenterY(sceneHeight / 2);
        
        joinServerButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        joinServerButton.setPrefWidth(joinServerButton.getFont().getSize() * 10);
        joinServerButton.setPrefHeight(sceneHeight / 8);
        joinServerButton.setLayoutX(sceneWidth - joinServerButton.getPrefWidth() - 10);
        joinServerButton.setLayoutY(sceneHeight - joinServerButton.getPrefHeight() - 30);
    
        backButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        backButton.setPrefWidth(backButton.getFont().getSize() * 5);
        backButton.setPrefHeight(sceneHeight / 8);
        backButton.setLayoutX(30);
        backButton.setLayoutY(sceneHeight - backButton.getPrefHeight() - 30);
    
        serverScrollPane.setPrefWidth(backButton.getFont().getSize() * 15);
        serverScrollPane.setPrefHeight(sceneHeight / 2);
        serverScrollPane.setLayoutX(sceneWidth / 2 - serverScrollPane.getPrefWidth() / 2);
        serverScrollPane.setLayoutY(sceneHeight / 2 - serverScrollPane.getPrefHeight() / 2 + title.getFont().getSize() / 2);
    
        availableServersLabel.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        availableServersLabel.setX(serverScrollPane.getLayoutX());
        availableServersLabel.setY(
                serverScrollPane.getLayoutY() - availableServersLabel.getFont().getSize() + 10);
        
        for (Node node : serverContainer.getChildren()) {
            Button b = (Button) node;
            b.setFont(new Font(Fonts.GAME_FONT.getFamily(), 20 * Math.min(sceneWidth, sceneHeight) / 600));
            b.setPrefWidth(serverScrollPane.getPrefWidth() - 2);
            b.setPrefHeight(sceneHeight / 12);
        }
    }
    
    
    @Override
    public void setApplication(TicTacToeApplication application) {
        this.application = application;
    }
    
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    public void addServer(String server) {
        Platform.runLater(() -> {
            Button b = new Button(server);
            b.setStyle("-fx-border-color: #151515; -fx-border-width: 1; -fx-background-radius: 1; -fx-background-color: " +
                    "#454545; -fx-text-fill: #F0F0F0; -fx-text-alignment: left;");
            b.setOnMouseClicked(this::selectServer);
    
            serverContainer.getChildren().add(b);
    
            updateUI();
        });
    }
    
    
    public void removeServer(String server) {
        if (selectedButton != null && selectedButton.getText().equals(server)) {
            selectedButton = null;
            joinServerButton.setDisable(true);
        }
        
        Platform.runLater(() -> serverContainer.getChildren().removeIf(n -> ((Button)n).getText().equals(server)));
    }
    
    
    public void selectServer(MouseEvent e) {
        if (selectedButton != null)
            selectedButton.setStyle("-fx-border-color: #151515; -fx-border-width: 1; -fx-background-radius: 1; " +
                    "-fx-background-color: #454545; -fx-text-fill: #F0F0F0; -fx-text-alignment: left;");
        
        Button tempButton = (Button)e.getSource();
        
        if (tempButton.equals(selectedButton)) {
            selectedButton = null;
            joinServerButton.setDisable(true);
        }
        else {
            selectedButton = tempButton;
            selectedButton.setStyle("-fx-border-color: #151515; -fx-border-width: 1; -fx-background-radius: 1; " +
                    "-fx-background-color: #3C5AC8; -fx-text-fill: #F0F0F0; -fx-text-alignment: left;");
            joinServerButton.setDisable(false);
        }
    }
    
    
    public void joinServer() {
        if (selectedButton != null) {
            joinServerButton.setDisable(true);
            backButton.setDisable(true);
            
            application.joinServer(selectedButton.getText());
        }
    }
    
    
    public void joiningServer() {
        backButton.setDisable(true);
        joinServerButton.setDisable(true);
        serverContainer.getChildren().forEach(node -> node.setDisable(true));
        application.stopServerListening();
        
        Timeline fadeOut = Animations.getFadeOutTimeline(fade, 100);
        fadeOut.setOnFinished(e -> application.startGame(stage, GameType.ONLINE_MULTIPLAYER));
        fadeOut.play();
    }
    
    
    public void goBack() {
        backButton.setDisable(true);
        joinServerButton.setDisable(true);
        serverContainer.getChildren().forEach(node -> node.setDisable(true));
        application.stopServerListening();
        
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

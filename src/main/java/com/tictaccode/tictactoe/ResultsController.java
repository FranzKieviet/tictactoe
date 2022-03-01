package com.tictaccode.tictactoe;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ResultsController extends Controller {
    
    @FXML
    private Pane pane;
    
    @FXML
    private Text resultsLabel;
    
    @FXML
    private Button tryAgainButton, backButton;
    
    private GameOverInfo gameOverInfo;
    
    private GameType gameType;
    
    
    public void initialize() {
        resultsLabel.setFont(Fonts.GAME_FONT);
        
        tryAgainButton.setFont(Fonts.GAME_FONT);
        backButton.setFont(Fonts.GAME_FONT);
    
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
    
        tryAgainButton.setOnMouseEntered(e -> Animations.uiButtonHover(tryAgainButton, 35));
        tryAgainButton.setOnMouseExited(e -> Animations.uiButtonIdle(tryAgainButton, 30));
    
        backButton.setOnMouseEntered(e -> Animations.uiButtonHover(backButton, 35));
        backButton.setOnMouseExited(e -> Animations.uiButtonIdle(backButton, 30));
    }
    
    
    @Override
    public void startUI() {
        stage.setResizable(true);
        updateUI();
        
        switch (gameOverInfo.getStateType()) {
            case X_WINNER:
                resultsLabel.setText("X wins!");
                break;
            case O_WINNER:
                resultsLabel.setText("O wins!");
                break;
            default:
                resultsLabel.setText("Cat's game!");
        }
        
        FadeTransition resultsTransition = new FadeTransition(Duration.millis(500), resultsLabel);
        resultsTransition.setFromValue(0);
        resultsTransition.setToValue(1);
        
        FadeTransition tryAgainTransition = new FadeTransition(Duration.millis(500), tryAgainButton);
        tryAgainTransition.setFromValue(0);
        tryAgainTransition.setToValue(1);
        
        FadeTransition backTransition = new FadeTransition(Duration.millis(500), backButton);
        backTransition.setFromValue(0);
        backTransition.setToValue(1);
    
        SequentialTransition transition = new SequentialTransition(resultsTransition, tryAgainTransition,
                backTransition);
        
        transition.play();
    }
    
    
    @Override
    public void updateUI() {
        double sceneWidth = resultsLabel.getScene().getWidth();
        double sceneHeight = resultsLabel.getScene().getHeight();
    
        resultsLabel.setFont(new Font(Fonts.GAME_FONT.getFamily(), 60 * Math.min(sceneWidth, sceneHeight) / 600));
        resultsLabel.setX(sceneWidth / 2 - resultsLabel.getFont().getSize() * 1.8);
        resultsLabel.setY(resultsLabel.getFont().getSize() * 1.5);
    
        tryAgainButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        tryAgainButton.setPrefWidth(tryAgainButton.getFont().getSize() * 10);
        tryAgainButton.setPrefHeight(sceneHeight / 8);
        tryAgainButton.setLayoutX(sceneWidth / 2 - tryAgainButton.getPrefWidth() / 2);
        tryAgainButton.setLayoutY(resultsLabel.getY() + 120);
    
        backButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        backButton.setPrefWidth(backButton.getFont().getSize() * 5);
        backButton.setPrefHeight(sceneHeight / 8);
        backButton.setLayoutX(sceneWidth / 2 - backButton.getPrefWidth() / 2);
        backButton.setLayoutY(tryAgainButton.getLayoutY() + tryAgainButton.getPrefHeight() + 50);
    }
    
    
    @Override
    public void setApplication(TicTacToeApplication application) {
        this.application = application;
    }
    
    
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    public void setGameOverInfo(GameOverInfo gameOverInfo) {
        this.gameOverInfo = gameOverInfo;
    }
    
    
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
    
    
    public void tryAgain() {
        FadeTransition resultsTransition = new FadeTransition(Duration.millis(500), resultsLabel);
        resultsTransition.setFromValue(1);
        resultsTransition.setToValue(0);
    
        FadeTransition tryAgainTransition = new FadeTransition(Duration.millis(500), tryAgainButton);
        tryAgainTransition.setFromValue(1);
        tryAgainTransition.setToValue(0);
    
        FadeTransition backTransition = new FadeTransition(Duration.millis(500), backButton);
        backTransition.setFromValue(1);
        backTransition.setToValue(0);
    
        ParallelTransition transition = new ParallelTransition(resultsTransition, tryAgainTransition,
                backTransition);
    
        transition.play();
        transition.setOnFinished(e -> {
            try {
                application.startLocalMultiplayerGame(stage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    
    public void goBack() {
        FadeTransition resultsTransition = new FadeTransition(Duration.millis(500), resultsLabel);
        resultsTransition.setFromValue(1);
        resultsTransition.setToValue(0);
    
        FadeTransition tryAgainTransition = new FadeTransition(Duration.millis(500), tryAgainButton);
        tryAgainTransition.setFromValue(1);
        tryAgainTransition.setToValue(0);
    
        FadeTransition backTransition = new FadeTransition(Duration.millis(500), backButton);
        backTransition.setFromValue(1);
        backTransition.setToValue(0);
    
        ParallelTransition transition = new ParallelTransition(resultsTransition, tryAgainTransition,
                backTransition);
    
        transition.play();
        transition.setOnFinished(e -> {
            try {
                application.startWelcomeScreen(stage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
}

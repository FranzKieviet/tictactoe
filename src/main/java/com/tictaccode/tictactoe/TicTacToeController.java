package com.tictaccode.tictactoe;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.stage.Stage;

/**
 * Controller for the tic-tac-toe game UI that handles all interactions with elements in the UI.
 */
public class TicTacToeController {
    /** Javafx pane element retrieved from the fxml file. */
    @FXML
    Pane pane;
    
    /**
     * Javafx text element retrieved from the fxml file.
     */
    @FXML
    Text versionLabel;
    
    /**
     * Javafx button elements retrieved from the fxml file.
     */
    @FXML
    Button spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8, spot9, backButton;
    
    /** Javafx circle elements retrieved from the fxml file. */
    @FXML
    Circle spot1Fade, spot2Fade, spot3Fade, spot4Fade, spot5Fade, spot6Fade, spot7Fade, spot8Fade, spot9Fade, gameFade;
    
    /** Javafx image view elements retrieved from the fxml file. */
    @FXML
    private ImageView boardImage, spot1XImage, spot2XImage, spot3XImage, spot4XImage, spot5XImage, spot6XImage,
            spot7XImage, spot8XImage, spot9XImage, spot1OImage, spot2OImage, spot3OImage, spot4OImage, spot5OImage,
            spot6OImage, spot7OImage, spot8OImage, spot9OImage, backgroundPattern;
    
    @FXML
    Label turnLabel;
    
    /**
     * Stores all the game buttons from the ui for ease of use.
     */
    private Button[] gameButtons;
    
    /** Stores all the game button image views for the ui for ease of use. */
    public ImageView[] gameButtonXImages, gameButtonOImages;
    
    /** Stores all the game button image fades for the ui for ease of use. */
    public Circle[] imageFades;
    
    /** Stores the x and o images used on the ui. */
    private Image xImage, oImage;
    
    /**
     * Stores all the X pieces.
     */
//    private XPiece[] xPieces;
    
    /**
     * Stores all the O pieces.
     */
//    private OPiece[] oPieces;
    
    /**
     * A board object that is used to keep track of the positions of pieces on the board and handle game logic.
     */
    private Board board;
    
    private boolean isXTurn;
    
    private Font gameFont;
    
    private Stage stage;
    
    private Scene welcomeScene;
    
    /**
     * An integer variable that holds count on which turn number it is
     */
    private int turnCount;
    
    /**
     * The initialize() method is automatically called by javafx when the controller class is first called.
     * Initializes all private data members and sets up the initial tic-tac-toe board.
     */
    public void initialize() {
        gameFont = Font.loadFont(TicTacToeController.class.getResource("Chalkduster.ttf").toString(), 45);
        
        // sets the text of the version label based on the current version of the application
        versionLabel.setText(TicTacToeApplication.VERSION);
        versionLabel.setFont(gameFont);
        
        // initializes the gameButtons array
        gameButtons = new Button[9];
        gameButtons[0] = spot1;
        gameButtons[1] = spot2;
        gameButtons[2] = spot3;
        gameButtons[3] = spot4;
        gameButtons[4] = spot5;
        gameButtons[5] = spot6;
        gameButtons[6] = spot7;
        gameButtons[7] = spot8;
        gameButtons[8] = spot9;
        
        // initializes the gameButtonXImages array
        gameButtonXImages = new ImageView[9];
        gameButtonXImages[0] = spot1XImage;
        gameButtonXImages[1] = spot2XImage;
        gameButtonXImages[2] = spot3XImage;
        gameButtonXImages[3] = spot4XImage;
        gameButtonXImages[4] = spot5XImage;
        gameButtonXImages[5] = spot6XImage;
        gameButtonXImages[6] = spot7XImage;
        gameButtonXImages[7] = spot8XImage;
        gameButtonXImages[8] = spot9XImage;
    
        // initializes the gameButtonXImages array
        gameButtonOImages = new ImageView[9];
        gameButtonOImages[0] = spot1OImage;
        gameButtonOImages[1] = spot2OImage;
        gameButtonOImages[2] = spot3OImage;
        gameButtonOImages[3] = spot4OImage;
        gameButtonOImages[4] = spot5OImage;
        gameButtonOImages[5] = spot6OImage;
        gameButtonOImages[6] = spot7OImage;
        gameButtonOImages[7] = spot8OImage;
        gameButtonOImages[8] = spot9OImage;
    
        xImage = new Image(TicTacToeController.class.getResource("images/X.png").toString());
        oImage = new Image(TicTacToeController.class.getResource("images/O.png").toString());
        
        // preserves the image size and sets images
        for (int i = 0; i < 9; ++i) {
            gameButtonXImages[i].setPreserveRatio(true);
            gameButtonOImages[i].setPreserveRatio(true);
            gameButtonXImages[i].setImage(xImage);
            gameButtonOImages[i].setImage(oImage);
            gameButtonXImages[i].setCache(true);
            gameButtonOImages[i].setCache(true);
            gameButtonXImages[i].setSmooth(true);
            gameButtonOImages[i].setSmooth(true);
        }
        
        
        // initializes the imageFades array
        imageFades = new Circle[9];
        imageFades[0] = spot1Fade;
        imageFades[1] = spot2Fade;
        imageFades[2] = spot3Fade;
        imageFades[3] = spot4Fade;
        imageFades[4] = spot5Fade;
        imageFades[5] = spot6Fade;
        imageFades[6] = spot7Fade;
        imageFades[7] = spot8Fade;
        imageFades[8] = spot9Fade;
        
        // initializes the board
        board = new Board(this);
        
        boardImage.setPreserveRatio(true);
        boardImage.setImage(new Image(TicTacToeController.class.getResource("images/Board.png").toString()));
        
        backgroundPattern.setPreserveRatio(true);
        backgroundPattern.setImage(
                new Image(TicTacToeController.class.getResource("images/BackgroundPattern.png").toString()));
    
        isXTurn = false;
    
        // initializes the turn count
        turnCount = 1;
        turnLabel.setText("X");
        
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
    }
    
    
    public void startUI() {
        updateUI();
    
        double sceneWidth = boardImage.getScene().getWidth();
        double sceneHeight = boardImage.getScene().getHeight();
        
        TranslateTransition backgroundAnimation = new TranslateTransition(Duration.seconds(150), backgroundPattern);
        backgroundAnimation.setFromX(-sceneWidth);
        backgroundAnimation.setFromY(-sceneHeight);
        backgroundAnimation.setToX(0);
        backgroundAnimation.setToY(0);
        backgroundAnimation.setAutoReverse(true);
        backgroundAnimation.setCycleCount(Animation.INDEFINITE);
        backgroundAnimation.play();
        
        playBoardAnimation();
    }
    
    
    public void updateUI() {
        double sceneWidth = boardImage.getScene().getWidth();
        double sceneHeight = boardImage.getScene().getHeight();
    
        boardImage.setFitWidth(Math.min(sceneWidth, sceneHeight) - 200);
        backgroundPattern.setFitWidth(Math.max(sceneWidth, sceneHeight) * 2);
    
        double boardImageSize = boardImage.getFitWidth();
    
        boardImage.setX(sceneWidth / 2 - boardImageSize / 2);
        boardImage.setY(sceneHeight / 2 - boardImageSize / 2);
    
        gameFade.setCenterX(sceneWidth / 2);
        gameFade.setCenterY(sceneHeight / 2);
    
        double boardX = boardImage.getX();
        double boardY = boardImage.getY();
    
        for (int y = 0; y < Board.BOARD_SIZE; ++y) {
            for (int x = 0; x < Board.BOARD_SIZE; ++x) {
                gameButtons[x + y * 3].setLayoutX(boardX + boardImageSize / 3 * x);
                gameButtons[x + y * 3].setLayoutY(boardY + boardImageSize / 3 * y);
                gameButtons[x + y * 3].setPrefWidth(boardImageSize / 3);
                gameButtons[x + y * 3].setPrefHeight(boardImageSize / 3);
            
                gameButtonXImages[x + y * 3].setX(boardX + boardImageSize / 3 * x + 25);
                gameButtonXImages[x + y * 3].setY(boardY + boardImageSize / 3 * y + 25);
                gameButtonXImages[x + y * 3].setFitWidth(boardImageSize / 3 - 50);
            
                gameButtonOImages[x + y * 3].setX(boardX + boardImageSize / 3 * x + 25);
                gameButtonOImages[x + y * 3].setY(boardY + boardImageSize / 3 * y + 25);
                gameButtonOImages[x + y * 3].setFitWidth(boardImageSize / 3 - 50);
            
                imageFades[x + y * 3].setCenterX(boardX + boardImageSize / 3 * x + boardImageSize / 6);
                imageFades[x + y * 3].setCenterY(boardY + boardImageSize / 3 * y + boardImageSize / 6);
            }
        }
    
        versionLabel.setY(sceneHeight - 10);
    }
    
    
    public void playBoardAnimation() {
        disableGameButtons();
    
        double width = boardImage.getScene().getWidth();
        double height = boardImage.getScene().getHeight();
        gameFade.setCenterX(width / 2);
        gameFade.setCenterY(height / 2);
        gameFade.setRadius(width + 100);
        
        Thread boardAnimation = new Thread(() -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(500), new KeyValue(gameFade.radiusProperty(),
                            gameFade.getRadius())),
                    new KeyFrame(Duration.millis(1000), new KeyValue(gameFade.radiusProperty(), 0))
            );
            
            Platform.runLater(timeline::play);
        });
        
        boardAnimation.start();
    
        enableAvailableGameButtons();
    }
    
    
    public void showGamePiece(PlayType playType, int spot) {
        Timeline timeline;
        
        imageFades[spot].setRadius(boardImage.getFitWidth() / 6 - 10);
        
        if (playType == PlayType.X) {
            gameButtonXImages[spot].setOpacity(1);
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), new KeyValue(imageFades[spot].radiusProperty(), 0))
            );
        } else {
            gameButtonOImages[spot].setOpacity(1);
            timeline = new Timeline(
                    new KeyFrame(Duration.millis(100), new KeyValue(imageFades[spot].radiusProperty(),
                            gameButtonOImages[spot].getFitWidth() / 3))
            );
        }

        timeline.play();
        timeline.setOnFinished(e -> {
            imageFades[spot].setVisible(false);
    
            // determines whether x won, o won, there was a draw, or to continue the game
            switch (board.checkState()) {
                case X_WINNER:
                    turnLabel.setText("X WIN");
                    break;
                case O_WINNER:
                    turnLabel.setText("O WIN");
                    break;
                case DRAW:
                    turnLabel.setText("DRAW");
                    break;
                default:
                    // move onto the next turn
                    ++turnCount;
            
                    // enables all game buttons in the spots that are empty on the game board
                    enableAvailableGameButtons();
            }
        });
    }
    
    
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    
    
    public void setWelcomeScene(Scene scene) {
        welcomeScene = scene;
    }
    
    
    /**
     * Disables all the game buttons on the tic-tac-toe board.
     */
    public void disableGameButtons() {
        for (Button gameButton : gameButtons)
            gameButton.setDisable(true);
    }
    
    
    /**
     * Enables all game buttons on the tic-tac-toe board that are empty (no X or O).
     */
    public void enableAvailableGameButtons() {
        // the boardSpots array holds whether each spot on the tic-tac-toe board has an O, X, or nothing in it
        PlayType[][] boardSpots = board.getBoardSpots();
        
        // traverses through the boardSpots array and enables all buttons in the spot that has nothing in it
        for (int y = 0; y < Board.BOARD_SIZE; ++y)
            for (int x = 0; x < Board.BOARD_SIZE; ++x)
                if (boardSpots[y][x] == PlayType.NOTHING)
                    gameButtons[x + y * 3].setDisable(false);
    }
    
    
    /**
     * The placeMove() method allows the user to play their move on the tic-tac-toe board and updates all necessary
     * data pertaining to the move.
     *
     * @param mouseEvent holds all the mouse event information when the method is called by the UI.
     */
    public void placeMove(MouseEvent mouseEvent) {
        // disables all game buttons while placing the move
        disableGameButtons();
        
        // sets what type of piece the user is playing based on the turn count
        PlayType playType;
        if (turnCount % 2 == 1) {
            playType = PlayType.X;
            turnLabel.setText("O");
        } else {
            playType = PlayType.O;
            turnLabel.setText("X");
        }
        
        // gets the id number of the button in order to get the position of the button on the game board
        Button b = (Button) mouseEvent.getSource();
        String id = b.getId();
        int idNum = Integer.parseInt(id.substring(id.length() - 1));
        
        // gets the x and y position where the move was placed
        int x = 0, y = 0;
        for (int i = 0; i < idNum - 1; ++i) {
            if (x < Board.BOARD_SIZE - 1)
                x++;
            else {
                y++;
                x = 0;
            }
        }
        
        // sets the move on the board
        board.setBoardPosition(x, y, playType);
        
        // displays the move to the user
        showGamePiece(playType, idNum - 1);
    }
    
    /**
     * The backMove() method allows the user to open welcome screen
     *
     */
    public void backMove() {
        stage.setScene(welcomeScene);
    }
}

package com.tictaccode.tictactoe;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
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
public class TicTacToeController extends Controller {
    /** Javafx pane element retrieved from the fxml file. */
    @FXML
    Pane pane;
    
    /**
     * Javafx text element retrieved from the fxml file.
     */
    @FXML
    Text versionLabel, turnLabel;
    
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
            spot6OImage, spot7OImage, spot8OImage, spot9OImage, backgroundPattern, horizontalLine, verticalLine,
            leftDiagonalLine, rightDiagonalLine;
    
    /** Stores all the game pieces used on the game board for ease of use. */
    private GamePiece[] gamePieces;
    
    /**
     * A board object that is used to keep track of the positions of pieces on the board and handle game logic.
     */
    private Board board;
    
    /**
     * An integer variable that holds count on which turn number it is
     */
    private int turnCount;
    
    private GameType gameType;
    
    
    /**
     * The initialize() method is automatically called by javafx when the controller class is first called.
     * Initializes all private data members and sets up the initial tic-tac-toe board.
     */
    public void initialize() {
        // sets the text of the version label based on the current version of the application
        versionLabel.setText(TicTacToeApplication.VERSION);
        
        turnLabel.setFont(Fonts.GAME_FONT);
        
        // adds all ui info about game pieces to a GamePiece array for ease of use
        gamePieces = new GamePiece[9];
        gamePieces[0] = new GamePiece(spot1XImage, spot1OImage, spot1, spot1Fade);
        gamePieces[1] = new GamePiece(spot2XImage, spot2OImage, spot2, spot2Fade);
        gamePieces[2] = new GamePiece(spot3XImage, spot3OImage, spot3, spot3Fade);
        gamePieces[3] = new GamePiece(spot4XImage, spot4OImage, spot4, spot4Fade);
        gamePieces[4] = new GamePiece(spot5XImage, spot5OImage, spot5, spot5Fade);
        gamePieces[5] = new GamePiece(spot6XImage, spot6OImage, spot6, spot6Fade);
        gamePieces[6] = new GamePiece(spot7XImage, spot7OImage, spot7, spot7Fade);
        gamePieces[7] = new GamePiece(spot8XImage, spot8OImage, spot8, spot8Fade);
        gamePieces[8] = new GamePiece(spot9XImage, spot9OImage, spot9, spot9Fade);
        
        // initializes the board
        board = new Board(this);
    
        // initializes the turn count
        turnCount = 1;
        turnLabel.setText("X's Turn");
    
        backButton.setFont(Fonts.GAME_FONT);
        
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
    
        backButton.setOnMouseEntered(e -> Animations.uiButtonHover(backButton, 25));
        backButton.setOnMouseExited(e -> Animations.uiButtonIdle(backButton, 20));
    }
    
    
    @Override
    public void startUI() {
        updateUI();
    
        double width = gameFade.getScene().getWidth();
        double height = gameFade.getScene().getHeight();
        gameFade.setCenterX(width / 2);
        gameFade.setCenterY(height / 2);
        gameFade.setRadius(Math.max(width, height) / 2 + 250);
        
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
        disableGameButtons();
        
        Animations.getFadeInTimeline(gameFade, 500).play();
        
        enableAvailableGameButtons();
    }
    
    
    @Override
    public void updateUI() {
        double sceneWidth = boardImage.getScene().getWidth();
        double sceneHeight = boardImage.getScene().getHeight();
    
        boardImage.setFitWidth(Math.min(sceneWidth, sceneHeight) - 200);
        backgroundPattern.setFitWidth(Math.max(sceneWidth, sceneHeight) * 2);
    
        turnLabel.setFont(new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        turnLabel.setX(sceneWidth / 2 - turnLabel.getFont().getSize() * 2.3);
        turnLabel.setY(turnLabel.getFont().getSize() * 1.5);
        
        double boardImageSize = boardImage.getFitWidth();
    
        boardImage.setX(sceneWidth / 2 - boardImageSize / 2);
        boardImage.setY(sceneHeight / 2 - boardImageSize / 2 + turnLabel.getFont().getSize() / 2);
    
        gameFade.setCenterX(sceneWidth / 2);
        gameFade.setCenterY(sceneHeight / 2);
    
        double boardX = boardImage.getX();
        double boardY = boardImage.getY();
    
        for (int y = 0; y < Board.BOARD_SIZE; ++y) {
            for (int x = 0; x < Board.BOARD_SIZE; ++x) {
                double pieceSize = boardImageSize / 3;
                
                gamePieces[x + y * 3].updatePiece(boardX + pieceSize * x, boardY + pieceSize * y,
                        pieceSize);
            }
        }
    
        backButton.setFont(
                new Font(Fonts.GAME_FONT.getFamily(), 20 * Math.min(sceneWidth, sceneHeight) / 600));
        backButton.setLayoutY(backButton.getFont().getSize() * 0.75);
        backButton.setPrefWidth(backButton.getFont().getSize() * 6);
        backButton.setPrefHeight(sceneHeight / 12);
        
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
    
    
    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
    
    
    /**
     * Disables all the game buttons on the tic-tac-toe board.
     */
    public void disableGameButtons() {
        for (GamePiece gamePiece : gamePieces)
            gamePiece.toggleGameButton(false);
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
                    gamePieces[x + y * 3].toggleGameButton(true);
    
        turnLabel.setText((turnCount % 2 == 1 ? "X's Turn" : "O's Turn"));
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
        PlayType playType = (turnCount % 2 == 1 ? PlayType.X : PlayType.O);
        
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
        GamePiece gamePiece = gamePieces[idNum - 1];
        gamePiece.setPiece(playType);
        gamePiece.showPiece(boardImage.getFitWidth(), this, gamePieces);
    
        // move onto the next turn
        ++turnCount;
    }
    
    
    public Board getBoard() {
        return board;
    }
    
    
    /**
     * The backMove() method allows the user to open welcome screen
     *
     */
    public void backMove() {
        Timeline fadeOut = Animations.getFadeOutTimeline(gameFade, 100);
        fadeOut.play();
        fadeOut.setOnFinished(e -> {
            try {
                application.startWelcomeScreen(stage);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    
    private void showWinner(GameOverInfo gameOverInfo) {
        Timeline fadeOutAnimation = Animations.getFadeOutTimeline(gameFade, 500);
        fadeOutAnimation.play();
        fadeOutAnimation.setOnFinished(e -> {
            try {
                application.startResultsScreen(stage, gameOverInfo, gameType);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }
    
    public void doGameOver(GameOverInfo gameOverInfo) {
        stage.setResizable(false);
        backButton.setDisable(true);
        
        if (gameOverInfo.getStateType() != StateType.DRAW) {
            Timeline timeline;
            
            switch (gameOverInfo.getLineType()) {
                case HORIZONTAL:
                    horizontalLine.setX(boardImage.getX());
                    horizontalLine.setY(gameOverInfo.getStartPiece().getY());
                    horizontalLine.setFitWidth(1);
                    horizontalLine.setFitHeight(gameOverInfo.getStartPiece().getHeight());
    
                    timeline = new Timeline(
                            new KeyFrame(Duration.millis(250), new KeyValue(horizontalLine.fitWidthProperty(),
                                    boardImage.getFitWidth()))
                    );
                    
                    horizontalLine.setOpacity(1);
                    
                    break;
                case VERTICAL:
                    verticalLine.setX(gameOverInfo.getStartPiece().getX());
                    verticalLine.setY(boardImage.getY());
                    verticalLine.setFitWidth(gameOverInfo.getStartPiece().getWidth());
                    verticalLine.setFitHeight(1);
    
                    timeline = new Timeline(
                            new KeyFrame(Duration.millis(250), new KeyValue(verticalLine.fitHeightProperty(),
                                    boardImage.getFitWidth()))
                    );
                    
                    verticalLine.setOpacity(1);
                    
                    break;
                case LEFT_DIAGONAL:
                    leftDiagonalLine.setX(boardImage.getX());
                    leftDiagonalLine.setY(boardImage.getY());
                    leftDiagonalLine.setFitWidth(1);
    
                    timeline = new Timeline(
                            new KeyFrame(Duration.millis(250), new KeyValue(leftDiagonalLine.fitWidthProperty(),
                                    boardImage.getFitWidth()))
                    );
                    
                    leftDiagonalLine.setOpacity(1);
                    
                    break;
                case RIGHT_DIAGONAL:
                    rightDiagonalLine.setX(boardImage.getFitWidth());
                    rightDiagonalLine.setY(boardImage.getY());
                    rightDiagonalLine.setFitWidth(1);
                    
                    timeline = new Timeline(
                            new KeyFrame(Duration.millis(250), new KeyValue(rightDiagonalLine.fitWidthProperty(),
                                    boardImage.getFitWidth())),
                            new KeyFrame(Duration.millis(250), new KeyValue(rightDiagonalLine.xProperty(),
                                    boardImage.getX()))
                    );
                    
                    rightDiagonalLine.setOpacity(1);
                    
                    break;
                default:
                    timeline = new Timeline();
                    break;
            }
    
            timeline.play();
            timeline.setOnFinished(e -> showWinner(gameOverInfo));
        } else {
            showWinner(gameOverInfo);
        }
    }
}

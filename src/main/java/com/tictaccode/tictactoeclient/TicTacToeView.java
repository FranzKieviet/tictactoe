package com.tictaccode.tictactoeclient;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Controller for the tic-tac-toe game UI that handles all interactions with elements in the UI.
 */
public class TicTacToeView extends Controller {
    /** The size of a tic-tac-toe board. */
    public static final int BOARD_SIZE = 3;
    
    /** Javafx pane element retrieved from the fxml file. */
    @FXML
    private Pane pane;
    
    /**
     * Javafx text element retrieved from the fxml file.
     */
    @FXML
    private Text versionLabel, turnLabel;
    
    /**
     * Javafx button elements retrieved from the fxml file.
     */
    @FXML
    private Button spot1, spot2, spot3, spot4, spot5, spot6, spot7, spot8, spot9, backButton;
    
    /** Javafx circle elements retrieved from the fxml file. */
    @FXML
    private Circle spot1Fade, spot2Fade, spot3Fade, spot4Fade, spot5Fade, spot6Fade, spot7Fade, spot8Fade, spot9Fade,
            gameFade;
    
    /** Javafx image view elements retrieved from the fxml file. */
    @FXML
    private ImageView boardImage, spot1XImage, spot2XImage, spot3XImage, spot4XImage, spot5XImage, spot6XImage,
            spot7XImage, spot8XImage, spot9XImage, spot1OImage, spot2OImage, spot3OImage, spot4OImage, spot5OImage,
            spot6OImage, spot7OImage, spot8OImage, spot9OImage, backgroundPattern, horizontalLine, verticalLine,
            leftDiagonalLine, rightDiagonalLine;
    
    /** Stores all the game pieces used on the game board for ease of use. */
    private GamePiece[] gamePieces;
    
    /**
     * An integer variable that holds count on which turn number it is
     */
    private int turnCount;
    
    private GameType gameType;
    
    private boolean isGameOver;
    
    private double minBackgroundSize;
    
    /**
     * The initialize() method is automatically called by javafx when the controller class is first called.
     * Initializes all private data members and sets up the initial tic-tac-toe board.
     */
    public void initialize() {
        // sets the text of the version label based on the current version of the application
        versionLabel.setText(TicTacToeApplication.VERSION);
        
        turnLabel.setFont(Fonts.GAME_FONT);
        
        isGameOver = false;
        
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
        
        // initializes the turn count
        turnCount = 1;
        
        backButton.setFont(Fonts.GAME_FONT);
        
        pane.widthProperty().addListener((observableValue, number, t1) -> updateUI());
        pane.heightProperty().addListener((observableValue, number, t1) -> updateUI());
        
        backButton.setOnMouseEntered(e -> Animations.uiButtonHover(backButton, 25));
        backButton.setOnMouseExited(e -> Animations.uiButtonIdle(backButton, 20));
    }
    
    
    @Override
    public void startUI() {
        minBackgroundSize = Math.max(backgroundPattern.getScene().getWidth(),
                backgroundPattern.getScene().getHeight()) * 2;
        
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
    }
    
    
    @Override
    public void updateUI() {
        double sceneWidth = boardImage.getScene().getWidth();
        double sceneHeight = boardImage.getScene().getHeight();
        
        boardImage.setFitWidth(Math.min(sceneWidth, sceneHeight) - 200);
        backgroundPattern.setFitWidth(Math.max(minBackgroundSize, (Math.max(sceneWidth, sceneHeight) * 2)));
        
        turnLabel.setFont(new Font(Fonts.GAME_FONT.getFamily(), 30 * Math.min(sceneWidth, sceneHeight) / 600));
        turnLabel.setY(turnLabel.getFont().getSize() * 1.5);
    
        if (gameType == GameType.LOCAL_MULTIPLAYER)
            turnLabel.setX(sceneWidth / 2 - turnLabel.getFont().getSize() * 2.3);
        else {
            if (turnLabel.getText().startsWith("Your"))
                turnLabel.setX(sceneWidth / 2 - turnLabel.getFont().getSize() * 3.1);
            else
                turnLabel.setX(sceneWidth / 2 - turnLabel.getFont().getSize() * 4.5);
        }
        
        double boardImageSize = boardImage.getFitWidth();
        
        boardImage.setX(sceneWidth / 2 - boardImageSize / 2);
        boardImage.setY(sceneHeight / 2 - boardImageSize / 2 + turnLabel.getFont().getSize() / 2);
        
        gameFade.setCenterX(sceneWidth / 2);
        gameFade.setCenterY(sceneHeight / 2);
        
        double boardX = boardImage.getX();
        double boardY = boardImage.getY();
        
        for (int y = 0; y < BOARD_SIZE; ++y) {
            for (int x = 0; x < BOARD_SIZE; ++x) {
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
    
    
    public void setPlayerTurn(boolean playerCurrentTurn) {
        if (gameType == GameType.LOCAL_MULTIPLAYER)
            turnLabel.setText((turnCount % 2 == 1 ? "X's Turn" : "O's Turn"));
        else {
            turnLabel.setText((playerCurrentTurn ? "Your Turn" : "Opponent's Turn"));
            updateUI();
        }
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
        // traverses through the boardSpots array and enables all buttons in the spot that has nothing in it
        for (int y = 0; y < BOARD_SIZE; ++y)
            for (int x = 0; x < BOARD_SIZE; ++x)
                if (gamePieces[x + y * 3].getPlayType() == PlayType.NOTHING)
                    gamePieces[x + y * 3].toggleGameButton(true);
    }
    
    
    public Text getTurnLabel() {
        return turnLabel;
    }
    
    
    public int getTurnCount() {
        return turnCount;
    }
    
    public boolean isGameOver() {
        return isGameOver;
    }
    
    
    public void otherPlaceMove(String moveInfo, boolean hasLost) {
        // sets what type of piece the user is playing based on the turn count
        PlayType playType = (turnCount % 2 == 1 ? PlayType.X : PlayType.O);
        
        int y = Integer.parseInt(moveInfo.substring(2, 3));
        int x = Integer.parseInt(moveInfo.substring(4));
        
        // move onto the next turn
        ++turnCount;
        
        // displays the move to the user
        GamePiece gamePiece = gamePieces[x + y * 3];
        gamePiece.setPiece(playType);
        gamePiece.showPiece(boardImage.getFitWidth(), this, gameType, false, hasLost);
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
            if (x < BOARD_SIZE - 1)
                x++;
            else {
                y++;
                x = 0;
            }
        }
        
        // move onto the next turn
        ++turnCount;
        
        // displays the move to the user
        GamePiece gamePiece = gamePieces[idNum - 1];
        gamePiece.setPiece(playType);
        gamePiece.showPiece(boardImage.getFitWidth(), this, gameType, true, false);
        
        application.sendMove((playType == PlayType.X ? "X" : "O") + " " + y + " " + x);
    }
    
    
    /**
     * The backMove() method allows the user to open welcome screen
     *
     */
    public void backMove() {
        backButton.setDisable(true);
        disableGameButtons();
        
        Timeline fadeOut = Animations.getFadeOutTimeline(gameFade, 100);
        application.leaveGame();
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
    
    
    private void showWinner(StateType stateType, String whoWon) {
        Timeline fadeOutAnimation = Animations.getFadeOutTimeline(gameFade, 500);
        fadeOutAnimation.setOnFinished(e -> {
            try {
                application.startResultsScreen(stage, stateType, gameType, whoWon);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        fadeOutAnimation.play();
    }
    
    public void doGameOver(StateType stateType, String whoWon) {
        isGameOver = true;
        stage.setResizable(false);
        backButton.setDisable(true);
        
        if (stateType != StateType.DRAW) {
            Timeline timeline;
            
            GameOverInfo goi = checkState();
            
            switch (goi.getLineType()) {
                case HORIZONTAL:
                    horizontalLine.setX(boardImage.getX());
                    horizontalLine.setY(goi.getStartPiece().getY());
                    horizontalLine.setFitWidth(1);
                    horizontalLine.setFitHeight(goi.getStartPiece().getHeight());
                    
                    timeline = new Timeline(
                            new KeyFrame(Duration.millis(250), new KeyValue(horizontalLine.fitWidthProperty(),
                                    boardImage.getFitWidth()))
                    );
                    
                    horizontalLine.setOpacity(1);
                    
                    break;
                case VERTICAL:
                    verticalLine.setX(goi.getStartPiece().getX());
                    verticalLine.setY(boardImage.getY());
                    verticalLine.setFitWidth(goi.getStartPiece().getWidth());
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
    
            timeline.setOnFinished(e -> showWinner(stateType, whoWon));
            timeline.play();
        } else
            showWinner(stateType, whoWon);
    }
    
    public GameOverInfo checkState() {
        //row control if complete X or O
        for (int y = 0; y < BOARD_SIZE; ++y) {
            if (gamePieces[3 * y].getPlayType() == gamePieces[3 * y + 1].getPlayType() &&
                    gamePieces[3 * y + 1].getPlayType() == gamePieces[3 * y + 2].getPlayType()) {
                if (gamePieces[3 * y].getPlayType() == PlayType.X)
                    return new GameOverInfo(StateType.X_WINNER, LineType.HORIZONTAL, gamePieces[y * 3],
                            gamePieces[2 + y * 3]);
                else if (gamePieces[3 * y].getPlayType() == PlayType.O)
                    return new GameOverInfo(StateType.O_WINNER, LineType.HORIZONTAL, gamePieces[y * 3],
                            gamePieces[2 + y * 3]);
            }
        }
        //column control if complete X or O
        for (int x = 0; x < BOARD_SIZE; ++x) {
            if (gamePieces[x].getPlayType() == gamePieces[x + 3].getPlayType() &&
                    gamePieces[x + 3].getPlayType() == gamePieces[x + 6].getPlayType()) {
                if (gamePieces[x].getPlayType() == PlayType.X)
                    return new GameOverInfo(StateType.X_WINNER, LineType.VERTICAL, gamePieces[x], gamePieces[x + 6]);
                else if (gamePieces[x].getPlayType() == PlayType.O)
                    return new GameOverInfo(StateType.O_WINNER, LineType.VERTICAL, gamePieces[x], gamePieces[x + 6]);
            }
        }
        
        //left diagonal control if complete X or O
        if (gamePieces[0].getPlayType() == gamePieces[4].getPlayType() &&
                gamePieces[4].getPlayType() == gamePieces[8].getPlayType()) {
            if (gamePieces[4].getPlayType() == PlayType.X)
                return new GameOverInfo(StateType.X_WINNER, LineType.LEFT_DIAGONAL, gamePieces[0], gamePieces[8]);
            else if (gamePieces[4].getPlayType() == PlayType.O)
                return new GameOverInfo(StateType.O_WINNER, LineType.LEFT_DIAGONAL, gamePieces[0], gamePieces[8]);
        }
        
        //right diagonal control if complete X or O
        if (gamePieces[6].getPlayType() == gamePieces[4].getPlayType() &&
                gamePieces[4].getPlayType() == gamePieces[2].getPlayType()) {
            if (gamePieces[4].getPlayType() == PlayType.X)
                return new GameOverInfo(StateType.X_WINNER, LineType.RIGHT_DIAGONAL, gamePieces[2], gamePieces[6]);
            else if (gamePieces[4].getPlayType() == PlayType.O)
                return new GameOverInfo(StateType.O_WINNER, LineType.RIGHT_DIAGONAL, gamePieces[2], gamePieces[6]);
        }
        
        return new GameOverInfo((isComplete() ? StateType.DRAW : StateType.CONTINUE), LineType.NOTHING);
    }
    
    private boolean isComplete() {
        for (int y = 0; y < BOARD_SIZE; ++y)
            for (int x = 0; x < BOARD_SIZE; ++x)
                if (gamePieces[x + y * 3].getPlayType() == PlayType.NOTHING)
                    return false;
        return true;
    }
}

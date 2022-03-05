package com.tictaccode.tictactoe;

import javafx.util.Pair;

public class PlayerAI {

    private PlayType playTypeAI;
    private PlayType playTypeOpponent;
    private StateType winAI;
    private StateType winOpponent;
    private TicTacToeController controller;

    public PlayerAI(TicTacToeController controller, PlayType playType) {
        this.controller = controller;
        this.playTypeAI = playType;

        if(playTypeAI == PlayType.O) {
            this.playTypeOpponent = PlayType.X;
            this.winAI = StateType.O_WINNER;
            this.winOpponent = StateType.X_WINNER;
        } else {
            this.playTypeOpponent = PlayType.O;
            this.winAI = StateType.X_WINNER;
            this.winOpponent = StateType.O_WINNER;
        }
    }


    /**
     * The placeAI() method plays the most optimal move with the given Board and PlayType
     *
     */
    public Pair<Integer, Integer> determineMove(Board board, GamePiece[] gamePieces) {
        int maxEval = Integer.MIN_VALUE;
        int optimalX = 0;
        int optimalY = 0;

        //Go through all possible moves
        for (int i = 0; i < board.BOARD_SIZE; ++i) {
            for (int j = 0; j < board.BOARD_SIZE; ++j) {
                if (board.getBoardSpots()[i][j] == PlayType.NOTHING) {

                    //Play move on new grid and pass it into child
                    board.setBoardPosition(j, i, playTypeAI);

                    int eval = minimax(board, gamePieces, Integer.MAX_VALUE, Integer.MIN_VALUE, false);

                    //Undo move
                    board.setBoardPosition(j, i, PlayType.NOTHING);

                    //If this last turn is currently most optimal, set it as so
                    if (eval > maxEval) {
                        maxEval = eval;
                        optimalX = j;
                        optimalY = i;
                    }
                }
            }
        }

        //Return most optimal move
        Pair<Integer, Integer> pair = new Pair<>(optimalX, optimalY);
        return pair;
    }

    /**
     * The minimax function scores how optimal a move is with the given board
     *
     */
    public int minimax(Board board, GamePiece[] gamePieces, int scoreX, int scoreO, boolean maximizingPlayerTurn) {

        //Determine if game is over
        if (board.checkState(gamePieces).getStateType() == winOpponent) return -1;        //-1 for loss
        if (board.checkState(gamePieces).getStateType() == winAI) return 1;         // +1 for win
        if (board.checkState(gamePieces).getStateType() == StateType.DRAW) return 0;       // 0 for tiw

        //If it is maximizing player's turn, player O
        if(maximizingPlayerTurn) {
            int maxEval = Integer.MIN_VALUE;

            //Go through all possible moves player O can make
            for(int y = 0; y < board.BOARD_SIZE; ++y) {
                for (int x = 0; x < board.BOARD_SIZE; ++x) {
                    if(board.getBoardSpots()[y][x] == PlayType.NOTHING) {

                        //Play move on the board
                        board.setBoardPosition(x, y , playTypeAI);

                        //Score the move
                        int eval = minimax(board, gamePieces, scoreX, scoreO, false);

                        //Undo the move
                        board.setBoardPosition(x, y , PlayType.NOTHING);;

                        //Evaluate if calculated score is the best score
                        maxEval = Math.max(scoreO, eval);
                        scoreO = Math.max(scoreO, eval);
                    }
                    if(scoreO >= scoreX) break;     //Can we break early
                }
                if(scoreO >= scoreX) break;         //Can we break early

            }
            return maxEval;
        }
        //If it is the other player's turn, player X
        else {
            int minEval = Integer.MAX_VALUE;

            //Go through all possible moves player X can make
            for(int y = 0; y < board.BOARD_SIZE; ++y) {
                for(int x = 0; x < board.BOARD_SIZE; ++x) {
                    if(board.getBoardSpots()[y][x] == PlayType.NOTHING) {

                        //Play move on the board
                        board.setBoardPosition(x, y, playTypeOpponent);

                        //Score the move
                        int eval = minimax(board, gamePieces, scoreX, scoreO, true);

                        //Undo the move
                        board.setBoardPosition(x, y, PlayType.NOTHING);;

                        //Evaluate if calculated score is the best score
                        minEval = Math.min(minEval, eval);
                        scoreX = Math.min(minEval, scoreX);
                    }
                    if(scoreO >= scoreX) break;     //Can we break early
                }
                if(scoreO >= scoreX) break;         //Can we break early

            }
            return minEval;
        }
    }
}

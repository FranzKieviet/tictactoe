package com.tictaccode.tictactoegamecontroller;

public class Game {
    private GameController gameController;
    
    private long gameID, client1ID, client2ID;
    private boolean isP1Turn, c1TryAgain, c2TryAgain;
    private Board board;
    
    public Game(GameController gameController, long gameID, long clientID) {
        this.gameController = gameController;
        this.gameID = gameID;
        this.client1ID = clientID;
        this.client2ID = clientID;
        
        createGame();
        
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client1ID, "JoinChannel");

        this.gameController.sendGameInfo(Long.toString(this.client1ID), "GameCreated 3 " + this.gameID);
    }
    
    public Game(GameController gameController, long gameID, long client1ID, long client2ID) {
        this.gameController = gameController;
        this.gameID = gameID;
        this.client1ID = client1ID;
        this.client2ID = client2ID;
        
        createGame();
    
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client1ID, "JoinChannel");
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client2ID, "JoinChannel");
        
        this.gameController.sendGameInfo(Long.toString(this.client1ID), "GameCreated 1 " + this.gameID);
        this.gameController.sendGameInfo(Long.toString(this.client2ID), "GameCreated 2 " + this.gameID);
    }
    
    public void createGame() {
        c1TryAgain = false;
        c2TryAgain = false;
        isP1Turn = true;
        board = new Board();
    }

    public Board getBoard() {return board;}
    
    public void doMove(int x, int y, PlayType playType, long clientID) {
        if ((isP1Turn && clientID == client1ID) || (!isP1Turn && clientID == client2ID)) {
            board.setBoardPosition(x, y, playType);
            
            if (client1ID == client2ID) {
                switch (board.checkState()) {
                    case X_WINNER:
                        gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Win X");
                        break;
                    case O_WINNER:
                        gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Win O");
                        break;
                    case DRAW:
                        gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Tie");
                        break;
                    default:
                        gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Continue");
                }
            }
            else {
                switch (board.checkState()) {
                    case X_WINNER:
                        gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Win");
                        gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "Lose "+playType+" "+y+" "+x);
                        break;
                    case O_WINNER:
                        gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Lose "+playType+" "+y+" "+x);
                        gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "Win");
                        break;
                    case DRAW:
                        gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Tie");
                        gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "Tie");
                        break;
                    default:
                        gameController.sendGameInfo("game/"+gameID+"/"+(isP1Turn ? client2ID : client1ID),
                                playType+" "+y+" "+x);
                }
    
                isP1Turn = !isP1Turn;
            }
        }
    }
    
    public void willTryAgain(long clientID) {
        if (client1ID == client2ID) {
            createGame();
            return;
        }
        else if (clientID == client1ID) {
            c1TryAgain = true;
            gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "TryAgain");
        }
        else if (clientID == client2ID) {
            c2TryAgain = true;
            gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "TryAgain");
        }
        
        if (c1TryAgain && c2TryAgain)
            createGame();
    }
    
    public void leaveGame(long clientID) {
        if (client1ID != client2ID) {
            if (clientID == client1ID)
                gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "LeaveGame");
            else if (clientID == client2ID)
                gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "LeaveGame");
        }
        
        closeGame();
    }
    
    private void closeGame() {
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client1ID, "ExitChannel");
        
        if (client1ID != client2ID)
            this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client2ID, "ExitChannel");
        
        gameController.gameFinished(gameID);
    }
}

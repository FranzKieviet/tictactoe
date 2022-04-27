package com.tictaccode.tictactoegamecontroller;

public class Game {
    private GameController gameController;
    
    private long gameID, client1ID, client2ID;
    private boolean isP1Turn;
    private Board board;
    
    public Game(GameController gameController, long gameID, long client1ID, long client2ID) {
        this.gameController = gameController;
        this.gameID = gameID;
        this.client1ID = client1ID;
        this.client2ID = client2ID;
        
        isP1Turn = true;
        board = new Board();
    
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client1ID, "JoinChannel");
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client2ID, "JoinChannel");
        
        this.gameController.sendGameInfo(Long.toString(this.client1ID), "GameCreated 1 " + this.gameID);
        this.gameController.sendGameInfo(Long.toString(this.client2ID), "GameCreated 2 " + this.gameID);
    }
    
    public void doMove(int x, int y, PlayType playType, long clientID) {
        if ((isP1Turn && clientID == client1ID) || (!isP1Turn && clientID == client2ID)) {
            board.setBoardPosition(x, y, playType);
            
            switch (board.checkState()) {
                case X_WINNER:
                    gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Win");
                    gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "Lose "+playType+" "+y+" "+x);
                    closeGame();
                    break;
                case O_WINNER:
                    gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Lose "+playType+" "+y+" "+x);
                    gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "Win");
                    closeGame();
                    break;
                case DRAW:
                    gameController.sendGameInfo("game/"+gameID+"/"+client1ID, "Tie");
                    gameController.sendGameInfo("game/"+gameID+"/"+client2ID, "Tie");
                    closeGame();
                    break;
                default:
                    gameController.sendGameInfo("game/"+gameID+"/"+(isP1Turn ? client2ID : client1ID),
                            playType+" "+y+" "+x);
            }
    
            isP1Turn = !isP1Turn;
        }
    }
    
    private void closeGame() {
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client1ID, "ExitChannel");
        this.gameController.sendGameInfo("game/"+this.gameID+"/"+this.client2ID, "ExitChannel");
        gameController.gameFinished(gameID);
    }
    
    public long getGameID() {
        return gameID;
    }
    
    public long getClient1ID() {
        return client1ID;
    }
    
    public long getClient2ID() {
        return client2ID;
    }
    
    public void setClient1ID(long client1ID) {
        this.client1ID = client1ID;
    }
    
    public void setClient2ID(long client2ID) {
        this.client2ID = client2ID;
    }
}

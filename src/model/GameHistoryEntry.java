package model;

import java.sql.Timestamp;

public class GameHistoryEntry {
    private int userId;
    private String opponentName;
    private String gameType;
    private String gameStatus;
    private Timestamp timestamp;
    public GameHistoryEntry() {
        // no-arg constructor
    }


    public GameHistoryEntry(String opponentName, String gameType, String gameStatus, Timestamp timestamp) {
        this.opponentName = opponentName;
        this.gameType = gameType;
        this.gameStatus = gameStatus;
        this.timestamp = timestamp;
    }

    public void setOpponetName(String opponentName){
        this.opponentName = opponentName;
    }
    public void setGameType(String gameType){
        this.gameType = gameType;
    }
    public void setGameStatus(String gameStatus){
        this.gameStatus = gameStatus;
    }
    public void setTimestamp(Timestamp timestamp){
        this.timestamp = timestamp;
    }
    public void setUserID(int userId){
        this.userId = userId;
    }
    
    public int getUserID() {return userId; }
    public String getOpponentName() { return opponentName; }
    public String getGameType() { return gameType; }
    public String getGameStatus() { return gameStatus; }
    public Timestamp getTimestamp() { return timestamp; }
}

package model;

import java.sql.Timestamp;

public class GameHistoryEntry {
    private String opponentName;
    private String gameType;
    private String gameStatus;
    private Timestamp timestamp;

    public GameHistoryEntry(String opponentName, String gameType, String gameStatus, Timestamp timestamp) {
        this.opponentName = opponentName;
        this.gameType = gameType;
        this.gameStatus = gameStatus;
        this.timestamp = timestamp;
    }

    public String getOpponentName() { return opponentName; }
    public String getGameType() { return gameType; }
    public String getGameStatus() { return gameStatus; }
    public Timestamp getTimestamp() { return timestamp; }
}

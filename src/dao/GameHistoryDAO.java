package dao;

import model.GameHistoryEntry;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameHistoryDAO {

    public List<GameHistoryEntry> getHistoryByUserId(int userId) {
        List<GameHistoryEntry> history = new ArrayList<>();
        String sql = "SELECT * FROM games WHERE user_id = ? ORDER BY timestamp DESC";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String opponent = rs.getString("opponent_name");
                String type = rs.getString("type");
                String status = rs.getString("game_status");
                Timestamp ts = rs.getTimestamp("timestamp");

                history.add(new GameHistoryEntry(opponent, type, status, ts));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }
}

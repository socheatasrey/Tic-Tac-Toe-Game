package dao;

import model.User;
import util.DBConnection;

import java.sql.*;
import java.util.Optional;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users (username, password, profile_path, nationality) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword()); // assume already hashed
            ps.setString(3, user.getPhotoPath());
            ps.setString(4, user.getNationality());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    user.setId(generatedId); // ✅ Now the user has an ID!
                }
                return true;
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.err.println("Username already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // ✅ Login a user and return User object if credentials match
    public Optional<User> loginUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password); // already hashed if needed

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("user_id");
                String photoPath = rs.getString("profile_path");
                String nationality = rs.getString("nationality");

                User user = new User(id, username, password, photoPath);
                user.setNationality(nationality);

                return Optional.of(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    // ✅ Check if username exists
    public boolean usernameExists(String username) {
        String sql = "SELECT user_id FROM users WHERE username = ?";

        try (Connection conn = DBConnection.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
            return true; // safer to block duplicate just in case
        }
    }

    public void updateUsername(int userId, String newName) {
        try (Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET username = ? WHERE user_id = ?")) {
            ps.setString(1, newName);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateNationality(int userId, String newNat) {
        try (Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET nationality = ? WHERE user_id = ?")) {
            ps.setString(1, newNat);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updatePhotoPath(int userId, String path) {
        try (Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement("UPDATE users SET profile_path = ? WHERE user_id = ?")) {
            ps.setString(1, path);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.connect();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("profile_path")
                );
                user.setNationality(rs.getString("nationality"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}

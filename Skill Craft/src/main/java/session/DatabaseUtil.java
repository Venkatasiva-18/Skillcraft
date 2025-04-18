package session;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {

    // Deletes records older than 2 hours
    public static void deleteExpiredSessions() {
        String deleteSQL = "DELETE FROM sessions WHERE created_at < NOW() - INTERVAL 2 HOUR";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteSQL)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieves records within the last 2 hours
    public static List<Session> getActiveSessions() {
        List<Session> sessions = new ArrayList<>();
        String selectSQL = "SELECT * FROM sessions WHERE created_at >= NOW() - INTERVAL 2 HOUR";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(selectSQL);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Session session = new Session();
                session.setId(rs.getInt("id"));
                session.setName(rs.getString("name"));
                session.setEmail(rs.getString("email"));
                session.setSubject(rs.getString("subject"));
                session.setLink(rs.getString("link"));
                session.setCreatedAt(rs.getTimestamp("created_at"));
                sessions.add(session);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessions;
    }

    // Your existing database connection method
    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/skillcraft_db";
        String username = "root";
        String password = "12345";
        return DriverManager.getConnection(url, username, password);
    }
}

package session;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/DisplaySession")
public class DisplaySession extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delete expired sessions (older than 2 hours)
        DatabaseUtil.deleteExpiredSessions();

        // Retrieve active sessions (within the last 2 hours)
        List<Session> sessions = DatabaseUtil.getActiveSessions();

        // Set sessions as request attribute for JSP
        request.setAttribute("sessions", sessions);

        // Forward to DisplaySessions.jsp
        request.getRequestDispatcher("/displaySessions.jsp").forward(request, response);
    }
}

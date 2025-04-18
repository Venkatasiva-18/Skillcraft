package Instructor;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/inslogin")
public class InstructorLoginServlet extends HttpServlet {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/skillcraft_db";
    private static final String DB_USER = "root"; 
    private static final String DB_PASSWORD = "12345"; 

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            showAlert(response, "Please fill in all fields.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "SELECT * FROM instructor WHERE username = ? AND password = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, username);
                statement.setString(2, password);

                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    response.sendRedirect("ins-contents.html");
                } else {
                    showAlert(response, "Invalid username or password.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(response, "Database connection error.");
        }
    }

    private void showAlert(HttpServletResponse response, String message) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<script type='text/javascript'>");
        out.println("alert('" + message + "');");
        out.println("window.location = 'login-home.html';");
        out.println("</script>");
    }
}

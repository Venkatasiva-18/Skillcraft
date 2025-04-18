package register;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class StudentLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Get the login form data
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database credentials (update as per your setup)
        String jdbcURL = "jdbc:mysql://localhost:3306/skillcraft_db";
        String dbUser = "root";
        String dbPassword = "12345";

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");

        // Initialize database variables
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Load the MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a database connection
            conn = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // SQL query to check the login credentials
            String sql = "SELECT * FROM learner WHERE username = ? AND password = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);

            // Execute the query
            rs = stmt.executeQuery();

            // Check if user credentials are valid
            if (rs.next()) {
                // Login success, redirect to the main content page
                response.sendRedirect("contents.html"); // content.jsp should be the main page
            } else {
                // Invalid credentials, show alert on the same page
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Invalid Username or Password');");
                out.println("location='login-home.html';");
                out.println("</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the database resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

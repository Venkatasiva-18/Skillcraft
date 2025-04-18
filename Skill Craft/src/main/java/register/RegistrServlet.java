package register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegistrServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String name = request.getParameter("name");
        String email = request.getParameter("mail");
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Connect to the database
            try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/skillcraft_db", "root", "12345")) {
                // Prepare SQL statement
                String sql = "INSERT INTO learner (name, email, username, password) VALUES (?, ?, ?, ?)";
                try (PreparedStatement statement = connection.prepareStatement(sql)) {
                    statement.setString(1, name);
                    statement.setString(2, email);
                    statement.setString(3, username);
                    statement.setString(4, password); // You may want to hash the password

                    // Execute update
                    int rows = statement.executeUpdate();
                    if (rows > 0) {
                    	out.println("<script type=\"text/javascript\">");
                        out.println("alert('Registration successful!');");
                        
                        out.println("location='login-home.html';");  // Redirect to login page
                        out.println("</script>");
                    } else {
                        out.println("<h1>Registration failed!</h1>");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }
}

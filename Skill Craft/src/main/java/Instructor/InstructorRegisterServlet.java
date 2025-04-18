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
import java.sql.SQLException;

@WebServlet("/insreg")
public class InstructorRegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Reading form parameters
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String qualification = request.getParameter("qualification");
        String skills = request.getParameter("skills");
        String programmingLanguages = request.getParameter("programming-languages");

        // Database connection details
        String jdbcURL = "jdbc:mysql://localhost:3306/skillcraft_db";
        String dbUser = "root";
        String dbPassword = "12345";  // Replace with your actual MySQL password

        // Set up response type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish database connection
            connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);

            // Insert instructor details into the database
            String sql = "INSERT INTO instructor (name, email, username, password, qualification, skills, programming_languages) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, username);
            preparedStatement.setString(4, password);
            preparedStatement.setString(5, qualification);
            preparedStatement.setString(6, skills);
            preparedStatement.setString(7, programmingLanguages);

            // Execute the query
            int result = preparedStatement.executeUpdate();

            // Provide feedback to the user
            if (result > 0) {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Registration successful!');");
                out.println("location='login-home.html';");  // Redirect to login page
                out.println("</script>");
            } else {
                out.println("<script type=\"text/javascript\">");
                out.println("alert('Registration failed. Please try again.');");
                out.println("location='reg-home.html';");  // Redirect to registration form
                out.println("</script>");
            }

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("<script type=\"text/javascript\">");
            out.println("alert('An error occurred. Please try again later.');");
            out.println("location='reg-home.html';");
            out.println("</script>");
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

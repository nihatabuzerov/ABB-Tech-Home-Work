package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.example.repository.UserRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Login</h2>");
        out.println("<form method='post'>");
        out.println("Username: <input type='text' name='username' required><br><br>");
        out.println("Password: <input type='password' name='password' required><br><br>");
        out.println("<button type='submit'>Login</button>");
        out.println("</form>");
        out.println("<p>Credentials: admin/admin123 or user/user123</p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Optional<User> user = UserRepository.authenticate(username, password);

        if (user.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user.get());

            Cookie authCookie = new Cookie("AUTH_SESSION", session.getId());
            authCookie.setMaxAge(30 * 60);
            authCookie.setPath("/");
            response.addCookie(authCookie);

            response.sendRedirect("dashboard");
        } else {
            response.setContentType("text/html");
            response.getWriter().println("<html><body>");
            response.getWriter().println("<h3>Invalid credentials</h3>");
            response.getWriter().println("<a href='login'>Try again</a>");
            response.getWriter().println("</body></html>");
        }
    }
}
package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet (
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        out.println("<html><body>");
        out.println("<h2>Dashboard</h2>");
        out.println("<p>Welcome, " + user.getUsername() + "!</p>");
        out.println("<p>User ID: " + user.getId() + "</p>");
        out.println("<p>Session ID: " + session.getId() + "</p>");
        out.println("<br><a href='logout'>Logout</a>");
        out.println("</body></html>");
    }
}
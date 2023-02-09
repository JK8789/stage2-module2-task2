package com.example.servlet;

import com.example.Users;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object user = session.getAttribute("user");
        if (user == null) {

            resp.sendRedirect("/login.jsp");
        } else {
            resp.sendRedirect("/user/hello.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Users users = Users.getInstance();
        List<String> allUsers = users.getUsers();
        boolean rightLogin = allUsers.contains(login);
        boolean rightPassword = (password != null) && (!password.trim().isEmpty());

        HttpSession session = req.getSession();
        if (rightLogin && rightPassword){
            session.setAttribute("user", login);
            try {
                resp.sendRedirect("/user/hello.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}

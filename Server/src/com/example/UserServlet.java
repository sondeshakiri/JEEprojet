package com.example;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/userServlet")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private UserDAO userDAO;

    public void init() {
        try {
            // Chargez le pilote JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Établissez une connexion à la base de données (assurez-vous de remplacer les détails appropriés)
            String jdbcUrl = "jdbc:mysql://localhost:3306/DataBase";
            String username = "root";
            String password = "14507329";
            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

            // Initialisez votre UserDAO avec la connexion à la base de données
            userDAO = new UserDAO(connection);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<User> userList = userDAO.getAllUsers();

            // Stocker la liste des utilisateurs dans la session
            HttpSession session = request.getSession();
            session.setAttribute("userList", userList);

            // Redirection vers la page d'affichage des utilisateurs
            response.sendRedirect("displayUsers.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de la récupération des utilisateurs.");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        User newUser = new User(username, password);

        try {
            userDAO.addUser(newUser);
            response.sendRedirect("index.html"); // Redirection après l'ajout
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Erreur lors de l'ajout de l'utilisateur.");
        }
    }

    public void destroy() {
        // Nettoyage des ressources
        try {
            if (userDAO != null) {
                userDAO.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

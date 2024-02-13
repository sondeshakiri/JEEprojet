package com.example;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class SessionFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialisation du filtre
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Récupérer la session existante ou en créer une nouvelle
        HttpSession session = httpRequest.getSession(true);

        // Exemple de vérification d'un attribut de session
        if (session.getAttribute("user") == null) {
            // Si l'utilisateur n'est pas connecté, redirigez-le vers une page de connexion
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
        } else {
            // Si l'utilisateur est connecté, passez la requête au filtre suivant dans la chaîne
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        // Nettoyage du filtre
    }
}

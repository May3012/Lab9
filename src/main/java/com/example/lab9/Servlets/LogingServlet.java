package com.example.lab9.Servlets;

import com.example.lab9.Beans.Usuario;
import com.example.lab9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "LogingServlet", value = {"/LogingServlet", ""})
public class LogingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("loginForm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        System.out.println("username: " + username + " | password: " + password);

        UsuarioDao usuarioDao = new UsuarioDao();
        if (usuarioDao.validarUsuarioPassword(username, password)) {
            System.out.println("Usuario y contraseña válidos");
            Usuario usuario = usuarioDao.obtenerUsuario("ola2@pucp.edu.pe");
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("usuarioLogueado", usuario);

            // Redirigir a la página correspondiente según el tipo de cuenta
            if ("Decano".equals(usuario.getRol().getNameRol())) {
                response.sendRedirect(request.getContextPath() + "/DocentesServlet");
            } else if ("Docente".equals(usuario.getRol().getNameRol())) {
                response.sendRedirect(request.getContextPath() + "/NotasYEvaluacionesServlet");
            } else {
                // Otros tipos de cuenta o redirección por defecto
                response.sendRedirect(request.getContextPath() + "/LogingServlet");
            }
        } else {
            System.out.println("Usuario o contraseña incorrectos");
            request.setAttribute("err", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("loginForm.jsp").forward(request, response);
        }
    }
}

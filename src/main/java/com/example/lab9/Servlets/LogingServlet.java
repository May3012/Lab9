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
        String username = request.getParameter("email");
        String password = request.getParameter("password");
        System.out.println("username: " + username + " | password: " + password);

        UsuarioDao usuarioDao = new UsuarioDao();
        if (usuarioDao.validarUsuarioPassword(username, password)) {
            System.out.println("Usuario y contraseña válidos");
            Usuario usuario = usuarioDao.obtenerUsuario(username);
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("usuarioLogueado", usuario);

            if ("Decano".equals(usuario.getRol().getNameRol())) {
                response.sendRedirect(request.getContextPath() + "/DocentesServlet");
            } else if ("Docente".equals(usuario.getRol().getNameRol())) {
                response.sendRedirect(request.getContextPath() + "/NotasYEvaluacionesServlet");
            } else {
                response.sendRedirect(request.getContextPath() + "/LogingServlet");
            }
        } else {
            System.out.println("Usuario o contraseña incorrectos");
            request.setAttribute("err", "Usuario o contraseña incorrectos");
            request.getRequestDispatcher("loginForm.jsp").forward(request, response);
        }
    }
}

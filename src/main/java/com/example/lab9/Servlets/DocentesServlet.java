package com.example.lab9.Servlets;

import com.example.lab9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "DocentesServlet", value = "/DocentesServlet")
public class DocentesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "listar" : request.getParameter("action");

        RequestDispatcher view;
        UsuarioDao docenteDao = new UsuarioDao();

        switch (action) {
            case "listar":
                // Obtener la lista de docentes según las condiciones dadas
                ArrayList<Docente> listaDocentes = docenteDao.listarDocentes(idDecano); // Reemplaza idDecano con el valor correcto
                request.setAttribute("listaDocentes", listaDocentes);

                // Muestra la vista para listar docentes
                view = request.getRequestDispatcher("ruta_de_tu_vista.jsp"); // Reemplaza con la ruta correcta de tu vista
                view.forward(request, response);
                break;
            case "agregar":
                // Muestra la vista para agregar docentes
                view = request.getRequestDispatcher("ruta_de_tu_vista.jsp"); // Reemplaza con la ruta correcta de tu vista
                view.forward(request, response);
                break;
            // Agrega otros casos según sea necesario (editar, eliminar, etc.)
            default:
                response.sendRedirect("DocentesServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "registrar" : request.getParameter("action");
        DocenteDao docenteDao = new DocenteDao();

        try {
            switch (action) {
                case "registrar":
                    // Obtener parámetros del formulario
                    String nombre = request.getParameter("nombre");
                    String correo = request.getParameter("correo");
                    String password = request.getParameter("password");

                    // Registrar el docente
                    docenteDao.registrarDocente(nombre, correo, password);
                    response.sendRedirect("DocentesServlet?action=listar");
                    break;
                case "eliminar":
                    // Obtener el ID del docente a eliminar desde el formulario
                    int idDocenteEliminar = Integer.parseInt(request.getParameter("idDocente"));

                    // Eliminar el docente
                    docenteDao.eliminarDocente(idDocenteEliminar);
                    response.sendRedirect("DocentesServlet?action=listar");
                    break;
                // Agrega otros casos según sea necesario (editar, etc.)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error en la base de datos.");
        }
    }
    }
}

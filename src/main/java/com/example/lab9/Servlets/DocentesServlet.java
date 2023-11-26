package com.example.lab9.Servlets;

import com.example.lab9.Beans.Usuario;
import com.example.lab9.Daos.DocentesDao;
import com.example.lab9.Daos.UsuarioDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "DocentesServlet", value = "/DocentesServlet")
public class DocentesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "listar" : request.getParameter("action");

        RequestDispatcher view;
        DocentesDao docenteDao = new DocentesDao();
        HttpSession httpSession = request.getSession();
        Usuario user = (Usuario) httpSession.getAttribute("usuarioLogueado");

        if (user.getRol().getNameRol().equals("Decano")) {
            switch (action) {
                case "listar":
                    ArrayList<Usuario> listaDocentes = docenteDao.listarDocentes(user.getIdUsuario());
                    request.setAttribute("listaDocentes", listaDocentes);
                    view = request.getRequestDispatcher("Docentes/lista.jsp");
                    view.forward(request, response);
                    break;
                case "agregar":
                    view = request.getRequestDispatcher("Docentes/FormularioNuevo.jsp");
                    view.forward(request, response);
                    break;
                case "editar":
                    int idDocente = Integer.parseInt(request.getParameter("id"));
                    Usuario docente = docenteDao.obtenerDocentePorId(idDocente);
                    request.setAttribute("usuario",docente);
                    view = request.getRequestDispatcher("Docentes/FormularioEditar.jsp");
                    view.forward(request, response);

                    break;
                case "borrar":
                    int idDocenteEliminar = Integer.parseInt(request.getParameter("id"));
                    docenteDao.eliminarDocente(idDocenteEliminar);
                    response.sendRedirect("DocentesServlet?action=listar");
            }
        }
    }

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "registrar" : request.getParameter("action");
        DocentesDao docenteDao = new DocentesDao();
       HttpSession httpSession = request.getSession();
       Usuario user = (Usuario) httpSession.getAttribute("usuarioLogueado");
       switch (action) {
           case "registrar":
                String name = request.getParameter("first_name");
                String correo = request.getParameter("email");
                String contra = request.getParameter("password");
                docenteDao.registrarDocente(name,correo,contra);
               response.sendRedirect("DocentesServlet?msg=Creado exitosamente");
               break;

           case "editar":
               String nuevoNombre = request.getParameter("nombre");
               String idDocenteParam = request.getParameter("docente_id");

               if (idDocenteParam != null && !idDocenteParam.isEmpty()) {
                   int idDocente = Integer.parseInt(idDocenteParam);
                   if (nuevoNombre != null) {
                       docenteDao.editarNombreDocente(idDocente, nuevoNombre);
                       response.sendRedirect("DocentesServlet?action=listar");
                   } else {
                       response.sendRedirect("DocentesServlet?action=listar");
                   }
               } else {
                   response.sendRedirect("DocentesServlet?action=listar");
               }
               break;
       }

    }
}


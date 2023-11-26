package com.example.lab9.Servlets;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.Usuario;
import com.example.lab9.Daos.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "CursoServlet", value = "/CursoServlet")
public class CursoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");
        RequestDispatcher view;
        CursoDao cursoDao = new CursoDao();
        DocentesDao dDao = new DocentesDao();
        HttpSession httpSession = request.getSession();
        Usuario user  = (Usuario) httpSession.getAttribute("usuarioLogueado");

        if (user.getRol().getNameRol().equals("Decano")) {
            switch (action) {
                case "lista":
                    request.setAttribute("listaCursos", cursoDao.listarCursos(user.getIdUsuario()));
                    view = request.getRequestDispatcher("Cursos/lista.jsp");
                    view.forward(request, response);
                    break;
                case "agregar":
                    request.setAttribute("listaDocentesSinCurso",dDao.listaDocentesSinCurso());
                    view = request.getRequestDispatcher("Cursos/formularioNuevo.jsp");
                    view.forward(request, response);
                    break;
                case "editar":
                    int idCurso = Integer.parseInt(request.getParameter("id"));
                    Curso curso = cursoDao.obtenerCursoPorID(idCurso);
                    request.setAttribute("curso",curso);
                    view = request.getRequestDispatcher("Cursos/formularioEditar.jsp");
                    view.forward(request, response);

                    break;
                case "borrar":
                    cursoDao.borrarCurso(Integer.parseInt(request.getParameter("id")));
                    response.sendRedirect("CursoServlet?msg=Empleado borrado exitosamente");
                    break;
                default:
                    response.sendRedirect("CursoServlet");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        CursoDao cursoDao = new CursoDao();
        DocentesDao dDao = new DocentesDao();
        FacultadDao facultadDao = new FacultadDao();
        HttpSession httpSession = request.getSession();
        Usuario user  = (Usuario) httpSession.getAttribute("usuarioLogueado");
        switch (action) {
            case "guardar":
                String nombre = request.getParameter("first_name");
                String codigo = request.getParameter("codigo");
                int idCursoDocente = Integer.parseInt(request.getParameter("curso"));

                int idFacultad = facultadDao.obtenerIdFacultadXIdDecano(user.getIdUsuario());
                cursoDao.registrarCurso(nombre,codigo,idCursoDocente,idFacultad);
                response.sendRedirect("CursoServlet?msg=Creado exitosamente");
                break;

            case "editar":
                String nuevoNombre = request.getParameter("nombre");
                String idCurso = request.getParameter("curso_id");

                if (idCurso != null && !idCurso.isEmpty()) {
                    int idDocente = Integer.parseInt(idCurso);
                    if (nuevoNombre != null) {
                        cursoDao.editarNombreCurso(idDocente, nuevoNombre);
                        response.sendRedirect("CursoServlet?action=listar");
                    } else {
                        response.sendRedirect("CursoServlet?action=listar");
                    }
                } else {
                    response.sendRedirect("CursoServlet?action=listar");
                }
                break;


    }
}}

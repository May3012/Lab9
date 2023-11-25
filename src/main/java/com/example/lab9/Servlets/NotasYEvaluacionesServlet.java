package com.example.lab9.Servlets;

import com.example.lab9.Beans.Curso;
import com.example.lab9.Beans.Evaluacion;
import com.example.lab9.Beans.Semestre;
import com.example.lab9.Beans.Usuario;
import com.example.lab9.Daos.Curso_has_Docente_Dao;
import com.example.lab9.Daos.EvaluacionesDao;
import com.example.lab9.Daos.SemestreDao;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "NotasYEvaluacionesServlet", value = "/NotasYEvaluacionesServlet")
public class NotasYEvaluacionesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action") == null ? "lista" : request.getParameter("action");

        RequestDispatcher view;
        EvaluacionesDao evaDao = new EvaluacionesDao();
        SemestreDao sDao = new SemestreDao();
        HttpSession httpSession = request.getSession();
        Usuario user  = (Usuario) httpSession.getAttribute("usuarioLogueado");

        if (user.getRol().getNameRol().equals("Docente")) {
            switch (action) {
                case "lista":
                    request.setAttribute("listaEvaluaciones", evaDao.listarEvaluaciones(user.getIdUsuario()));

                    view = request.getRequestDispatcher("Evaluaciones/lista.jsp");
                    view.forward(request, response);
                    break;
                case "agregar":
                    request.setAttribute("listaSemetres",sDao.listarSemestres());
                    view = request.getRequestDispatcher("Evaluaciones/formularioNuevo.jsp");
                    view.forward(request, response);
                    break;
                /*case "editar":
                    HttpSession httpSession = request.getSession();
                    Employee employee = (Employee) httpSession.getAttribute("usuarioLogueado");


                    break;
                case "borrar":
                */
                default:
                    response.sendRedirect("NotasYEvaluacionesServlet");
            }
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action") == null ? "guardar" : request.getParameter("action");
        EvaluacionesDao evaluacionesDao = new EvaluacionesDao();
        HttpSession httpSession = request.getSession();
        Usuario user  = (Usuario) httpSession.getAttribute("usuarioLogueado");

        try{
        switch (action) {
            case "guardar":
                String nombre = request.getParameter("nombre");
                String codigo = request.getParameter("codigo");
                String correo = request.getParameter("correo");
                int nota = Integer.parseInt(request.getParameter("nota"));

                Curso_has_Docente_Dao cd = new Curso_has_Docente_Dao();
                int idCurso = cd.obtenerCursoXDocente(user.getIdUsuario());
                int idSemestre = Integer.parseInt(request.getParameter("semestre"));

                evaluacionesDao.guardarEvaluacion(nombre,codigo,correo,nota,idCurso,idSemestre);
                response.sendRedirect("NotasYEvaluacionesServlet?msg=Empleado creado exitosamente");
                break;
            case "borrar":
                break;
        }}catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al guardar la evaluación en la base de datos.");

        }
    }
}

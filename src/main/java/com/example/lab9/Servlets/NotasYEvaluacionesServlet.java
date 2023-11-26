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
                case "editar":
                    int idEvaluacion = Integer.parseInt(request.getParameter("id"));
                    Evaluacion eva = evaDao.obtenerEvaluacion(user.getIdUsuario());
                    request.setAttribute("evaluacion",eva);
                    view = request.getRequestDispatcher("Evaluaciones/formularioEditar.jsp");
                    view.forward(request, response);
                    break;
                case "borrar":
                    evaDao.borrarEvaluacion(Integer.parseInt(request.getParameter("id")));

                    response.sendRedirect("NotasYEvaluacionesServlet?msg=Empleado borrado exitosamente");
                    break;
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
                String nombre = request.getParameter("first_name");
                String codigo = request.getParameter("codigo");
                String correo = request.getParameter("email");
                int nota = Integer.parseInt(request.getParameter("nota"));

                Curso_has_Docente_Dao cd = new Curso_has_Docente_Dao();
                int idCurso = cd.obtenerCursoXDocente(user.getIdUsuario());
                int idSemestre = Integer.parseInt(request.getParameter("semestre"));

                evaluacionesDao.guardarEvaluacion(nombre,codigo,correo,nota,idCurso,idSemestre);
                response.sendRedirect("NotasYEvaluacionesServlet?msg=Empleado creado exitosamente");
                break;

            case "editar":
                String id_eva = request.getParameter("evaluacion_id");
                String nuevoNombre = request.getParameter("nombre");
                String cod = request.getParameter("cod");
                String ema = request.getParameter("ema");
                String notaa = request.getParameter("notaa");

                if (id_eva != null && !id_eva.isEmpty()) {
                    int idDocente = Integer.parseInt(id_eva);
                    int notaaa = Integer.parseInt(notaa);
                    if (nuevoNombre != null && cod != null && ema!=null && notaa!=null ) {
                        evaluacionesDao.editarEvaluacion(idDocente, nuevoNombre,cod,ema,notaaa);
                        response.sendRedirect("NotasYEvaluacionesServlet?action=listar");
                    } else {
                        response.sendRedirect("NotasYEvaluacionesServlet?action=listar");
                    }
                } else {
                    response.sendRedirect("NotasYEvaluacionesServlet?action=listar");
                }
                break;
        }}catch (SQLException e) {
            e.printStackTrace();
            response.getWriter().println("Error al guardar la evaluación en la base de datos.");

        }
    }
}

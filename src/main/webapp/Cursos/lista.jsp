<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 26/11/2023
  Time: 06:16
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Daos.CursoDao" %>
<%@ page import="com.example.lab9.Beans.Curso" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<jsp:useBean id="listaCursos" type="java.util.ArrayList<com.example.lab9.Beans.Curso>" scope="request"/>
<jsp:useBean id="usuarioLogueado" class="com.example.lab9.Beans.Usuario" type="com.example.lab9.Beans.Usuario" scope="session" />
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Cursos</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="emp"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>Lista de Cursos</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%= request.getContextPath()%>/CursoServlet?action=agregar" class="btn btn-primary">Agregar curso</a>
        </div>
    </div>
    <% if (request.getParameter("msg") != null) {%>
    <div class="alert alert-success" role="alert"><%=request.getParameter("msg")%>
    </div>
    <% } %>
    <% if (request.getParameter("err") != null) {%>
    <div class="alert alert-danger" role="alert"><%=request.getParameter("err")%>
    </div>
    <% } %>
    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>ID Curso</th>
            <th>Codigo</th>
            <th>Nombre</th>
            <th>Facultad</th>
            <th>Fecha de Registro</th>
            <% if(usuarioLogueado != null && usuarioLogueado.getIdUsuario() > 0) {%>
            <th>Editar</th>
            <th>Borrar</th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <%
            int i = 1;
            for (Curso c : listaCursos) {
                CursoDao cu = new CursoDao();
                int cantidadEva = cu.cantidadEvaluacionesPorCurso(c.getIdCurso());
                boolean tieneCursos = cantidadEva > 0;

        %>
        <tr>
            <td><%= i%>
            </td>
            <td><%= c.getIdCurso()%>
            </td>
            <td><%= c.getCodigo()%>
            </td>
            <td><%= c.getNombre()%>
            </td>
            <td><%= c.getFacultad().getNombreFacu()%>
            </td>
            <td><%= c.getFecha_registro()%>
            </td>

            <% if(usuarioLogueado != null && usuarioLogueado.getIdUsuario() > 0) {%>
            <td>
                <a href="<%=request.getContextPath()%>/CursoServlet?action=editar&id=<%= c.getIdCurso()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <% if (!tieneCursos) { %>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/CursoServlet?action=borrar&id=<%= c.getIdCurso()%>"
                   type="button" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                </a>
                <% } else { %>

                <% } %>
            </td>
        </tr>
        <%
                    i++;
                }
            }
        %>
        </tbody>
    </table>
    <jsp:include page="../includes/footer.jsp"/>
</div>
</body>
</html>
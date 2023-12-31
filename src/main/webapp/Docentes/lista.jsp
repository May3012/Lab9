<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 25/11/2023
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@ page import="com.example.lab9.Daos.DocentesDao" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<jsp:useBean id="listaDocentes" type="java.util.ArrayList<com.example.lab9.Beans.Usuario>" scope="request"/>
<jsp:useBean id="usuarioLogueado" class="com.example.lab9.Beans.Usuario" type="com.example.lab9.Beans.Usuario" scope="session" />
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Docentes</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="emp"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>Lista de Docentes</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%= request.getContextPath()%>/DocentesServlet?action=agregar" class="btn btn-primary">Agregar
                nuevo docente</a>
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
            <th>Docente ID</th>
            <th>Nombre </th>
            <th>Correo</th>
            <th>Fecha de Registro</th>
            <th>Fecha de Edición</th>
            <% if(usuarioLogueado != null && usuarioLogueado.getIdUsuario() > 0) {%>
            <th>Editar</th>
            <th>Borrar</th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <%
            int i = 1;
            for (Usuario d : listaDocentes) {
                DocentesDao docenteDao = new DocentesDao();
                int cantidadCursos = docenteDao.cantidadCursosAsignados(d.getIdUsuario());
                boolean tieneCursos = cantidadCursos > 0;
        %>
        <tr>
            <td><%= i%>
            </td>
            <td><%= d.getIdUsuario()%>
            </td>
            <td><%= d.getNombre()%>
            </td>
            <td><%= d.getCorreo()%>
            </td>
            <td><%= d.getFecha_registro()%>
            </td>
            <td><%= d.getFecha_edicion()%>
            </td>

            <% if(usuarioLogueado != null && usuarioLogueado.getIdUsuario() > 0) {%>
            <td>
                <a href="<%=request.getContextPath()%>/DocentesServlet?action=editar&id=<%= d.getIdUsuario()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <% if (!tieneCursos) { %>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/DocentesServlet?action=borrar&id=<%= d.getIdUsuario()%>"
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

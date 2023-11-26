<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 25/11/2023
  Time: 03:22
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Beans.Evaluacion" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaEvaluaciones" type="java.util.ArrayList<com.example.lab9.Beans.Evaluacion>" scope="request"/>
<jsp:useBean id="textoBusqueda" scope="request" type="java.lang.String" class="java.lang.String"/>
<jsp:useBean id="usuarioLogueado" class="com.example.lab9.Beans.Usuario" type="com.example.lab9.Beans.Usuario" scope="session" />
<!DOCTYPE html>
<html>
<head>
    <title>Lista empleados</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="emp"/>
    </jsp:include>
    <div class="row mb-5 mt-4">
        <div class="col-md-7">
            <h1>Lista de empleados</h1>
        </div>
        <div class="col-md-5 col-lg-4 ms-auto my-auto text-md-end">
            <a href="<%= request.getContextPath()%>/NotasYEvaluacionesServlet?action=agregar" class="btn btn-primary">Agregar
                nuevo empleado</a>
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
    <form method="post" action="<%=request.getContextPath()%>/NotasYEvaluacionesServlet?action=buscar">
        <div class="input-group mb-3">
            <input type="text" class="form-control" placeholder="Buscar por nombre" name="textoBuscar"
                   value="<%=textoBusqueda%>"/>
            <button class="input-group-text" type="submit">
                <i class="bi bi-search"></i>
            </button>
            <a class="input-group-text" href="<%=request.getContextPath()%>/NotasYEvaluacionesServlet">
                <i class="bi bi-x-circle"></i>
            </a>
        </div>
    </form>
    <table class="table">
        <thead>
        <tr>
            <th>#</th>
            <th>Employee</th>
            <th>Email</th>
            <th>Job ID</th>
            <th>Salary</th>
            <th>Commision</th>
            <th>Manager ID</th>
            <th>Department ID</th>
            <% if(usuarioLogueado != null && usuarioLogueado.getIdUsuario() > 0) {%>
            <th></th>
            <th></th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <%
            int i = 1;
            for (Evaluacion e : listaEvaluaciones) {
        %>
        <tr>
            <td><%= i%>
            </td>
            <td><%= e.getIdEvalluaciones()%>
            </td>
            <td><%= e.getNombre_estudiantes()%>
            </td>
            <td><%= e.getCorreo_estudiantes()%>
            </td>
            <td><%= e.getNota()%>
            </td>
            <td><%= e.getSemestre().getNombre()%>
            </td>
            <td><%= e.getFecha_registro()%>
            </td>
            <td><%= e.getFecha_edicion()%>
            </td>
            <% if(usuarioLogueado != null && usuarioLogueado.getIdUsuario() > 0) {%>
            <td>
                <a href="<%=request.getContextPath()%>/NotasYEvaluacionesServlet?action=editar&id=<%= e.getIdEvalluaciones()%>"
                   type="button" class="btn btn-primary">
                    <i class="bi bi-pencil-square"></i>
                </a>
            </td>
            <td>
                <a onclick="return confirm('¿Estas seguro de borrar?');"
                   href="<%=request.getContextPath()%>/NotasYEvaluacionesServlet?action=borrar&id=<%= e.getIdEvalluaciones()%>"
                   type="button" class="btn btn-danger">
                    <i class="bi bi-trash"></i>
                </a>
            </td>
            <% } %>
        </tr>
        <%
                i++;
            }
        %>
        </tbody>
    </table>
    <jsp:include page="../includes/footer.jsp"/>
</div>
</body>
</html>

<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<% String currentPage = request.getParameter("currentPage"); %>
<jsp:useBean id="usuarioLogueado" scope="session" type="Usuario" class="com.example.lab9.Beans.Usuario" />

<nav class="navbar navbar-expand-md navbar-light bg-light">
    <div class="container-fluid">
        <p class="navbar-brand">PUCP</p>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse justify-content-end" id="navbarSupportedContent">
            <ul class="navbar-nav">
                <% if(usuarioLogueado.getRol().getNameRol().equals("Docente")){ %>
                <li class="nav-item">
                    <a class="nav-link <%=currentPage.equals("docentes") ? "active" : ""%>"
                       href="<%=request.getContextPath()%>/NotasYEvaluacionesServlet">
                        Evaluaciones
                    </a>
                </li>
                <%}%>
                <% if(usuarioLogueado.getRol().getNameRol().equals("Decano")){ %>
                <li class="nav-item">
                    <a class="nav-link <%=currentPage.equals("docentes") ? "active" : ""%>"
                       href="<%=request.getContextPath()%>/DocentesServlet">
                        Docentes
                    </a>
                </li>
                <li class="nav-item">
                    <a class="nav-link <%=currentPage.equals("cursos") ? "active" : ""%>"
                       href="<%=request.getContextPath()%>/CursoServlet">
                        Cursos
                    </a>
                </li>
                <li class="nav-item">
                    <% if(usuarioLogueado.getIdUsuario() == 0){ %>
                    <a class="nav-link" style="text-decoration: underline;color: #0d6efd;"
                       href="<%=request.getContextPath()%>/LoginServlet">
                        (Iniciar sesión)
                    </a>
                    <% }else{ %>
                    <a class="nav-link disabled"><%=usuarioLogueado.getNombre()%></a>
                    <% } %>
                </li>
                <%}%>
                <% if(usuarioLogueado.getIdUsuario() != 0){ %>
                <li class="nav-item">
                    <a class="nav-link" style="text-decoration: underline;color: #0d6efd;" href="<%=request.getContextPath()%>/LogingServlet?a=logout">(Cerrar sesión)</a>
                </li>
                <% } %>
            </ul>
        </div>
    </div>
</nav>

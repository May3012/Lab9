<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 26/11/2023
  Time: 06:20
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Daos.DocentesDao" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <jsp:useBean id="listaDocentesSinCurso" type="java.util.ArrayList<com.example.lab9.Beans.Usuario>" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>Nuevo empleado</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="emp"/>
    </jsp:include>
    <div class="row mb-4">
        <div class="col"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Nuevo usuario</h1>
            <hr>
            <form method="POST" action="CursoServlet">
                <div class="mb-3">
                    <label class="form-label" for="first_name">Nombre del Curso</label>
                    <input type="text" class="form-control form-control-sm" id="first_name" name="first_name">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="codigo">Código del curso</label>
                    <input type="text" class="form-control form-control-sm" id="codigo" name="codigo">
                </div>

                <div class="mb-3">
                    <label class="form-label" for="curso">Docente</label>
                    <select name="curso" id="curso" class="form-select">
                        <% for (Usuario d : listaDocentesSinCurso) {%>
                        <option value="<%=d.getIdUsuario()%>"><%=d.getNombre()%>
                        </option>
                        <% }%>
                    </select>
                </div>
                <a href="<%= request.getContextPath()%>/CursoServlett" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>

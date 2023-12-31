<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 25/11/2023
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<jsp:useBean id="usuario" type="com.example.lab9.Beans.Usuario" scope="request"/>


<!DOCTYPE html>
<html>
<head>
    <title>Editar Docente</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="emp"/>
    </jsp:include>
    <div class="row mb-4">
        <div class="col-md-3"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Editar Docente</h1>
            <form method="POST" action="<%=request.getContextPath()%>/DocentesServlet?action=editar">
                <input type="hidden" name="docente_id" value="<%= usuario.getIdUsuario()%>"/>
                <div class="mb-3">
                    <label class="form-label" for="nombre">Nombre</label>
                    <input type="text" class="form-control form-control-sm" id="nombre" name="nombre">
                </div>
                <a href="<%= request.getContextPath()%>/DocentesServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 25/11/2023
  Time: 19:26
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<!DOCTYPE html>
<html>
<head>
    <title>Nuevo Docente</title>
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
            <h1 class='mb-3'>Nuevo Docente</h1>
            <hr>
            <form method="POST" action="DocentesServlet">
                <div class="mb-3">
                    <label class="form-label" for="first_name">Nombre</label>
                    <input type="text" class="form-control form-control-sm" id="first_name" name="first_name" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="email">Correo</label>
                    <input type="text" class="form-control form-control-sm" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="password">Contraseña</label>
                    <input type="text" class="form-control form-control-sm" id="password" name="password" required>
                </div>
                <a href="<%= request.getContextPath()%>/DocentesServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>

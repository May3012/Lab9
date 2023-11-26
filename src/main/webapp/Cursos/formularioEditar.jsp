<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.example.lab9.Beans.Usuario" %>
<jsp:useBean id="curso" type="com.example.lab9.Beans.Curso" scope="request"/>


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
            <h1 class='mb-3'>Editar Curso</h1>
            <form method="POST" action="<%=request.getContextPath()%>/CursoServlet?action=editar">
                <input type="hidden" name="curso_id" value="<%= curso.getIdCurso()%>"/>
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

<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 25/11/2023
  Time: 03:22
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="com.example.lab9.Beans.Evaluacion" %>
<jsp:useBean id="evaluacion" type="java.util.ArrayList<com.example.lab9.Beans.Evaluacion>" scope="request"/>


<!DOCTYPE html>
<html>
<head>
    <title>Editar Evaluacion</title>
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
            <h1 class='mb-3'>Editar Evaluacion</h1>
            <form method="POST" action="<%=request.getContextPath()%>/NotasYEvaluacionesServlet?action=editar">
                <input type="hidden" name="evaluacion_id" value="<%=evaluacion.getIdEvalluaciones()%>"/>
                <div class="mb-3">
                    <label class="form-label" for="nombre">Nombre</label>
                    <input type="text" class="form-control form-control-sm" id="nombre" name="nombre">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="cod">Codigo</label>
                    <input type="text" class="form-control form-control-sm" id="cod" name="cod">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="ema">Correo</label>
                    <input type="text" class="form-control form-control-sm" id="ema" name="ema">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="notaa">Nota</label>
                    <input type="text" class="form-control form-control-sm" id="notaa" name="notaa">
                </div>
                <a href="<%= request.getContextPath()%>/NotasYEvaluaciones" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col-md-3"></div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>


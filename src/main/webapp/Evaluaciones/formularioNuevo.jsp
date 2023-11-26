<%--
  Created by IntelliJ IDEA.
  User: HP
  Date: 25/11/2023
  Time: 03:22
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.ArrayList" %>
<%@ page import="com.example.lab9.Beans.Evaluacion" %>
<%@ page import="com.example.lab9.Beans.Semestre" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<jsp:useBean id="listaSemetres" type="java.util.ArrayList<com.example.lab9.Beans.Semestre>" scope="request"/>
<!DOCTYPE html>
<html>
<head>
    <title>Nuevo Evaluación</title>
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
            <h1 class='mb-3'>Nuevo Evaluación</h1>
            <hr>
            <form method="POST" action="NotasYEvaluacionesServlet">
                <div class="mb-3">
                    <label class="form-label" for="first_name">Nombre</label>
                    <input type="text" class="form-control form-control-sm" id="first_name" name="first_name">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="codigo">Código</label>
                    <input type="text" class="form-control form-control-sm" id="codigo" name="codigo">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="email">Correo</label>
                    <input type="text" class="form-control form-control-sm" id="email" name="email" required>
                </div>
                <div class="mb-3">
                    <label class="form-label" for="nota">Nota</label>
                    <input type="text" class="form-control form-control-sm" id="nota" name="nota" required>
                </div>

                <div class="mb-3">
                    <label class="form-label" for="semestre">Semestre</label>
                    <select name="semestre" id="semestre" class="form-select">
                        <% for (Semestre s : listaSemetres) {%>
                        <option value="<%=s.getIdSemestre()%>"><%=s.getNombre()%>
                        </option>
                        <% }%>
                    </select>
                </div>
                <a href="<%= request.getContextPath()%>/NotasYEvaluacionesServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>
</div>
<jsp:include page="../includes/footer.jsp"/>
</body>
</html>
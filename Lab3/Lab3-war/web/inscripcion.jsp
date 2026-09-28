<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inscripciones</title>
    </head>
    <body>
        <a href="index.jsp">Inicio</a> | <a href="StudentServlet">Estudiantes</a> | <a href="CursoServlet">Cursos</a>
        <h1>Inscripción de estudiantes en cursos</h1>
        <c:if test="${not empty mensaje}"><p style="color:blue">${mensaje}</p></c:if>
        <form action="./InscripcionServlet" method="POST">
            <table>
                <tr>
                    <td>Estudiante</td>
                    <td>
                        <select name="studentId">
                            <option value="">-- Seleccione --</option>
                            <c:forEach items="${allStudents}" var="stud">
                                <option value="${stud.studentid}">${stud.studentid} - ${stud.firstname} ${stud.lastname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Curso</td>
                    <td>
                        <select name="codigo">
                            <option value="">-- Seleccione --</option>
                            <c:forEach items="${allCursos}" var="cur">
                                <option value="${cur.codigo}">${cur.codigo} - ${cur.nombre} (cupos: ${cur.cuposDisponibles})</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" name="action" value="Inscribir" />
                        <input type="submit" name="action" value="Retirar" />
                    </td>
                </tr>
            </table>
        </form>
        <br>
        <h2>Cursos de cada estudiante</h2>
        <table border="1">
            <th>Estudiante</th>
            <th>Cursos que toma</th>
            <c:forEach items="${allStudents}" var="stud">
                <tr>
                    <td>${stud.firstname} ${stud.lastname}</td>
                    <td><c:forEach items="${stud.cursoList}" var="cur">${cur.nombre} (${cur.creditos} créditos)<br></c:forEach></td>
                </tr>
            </c:forEach>
        </table>
        <h2>Estudiantes de cada curso</h2>
        <table border="1">
            <th>Curso</th>
            <th>Estudiantes</th>
            <c:forEach items="${allCursos}" var="cur">
                <tr>
                    <td>${cur.nombre}</td>
                    <td><c:forEach items="${cur.studentList}" var="stud">${stud.firstname} ${stud.lastname}<br></c:forEach></td>
                </tr>
            </c:forEach>
        </table>
    </body>
</html>
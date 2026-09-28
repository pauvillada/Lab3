package edu.unipiloto.servlet;

import edu.unipiloto.dao.CursoDAOLocal;
import edu.unipiloto.dao.StudentDAOLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "InscripcionServlet", urlPatterns = {"/InscripcionServlet"})
public class InscripcionServlet extends HttpServlet {

    @EJB
    private StudentDAOLocal studentDao;

    @EJB
    private CursoDAOLocal cursoDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String mensaje = null;

        if (action != null) {
            try {
                int studentId = Integer.parseInt(request.getParameter("studentId"));
                String codigo = request.getParameter("codigo");
                if ("Inscribir".equals(action)) {
                    mensaje = cursoDao.inscribir(studentId, codigo);
                } else if ("Retirar".equals(action)) {
                    mensaje = cursoDao.retirar(studentId, codigo);
                }
            } catch (NumberFormatException e) {
                mensaje = "Seleccione un estudiante y un curso";
            }
        }

        request.setAttribute("mensaje", mensaje);
        request.setAttribute("allStudents", studentDao.getAllStudents());
        request.setAttribute("allCursos", cursoDao.getAllCursos());
        request.getRequestDispatcher("inscripcion.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
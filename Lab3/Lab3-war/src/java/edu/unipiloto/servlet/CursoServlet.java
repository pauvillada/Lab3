package edu.unipiloto.servlet;

import edu.unipiloto.dao.CursoDAOLocal;
import edu.unipiloto.entity.Curso;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "CursoServlet", urlPatterns = {"/CursoServlet"})
public class CursoServlet extends HttpServlet {

    @EJB
    private CursoDAOLocal cursoDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        Curso curso = null;
        String mensaje = null;

        if (action != null) {
            try {
                String codigo = texto(request.getParameter("codigo")).toUpperCase();
                String nombre = texto(request.getParameter("nombre"));
                int creditos = numero(request.getParameter("creditos"));
                int semestre = numero(request.getParameter("semestre"));
                int admitidos = numero(request.getParameter("admitidos"));
                curso = new Curso(codigo, nombre, creditos, semestre, admitidos);

                if (codigo.isEmpty()) {
                    mensaje = "El código del curso es obligatorio";
                } else if ("Add".equalsIgnoreCase(action)) {
                    if (cursoDao.getCurso(codigo) != null) {
                        mensaje = "Ya existe un curso con código " + codigo;
                    } else {
                        cursoDao.addCurso(curso);
                    }
                } else if ("Edit".equalsIgnoreCase(action)) {
                    cursoDao.editCurso(curso);
                } else if ("Delete".equalsIgnoreCase(action)) {
                    cursoDao.deleteCurso(codigo);
                    curso = null;
                } else if ("Search".equalsIgnoreCase(action)) {
                    curso = cursoDao.getCurso(codigo);
                    if (curso == null) {
                        mensaje = "No existe un curso con código " + codigo;
                    }
                }
            } catch (NumberFormatException e) {
                mensaje = "Créditos, semestre y admitidos deben ser números";
            }
        }

        request.setAttribute("curso", curso);
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("allCursos", cursoDao.getAllCursos());
        request.getRequestDispatcher("cursoInfo.jsp").forward(request, response);
    }

    private static String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    private static int numero(String valor) {
        return (valor == null || valor.trim().equals("")) ? 0 : Integer.parseInt(valor.trim());
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
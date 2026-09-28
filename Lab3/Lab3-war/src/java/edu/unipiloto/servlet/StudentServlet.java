package edu.unipiloto.servlet;

import edu.unipiloto.dao.StudentDAOLocal;
import edu.unipiloto.entity.Student;
import java.io.IOException;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "StudentServlet", urlPatterns = {"/StudentServlet"})
public class StudentServlet extends HttpServlet {

    @EJB
    private StudentDAOLocal studentDao;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String studentIdStr = request.getParameter("studentId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String yearLevelStr = request.getParameter("yearLevel");

        Student student = null;
        String mensaje = null;

        if (action != null) {
            try {
                int studentId = 0;
                if (studentIdStr != null && !studentIdStr.trim().equals("")) {
                    studentId = Integer.parseInt(studentIdStr.trim());
                }
                int yearLevel = 0;
                if (yearLevelStr != null && !yearLevelStr.trim().equals("")) {
                    yearLevel = Integer.parseInt(yearLevelStr.trim());
                }
                student = new Student(studentId, firstName, lastName, yearLevel);

                if ("Add".equalsIgnoreCase(action)) {
                    if (studentDao.getStudent(studentId) != null) {
                        mensaje = "Ya existe un estudiante con ID " + studentId;
                    } else {
                        studentDao.addStudent(student);
                    }
                } else if ("Edit".equalsIgnoreCase(action)) {
                    studentDao.editStudent(student);
                } else if ("Delete".equalsIgnoreCase(action)) {
                    studentDao.deleteStudent(studentId);
                    student = null;
                } else if ("Search".equalsIgnoreCase(action)) {
                    student = studentDao.getStudent(studentId);
                    if (student == null) {
                        mensaje = "No existe un estudiante con ID " + studentId;
                    }
                }
            } catch (NumberFormatException e) {
                mensaje = "Student Id y Year Level deben ser números";
            }
        }

        request.setAttribute("student", student);
        request.setAttribute("mensaje", mensaje);
        request.setAttribute("allStudents", studentDao.getAllStudents());
        request.getRequestDispatcher("studentInfo.jsp").forward(request, response);
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
package edu.unipiloto.dao;

import edu.unipiloto.entity.Curso;
import edu.unipiloto.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class StudentDAO implements StudentDAOLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addStudent(Student student) {
        em.persist(student);
    }

    @Override
    public void editStudent(Student student) {
        // Se actualizan los datos sin borrar los cursos inscritos
        Student actual = em.find(Student.class, student.getStudentid());
        if (actual != null) {
            actual.setFirstname(student.getFirstname());
            actual.setLastname(student.getLastname());
            actual.setYearlevel(student.getYearlevel());
        }
    }

    @Override
    public void deleteStudent(int studentId) {
        Student student = em.find(Student.class, studentId);
        if (student != null) {
            // Primero se borran sus filas en STUDENT_CURSO
            if (student.getCursoList() != null) {
                for (Curso c : new ArrayList<>(student.getCursoList())) {
                    c.getStudentList().remove(student);
                }
                student.getCursoList().clear();
            }
            em.remove(student);
        }
    }

    @Override
    public Student getStudent(int studentId) {
        Student s = em.find(Student.class, studentId);
        if (s != null && s.getCursoList() != null) {
            s.getCursoList().size(); // carga los cursos para mostrarlos en el JSP
        }
        return s;
    }

    @Override
    public List<Student> getAllStudents() {
        List<Student> lista = em.createNamedQuery("Student.getAll", Student.class).getResultList();
        for (Student s : lista) {
            if (s.getCursoList() != null) {
                s.getCursoList().size();
            }
        }
        return lista;
    }
}
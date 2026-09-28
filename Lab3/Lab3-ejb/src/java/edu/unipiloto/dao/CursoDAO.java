package edu.unipiloto.dao;

import edu.unipiloto.entity.Curso;
import edu.unipiloto.entity.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CursoDAO implements CursoDAOLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void addCurso(Curso curso) {
        em.persist(curso);
    }

    @Override
    public void editCurso(Curso curso) {
        Curso actual = em.find(Curso.class, curso.getCodigo());
        if (actual != null) {
            actual.setNombre(curso.getNombre());
            actual.setCreditos(curso.getCreditos());
            actual.setSemestre(curso.getSemestre());
            actual.setAdmitidos(curso.getAdmitidos());
        }
    }

    @Override
    public void deleteCurso(String codigo) {
        Curso curso = em.find(Curso.class, codigo);
        if (curso != null) {
            // Student es el dueño de la relación: se quita el curso de cada estudiante
            if (curso.getStudentList() != null) {
                for (Student s : new ArrayList<>(curso.getStudentList())) {
                    s.getCursoList().remove(curso);
                }
                curso.getStudentList().clear();
            }
            em.remove(curso);
        }
    }

    @Override
    public Curso getCurso(String codigo) {
        Curso c = em.find(Curso.class, codigo);
        if (c != null && c.getStudentList() != null) {
            c.getStudentList().size();
        }
        return c;
    }

    @Override
    public List<Curso> getAllCursos() {
        List<Curso> lista = em.createNamedQuery("Curso.getAll", Curso.class).getResultList();
        for (Curso c : lista) {
            if (c.getStudentList() != null) {
                c.getStudentList().size();
            }
        }
        return lista;
    }

    @Override
    public String inscribir(int studentId, String codigo) {
        Student student = em.find(Student.class, studentId);
        Curso curso = em.find(Curso.class, codigo);
        if (student == null || curso == null) {
            return "Seleccione un estudiante y un curso existentes.";
        }
        if (student.getCursoList() == null) {
            student.setCursoList(new ArrayList<Curso>());
        }
        if (curso.getStudentList() == null) {
            curso.setStudentList(new ArrayList<Student>());
        }
        if (student.getCursoList().contains(curso)) {
            return student.getFirstname() + " ya está inscrito en " + curso.getNombre() + ".";
        }
        if (curso.getCuposDisponibles() <= 0) {
            return "El curso " + curso.getNombre() + " no tiene cupos (admitidos: " + curso.getAdmitidos() + ").";
        }
        student.getCursoList().add(curso);
        curso.getStudentList().add(student);
        return student.getFirstname() + " " + student.getLastname() + " quedó inscrito en " + curso.getNombre() + ".";
    }

    @Override
    public String retirar(int studentId, String codigo) {
        Student student = em.find(Student.class, studentId);
        Curso curso = em.find(Curso.class, codigo);
        if (student == null || curso == null || student.getCursoList() == null
                || !student.getCursoList().contains(curso)) {
            return "Esa inscripción no existe.";
        }
        student.getCursoList().remove(curso);
        curso.getStudentList().remove(student);
        return student.getFirstname() + " fue retirado de " + curso.getNombre() + ".";
    }
}
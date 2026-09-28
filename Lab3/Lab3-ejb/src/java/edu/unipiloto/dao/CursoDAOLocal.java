package edu.unipiloto.dao;

import edu.unipiloto.entity.Curso;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CursoDAOLocal {

    void addCurso(Curso curso);

    void editCurso(Curso curso);

    void deleteCurso(String codigo);

    Curso getCurso(String codigo);

    List<Curso> getAllCursos();

    String inscribir(int studentId, String codigo);

    String retirar(int studentId, String codigo);
}
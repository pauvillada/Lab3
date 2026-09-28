package edu.unipiloto.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "CURSO")
@NamedQueries({
    @NamedQuery(name = "Curso.getAll", query = "SELECT c FROM Curso c ORDER BY c.semestre, c.codigo")
})
public class Curso implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CODIGO")
    private String codigo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "CREDITOS")
    private Integer creditos;

    @Column(name = "SEMESTRE")
    private Integer semestre;

    @Column(name = "ADMITIDOS")
    private Integer admitidos;

    // Lado inverso: un curso es tomado por varios estudiantes
    @ManyToMany(mappedBy = "cursoList")
    private List<Student> studentList;

    public Curso() {
    }

    public Curso(String codigo, String nombre, Integer creditos, Integer semestre, Integer admitidos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.creditos = creditos;
        this.semestre = semestre;
        this.admitidos = admitidos;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Integer getCreditos() { return creditos; }
    public void setCreditos(Integer creditos) { this.creditos = creditos; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public Integer getAdmitidos() { return admitidos; }
    public void setAdmitidos(Integer admitidos) { this.admitidos = admitidos; }

    public List<Student> getStudentList() { return studentList; }
    public void setStudentList(List<Student> studentList) { this.studentList = studentList; }

    // Cupos que quedan = admitidos - inscritos
    public int getCuposDisponibles() {
        int inscritos = studentList == null ? 0 : studentList.size();
        return (admitidos == null ? 0 : admitidos) - inscritos;
    }

    @Override
    public int hashCode() {
        return codigo != null ? codigo.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Curso)) {
            return false;
        }
        Curso other = (Curso) object;
        return this.codigo != null && this.codigo.equals(other.codigo);
    }

    @Override
    public String toString() {
        return "edu.unipiloto.entity.Curso[ codigo=" + codigo + " ]";
    }
}
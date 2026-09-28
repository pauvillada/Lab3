package edu.unipiloto.entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "STUDENT")
@NamedQueries({
    @NamedQuery(name = "Student.getAll", query = "SELECT s FROM Student s ORDER BY s.studentid")
})
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "STUDENTID")
    private Integer studentid;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "YEARLEVEL")
    private Integer yearlevel;

    // Relación muchos a muchos: un estudiante toma varios cursos
    @ManyToMany
    @JoinTable(name = "STUDENT_CURSO",
            joinColumns = @JoinColumn(name = "STUDENTID", referencedColumnName = "STUDENTID"),
            inverseJoinColumns = @JoinColumn(name = "CODIGO_CURSO", referencedColumnName = "CODIGO"))
    private List<Curso> cursoList;

    public Student() {
    }

    public Student(Integer studentid, String firstname, String lastname, Integer yearlevel) {
        this.studentid = studentid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.yearlevel = yearlevel;
    }

    public Integer getStudentid() { return studentid; }
    public void setStudentid(Integer studentid) { this.studentid = studentid; }

    public String getFirstname() { return firstname; }
    public void setFirstname(String firstname) { this.firstname = firstname; }

    public String getLastname() { return lastname; }
    public void setLastname(String lastname) { this.lastname = lastname; }

    public Integer getYearlevel() { return yearlevel; }
    public void setYearlevel(Integer yearlevel) { this.yearlevel = yearlevel; }

    public List<Curso> getCursoList() { return cursoList; }
    public void setCursoList(List<Curso> cursoList) { this.cursoList = cursoList; }

    @Override
    public int hashCode() {
        return studentid != null ? studentid.hashCode() : 0;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        return this.studentid != null && this.studentid.equals(other.studentid);
    }

    @Override
    public String toString() {
        return "edu.unipiloto.entity.Student[ studentid=" + studentid + " ]";
    }
}
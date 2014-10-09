/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonsuchdata;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "marks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Marks.findAll", query = "SELECT m FROM Marks m"),
    @NamedQuery(name = "Marks.findByMarkid", query = "SELECT m FROM Marks m WHERE m.markid = :markid"),
    @NamedQuery(name = "Marks.findByStudentid", query = "SELECT m FROM Marks m WHERE m.studentid = :studentid"),
    @NamedQuery(name = "Marks.findBySubjectid", query = "SELECT m FROM Marks m WHERE m.subjectid = :subjectid"),
    @NamedQuery(name = "Marks.findByTaskid", query = "SELECT m FROM Marks m WHERE m.taskid = :taskid"),
    @NamedQuery(name = "Marks.findByMark", query = "SELECT m FROM Marks m WHERE m.mark = :mark")})
public class Marks implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "markid")
    private Integer markid;
    @Basic(optional = false)
    @Column(name = "studentid")
    private String studentid;
    @Basic(optional = false)
    @Column(name = "subjectid")
    private String subjectid;
    @Basic(optional = false)
    @Column(name = "taskid")
    private String taskid;
    @Basic(optional = false)
    @Column(name = "mark")
    private int mark;

    public Marks() {
    }

    public Marks(Integer markid) {
        this.markid = markid;
    }

    public Marks(Integer markid, String studentid, String subjectid, String taskid, int mark) {
        this.markid = markid;
        this.studentid = studentid;
        this.subjectid = subjectid;
        this.taskid = taskid;
        this.mark = mark;
    }

    public Integer getMarkid() {
        return markid;
    }

    public void setMarkid(Integer markid) {
        this.markid = markid;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (markid != null ? markid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Marks)) {
            return false;
        }
        Marks other = (Marks) object;
        if ((this.markid == null && other.markid != null) || (this.markid != null && !this.markid.equals(other.markid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nonsuchdata.Marks[ markid=" + markid + " ]";
    }
    
}

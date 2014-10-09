/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonsuchdata;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "tasks")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tasks.findAll", query = "SELECT t FROM Tasks t"),
    @NamedQuery(name = "Tasks.findByTaskid", query = "SELECT t FROM Tasks t WHERE t.tasksPK.taskid = :taskid"),
    @NamedQuery(name = "Tasks.findBySubjectid", query = "SELECT t FROM Tasks t WHERE t.tasksPK.subjectid = :subjectid"),
    @NamedQuery(name = "Tasks.findByDescription", query = "SELECT t FROM Tasks t WHERE t.description = :description"),
    @NamedQuery(name = "Tasks.findByMark", query = "SELECT t FROM Tasks t WHERE t.mark = :mark")})
public class Tasks implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TasksPK tasksPK;
    @Column(name = "description")
    private String description;
    @Column(name = "mark")
    private Integer mark;
    @JoinColumn(name = "subjectid", referencedColumnName = "subjectid", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Subject subject;

    public Tasks() {
    }

    public Tasks(TasksPK tasksPK) {
        this.tasksPK = tasksPK;
    }

    public Tasks(String taskid, String subjectid) {
        this.tasksPK = new TasksPK(taskid, subjectid);
    }

    public TasksPK getTasksPK() {
        return tasksPK;
    }

    public void setTasksPK(TasksPK tasksPK) {
        this.tasksPK = tasksPK;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tasksPK != null ? tasksPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tasks)) {
            return false;
        }
        Tasks other = (Tasks) object;
        if ((this.tasksPK == null && other.tasksPK != null) || (this.tasksPK != null && !this.tasksPK.equals(other.tasksPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nonsuchdata.Tasks[ tasksPK=" + tasksPK + " ]";
    }
    
}

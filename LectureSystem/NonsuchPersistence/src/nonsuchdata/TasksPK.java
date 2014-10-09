/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonsuchdata;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Administrator
 */
@Embeddable
public class TasksPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "taskid")
    private String taskid;
    @Basic(optional = false)
    @Column(name = "subjectid")
    private String subjectid;

    public TasksPK() {
    }

    public TasksPK(String taskid, String subjectid) {
        this.taskid = taskid;
        this.subjectid = subjectid;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (taskid != null ? taskid.hashCode() : 0);
        hash += (subjectid != null ? subjectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TasksPK)) {
            return false;
        }
        TasksPK other = (TasksPK) object;
        if ((this.taskid == null && other.taskid != null) || (this.taskid != null && !this.taskid.equals(other.taskid))) {
            return false;
        }
        if ((this.subjectid == null && other.subjectid != null) || (this.subjectid != null && !this.subjectid.equals(other.subjectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nonsuchdata.TasksPK[ taskid=" + taskid + ", subjectid=" + subjectid + " ]";
    }
    
}

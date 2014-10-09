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
public class EnrolmentPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "studentid")
    private String studentid;
    @Basic(optional = false)
    @Column(name = "subjectid")
    private String subjectid;

    public EnrolmentPK() {
    }

    public EnrolmentPK(String studentid, String subjectid) {
        this.studentid = studentid;
        this.subjectid = subjectid;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentid != null ? studentid.hashCode() : 0);
        hash += (subjectid != null ? subjectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EnrolmentPK)) {
            return false;
        }
        EnrolmentPK other = (EnrolmentPK) object;
        if ((this.studentid == null && other.studentid != null) || (this.studentid != null && !this.studentid.equals(other.studentid))) {
            return false;
        }
        if ((this.subjectid == null && other.subjectid != null) || (this.subjectid != null && !this.subjectid.equals(other.subjectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nonsuchdata.EnrolmentPK[ studentid=" + studentid + ", subjectid=" + subjectid + " ]";
    }
    
}

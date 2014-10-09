/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonsuchdata;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "enrolment")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Enrolment.findAll", query = "SELECT e FROM Enrolment e"),
    @NamedQuery(name = "Enrolment.findByStudentid", query = "SELECT e FROM Enrolment e WHERE e.enrolmentPK.studentid = :studentid"),
    @NamedQuery(name = "Enrolment.findBySubjectid", query = "SELECT e FROM Enrolment e WHERE e.enrolmentPK.subjectid = :subjectid")})
public class Enrolment implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EnrolmentPK enrolmentPK;

    public Enrolment() {
    }

    public Enrolment(EnrolmentPK enrolmentPK) {
        this.enrolmentPK = enrolmentPK;
    }

    public Enrolment(String studentid, String subjectid) {
        this.enrolmentPK = new EnrolmentPK(studentid, subjectid);
    }

    public EnrolmentPK getEnrolmentPK() {
        return enrolmentPK;
    }

    public void setEnrolmentPK(EnrolmentPK enrolmentPK) {
        this.enrolmentPK = enrolmentPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (enrolmentPK != null ? enrolmentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Enrolment)) {
            return false;
        }
        Enrolment other = (Enrolment) object;
        if ((this.enrolmentPK == null && other.enrolmentPK != null) || (this.enrolmentPK != null && !this.enrolmentPK.equals(other.enrolmentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nonsuchdata.Enrolment[ enrolmentPK=" + enrolmentPK + " ]";
    }
    
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonsuchdata;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findByStaffid", query = "SELECT s FROM Staff s WHERE s.staffid = :staffid"),
    @NamedQuery(name = "Staff.findByFullname", query = "SELECT s FROM Staff s WHERE s.fullname = :fullname"),
    @NamedQuery(name = "Staff.findByPassword", query = "SELECT s FROM Staff s WHERE s.password = :password"),
    @NamedQuery(name = "Staff.findByRole", query = "SELECT s FROM Staff s WHERE s.role = :role")})
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "staffid")
    private String staffid;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @JoinTable(name = "teaching", joinColumns = {
        @JoinColumn(name = "staffid", referencedColumnName = "staffid")}, inverseJoinColumns = {
        @JoinColumn(name = "subjectid", referencedColumnName = "subjectid")})
    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<Subject> subjectCollection;

    public Staff() {
    }

    public Staff(String staffid) {
        this.staffid = staffid;
    }

    public String getStaffid() {
        return staffid;
    }

    public void setStaffid(String staffid) {
        this.staffid = staffid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @XmlTransient
    public Collection<Subject> getSubjectCollection() {
        return subjectCollection;
    }

    public void setSubjectCollection(Collection<Subject> subjectCollection) {
        this.subjectCollection = subjectCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (staffid != null ? staffid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.staffid == null && other.staffid != null) || (this.staffid != null && !this.staffid.equals(other.staffid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nonsuchdata.Staff[ staffid=" + staffid + " ]";
    }
    
}

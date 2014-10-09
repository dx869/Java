/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nonsuchdata;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Administrator
 */
@Entity
@Table(name = "subject")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Subject.findAll", query = "SELECT s FROM Subject s"),
    @NamedQuery(name = "Subject.findBySubjectid", query = "SELECT s FROM Subject s WHERE s.subjectid = :subjectid"),
    @NamedQuery(name = "Subject.findByTitle", query = "SELECT s FROM Subject s WHERE s.title = :title"),
    @NamedQuery(name = "Subject.findByContent", query = "SELECT s FROM Subject s WHERE s.content = :content"),
    @NamedQuery(name = "Subject.findByObjectives", query = "SELECT s FROM Subject s WHERE s.objectives = :objectives")})
public class Subject implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "subjectid")
    private String subjectid;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "objectives")
    private String objectives;
    @ManyToMany(mappedBy = "subjectCollection", fetch = FetchType.EAGER)
    private Collection<Staff> staffCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "subject", fetch = FetchType.EAGER)
    private Collection<Tasks> tasksCollection;

    public Subject() {
    }

    public Subject(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getSubjectid() {
        return subjectid;
    }

    public void setSubjectid(String subjectid) {
        this.subjectid = subjectid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getObjectives() {
        return objectives;
    }

    public void setObjectives(String objectives) {
        this.objectives = objectives;
    }

    @XmlTransient
    public Collection<Staff> getStaffCollection() {
        return staffCollection;
    }

    public void setStaffCollection(Collection<Staff> staffCollection) {
        this.staffCollection = staffCollection;
    }

    @XmlTransient
    public Collection<Tasks> getTasksCollection() {
        return tasksCollection;
    }

    public void setTasksCollection(Collection<Tasks> tasksCollection) {
        this.tasksCollection = tasksCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (subjectid != null ? subjectid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Subject)) {
            return false;
        }
        Subject other = (Subject) object;
        if ((this.subjectid == null && other.subjectid != null) || (this.subjectid != null && !this.subjectid.equals(other.subjectid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "nonsuchdata.Subject[ subjectid=" + subjectid + " ]";
    }
    
}

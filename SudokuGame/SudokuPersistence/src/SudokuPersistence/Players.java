/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuPersistence;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rebecca
 */
@Entity
@Table(name = "players")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Players.findAll", query = "SELECT p FROM Players p"),
    @NamedQuery(name = "Players.findByName", query = "SELECT p FROM Players p WHERE p.name = :name"),
    @NamedQuery(name = "Players.findByPassword", query = "SELECT p FROM Players p WHERE p.password = :password"),
    @NamedQuery(name = "Players.findByGameid", query = "SELECT p FROM Players p WHERE p.gameid = :gameid"),
    @NamedQuery(name = "Players.findByCurrentmove", query = "SELECT p FROM Players p WHERE p.currentmove = :currentmove")})
public class Players implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Basic(optional = false)
    @Column(name = "gameid")
    private int gameid;
    @Basic(optional = false)
    @Column(name = "currentmove")
    private String currentmove;

    public Players() {
    }

    public Players(String name) {
        this.name = name;
    }

    public Players(String name, String password, int gameid, String currentmove) {
        this.name = name;
        this.password = password;
        this.gameid = gameid;
        this.currentmove = currentmove;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getCurrentmove() {
        return currentmove;
    }

    public void setCurrentmove(String currentmove) {
        this.currentmove = currentmove;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Players)) {
            return false;
        }
        Players other = (Players) object;
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SudokuPersistence.Players[ name=" + name + " ]";
    }
    
}

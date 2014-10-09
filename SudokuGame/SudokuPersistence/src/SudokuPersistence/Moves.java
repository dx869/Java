/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuPersistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author rebecca
 */
@Entity
@Table(name = "Moves")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Moves.findAll", query = "SELECT m FROM Moves m"),
    @NamedQuery(name = "Moves.findById", query = "SELECT m FROM Moves m WHERE m.id = :id"),
    @NamedQuery(name = "Moves.findByPlayer", query = "SELECT m FROM Moves m WHERE m.player = :player"),
    @NamedQuery(name = "Moves.findByGameid", query = "SELECT m FROM Moves m WHERE m.gameid = :gameid"),
    @NamedQuery(name = "Moves.findByMove", query = "SELECT m FROM Moves m WHERE m.move = :move"),
    @NamedQuery(name = "Moves.findByTime", query = "SELECT m FROM Moves m WHERE m.time = :time")})
public class Moves implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "player")
    private String player;
    @Basic(optional = false)
    @Column(name = "gameid")
    private int gameid;
    @Basic(optional = false)
    @Column(name = "move")
    private String move;
    @Basic(optional = false)
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

    public Moves() {
    }

    public Moves(Integer id) {
        this.id = id;
    }

    public Moves(Integer id, String player, int gameid, String move, Date time) {
        this.id = id;
        this.player = player;
        this.gameid = gameid;
        this.move = move;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getGameid() {
        return gameid;
    }

    public void setGameid(int gameid) {
        this.gameid = gameid;
    }

    public String getMove() {
        return move;
    }

    public void setMove(String move) {
        this.move = move;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Moves)) {
            return false;
        }
        Moves other = (Moves) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SudokuPersistence.Moves[ id=" + id + " ]";
    }
    
}

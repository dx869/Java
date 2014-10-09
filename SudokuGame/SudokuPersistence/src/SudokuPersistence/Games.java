/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package SudokuPersistence;

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
 * @author rebecca
 */
@Entity
@Table(name = "games")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Games.findAll", query = "SELECT g FROM Games g"),
    @NamedQuery(name = "Games.findByGameid", query = "SELECT g FROM Games g WHERE g.gameid = :gameid"),
    @NamedQuery(name = "Games.findByPuzzle", query = "SELECT g FROM Games g WHERE g.puzzle = :puzzle"),
    @NamedQuery(name = "Games.findBySolution", query = "SELECT g FROM Games g WHERE g.solution = :solution"),
    @NamedQuery(name = "Games.findByLevel", query = "SELECT g FROM Games g WHERE g.level = :level"),
    @NamedQuery(name = "Games.findByPlayer", query = "SELECT g FROM Games g WHERE g.player = :player"),
    @NamedQuery(name = "Games.findByFnish", query = "SELECT g FROM Games g WHERE g.fnish = :fnish")})
public class Games implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "gameid")
    private Integer gameid;
    @Basic(optional = false)
    @Column(name = "puzzle")
    private String puzzle;
    @Basic(optional = false)
    @Column(name = "solution")
    private String solution;
    @Basic(optional = false)
    @Column(name = "level")
    private int level;
    @Basic(optional = false)
    @Column(name = "player")
    private String player;
    @Basic(optional = false)
    @Column(name = "fnish")
    private int fnish;

    public Games() {
    }

    public Games(Integer gameid) {
        this.gameid = gameid;
    }

    public Games(Integer gameid, String puzzle, String solution, int level, String player, int fnish) {
        this.gameid = gameid;
        this.puzzle = puzzle;
        this.solution = solution;
        this.level = level;
        this.player = player;
        this.fnish = fnish;
    }

    public Integer getGameid() {
        return gameid;
    }

    public void setGameid(Integer gameid) {
        this.gameid = gameid;
    }

    public String getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(String puzzle) {
        this.puzzle = puzzle;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getFnish() {
        return fnish;
    }

    public void setFnish(int fnish) {
        this.fnish = fnish;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gameid != null ? gameid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Games)) {
            return false;
        }
        Games other = (Games) object;
        if ((this.gameid == null && other.gameid != null) || (this.gameid != null && !this.gameid.equals(other.gameid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "SudokuPersistence.Games[ gameid=" + gameid + " ]";
    }
    
}

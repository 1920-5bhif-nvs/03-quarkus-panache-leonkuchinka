package at.htl.gca.model;

import com.fasterxml.jackson.annotation.*;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@Entity
@NamedQueries({
        @NamedQuery(name = "TeeTime.findAll", query = "select distinct t from TeeTime t join fetch t.players")
})
@NamedEntityGraph(
        name = "teetime-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("players")
        }
)
@XmlAccessorType(XmlAccessType.FIELD)
public class TeeTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    //@Temporal(TemporalType.TIMESTAMP)
    @JsonbDateFormat(value = JsonbDateFormat.DEFAULT_FORMAT)
    private LocalDateTime time;

    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name="GOLFER_TEETIME",
            joinColumns = @JoinColumn(name = "TEETIMEID"),
            inverseJoinColumns = @JoinColumn(name = "GOLFERID")
    )
    private List<Golfer> players;

    //region Constructors
    public TeeTime(LocalDateTime time) {
        this();
        this.time = time;
    }

    public TeeTime() {
        players = new LinkedList<>();
    }
    //endregion

    //region Getter and Setter

    public Long getId() {
        return id;
    }

    public List<Golfer> getPlayers() {
        return players;
    }

    public void addPlayer(Golfer g){
        if(!players.contains(g))
            players.add(g);
    }

    public void removePlayer(Golfer g){
        if(players.contains(g))
            players.remove(g);
    }

    public void setPlayers(List<Golfer> players) {
        this.players = players;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    //endregion
}

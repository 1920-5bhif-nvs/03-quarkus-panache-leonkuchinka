package at.htl.gca.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.LinkedList;
import java.util.List;

@XmlRootElement
@Entity
@NamedQuery(name="Team.findall", query = "select t from Team t")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;

    //region Constructor
    public Team(String name){
        teamName = name;
    }
    public Team(){
    }
    //endregion

    //region Getter and Setter


    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getId() {
        return id;
    }

    public String getTeamName() {
        return teamName;
    }

    //endregion
}

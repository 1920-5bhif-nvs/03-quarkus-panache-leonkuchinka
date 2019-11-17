package at.htl.gca.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NamedQuery(name="Golfer.findall", query="select g from Golfer g")
public class Golfer {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    protected String name;
    protected double hcp;
    protected int age;


    //region Constructors
    public Golfer() {
    }

    public Golfer(String name, double hcp, int age) {
        this();
        this.name = name;
        this.hcp = hcp;
        this.age = age;
    }
    //endregion

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public double getHcp() {
        return hcp;
    }

    public void setHcp(double hcp) {
        this.hcp = hcp;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    //endregion
}

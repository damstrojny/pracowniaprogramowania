package net.javatutorial.tutorials.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.javatutorial.tutorials.Serializers.LeagueSerializer;

import javax.persistence.*;
import java.util.*;

@Entity(name = "League")
@Table(name = "LEAGUE")
@JsonSerialize(using = LeagueSerializer.class)
public class League{

    private int leagueid;
    private String name;
    private String country;

    public League() {

    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "LEAGUE_ID")
    public int getLeagueid() {
        return leagueid;
    }

    public void setLeagueid(int leagueid) {
        this.leagueid = leagueid;
    }

    @Column(name = "LEAGUE_NAME")
    public String getLeagueName() {
        return name;
    }

    public void setLeagueName(String name) {
        this.name = name;
    }

    @Column(name = "COUNTRY")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String toString() {
        return "League [leagueid = " + getLeagueid() + ", name = " + getLeagueName() + ", country = " + getCountry() + "]";
    }

}
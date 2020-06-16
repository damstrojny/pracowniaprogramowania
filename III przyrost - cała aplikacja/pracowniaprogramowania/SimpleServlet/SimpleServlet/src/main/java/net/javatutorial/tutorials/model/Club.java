package net.javatutorial.tutorials.model;

import javax.persistence.*;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.javatutorial.tutorials.Serializers.ClubSerializer;
import net.javatutorial.tutorials.model.League;

@Entity(name = "Club")
@Table(name = "CLUB")
@JsonSerialize(using = ClubSerializer.class)
public class Club{

    private int clubid;
    private String name;
    private League refleague;

    public Club() {

    }

    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "CLUB_ID")
    public int getClubid() {
        return clubid;
    }

    public void setClubid(int clubid) {
        this.clubid = clubid;
    }

    @Column(name = "TEAM_NAME")
    public String getTeamName() {
        return name;
    }

    public void setTeamName(String name) {
        this.name = name;
    }

    @ManyToOne
    @JoinColumn(name = "LEAGUE_ID", referencedColumnName = "LEAGUE_ID")
    public League getLeagueDes(){
        return refleague;
    }

    public void setLeagueDes(League refleague){
        this.refleague = refleague;
    }

    public String toString() {
        return "Club [clubid = " + getClubid() + ", name = " + getTeamName() + ", league = " + getLeagueDes() + "]";
    }


}
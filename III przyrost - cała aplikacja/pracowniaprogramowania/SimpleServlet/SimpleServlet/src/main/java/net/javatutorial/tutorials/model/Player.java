package net.javatutorial.tutorials.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.javatutorial.tutorials.Serializers.PlayerSerializer;

import javax.persistence.*;


@Entity(name = "Player")
@Table(name = "PLAYER")
@JsonSerialize(using = PlayerSerializer.class)
public class Player{

    private int playerId;
    public String firstname;
    public String lastname;
    public Club refclub;
    public IdentityCard identityCardDetails;
    public Passport passportDetails;

    public Player() {

    }

    @Id
    @Column(name = "P_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    @Column(name = "P_FIRSTNAME")
    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "P_LASTNAME")
    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "PASSPORT_NUMBER", referencedColumnName = "PASSPORT_NUMBER")
    public Passport getPassportDetails() {
        return passportDetails;
    }

    public void setPassportDetails(Passport passportDetails) {
        this.passportDetails = passportDetails;
    }

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "IDENTITYCARD_NUMBER", referencedColumnName = "IDENTITYCARD_NUMBER")
    public IdentityCard getIdentityCardDetails() {
        return identityCardDetails;
    }

    public void setIdentityCardDetails(IdentityCard identityCardDetails) {
        this.identityCardDetails = identityCardDetails;
    }

    @ManyToOne
    @JoinColumn(name = "CLUB_ID", referencedColumnName = "CLUB_ID")
    public Club getClubDes(){
        return refclub;
    }

    public void setClubDes(Club refclub){
        this.refclub = refclub;
    }

    public String toString() {
        return "Player [id = " + getPlayerId() +
                ", firstname = " + getFirstName() +
                ", lastname = " + getLastName() +
                ", passport = " + getPassportDetails() +
                ", identitycard = " + getIdentityCardDetails() +
                ", club = " + getClubDes().getClubid();

    }
}
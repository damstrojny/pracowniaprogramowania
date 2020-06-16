package net.javatutorial.tutorials.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.javatutorial.tutorials.Serializers.PassportSerializer;
import org.hibernate.annotations.Type;
import org.hibernate.type.DateType;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;

@Entity(name = "Passport")
@Table(name = "PASSPORT")
@JsonSerialize(using = PassportSerializer.class)
public class Passport{

    private String passportNumber;
    private String kodPanstwa;
    private ZonedDateTime dataWydania;


    public Passport() {

    }

    @Id
    @Column(name = "PASSPORT_NUMBER")
    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    @Column(name = "KOD_PANSTWA")
    public String getKodPanstwa() {
        return kodPanstwa;
    }

    public void setKodPanstwa(String kodPanstwa) {
        this.kodPanstwa = kodPanstwa;
    }

    @Column(name = "dataWydania")
    public ZonedDateTime getDataWydania() {
        return dataWydania;
    }

    public void setDataWydania(ZonedDateTime dataWydania) {
        this.dataWydania = dataWydania;
    }

    public String toString() {
        return "Passport [passportNumber = " + getPassportNumber() +", kodPanstwa = " + getKodPanstwa() + ", dataWydania = " + getDataWydania() + "]";
    }

}
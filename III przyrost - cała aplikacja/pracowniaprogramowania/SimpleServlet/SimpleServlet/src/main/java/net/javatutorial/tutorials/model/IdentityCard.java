package net.javatutorial.tutorials.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import net.javatutorial.tutorials.Serializers.IdentityCardSerializer;
import org.hibernate.annotations.Type;
import org.hibernate.type.DateType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity(name = "IdentityCard")
@Table(name = "IDENTITYCARD")
@JsonSerialize(using = IdentityCardSerializer.class)
public class IdentityCard{

    private String serialNumber;
    private ZonedDateTime dataWydania;


    public IdentityCard() {

    }

    @Id
    @Column(name = "IDENTITYCARD_NUMBER")
    public String getserialNumber() {
        return serialNumber;
    }

    public void setserialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Column(name = "dataWydania")
    public ZonedDateTime getDataWydania() {
        return dataWydania;
    }

    public void setDataWydania(ZonedDateTime dataWydania) {
        this.dataWydania = dataWydania;
    }

    public String toString() {
        return "IdentityCard [serialNumber = " + getserialNumber() + ", dataWydania = " + getDataWydania() + "]";
    }

}
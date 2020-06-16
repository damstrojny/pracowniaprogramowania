package net.javatutorial.tutorials.Serializers;

import net.javatutorial.tutorials.model.Club;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.javatutorial.tutorials.model.IdentityCard;
import net.javatutorial.tutorials.model.Passport;
import net.javatutorial.tutorials.model.Player;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerSerializer extends StdSerializer<Player> {
    public PlayerSerializer(){
        this(null);
    }

    public PlayerSerializer(Class<Player> playerClass){
        super(playerClass);
    }

    @Override
    public void serialize(Player player, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("P_ID", player.getPlayerId());
        jsonGenerator.writeStringField("P_FIRSTNAME", player.getFirstName());
        jsonGenerator.writeStringField("P_LASTNAME", player.getLastName());
        jsonGenerator.writeStringField("PASSPORT_NUMBER", player.getPassportDetails().getPassportNumber());
        jsonGenerator.writeStringField("IDENTITYCARD_NUMBER", player.getIdentityCardDetails().getserialNumber());
        jsonGenerator.writeNumberField("CLUB_ID", player.getClubDes().getClubid());
        jsonGenerator.writeEndObject();
    }
}

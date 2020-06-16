package net.javatutorial.tutorials.Serializers;

import net.javatutorial.tutorials.model.Club;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class ClubSerializer extends StdSerializer<Club> {
    public ClubSerializer(){
        this(null);
    }

    public ClubSerializer(Class<Club> clubClass){
        super(clubClass);
    }

    @Override
    public void serialize(Club club, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("CLUB_ID", club.getClubid());
        jsonGenerator.writeStringField("TEAM_NAME", club.getTeamName());
        jsonGenerator.writeNumberField("LEAGUE_ID", club.getLeagueDes().getLeagueid());
        jsonGenerator.writeEndObject();
    }
}

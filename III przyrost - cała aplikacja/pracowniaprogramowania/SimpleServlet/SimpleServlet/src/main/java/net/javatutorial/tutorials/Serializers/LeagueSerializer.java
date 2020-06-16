package net.javatutorial.tutorials.Serializers;

import net.javatutorial.tutorials.model.Club;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.javatutorial.tutorials.model.League;

import java.io.IOException;

public class LeagueSerializer extends StdSerializer<League> {
    public LeagueSerializer(){
        this(null);
    }

    public LeagueSerializer(Class<League> leagueClass){
        super(leagueClass);
    }

    @Override
    public void serialize(League league, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("LEAGUE_ID", league.getLeagueid());
        jsonGenerator.writeStringField("LEAGUE_NAME", league.getLeagueName());
        jsonGenerator.writeStringField("COUNTRY", league.getCountry());
        jsonGenerator.writeEndObject();
    }
}

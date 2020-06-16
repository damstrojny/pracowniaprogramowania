package net.javatutorial.tutorials.Serializers;

import net.javatutorial.tutorials.model.Club;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.javatutorial.tutorials.model.IdentityCard;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class IdentityCardSerializer extends StdSerializer<IdentityCard> {
    public IdentityCardSerializer(){
        this(null);
    }

    public IdentityCardSerializer(Class<IdentityCard> identityCardClass){
        super(identityCardClass);
    }

    @Override
    public void serialize(IdentityCard identityCard, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
        ZonedDateTime dt = identityCard.getDataWydania();
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").format(dt);

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("IDENTITYCARD_NUMBER", identityCard.getserialNumber());
        jsonGenerator.writeStringField("dataWydania", date);
        jsonGenerator.writeEndObject();
    }
}

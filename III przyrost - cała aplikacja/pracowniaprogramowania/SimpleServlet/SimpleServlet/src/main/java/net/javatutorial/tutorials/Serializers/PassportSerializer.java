package net.javatutorial.tutorials.Serializers;

import net.javatutorial.tutorials.model.Club;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import net.javatutorial.tutorials.model.IdentityCard;
import net.javatutorial.tutorials.model.Passport;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class PassportSerializer extends StdSerializer<Passport> {
    public PassportSerializer(){
        this(null);
    }

    public PassportSerializer(Class<Passport> passportClass){
        super(passportClass);
    }

    @Override
    public void serialize(Passport passport, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException{
        ZonedDateTime dt = passport.getDataWydania();
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS").format(dt);

        jsonGenerator.writeStartObject();
        jsonGenerator.writeStringField("PASSPORT_NUMBER", passport.getPassportNumber());
        jsonGenerator.writeStringField("KOD_PANSTWA", passport.getKodPanstwa());
        jsonGenerator.writeStringField("dataWydania", date);
        jsonGenerator.writeEndObject();
    }
}

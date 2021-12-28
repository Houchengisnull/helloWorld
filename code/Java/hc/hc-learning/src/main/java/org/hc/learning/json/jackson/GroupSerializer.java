package org.hc.learning.json.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.hc.learning.json.pojo.Group;

import java.io.IOException;
import java.util.Optional;

public class GroupSerializer extends StdSerializer<Group> {

    public GroupSerializer() {
        this(null);
    }

    protected GroupSerializer(Class<Group> t) {
        super(t);
    }

    @Override
    public void serialize(Group value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("index", Optional.ofNullable(value.getIndex()).orElse(0));
        gen.writeEndObject();
    }
}

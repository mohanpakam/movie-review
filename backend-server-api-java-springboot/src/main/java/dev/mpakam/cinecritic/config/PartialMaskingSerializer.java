package dev.mpakam.cinecritic.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;

public class PartialMaskingSerializer extends JsonSerializer<String> {
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null || value.isEmpty()) {
            gen.writeString("");
        } else {
            // Apply partial masking logic here
            int visibleChars = Math.min(4, value.length());
            int maskedChars = value.length() - visibleChars;

            StringBuilder maskedValue = new StringBuilder();
            for (int i = 0; i < visibleChars; i++) {
                maskedValue.append(value.charAt(i));
            }

            for (int i = 0; i < maskedChars; i++) {
                maskedValue.append("*");
            }

            gen.writeString(maskedValue.toString());
        }
    }
}


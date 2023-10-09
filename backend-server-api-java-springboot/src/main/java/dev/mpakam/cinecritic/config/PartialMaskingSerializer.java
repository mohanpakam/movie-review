package dev.mpakam.cinecritic.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;


/**
 * Serializes a string value by applying partial masking logic to it.
 * If the value is null or empty, an empty string is serialized.
 * Otherwise, the first 4 characters of the value are visible and the rest are masked with "*".
 *
 * @param value The string value to be serialized.
 * @param gen The JsonGenerator used for serialization.
 * @param serializers The SerializerProvider used for serialization.
 * @throws IOException If an I/O error occurs during serialization.
 */
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


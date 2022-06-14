package com.dot.file.reader.persistence.model.enums.request.duration;

import com.dot.file.reader.persistence.model.enums.request.Duration;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class DurationConverter implements AttributeConverter<Duration, String> {
    /**
     * @param status
     * @return String
     */
    @Override
    public String convertToDatabaseColumn(Duration status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    /**
     * @param code
     * @return CallMode
     */
    @Override
    public Duration convertToEntityAttribute(final String code) {
        if (code == null) {
            return null;
        }

        return Stream.of(Duration.values())
                .filter(c -> c.getCode().equals(code))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
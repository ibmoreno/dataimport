package com.dataimport.api.configuration;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfiguration implements Jackson2ObjectMapperBuilderCustomizer {

    private final String APPLICATION_DATE_FORMAT = "dd/MM/yyyy";
    private final String APPLICATION_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
    @Override
    public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
        jacksonObjectMapperBuilder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .deserializers(new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(APPLICATION_DATE_TIME_FORMAT)))
                .deserializers(new LocalDateDeserializer(DateTimeFormatter.ofPattern(APPLICATION_DATE_FORMAT)))
                .serializers(new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(APPLICATION_DATE_TIME_FORMAT)))
                .serializers(new LocalDateSerializer(DateTimeFormatter.ofPattern(APPLICATION_DATE_FORMAT)))
                .simpleDateFormat(APPLICATION_DATE_TIME_FORMAT)
                .timeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
    }
}

package com.example.chatroom.utils;

import com.example.chatroom.exception.JsonException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
@Slf4j
public class JsonUtils {
    public static final ObjectMapper MAPPER;

    static {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        objectMapper.registerModule(javaTimeModule);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER = objectMapper;
    }

    public static <T> T read(String content, Class<T> valueType) {
        try {
            return MAPPER.readValue(content, valueType);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    public static <T> List<T> readAsList(String content, Class<T> valueType) {
        JavaType listType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, valueType);
        try {
            return MAPPER.readValue(content, listType);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    public static <K, V> Map<K, V> readAsMap(String content, Class<K> keyType, Class<V> valueType) {
        JavaType mapType = TypeFactory.defaultInstance().constructMapType(HashMap.class, keyType, valueType);
        try {
            return MAPPER.readValue(content, mapType);
        } catch (IOException e) {
            throw new JsonException(e);
        }
    }

    public static String write(Object data) {
        try {
            return MAPPER.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new JsonException(e);
        }
    }

    public static String escape(final String json) {
        return new String(JsonStringEncoder.getInstance().quoteAsString(json));
    }

    public static String writeAndEscape(Object data) {
        return escape(write(data));
    }

}

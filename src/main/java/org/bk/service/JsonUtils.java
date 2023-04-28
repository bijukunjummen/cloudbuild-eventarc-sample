package org.bk.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class JsonUtils {
    public static <T> String writeValueAsString(T obj, ObjectMapper objectMapper) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String rawData, Class<T> clazz, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(rawData, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T readValue(String rawData, TypeReference<T> typeReference, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(rawData, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

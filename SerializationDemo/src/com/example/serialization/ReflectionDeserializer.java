package com.example.serialization;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReflectionDeserializer {
    public Object deserialize(String serializedData, String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Object instance = clazz.getDeclaredConstructor().newInstance();
        String[] pairs = serializedData.split(" ");

        for (String pair : pairs) {
            String[] keyValue = pair.split("='");
            String key = keyValue[0].replace("'", "");
            String value = keyValue[1].replace("'", "");

            Field field = clazz.getDeclaredField(key);
            field.setAccessible(true);

            if (field.getType() == String.class) {
                field.set(instance, value);
            } else if (field.getType() == int.class) {
                field.set(instance, Integer.parseInt(value));
            } else if (field.getType() == LocalDate.class) {
                field.set(instance, LocalDate.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            } else if (field.getType() == LocalTime.class) {
                field.set(instance, LocalTime.parse(value, DateTimeFormatter.ofPattern("HH:mm:ss")));
            } else if (field.getType() == LocalDateTime.class) {
                field.set(instance, LocalDateTime.parse(value, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
            }
        }

        return instance;
    }
}
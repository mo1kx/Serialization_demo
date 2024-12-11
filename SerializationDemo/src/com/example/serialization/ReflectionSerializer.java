package com.example.serialization;

import java.lang.reflect.Field;

public class ReflectionSerializer {
    public String serialize(Object object) throws IllegalAccessException {
        StringBuilder serializedData = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();

        for (Field field : fields) {
            field.setAccessible(true);
            serializedData.append("'").append(field.getName()).append("'='").append(field.get(object)).append("' ");
        }

        return serializedData.toString().trim();
    }
}
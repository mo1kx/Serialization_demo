package com.example.serialization;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите имя файла (например, test.meeting): ");
        String fileName = scanner.nextLine();
        String filePath = "data/" + fileName;

        ReflectionSerializer serializer = new ReflectionSerializer();
        ReflectionDeserializer deserializer = new ReflectionDeserializer();

        try {
            // 1. Загружаем объект из файла
            String serializedData = new String(Files.readAllBytes(Paths.get(filePath)));
            Meeting meeting = (Meeting) deserializer.deserialize(serializedData, "com.example.serialization.Meeting");
            System.out.println("Загруженный объект: " + serializedData);

            // 2. Изменяем объект с помощью setter'ов
            System.out.print("Введите новое название собрания: ");
            String newTitle = scanner.nextLine();
            meeting.setTitle(newTitle);

            System.out.print("Введите новое описание собрания: ");
            String newDescription = scanner.nextLine();
            meeting.setDescription(newDescription);

            // 3. Сохраняем объект в файл
            String newSerializedData = serializer.serialize(meeting);
            try (FileWriter writer = new FileWriter(filePath)) {
                writer.write(newSerializedData);
            }
            System.out.println("Объект успешно сохранен в файл: " + filePath);

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Ошибка: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
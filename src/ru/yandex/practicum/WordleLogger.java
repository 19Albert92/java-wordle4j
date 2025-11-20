package ru.yandex.practicum;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class WordleLogger {

    public static PrintWriter createFileLogger(String filename) throws FileNotFoundException {

        createFileIfNotExist(filename);

        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(filename, true),
                StandardCharsets.UTF_8), true);
    }

    public static PrintWriter createTestLogger() {
        return new PrintWriter(System.out, true);
    }

    private static void createFileIfNotExist(String filename) {
        Path path = Path.of(filename);

        if (!Files.exists(path)) {
            try {
                Files.createFile(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.WorldFileException;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;

public class WordleLogger {

    private static final String FILE_NAME = "log.txt";

    private WordleLogger() {}

    public static void log(LoggerTypes type, String message) {

        Path path = Path.of(FILE_NAME);

        if (Files.exists(path)) {

            try (Writer writer = new BufferedWriter(new FileWriter(path.toString(), true))) {

                writer.write(String.format("%s: %s",type.getType(), message));

            } catch (IOException exception) {
                throw new WorldFileException(exception.getMessage());
            }

        } else {
            try {
                Files.createFile(path);

                try (Writer writer = new BufferedWriter(new FileWriter(path.toString()))) {

                    writer.write(String.format("%s: %s",type.getType(), message));

                } catch (IOException exception) {
                    throw new WorldFileException(exception.getMessage());
                }
            } catch (IOException exception) {
                throw new WorldFileException(exception.getMessage());
            }
        }
    }

    public static void log(String message) {
        log(LoggerTypes.INFO, message);
    }
}

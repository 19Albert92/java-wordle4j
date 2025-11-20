package ru.yandex.practicum;

import ru.yandex.practicum.exeptions.EmptyDictionaryException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/*
этот класс содержит в себе всю рутину по работе с файлами словарей и с кодировками
    ему нужны методы по загрузке списка слов из файла по имени файла
    на выходе должен быть класс WordleDictionary
 */
public class WordleDictionaryLoader {

    private final PrintWriter logger;

    public WordleDictionaryLoader(PrintWriter logger) {
        this.logger = logger;
    }

    public WordleDictionary loadFileDictionary(String path, Charset charset) throws EmptyDictionaryException {

        List<String> words = new ArrayList<>();

        Path dictionaryPath = Path.of(path);

        try (BufferedReader br = new BufferedReader(new FileReader(dictionaryPath.toString(), charset))) {

            while (br.ready()) {
                String word = br.readLine();
                if (word.length() == 5) {
                    words.add(word);
                }
            }

            if (words.isEmpty()) {
                throw new EmptyDictionaryException("Словарь пуст!");
            }

            return new WordleDictionary(logger, words);

        } catch (IOException exception) {

            logger.print(exception.getMessage());

            throw new RuntimeException("Файл с таким названием не найден!");
        }
    }

    public WordleDictionary loadFileDictionary(String path) throws EmptyDictionaryException {
        return loadFileDictionary(path, StandardCharsets.UTF_8);
    }
}
